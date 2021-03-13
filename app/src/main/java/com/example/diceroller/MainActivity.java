package com.example.diceroller;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Dice dice = new Dice(4, 12);
    private static final int settingsRequestCode = 1234;
    Random random = new Random();
    //Sounds
    SoundPool soundPool;
    int shake;
    int[] rollSfx;
    int lastRollSfxId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateRollButton();
        loadSounds();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == settingsRequestCode) {
            if (resultCode == Activity.RESULT_OK) {

                dice.numOfDice = data.getIntExtra("numOfDice", 1);
                dice.sides = data.getIntExtra("sides", 6);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("Settings Result", "Settings Activity failed to return a result");
            }
        }
        updateRollButton();
        updateResults("", "");
        playSound(shake);

    }

    public void updateRollButton() {
        Button rollDiceButton = findViewById(R.id.rollDicButton);
        String rollString = getResources().getString(R.string.roll);
        String diceString = rollString + " " + dice.numOfDice + "D" + dice.sides;
        rollDiceButton.setText(diceString);
    }

    public void rollDice(View view) {

        int[] rolls = dice.roll();
        StringBuilder results = new StringBuilder();
        int sum = 0;

        if (rolls.length == 1) {
            results.append("");
            sum = rolls[0];

        } else {
            for (int i = 0; i < rolls.length - 1; i++) {
                results.append(rolls[i]);
                results.append(" + ");
                sum += rolls[i];
            }
            results.append(rolls[rolls.length - 1]);
            results.append("\n=");
            sum += rolls[rolls.length - 1];
        }
        updateResults(results.toString(), String.valueOf(sum));

        int rollSfxId = random.nextInt(4);
        while (rollSfxId == lastRollSfxId){
            rollSfxId = random.nextInt(4);
        }
        playSound(rollSfx[rollSfxId]);
        lastRollSfxId = rollSfxId;
    }

    public void updateResults(String results, String totals) {
        TextView result = findViewById(R.id.results);
        TextView total = findViewById(R.id.total);
        result.setText(results);
        total.setText(totals);
    }

    public void Settings(View view) {
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("numberOfDice", dice.numOfDice);
        intent.putExtra("sides", dice.sides);
        startActivityForResult(intent, settingsRequestCode);
    }

    public void loadSounds() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        shake = soundPool.load(this, R.raw.shake, 1);
        rollSfx = new int[]{
                soundPool.load(this, R.raw.roll1, 1),
                soundPool.load(this, R.raw.roll2, 1),
                soundPool.load(this, R.raw.roll3, 1),
                soundPool.load(this, R.raw.roll4, 1)
        };
    }

    public void playSound(int id) {
        soundPool.play(id, 1, 1, 1, 0, 1);
    }
}