package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Presenter.DetailedResults.DetailedResultsPresenter;
import com.example.nightingale.qwalk.Presenter.DetailedResults.IDetailedResults;
import com.example.nightingale.qwalk.R;

/**
 * Created by PiaLocal on 2017-05-26.
 */

public class DetailedResultsActivity extends AppCompatActivity implements IDetailedResults {
    private DetailedResultsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedresults);

        Intent i = getIntent();
        int[] answers = i.getIntArrayExtra("answers");
        Quiz quiz = i.getParcelableExtra("quiz");

        presenter = new DetailedResultsPresenter(this, quiz, answers);
    }

    public void onBackPressed(View view) {
        finish();
    }
}
