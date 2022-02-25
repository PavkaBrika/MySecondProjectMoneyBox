package com.example.mysecondprojectmoneybox;

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

public class ChangeCharacterActivity extends AppCompatActivity {

    private SharedPreferences AppSettings;

    RadioButton Griff;
    RadioButton Krabs;
    RadioButton Mcduck;

    int character;

    public static final String SETTINGS_MEMORY = "settings memory";
    public static final String SETTINGS_MEMORY_CHARACTER = "character";
    public static final String SETTINGS_MEMORY_VIBRATION = "vibration";
    public static final String SETTINGS_MEMORY_AUDIO = "audio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_character);

        Griff = (RadioButton) findViewById(R.id.griffButton);
        Krabs = (RadioButton) findViewById(R.id.mrkrabsButton);
        Mcduck = (RadioButton) findViewById(R.id.mcduckButton);
        Button OKbtn = (Button) findViewById(R.id.buttonOk);
        Button Cancelbtn = (Button) findViewById(R.id.buttonCancel);
        CheckBox AudioCheckBox = (CheckBox) findViewById(R.id.checkBoxEnableSound);
        CheckBox VibroCheckBox = (CheckBox) findViewById(R.id.checkBoxEnableVibration);

        AppSettings = getSharedPreferences(SETTINGS_MEMORY, Context.MODE_PRIVATE);


        OKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkChar();

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
        if (AppSettings.contains(SETTINGS_MEMORY_CHARACTER)) {
            character = AppSettings.getInt(SETTINGS_MEMORY_CHARACTER, 0);

            if (character == 1) {
                Griff.setChecked(true);
            }
            else if (character == 2) {
                Krabs.setChecked(true);
            }
            else if (character == 3) {
                Mcduck.setChecked(true);
            }
        }
    }

    public void checkChar() {
        if (Griff.isChecked()) {
            changeSettings(1);
            saveIntInMemory(SETTINGS_MEMORY_CHARACTER, 0);
        }
        else if (Krabs.isChecked()) {
            changeSettings(2);
            saveIntInMemory(SETTINGS_MEMORY_CHARACTER, 0);
        }
        else if (Mcduck.isChecked()) {
            changeSettings(3);
            saveIntInMemory(SETTINGS_MEMORY_CHARACTER, 0);
        }
        else if ((!Griff.isChecked()) && (!Krabs.isChecked()) && (!Mcduck.isChecked())) {
            Toast.makeText(getApplicationContext(), R.string.toastNoCharacterChangeCharacterActivity, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveIntInMemory(String preferences, int variable) {
        SharedPreferences.Editor editor = AppSettings.edit();
        editor.putInt(preferences, variable);
        editor.apply();
    }


    public void changeSettings(int charact) {
        Intent dataCharacter = new Intent();
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_CHARACTER, charact);
        setResult(RESULT_OK, dataCharacter);
        finish();
    }
}
