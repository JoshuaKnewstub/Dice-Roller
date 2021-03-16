package joshuaknewstub.diceroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    NumberPicker numberOfDicePicker;
    Spinner sidesSpinner;
    Boolean sfxOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        //Number picker in range 1-10 and set on currently number of dice
        numberOfDicePicker = findViewById(R.id.numberOfDicePicker);
        numberOfDicePicker.setMinValue(1);
        numberOfDicePicker.setMaxValue(10);


        //Use the dice array to fill a spinner to choose from
        sidesSpinner = findViewById(R.id.sidesSpinner);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Dice.getSidesAsInteger());
        sidesSpinner.setAdapter(adapter);
        //Find the index of the current sides and set the spinner to this value


        //Toggle whether sound effects are on or not with a toggle button
        ToggleButton toggle = findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> sfxOn = isChecked);

        //Get data from intent
        Intent intent = getIntent();
        int numberOfDice;
        //Get settings if page was destroyed
        if (savedInstanceState != null) {
            numberOfDice = savedInstanceState.getInt("numOfDicePicker");
            sfxOn = savedInstanceState.getBoolean("sfxOn");
            sidesSpinner.setSelection(savedInstanceState.getInt("sidesSpinnerPos"));
            //Else get them from the intent
        } else {
            numberOfDice = intent.getIntExtra("numberOfDice", 1);
            sfxOn = intent.getBooleanExtra("sfxOn", true);
            sidesSpinner.setSelection(Dice.getIndex(intent.getIntExtra("sides", 6)));
        }

        numberOfDicePicker.setValue(numberOfDice);
        toggle.setChecked(sfxOn);
    }

    //When you save return all settings with settings
    public void backClicked(View view) {
        Intent returnIntent = new Intent(this, MainActivity.class);
        returnIntent.putExtra("numOfDice", numberOfDicePicker.getValue());
        returnIntent.putExtra("sides", sidesSpinner.getSelectedItemPosition());
        returnIntent.putExtra("sfxOn", sfxOn);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    //Save current value of the settings before being destroyed
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("numOfDicePicker", numberOfDicePicker.getValue());
        savedInstanceState.putInt("sidesSpinnerPos", sidesSpinner.getSelectedItemPosition());
        savedInstanceState.putBoolean("sfxOn", sfxOn);
    }
}