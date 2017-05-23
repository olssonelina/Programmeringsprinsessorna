package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nightingale.qwalk.InterfaceView.ICreateTiebreaker;
import com.example.nightingale.qwalk.Model.Tiebreaker;
import com.example.nightingale.qwalk.Presenter.CreateTiebreakerPresenter;
import com.example.nightingale.qwalk.R;

import static com.example.nightingale.qwalk.View.CreateOptionQuestionActivity.GET_POSITION_CODE;
import static java.lang.Integer.min;
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
    protected void onCreate(Bundle savedInstanceState) {
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
        }
        catch (NullPointerException e) {}
    }

    public void addPosition(View view) {
        Intent intent = new Intent(this, GetPositionActivity.class);
        startActivityForResult(intent, GET_POSITION_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_POSITION_CODE) {
            try {
                Location l = (Location) data.getExtras().get("result");
                latitude = l.getLatitude();
                longitude = l.getLongitude();
            }
            catch (NullPointerException e){
                latitude = 0;
                longitude = 0;
            }
            presenter.updateLocationText();
        }
    }

    public void doneButtonPressed(View view){
        presenter.doneButtonPressed();
    }

    @Override
    public int getLowerBounds() {
        return parseInt(minField.getText().toString());
    }

    @Override
    public int getHigherBounds() {
        return parseInt(maxField.getText().toString());
    }

    @Override
    public int getAnswer() {
        return seekBar.getProgress() + parseInt(minField.getText().toString());
    }

    @Override
    public String getQuestionTitle() {
        return questionTitle.getText().toString();
    }

    @Override
    public void closeWithResult(Tiebreaker question) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("tiebreaker", question);
        setResult(GetPositionActivity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public void sendError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
        value.setText("Rätt \n" + " " + (progress + parseInt(minField.getText().toString())));
        value.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
        int minValue = parseInt(minField.getText().toString());
        seekBar.setMax(parseInt(maxField.getText().toString()) - minValue);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public void setLowerBounds(int lowerBounds) {
        minField.setText(""+lowerBounds);
    }

    @Override
    public void setUpperBounds(int upperBounds) {
        maxField.setText(""+upperBounds);
    }

    @Override
    public void setAnswer(int answer) {
        seekBar.setProgress(answer + Integer.parseInt(minField.getText().toString()));
        onProgressChanged(seekBar, answer + Integer.parseInt(minField.getText().toString()), false);
    }

    @Override
    public void setQuestionTitle(String questionTitle) {
        this.questionTitle.setText(questionTitle);
    }

    @Override
    public void setLocationText(String text) {
        locationText.setText(text);
    }
}
