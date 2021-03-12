package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Dice dice = new Dice(2,20);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rollDiceButton = (Button) findViewById(R.id.rollDicButton);
        String rollString = (String) getResources().getString(R.string.roll);
        String diceString = String.format("%s %xd%x",rollString, dice.numOfDice, dice.sides);
        rollDiceButton.setText(diceString);
    }


    public void rollDice(View view) {
        TextView result = (TextView) findViewById(R.id.result);
        int roll = dice.roll();
        result.setText(String.valueOf(roll));

    }

    public void Settings(View view) {
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("numberOfDice", dice.numOfDice);
        intent.putExtra("sides", dice.sides);
        startActivity(intent);
    }
}