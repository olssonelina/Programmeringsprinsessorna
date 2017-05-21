package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IMaps;
import com.example.nightingale.qwalk.Model.Actor;
import com.example.nightingale.qwalk.Model.GameTimer;
import com.example.nightingale.qwalk.Model.QLocation;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.QwalkGame;

import java.util.ArrayList;

/**
 * Created by Kraft on 2017-05-12.
 */

public class MapsPresenter {

    private IMaps view;
    private QwalkGame model;


    // ------------ Methods used by the view ------------ \\

    /**
     *
     * @param view
     * @param quiz
     */
    public MapsPresenter(IMaps view, Quiz quiz) {
        this.view = view;
        this.model = new QwalkGame(this, quiz);
    }

    /**
     *
     * @param location
     */
    public void updateUserLocation(QLocation location){
        model.update(location);
    }

    public void mapIsReady(){
        model.startQuiz();
    }

    /**
     *
     */
    public void setAnswer(Question question, int answer){
        model.setAnswer(question, answer);
    }

    public void onCameraChanged(){
        model.updateArrow();
    }

    public void focusOnClosestQuestion(){
        focusOn(model.getClosestQuestion().getLocation());
    }


    // ------------ Methods used by the model ------------ \\
    // All of them only sends the request to the view.

    /**
     *
     * @param question
     */
    public void placeMarker(Question question){ view.placeMarker(question); }

    /**
     *
     * @param question
     */
    public void removeMarker(Question question){ view.removeMarker(question); }

    /**
     *
     * @param quiz
     * @param playerAnswers
     * @param botAnswers
     *
     */
    public void showResults(Quiz quiz, ArrayList<Integer> playerAnswers, ArrayList<Integer> botAnswers, long quizTime){ view.showResults(quiz, playerAnswers, botAnswers, quizTime ); }

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

    /**
     *
     * @param location
     */
    public void updateArrow(QLocation location){
        view.pointArrowTo(location);
    }

    /**
     *
     */
    public void hideArrow(){ view.hideArrow(); }

    /**
     *
     * @param location
     */
    public void initializeBot(QLocation location){ view.initializeBot(location); }

    /**
     *
     * @param location
     */
    public void moveBot(QLocation location){ view.moveBot(location); }

    /**
     *
     */
    public void close(){ view.close(); }

    /**
     *
     */
    public void focusOn(QLocation location){ view.focusOn(location);}


    public void setProgress(int current, int total) {
        view.setProgress(current, total);
    }

    public int getQuestionIndex(Question question){ return model.getQuestionIndex(question);}

    public void setShowClosestEnabled(boolean value) {
        view.setShowClosestEnabled(value);
    }



}
