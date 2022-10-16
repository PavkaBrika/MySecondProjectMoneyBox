package com.breackneck.mysecondprojectmoneybox.presentation.activity

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.breackneck.mysecondprojectmoneybox.R
import com.breackneck.mysecondprojectmoneybox.presentation.viewmodel.MainActivityViewModel
import com.breckneck.mysecondprojectmoneybox.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class StartActivity: AppCompatActivity() {

    val decimalFormat = DecimalFormat("#.##")

    private val vm by viewModel<MainActivityViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkMainActivity: CheckMainActivityUseCase by inject()
        val createGoal: CreateGoalUseCase by inject()
        val getLastGoalId: GetLastGoalIdUseCase by inject()
        val getGoal: GetGoalUseCase by inject()
        val migration: MigrationUseCase by inject()
        val getVibro: GetVibroSettingUseCase by inject()

        val moneyQuantityTextView: TextView = findViewById(R.id.money)
        val costTextView: TextView = findViewById(R.id.costEditText)
        val itemTextView: TextView = findViewById(R.id.item)
        val leftToSaveTextView: TextView = findViewById(R.id.leftTextView)
        val textFirst: TextView = findViewById(R.id.textView)
        val textSecond: TextView = findViewById(R.id.textView2)
        val jarHintTextView: TextView = findViewById(R.id.HintToAddNewGoalTextView)
        val hintMainActivityTextView: TextView = findViewById(R.id.mainActivityHintTextView)
        val thoughtsImageView: ImageView = findViewById(R.id.imageViewThoughts)
        val coinsFirstImageView: ImageView = findViewById(R.id.coins1)
        val coinsSecondImageView: ImageView = findViewById(R.id.coins2)
        val coinsThirdImageView: ImageView = findViewById(R.id.coins3)
        val coinsForthImageView: ImageView = findViewById(R.id.coins4)
        val coinsFifthImageView: ImageView = findViewById(R.id.coins5)
        val coinsSixthImageView: ImageView = findViewById(R.id.coins6)
        val coinsSeventhImageView: ImageView = findViewById(R.id.coins7)
        val coinsEighthImageView: ImageView = findViewById(R.id.coins8)
        val coinsNinthImageView: ImageView = findViewById(R.id.coins9)

        val player = MediaPlayer.create(this, R.raw.coinssound)

        lifecycleScope.launch(Dispatchers.IO) {
            if (!checkMainActivity.execute()) {
                migration.execute()
            }
        }

        val id = getLastGoalId.execute()
        vm.getGoal(id = id)
        Log.e("TAG", "Info wrote")
        vm.resultGoal.observe(this) { goal ->
            itemTextView.text = goal.item
            costTextView.text = goal.cost.toString()
            moneyQuantityTextView.text = decimalFormat.format(goal.money)
        }




        val moneyJarImageView: ImageView = findViewById(R.id.imageView)
        moneyJarImageView.setOnClickListener {
            if ((vm.resultGoal.value?.item?.trim() == "") && (vm.resultGoal.value?.cost == 0.0)) {
                startActivity( Intent(this, NewGoal::class.java))
            } else {
                Toast.makeText(this, R.string.alertDialogMessageNewGoal, Toast.LENGTH_SHORT).show()
            }
        }

        val characterImageView: ImageView = findViewById(R.id.imageViewCharacter)
        characterImageView.setOnClickListener {
            startVibration(VibrationEffect.EFFECT_TICK, getVibro.execute())
            if (thoughtsImageView.visibility == View.INVISIBLE) {
                if (vm.resultGoal.value?.item?.trim() != "" && vm.resultGoal.value?.cost != 0.0) {
                    startAnim(R.anim.startthoughtsanim)
                    thoughtsImageView.visibility = View.VISIBLE
                    itemTextView.visibility = View.VISIBLE
                    itemTextView.visibility = View.VISIBLE
                    hintMainActivityTextView.visibility = View.INVISIBLE
                    leftToSaveTextView.visibility = View.VISIBLE
                    textFirst.visibility = View.VISIBLE
                    textSecond.visibility = View.VISIBLE

                }
            }
        }

        val addSubButton: ImageView = findViewById(R.id.buttonAddSubMoney)
        addSubButton.setOnClickListener {
            startActivity(Intent(this, AddMoney::class.java))
        }






    }


    private fun startVibration(vibrationEffect: Int, enabled: Boolean) {
        if (enabled) {
            val vibrator = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as Vibrator
            val effect = VibrationEffect.createOneShot(150, vibrationEffect)
            vibrator.vibrate(effect)
        }
    }

    private fun startAnim(animation: Int) {
        val anim = AnimationUtils.loadAnimation(this, animation)
        val costTextView: TextView = findViewById(R.id.costEditText)
        val itemTextView: TextView = findViewById(R.id.item)
        val leftToSaveTextView: TextView = findViewById(R.id.leftTextView)
        val textFirst: TextView = findViewById(R.id.textView)
        val textSecond: TextView = findViewById(R.id.textView2)
        val thoughtsImageView: ImageView = findViewById(R.id.imageViewThoughts)
        costTextView.startAnimation(anim)
        itemTextView.startAnimation(anim)
        leftToSaveTextView.startAnimation(anim)
        textFirst.startAnimation(anim)
        textSecond.startAnimation(anim)
        thoughtsImageView.startAnimation(anim)
    }

    private fun calcLeftSum(cost: Double, money: Double) {
        val left = cost - money
        if (left > 0) {

        }
    }
}