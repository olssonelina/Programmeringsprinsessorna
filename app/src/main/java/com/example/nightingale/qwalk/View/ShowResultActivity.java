package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nightingale.qwalk.InterfaceView.IShowResult;
import com.example.nightingale.qwalk.Model.AI;
import com.example.nightingale.qwalk.Presenter.ShowResultPresenter;
import com.example.nightingale.qwalk.R;

/**
 * Created by Elina Olsson on 2017-05-09.
 */

public class ShowResultActivity extends AppCompatActivity implements IShowResult {

    private ShowResultPresenter presenter;
    private AI AI;

    ShowResultActivity(AI AI) {
        this.AI = AI;
    }

    private TextView rightView;
    private TextView totalView;
    private TextView timeView;
    private ImageView monkey;
    private TextView monkeyTitle;
    private TextView monkeyResult;
    private TextView monkeyTotal;
    private TextView monkeyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showresult);

        rightView = (TextView)findViewById(R.id.rightAnswers);
        totalView = (TextView)findViewById(R.id.total);
        timeView = (TextView)findViewById(R.id.time);

        monkey = (ImageView)findViewById(R.id.monkey);
        monkey.setImageResource(R.drawable.monkey);

        monkeyTitle = (TextView) findViewById(R.id.monkeysAnswer);
        monkeyResult = (TextView) findViewById(R.id.rightAnswersMonkey);
        monkeyTotal = (TextView) findViewById(R.id.totalMonkey);
        monkeyTime = (TextView) findViewById(R.id.monkeyTime);

        Intent i = getIntent();
        long time = i.getLongExtra("time", 0);
        int[] player = i.getIntArrayExtra("player"); //testrad byts ut när det finns en Actor som sparar resultat
        presenter = new ShowResultPresenter(this, player, time);
    }

    public void showMonkeyResult() {
        if(isPlayingAgainstBot()) {
            monkey.setVisibility(View.VISIBLE);
            monkeyTitle.setVisibility(View.VISIBLE);
            monkeyResult.setVisibility(View.VISIBLE);
            monkeyTotal.setVisibility(View.VISIBLE);
            monkeyTime.setVisibility(View.VISIBLE);
        }
    }

    public void setMonkeyScore() {
        monkeyResult.setText(AI.getScore() + " rätt");
        monkeyTotal.setText("Av " + AI.getNumberOfQuestions() + " möjliga");
    }

    public boolean isPlayingAgainstBot() {
        return true;
    }

    @Override
    public void showRightAnswers(int right) { this.rightView.setText(right + " rätt"); }

    @Override
    public void showTotalAnswers(int total) { this.totalView.setText("av " + total + " möjliga."); }

    @Override
    public void showTime(long min, long sec) { this.timeView.setText("Det tog " + min + " minuter och " + sec + " sekunder."); }
}
