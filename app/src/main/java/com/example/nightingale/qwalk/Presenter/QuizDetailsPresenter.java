package com.example.nightingale.qwalk.Presenter;

import android.app.FragmentTransaction;
import android.util.Log;

import com.example.nightingale.qwalk.InterfaceView.IQuizDetails;
import com.example.nightingale.qwalk.Model.DatabaseHandler;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.QuizDifficulty;
import com.example.nightingale.qwalk.Model.QuizSetting;
import com.example.nightingale.qwalk.View.QuizDetailsActivity;

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

    public void deletePressed(QuizDetailsActivity view) {
        Log.d("QuizID", String.valueOf(quiz.getQuizID()));
        DatabaseHandler.deleteQuiz(quiz.getQuizID(), view);

    }  //TODO



}
