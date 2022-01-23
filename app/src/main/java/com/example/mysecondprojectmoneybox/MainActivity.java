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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences AppSettings;

    float money;
    float cost;
    String item;
    float left;

    TextView moneyQuantity;
    EditText itemDesire;
    EditText addItemCost;

    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_ITEM = "item";
    public static final String APP_PREFERENCES_MONEY = "money";
    public static final String APP_PREFERENCES_COST = "cost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        moneyQuantity = (TextView) findViewById(R.id.money);

        EditText moneyAddition = (EditText) findViewById(R.id.editText);
        addItemCost = (EditText) findViewById(R.id.costEditText);

        moneyQuantity.setText(Float.toString(money));
        addItemCost.setText(Float.toString(cost));


        ImageView addButton = (ImageView) findViewById(R.id.buttonPlus);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moneyAddition.getText().toString().length() != 0) {
                    money += Float.parseFloat(moneyAddition.getText().toString());
                    if (money % 1 == 0)
                        moneyQuantity.setText(Float.toString(Math.round(money)));
                    else
                        moneyQuantity.setText(Float.toString(money)); //TODO: добавить выскавиващее окошко ввода при нажатии на кнопку добавить или убавить вместо поля ввода
                } else Toast.makeText(getApplicationContext(), R.string.toastAdd, Toast.LENGTH_SHORT).show();  //TODO: добавить музыку после добавления денег
                calcLeftSum();
            }
        });

        ImageView subButton = (ImageView) findViewById(R.id.buttonMinus);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (moneyAddition.getText().toString().length() != 0) {
                        money -= Float.parseFloat(moneyAddition.getText().toString());
                        if (money<0)
                            money = 0;
                        moneyQuantity.setText(Float.toString(money));
                    } else Toast.makeText(getApplicationContext(), R.string.toastAdd, Toast.LENGTH_SHORT).show();
                    calcLeftSum();
            }
        });

        ImageView resButton = (ImageView) findViewById(R.id.buttonReset);
        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money = 0;
                cost = 0;
                item = "";
                calcLeftSum();

                SharedPreferences.Editor editor = AppSettings.edit();
                editor.putFloat(APP_PREFERENCES_MONEY, money);
                editor.putString(APP_PREFERENCES_ITEM, item);
                editor.putFloat(APP_PREFERENCES_COST, cost);
                editor.apply();

                moneyQuantity = (TextView) findViewById(R.id.money);
                moneyQuantity.setText(Float.toString(money));

                itemDesire = (EditText) findViewById(R.id.item);
                itemDesire.setText(item);

                addItemCost = (EditText) findViewById(R.id.costEditText);
                addItemCost.setText("");
            }
        });

        ImageView settingsButton = (ImageView) findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }







    protected void onPause() {
        super.onPause();

        itemDesire = (EditText) findViewById(R.id.item);
        item = itemDesire.getText().toString();

        addItemCost = (EditText) findViewById(R.id.costEditText);
        cost = Float.parseFloat(addItemCost.getText().toString());

        SharedPreferences.Editor editor = AppSettings.edit();
        editor.putFloat(APP_PREFERENCES_MONEY, money);
        editor.putString(APP_PREFERENCES_ITEM, item);
        editor.putFloat(APP_PREFERENCES_COST, cost);
        editor.apply();
    }

    protected void onResume() {
        super.onResume();

        if ((AppSettings.contains(APP_PREFERENCES_MONEY)) && (AppSettings.contains(APP_PREFERENCES_ITEM)) && (AppSettings.contains(APP_PREFERENCES_COST))) {
            money = AppSettings.getFloat(APP_PREFERENCES_MONEY, 0);
            moneyQuantity = (TextView) findViewById(R.id.money);
            moneyQuantity.setText(Float.toString(money));

            item = AppSettings.getString(APP_PREFERENCES_ITEM, "");
            itemDesire = (EditText) findViewById(R.id.item);
            itemDesire.setText(item);

            cost = AppSettings.getFloat(APP_PREFERENCES_COST, 0);
            addItemCost = (EditText) findViewById(R.id.costEditText);
            addItemCost.setText(Float.toString(cost));

            calcLeftSum();
        }

    }

    private void calcLeftSum() {
        left = cost - money;
        TextView leftToSaving = (TextView) findViewById(R.id.leftTextView);
        leftToSaving.setText(Float.toString(left) + " Left");
    }
}