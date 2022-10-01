package com.breackneck.mysecondprojectmoneybox.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.breackneck.mysecondprojectmoneybox.R;

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

    String item, firstItem, secondItem, thirdItem;
    float cost, money, firstCost, firstMoney, secondCost, secondMoney, thirdCost, thirdMoney, left;
    Button createFirstGoal, createSecondGoal, createThirdGoal, cancelButton;

    int id;

    TextView firstItemView;
    TextView firstCostView;
    TextView firstMoneyView;
    TextView firstLine;
    ProgressBar firstBar;
    LinearLayout firstGoalButton;
    TextView secondItemView;
    TextView secondCostView;
    TextView secondMoneyView;
    TextView secondLine;
    ProgressBar secondBar;
    LinearLayout secondGoalButton;
    TextView thirdItemView;
    TextView thirdCostView;
    TextView thirdMoneyView;
    TextView thirdLine;
    ProgressBar thirdBar;
    LinearLayout thirdGoalButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goals_list_activity);
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        AppSettings = getSharedPreferences(GOALS_LIST_MEMORY, Context.MODE_PRIVATE);

        firstItemView = findViewById(R.id.firstgoal);
        firstCostView = findViewById(R.id.firstgoalcost);
        firstMoneyView = findViewById(R.id.firstgoalmoney);
        firstBar = findViewById(R.id.firstBar);
        firstLine = findViewById(R.id.firstLine);
        secondItemView = findViewById(R.id.secondgoal);
        secondCostView = findViewById(R.id.secondgoalcost);
        secondMoneyView = findViewById(R.id.secondgoalmoney);
        secondBar = findViewById(R.id.secondBar);
        secondLine = findViewById(R.id.secondLine);
        thirdItemView = findViewById(R.id.thirdgoal);
        thirdCostView = findViewById(R.id.thirdgoalcost);
        thirdMoneyView = findViewById(R.id.thirdgoalmoney);
        thirdBar = findViewById(R.id.thirdBar);

        int nightModeFlags =
                v.getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                firstBar.getProgressDrawable().setColorFilter(
                        getColor(R.color.buttonColorNight), android.graphics.PorterDuff.Mode.SRC_IN);
                secondBar.getProgressDrawable().setColorFilter(
                        getColor(R.color.buttonColorNight), android.graphics.PorterDuff.Mode.SRC_IN);
                thirdBar.getProgressDrawable().setColorFilter(
                        getColor(R.color.buttonColorNight), android.graphics.PorterDuff.Mode.SRC_IN);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                firstBar.getProgressDrawable().setColorFilter(
                        getColor(R.color.buttonColor), android.graphics.PorterDuff.Mode.SRC_IN);
                secondBar.getProgressDrawable().setColorFilter(
                        getColor(R.color.buttonColor), android.graphics.PorterDuff.Mode.SRC_IN);
                thirdBar.getProgressDrawable().setColorFilter(
                        getColor(R.color.buttonColor), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
        }


        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            item = arguments.getString("item");
            cost = arguments.getFloat("cost");
            money = arguments.getFloat("money");
            id = arguments.getInt("id");
        }
        if (id == 1) {
            saveStringInMemory(FIRST_ITEM, item);
            saveFloatInMemory(FIRST_COST, cost);
            saveFloatInMemory(FIRST_MONEY, money);
        }
        else if (id == 2) {
            saveStringInMemory(SECOND_ITEM, item);
            saveFloatInMemory(SECOND_COST, cost);
            saveFloatInMemory(SECOND_MONEY, money);
        }
        else if (id == 3) {
            saveStringInMemory(THIRD_ITEM, item);
            saveFloatInMemory(THIRD_COST, cost);
            saveFloatInMemory(THIRD_MONEY, money);
        }

        createFirstGoal = findViewById(R.id.firstGoalCreate);
        createFirstGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = "";
                cost = 0;
                money = 0;
                id = 1;
                selectGoal(item, cost, money, id);
            }
        });

        createSecondGoal = findViewById(R.id.secondGoalCreate);
        createSecondGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = "";
                cost = 0;
                money = 0;
                id = 2;
                selectGoal(item, cost, money, id);
            }
        });

        createThirdGoal = findViewById(R.id.thirdGoalCreate);
        createThirdGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = "";
                cost = 0;
                money = 0;
                id = 3;
                selectGoal(item, cost, money, id);
            }
        });

        firstGoalButton = findViewById(R.id.firstGoalButton);
        firstGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = AppSettings.getString(FIRST_ITEM, "");
                cost = AppSettings.getFloat(FIRST_COST, 0);
                money = AppSettings.getFloat(FIRST_MONEY, 0);
                id = 1;
                selectGoal(item, cost, money, id);
            }
        });

        secondGoalButton = findViewById(R.id.secondGoalButton);
        secondGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = AppSettings.getString(SECOND_ITEM, "");
                cost = AppSettings.getFloat(SECOND_COST, 0);
                money = AppSettings.getFloat(SECOND_MONEY, 0);
                id = 2;
                selectGoal(item, cost, money, id);
            }
        });

        thirdGoalButton = findViewById(R.id.thirdGoalButton);
        thirdGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = AppSettings.getString(THIRD_ITEM, "");
                cost = AppSettings.getFloat(THIRD_COST, 0);
                money = AppSettings.getFloat(THIRD_MONEY, 0);
                id = 3;
                selectGoal(item, cost, money, id);
            }
        });

        cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        float left = 0;
        if ((AppSettings.contains(FIRST_ITEM)) && (AppSettings.contains(FIRST_COST)) && (AppSettings.contains(FIRST_MONEY)) && (!(AppSettings.getString(FIRST_ITEM, "").equals("")))) {
            firstItem = AppSettings.getString(FIRST_ITEM, "");
            firstCost = AppSettings.getFloat(FIRST_COST, 0);
            firstMoney = AppSettings.getFloat(FIRST_MONEY, 0);

            firstItemView.setText(firstItem);
            left = firstCost - firstMoney;
            if (left > 0) {
                firstCostView.setText((decimalFormat.format(left)) + " " + getString(R.string.left));
            }
            else {
                firstCostView.setText(getString(R.string.alreadyFinished));
            }

            firstMoneyView.setText(decimalFormat.format(firstMoney) + " " + getString(R.string.progressBarText));
            firstBar.setProgress((int) ((firstMoney/firstCost) * 100));
            createFirstGoal.setVisibility(View.GONE);
        }
        else {
            firstGoalButton.setVisibility(View.GONE);
        }
        if ((AppSettings.contains(SECOND_ITEM)) && (AppSettings.contains(SECOND_COST)) && (AppSettings.contains(SECOND_MONEY)) && (!(AppSettings.getString(SECOND_ITEM, "").equals("")))) {
            secondItem = AppSettings.getString(SECOND_ITEM, "");
            secondCost = AppSettings.getFloat(SECOND_COST, 0);
            secondMoney = AppSettings.getFloat(SECOND_MONEY, 0);

            secondItemView.setText(secondItem);
            secondCostView.setText(decimalFormat.format((secondCost - secondMoney)) + " " + getString(R.string.left));
            secondMoneyView.setText(decimalFormat.format(secondMoney) + " " + getString(R.string.progressBarText));
            secondBar.setProgress((int) ((secondMoney/secondCost) * 100));
            createSecondGoal.setVisibility(View.GONE);
        }
        else {
            secondGoalButton.setVisibility(View.GONE);
        }
        if ((AppSettings.contains(THIRD_ITEM)) && (AppSettings.contains(THIRD_COST)) && (!(AppSettings.getString(THIRD_ITEM, "").equals("")))) {
            thirdItem = AppSettings.getString(THIRD_ITEM, "");
            thirdCost = AppSettings.getFloat(THIRD_COST, 0);
            thirdMoney = AppSettings.getFloat(THIRD_MONEY, 0);

            thirdItemView.setText(thirdItem);
            thirdCostView.setText(decimalFormat.format((thirdCost - thirdMoney)) + " " + getString(R.string.left));
            thirdMoneyView.setText(decimalFormat.format(thirdMoney) + " " + getString(R.string.progressBarText));
            thirdBar.setProgress((int) ((thirdMoney/thirdCost) * 100));
            createThirdGoal.setVisibility(View.GONE);
        }
        else {
            thirdGoalButton.setVisibility(View.GONE);
        }
    }

    public void selectGoal(String item, float cost, float money, int id) {
        Intent dataCharacter = new Intent();
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_ITEM, item);
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_COST, cost);
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_MONEY, money);
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_GOAL_ID, id);
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
