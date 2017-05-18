package com.example.nightingale.qwalk.View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.nightingale.qwalk.R;

/**
 * Created by Elina Olsson on 2017-05-18.
 */

public class QuizDetailsActivity extends AppCompatActivity {


    Button play;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizdetails);

        play = (Button) findViewById(R.id.play);
        play.setBackgroundResource(R.drawable.play);

        edit = (Button) findViewById(R.id.edit);
        edit.setBackgroundResource(R.drawable.pen);
    }

}
