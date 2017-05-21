package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IQuizDetails;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.QuizDifficulty;
import com.example.nightingale.qwalk.Model.QuizSetting;

/**
 * Created by Kraft on 2017-05-18.
 */

public class QuizDetailsPresenter {
    private IQuizDetails view;
    private Quiz quiz;

    public QuizDetailsPresenter(IQuizDetails view, Quiz quiz, Boolean editable) {
        this.view = view;
        this.quiz = quiz;

        view.setTitle(quiz.getTitle());
        view.setDescription(quiz.getDescription());
        view.setEditButtonEnabled(editable);
    }

    public void settingsChanged(QuizSetting[] setTrue, QuizSetting[] setFalse){
        for (QuizSetting qs: setTrue) {
            quiz.setSetting(qs, true);
        }

        for (QuizSetting qs: setFalse) {
            quiz.setSetting(qs, false);
        }
    }

    public void difficultyChanged(QuizDifficulty difficulty){
        quiz.setDifficulty(difficulty);
    }

    public void playPressed(){
        view.playQuiz(quiz);
    }

    public void editPressed(){
        view.editQuiz(quiz);
    }

    public void settingsPressed(){
        view.openSettings(quiz);
    }

    public void deletePressed() { }  //TODO

}
