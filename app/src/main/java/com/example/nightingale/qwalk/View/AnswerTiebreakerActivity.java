package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nightingale.qwalk.InterfaceView.IAnswerTiebreaker;
import com.example.nightingale.qwalk.Model.Tiebreaker;
import com.example.nightingale.qwalk.Presenter.AnswerOptionPresenter;
import com.example.nightingale.qwalk.Presenter.AnswerTiebreakerPresenter;
import com.example.nightingale.qwalk.R;

import static java.lang.Integer.parseInt;

/**
 * Created by Kraft on 2017-05-10.
 */

public class AnswerTiebreakerActivity extends AppCompatActivity implements IAnswerTiebreaker, SeekBar.OnSeekBarChangeListener {

    private AnswerTiebreakerPresenter presenter;

    private TextView title;
    private TextView value;
    private SeekBar slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answertiebreaker);

        title = ((TextView)findViewById(R.id.tiebreakerTitle));
        value = (TextView) findViewById(R.id.answerValue);
        slider = (SeekBar)findViewById(R.id.answerSlider);

        Intent i = getIntent();
        Tiebreaker question = i.getParcelableExtra("question");
        presenter = new AnswerTiebreakerPresenter(this, question);

        slider.setOnSeekBarChangeListener(this);

    }

    @Override
    public void closeWithResult(int value) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("answer", value);
        setResult(GetPositionActivity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setRange(int from, int to) {
        int progress = slider.getProgress();
        int val = (progress * (slider.getWidth() - 2 * slider.getThumbOffset())) / slider.getMax();
        value.setText("" + (progress + from));
        value.setX(slider.getX() + val + slider.getThumbOffset() / 2);
        slider.setMax(to - from);
    }

    @Override
    public int getChoice(int from) {
        return slider.getProgress() + from;
    }

    public void saveButtonPressed(View view){
        presenter.closePressed();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        presenter.sliderChanged(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
}