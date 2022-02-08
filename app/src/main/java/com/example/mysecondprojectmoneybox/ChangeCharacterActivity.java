package com.example.mysecondprojectmoneybox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeCharacterActivity extends AppCompatActivity {

    private SharedPreferences AppChar;
    public static final String APP_PREFERENCES_CHARACTER= "character";
    int character;

    RadioButton Griff;
    RadioButton Krabs;
    RadioButton Mcduck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_character);

        Griff = (RadioButton) findViewById(R.id.griffButton);
        Krabs = (RadioButton) findViewById(R.id.mrkrabsButton);
        Mcduck = (RadioButton) findViewById(R.id.mcduckButton);
        Button OKbtn = (Button) findViewById(R.id.buttonOk);
        Button Cancelbtn = (Button) findViewById(R.id.buttonCancel);


        OKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Griff.isChecked()) {
                    changeChar(1);
                }
                else if (Krabs.isChecked()) {
                    changeChar(2);
                }
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

        Griff = (RadioButton) findViewById(R.id.griffButton);
        Krabs = (RadioButton) findViewById(R.id.mrkrabsButton);
        Mcduck = (RadioButton) findViewById(R.id.mcduckButton);

        if (AppChar.contains(APP_PREFERENCES_CHARACTER)) {
            character = AppChar.getInt(APP_PREFERENCES_CHARACTER, 0);
        }

        if (character == 1) {
            Griff.setChecked(true);
        }
        else if (character == 2) {
            Krabs.setChecked(true);
        }

    }

    public void changeChar(int charact) {
        Intent dataCharacter = new Intent();
        character = charact;
        SharedPreferences.Editor editor = AppChar.edit();
        editor.putInt(APP_PREFERENCES_CHARACTER, character);
        editor.apply();
        dataCharacter.putExtra(MainActivity.ACTIVITY_FOR_RESULT_ADD_MONEY, character);
        setResult(RESULT_OK, dataCharacter);
        finish();
    }
}
