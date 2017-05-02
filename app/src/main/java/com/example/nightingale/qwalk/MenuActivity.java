package com.example.nightingale.qwalk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraft on 2017-04-28.
 */

public class MenuActivity extends AppCompatActivity {

    private List<Quiz> quizzes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); //ändra namnet till rätt xml-fil
        loadQuizzes();
    }

    private void loadQuizzes(){
        quizzes.add(StandardQuizzes.getChalmersQuiz());
        //TODO Kevin, här kanske du kan lägga till från databasen ?. Eventuellt fler standardquizzes med
    }

    private void play(Quiz quiz){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("quiz", quiz);
        startActivity(intent);
    }
    private void play(int index){
        play(quizzes.get(index));
    }


    public void playButtonPressed(View view){
        play(0);
    }

    public void createButtonPressed(View view){
        Intent intent = new Intent(this, CreateQuizActivity.class);
        startActivity(intent);
    }
}
