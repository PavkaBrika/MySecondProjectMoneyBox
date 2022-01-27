package com.example.mysecondprojectmoneybox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNewGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewgoal);

        EditText itemEnterView = (EditText) findViewById(R.id.edittextItem);
        EditText costEnterView = (EditText) findViewById(R.id.edittextCost);

        Button cancelView = (Button) findViewById(R.id.buttonCancel);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        Button okView = (Button) findViewById(R.id.buttonOk);
        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra(MainActivity.APP_PREFERENCES_ITEM, itemEnterView.getText().toString());
                data.putExtra(MainActivity.APP_PREFERENCES_COST, Float.parseFloat(costEnterView.getText().toString()));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
