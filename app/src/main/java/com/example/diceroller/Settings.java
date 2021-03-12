package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        int numberOfDice = intent.getIntExtra("numberOfDice", 1);
        int sides = intent.getIntExtra("sides", 6);

        NumberPicker numberOfDicePicker = findViewById(R.id.numberOfDicePicker);
        numberOfDicePicker.setMinValue(1);
        numberOfDicePicker.setMaxValue(10);
        numberOfDicePicker.setValue(numberOfDice);

        Spinner sidesSpinner = findViewById(R.id.sidesSpinner);
        Integer[] diceSides = Dice.dice;
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, diceSides);
        sidesSpinner.setAdapter(adapter);

        int spinnerPos = 1;
        for (int i = 0; i < diceSides.length; i++){
            if (sides == diceSides[i]){
                spinnerPos = i;
            }
        }
        sidesSpinner.setSelection(spinnerPos);
    }

    public void backClicked(View view) {


    }
}