package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nightingale.qwalk.R;

import static java.lang.Integer.parseInt;

/**
 * Created by Elina Olsson on 2017-05-08.
 */

public class TiebreakerActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView value;
    private EditText minField;
    private EditText maxField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiebreaker);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        value = (TextView) findViewById(R.id.value);
        minField = (EditText) findViewById(R.id.minField);
        maxField = (EditText) findViewById(R.id.maxField);



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
        return seekBar.getProgress();
    }

    public void addPosition(View view) {
        Intent intent = new Intent(this, GetPositionActivity.class);
        startActivityForResult(intent, CreateQuestionActivity.GET_POSITION_CODE);
    }


}
