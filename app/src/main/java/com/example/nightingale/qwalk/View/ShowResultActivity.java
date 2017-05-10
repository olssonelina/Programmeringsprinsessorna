package com.example.nightingale.qwalk.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.nightingale.qwalk.InterfaceView.IShowResultActivity;
import com.example.nightingale.qwalk.R;

/**
 * Created by Elina Olsson on 2017-05-09.
 */

public class ShowResultActivity extends AppCompatActivity implements IShowResultActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showresult);
    }
}
