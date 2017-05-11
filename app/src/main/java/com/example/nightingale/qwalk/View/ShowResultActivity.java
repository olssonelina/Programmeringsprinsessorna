package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.nightingale.qwalk.InterfaceView.IShowResultActivity;
import com.example.nightingale.qwalk.Presenter.ShowResultPresenter;
import com.example.nightingale.qwalk.R;

/**
 * Created by Elina Olsson on 2017-05-09.
 */

public class ShowResultActivity extends AppCompatActivity implements IShowResultActivity {

    private ShowResultPresenter presenter;

    private TextView rightView;
    private TextView totalView;
    private TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showresult);

        rightView = (TextView)findViewById(R.id.rightAnswers);
        totalView = (TextView)findViewById(R.id.total);
        timeView = (TextView)findViewById(R.id.time);

        Intent i = getIntent();
        int[] player = i.getIntArrayExtra("player"); //testrad byts ut när det finns en Actor som sparar resultat
        presenter = new ShowResultPresenter(this, player);
    }

    @Override
    public void showRightAnswers(int right) { this.rightView.setText(right + " rätt"); }

    @Override
    public void showTotalAnswers(int total) { this.totalView.setText("av " + total + " möjliga."); }

    @Override
    public void showTime(int min, int sec) { this.timeView.setText("Det tog " + min + " minuter och " + sec + " sekunder."); }
}
