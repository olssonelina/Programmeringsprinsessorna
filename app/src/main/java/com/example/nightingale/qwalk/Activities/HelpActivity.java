package com.example.nightingale.qwalk.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.nightingale.qwalk.R;

/**
 * Created by Elina Olsson on 2017-05-12.
 */

public class HelpActivity extends AppCompatActivity {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }


    public final void onBackPressed(View view) {
        finish();
    }
}
