package com.example.nightingale.qwalk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.example.nightingale.qwalk.R.id.textView;

/**
 * Created by Elina Olsson on 2017-05-08.
 */

public class TiebreakerActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiebreaker);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        value = (TextView) findViewById(R.id.value);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                value.setText("" + progress);
                value.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
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
}
