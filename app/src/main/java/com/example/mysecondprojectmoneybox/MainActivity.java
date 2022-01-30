package com.example.mysecondprojectmoneybox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    TextView itemDesire;
    TextView addItemCost;
    TextView leftToSaving;

    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_ITEM = "item";
    public static final String APP_PREFERENCES_MONEY = "money";
    public static final String APP_PREFERENCES_COST = "cost";
    public static final String ACTIVITY_FOR_RESULT_ADD_MONEY = "addmoney";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        moneyQuantity = (TextView) findViewById(R.id.money);

        addItemCost = (TextView) findViewById(R.id.costEditText);

        moneyQuantity.setText(Float.toString(money));
        addItemCost.setText(""); //TODO: добавить музыку после добавления денег


        ImageView resButton = (ImageView) findViewById(R.id.buttonReset);
        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.alertDialogTitle);
                builder.setMessage(R.string.alertDialogMessage);
                builder.setPositiveButton(R.string.alertDialogPositiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        money = 0;
                        cost = 0;
                        item = "";
                        calcLeftSum();

                        SharedPreferences.Editor editor = AppSettings.edit();
                        editor.putFloat(APP_PREFERENCES_MONEY, money);
                        editor.putString(APP_PREFERENCES_ITEM, item);
                        editor.putFloat(APP_PREFERENCES_COST, cost);
                        editor.commit();
                        editor.apply();

                        moneyQuantity = (TextView) findViewById(R.id.money);
                        moneyQuantity.setText(Float.toString(money));

                        itemDesire = (TextView) findViewById(R.id.item);
                        itemDesire.setText(item);

                        addItemCost = (TextView) findViewById(R.id.costEditText);
                        addItemCost.setText("");

                        leftToSaving = (TextView) findViewById(R.id.leftTextView);
                        leftToSaving.setVisibility(View.INVISIBLE);
                        dialogInterface.cancel();
                    }
                });
                builder.setNegativeButton(R.string.alertDialogNegativeButton, null);
                builder.show();
            }
        });


        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                itemDesire = (TextView) findViewById(R.id.item);
                addItemCost = (TextView) findViewById(R.id.costEditText);
                if(result.getResultCode() == RESULT_OK){
                    Intent intent = result.getData();
                    item = intent.getStringExtra(APP_PREFERENCES_ITEM);
                    cost = intent.getFloatExtra(APP_PREFERENCES_COST, 0);
                    SharedPreferences.Editor editor = AppSettings.edit();
                    editor.putString(APP_PREFERENCES_ITEM, item);
                    editor.putFloat(APP_PREFERENCES_COST, cost);
                    editor.apply();
                    itemDesire.setText(item);
                    addItemCost.setText(Float.toString(cost));
                }
                else{
                    itemDesire.setText("Error");
                    addItemCost.setText("Error");
                }
            }
        });

        ActivityResultLauncher<Intent> startForResultAddMoney = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                float addmoney = 0;
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    addmoney = intent.getFloatExtra(ACTIVITY_FOR_RESULT_ADD_MONEY, 0);
                    money += addmoney;
                    SharedPreferences.Editor editor = AppSettings.edit();
                    editor.putFloat(APP_PREFERENCES_MONEY, money);
                    editor.apply();
                    moneyQuantity.setText(Float.toString(money));
                }
                else {
                    moneyQuantity.setText("Error");
                }
            }
        });

        ImageView settingsButton = (ImageView) findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMoneyActivity.class);

                startForResultAddMoney.launch(intent);
            }
        });

        ImageView moneyJar = (ImageView) findViewById(R.id.imageView);
        moneyJar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((item.equals("")) && (cost == 0)) {
                    Intent intent = new Intent(MainActivity.this, AddNewGoalActivity.class);
                    startForResult.launch(intent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.alertDialogTitleNewGoal);
                    builder.setMessage(R.string.alertDialogMessageNewGoal);
                    builder.setPositiveButton("Ok", null);
                    builder.show();
                }
            }
        });


    }

    protected void onPause() {
        super.onPause();

        itemDesire = (TextView) findViewById(R.id.item);
        item = itemDesire.getText().toString();

        addItemCost = (TextView) findViewById(R.id.costEditText);
        if (addItemCost.getText().toString().equals(""))
            cost = 0;
        else
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
            itemDesire = (TextView) findViewById(R.id.item);
            itemDesire.setText(item);

            cost = AppSettings.getFloat(APP_PREFERENCES_COST, 0);
            addItemCost = (TextView) findViewById(R.id.costEditText);
            if (cost != 0) {
                addItemCost.setText(Float.toString(cost));
            }
            else addItemCost.setText("");

            calcLeftSum();
        }

    }

    private void calcLeftSum() {
        left = cost - money;
        leftToSaving = (TextView) findViewById(R.id.leftTextView);
        if (left > 0) {
            leftToSaving.setText(Float.toString(left) + " Left");
            leftToSaving.setVisibility(View.VISIBLE);
        }
        else if (left < 0) {
            leftToSaving.setText(R.string.congratulations);
            leftToSaving.setVisibility(View.VISIBLE);
        }
    }


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}