package joshuaknewstub.diceroller;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Initiate dice
    Dice dice = Dice.dice[2];
    private static final int settingsRequestCode = 1234;

    //Sounds
    SoundPool soundPool;
    int shake;
    int[] rollSfx;
    int lastRollSfxId;
    Boolean sfxOn = true;

    //Get the dice and sfx settings after activity is destroyed, load sounds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            dice = Dice.dice[savedInstanceState.getInt("dice")];
            sfxOn = savedInstanceState.getBoolean("sfxOn");
        }
        loadSounds();
    }

    //Update the button on start and play a sound
    @Override
    protected void onStart() {
        super.onStart();
        updateRollButton();
        playSound(shake);
    }

    //Save dice and sfx settings before destroy
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("dice", dice.Sides);
        savedInstanceState.putBoolean("sfxOn", sfxOn);
    }

    //Free up resources when app is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
    }

    //Use the dice settings to change the appearance of the button
    public void updateRollButton() {
        Button rollDiceButton = findViewById(R.id.rollDiceButton);
        String rollString = getResources().getString(R.string.roll);
        String diceString = rollString + " " + dice.numOfDice + "D" + dice.Sides;
        rollDiceButton.setText(diceString);
    }

    //Roll the number of dice specified
    public void rollDice(View view) {
        int[] results = new int[dice.numOfDice];
        for (int i = 0; i < dice.numOfDice; i++) {
            results[i] = dice.roll();
        }

        StringBuilder resultString = new StringBuilder();
        int sum = 0;
        //If only one dice then no show addition or sum the numbers
        if (results.length == 1) {
            resultString.append("");
            sum = results[0];
            updateResults("", "");
        } else {
            //If multiple dice, show every result in a string separated by a + sign and sum them together
            for (int i = 0; i < results.length - 1; i++) {
                resultString.append(results[i]);
                resultString.append(" + ");
                sum += results[i];
            }
            //Add the last number with an = sign instead
            resultString.append(results[results.length - 1]);
            resultString.append("\n=");
            sum += results[results.length - 1];
        }

        //Display the results on screen
        updateResults(resultString.toString(), String.valueOf(sum));
        //Play SFX
        playRollSound();

    }

    //Use strings passed on to update the views
    public void updateResults(String results, String totals) {
        TextView result = findViewById(R.id.results);
        TextView total = findViewById(R.id.total);
        result.setText(results);
        total.setText(totals);
    }

    //Use intents to do to the settings page passing the current settings
    public void settingsClicked(View view) {
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("numberOfDice", dice.numOfDice);
        intent.putExtra("sides", dice.Sides);
        intent.putExtra("sfxOn", sfxOn);
        startActivityForResult(intent, settingsRequestCode);
    }

    //When returning to this activity apply the new settings
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == settingsRequestCode) {
            if (resultCode == Activity.RESULT_OK) {

                dice = Dice.dice[data.getIntExtra("sides", 2)];
                dice.numOfDice = data.getIntExtra("numOfDice", 1);
                sfxOn = data.getBooleanExtra("sfxOn", false);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("Settings Result", "Settings Activity failed to return a result");
            }
        }
    }

    //Use sound pool to load a few sound at once
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

    //Play a random rolling sound that wasn't the last played
    public void playRollSound() {
        Random random = new Random();
        int rollSfxId = random.nextInt(4);
        while (rollSfxId == lastRollSfxId) {
            rollSfxId = random.nextInt(4);
        }
        playSound(rollSfx[rollSfxId]);
        lastRollSfxId = rollSfxId;

    }

    //Use a ID defined in loadSounds() to play a sound
    public void playSound(int id) {
        if (sfxOn) {
            soundPool.play(id, 1, 1, 1, 0, 1);
        }
    }
}