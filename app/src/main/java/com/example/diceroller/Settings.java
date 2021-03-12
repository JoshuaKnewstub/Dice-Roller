package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.Arrays;

public class Settings extends AppCompatActivity {

    NumberPicker numberOfDicePicker;
    Spinner sidesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        int numberOfDice = intent.getIntExtra("numberOfDice", 1);
        int sides = intent.getIntExtra("sides", 6);

        numberOfDicePicker = findViewById(R.id.numberOfDicePicker);
        numberOfDicePicker.setMinValue(1);
        numberOfDicePicker.setMaxValue(10);
        numberOfDicePicker.setValue(numberOfDice);

        sidesSpinner = findViewById(R.id.sidesSpinner);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Dice.dice);
        sidesSpinner.setAdapter(adapter);
        sidesSpinner.setSelection(Dice.indexOfDice(sides));
    }

    public void backClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("numberOfDice", numberOfDicePicker.getValue());
        intent.putExtra("sides", sidesSpinner.getSelectedItemPosition());
        startActivity(intent);
    }
}