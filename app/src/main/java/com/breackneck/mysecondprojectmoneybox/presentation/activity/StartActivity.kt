package com.breackneck.mysecondprojectmoneybox.presentation.activity

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.breackneck.mysecondprojectmoneybox.R
import com.breackneck.mysecondprojectmoneybox.adapter.GoalAdapter
import com.breackneck.mysecondprojectmoneybox.databinding.ActivityMainBinding
import com.breackneck.mysecondprojectmoneybox.presentation.viewmodel.MainActivityViewModel
import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import com.breckneck.mysecondprojectmoneybox.domain.usecase.*
import com.breckneck.mysecondprojectmoneybox.domain.usecase.settings.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var id = 0

    private val decimalFormat = DecimalFormat("#.##")

    private val vm by viewModel<MainActivityViewModel>()
    private val getLastGoalId: GetLastGoalIdUseCase by inject()
    val getVibro: GetVibroSettingUseCase by inject()
    val getAudio: GetAudioUseCase by inject()
    val getCharacter: GetCharacterUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val checkMainActivity: CheckMainActivityUseCase by inject()

        val getGoal: GetGoalUseCase by inject()
        val migration: MigrationUseCase by inject()

        lifecycleScope.launch(Dispatchers.IO) {
            if (!checkMainActivity.execute())
                migration.execute()
            id = getLastGoalId.execute()
        }

        Log.e("TAG", "Info wrote")
        vm.resultGoal.observe(this) { goal ->
            binding.item.text = goal.item
            binding.costEditText.text = goal.cost.toString()
            calcLeftSum(cost = goal.cost, money = goal.money)
            if ((goal.cost != 0.0) && (goal.item != "")) { //if target is existed
                binding.money.text = decimalFormat.format(goal.money)
                binding.money.visibility = View.VISIBLE
                binding.HintToAddNewGoalTextView.visibility = View.INVISIBLE
                binding.buttonReset.visibility = View.VISIBLE
                binding.resetHintTextView.visibility = View.VISIBLE
            } else {
                binding.HintToAddNewGoalTextView.visibility = View.VISIBLE
                binding.buttonReset.visibility = View.INVISIBLE
                binding.resetHintTextView.visibility = View.INVISIBLE
                binding.money.visibility = View.INVISIBLE
            }
        }

        vm.resultCharacter.observe(this) { character ->
            when (character) {
                1 -> changeCharacter(R.drawable.griff, 216, 203)
                2 -> changeCharacter(R.drawable.krabs, 250, 250)
                3 -> changeCharacter(R.drawable.mcduck, 216, 203)
            }
        }

        binding.imageView.setOnClickListener {
            if ((vm.resultGoal.value?.item?.trim() == "") && (vm.resultGoal.value?.cost == 0.0)) {
                showNewGoalBottomSheetDialog()
            } else {
                Toast.makeText(this, R.string.alertDialogMessageNewGoal, Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonSettings.setOnClickListener {
            showSettingsBottomSheetDialog()
        }

        binding.imageViewCharacter.setOnClickListener {
            startVibration(VibrationEffect.EFFECT_TICK, getVibro.execute())
            if (binding.imageViewThoughts.visibility == View.INVISIBLE) {
                if (vm.resultGoal.value?.item?.trim() != "" && vm.resultGoal.value?.cost != 0.0) {
                    startAnim(R.anim.startthoughtsanim)
                    binding.imageViewThoughts.visibility = View.VISIBLE
                    binding.item.visibility = View.VISIBLE
                    binding.costEditText.visibility = View.VISIBLE
                    binding.mainActivityHintTextView.visibility = View.INVISIBLE
                    binding.leftTextView.visibility = View.VISIBLE
                    binding.textView.visibility = View.VISIBLE
                    binding.textView2.visibility = View.VISIBLE
                    calcLeftSum(
                        cost = vm.resultGoal.value!!.cost,
                        money = vm.resultGoal.value!!.money
                    )
                } else {
                    Toast.makeText(this, R.string.toastOnCharacterClick, Toast.LENGTH_SHORT).show()
                }
            } else {
                startAnim(R.anim.finishthoughtsanim)
                binding.mainActivityHintTextView.visibility = View.VISIBLE
                binding.imageViewThoughts.visibility = View.INVISIBLE
                binding.item.visibility = View.INVISIBLE
                binding.costEditText.visibility = View.INVISIBLE
                binding.leftTextView.visibility = View.INVISIBLE
                binding.textView.visibility = View.INVISIBLE
                binding.textView2.visibility = View.INVISIBLE
            }
        }

        binding.buttonAddSubMoney.setOnClickListener {
            showAddMoneyBottomSheetDialog()
        }

        binding.buttonReset.setOnClickListener {
            showResetBottomSheetDialog()
        }

        binding.buttonGoalsList.setOnClickListener {
            showGoalsListBottomSheetDialog()
        }

    }

    override fun onResume() {
        super.onResume()
        vm.getGoal(id = id)
    }


    private fun startVibration(vibrationEffect: Int, enabled: Boolean) {
//        if (enabled) {
//            val vibrator = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as Vibrator
//            val effect = VibrationEffect.createOneShot(150, vibrationEffect)
//            vibrator.vibrate(effect)
//        }
    }

    private fun startAudio(enabled: Boolean) {
        if (enabled) {
            val player = MediaPlayer.create(this, R.raw.coinssound)
            player.start()
        }
    }

    private fun startAnim(animation: Int) {
        val anim = AnimationUtils.loadAnimation(this, animation)
        binding.costEditText.startAnimation(anim)
        binding.item.startAnimation(anim)
        binding.leftTextView.startAnimation(anim)
        binding.textView.startAnimation(anim)
        binding.textView2.startAnimation(anim)
        binding.imageViewThoughts.startAnimation(anim)
    }

    private fun calcLeftSum(cost: Double, money: Double) {
        val left = cost - money
        if (left > 0) {
            binding.leftTextView.text = "${decimalFormat.format(left)} ${getString(R.string.left)}"
            binding.buttonAddSubMoney.visibility = View.VISIBLE
            binding.changeAmountHintTextView.visibility = View.VISIBLE
            binding.HintToAddNewGoalTextView.visibility = View.INVISIBLE
            binding.leftTextView.textSize = 30F
        } else {
            binding.leftTextView.apply {
                text = getString(R.string.congratulations)
                textSize = 25F
                setPadding(170, 0, 170, 0)
            }
            binding.buttonAddSubMoney.visibility = View.INVISIBLE
            binding.changeAmountHintTextView.visibility = View.INVISIBLE
            binding.HintToAddNewGoalTextView.visibility = View.INVISIBLE
        }
    }

    private fun showNewGoalBottomSheetDialog() {
        val createGoal: CreateGoalUseCase by inject()

        val bottomSheetDialogNewGoal = BottomSheetDialog(this)
        bottomSheetDialogNewGoal.setContentView(R.layout.addnewgoal)

        val buttonOk = bottomSheetDialogNewGoal.findViewById<Button>(R.id.buttonOk)
        val buttonCancel = bottomSheetDialogNewGoal.findViewById<Button>(R.id.buttonCancel)
        val itemEditText = bottomSheetDialogNewGoal.findViewById<EditText>(R.id.edittextItem)
        val costEditText = bottomSheetDialogNewGoal.findViewById<EditText>(R.id.edittextCost)

        buttonCancel!!.setOnClickListener {
            bottomSheetDialogNewGoal.dismiss()
        }

        buttonOk!!.setOnClickListener {
            if ((itemEditText!!.text.toString() != "") && (costEditText!!.text.toString() != "") && (costEditText.text.toString() != ".")) {
                lifecycleScope.launch(Dispatchers.IO) {
                    createGoal.execute(
                        id = 1,
                        cost = costEditText.text.toString().toDouble(),
                        money = 0.0,
                        item = itemEditText.text.toString()
                    )
                    id = getLastGoalId.execute()
                    vm.getGoal(id = id)
                }
                bottomSheetDialogNewGoal.cancel()
            } else if ((itemEditText.text.toString() == "") && (costEditText!!.text.toString() == "") && (costEditText.text.toString() == "."))
                Toast.makeText(this, R.string.toastNoInfoNewGoalActivity, Toast.LENGTH_SHORT).show()
            else if ((itemEditText.text.toString() == "") && (costEditText!!.text.toString() != ""))
                Toast.makeText(this, R.string.toastNoTargetNewGoalActivity, Toast.LENGTH_SHORT)
                    .show();
            else if ((itemEditText.text.toString() != "") && (costEditText!!.text.toString() == "") && (costEditText.text.toString() == "."))
                Toast.makeText(this, R.string.toastNoCostNewGoalActivity, Toast.LENGTH_SHORT).show()
        }

        bottomSheetDialogNewGoal.show()
    }

    private fun showAddMoneyBottomSheetDialog() {
        val changeMoney: ChangeMoneyUseCase by inject()

        val bottomSheetDialogAddMoney = BottomSheetDialog(this)
        bottomSheetDialogAddMoney.setContentView(R.layout.addmoneyactivity)

        val moneyEditText = bottomSheetDialogAddMoney.findViewById<EditText>(R.id.editTextAddMoney)
        val minusButton = bottomSheetDialogAddMoney.findViewById<Button>(R.id.buttonMinus)
        val plusButton = bottomSheetDialogAddMoney.findViewById<Button>(R.id.buttonPlus)

        plusButton!!.setOnClickListener {
            if (moneyEditText!!.text.toString() != "") {
                lifecycleScope.launch(Dispatchers.IO) {
                    changeMoney.execute(
                        id = id,
                        money = vm.resultGoal.value!!.money + moneyEditText.text.toString()
                            .toDouble()
                    )
                    vm.getGoal(id = id)
                }
                startAudio(getAudio.execute())
                bottomSheetDialogAddMoney.cancel()
            } else {
                Toast.makeText(this, R.string.toastAdd, Toast.LENGTH_SHORT).show()
            }
        }

        minusButton!!.setOnClickListener {
            if (moneyEditText!!.text.toString() != "") {
                lifecycleScope.launch(Dispatchers.IO) {
                    changeMoney.execute(
                        id = id,
                        money = vm.resultGoal.value!!.money - moneyEditText.text.toString()
                            .toDouble()
                    )
                    vm.getGoal(id = id)
                }
                startAudio(getAudio.execute())
                bottomSheetDialogAddMoney.cancel()
            } else {
                Toast.makeText(this, R.string.toastAdd, Toast.LENGTH_SHORT).show()
            }
        }

        bottomSheetDialogAddMoney.show()
    }

    private fun showResetBottomSheetDialog() {
        val resetGoalUseCase: ResetGoalUseCase by inject()

        val bottomSheetDialogReset = BottomSheetDialog(this)
        bottomSheetDialogReset.setContentView(R.layout.reset_bottom_sheet_layout)

        val resetButton = bottomSheetDialogReset.findViewById<Button>(R.id.reset_button)
        val cancelButton = bottomSheetDialogReset.findViewById<Button>(R.id.cancel_button)

        resetButton!!.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                resetGoalUseCase.execute(id = id)
                id = 0
                vm.getGoal(id = id)
            }
            bottomSheetDialogReset.cancel()
        }

        cancelButton!!.setOnClickListener {
            bottomSheetDialogReset.cancel()
        }

        bottomSheetDialogReset.show()
    }

    private fun showGoalsListBottomSheetDialog() {
        val bottomSheetDialogGoalsList = BottomSheetDialog(this)
        bottomSheetDialogGoalsList.setContentView(R.layout.goalslist)

        val getAllGoalsUseCase: GetAllGoalsUseCase by inject()

        val goalsListRecyclerView = bottomSheetDialogGoalsList.findViewById<RecyclerView>(R.id.goalsListRecyclerView)
        val addGoalButton = bottomSheetDialogGoalsList.findViewById<ImageView>(R.id.addGoalButtonImageView)

        addGoalButton!!.setOnClickListener {
            id = 0
            vm.getGoal(id = id)
            bottomSheetDialogGoalsList.cancel()
        }

        val onGoalClickListener = object: GoalAdapter.OnGoalClickListener {
            override fun onGoalClick(goalDomain: GoalDomain, position: Int) {
                id = goalDomain.id
                vm.getGoal(id = id)
                bottomSheetDialogGoalsList.cancel()
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val goals = getAllGoalsUseCase.execute()
            this.launch(Dispatchers.Main) {
                val adapter = GoalAdapter(goalList = goals, goalClickListener = onGoalClickListener)
                goalsListRecyclerView!!.adapter = adapter
            }
        }

        bottomSheetDialogGoalsList.show()
    }

    private fun showSettingsBottomSheetDialog() {
        val bottomSheetDialogSettings = BottomSheetDialog(this)
        bottomSheetDialogSettings.setContentView(R.layout.change_character)

        val setAudio: SetAudioUseCase by inject()
        val setCharacter: SetCharacterUseCase by inject()
        val setVibro: SetVibroUseCase by inject()

        val griffButton = bottomSheetDialogSettings.findViewById<RadioButton>(R.id.griffButton)
        val mrkrabsButton = bottomSheetDialogSettings.findViewById<RadioButton>(R.id.mrkrabsButton)
        val mcDuckButton = bottomSheetDialogSettings.findViewById<RadioButton>(R.id.mcduckButton)

        val vibrationCheckBox =
            bottomSheetDialogSettings.findViewById<CheckBox>(R.id.checkBoxEnableVibration)
        val audioCheckBox =
            bottomSheetDialogSettings.findViewById<CheckBox>(R.id.checkBoxEnableSound)

        val okButton = bottomSheetDialogSettings.findViewById<Button>(R.id.buttonOk)
        val cancelButton = bottomSheetDialogSettings.findViewById<Button>(R.id.buttonCancel)

        when (getCharacter.execute()) {
            1 -> griffButton!!.isChecked = true
            2 -> mrkrabsButton!!.isChecked = true
            3 -> mcDuckButton!!.isChecked = true
        }

        audioCheckBox!!.isChecked = getAudio.execute()
        vibrationCheckBox!!.isChecked = getVibro.execute()

        okButton!!.setOnClickListener {
            setAudio.execute(audioCheckBox.isChecked)
            setVibro.execute(vibrationCheckBox.isChecked)
            if (griffButton!!.isChecked)
                setCharacter.execute(1)
            if (mrkrabsButton!!.isChecked)
                setCharacter.execute(2)
            if (mcDuckButton!!.isChecked)
                setCharacter.execute(3)
            vm.getCharacter()
            bottomSheetDialogSettings.cancel()
        }

        cancelButton!!.setOnClickListener {
            bottomSheetDialogSettings.cancel()
        }

        bottomSheetDialogSettings.show()
    }

    private fun changeCharacter(character: Int, height: Int, width: Int) {
        binding.imageViewCharacter.setImageResource(character)
        binding.imageViewCharacter.layoutParams.height = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            height.toFloat(),
            resources.displayMetrics
        ).toInt()
        binding.imageViewCharacter.layoutParams.width = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            width.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

}