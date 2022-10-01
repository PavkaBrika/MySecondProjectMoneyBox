package com.breackneck.mysecondprojectmoneybox.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.breackneck.mysecondprojectmoneybox.R;

public class AddNewGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewgoal);

        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

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
                if ((!itemEnterView.getText().toString().equals("")) && (!costEnterView.getText().toString().equals("")) && ((!costEnterView.getText().toString().equals(".")))) {
                    data.putExtra(MainActivity.APP_PREFERENCES_ITEM, itemEnterView.getText().toString());
                    data.putExtra(MainActivity.APP_PREFERENCES_COST, Float.parseFloat(costEnterView.getText().toString()));
                    setResult(RESULT_OK, data);
                    finish();
                }
                else if ((itemEnterView.getText().toString().equals("")) && (costEnterView.getText().toString().equals("")) && (costEnterView.getText().toString().equals("."))) {
                    Toast.makeText(getApplicationContext(), R.string.toastNoInfoNewGoalActivity, Toast.LENGTH_SHORT).show();
                }
                else if ((itemEnterView.getText().toString().equals("")) && (!costEnterView.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), R.string.toastNoTargetNewGoalActivity, Toast.LENGTH_SHORT).show();
                }
                else if ((!itemEnterView.getText().toString().equals("")) && (costEnterView.getText().toString().equals("")) && (costEnterView.getText().toString().equals("."))) {
                    Toast.makeText(getApplicationContext(), R.string.toastNoCostNewGoalActivity, Toast.LENGTH_SHORT).show();
                }
            }
        });

        costEnterView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                int p = str.indexOf(".");
                if (p != -1) {
                    String tmpStr = str.substring(p);
                    if (tmpStr.length() == 4) {
                        editable.delete(editable.length() - 1, editable.length());
                    }
                }
            }
        });
    }
}
