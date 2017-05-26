package com.example.nightingale.qwalk.Presenter.Maps;

import com.example.nightingale.qwalk.Model.QLocation;
import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Model.QwalkGame;


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

    public int getAiAnswer(Question question){
        return model.getAiAnswer(question);
    }

    public boolean hasAi(){
        return model.hasAi();
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
     * @param aiAnswers
     *
     */
    public void showResults(Quiz quiz, int[] playerAnswers, int[] aiAnswers, long quizTime){ view.showResults(quiz, playerAnswers, aiAnswers, quizTime ); }

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
    public void initializeAi(QLocation location){ view.initializeAi(location); }

    /**
     *
     * @param location
     */
    public void moveBot(QLocation location){ view.moveAi(location); }

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
