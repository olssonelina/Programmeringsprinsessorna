package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nightingale.qwalk.Presenter.CreateTiebreaker.ICreateTiebreaker;
import com.example.nightingale.qwalk.Model.Question.Tiebreaker;
import com.example.nightingale.qwalk.Presenter.CreateTiebreaker.CreateTiebreakerPresenter;
import com.example.nightingale.qwalk.R;

import static com.example.nightingale.qwalk.Activities.CreateOptionQuestionActivity.GET_POSITION_CODE;
import static java.lang.Integer.parseInt;

/**
 * Created by Elina Olsson on 2017-05-08.
 */

public class CreateTiebreakerActivity extends AppCompatActivity implements ICreateTiebreaker, SeekBar.OnSeekBarChangeListener {

    private CreateTiebreakerPresenter presenter;

    private SeekBar seekBar;
    private TextView value;
    private EditText minField;
    private EditText maxField;
    private EditText questionTitle;
    private TextView locationText;
    private double latitude = 0, longitude = 0;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtiebreaker);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        value = (TextView) findViewById(R.id.value);
        minField = (EditText) findViewById(R.id.minField);
        maxField = (EditText) findViewById(R.id.maxField);
        questionTitle = (EditText) findViewById(R.id.questionField);
        locationText = (TextView) findViewById(R.id.tiebreakerAddPosition);
        value.setText("Rätt \n" + " " + minField.getText());
        seekBar.setOnSeekBarChangeListener(this);

        presenter = new CreateTiebreakerPresenter(this);

        try {
            presenter.setAllFields((Tiebreaker) getIntent().getParcelableExtra("question"));
            presenter.updateLocationText();
        } catch (NullPointerException e) {
        }

        EditText[] arr = {minField, maxField};
        for (EditText et: arr) {
            et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        presenter.makeAllowed(minField.getText().toString(), maxField.getText().toString());
                    }
                }
            });
        }

    }

    public final void addPosition(View view) {
        Intent intent = new Intent(this, GetPositionActivity.class);
        startActivityForResult(intent, GET_POSITION_CODE);
    }

    public final void onBackPressed(View view) {
        finish();
    }

    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_POSITION_CODE) {
            try {
                Location l = (Location) data.getExtras().get("result");
                latitude = l.getLatitude();
                longitude = l.getLongitude();
            } catch (NullPointerException e) {
                latitude = 0;
                longitude = 0;
            }
            presenter.updateLocationText();
        }
    }

    public final void doneButtonPressed(View view) {
        presenter.doneButtonPressed();
    }

    @Override
    public final int getLowerBounds() {
        return parseInt(minField.getText().toString());
    }

    @Override
    public final int getHigherBounds() {
        return parseInt(maxField.getText().toString());
    }

    @Override
    public final int getAnswer() {
        return seekBar.getProgress() + parseInt(minField.getText().toString());
    }

    @Override
    public final String getQuestionTitle() {
        return questionTitle.getText().toString();
    }

    @Override
    public final void closeWithResult(Tiebreaker question) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("tiebreaker", question);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public final double getLatitude() {
        return latitude;
    }

    @Override
    public final double getLongitude() {
        return longitude;
    }

    @Override
    public final void sendError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public final void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            minField.clearFocus();
            maxField.clearFocus();
        }
        int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
        value.setText("Rätt \n" + " " + (progress + parseInt(minField.getText().toString())));
        value.setX((int) (seekBar.getX() + val + seekBar.getThumbOffset() / 2.0));
        int minValue = parseInt(minField.getText().toString());
        seekBar.setMax(parseInt(maxField.getText().toString()) - minValue);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public final void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public final void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public final void setLowerBounds(int lowerBounds) {
        minField.setText("" + lowerBounds);
    }

    @Override
    public final void setUpperBounds(int upperBounds) {
        maxField.setText("" + upperBounds);
    }

    @Override
    public final void setAnswer(int answer) {
        seekBar.setProgress(answer + Integer.parseInt(minField.getText().toString()));
        onProgressChanged(seekBar, answer + Integer.parseInt(minField.getText().toString()), false);
    }

    @Override
    public final void setQuestionTitle(String questionTitle) {
        this.questionTitle.setText(questionTitle);
    }

    @Override
    public final void setLocationText(String text) {
        locationText.setText(text);
    }
}
