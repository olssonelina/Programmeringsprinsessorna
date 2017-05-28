package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nightingale.qwalk.Presenter.ShowResult.IShowResult;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Presenter.ShowResult.ShowResultPresenter;
import com.example.nightingale.qwalk.R;

/**
 * Created by Elina Olsson on 2017-05-09.
 */

public class ShowResultActivity extends AppCompatActivity implements IShowResult {

    private ShowResultPresenter presenter;
    //private AI AI;

    private TextView rightView;
    private TextView totalView;
    private TextView timeView;
    private TextView monkeyScore;
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
    protected final void onCreate(Bundle savedInstanceState) {
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


        monkeyScore = (TextView) findViewById(R.id.monkeyScore);
        monkeyScore.setVisibility(View.INVISIBLE);
        monkey = (ImageView) findViewById(R.id.monkey);
        monkey.setVisibility(View.INVISIBLE);

        newQuizButton = (Button) findViewById(R.id.playNewButton);
        detailsButton = (Button) findViewById(R.id.detailedButton);

        Intent i = getIntent();
        long time = i.getLongExtra("time", 0);
        int[] playerAnswers = i.getIntArrayExtra("player");
        int[] aiAnswers = i.getIntArrayExtra("ai");
        Quiz quiz = i.getParcelableExtra("quiz");

        presenter = new ShowResultPresenter(this, playerAnswers, aiAnswers, quiz, time);
    }

    @Override
    public final void showMonkeyResult(int monkeyRight) {
        monkey.setVisibility(View.VISIBLE);
        monkeyScore.setVisibility(View.VISIBLE);
        monkeyScore.setText(getResources().getString(R.string.monkey_got)+" "+monkeyRight+" "+getResources().getString(R.string.rigth_answers));
    }

    @Override
    public final void showTieBreakerResult(int rightAnswer, int playerAnswer){
        tieBreaker.setVisibility(View.VISIBLE);
        tieAnswer.setVisibility(View.VISIBLE);
        tieAnswer.setText(getResources().getString(R.string.right_answer_to_tiebreaker)+" "+rightAnswer);
        playerTieAnswer.setVisibility(View.VISIBLE);
        playerTieAnswer.setText(getResources().getString(R.string.your_answer)+" "+playerAnswer);
    }

    @Override
    public final void showMonkeyTieBreaker(int monkeyAnswer){
        monkeyTieAnswer.setVisibility(View.VISIBLE);
        monkeyTieAnswer.setText(getResources().getString(R.string.monkeys_answer)+" "+monkeyAnswer);
    }

    @Override
    public final void showCompetitionResult(boolean playerWins){
        if(playerWins){
            result.setText(getResources().getString(R.string.you_won));
        } else{
            result.setText(getResources().getString(R.string.monkey_won));
            winner.setImageResource(R.drawable.monkey);
        }
    }

    public final void onBackPressed(View view) {
        finish();
    }


    @Override
    public final void showRightAnswers(int right) {
        this.rightView.setText(right +" "+ getResources().getString(R.string.rigth_answers));
    }

    @Override
    public final void showTotalAnswers(int total) {
        this.totalView.setText(getResources().getString(R.string.of) +" "+ total +" "+ getResources().getString(R.string.possible));
    }

    @Override
    public final void showTime(long min, long sec) {
        this.timeView.setText( min +" "+ getResources().getString(R.string.minutes_and) +" "+ sec +" "+ getResources().getString(R.string.seconds));
    }

    public final void playNewButtonPressed(View view){presenter.playNewPressed();}

    public void detailedButtonPressed(View view){presenter.detailedButtonPressed();}

    @Override
    public final void openMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void openDetailed(Quiz quiz, int[] answers){
        Intent intent = new Intent(this, DetailedResultsActivity.class);
        intent.putExtra("quiz", quiz);
        intent.putExtra("answers", answers);
        startActivity(intent);
    }
}
