package com.breackneck.mysecondprojectmoneybox.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import com.breckneck.mysecondprojectmoneybox.domain.usecase.GetGoalUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getGoalUseCase: GetGoalUseCase
): ViewModel() {

    val resultGoal = MutableLiveData<GoalDomain>()

    init {
        Log.e("TAG", "VM created")
    }

    override fun onCleared() {
        Log.e("TAG", "VM cleared")
        super.onCleared()
    }

    fun getGoal(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val goal = getGoalUseCase.execute(id = id)
            launch(Dispatchers.Main) {
                resultGoal.value = goal
                Log.e("TAG", "goal loaded in VM")
            }
        }
    }

}