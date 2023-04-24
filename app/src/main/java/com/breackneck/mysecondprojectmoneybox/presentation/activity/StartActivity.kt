package com.breackneck.mysecondprojectmoneybox.presentation.activity

import android.content.Context
import android.graphics.Typeface.BOLD
import android.media.MediaPlayer
import android.os.*
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.appodeal.ads.Appodeal
import com.appodeal.ads.initializing.ApdInitializationCallback
import com.appodeal.ads.initializing.ApdInitializationError
import com.breackneck.mysecondprojectmoneybox.R
import com.breackneck.mysecondprojectmoneybox.adapter.GoalAdapter
import com.breackneck.mysecondprojectmoneybox.databinding.ActivityMainBinding
import com.breackneck.mysecondprojectmoneybox.presentation.viewmodel.MainActivityViewModel
import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import com.breckneck.mysecondprojectmoneybox.domain.usecase.*
import com.breckneck.mysecondprojectmoneybox.domain.usecase.ads.AddButtonClickQuantityUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.ads.GetButtonClicksQuantityUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.ads.SetButtonClickQuantityUseCase
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

    private lateinit var countDownTimer: CountDownTimer

    private val vm by viewModel<MainActivityViewModel>()
    private val getLastGoalId: GetLastGoalIdUseCase by inject()
    val getVibro: GetVibroSettingUseCase by inject()
    val getAudio: GetAudioUseCase by inject()
    val getCharacter: GetCharacterUseCase by inject()
    private val getLastShowGoalUseCase: GetLastShowGoalUseCase by inject()
    private val setLastShowGoalIdUseCase: SetLastShowGoalIdUseCase by inject()
    private val addButtonClickQuantityUseCase: AddButtonClickQuantityUseCase by inject()
    private val getButtonClickQuantityUseCase: GetButtonClicksQuantityUseCase by inject()
    private val setButtonClicksQuantityUseCase: SetButtonClickQuantityUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.debug)
        Appodeal.setChildDirectedTreatment(false)
        Appodeal.muteVideosIfCallsMuted(true)
        Appodeal.initialize(this, "ef7385950c135b27e91511adc3bbb22b25cf7edc8b5c70a1", Appodeal.INTERSTITIAL or Appodeal.BANNER_BOTTOM or Appodeal.SKIPPABLE_VIDEO, object : ApdInitializationCallback {
            override fun onInitializationFinished(errors: List<ApdInitializationError>?) {
                Log.e("TAG", "Appodeal initialized")
            }
        })

        val checkMainActivity: CheckMainActivityUseCase by inject()
        val migration: MigrationUseCase by inject()

        lifecycleScope.launch(Dispatchers.IO) {
            if (!checkMainActivity.execute())
                migration.execute()
            launch(Dispatchers.Main) {
                id = getLastShowGoalUseCase.execute()
                vm.getGoal(id = id)
            }
        }

        Log.e("TAG", "Info wrote")
        vm.resultGoal.observe(this) { goal ->
            Log.e("TAG", "id = ${goal.id}")
            var spannable = SpannableString("${resources.getString(R.string.saving)}\n${goal.item}")
            spannable.setSpan(StyleSpan(BOLD), resources.getString(R.string.saving).length, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.item.text = spannable
            spannable = SpannableString("${resources.getString(R.string.cost)} ${goal.cost}")
            spannable.setSpan(StyleSpan(BOLD), resources.getString(R.string.cost).length, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.costEditText.text = spannable
            calcLeftSum(cost = goal.cost, money = goal.money)
            setCoinsInJar(cost = goal.cost, money = goal.money)
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
                4 -> changeCharacter(R.drawable.booba, 216, 203)
            }
        }

        binding.imageView.setOnClickListener {
            if ((vm.resultGoal.value?.item?.trim() == "") && (vm.resultGoal.value?.cost == 0.0)) {
                showNewGoalBottomSheetDialog()
                addButtonClickQuantityUseCase.execute()
            } else {
                Toast.makeText(this, R.string.alertDialogMessageNewGoal, Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonSettings.setOnClickListener {
            showSettingsBottomSheetDialog()
            addButtonClickQuantityUseCase.execute()
        }

        binding.imageViewCharacter.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 29)
                startVibration(VibrationEffect.EFFECT_TICK, getVibro.execute())
            else
                startVibration(VibrationEffect.DEFAULT_AMPLITUDE, getVibro.execute())
            if (binding.imageViewThoughts.visibility == View.INVISIBLE) {
                if (vm.resultGoal.value?.item?.trim() != "" && vm.resultGoal.value?.cost != 0.0) {
                    showThoughts()
                    addButtonClickQuantityUseCase.execute()
                } else {
                    Toast.makeText(this, R.string.toastOnCharacterClick, Toast.LENGTH_SHORT).show()
                }
            } else {
                hideThoughts()
            }
        }

        binding.imageViewThoughts.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 29)
                startVibration(VibrationEffect.EFFECT_TICK, getVibro.execute())
            else
                startVibration(VibrationEffect.DEFAULT_AMPLITUDE, getVibro.execute())
            if (binding.imageViewThoughts.visibility != View.INVISIBLE)
                hideThoughts()
        }

        binding.buttonAddSubMoney.setOnClickListener {
            showAddMoneyBottomSheetDialog()
            addButtonClickQuantityUseCase.execute()
        }

        binding.buttonReset.setOnClickListener {
            showResetBottomSheetDialog()
            addButtonClickQuantityUseCase.execute()
        }

        binding.buttonGoalsList.setOnClickListener {
            showGoalsListBottomSheetDialog()
            addButtonClickQuantityUseCase.execute()
        }

        countDownTimer = object: CountDownTimer(6000, 1000) {

            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                if (binding.imageViewThoughts.visibility == View.VISIBLE)
                    hideThoughts()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        vm.getGoal(id = id)
        Appodeal.show(this, Appodeal.BANNER_BOTTOM)
    }

    private fun startVibration(vibrationEffect: Int, enabled: Boolean) {
        if (enabled) {
            val effect = VibrationEffect.createOneShot(150, vibrationEffect)
            if (Build.VERSION.SDK_INT >= 31) {
                val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator = vibratorManager.defaultVibrator
                vibrator.vibrate(effect)
            } else {
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(effect)
            }
        }
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
        binding.imageViewThoughts.startAnimation(anim)
    }

    private fun calcLeftSum(cost: Double, money: Double) {
        val left = cost - money
        if (left > 0) {
            val spannable = SpannableString("${decimalFormat.format(left)} ${getString(R.string.left)}")
            spannable.setSpan(StyleSpan(BOLD), 0, decimalFormat.format(left).length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.leftTextView.text = spannable
            binding.buttonAddSubMoney.visibility = View.VISIBLE
            binding.changeAmountHintTextView.visibility = View.VISIBLE
            binding.HintToAddNewGoalTextView.visibility = View.INVISIBLE
            binding.leftTextView.textSize = 30F
        } else {
            binding.leftTextView.apply {
                text = getString(R.string.congratulations)
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
                    setLastShowGoalIdUseCase.execute(id = id)
                    vm.getGoal(id = id)
                }
                showThoughts()
                bottomSheetDialogNewGoal.cancel()
                showInterstitialAd()
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

        moneyEditText!!.addTextChangedListener {
            val str = it!!.toString()
            val p = str.indexOf(".")
            if (p != - 1) {
                val tmpStr = str.substring(p)
                if (tmpStr.length == 4)
                    it.delete(it.length - 1, it.length)
            }
        }

        plusButton!!.setOnClickListener {
            if (moneyEditText.text.toString() != "") {
                lifecycleScope.launch(Dispatchers.IO) {
                    changeMoney.execute(
                        id = id,
                        money = vm.resultGoal.value!!.money + moneyEditText.text.toString()
                            .toDouble()
                    )
                    vm.getGoal(id = id)
                }
                showThoughts()
                startAudio(getAudio.execute())
                if (Build.VERSION.SDK_INT >= 29)
                    startVibration(VibrationEffect.EFFECT_HEAVY_CLICK, getVibro.execute())
                else
                    startVibration(VibrationEffect.DEFAULT_AMPLITUDE, getVibro.execute())
                bottomSheetDialogAddMoney.cancel()
                showInterstitialAd()
            } else {
                Toast.makeText(this, R.string.toastAdd, Toast.LENGTH_SHORT).show()
            }
        }

        minusButton!!.setOnClickListener {
            if (moneyEditText.text.toString() != "") {
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
                showInterstitialAd()
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
            if (binding.imageViewThoughts.visibility == View.VISIBLE)
                hideThoughts()
            startVibration(VibrationEffect.DEFAULT_AMPLITUDE, getVibro.execute())
            bottomSheetDialogReset.cancel()
            showInterstitialAd()
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
            showInterstitialAd()
        }

        val onGoalClickListener = object: GoalAdapter.OnGoalClickListener {
            override fun onGoalClick(goalDomain: GoalDomain, position: Int) {
                id = goalDomain.id
                vm.getGoal(id = id)
                setLastShowGoalIdUseCase.execute(id = id)
                showThoughts()
                bottomSheetDialogGoalsList.cancel()
                showInterstitialAd()
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
        val setVibration: SetVibroUseCase by inject()

        val griffButton = bottomSheetDialogSettings.findViewById<RadioButton>(R.id.griffButton)
        val mrKrabsButton = bottomSheetDialogSettings.findViewById<RadioButton>(R.id.mrkrabsButton)
        val mcDuckButton = bottomSheetDialogSettings.findViewById<RadioButton>(R.id.mcduckButton)
        val boobaButton = bottomSheetDialogSettings.findViewById<RadioButton>(R.id.boobaButton)

        val vibrationCheckBox =
            bottomSheetDialogSettings.findViewById<CheckBox>(R.id.checkBoxEnableVibration)
        val audioCheckBox =
            bottomSheetDialogSettings.findViewById<CheckBox>(R.id.checkBoxEnableSound)

        val okButton = bottomSheetDialogSettings.findViewById<Button>(R.id.buttonOk)
        val cancelButton = bottomSheetDialogSettings.findViewById<Button>(R.id.buttonCancel)

        when (getCharacter.execute()) {
            1 -> griffButton!!.isChecked = true
            2 -> mrKrabsButton!!.isChecked = true
            3 -> mcDuckButton!!.isChecked = true
            4 -> boobaButton!!.isChecked = true
        }

        audioCheckBox!!.isChecked = getAudio.execute()
        vibrationCheckBox!!.isChecked = getVibro.execute()

        okButton!!.setOnClickListener {
            setAudio.execute(audioCheckBox.isChecked)
            setVibration.execute(vibrationCheckBox.isChecked)
            if (griffButton!!.isChecked)
                setCharacter.execute(1)
            if (mrKrabsButton!!.isChecked)
                setCharacter.execute(2)
            if (mcDuckButton!!.isChecked)
                setCharacter.execute(3)
            if (boobaButton!!.isChecked)
                setCharacter.execute(4)
            vm.getCharacter()
            bottomSheetDialogSettings.cancel()
            showInterstitialAd()
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

    private fun showThoughts() {
        countDownTimer.start()
        startAnim(R.anim.startthoughtsanim)
        binding.imageViewThoughts.visibility = View.VISIBLE
        binding.item.visibility = View.VISIBLE
        binding.costEditText.visibility = View.VISIBLE
        binding.mainActivityHintTextView.visibility = View.INVISIBLE
        binding.leftTextView.visibility = View.VISIBLE
        calcLeftSum(
            cost = vm.resultGoal.value!!.cost,
            money = vm.resultGoal.value!!.money
        )
    }

    private fun hideThoughts() {
        startAnim(R.anim.finishthoughtsanim)
        binding.mainActivityHintTextView.visibility = View.VISIBLE
        binding.imageViewThoughts.visibility = View.INVISIBLE
        binding.item.visibility = View.INVISIBLE
        binding.costEditText.visibility = View.INVISIBLE
        binding.leftTextView.visibility = View.INVISIBLE
        countDownTimer.cancel()
    }

    private fun showInterstitialAd() {
        val hello = getButtonClickQuantityUseCase.execute()
        if ((hello >= 15) && (Appodeal.isLoaded(Appodeal.INTERSTITIAL))) {
            Appodeal.show(this, Appodeal.INTERSTITIAL)
            setButtonClicksQuantityUseCase.execute(0)
        }
    }

    private fun setCoinsInJar(cost: Double, money: Double) {
        val percent = (money / cost) * 100
        if (percent > 10)
            binding.coins1ImageView.visibility = View.VISIBLE
        else
            binding.coins1ImageView.visibility = View.GONE
        if (percent > 20)
            binding.coins2ImageView.visibility = View.VISIBLE
        else
            binding.coins2ImageView.visibility = View.GONE
        if (percent > 30)
            binding.coins3ImageView.visibility = View.VISIBLE
        else
            binding.coins3ImageView.visibility = View.GONE
        if (percent > 40)
            binding.coins4ImageView.visibility = View.VISIBLE
        else
            binding.coins4ImageView.visibility = View.GONE
        if (percent > 50)
            binding.coins5ImageView.visibility = View.VISIBLE
        else
            binding.coins5ImageView.visibility = View.GONE
        if (percent > 60)
            binding.coins6ImageView.visibility = View.VISIBLE
        else
            binding.coins6ImageView.visibility = View.GONE
        if (percent > 70)
            binding.coins7ImageView.visibility = View.VISIBLE
        else
            binding.coins7ImageView.visibility = View.GONE
        if (percent > 80)
            binding.coins8ImageView.visibility = View.VISIBLE
        else
            binding.coins8ImageView.visibility = View.GONE
        if (percent > 90)
            binding.coins9ImageView.visibility = View.VISIBLE
        else
            binding.coins9ImageView.visibility = View.GONE
    }
}