package com.example.nightingale.qwalk.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nightingale.qwalk.Presenter.CreateOptionQuestion.ICreateOptionQuestion;
import com.example.nightingale.qwalk.Model.Question.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.Presenter.CreateOptionQuestion.CreateOptionQuestionPresenter;
import com.example.nightingale.qwalk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elina Olsson on 2017-04-24.
 */

public class CreateOptionQuestionActivity extends AppCompatActivity
        implements ICreateOptionQuestion, View.OnFocusChangeListener,
        TextView.OnEditorActionListener /* TextWatcher */ {

    private CreateOptionQuestionPresenter presenter;
    public static final int GET_POSITION_CODE = 97;

    private Button deleteQuestion;

    private TextView questionNumber;
    private EditText questionText;
    private TextView locationText;

    private EditText[] options = new EditText[4];
    private TextView[] numbers = new TextView[4];
    private RadioButton[] radioButtons = new RadioButton[4];

    private double latitude = 0;
    private double longitude = 0;
    private int questionID = 0;


    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquestion);

        questionText = (EditText) findViewById(R.id.questionField);
        locationText = (TextView) findViewById(R.id.addPosition);
        deleteQuestion = (Button) findViewById(R.id.deleteQuestionButton);
        deleteQuestion.setVisibility(View.GONE);
        options[0] = (EditText) findViewById(R.id.option1Field);
        options[1] = (EditText) findViewById(R.id.option2Field);
        options[2] = (EditText) findViewById(R.id.option3Field);
        options[3] = (EditText) findViewById(R.id.option4Field);


        numbers[0] = (TextView) findViewById(R.id.number1);
        numbers[1] = (TextView) findViewById(R.id.number2);
        numbers[2] = (TextView) findViewById(R.id.number3);
        numbers[3] = (TextView) findViewById(R.id.number4);

        radioButtons[0] = (RadioButton) findViewById(R.id.radioButton1);
        radioButtons[1] = (RadioButton) findViewById(R.id.radioButton2);
        radioButtons[2] = (RadioButton) findViewById(R.id.radioButton3);
        radioButtons[3] = (RadioButton) findViewById(R.id.radioButton4);

        questionNumber = (TextView) findViewById(R.id.questionX);

        for (int i = 0; i < options.length; i++) {
            options[i].setOnFocusChangeListener(this);
            options[i].setOnEditorActionListener(this);
            //options[i].addTextChangedListener(this);
        }

        presenter = new CreateOptionQuestionPresenter(this, getIntent().getIntExtra("questionCounter", 1));

        try {
            presenter.setAllFields((OptionQuestion) getIntent().getParcelableExtra("question"));
            presenter.updateLocationText();
        } catch (NullPointerException e) {
        }
    }


    /**
     * Makes it possible for the user to add one more option by using a TextChangeListener by
     * showing a new text field, radio button and a "+" symbol. It also changes the symbol "+"
     * to "×" to show that an option is added and make it possible to delete it.
     *
     * @param view origin of clicked component
     */


    public final void addOption(final View view) {
        final int index = getIndex(view, options);

        options[index].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (index == 3) {
                    numbers[index].setText("×");
                    numbers[index].setVisibility(View.VISIBLE);
                    radioButtons[index].setVisibility(View.VISIBLE);
                } else if (!options[index + 1].isShown()) {
                    radioButtons[index].setVisibility(View.VISIBLE);
                    numbers[index].setText("×");
                    numbers[index].setVisibility(View.VISIBLE);
                    numbers[index + 1].setText("+");
                    numbers[index + 1].setVisibility(View.VISIBLE);
                    options[index + 1].setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public final void onBackPressed(View view) {
        presenter.backButtonPressed();
    }


    /**
     * Clears text field if the icon "×" is clicked
     */

    public final void removeOption(View view) {
        int index = getIndex(view, numbers);

        if (numbers[index].getText().toString().equals("×")) {
            options[index].getText().clear();
            shiftOptions(index);
        }
    }


    /**
     * Shifts options and make unused text fields invisible if options are removed.
     *
     * @param index
     */

    private void shiftOptions(int index) {
        for (int i = index; i < options.length - 1; i++) {
            options[i].setText(options[i + 1].getText());
            options[i + 1].getText().clear();
            if (radioButtons[i + 1].isChecked()) {
                radioButtons[i].setChecked(true);
                radioButtons[i + 1].setChecked(false);
            }
        }

        numbers[numbers.length - 1].setText("+");

        for (int j = 0; j < options.length - 1; j++) {
            if (options[j].getText().toString().equals("")) {
                numbers[j].setText("+");
                radioButtons[j].setChecked(false);
                radioButtons[j].setVisibility(View.INVISIBLE);
                if (options[j].getText().toString().equals("") && options[j + 1].getText().toString().equals("")) {
                    numbers[j + 1].setVisibility(View.INVISIBLE);
                    options[j + 1].setVisibility(View.INVISIBLE);
                    radioButtons[j + 1].setChecked(false);
                    radioButtons[j + 1].setVisibility(View.INVISIBLE);
                }
            }
        }
    }


    /**
     * Returns index of the right answer by looking which radio button is checked.
     *
     * @return index of the right answer
     */

    @Override
    public final int getAnswer() {
        for (int index = 0; index < radioButtons.length; index++) {
            if (radioButtons[index].isChecked()) {
                return index;
            }
        }
        throw new IllegalArgumentException(getResources().getString(R.string.no_correct_answer_ex));
    }

    @Override
    public final boolean hasAnswer() {
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isChecked() && !options[i].getText().toString().equals("")) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if all all parts of a questionText is completed. If it is completed the questionText is
     * saved and the user can type in the next questionText. If not, the user gets error messages
     * of which information is missing.
     *
     * @param view origin of clicked component
     */

    public final void addQuestion(View view) {
        presenter.addQuestion(true);
    }

    @Override
    public final String[] getOptions() {
        String[] options = new String[this.options.length];
        for (int i = 0; i < options.length; i++) {
            options[i] = this.options[i].getText().toString();
        }
        return options;
    }


    /**
     * Checks if all all parts of the questionText is completed. If it is completed the questionText is
     * saved and the user goes back to the view where he/she started to create the quiz. If the
     * questionText is not completed the user gets error messages of which information is missing.
     *
     * @param view origin of clicked component
     */

    public final void questionsDone(View view) {
        presenter.finishQuestions();
    }


    @Override
    public final void setLocationText(String text) {
        locationText.setText(text);
    }

    @Override
    public final void reset(int questionCounter) {
        questionNumber.setText(getResources().getString(R.string.question) + " " + questionCounter + ".");
        questionText.getText().clear();
        numbers[0].setText("+");
        latitude = 0;
        longitude = 0;

        for (int i = 0; i < options.length; i++) {
            options[i].getText().clear();
            radioButtons[i].setChecked(true);
        }

        for (int i = 1; i < numbers.length; i++) {
            options[i].setVisibility(View.INVISIBLE);
            numbers[i].setVisibility(View.INVISIBLE);
            radioButtons[i].setVisibility(View.INVISIBLE);
        }
    }


    /**
     * Shows map view for adding position of the questionText
     *
     * @param view origin of clicked component
     */

    public final void addPosition(View view) {
        Intent intent = new Intent(this, GetPositionActivity.class);
        startActivityForResult(intent, GET_POSITION_CODE);
    }

    private int getIndex(View object, View[] views) {
        for (int i = 0; i < views.length; i++) {
            if (views[i].equals(object)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Object not in array");
    }

    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_POSITION_CODE) {
            try {
                Location l = (Location) data.getExtras().get("result");
                latitude = l.getLatitude();
                longitude = l.getLongitude();
            } catch (NullPointerException e) {
                latitude = 0;
                longitude = 0;
            }
            presenter.updateLocationText();
        }
    }

    @Override
    public final void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            addOption(v);
        }
    }

    @Override
    public final String getQuestionTitle() {
        return questionText.getText().toString();
    }

    @Override
    public final void closeWithResult(List<Question> questions) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("questions", (ArrayList) questions);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void databaseComplete(String message) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public final void close() {
        finish();
    }

    @Override
    public final double getLatitude() {
        return latitude;
    }

    @Override
    public final double getLongitude() {
        return longitude;
    }

    public final int getQuestionID() {
        return questionID;
    }

    @Override
    public final void sendError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public final boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
            for (int i = 0; i < options.length - 1; i++) {
                if (v.equals(options[i])) {
                    options[i + 1].requestFocus();
                    return false;
                }
            }

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        return false;
    }

    @Override
    public final void onBackPressed() {
        presenter.backButtonPressed();
    }

    @Override
    public final void setOptions(String[] options) {
        for (int i = 0; i < options.length; i++) {
            if (!options[i].equals("")) {
                addOption(this.options[i]);
                this.options[i].setText(options[i]);
                this.options[i].setEnabled(true);

            }
        }
    }


    public void onDeletePressed(View view){
        presenter.removeQuestion();
    }

    public void setDeleteVisible(){
        deleteQuestion.setVisibility(View.VISIBLE);
    }

    @Override
    public final void setQuestionTitle(String questionTitle) {
        questionText.setText(questionTitle);
    }

    @Override
    public final void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public final void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public final void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    @Override
    public final void setAnswer(int answer) {
        radioButtons[answer].setChecked(true);
    }
}