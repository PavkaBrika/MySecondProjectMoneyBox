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
    EditText itemDesire;
    EditText addItemCost;
    TextView leftToSaving;

    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_ITEM = "item";
    public static final String APP_PREFERENCES_MONEY = "money";
    public static final String APP_PREFERENCES_COST = "cost";

    public static final String AGE_KEY = "AGE";
    public static final String ACCESS_MESSAGE = "ACCESS_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        moneyQuantity = (TextView) findViewById(R.id.money);

        EditText moneyAddition = (EditText) findViewById(R.id.editText);
        addItemCost = (EditText) findViewById(R.id.costEditText);

        moneyQuantity.setText(Float.toString(money));
        addItemCost.setText("");

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
                        editor.apply();

                        moneyQuantity = (TextView) findViewById(R.id.money);
                        moneyQuantity.setText(Float.toString(money));

                        itemDesire = (EditText) findViewById(R.id.item);
                        itemDesire.setText(item);

                        addItemCost = (EditText) findViewById(R.id.costEditText);
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

        ImageView settingsButton = (ImageView) findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                leftToSaving = (TextView) findViewById(R.id.leftTextView);
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    String accessmessage = intent.getStringExtra(ACCESS_MESSAGE);
                    leftToSaving.setText(accessmessage);
                }
                else {
                    leftToSaving.setText("Error");
                }
            }
        });


        ImageView moneyJar = (ImageView) findViewById(R.id.imageView);
        moneyJar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemDesire = (EditText) findViewById(R.id.item);
                String age = itemDesire.getText().toString();

                Intent intent = new Intent(MainActivity.this, AddNewGoalActivity.class);
                intent.putExtra(AGE_KEY, age);

                startForResult.launch(intent);
            }
        });


//        addItemCost.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(!charSequence.equals("")) {
//                    cost = Float.parseFloat(addItemCost.getText().toString());
//                    calcLeftSum();
//                    if (cost == 0) {
//                        addItemCost.setText("");
//                    }
//                }
//                else {
//                    cost = 0;
//                    addItemCost.setText("");
//                }
//                calcLeftSum();
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

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