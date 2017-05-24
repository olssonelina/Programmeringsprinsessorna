package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.nightingale.qwalk.InterfaceView.IQuizSettings;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.QuizDifficulty;
import com.example.nightingale.qwalk.Model.QuizSetting;
import com.example.nightingale.qwalk.Presenter.QuizSettingsPresenter;
import com.example.nightingale.qwalk.R;

import java.util.ArrayList;

import static com.example.nightingale.qwalk.Model.QuizDifficulty.*;
import static com.example.nightingale.qwalk.Model.QuizSetting.*;

/**
 * Created by Elina Olsson on 2017-05-18.
 */

public class QuizSettingsActivity extends AppCompatActivity implements IQuizSettings {

    private QuizSettingsPresenter presenter;

    private CheckBox hideQuestions, inOrder, questionTimer, quizTimer, bot;
    private Button easy, medium, hard;
    private TextView level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizsettings);

        hideQuestions = (CheckBox) findViewById(R.id.hiddenQuestions);
        inOrder = (CheckBox) findViewById(R.id.customOrder);
        questionTimer = (CheckBox) findViewById(R.id.questionTimer);
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
    public void setDifficultiesVisible(boolean value) {
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
    public void closeWithResult(ArrayList<QuizSetting> setTrue, ArrayList<QuizSetting> setFalse, QuizDifficulty difficulty) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("setTrue", setTrue);
        returnIntent.putExtra("setFalse", setFalse);
        returnIntent.putExtra("difficulty", difficulty);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        presenter.backPressed();
    }

    public void checkBoxPressed(View view) {
        CheckBox cb = (CheckBox) view;

        if (cb.equals(hideQuestions)){
            presenter.setSetting(IS_HIDDEN, hideQuestions.isChecked());
        }
        else if (cb.equals(inOrder)){
            presenter.setSetting(IN_ORDER, inOrder.isChecked());
        }
        else if (cb.equals(questionTimer)){
            presenter.setSetting(QUESTION_TIMER, questionTimer.isChecked());
        }
        else if (cb.equals(quizTimer)){
            presenter.setSetting(QUIZ_TIMER, quizTimer.isChecked());
        }
        else if (cb.equals(bot)){
            presenter.setSetting(WITH_AI, bot.isChecked());
        }
        else {
            throw new IllegalArgumentException("No such checkBox!");
        }
    }

    public void difficultyPressed(View view){
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
    public void setChecked(QuizSetting quizSetting, boolean value) {
        switch (quizSetting){
            case IN_ORDER:
                inOrder.setChecked(value);
                break;

            case IS_HIDDEN:
                hideQuestions.setChecked(value);
                break;

            case QUIZ_TIMER:
                quizTimer.setChecked(value);
                break;

            case QUESTION_TIMER:
                questionTimer.setChecked(value);
                break;

            case WITH_AI:
                bot.setChecked(value);
                break;

            default:
                throw new IllegalArgumentException("No such setting!");
        }
    }
}
