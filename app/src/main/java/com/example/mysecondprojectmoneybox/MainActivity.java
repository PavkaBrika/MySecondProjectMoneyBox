package com.example.mysecondprojectmoneybox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //SharedPreferences for access to memory
    private SharedPreferences AppSettings;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;

    int AdsMoneyAddClick;
    int AdsCharacterClick;
    int AdsChangeCharClick;
    int AdsResetClick;

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
    public static final String APP_PREFERENCES_ADSMONEYADDCLICK = "adsmoneyaddclick";


    DecimalFormat decimalFormat = new DecimalFormat( "#.##" ); //pattern for numbers


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = "";

        MediaPlayer player;
        player = MediaPlayer.create(this, R.raw.coinssound);

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
                    if (money < 0)
                        money = 0;
                    moneyQuantity.setText(decimalFormat.format(money));
                    startVibration(VibrationEffect.EFFECT_HEAVY_CLICK);
                    player.start();
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
                    SharedPreferences.Editor editor = AppSettings.edit();
                    editor.putInt(APP_PREFERENCES_CHARACTER, character);
                    editor.apply();
                }
            }
        });

        AddSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdsMoneyAddClick += 1;
                SharedPreferences.Editor editor = AppSettings.edit();
                editor.putFloat(APP_PREFERENCES_MONEY, money);
                editor.putInt(APP_PREFERENCES_ADSMONEYADDCLICK, AdsMoneyAddClick);
                editor.apply();
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
                        startAnim(R.anim.startthoughtsanim);
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
                                if ((thoughtsView.getVisibility() == View.VISIBLE) && (itemDesire.getVisibility() == View.VISIBLE) && (addItemCost.getVisibility() == View.VISIBLE) && (hintMainActivity.getVisibility() == View.INVISIBLE)) {
                                    startAnim(R.anim.finishthoughtsanim);
                                    thoughtsView.setVisibility(View.INVISIBLE);
                                    itemDesire.setVisibility(View.INVISIBLE);
                                    addItemCost.setVisibility(View.INVISIBLE);  //ТУТ
                                    hintMainActivity.setVisibility(View.VISIBLE);
                                    setVisibilityView(View.INVISIBLE);
                                }
                            }
                        }.start();
                    }
                    else Toast.makeText(getApplicationContext(), R.string.toastOnCharacterClick, Toast.LENGTH_SHORT).show();
                }
                else {
                    startAnim(R.anim.finishthoughtsanim);
                    hintMainActivity.setVisibility(View.VISIBLE);
                    thoughtsView.setVisibility(View.INVISIBLE);
                    setVisibilityView(View.INVISIBLE); //ТУТ
                    itemDesire.setVisibility(View.INVISIBLE);
                    addItemCost.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    protected void onStart() {
        super.onStart();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }

    protected void onResume() {
        super.onResume();

        if ((AppSettings.contains(APP_PREFERENCES_MONEY)) && (AppSettings.contains(APP_PREFERENCES_ITEM)) && (AppSettings.contains(APP_PREFERENCES_COST)) && (AppSettings.contains(APP_PREFERENCES_CHARACTER))) {
            if (!AppSettings.contains(APP_PREFERENCES_ADSMONEYADDCLICK)) {
                AdsMoneyAddClick = 0;
                SharedPreferences.Editor editor = AppSettings.edit();
                editor.putInt(APP_PREFERENCES_ADSMONEYADDCLICK, AdsMoneyAddClick);
                editor.apply();
            }

            AdsMoneyAddClick = AppSettings.getInt(APP_PREFERENCES_ADSMONEYADDCLICK, 0);
            if ((AdsMoneyAddClick == 3) && (mInterstitialAd != null)) {
                mInterstitialAd.show(MainActivity.this);
                AdsMoneyAddClick = 0;
            }

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

            character = AppSettings.getInt(APP_PREFERENCES_CHARACTER, 0);
//            ImageView moneyJar = (ImageView) findViewById(R.id.imageView);
            if (character == 1) {
                changeCharacter(R.drawable.griff, 216, 203);
            }
            else if (character == 2) {
                changeCharacter(R.drawable.krabs, 250, 250);
            }
            calcLeftSum();

            if ((cost != 0) && (!item.equals(""))) {
                startAnim(R.anim.startthoughtsanim);

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
                        if ((thoughtsView.getVisibility() == View.VISIBLE) && (itemDesire.getVisibility() == View.VISIBLE) && (addItemCost.getVisibility() == View.VISIBLE) && (hintMainActivity.getVisibility() == View.INVISIBLE)) {
                            startAnim(R.anim.finishthoughtsanim);
                            setVisibilityView(View.INVISIBLE);
                            addItemCost.setVisibility(View.INVISIBLE);
                            thoughtsView.setVisibility(View.INVISIBLE);
                            itemDesire.setVisibility(View.INVISIBLE);
                            hintMainActivity.setVisibility(View.VISIBLE);
                        }
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
        editor.putInt(APP_PREFERENCES_ADSMONEYADDCLICK, AdsMoneyAddClick);
        editor.apply();
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

    private void startAnim(int animation) {
        Animation anim = AnimationUtils.loadAnimation(this, animation);
        addItemCost.startAnimation(anim);
        thoughtsView.startAnimation(anim);
        itemDesire.startAnimation(anim);
        leftToSaving.startAnimation(anim);
        textFirst.startAnimation(anim);
        textSecond.startAnimation(anim);
    }

    private void changeCharacter(int character, int height, int width) {
        ImageView characterView = (ImageView) findViewById(R.id.imageViewCharacter);
        characterView.setImageResource(character);
        int heightImageView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        int widthImageView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        characterView.getLayoutParams().height = heightImageView;
        characterView.getLayoutParams().width = widthImageView;
    }
}