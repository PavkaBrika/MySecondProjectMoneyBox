package com.breackneck.mysecondprojectmoneybox.presentation.activity

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.breackneck.mysecondprojectmoneybox.R
import com.breckneck.mysecondprojectmoneybox.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

//list activity shared prefs
//private val GOALS_LIST_MEMORY = "goals list memory"
//private val FIRST_ITEM = "firstitem"
//private val FIRST_COST = "firstcost"
//private val FIRST_MONEY = "firstmoney"
//private val SECOND_ITEM = "seconditem"
//private val SECOND_COST = "secondcost"
//private val SECOND_MONEY = "secondmoney"
//private val THIRD_ITEM = "thirditem"
//private val THIRD_COST = "thirdcost"
//private val THIRD_MONEY = "thirdmoney"

class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkMainActivity: CheckMainActivityUseCase by inject()
        val createGoal: CreateGoalUseCase by inject()
        val getLastGoalId: GetLastGoalIdUseCase by inject()
        val getGoal: GetGoalUseCase by inject()
        val migration: MigrationUseCase by inject()

//        val listGoals = getSharedPreferences(GOALS_LIST_MEMORY, Context.MODE_PRIVATE)
        lifecycleScope.launch(Dispatchers.IO) {
            if (!checkMainActivity.execute()) {
                migration.execute()
            }
            val id = getLastGoalId.execute()
            val goal = getGoal.execute(id = id)
            launch(Dispatchers.Main) {
                val costText: TextView = findViewById(R.id.costEditText)
                costText.text = goal.cost.toString()
            }
        }


    }
}