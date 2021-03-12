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

        NumberPicker numberOfDicePicker = (NumberPicker) findViewById(R.id.numberOfDicePicker);
        numberOfDicePicker.setMinValue(1);
        numberOfDicePicker.setMaxValue(10);
        numberOfDicePicker.setValue(numberOfDice);

        Spinner sidesSpinner = (Spinner) findViewById(R.id.sidesSpinner);
        Integer[] dice = new Integer[]{4, 6, 8, 10, 12, 20};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, dice);
        sidesSpinner.setAdapter(adapter);

        int spinnerPos = 1;
        for (int i = 0; i < dice.length; i++){
            if (sides == dice[i]){
                spinnerPos = i;
            }
        }
        sidesSpinner.setSelection(spinnerPos);
    }

    public void backClicked(View view) {


    }
}