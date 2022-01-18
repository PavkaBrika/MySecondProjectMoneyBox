package com.example.mysecondprojectmoneybox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    float money = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView moneyQuantity = (TextView) findViewById(R.id.money);
        EditText moneyAddition = (EditText) findViewById(R.id.editText);
        moneyQuantity.setText(Float.toString(money));
        Button addButton = (Button) findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moneyAddition.getText().toString().length() != 0) {
                    money += Float.parseFloat(moneyAddition.getText().toString());
                    moneyQuantity.setText(Float.toString(money));
                } else Toast.makeText(getApplicationContext(), R.string.toastAdd, Toast.LENGTH_SHORT).show();  //TODO: добавить музыку после добавления денег
            }
        });
    }
}