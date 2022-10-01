package com.breackneck.mysecondprojectmoneybox.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.breackneck.mysecondprojectmoneybox.R;

public class ChangeCharacterActivity extends AppCompatActivity {

    private SharedPreferences AppSettings;

    RadioButton Griff;
    RadioButton Krabs;
    RadioButton Mcduck;
    RadioButton Homer;
    RadioButton Griffin;
    CheckBox AudioCheckBox;
    CheckBox VibroCheckBox;

    int character;
    boolean audio;
    boolean vibro;

    public static final String SETTINGS_MEMORY = "settings memory";
    public static final String SETTINGS_MEMORY_CHARACTER = "character";
    public static final String SETTINGS_MEMORY_VIBRATION = "vibration";
    public static final String SETTINGS_MEMORY_AUDIO = "audio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_character);

        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        Griff = (RadioButton) findViewById(R.id.griffButton);
        Krabs = (RadioButton) findViewById(R.id.mrkrabsButton);
        Mcduck = (RadioButton) findViewById(R.id.mcduckButton);
        Homer = findViewById(R.id.homerButton);
        Griffin = findViewById(R.id.griffinButton);
        Button OKbtn = (Button) findViewById(R.id.buttonOk);
        Button Cancelbtn = (Button) findViewById(R.id.buttonCancel);
        AudioCheckBox = (CheckBox) findViewById(R.id.checkBoxEnableSound);
        VibroCheckBox = (CheckBox) findViewById(R.id.checkBoxEnableVibration);

        AppSettings = getSharedPreferences(SETTINGS_MEMORY, Context.MODE_PRIVATE);


        OKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCheckBoxes();
                changeSettings(character, audio, vibro);
            }
        });

        Cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        if ((AppSettings.contains(SETTINGS_MEMORY_CHARACTER)) && (AppSettings.contains(SETTINGS_MEMORY_AUDIO)) && (AppSettings.contains(SETTINGS_MEMORY_VIBRATION))) {
            character = AppSettings.getInt(SETTINGS_MEMORY_CHARACTER, 0);
            audio = AppSettings.getBoolean(SETTINGS_MEMORY_AUDIO, true);
            vibro = AppSettings.getBoolean(SETTINGS_MEMORY_VIBRATION, true);

            if (character == 1) {
                Griff.setChecked(true);
            }
            else if (character == 2) {
                Krabs.setChecked(true);
            }
            else if (character == 3) {
                Mcduck.setChecked(true);
            }
            else if (character == 4) {
                Homer.setChecked(true);
            }
            if (audio == true)
                AudioCheckBox.setChecked(true);
            else
                AudioCheckBox.setChecked(false);
            if (vibro == true)
                VibroCheckBox.setChecked(true);
            else
                VibroCheckBox.setChecked(false);
        }
    }

    public void checkCheckBoxes() {
        if (Griff.isChecked()) {
            character = 1;
            saveIntInMemory(SETTINGS_MEMORY_CHARACTER, character);
        }
        else if (Krabs.isChecked()) {
            character = 2;
            saveIntInMemory(SETTINGS_MEMORY_CHARACTER, character);
        }
        else if (Mcduck.isChecked()) {
            character = 3;
            saveIntInMemory(SETTINGS_MEMORY_CHARACTER, character);
        }
        else if (Homer.isChecked()) {
            character = 4;
            saveIntInMemory(SETTINGS_MEMORY_CHARACTER, character);
        }
        else if (Griffin.isChecked()) {
            character = 5;
            saveIntInMemory(SETTINGS_MEMORY_CHARACTER, character);
        }
        else if ((!Griff.isChecked()) && (!Krabs.isChecked()) && (!Mcduck.isChecked()) && (!Homer.isChecked()) && (!Griffin.isChecked())) {
            Toast.makeText(getApplicationContext(), R.string.toastNoCharacterChangeCharacterActivity, Toast.LENGTH_SHORT).show();
        }
        if (AudioCheckBox.isChecked()) {
            audio = true;
            saveBoolInMemory(SETTINGS_MEMORY_AUDIO, audio);
        }
        else{
            audio = false;
            saveBoolInMemory(SETTINGS_MEMORY_AUDIO, audio);
        }
        if (VibroCheckBox.isChecked()) {
            vibro = true;
            saveBoolInMemory(SETTINGS_MEMORY_VIBRATION, vibro);
        }
        else {
            vibro = false;
            saveBoolInMemory(SETTINGS_MEMORY_VIBRATION, vibro);
        }
    }

    private void saveIntInMemory(String preferences, int variable) {
        SharedPreferences.Editor editor = AppSettings.edit();
        editor.putInt(preferences, variable);
        editor.apply();
    }

    private void saveBoolInMemory(String preferences, boolean variable) {
        SharedPreferences.Editor editor = AppSettings.edit();
        editor.putBoolean(preferences, variable);
        editor.apply();
    }


    public void changeSettings(int charact, boolean audio, boolean vibro) {
        Intent dataCharacter = new Intent();
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_CHARACTER, charact);
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_AUDIO, audio);
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_VIBRO, vibro);
        setResult(RESULT_OK, dataCharacter);
        finish();
    }
}
