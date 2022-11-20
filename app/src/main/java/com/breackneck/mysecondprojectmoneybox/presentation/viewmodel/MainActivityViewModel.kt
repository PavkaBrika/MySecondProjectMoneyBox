package com.breackneck.mysecondprojectmoneybox.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import com.breckneck.mysecondprojectmoneybox.domain.usecase.CheckGoalUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.GetGoalUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getGoalUseCase: GetGoalUseCase,
    private val checkGoalUseCase: CheckGoalUseCase
): ViewModel() {

    val
            resultGoal = MutableLiveData<GoalDomain>()

    init {
        Log.e("TAG", "VM created")
    }

    override fun onCleared() {
        Log.e("TAG", "VM cleared")
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
                resultGoal.value = goal
                Log.e("TAG", "goal loaded in VM")
            }
        }
    }

}