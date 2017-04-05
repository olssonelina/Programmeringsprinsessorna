package com.example.nightingale.qwalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;



/**
 * Created by Nightingale on 2017-04-05.
 */

    public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /** Called when the user taps the Send button */
        public void guestButtonClicked(View view) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
    }

