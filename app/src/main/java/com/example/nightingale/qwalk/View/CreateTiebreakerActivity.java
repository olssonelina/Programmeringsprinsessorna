package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nightingale.qwalk.Model.Tiebreaker;
import com.example.nightingale.qwalk.R;

import static com.example.nightingale.qwalk.View.CreateQuestionActivity.GET_POSITION_CODE;
import static java.lang.Integer.parseInt;

/**
 * Created by Elina Olsson on 2017-05-08.
 */

public class CreateTiebreakerActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView value;
    private EditText minField;
    private EditText maxField;
    private EditText questionTitle;
    private double latitude = 0, longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtiebreaker);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        value = (TextView) findViewById(R.id.value);
        minField = (EditText) findViewById(R.id.minField);
        maxField = (EditText) findViewById(R.id.maxField);
        questionTitle = (EditText) findViewById(R.id.questionField);


        value.setText("0");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                value.setText("" + (progress + parseInt(minField.getText().toString())));
                value.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                int minValue = parseInt(minField.getText().toString());
                seekBar.setMax(parseInt(maxField.getText().toString()) - minValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public int getCorrectAnswer() {
        return seekBar.getProgress() + parseInt(minField.getText().toString());
    }

    public void addPosition(View view) {
        Intent intent = new Intent(this, GetPositionActivity.class);
        startActivityForResult(intent, GET_POSITION_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_POSITION_CODE) {
            Location l = (Location) data.getExtras().get("result");
            latitude = l.getLatitude();
            longitude = l.getLongitude();
        }
    }

    public void doneButtonPressed(View view) {
        if (isQuestionDone()) {
            Tiebreaker question = buildQuestion();

            Intent returnIntent = new Intent();
            returnIntent.putExtra("tiebreaker", question);
            setResult(GetPositionActivity.RESULT_OK, returnIntent);
            finish();

        } else {
            sendErrorMessage();
        }
    }

    private boolean isQuestionDone() {
        int min = parseInt(minField.getText().toString());
        int max = parseInt(maxField.getText().toString());
        int ans = getCorrectAnswer();
        return latitude != 0 && longitude != 0 && min < max && min <= ans && ans <= max && !questionTitle.getText().toString().equals("");
    }

    private void sendErrorMessage() { //TODO
        Toast.makeText(this, "EJ implementerat", Toast.LENGTH_LONG).show();
    }

    private Tiebreaker buildQuestion() {
        return new Tiebreaker(questionTitle.getText().toString(), getCorrectAnswer(), longitude, latitude, parseInt(minField.getText().toString()), parseInt(maxField.getText().toString()));
    }


}