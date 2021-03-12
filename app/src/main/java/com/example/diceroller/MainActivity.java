package com.example.diceroller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Dice dice = new Dice(4, 12);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rollDiceButton = findViewById(R.id.rollDicButton);
        String rollString = getResources().getString(R.string.roll);
        String diceString = rollString + " " + dice.numOfDice + "D" + dice.sides;
        rollDiceButton.setText(diceString);
    }


    public void rollDice(View view) {
        TextView result = findViewById(R.id.result);
        int[] rolls = dice.roll();
        StringBuilder results = new StringBuilder();
        if (rolls.length == 1) {
            results.append(rolls[0]);
        } else {
            int sum = 0;
            for (int i = 0; i < rolls.length - 1; i++) {
                results.append(rolls[i]).append(" + ");
                sum += rolls[i];
            }
            results.append(rolls[rolls.length - 1]);
            sum += rolls[rolls.length - 1];
            results.append("\n=\n").append(sum);
        }
        result.setText(results.toString());
    }

    public void Settings(View view) {
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("numberOfDice", dice.numOfDice);
        intent.putExtra("sides", dice.sides);
        startActivity(intent);
    }
}