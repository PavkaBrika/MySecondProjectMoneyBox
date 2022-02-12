package com.example.mysecondprojectmoneybox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeCharacterActivity extends AppCompatActivity {

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
                else if (Mcduck.isChecked()) {
                    changeChar(3);
                }
                else if ((!Griff.isChecked()) && (!Krabs.isChecked()) && (!Mcduck.isChecked())) {
                    Toast.makeText(getApplicationContext(), R.string.toastNoCharacterChangeCharacterActivity, Toast.LENGTH_SHORT).show();
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

    public void changeChar(int charact) {
        Intent dataCharacter = new Intent();
        dataCharacter.putExtra(MainActivity.APP_PREFERENCES_CHARACTER, charact);
        setResult(RESULT_OK, dataCharacter);
        finish();
    }
}
