package com.breackneck.mysecondprojectmoneybox.presentation.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.breackneck.mysecondprojectmoneybox.R
import com.breckneck.mysecondprojectmoneybox.domain.usecase.CreateGoalUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class NewGoal: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addnewgoal)

        val createGoal: CreateGoalUseCase by inject()

        val okButton: Button = findViewById(R.id.buttonOk)
        val cancelButton: Button = findViewById(R.id.buttonCancel)
        val itemEditText: EditText = findViewById(R.id.edittextItem)
        val costEditText: EditText = findViewById(R.id.edittextCost)


        cancelButton.setOnClickListener {
            finish()
        }

        okButton.setOnClickListener {
            if ((itemEditText.text.toString() != "") && (costEditText.text.toString() != "") && (costEditText.text.toString() != ".")) {
                lifecycleScope.launch(Dispatchers.IO) {
                    createGoal.execute(id = 1, cost = costEditText.text.toString().toDouble(), money = 0.0, item = itemEditText.text.toString())
                }
                finish()
            }
            else if ((itemEditText.text.toString() == "") && (costEditText.text.toString() == "") && (costEditText.text.toString() == "."))
                Toast.makeText(this, R.string.toastNoInfoNewGoalActivity, Toast.LENGTH_SHORT).show()
            else if ((itemEditText.text.toString() == "") && (costEditText.text.toString() != ""))
                Toast.makeText(this, R.string.toastNoTargetNewGoalActivity, Toast.LENGTH_SHORT).show();
            else if ((itemEditText.text.toString() != "") && (costEditText.text.toString() == "") && (costEditText.text.toString() == "."))
                Toast.makeText(this, R.string.toastNoCostNewGoalActivity, Toast.LENGTH_SHORT).show()
        }
    }
}