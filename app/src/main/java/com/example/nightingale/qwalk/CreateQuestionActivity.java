package com.example.nightingale.qwalk;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-04-24.
 */

public class CreateQuestionActivity extends AppCompatActivity {

    public static final int GET_POSITION_CODE = 97;

    ArrayList<OptionQuestion> questionsToSave = new ArrayList<OptionQuestion>();


    TextView questionNumber;
    EditText question;
    EditText option1;
    EditText option2;
    EditText option3;
    EditText option4;
    TextView number1;
    TextView number2;
    TextView number3;
    TextView number4;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;

    String questionTitle;
    String questionOption1;
    String questionOption2;
    String questionOption3;
    String questionOption4;

    RadioButton[] radioButtons = new RadioButton[4];
    TextView[] numbers = new TextView[4];
    EditText[] options = new EditText[4];

    private double latitude = 0;
    private double longitude = 0;

    int correctAnswer;

    int questionCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquestion);

        question = (EditText) findViewById(R.id.questionField);

        option1 = (EditText) findViewById(R.id.option1Field);
        option2 = (EditText) findViewById(R.id.option2Field);
        option3 = (EditText) findViewById(R.id.option3Field);
        option4 = (EditText) findViewById(R.id.option4Field);

        number1 = (TextView) findViewById(R.id.number1);
        number2 = (TextView) findViewById(R.id.number2);
        number3 = (TextView) findViewById(R.id.number3);
        number4 = (TextView) findViewById(R.id.number4);

        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);

        questionNumber = (TextView) findViewById(R.id.questionX);

        fillArrays();

        for (int i = 0; i < options.length; i++) {
            options[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        addOption(v);
                    }
                }
            });

            final int count = i;

            options[i].setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        if (count < options.length - 1) {
                            options[count + 1].requestFocus();
                            //Toast.makeText(getApplicationContext(), "Request Focus", Toast.LENGTH_LONG).show();
                        } else if (count == options.length - 1) {
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            //inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        }
                    }
                    return false;
                }
            });

        }
    }

    public void fillArrays() {
        radioButtons[0] = radioButton1;
        radioButtons[1] = radioButton2;
        radioButtons[2] = radioButton3;
        radioButtons[3] = radioButton4;

        numbers[0] = number1;
        numbers[1] = number2;
        numbers[2] = number3;
        numbers[3] = number4;

        options[0] = option1;
        options[1] = option2;
        options[2] = option3;
        options[3] = option4;
    }



    /**
     * Makes it possible for the user to add one more option by using a TextChangeListener by
     * showing a new text field, radio button and a "+" symbol. It also changes the symbol "+"
     * to "×" to show that an option is added and make it possible to delete it.
     *
     * @param view origin of clicked component
     */

    public void addOption(final View view) {

        //Get index for current option in the option arrays by looking into the view's id
        String id = view.getResources().getResourceEntryName(view.getId());
        String stringIndex = id.substring(6, 7);
        final int index = Integer.parseInt(stringIndex) - 1;

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


    /**
     * Clears text field if the icon "×" is clicked
     */

    public void removeOption(View view) {
        //Get index for current option in the option array by looking into the view's id
        String id = view.getResources().getResourceEntryName(view.getId());
        String stringIndex = id.substring(6);
        int index = Integer.parseInt(stringIndex) - 1;

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

    public void shiftOptions(int index) {
        for (int i = index; i < options.length - 1; i++) {
            options[i].setText(options[i + 1].getText());
            options[i + 1].getText().clear();
            numbers[numbers.length - 1].setText("+");
            if(radioButtons[i + 1].isChecked()) {
                radioButtons[i].setChecked(true);
                radioButtons[i + 1].setChecked(false);
            }

        }
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

    public int correctAnswer() {
        for (int index = 0; index < radioButtons.length; index++) {
            if (radioButtons[index].isChecked()) {
                return index;
            }
        }
        return 77;          //is never going to reach this
    }


    /**
     * Checks if all all parts of a question is completed. If it is completed the question is
     * saved and the user can type in the next question. If not, the user gets error messages
     * of which information is missing.
     *
     * @param view origin of clicked component
     */

    public void addQuestion(View view) {
        if (isQuestionComplete()) {
            saveQuestion();
            newQuestion();
        } else
            sendErrorMsg();
    }

    /**
     * Checks if all all parts of the question is completed. If it is completed the question is
     * saved and the user goes back to the view where he/she started to create the quiz. If the
     * question is not completed the user gets error messages of which information is missing.
     *
     * @param view origin of clicked component
     */

    public void questionsDone(View view) {
        if (isQuestionComplete()) {
            saveQuestion();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("questions", questionsToSave);
            setResult(GetPositionActivity.RESULT_OK, returnIntent);
            finish();
        } else
            sendErrorMsg();
    }

    private boolean isQuestionComplete() {
        if (question.getText().toString().equals("") || option2.getText().toString().equals("") || !(radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked() || radioButton4.isChecked()) || latitude == 0 || longitude == 0) {
            return false;
        }
        return true;
    }

    private void sendErrorMsg() {
        String errorMsg;
        if (question.getText().toString().equals("")) {
            errorMsg = "Skriv en fråga";
        } else if (option2.getText().toString().equals("")) {
            errorMsg = "Du måste ha minst 2 alternativ";
        } else if (!(radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked() || radioButton4.isChecked())) {
            errorMsg = "Välj rätt svarsalternativ";
        } else if (latitude == 0 || longitude == 0) {
            errorMsg = "Välj position";
        } else {
            errorMsg = "";
        }

        Toast toast = Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 160);
        toast.show();
    }

    private void newQuestion() {
        questionCounter++;
        questionNumber.setText("Fråga " + questionCounter + ".");
        question.getText().clear();
        number1.setText("+");

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

    private void saveQuestion() {
        //Spara all input till strängar, bild och position
        //Skapa ny fråga genom att anropa konstruktorn i OptionQuestion och skicka med titel, alternativ, rätt svar, bild och position
        questionTitle = question.getText().toString();
        questionOption1 = option1.getText().toString();
        questionOption2 = option2.getText().toString();
        questionOption3 = option3.getText().toString();
        questionOption4 = option4.getText().toString();
        correctAnswer = correctAnswer();

        OptionQuestion question = new OptionQuestion(questionTitle, questionOption1, questionOption2, questionOption3, questionOption4, correctAnswer, latitude, longitude);
        OptionQuestion.questionsToSend.add(question);
        questionsToSave.add(question); //TODO lite fult att skicka frågan till två ställen
    }

    /**
     * Shows map view for adding position of the question
     *
     * @param view origin of clicked component
     */

    public void addPosition(View view) {
        Intent intent = new Intent(this, GetPositionActivity.class);
        startActivityForResult(intent, GET_POSITION_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_POSITION_CODE) {
            Location l = (Location) data.getExtras().get("result");
            latitude = l.getLatitude();
            longitude = l.getLongitude();
        }
    }
}