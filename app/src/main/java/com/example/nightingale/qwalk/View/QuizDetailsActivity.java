package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nightingale.qwalk.InterfaceView.IQuizDetails;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.QuizDifficulty;
import com.example.nightingale.qwalk.Model.QuizSetting;
import com.example.nightingale.qwalk.Presenter.QuizDetailsPresenter;
import com.example.nightingale.qwalk.R;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-05-18.
 */

public class QuizDetailsActivity extends AppCompatActivity implements IQuizDetails {

    private QuizDetailsPresenter presenter;

    private Button edit;
    private TextView title;
    private TextView description;

    public static final int QUIZ_SETTING_CODE = 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizdetails);

        edit = (Button) findViewById(R.id.edit);
        edit.setBackgroundResource(R.drawable.pen);

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);

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
        //TODO KEVIN här får du :)
    }

    @Override
    public void setEditButtonEnabled(boolean value) {
        edit.setEnabled(value);
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
