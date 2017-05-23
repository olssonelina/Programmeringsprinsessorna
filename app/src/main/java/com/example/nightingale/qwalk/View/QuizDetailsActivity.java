package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nightingale.qwalk.InterfaceView.IQuizDetails;
import com.example.nightingale.qwalk.Model.DatabaseHandler;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.QuizDifficulty;
import com.example.nightingale.qwalk.Model.QuizSetting;
import com.example.nightingale.qwalk.Presenter.DeleteDialog;
import com.example.nightingale.qwalk.Presenter.QuizDetailsPresenter;
import com.example.nightingale.qwalk.R;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-05-18.
 */

public class QuizDetailsActivity extends AppCompatActivity implements IQuizDetails {

    private QuizDetailsPresenter presenter;


    private TextView title;
    private TextView description;
    Button edit;
    private ProgressBar spinner;

    public static final int QUIZ_SETTING_CODE = 34;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizdetails);

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);

        boolean editable = (Boolean) getIntent().getExtras().get("editable");
        Quiz quiz = (Quiz) getIntent().getExtras().get("quiz");

        presenter = new QuizDetailsPresenter(this, quiz, editable);

        edit = (Button) findViewById(R.id.edit);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
    }


    public void onPlayPressed(View view) {
        presenter.playPressed();
    }

    public void onEditPressed(View view) {
        presenter.editPressed();
    }

    public void onSettingsPressed(View view) {
        presenter.settingsPressed();
    }

    public void onBackPressed(View view) {finish();}

    public void onDeletePressed(View view) {

        edit.setEnabled(false);
        spinner.setVisibility(View.VISIBLE);


        presenter.deletePressed(this);

    }

    public void deleteComplete(String msg){
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_LONG).show();

        edit.setEnabled(true);
        spinner.setVisibility(View.GONE);

        finish();

    }

    @Override
    public void openSettings(Quiz quiz) {
        Intent intent = new Intent(this, QuizSettingsActivity.class);
        intent.putExtra("quiz", quiz);
        startActivityForResult(intent, QUIZ_SETTING_CODE);
    }

    @Override
    public void playQuiz(Quiz quiz) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("quiz", quiz);
        startActivity(intent);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void editQuiz(Quiz quiz) {
        Intent intent = new Intent(this, CreateQuizActivity.class);
        intent.putExtra("quiz", quiz);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUIZ_SETTING_CODE) {
            ArrayList<QuizSetting> trueSettings = (ArrayList<QuizSetting>) data.getExtras().get("setTrue");
            ArrayList<QuizSetting> falseSettings = (ArrayList<QuizSetting>) data.getExtras().get("setFalse");

            Object[] tso = trueSettings.toArray();
            QuizSetting[] tsqs = new QuizSetting[tso.length];
            for (int i = 0; i < tso.length; i++) {
                tsqs[i] = (QuizSetting) tso[i];
            }

            Object[] fso = falseSettings.toArray();
            QuizSetting[] fsqs = new QuizSetting[fso.length];
            for (int i = 0; i < fso.length; i++) {
                fsqs[i] = (QuizSetting) fso[i];
            }

            presenter.settingsChanged(tsqs, fsqs);

            presenter.difficultyChanged((QuizDifficulty) data.getExtras().get("difficulty"));
        }
    }

}
