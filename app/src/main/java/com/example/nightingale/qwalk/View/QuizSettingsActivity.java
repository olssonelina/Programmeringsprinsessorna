package com.example.nightingale.qwalk.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.nightingale.qwalk.R;

/**
 * Created by Elina Olsson on 2017-05-18.
 */

public class QuizSettingsActivity extends AppCompatActivity {

    CheckBox hideQuestions, customOrder, questionTimer, quizTimer, bot;
    Button easy, medium, hard;
    TextView level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizsettings);

        hideQuestions = (CheckBox) findViewById(R.id.hiddenQuestions);
        customOrder = (CheckBox) findViewById(R.id.customOrder);
        questionTimer = (CheckBox) findViewById(R.id.questionTimer);
        quizTimer = (CheckBox) findViewById(R.id.quizTimer);
        bot = (CheckBox) findViewById(R.id.bot);

        easy = (Button) findViewById(R.id.easy);
        medium = (Button) findViewById(R.id.medium);
        hard = (Button) findViewById(R.id.hard);

        level = (TextView) findViewById(R.id.level);
    }


    public void enableChoosingLevel(View view) {
        if (bot.isChecked()) {
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
    }
}
