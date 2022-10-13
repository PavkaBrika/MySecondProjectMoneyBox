package com.breackneck.mysecondprojectmoneybox.presentation.activity

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.breackneck.mysecondprojectmoneybox.R
import com.breckneck.mysecondprojectmoneybox.domain.usecase.CheckMainActivityUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.CreateGoalUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.GetGoalUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.GetLastGoalIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

//list activity shared prefs
private val GOALS_LIST_MEMORY = "goals list memory"
private val FIRST_ITEM = "firstitem"
private val FIRST_COST = "firstcost"
private val FIRST_MONEY = "firstmoney"
private val SECOND_ITEM = "seconditem"
private val SECOND_COST = "secondcost"
private val SECOND_MONEY = "secondmoney"
private val THIRD_ITEM = "thirditem"
private val THIRD_COST = "thirdcost"
private val THIRD_MONEY = "thirdmoney"

class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkMainActivity: CheckMainActivityUseCase by inject()
        val createGoal: CreateGoalUseCase by inject()
        val getLastGoalId: GetLastGoalIdUseCase by inject()
        val getGoal: GetGoalUseCase by inject()

        val listGoals = getSharedPreferences(GOALS_LIST_MEMORY, Context.MODE_PRIVATE)

        if (!checkMainActivity.execute()) {
            lifecycleScope.launch(Dispatchers.IO) {
                val id = getLastGoalId.execute()
                if (id == 1) {
                    if ((listGoals.contains(FIRST_COST)) && (listGoals.contains(FIRST_MONEY)) && (listGoals.contains(FIRST_ITEM)))
                        createGoal.execute(id = 1, cost = listGoals.getFloat(FIRST_COST, 0F).toDouble(), money = listGoals.getFloat(FIRST_MONEY, 0F).toDouble(), item = listGoals.getString(FIRST_ITEM, "").toString())
                    if ((listGoals.contains(SECOND_COST)) && (listGoals.contains(SECOND_MONEY)) && (listGoals.contains(SECOND_ITEM)))
                        createGoal.execute(id = 2, cost = listGoals.getFloat(SECOND_COST, 0F).toDouble(), money = listGoals.getFloat(SECOND_MONEY, 0F).toDouble(), item = listGoals.getString(SECOND_ITEM, "").toString())
                    if ((listGoals.contains(THIRD_COST)) && (listGoals.contains(THIRD_MONEY)) && (listGoals.contains(THIRD_ITEM)))
                        createGoal.execute(id = 3, cost = listGoals.getFloat(THIRD_COST, 0F).toDouble(), money = listGoals.getFloat(THIRD_MONEY, 0F).toDouble(), item = listGoals.getString(THIRD_ITEM, "").toString())
                } else if (id == 2) {

                }

            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val id = getLastGoalId.execute()
            val goal = getGoal.execute(id = id)
            launch(Dispatchers.Main) {
                val costText: TextView = findViewById(R.id.costEditText)
                costText.text = goal.cost.toString()
            }
        }


    }
}