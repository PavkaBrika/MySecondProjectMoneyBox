package com.example.mysecondprojectmoneybox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences AppSettings;

    float money;
    String item;

    TextView moneyQuantity;
    EditText itemDesire;

    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_ITEM = "item";
    public static final String APP_PREFERENCES_MONEY = "money";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        moneyQuantity = (TextView) findViewById(R.id.money);

        EditText moneyAddition = (EditText) findViewById(R.id.editText);

        moneyQuantity.setText(Float.toString(money));

        Button addButton = (Button) findViewById(R.id.buttonPlus);
        Button settingsButton = (Button) findViewById(R.id.buttonSettings);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moneyAddition.getText().toString().length() != 0) {
                    money += Float.parseFloat(moneyAddition.getText().toString());
                    moneyQuantity.setText(Float.toString(money)); //TODO: добавить выскавиващее окошко ввода при нажатии на кнопку добавить или убавить вместо поля ввода
                } else Toast.makeText(getApplicationContext(), R.string.toastAdd, Toast.LENGTH_SHORT).show();  //TODO: добавить музыку после добавления денег
            }
        });

        Button subButton = (Button) findViewById(R.id.buttonMinus);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (moneyAddition.getText().toString().length() != 0) {
                        money -= Float.parseFloat(moneyAddition.getText().toString());
                        if (money<0)
                            money = 0;
                        moneyQuantity.setText(Float.toString(money));
                    } else Toast.makeText(getApplicationContext(), R.string.toastAdd, Toast.LENGTH_SHORT).show();
            }
        });

        Button resButton = (Button) findViewById(R.id.buttonReset);
        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money = 0;
                item = "";

                SharedPreferences.Editor editor = AppSettings.edit();
                editor.putFloat(APP_PREFERENCES_MONEY, money);
                editor.putString(APP_PREFERENCES_ITEM, item);
                editor.apply();

                moneyQuantity = (TextView) findViewById(R.id.money);
                moneyQuantity.setText(Float.toString(money));

                itemDesire = (EditText) findViewById(R.id.item);
                itemDesire.setText(item);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }







    protected void onPause() {
        super.onPause();

        itemDesire = findViewById(R.id.item);
        item = itemDesire.getText().toString();

        SharedPreferences.Editor editor = AppSettings.edit();
        editor.putFloat(APP_PREFERENCES_MONEY, money);
        editor.putString(APP_PREFERENCES_ITEM, item);
        editor.apply();
    }

    protected void onResume() {
        super.onResume();

        if ((AppSettings.contains(APP_PREFERENCES_MONEY)) && (AppSettings.contains(APP_PREFERENCES_ITEM))) {
            money = AppSettings.getFloat(APP_PREFERENCES_MONEY, 0);
            moneyQuantity = (TextView) findViewById(R.id.money);
            moneyQuantity.setText(Float.toString(money));

            item = AppSettings.getString(APP_PREFERENCES_ITEM, "");
            itemDesire = (EditText) findViewById(R.id.item);
            itemDesire.setText(item);
        }

    }
}