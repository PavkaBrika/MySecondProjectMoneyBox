package com.breackneck.mysecondprojectmoneybox;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        EditText addMoneyView = (EditText) findViewById(R.id.editTextAddMoney);

        ImageView btnPlus = (ImageView) findViewById(R.id.buttonPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!addMoneyView.getText().toString().equals("")) && (!addMoneyView.getText().toString().equals("."))) {
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
                if ((!addMoneyView.getText().toString().equals("")) && (!addMoneyView.getText().toString().equals(".")))  {
                    shareMoney((Float.parseFloat(addMoneyView.getText().toString()))*(-1));
                }
                else  {
                    Toast.makeText(getApplicationContext(), R.string.toastAdd, Toast.LENGTH_SHORT).show();
                }
            }
        });

        addMoneyView.addTextChangedListener(new TextWatcher() {
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

    public void shareMoney(float money) {
        Intent dataMoney = new Intent();
        dataMoney.putExtra(MainActivity.ACTIVITY_FOR_RESULT_ADD_MONEY, money);
        setResult(RESULT_OK, dataMoney);
        finish();
    }
}
