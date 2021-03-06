package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nightingale.qwalk.Presenter.AnswerOptionQuestion.IAnswerOption;
import com.example.nightingale.qwalk.Model.Question.OptionQuestion;
import com.example.nightingale.qwalk.Presenter.AnswerOptionQuestion.AnswerOptionQuestionPresenter;
import com.example.nightingale.qwalk.R;

import static com.example.nightingale.qwalk.Model.Actor.IActor.NO_ANSWER;

/**
 * Created by Kraft on 2017-04-26.
 */

public class AnswerOptionQuestionActivity extends AppCompatActivity implements IAnswerOption {


    private AnswerOptionQuestionPresenter presenter;

    private Button[] optionButtons = new Button[4];
    private Button saveAnswer;
    private TextView title;

    private static final int SELECTED_COLOUR = Color.parseColor("#FF0295d8");
    private static final int DESELECTED_COLOUR = Color.parseColor("#FF303030");

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answeroption);

        title = ((TextView) findViewById(R.id.question));

        optionButtons[0] = (Button) findViewById(R.id.option1);
        optionButtons[1] = (Button) findViewById(R.id.option2);
        optionButtons[2] = (Button) findViewById(R.id.option3);
        optionButtons[3] = (Button) findViewById(R.id.option4);

        saveAnswer = (Button) findViewById(R.id.saveAnswer);

        Intent i = getIntent();
        OptionQuestion question = i.getParcelableExtra("question");

        int ai = i.getIntExtra("aiAnswer", NO_ANSWER);
        presenter = new AnswerOptionQuestionPresenter(this, question, ai);
    }

    public final void optionPressed(View view) {
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].equals(view)) {
                presenter.optionPressed(i);
                break;
            }
        }
    }

    public final void onBackPressed(View view) {
        finish();
    }

    public final void submitAnswer(View view) {
        presenter.submitClicked();
    }

    public final void showBotAnswer(int index) {
        if (presenter.isSameAnswer()) {
            optionButtons[index].setBackgroundResource(R.drawable.monkeyanswerblue);
        } else {
            optionButtons[index].setBackgroundResource(R.drawable.monkeyanswer);
        }
    }

    public final String getButtonText() {
        return saveAnswer.getText().toString();
    }

    public final void setButtonText(String text) {
        saveAnswer.setText(text);
    }

    @Override
    public final void setOptions(String[] options) {
        for (Button b : optionButtons) {
            b.setEnabled(false);
            b.setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < options.length && !options[i].equals(""); i++) {
            optionButtons[i].setEnabled(true);
            optionButtons[i].setText(options[i]);
            optionButtons[i].setVisibility(View.VISIBLE);
        }
    }


    @Override
    public final void closeWithResult(int chosenIndex, OptionQuestion question) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("answer", chosenIndex);
        returnIntent.putExtra("question", question);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public final void setOptionColour(int index, boolean isSelectedColour) {
        optionButtons[index].setBackgroundColor(isSelectedColour ? SELECTED_COLOUR : DESELECTED_COLOUR);
    }

    @Override
    public final void setCloseButtonEnabled(boolean enabled) {
        saveAnswer.setEnabled(enabled);
    }

    @Override
    public final void setTitle(String title) {
        this.title.setText(title);
    }
}