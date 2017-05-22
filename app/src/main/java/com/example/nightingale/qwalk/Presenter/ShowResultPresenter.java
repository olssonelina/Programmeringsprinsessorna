package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IShowResult;
import com.example.nightingale.qwalk.Model.Quiz;

/**
 * Created by PiaLocal on 2017-05-10.
 */

public class ShowResultPresenter {

    private IShowResult view;
    private int[] playerAnswers;//byt ut mot player

    public ShowResultPresenter(IShowResult view, int[] playerAnswers, int[] aiAnswers, Quiz quiz, long time){ //byt ut "results" mot en player som innehåller resultat
        this.view=view;
        this.playerAnswers=playerAnswers;
        view.showRightAnswers(playerAnswers[0]);
       // view.showTotalAnswers(results[1]);
        //view.showTime(time/60,time%60);
    }
}
