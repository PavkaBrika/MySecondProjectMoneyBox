package com.example.mysecondprojectmoneybox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewgoal);

        Button cancelButton = (Button) findViewById(R.id.btnCancel);
        Button okayButton = (Button) findViewById(R.id.btnOk);
        EditText itemEditText = (EditText) findViewById(R.id.costEditText);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });


        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(itemEditText.getText().toString());
            }
        });

    }

    public void sendMessage(String message) {
        Intent data = new Intent();
        data.putExtra(MainActivity.ITEM_KEY, message);
        setResult(RESULT_OK, data);
        finish();
    }
}
