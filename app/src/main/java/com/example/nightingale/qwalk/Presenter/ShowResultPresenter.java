package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IShowResultActivity;

/**
 * Created by PiaLocal on 2017-05-10.
 */

public class ShowResultPresenter {

    private IShowResultActivity view;
    private int[] results;//byt ut mot player

    public ShowResultPresenter(IShowResultActivity view, int[] results){ //byt ut "results" mot en player som innehåller resultat
        this.view=view;
        this.results=results;
        view.showRightAnswers(results[0]);
        view.showTotalAnswers(results[1]);
        int totalsec=results[2]/1000;
        view.showTime(totalsec/60,totalsec%60);
    }
}
