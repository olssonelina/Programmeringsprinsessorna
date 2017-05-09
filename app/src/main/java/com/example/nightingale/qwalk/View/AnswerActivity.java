package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nightingale.qwalk.R;

/**
 * Created by Kraft on 2017-04-26.
 */

public class AnswerActivity extends AppCompatActivity {

    private Button[] optionButtons = new Button[4];
    private Button submitButton;
    private int answer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer); //ändra namnet till rätt xml-fil

        Intent i = getIntent();
        Bundle b = i.getExtras();

        ((TextView)findViewById(R.id.question)).setText(b.getString("questionTitle"));


        optionButtons[0] = (Button)findViewById(R.id.option1);
        optionButtons[1] = (Button)findViewById(R.id.option2);
        optionButtons[2] = (Button)findViewById(R.id.option3);
        optionButtons[3] = (Button)findViewById(R.id.option4);

        optionButtons[0].setText(b.getString("option1"));
        optionButtons[1].setText(b.getString("option2"));
        optionButtons[2].setText(b.getString("option3"));
        optionButtons[3].setText(b.getString("option4"));

        for(Button a: optionButtons){
            a.setBackgroundColor(Color.LTGRAY);
        }

        submitButton = (Button)findViewById(R.id.saveAnswer);
        submitButton.setEnabled(false);


        /*((Button)findViewById(R.id.option1)).setText(b.getString("option1"));
        ((Button)findViewById(R.id.option2)).setText(b.getString("option2"));
        ((Button)findViewById(R.id.option3)).setText(b.getString("option3"));
        ((Button)findViewById(R.id.option4)).setText(b.getString("option4"));*/

    }

    public void optionPressed(View view){


        for(Button b: optionButtons){
            b.setBackgroundColor(Color.LTGRAY);
        }


        Button b = (Button) view;
        b.setBackgroundColor(Color.WHITE);

        for (int i = 0; i < optionButtons.length; i++){
            if (optionButtons[i].equals(b)){
                answer = i;
                submitButton.setEnabled(true);
            }
        }



    }


    public void submitAnswer(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("answer", answer);
        setResult(GetPositionActivity.RESULT_OK, returnIntent);
        finish();
    }

    public void showResult(View view) {
        Intent intent = new Intent(this, ShowResultActivity.class);
        startActivity(intent);
    }
}