package com.example.nightingale.qwalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Elina Olsson on 2017-04-24.
 */

public class QuestionActivity extends AppCompatActivity {

    TextView questionX;
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

    int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question); //ändra namnet till rätt xml-fil
        question = (EditText) findViewById(R.id.question);
        option1 = (EditText) findViewById(R.id.option1);
        option2 = (EditText) findViewById(R.id.option2);
        option3 = (EditText) findViewById(R.id.option3);
        option4 = (EditText) findViewById(R.id.option4);
        number1 = (TextView) findViewById(R.id.number1);
        number2 = (TextView) findViewById(R.id.number2);
        number3 = (TextView) findViewById(R.id.number3);
        number4 = (TextView) findViewById(R.id.number4);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);

        questionX = (TextView) findViewById(R.id.questionX);
    }

    /*Hur gör jag detta till en generell metod?*/
    public void clearText(View v, EditText textField) {
        textField.getText().clear();
    }

    public void clearQuestion(View view) {
        question.getText().clear();
    }

    public void clearOption1(View view) {
        option1.getText().clear();
        number1.setText("1.");
        number2.setVisibility(View.VISIBLE);
        number2.setText("+");
        option2.setVisibility(View.VISIBLE);
        option2.setText("");
        radioButton2.setVisibility(View.VISIBLE);
    }

    /*Hur gör jag detta till en generell metod?*/
    public void radioButtonCheck(View view) {
        if (radioButton2.isChecked() || radioButton3.isChecked() || radioButton4.isChecked())
            radioButton2.setChecked(false);
        radioButton3.setChecked(false);
        radioButton4.setChecked(false);
    }

    /*Ska kolla om användaren har markerat det rätta svaret, spara frågan och gå till nästa fråga*/
    public void addQuestion(View view) {
        if (!(radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked() || radioButton4.isChecked())) {
            Toast toast = Toast.makeText(getApplicationContext(), "Markera rätt svar", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 220);
            toast.show();
        } else {
            saveQuestion();
            newQuestion();
        }

    }

    private void newQuestion() {
        counter++;
        questionX.setText("Fråga " + counter + ".");
        question.getText().clear();
        option1.getText().clear();
        option2.getText().clear();
        option3.getText().clear();
        option4.getText().clear();
    }

    /* Denna metod ska spara information till klassen Question */
    private void saveQuestion() {

    }
}
