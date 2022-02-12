package com.example.mysecondprojectmoneybox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //SharedPreferences for access to memory
    private SharedPreferences AppSettings;

    // variables
    float money; //money in pocket
    float cost; //target cost
    String item; //name of target
    float left; //left to save
    int character; //number of character

    TextView moneyQuantity; //textView for money in pocket
    TextView itemDesire; //textView for name of target
    TextView addItemCost; //textView for target cost
    TextView leftToSaving; //textView for left to save
    TextView jarHint; //textView for hint on jar
    TextView hintMainActivity; //textView for hint on mainactivity
    ImageView AddSubButton; //imageView for adding/subtracting button
    ImageView thoughtsView; //imageView for image where all information is displayed
    TextView textFirst;
    TextView textSecond;

    public static final String APP_PREFERENCES = "settings"; //variable for whole SharedPreferences
    public static final String APP_PREFERENCES_ITEM = "item"; //variable for SharedPreferences item
    public static final String APP_PREFERENCES_MONEY = "money"; //variable for SharedPreferences money
    public static final String APP_PREFERENCES_COST = "cost"; //variable for SharedPreferences cost
    public static final String ACTIVITY_FOR_RESULT_ADD_MONEY = "addmoney"; //variable for ActivityForResult add_money
    public static final String APP_PREFERENCES_CHARACTER = "character"; //variable for ActivityForResult character

    DecimalFormat decimalFormat = new DecimalFormat( "#.##" ); //pattern for numbers


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MobileAds.initialize(this, "ca-app-pub-3967661567296020~5965954619");
//        interstitialAd = new InterstitialAd(this);

        AppSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        moneyQuantity = (TextView) findViewById(R.id.money);
        addItemCost = (TextView) findViewById(R.id.costEditText);
        leftToSaving = (TextView) findViewById(R.id.leftTextView);
        textFirst = (TextView) findViewById(R.id.textView);
        textSecond = (TextView) findViewById(R.id.textView2);
        jarHint = (TextView) findViewById(R.id.HintToAddNewGoalTextView);
        itemDesire = (TextView) findViewById(R.id.item);
        hintMainActivity = (TextView) findViewById(R.id.mainActivityHintTextView);
        AddSubButton = (ImageView) findViewById(R.id.buttonAddSubMoney);
        thoughtsView = (ImageView) findViewById(R.id.imageViewThoughts);

        moneyQuantity.setText(decimalFormat.format(money));
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
                        editor.apply();

                        moneyQuantity.setText(decimalFormat.format(money));
                        leftToSaving.setTextSize(30);
                        leftToSaving.setPadding(0,0,0,0);
                        itemDesire.setText(item);
                        addItemCost.setText("");
                        moneyQuantity.setVisibility(View.INVISIBLE);
                        setVisibilityView(View.INVISIBLE);
                        jarHint.setVisibility(View.VISIBLE);
                        AddSubButton.setVisibility(View.INVISIBLE); //ТУТ
                        thoughtsView.setVisibility(View.INVISIBLE);
                        hintMainActivity.setVisibility(View.INVISIBLE);
                        startVibration(VibrationEffect.DEFAULT_AMPLITUDE);

                        dialogInterface.cancel();
                    }
                });
                builder.setNegativeButton(R.string.alertDialogNegativeButton, null);
                builder.show();
            }
        });


        ActivityResultLauncher<Intent> startForResultCreateTarget = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
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
                    moneyQuantity.setText(decimalFormat.format(money));
                    startVibration(VibrationEffect.EFFECT_HEAVY_CLICK);
                }
                else {
                    moneyQuantity.setText("Error");
                }
            }
        });

        ActivityResultLauncher<Intent> startForResultChangeCharacter = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()==RESULT_OK) {
                    Intent intent = result.getData();
                    character = intent.getIntExtra(APP_PREFERENCES_CHARACTER, 0);
                }
            }
        });


        AddSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMoneyActivity.class);

                startForResultAddMoney.launch(intent);
            }
        });

        ImageView settingsButton = (ImageView) findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChangeCharacterActivity.class);

                startForResultChangeCharacter.launch(intent);
            }
        });

        ImageView moneyJar = (ImageView) findViewById(R.id.imageView);
        moneyJar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((item.equals("")) && (cost == 0)) {
                    Intent intent = new Intent(MainActivity.this, AddNewGoalActivity.class);
                    startForResultCreateTarget.launch(intent);
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


        ImageView characterView = (ImageView) findViewById(R.id.imageViewCharacter);
        characterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVibration(VibrationEffect.EFFECT_TICK);
                if (thoughtsView.getVisibility() == View.INVISIBLE) {
                    if (!(item.equals("")) && !(cost == 0)) {
                        thoughtsView.setVisibility(View.VISIBLE);
                        itemDesire.setVisibility(View.VISIBLE);
                        addItemCost.setVisibility(View.VISIBLE);  //ТУТ
                        hintMainActivity.setVisibility(View.INVISIBLE);
                        setVisibilityView(View.VISIBLE);
                        calcLeftSum();
                        new CountDownTimer(6000, 1000) {

                            @Override
                            public void onTick(long l) {
                            }

                            @Override
                            public void onFinish() {
                                thoughtsView.setVisibility(View.INVISIBLE);
                                itemDesire.setVisibility(View.INVISIBLE);
                                addItemCost.setVisibility(View.INVISIBLE);  //ТУТ
                                hintMainActivity.setVisibility(View.VISIBLE);
                                setVisibilityView(View.INVISIBLE);
                            }
                        }.start();
                    }
                    else Toast.makeText(getApplicationContext(), R.string.toastOnCharacterClick, Toast.LENGTH_SHORT).show();
                }
                else {
                    hintMainActivity.setVisibility(View.VISIBLE);
                    thoughtsView.setVisibility(View.INVISIBLE);
                    setVisibilityView(View.INVISIBLE); //ТУТ
                    itemDesire.setVisibility(View.INVISIBLE);
                    addItemCost.setVisibility(View.INVISIBLE);
                }
            }
        });


    }



    protected void onPause() {
        super.onPause();

        item = itemDesire.getText().toString();

        if (addItemCost.getText().toString().equals(""))
            cost = 0;
        else
            cost = Float.parseFloat(addItemCost.getText().toString());

        SharedPreferences.Editor editor = AppSettings.edit();
        editor.putFloat(APP_PREFERENCES_MONEY, money);
        editor.putString(APP_PREFERENCES_ITEM, item);
        editor.putFloat(APP_PREFERENCES_COST, cost);
        editor.putInt(APP_PREFERENCES_CHARACTER, character);
        editor.apply();
    }

    protected void onResume() {
        super.onResume();

        if ((AppSettings.contains(APP_PREFERENCES_MONEY)) && (AppSettings.contains(APP_PREFERENCES_ITEM)) && (AppSettings.contains(APP_PREFERENCES_COST)) && (AppSettings.contains(APP_PREFERENCES_CHARACTER))) {
            money = AppSettings.getFloat(APP_PREFERENCES_MONEY, 0);
            moneyQuantity = (TextView) findViewById(R.id.money);
            moneyQuantity.setText(decimalFormat.format(money));

            item = AppSettings.getString(APP_PREFERENCES_ITEM, "");

            itemDesire.setText(item);

            cost = AppSettings.getFloat(APP_PREFERENCES_COST, 0);
            if (cost != 0) {
                addItemCost.setText(decimalFormat.format(cost));
            }
            else addItemCost.setText("");

            ImageView characterView = (ImageView) findViewById(R.id.imageViewCharacter);
            ImageView moneyJar = (ImageView) findViewById(R.id.imageView);
            if (character == 1) {
                characterView.setImageResource(R.drawable.griff);
                moneyJar.setImageResource(R.drawable.moneyjar);
            }
            else if (character == 2) {
                characterView.setImageResource(R.drawable.krabs);
                moneyJar.setImageResource(R.drawable.moneyjarkrabs);

            }
            calcLeftSum();

            if ((money != 0) || (cost != 0) || (!item.equals(""))) {
                setVisibilityView(View.VISIBLE);
                AddSubButton.setVisibility(View.VISIBLE);
                moneyQuantity.setVisibility(View.VISIBLE);
                addItemCost.setVisibility(View.VISIBLE);
                thoughtsView.setVisibility(View.VISIBLE);
                itemDesire.setVisibility(View.VISIBLE);
                hintMainActivity.setVisibility(View.INVISIBLE);
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        setVisibilityView(View.INVISIBLE);
                        addItemCost.setVisibility(View.INVISIBLE);
                        thoughtsView.setVisibility(View.INVISIBLE);
                        itemDesire.setVisibility(View.INVISIBLE);
                        hintMainActivity.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
            else {
                jarHint.setVisibility(View.VISIBLE);
                hintMainActivity.setVisibility(View.INVISIBLE);
                moneyQuantity.setVisibility(View.INVISIBLE);
                setVisibilityView(View.INVISIBLE);
                jarHint.setVisibility(View.VISIBLE);
                AddSubButton.setVisibility(View.INVISIBLE); //ТУТ
                thoughtsView.setVisibility(View.INVISIBLE);
            }
        }

    }


    private void calcLeftSum() {
        left = cost - money;
        if (left > 0) {
            leftToSaving.setText(decimalFormat.format(left) + " Left");
            jarHint.setVisibility(View.INVISIBLE);
            leftToSaving.setTextSize(30);
        }
        else if (left <= 0) {
            leftToSaving.setText(R.string.congratulations);
            leftToSaving.setTextSize(25);
            leftToSaving.setPadding(80,0,80,0);
            jarHint.setVisibility(View.INVISIBLE);
        }
    }

    private void setVisibilityView(int visibility) {
        leftToSaving.setVisibility(visibility);
        textFirst.setVisibility(visibility);
        textSecond.setVisibility(visibility);
    }

    private void startVibration(int vibrationEffect) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        VibrationEffect effect = VibrationEffect.createOneShot(150, vibrationEffect);
        vibrator.vibrate(effect);
    };


}