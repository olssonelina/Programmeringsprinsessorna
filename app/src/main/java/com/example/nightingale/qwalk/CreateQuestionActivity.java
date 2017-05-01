package com.example.nightingale.qwalk;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Elina Olsson on 2017-04-24.
 */

public class CreateQuestionActivity extends AppCompatActivity {

    public static final int GET_POSITION_CODE = 97;

    //Välj olika namn på variabler och "ikoner"
    ArrayList<Question> questionsToSave = new ArrayList<Question>();

    RelativeLayout relativeLayout;
    LinearLayout linearLayout;

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

    private double latitude;
    private double longitude;


    int correctAnswer;

    int questionCounter = 1;
    int addOptionCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquestion); //ändra namnet till rätt xml-fil
        relativeLayout = (RelativeLayout) findViewById((R.id.realtiveLayout));
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

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

    public void testAddOption(final View view) {
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
                } else if (!options[index + 1].isShown()) {
                    numbers[index].setText("×");
                    numbers[index].setVisibility(View.VISIBLE);
                    numbers[index + 1].setText("+");
                    numbers[index + 1].setVisibility(View.VISIBLE);
                    options[index + 1].setVisibility(View.VISIBLE);
                    radioButtons[index + 1].setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    public void addOption(View view) {
        String id = view.getResources().getResourceEntryName(view.getId());
        String stringIndex = id.substring(6, 7);
        int index = Integer.parseInt(stringIndex) - 1;
        if (index == 3) {
            numbers[index].setText("×");
            numbers[index].setVisibility(View.VISIBLE);
        } else if (!options[index + 1].isShown()) {
            numbers[index].setText("×");
            numbers[index].setVisibility(View.VISIBLE);
            numbers[index + 1].setText("+");
            numbers[index + 1].setVisibility(View.VISIBLE);
            options[index + 1].setVisibility(View.VISIBLE);
            radioButtons[index + 1].setVisibility(View.VISIBLE);
        }
    }


    public void removeOption(View view) {
        String id = view.getResources().getResourceEntryName(view.getId());
        String stringIndex = id.substring(6);
        int index = Integer.parseInt(stringIndex) - 1;
        if (numbers[index].getText().toString().equals("×")) {
            options[index].getText().clear();
            moveOptions(index);
        }
    }


    public void moveOptions(int index) {
        for (int i = index; i < options.length - 1; i++) {
            options[i].setText(options[i + 1].getText());
            options[i + 1].getText().clear();
            numbers[numbers.length - 1].setText("+");
        }
        for (int j = 0; j < options.length - 1; j++) {
            if (options[j].getText().toString().equals("")) {
                numbers[j].setText("+");
                if (options[j].getText().toString().equals("") && options[j + 1].getText().toString().equals("")) {
                    numbers[j + 1].setVisibility(View.INVISIBLE);
                    options[j + 1].setVisibility(View.INVISIBLE);
                    radioButtons[j + 1].setVisibility(View.INVISIBLE);
                }
            }
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
            Intent intent = new Intent(this, CreateQuizActivity.class);
            startActivity(intent);
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
        number1.setText("+");

        for (int i = 0; i < options.length; i++) {
            options[i].getText().clear();
            radioButtons[i].setChecked(false);
        }

        for (int i = 1; i < numbers.length; i++) {
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
        question.setLocation(latitude, longitude);
        questionsToSave.add(question);
    }

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