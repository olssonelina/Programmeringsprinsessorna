package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nightingale.qwalk.InterfaceView.IAnswerOption;
import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Presenter.AnswerOptionPresenter;
import com.example.nightingale.qwalk.R;

/**
 * Created by Kraft on 2017-04-26.
 */

public class AnswerOptionActivity extends AppCompatActivity implements IAnswerOption {


    private AnswerOptionPresenter presenter;

    private Button[] optionButtons = new Button[4];
    private Button submitButton;
    private TextView title;

    private static final int selectedColour = Color.BLUE;
    private static final int deselectedColour = Color.WHITE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answertiebreaker);

        title = ((TextView)findViewById(R.id.question));

        optionButtons[0] = (Button)findViewById(R.id.option1);
        optionButtons[1] = (Button)findViewById(R.id.option2);
        optionButtons[2] = (Button)findViewById(R.id.option3);
        optionButtons[3] = (Button)findViewById(R.id.option4);

        submitButton = (Button)findViewById(R.id.saveAnswer);

        Intent i = getIntent();
        OptionQuestion question = i.getParcelableExtra("question");
        presenter = new AnswerOptionPresenter(this, question);

    }

    public void optionPressed(View view){
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].equals(view)){
                presenter.optionPressed(i);
                break;
            }
        }
    }


    public void submitAnswer(View view) {
        presenter.closePressed();
    }

    @Override
    public void setOptions(String[] options) {
        for (Button b : optionButtons){
            b.setEnabled(false);
        }

        for (int i = 0; i < options.length && !options[i].equals(""); i++) {
            optionButtons[i].setEnabled(true);
            optionButtons[i].setText(options[i]);
        }
    }

    @Override
    public void setOptionColour(int index, boolean isSelectedColour) {
        optionButtons[index].setBackgroundColor( isSelectedColour ? selectedColour : deselectedColour);
    }

    @Override
    public void closeWithResult(int chosenIndex) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("answer", chosenIndex);
        setResult(GetPositionActivity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void setCloseButtonEnabled(boolean enabled) {
        submitButton.setEnabled(enabled);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }
}