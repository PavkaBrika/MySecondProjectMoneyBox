package com.example.mysecondprojectmoneybox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddNewGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewgoal);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView ageView = (TextView) findViewById(R.id.textViewAge);
            String age = extras.getString(MainActivity.AGE_KEY);
            ageView.setText("Age = " + age);
        }

        Button openView = (Button) findViewById(R.id.buttonOpen);
        openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("Open");
            }
        });

        Button closeView = (Button) findViewById(R.id.buttonClose);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("Close");
            }
        });

        Button invalidView = (Button) findViewById(R.id.buttonInvalid);
        invalidView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("Invalid age");
            }
        });

        Button cancelView = (Button) findViewById(R.id.buttonCancel);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }




    public void sendMessage(String message) {
        Intent data = new Intent();
        data.putExtra(MainActivity.ACCESS_MESSAGE, message);
        setResult(RESULT_OK, data);
        finish();
    }
}
