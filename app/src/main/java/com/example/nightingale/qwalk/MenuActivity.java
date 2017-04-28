package com.example.nightingale.qwalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Kraft on 2017-04-28.
 */

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); //ändra namnet till rätt xml-fil
    }


    public void playButtonPressed(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void createButtonPressed(View view){
        Intent intent = new Intent(this, CreateQuizActivity.class);
        startActivity(intent);
    }
}
