package com.example.nightingale.qwalk.View;

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
    int[] player={1,2,3}; //Kommer att vara en Actor

    private TextView rightView;
    private TextView totalView;
    private TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showresult);
        //player = getParent().getActor; Vet inte om detta funkar. Testar senare

        rightView = (TextView)findViewById(R.id.rightAnswers);
        totalView = (TextView)findViewById(R.id.total);
        timeView = (TextView)findViewById(R.id.time);

        int[] player={1,2,345678}; //Kommer att vara en Actor (som hämtas från map?)
        presenter = new ShowResultPresenter(this, player);
    }

    @Override
    public void showRightAnswers(int right) { this.rightView.setText(right + "rätt"); }

    @Override
    public void showTotalAnswers(int total) { this.totalView.setText("av" + total + "möjliga."); }

    @Override
    public void showTime(int min, int sec) { this.timeView.setText("Det tog" + min + "minuter och" + sec + "sekunder."); }
}
