package com.breackneck.mysecondprojectmoneybox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class GoalsListActivity extends AppCompatActivity {

    private SharedPreferences AppSettings;

    DecimalFormat decimalFormat = new DecimalFormat( "#.##" );

    public static final String GOALS_LIST_MEMORY = "goals list memory";
    public static final String FIRST_ITEM = "firstitem";
    public static final String FIRST_COST = "firstcost";
    public static final String FIRST_MONEY = "firstmoney";
    public static final String SECOND_ITEM = "seconditem";
    public static final String SECOND_COST = "secondcost";
    public static final String SECOND_MONEY = "secondmoney";
    public static final String THIRD_ITEM = "thirditem";
    public static final String THIRD_COST = "thirdcost";
    public static final String THIRD_MONEY = "thirdmoney";

    String firstItem;
    float firstCost, firstMoney, left;
    Button addNewGoalButton;

    TextView firstItemView;
    TextView firstCostView;
    TextView firstMoneyView;
    ProgressBar firstBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goals_list_activity);

        AppSettings = getSharedPreferences(GOALS_LIST_MEMORY, Context.MODE_PRIVATE);

        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        firstItemView = findViewById(R.id.firstgoal);
        firstCostView = findViewById(R.id.firstgoalcost);
        firstMoneyView = findViewById(R.id.firstgoalmoney);
        firstBar = findViewById(R.id.firstBar);


        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            firstItem = arguments.getString("item");
            firstCost = arguments.getFloat("cost");
            firstMoney = arguments.getFloat("money");

        }
        left = firstCost - firstMoney;
        firstItemView.setText(firstItem);
        firstCostView.setText(decimalFormat.format(left));
        firstMoneyView.setText(decimalFormat.format(firstMoney));
        firstBar.setProgress((int) ((firstMoney/firstCost) * 100));

        addNewGoalButton = findViewById(R.id.addNewGoalButton);
        addNewGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStringInMemory(FIRST_ITEM, firstItem);
                saveFloatInMemory(FIRST_COST, firstCost);
                saveFloatInMemory(FIRST_MONEY, firstMoney);
                firstItem = "";
                firstCost = 0;
                firstMoney = 0;
                selectGoal(firstItem, firstCost, firstMoney);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((AppSettings.contains(FIRST_ITEM)) && (AppSettings.contains(FIRST_COST)) && (AppSettings.contains(FIRST_MONEY))) {
            firstItem = AppSettings.getString(FIRST_ITEM, "");
            firstCost = AppSettings.getFloat(FIRST_COST, 0);
            firstMoney = AppSettings.getFloat(FIRST_MONEY, 0);

            firstItemView.setText(firstItem);
            firstCostView.setText(decimalFormat.format(left));
            firstMoneyView.setText(decimalFormat.format(firstMoney));
            firstBar.setProgress((int) ((firstMoney/firstCost) * 100));
        }
    }

    public void selectGoal(String item, float cost, float money) {
        Intent dataCharacter = new Intent();
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_ITEM, item);
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_COST, cost);
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_MONEY, money);
        setResult(RESULT_OK, dataCharacter);
        finish();
    }

    private void saveFloatInMemory(String preferences, float variable) {
        SharedPreferences.Editor editor = AppSettings.edit();
        editor.putFloat(preferences, variable);
        editor.apply();
    }

    private void saveStringInMemory(String preferences, String variable) {
        SharedPreferences.Editor editor = AppSettings.edit();
        editor.putString(preferences, variable);
        editor.apply();
    }
}
