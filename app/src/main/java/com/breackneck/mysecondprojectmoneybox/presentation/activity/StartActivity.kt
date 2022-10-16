package com.breackneck.mysecondprojectmoneybox.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.breackneck.mysecondprojectmoneybox.R
import com.breckneck.mysecondprojectmoneybox.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

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

        val addSubButton: ImageView = findViewById(R.id.buttonAddSubMoney)
        addSubButton.setOnClickListener {
            startActivity(Intent(this, AddMoney::class.java))
        }


    }
}