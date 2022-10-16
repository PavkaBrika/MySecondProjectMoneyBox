package com.breackneck.mysecondprojectmoneybox.presentation.activity

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.breackneck.mysecondprojectmoneybox.R
import com.breckneck.mysecondprojectmoneybox.domain.usecase.GetGoalUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.GetLastGoalIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class AddMoney: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addmoneyactivity)

        val getLastGoalId: GetLastGoalIdUseCase by inject()

        val addMoneyEditText: EditText = findViewById(R.id.editTextAddMoney)

        lifecycleScope.launch(Dispatchers.IO) {
            val id = getLastGoalId.execute()

        }
    }
}