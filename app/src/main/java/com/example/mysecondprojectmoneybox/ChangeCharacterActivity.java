package com.example.mysecondprojectmoneybox;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeCharacterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_character);

        RadioButton Griff = (RadioButton) findViewById(R.id.griffButton);
        RadioButton Krabs = (RadioButton) findViewById(R.id.mrkrabsButton);
        RadioButton Mcduck = (RadioButton) findViewById(R.id.mcduckButton);
        Button OKbtn = (Button) findViewById(R.id.buttonOk);
        Button Cancelbtn = (Button) findViewById(R.id.buttonCancel);

        OKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
