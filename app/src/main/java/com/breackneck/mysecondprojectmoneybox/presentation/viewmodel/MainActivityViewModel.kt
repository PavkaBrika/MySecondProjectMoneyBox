package com.breackneck.mysecondprojectmoneybox.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import com.breckneck.mysecondprojectmoneybox.domain.usecase.CheckGoalUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.GetGoalUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.SetLastShowGoalIdUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.ads.GetButtonClicksQuantityUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.ads.SetButtonClickQuantityUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.settings.GetCharacterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getGoalUseCase: GetGoalUseCase,
    private val checkGoalUseCase: CheckGoalUseCase,
    private val getCharacterUseCase: GetCharacterUseCase,
    private val setLastShowGoalIdUseCase: SetLastShowGoalIdUseCase,
    private val getButtonClickQuantityUseCase: GetButtonClicksQuantityUseCase,
    private val setButtonClicksQuantityUseCase: SetButtonClickQuantityUseCase
): ViewModel() {

    private val _resultGoal = MutableLiveData<GoalDomain>()
    val resultGoal: LiveData<GoalDomain>
        get() = _resultGoal
    private val _resultCharacter = MutableLiveData<Int>()
    val resultCharacter: LiveData<Int>
        get() = _resultCharacter
    private val _actionClickQuantity = MutableLiveData<Int>()
    val actionClickQuantity: LiveData<Int>
        get() = _actionClickQuantity


    init {
        Log.e("TAG", "VM created")
        getActionClickQuantity()
    }

    override fun onCleared() {
        Log.e("TAG", "VM cleared")
        setButtonClicksQuantityUseCase.execute(actionClickQuantity.value!!)
        super.onCleared()
    }

    fun getGoal(id: Int) {
        var goal: GoalDomain
        viewModelScope.launch(Dispatchers.IO) {
            if (checkGoalUseCase.execute(id = id)) {
                goal = getGoalUseCase.execute(id = id)
            } else {
                goal = GoalDomain(id = 1, cost = 0.0, money = 0.0, item = "")
            }
            launch(Dispatchers.Main) {
                _resultGoal.value = goal
                Log.e("TAG", "goal loaded in VM")
            }
        }
    }

    fun getCharacter() {
        _resultCharacter.value = getCharacterUseCase.execute()
        Log.e("TAG", "Character loaded in VM")
    }

    private fun getActionClickQuantity() {
        _actionClickQuantity.value = getButtonClickQuantityUseCase.execute()
    }

    fun incrementActionClickQuantity() {
        _actionClickQuantity.value = _actionClickQuantity.value!! + 1
    }

    fun refreshActionClickQuantity() {
        _actionClickQuantity.value = 0
    }
}