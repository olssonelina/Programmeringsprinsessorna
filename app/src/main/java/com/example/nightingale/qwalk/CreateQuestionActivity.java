package com.example.nightingale.qwalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elina Olsson on 2017-04-24.
 */

public class CreateQuestionActivity extends AppCompatActivity {

    //Välj olika namn på variabler och "ikoner"
    ArrayList<Question> questionsToSave = new ArrayList<Question>();

    TextView questionNumber;
    EditText question;
    EditText option1;       //Kolla upp så det blev rätt med variabler och textfält
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
    String questionOption1;     //Gör detta till en String array istället?
    String questionOption2;
    String questionOption3;
    String questionOption4;

    RadioButton[] radioButtons = new RadioButton[4];
    TextView[] numbers = new TextView[4];
    EditText[] options = new EditText[4];


    int correctAnswer;

    int questionCounter = 1;
    int addOptionCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquestion); //ändra namnet till rätt xml-fil
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

        /*option1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                numbers[addOptionCounter].setText("×");
                numbers[addOptionCounter].setVisibility(View.VISIBLE);
                if (addOptionCounter != 3) {
                    numbers[addOptionCounter + 1].setText("+");
                    numbers[addOptionCounter + 1].setVisibility(View.VISIBLE);
                    options[addOptionCounter + 1].setVisibility(View.VISIBLE);
                    radioButtons[addOptionCounter + 1].setVisibility(View.VISIBLE);
                    if(addOptionCounter == 1)
                        addOptionCounter++;
                    else if (addOptionCounter == 2)
                        addOptionCounter++;
                    else if (addOptionCounter == 3)
                        addOptionCounter++;
                }
            }
        });*/

        makeArrays();
        //option1.addTextChangedListener(new addListenerOnTextChange(this, option1));

    }

    public void makeArrays() {
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


    public void addOption(View view) {
        numbers[addOptionCounter].setText("×");
        numbers[addOptionCounter].setVisibility(View.VISIBLE);
        if (addOptionCounter < 3) {
            numbers[addOptionCounter + 1].setText("+");
            numbers[addOptionCounter + 1].setVisibility(View.VISIBLE);
            options[addOptionCounter + 1].setVisibility(View.VISIBLE);
            radioButtons[addOptionCounter + 1].setVisibility(View.VISIBLE);
            addOptionCounter++;
        }
    }

    public int correctAnswer() {
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isChecked()) {
                return i;
            }
        }
        return 77;
    }

    public void addQuestion(View view) {
        if (isQuestionComplete()) {
            saveQuestion();
            newQuestion();
        } else
            sendErrorMsg();
    }
    

    public void questionsDone(View view) {
        if (isQuestionComplete()) {
            saveQuestion();
        } else
            sendErrorMsg();
    }

    public boolean isQuestionComplete() {
        if (question.getText().toString().equals("") || option2.getText().toString().equals("") || !(radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked() || radioButton4.isChecked())) {
            return false;
        }
        return true;
    }

    public void sendErrorMsg() {
        String errorMsg;
        if (question.getText().toString().equals("")) {
            errorMsg = "Skriv en fråga";
        } else if (option2.getText().toString().equals("")) {
            errorMsg = "Du måste ha minst 2 alternativ";
        } else if (!(radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked() || radioButton4.isChecked())) {
            errorMsg = "Välj rätt svarsalternativ";
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

        for(int i = 0; i < options.length; i++) {
            options[i].getText().clear();
        }

        for(int i = 1; i < numbers.length; i++) {
            options[i].setVisibility(View.INVISIBLE);
            numbers[i].setVisibility(View.INVISIBLE);
            radioButtons[i].setVisibility(View.INVISIBLE);
        }

    }

    private void saveQuestion() {
        //Spara all input till strängar, bild och position
        //Skapa ny fråga genom att anropa konstruktorn i Question och skicka med titel, alternativ, rätt svar, bild och position
        questionTitle = question.getText().toString();
        questionOption1 = option1.getText().toString();
        questionOption2 = option2.getText().toString();
        questionOption3 = option3.getText().toString();
        questionOption4 = option4.getText().toString();
        correctAnswer = correctAnswer();

        Question question = new Question(questionTitle, questionOption1, questionOption2, questionOption3, questionOption4, correctAnswer);
        questionsToSave.add(question);
    }

    public void addPosition(View view) {
        Intent intent = new Intent(this, GetPositionActivity.class);
        startActivity(intent);
    }
}