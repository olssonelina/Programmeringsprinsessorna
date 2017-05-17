package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IMaps;
import com.example.nightingale.qwalk.Model.Actor;
import com.example.nightingale.qwalk.Model.QLocation;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.QwalkGame;

/**
 * Created by Kraft on 2017-05-12.
 */

public class MapsPresenter {
    private IMaps view;
    private QwalkGame model;

    /**
     *
     * @param view
     * @param quiz
     */
    public MapsPresenter(IMaps view, Quiz quiz) {
        this.view = view;
        this.model = new QwalkGame(this, quiz);

        view.checkLocationPermission();
    }

    /**
     *
     * @param question
     * @param visible
     */
    public void placeMarker(Question question, boolean visible){
        if (visible){
            view.placeMarker(question);
        }
        else {
            view.placeHiddenMarker(question);
        }
    }

    /**
     *
     * @param question
     */
    public void removeMarker(Question question){
        view.removeMarker(question);
    }

    /**
     *
     * @param location
     */
    public void updateUserLocation(QLocation location){
        model.update(location);
    }


    /**
     *
     * @param quiz
     * @param player
     * @param bot
     */
    public void showResults(Quiz quiz, Actor player, Actor bot){
        view.showResults(quiz, player, bot);
    }

    /**
     *
     * @param quiz
     * @param player
     */
    public void showResults(Quiz quiz, Actor player){
        view.showResults(quiz, player);
    }

    /**
     *
     * @param question
     */
    public void enableMarker(Question question){
        view.enableMarker(question);
    }

    /**
     *
     * @param location
     * @return
     */
    public boolean isOnScreen(QLocation location){
        return view.isOnScreen(location);
    }

    public void mapIsReady(){
        model.startQuiz();
    }

    /**
     *
     * @param location
     */
    public void updateArrow(QLocation location){
        view.pointArrowTo(location);
    }



}
