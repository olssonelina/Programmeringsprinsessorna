package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IShowResult;

/**
 * Created by PiaLocal on 2017-05-10.
 */

public class ShowResultPresenter {

    private IShowResult view;
    private int[] results;//byt ut mot player

    public ShowResultPresenter(IShowResult view, int[] results){ //byt ut "results" mot en player som innehåller resultat
        this.view=view;
        this.results=results;
        view.showRightAnswers(results[0]);
        view.showTotalAnswers(results[1]);
        int totalsec=results[2]/1000;
        view.showTime(totalsec/60,totalsec%60);
    }
}
