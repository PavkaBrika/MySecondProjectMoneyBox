package com.breackneck.mysecondprojectmoneybox;

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
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
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

    private SharedPreferences AppSettings; //SharedPreferences for access to memory
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
    boolean audio; //enable audio
    boolean vibro; //enable vibration
    float costpercent; //variable for coins in jar

    TextView moneyQuantity; //textView for money in pocket
    TextView itemDesire; //textView for name of target
    TextView addItemCost; //textView for target cost
    TextView leftToSaving; //textView for left to save
    TextView jarHint; //textView for hint on jar
    TextView hintMainActivity; //textView for hint on mainactivity
    ImageView AddSubButton; //imageView for adding/subtracting button
    ImageView thoughtsView; //imageView for image where all information is displayed
    ImageView coinsFirstView;
    ImageView coinsSecondView;
    ImageView coinsThirdView;
    ImageView coinsForthView;
    ImageView coinsFifthView;
    ImageView coinsSixthView;
    ImageView coinsSeventView;
    ImageView coinsEighthView;
    ImageView coinsNinthView;
    TextView textFirst;
    TextView textSecond;

    public static final String APP_PREFERENCES = "settings"; //variable for whole SharedPreferences
    public static final String APP_PREFERENCES_ITEM = "item"; //variable for SharedPreferences item
    public static final String APP_PREFERENCES_MONEY = "money"; //variable for SharedPreferences money
    public static final String APP_PREFERENCES_COST = "cost"; //variable for SharedPreferences cost
    public static final String ACTIVITY_FOR_RESULT_ADD_MONEY = "addmoney"; //variable for ActivityForResult add_money
    public static final String APP_PREFERENCES_CHARACTER = "character"; //variable for ActivityForResult character
    public static final String APP_PREFERENCES_ADSMONEYADDCLICK = "adsmoneyaddclick"; //variable for Ad
    public static final String APP_PREFERENCES_ADSCHARACTERCLICK = "adscharacterclick";//variable for Ad
    public static final String APP_PREFERENCES_ADSCHANGECHARCLICK = "adschangecharclick"; //variable for Ad
    public static final String APP_PREFERENCES_ADSRESETCLICK = "adsresetclick"; //variable for Ad
    public static final String APP_PREFERENCES_AUDIO = "audio"; //variable for enable audio
    public static final String APP_PREFERENCES_VIBRO = "vibro"; //variable for enable vibration

    DecimalFormat decimalFormat = new DecimalFormat( "#.##" ); //pattern for numbers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = "";

        MediaPlayer player; //initialize player for coin sound
        player = MediaPlayer.create(this, R.raw.coinssound); //added coin sound to MediaPlayer

        AppSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE); //set name to App Preferences

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
        addItemCost.setText("");
        //on reset button click action
        ImageView resButton = (ImageView) findViewById(R.id.buttonReset);
        resButton.setOnClickListener(new View.OnClickListener() { // on Reset button click listener
            @Override
            public void onClick(View view) { //on reset button click
                AdsResetClick += 1; //add 1 to Ad variable
                if ((AdsResetClick == 2) && (mInterstitialAd != null)) { // if reset button was clicked 2 times and ad is loaded
                    mInterstitialAd.show(MainActivity.this); //show the ads
                    AdsResetClick = 0; //and ad variable set 0
                }
                saveIntInMemory(APP_PREFERENCES_ADSRESETCLICK, AdsResetClick); //save ad variable in memory
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.AlertDialogStyle)); //create alertdialog
                builder.setTitle(R.string.alertDialogTitle); //set title
                builder.setMessage(R.string.alertDialogMessage); //set message
                builder.setPositiveButton(R.string.alertDialogPositiveButton, new DialogInterface.OnClickListener() { //set title to positive button and listener
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { //on yes button on click
                        money = 0; //set variables to 0
                        cost = 0;
                        item = "";
                        calcLeftSum(); //calculating other variables

                        saveFloatInMemory(APP_PREFERENCES_MONEY, money); //save variables in memory
                        saveStringInMemory(APP_PREFERENCES_ITEM, item);
                        saveFloatInMemory(APP_PREFERENCES_COST, cost);

                        moneyQuantity.setText(decimalFormat.format(money));
                        leftToSaving.setTextSize(30);
                        leftToSaving.setPadding(0,0,0,0);
                        itemDesire.setText(item);
                        addItemCost.setText("");
                        moneyQuantity.setVisibility(View.INVISIBLE);
                        setVisibilityView(View.INVISIBLE);
                        jarHint.setVisibility(View.VISIBLE);
                        AddSubButton.setVisibility(View.INVISIBLE);
                        thoughtsView.setVisibility(View.INVISIBLE);
                        hintMainActivity.setVisibility(View.INVISIBLE);
                        coinsFirstView.setVisibility(View.INVISIBLE);
                        coinsSecondView.setVisibility(View.INVISIBLE);
                        coinsThirdView.setVisibility(View.INVISIBLE);
                        coinsForthView.setVisibility(View.INVISIBLE);
                        coinsFifthView.setVisibility(View.INVISIBLE);
                        coinsSixthView.setVisibility(View.INVISIBLE);
                        coinsSeventView.setVisibility(View.INVISIBLE);
                        coinsEighthView.setVisibility(View.INVISIBLE);
                        coinsNinthView.setVisibility(View.INVISIBLE);
                        startVibration(VibrationEffect.DEFAULT_AMPLITUDE, vibro); //start vibration in default effect if vibro is true

                        dialogInterface.cancel(); //close alert dialog
                    }
                });
                builder.setNegativeButton(R.string.alertDialogNegativeButton, null); //set title No to negative button and null action
                builder.show(); //show alert dialog
            }
        });
        //start AddNewGoalActivity for result
        ActivityResultLauncher<Intent> startForResultCreateTarget = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){ //if result is OK
                    Intent intent = result.getData(); //set name to getData()
                    item = intent.getStringExtra(APP_PREFERENCES_ITEM); //get item variable
                    cost = intent.getFloatExtra(APP_PREFERENCES_COST, 0); //get cost variable
                    saveStringInMemory(APP_PREFERENCES_ITEM, item); //save this variables in memory
                    saveFloatInMemory(APP_PREFERENCES_COST, cost);
                    itemDesire.setText(item); //and set text in Views
                    addItemCost.setText(Float.toString(cost));
                }
                else{ //if result is not OK
                    itemDesire.setText("Error"); //set Error text in Views
                    addItemCost.setText("Error");
                }
            }
        });
        // start AddMoneyActivity for result
        ActivityResultLauncher<Intent> startForResultAddMoney = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                float addmoney = 0; //initialize variable for money add and set 0
                if (result.getResultCode() == RESULT_OK) { // if result ok
                    Intent intent = result.getData(); //set name to getData
                    addmoney = intent.getFloatExtra(ACTIVITY_FOR_RESULT_ADD_MONEY, 0); //get variable
                    money += addmoney; //add addmoney variable to money
                    if (money < 0) // fix bug if money < 0 set money 0
                        money = 0;
                    saveFloatInMemory(APP_PREFERENCES_MONEY, money); //save money variable in memory
                    moneyQuantity.setText(decimalFormat.format(money)); //set text money
                    startVibration(VibrationEffect.EFFECT_HEAVY_CLICK, vibro); //if vibro variable is true start vibration with heavy click effect
                    if (audio == true) //if audio variable is true
                        player.start(); //start coin sound
                }
                else {
                    moneyQuantity.setText("Error"); //else if result is not ok set text error
                }
            }
        });
        //start ChangeCharacterActivity for result
        ActivityResultLauncher<Intent> startForResultChangeCharacter = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()==RESULT_OK) { //if result is OK
                    Intent intent = result.getData(); //set name to getData
                    character = intent.getIntExtra(APP_PREFERENCES_CHARACTER, 0);//get variables
                    audio = intent.getBooleanExtra(APP_PREFERENCES_AUDIO, true);
                    vibro = intent.getBooleanExtra(APP_PREFERENCES_VIBRO, true);
                    saveIntInMemory(APP_PREFERENCES_CHARACTER, character); //save variables in memory
                    saveBoolInMemory(APP_PREFERENCES_AUDIO, audio);
                    saveBoolInMemory(APP_PREFERENCES_VIBRO, vibro);
                }
            }
        });
        //action on adding/substracting button click
        AddSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdsMoneyAddClick += 1; //add 1 to Ad variable
                saveIntInMemory(APP_PREFERENCES_ADSMONEYADDCLICK, AdsMoneyAddClick); //save ad variable in memory
                Intent intent = new Intent(MainActivity.this, AddMoneyActivity.class); //start AddMoneyActivity
                startForResultAddMoney.launch(intent); //start activity
            }
        });

        ImageView settingsButton = (ImageView) findViewById(R.id.buttonSettings); //initialize settingsButton
        settingsButton.setOnClickListener(new View.OnClickListener() { //set on settingsButton click listener
            @Override
            public void onClick(View view) {
                AdsChangeCharClick += 1; //add 1 to ad variable
                saveIntInMemory(APP_PREFERENCES_ADSCHANGECHARCLICK, AdsChangeCharClick); //save ad variable in memory
                Intent intent = new Intent(MainActivity.this, ChangeCharacterActivity.class); //start ChangeCharacter activity
                startForResultChangeCharacter.launch(intent); //start activity
            }
        });

        ImageView moneyJar = (ImageView) findViewById(R.id.imageView); //initialize moneyJar imageview
        moneyJar.setOnClickListener(new View.OnClickListener() { //set on moneyjar imageview click listener
            @Override
            public void onClick(View view) {
                if ((item.equals("")) && (cost == 0)) { //if goal is not setted
                    Intent intent = new Intent(MainActivity.this, AddNewGoalActivity.class); //start AddNewGoalActivity
                    startForResultCreateTarget.launch(intent); //launch activity
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //else show AlertDialog
                    builder.setTitle(R.string.alertDialogTitleNewGoal); //which says that goal is already setted
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
                AdsCharacterClick += 1;
                if ((AdsCharacterClick == 10) && (mInterstitialAd != null)) {
                    mInterstitialAd.show(MainActivity.this);
                    AdsCharacterClick = 0;
                }
                saveIntInMemory(APP_PREFERENCES_ADSCHARACTERCLICK, AdsCharacterClick);
                startVibration(VibrationEffect.EFFECT_TICK, vibro);
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

    protected void onResume() {
        super.onResume();

        if ((AppSettings.contains(APP_PREFERENCES_MONEY)) && (AppSettings.contains(APP_PREFERENCES_ITEM)) && (AppSettings.contains(APP_PREFERENCES_COST)) && (AppSettings.contains(APP_PREFERENCES_CHARACTER))) {
            AdsMoneyAddClick = AppSettings.getInt(APP_PREFERENCES_ADSMONEYADDCLICK, 0);
            if ((!AppSettings.contains(APP_PREFERENCES_ADSMONEYADDCLICK)) || (AdsMoneyAddClick > 3)) {
                AdsMoneyAddClick = 0;
                saveIntInMemory(APP_PREFERENCES_ADSMONEYADDCLICK, AdsMoneyAddClick);
            }
            AdsCharacterClick = AppSettings.getInt(APP_PREFERENCES_ADSCHARACTERCLICK, 0);
            if ((!AppSettings.contains(APP_PREFERENCES_ADSCHARACTERCLICK)) || (AdsCharacterClick > 10)) {
                AdsCharacterClick = 0;
                saveIntInMemory(APP_PREFERENCES_ADSMONEYADDCLICK, AdsCharacterClick);
            }
            AdsChangeCharClick = AppSettings.getInt(APP_PREFERENCES_ADSCHANGECHARCLICK, 0);
            if ((!AppSettings.contains(APP_PREFERENCES_ADSCHANGECHARCLICK)) || (AdsChangeCharClick > 2)) {
                AdsChangeCharClick = 0;
                saveIntInMemory(APP_PREFERENCES_ADSCHANGECHARCLICK, AdsChangeCharClick);
            }
            if ((!AppSettings.contains(APP_PREFERENCES_ADSRESETCLICK)) || (AdsResetClick > 2)) {
                AdsResetClick = 0;
                saveIntInMemory(APP_PREFERENCES_ADSRESETCLICK, AdsResetClick);
            }

            if ((AdsChangeCharClick == 2) && (mInterstitialAd != null)) {
                mInterstitialAd.show(MainActivity.this);
                AdsChangeCharClick = 0;
            }

            if ((AdsMoneyAddClick == 3) && (mInterstitialAd != null)) {
                mInterstitialAd.show(MainActivity.this);
                AdsMoneyAddClick = 0;
            }

            vibro = AppSettings.getBoolean(APP_PREFERENCES_VIBRO, true);
            audio = AppSettings.getBoolean(APP_PREFERENCES_AUDIO, true);

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
            if (character == 1) {
                changeCharacter(R.drawable.griff, 216, 203);
            }
            else if (character == 2) {
                changeCharacter(R.drawable.krabs, 250, 250);
            }
            else if (character == 3) {
                changeCharacter(R.drawable.mcduck, 216, 203);
            }
            calcLeftSum();
            //Adding coins in jar
            coinsFirstView = (ImageView) findViewById(R.id.coins1);
            coinsSecondView = (ImageView) findViewById(R.id.coins2);
            coinsThirdView = (ImageView) findViewById(R.id.coins3);
            coinsForthView = (ImageView) findViewById(R.id.coins4);
            coinsFifthView = (ImageView) findViewById(R.id.coins5);
            coinsSixthView = (ImageView) findViewById(R.id.coins6);
            coinsSeventView = (ImageView) findViewById(R.id.coins7);
            coinsEighthView = (ImageView) findViewById(R.id.coins8);
            coinsNinthView = (ImageView) findViewById(R.id.coins9);

            costpercent = (money / cost) * 100;
            addAndSubCoinsInJar(10, coinsFirstView);
            addAndSubCoinsInJar(20, coinsSecondView);
            addAndSubCoinsInJar(30, coinsThirdView);
            addAndSubCoinsInJar(40, coinsForthView);
            addAndSubCoinsInJar(50, coinsFifthView);
            addAndSubCoinsInJar(60, coinsSixthView);
            addAndSubCoinsInJar(70, coinsSeventView);
            addAndSubCoinsInJar(80, coinsEighthView);
            addAndSubCoinsInJar(90, coinsNinthView);

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
        //Ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                mAdView = (AdView) findViewById(R.id.adView); //banner ad view
                adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest); //load banner
                if (mInterstitialAd == null) {
                    InterstitialAd.load(MainActivity.this,"ca-app-pub-3967661567296020/5029111613", adRequest,
                            new InterstitialAdLoadCallback() {
                                @Override
                                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                    mInterstitialAd = interstitialAd;
                                    Log.i("TAG", "onAdLoaded");
                                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                                        @Override
                                        public void onAdDismissedFullScreenContent() {
                                            // Called when fullscreen content is dismissed.
                                            Log.i("TAG", "The ad was dismissed.");
                                        }

                                        @Override
                                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                                            // Called when fullscreen content failed to show.
                                            Log.i("TAG", "The ad failed to show.");
                                        }

                                        @Override
                                        public void onAdShowedFullScreenContent() {
                                            // Called when fullscreen content is shown.
                                            // Make sure to set your reference to null so you don't
                                            // show it a second time.
                                            mInterstitialAd = null;
                                            Log.i("TAG", "The ad was shown.");
                                        }
                                    });
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                    mInterstitialAd = null;
                                    Log.i("TAG", loadAdError.getMessage());
                                }
                            });
                    }
                }
            });
    }

    protected void onPause() {
        super.onPause();

        item = itemDesire.getText().toString();

        if (addItemCost.getText().toString().equals(""))
            cost = 0;

        saveFloatInMemory(APP_PREFERENCES_MONEY, money);
        saveStringInMemory(APP_PREFERENCES_ITEM, item);
        saveFloatInMemory(APP_PREFERENCES_COST, cost);
        saveIntInMemory(APP_PREFERENCES_CHARACTER, character);
        saveIntInMemory(APP_PREFERENCES_ADSMONEYADDCLICK, AdsMoneyAddClick);
        saveIntInMemory(APP_PREFERENCES_ADSCHANGECHARCLICK, AdsChangeCharClick);
        saveIntInMemory(APP_PREFERENCES_ADSCHARACTERCLICK, AdsCharacterClick);
        saveIntInMemory(APP_PREFERENCES_ADSRESETCLICK, AdsResetClick);
    }

    private void calcLeftSum() {
        left = cost - money;
        if (left > 0) {
            leftToSaving.setText(decimalFormat.format(left) + " " + getString(R.string.left));
            jarHint.setVisibility(View.INVISIBLE);
            leftToSaving.setTextSize(30);
        }
        else if (left <= 0) {
            leftToSaving.setText(R.string.congratulations);
            leftToSaving.setTextSize(25);
            leftToSaving.setPadding(170,0,170,0);
            AddSubButton.setVisibility(View.INVISIBLE);
            jarHint.setVisibility(View.INVISIBLE);
        }
    }

    private void setVisibilityView(int visibility) {
        leftToSaving.setVisibility(visibility);
        textFirst.setVisibility(visibility);
        textSecond.setVisibility(visibility);
    }

    private void startVibration(int vibrationEffect, boolean enable) {
        if (enable == true) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            VibrationEffect effect = VibrationEffect.createOneShot(150, vibrationEffect);
            vibrator.vibrate(effect);
        }
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

    private void saveIntInMemory(String preferences, int variable) {
        SharedPreferences.Editor editor = AppSettings.edit();
        editor.putInt(preferences, variable);
        editor.apply();
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

    private void saveBoolInMemory(String preferences, boolean variable) {
        SharedPreferences.Editor editor = AppSettings.edit();
        editor.putBoolean(preferences, variable);
        editor.apply();
    }

    private void addAndSubCoinsInJar(int percent, ImageView coinImage) {
        if (costpercent >= percent)
            coinImage.setVisibility(View.VISIBLE);
        else
            coinImage.setVisibility(View.INVISIBLE);
    }
}