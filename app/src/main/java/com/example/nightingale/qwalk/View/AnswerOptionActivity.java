package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nightingale.qwalk.InterfaceView.IAnswerOption;
import com.example.nightingale.qwalk.Model.AI;
import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Presenter.AnswerOptionPresenter;
import com.example.nightingale.qwalk.R;

import static com.example.nightingale.qwalk.Model.IActor.NO_ANSWER;

/**
 * Created by Kraft on 2017-04-26.
 */

public class AnswerOptionActivity extends AppCompatActivity implements IAnswerOption {


    private AnswerOptionPresenter presenter;

    private Button[] optionButtons = new Button[4];
    private Button saveAnswer;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        int questionIndex = i.getIntExtra("questionIndex", 0);

        int ai = i.getIntExtra("aiAnswer", NO_ANSWER);
        presenter = new AnswerOptionPresenter(this, question, questionIndex, ai);
    }

    public void optionPressed(View view) {
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].equals(view)) {
                presenter.optionPressed(i);
                break;
            }
        }
    }

    public void submitAnswer(View view) {
        presenter.submitClicked();
    }

    public void showBotAnswer(int index) {
        optionButtons[index].setBackgroundResource(R.drawable.monkeyanswer);
    }

    public String getButtonText() {
        return saveAnswer.getText().toString();
    }

    public void setButtonText(String text) {
        saveAnswer.setText(text);
    }

    @Override
    public void setOptions(String[] options) {
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
    public void closeWithResult(int chosenIndex, OptionQuestion question) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("answer", chosenIndex);
        returnIntent.putExtra("question", question);
        setResult(GetPositionActivity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void setCloseButtonEnabled(boolean enabled) {
        saveAnswer.setEnabled(enabled);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }
}