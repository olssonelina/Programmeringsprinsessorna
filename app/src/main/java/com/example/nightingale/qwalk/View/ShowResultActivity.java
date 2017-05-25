package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nightingale.qwalk.InterfaceView.IShowResult;
import com.example.nightingale.qwalk.Model.AI;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Presenter.ShowResultPresenter;
import com.example.nightingale.qwalk.R;

/**
 * Created by Elina Olsson on 2017-05-09.
 */

public class ShowResultActivity extends AppCompatActivity implements IShowResult {

    private ShowResultPresenter presenter;
    private boolean isPlayingAgainstBot=false;
    //private AI AI;

    private TextView rightView;
    private TextView totalView;
    private TextView timeView;
    private TextView monkeyRight;
    private TextView tieAnswer;
    private TextView playerTieAnswer;
    private TextView monkeyTieAnswer;
    private TextView result;
    private ImageView monkey;
    private ImageView timer;
    private ImageView tieBreaker;
    private ImageView winner;
    private Button newQuizButton;
    private Button detailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showresult);

        rightView = (TextView) findViewById(R.id.rightAnswers);
        totalView = (TextView) findViewById(R.id.total);
        timeView = (TextView) findViewById(R.id.time);
        timer = (ImageView) findViewById(R.id.timer);

        result = (TextView) findViewById(R.id.resultText);
        winner =(ImageView) findViewById(R.id.winnerIcon);

        tieAnswer= (TextView) findViewById(R.id.tieText1);
        tieAnswer.setVisibility(View.INVISIBLE);
        playerTieAnswer = (TextView) findViewById(R.id.tieText2);
        playerTieAnswer.setVisibility(View.INVISIBLE);
        monkeyTieAnswer = (TextView) findViewById(R.id.tieText3);
        monkeyTieAnswer.setVisibility(View.INVISIBLE);
        tieBreaker = (ImageView) findViewById(R.id.tieIcon);
        tieBreaker.setVisibility(View.INVISIBLE);


        monkeyRight = (TextView) findViewById(R.id.monkeyScore);
        monkey = (ImageView) findViewById(R.id.monkey);
        monkey.setVisibility(View.INVISIBLE);

        newQuizButton = (Button) findViewById(R.id.playNewButton);
        detailsButton = (Button) findViewById(R.id.detailedButton);

        Intent i = getIntent();
        long time = i.getLongExtra("time", 0);
        int[] playerAnswers = i.getIntArrayExtra("player");
        int[] aiAnswers = i.getIntArrayExtra("ai");
        if(aiAnswers!=null){isPlayingAgainstBot=true;}
        Quiz quiz = i.getParcelableExtra("quiz");

        presenter = new ShowResultPresenter(this, playerAnswers, aiAnswers, quiz, time);
    }

    public void showMonkeyResult() {
        if (isPlayingAgainstBot) {

        }
    }

    public void setMonkeyScore() {
        //monkeyResult.setText("" + AI.getScore(correctAnswers) + getResources().getString(R.string.rigth_answers));

        //monkeyTotal.setText(getResources().getString(R.string.of) +  + getResources().getString(R.string.possible));
    }

    public void onBackPressed(View view) {
        finish();
    }


    @Override
    public void showRightAnswers(int right) {
        this.rightView.setText(right + getResources().getString(R.string.rigth_answers));
    }

    @Override
    public void showTotalAnswers(int total) {
        this.totalView.setText(getResources().getString(R.string.of) + total + getResources().getString(R.string.possible));
    }

    @Override
    public void showTime(long min, long sec) {
        this.timeView.setText( min + getResources().getString(R.string.minutes_and) + sec + getResources().getString(R.string.seconds));
    }
}
