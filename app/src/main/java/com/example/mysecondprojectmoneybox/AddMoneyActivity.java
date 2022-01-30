package com.example.mysecondprojectmoneybox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMoneyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmoneyactivity);

        EditText addMoneyView = (EditText) findViewById(R.id.editTextAddMoney);

        ImageView btnPlus = (ImageView) findViewById(R.id.buttonPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addMoneyView.getText().toString().equals("")) {
                    shareMoney(Float.parseFloat(addMoneyView.getText().toString()));
                }
                else  {
                    Toast.makeText(getApplicationContext(), R.string.toastAdd, Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView btnMinus = (ImageView) findViewById(R.id.buttonMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addMoneyView.getText().toString().equals("")) {
                    shareMoney((Float.parseFloat(addMoneyView.getText().toString()))*(-1));
                }
                else  {
                    Toast.makeText(getApplicationContext(), R.string.toastAdd, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void shareMoney(float money) {
        Intent dataMoney = new Intent();
        dataMoney.putExtra(MainActivity.ACTIVITY_FOR_RESULT_ADD_MONEY, money);
        setResult(RESULT_OK, dataMoney);
        finish();
    }
}
