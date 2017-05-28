package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.nightingale.qwalk.Presenter.QuizSettings.IQuizSettings;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Model.Quiz.QuizDifficulty;
import com.example.nightingale.qwalk.Model.Quiz.QuizSetting;
import com.example.nightingale.qwalk.Presenter.QuizSettings.QuizSettingsPresenter;
import com.example.nightingale.qwalk.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.nightingale.qwalk.Model.Quiz.QuizDifficulty.*;
import static com.example.nightingale.qwalk.Model.Quiz.QuizSetting.*;

/**
 * Created by Elina Olsson on 2017-05-18.
 */

public class QuizSettingsActivity extends AppCompatActivity implements IQuizSettings {

    private QuizSettingsPresenter presenter;

    private CheckBox hideQuestions, inOrder, quizTimer, bot;
    private Button easy, medium, hard;
    private TextView level;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizsettings);

        hideQuestions = (CheckBox) findViewById(R.id.hiddenQuestions);
        inOrder = (CheckBox) findViewById(R.id.customOrder);
        quizTimer = (CheckBox) findViewById(R.id.quizTimer);
        bot = (CheckBox) findViewById(R.id.bot);

        easy = (Button) findViewById(R.id.easy);
        medium = (Button) findViewById(R.id.medium);
        hard = (Button) findViewById(R.id.hard);

        level = (TextView) findViewById(R.id.level);

        Quiz quiz = (Quiz) getIntent().getExtras().get("quiz");

        presenter = new QuizSettingsPresenter(this, quiz);
    }

    @Override
    public final void setDifficultiesVisible(boolean value) {
        if (value) {
            level.setVisibility(View.VISIBLE);
            easy.setVisibility(View.VISIBLE);
            medium.setVisibility(View.VISIBLE);
            hard.setVisibility(View.VISIBLE);
        } else {
            level.setVisibility(View.INVISIBLE);
            easy.setVisibility(View.INVISIBLE);
            medium.setVisibility(View.INVISIBLE);
            hard.setVisibility(View.INVISIBLE);
        }
        level.setEnabled(value);
        easy.setEnabled(value);
        medium.setEnabled(value);
        hard.setEnabled(value);
    }

    @Override
    public final void closeWithResult(List<QuizSetting> setTrue, List<QuizSetting> setFalse, QuizDifficulty difficulty) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("setTrue", (ArrayList) setTrue);
        returnIntent.putExtra("setFalse", (ArrayList) setFalse);
        returnIntent.putExtra("difficulty", difficulty);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public final void onBackPressed() {
        presenter.backPressed();
    }

    public final void onBackClicked(View view) {
        onBackPressed();
    }

    public final void checkBoxPressed(View view) {
        CheckBox cb = (CheckBox) view;

        if (cb.equals(hideQuestions)){
            presenter.setSetting(QUESTIONS_ARE_HIDDEN, hideQuestions.isChecked());
        }
        else if (cb.equals(inOrder)){
            presenter.setSetting(QUESTIONS_IN_ORDER, inOrder.isChecked());
        }
        else if (cb.equals(quizTimer)){
            presenter.setSetting(HAS_QUIZ_TIMER, quizTimer.isChecked());
        }
        else if (cb.equals(bot)){
            presenter.setSetting(WITH_AI, bot.isChecked());
        }
        else {
            throw new IllegalArgumentException("No such checkBox!");
        }
    }

    public final void difficultyPressed(View view){
        easy.setBackgroundResource(R.drawable.greenmonkey);
        medium.setBackgroundResource(R.drawable.yellowmonkey);
        hard.setBackgroundResource(R.drawable.redmonkey);

        if (view.equals(easy)){
            presenter.setDifficulty(EASY);
            easy.setBackgroundResource(R.drawable.greenmonkeyclicked);
        }else if (view.equals(medium)){
            presenter.setDifficulty(MEDIUM);
            medium.setBackgroundResource(R.drawable.yellowmonkeyclicked);
        }else if (view.equals(hard)){
            presenter.setDifficulty(HARD);
            hard.setBackgroundResource(R.drawable.redmonkeyclicked);
        }
    }

    @Override
    public final void setChecked(QuizSetting quizSetting, boolean value) {
        switch (quizSetting){
            case QUESTIONS_IN_ORDER:
                inOrder.setChecked(value);
                break;

            case QUESTIONS_ARE_HIDDEN:
                hideQuestions.setChecked(value);
                break;

            case HAS_QUIZ_TIMER:
                quizTimer.setChecked(value);
                break;

            case WITH_AI:
                bot.setChecked(value);
                break;

            default:
                throw new IllegalArgumentException("No such setting!");
        }
    }
}
