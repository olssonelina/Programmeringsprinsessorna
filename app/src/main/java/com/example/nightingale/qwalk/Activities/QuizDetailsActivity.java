package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nightingale.qwalk.Presenter.QuizDetails.IQuizDetails;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Model.Quiz.QuizDifficulty;
import com.example.nightingale.qwalk.Model.Quiz.QuizSetting;
import com.example.nightingale.qwalk.Presenter.QuizDetails.QuizDetailsPresenter;
import com.example.nightingale.qwalk.R;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-05-18.
 */

public class QuizDetailsActivity extends AppCompatActivity implements IQuizDetails {

    private QuizDetailsPresenter presenter;


    private TextView title;
    private TextView description;
    private Button edit;
    private Button delete;
    private ProgressBar spinner;

    public static final int QUIZ_SETTING_CODE = 34;
    public static final int QUIZ_EDIT_CODE = 72;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizdetails);

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        edit = (Button) findViewById(R.id.edit);
        delete = (Button) findViewById(R.id.deleteQuizButton);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        boolean editable = (Boolean) getIntent().getExtras().get("editable");
        Quiz quiz = (Quiz) getIntent().getExtras().get("quiz");

        presenter = new QuizDetailsPresenter(this, quiz, editable);

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

    public void onBackPressed(View view) {
        finish();
    }

    public void onDeletePressed(View view) {

        edit.setEnabled(false);
        spinner.setVisibility(View.VISIBLE);


        presenter.deletePressed();

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
        startActivityForResult(intent, QUIZ_EDIT_CODE);
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
        else if (requestCode == QUIZ_EDIT_CODE){
            closeWithResult(true);
        }
    }

    @Override
    public void setEditable(boolean value) {
        edit.setEnabled(value);
        edit.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
        delete.setEnabled(value);
        delete.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void closeWithResult(boolean shouldMenuUpdate) {
        Intent returnIntent = new Intent();
        setResult(GetPositionActivity.RESULT_OK, returnIntent);
        returnIntent.putExtra("update", shouldMenuUpdate);
        finish();
    }
}
