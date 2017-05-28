package com.example.nightingale.qwalk.Presenter.QuizDetails;

import android.util.Log;

import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Model.Quiz.QuizDifficulty;
import com.example.nightingale.qwalk.Model.Quiz.QuizSetting;

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
        view.setEditable(editable);

    }

    public final void settingsChanged(QuizSetting[] setTrue, QuizSetting[] setFalse){
        for (QuizSetting qs: setTrue) {
            quiz.setSetting(qs, true);
        }

        for (QuizSetting qs: setFalse) {
            quiz.setSetting(qs, false);
        }
    }

    public final void difficultyChanged(QuizDifficulty difficulty){
        quiz.setDifficulty(difficulty);
    }

    public final void playPressed(){
        view.playQuiz(quiz);
    }

    public final void editPressed(){
        view.editQuiz(quiz);
    }

    public final void settingsPressed(){
        view.openSettings(quiz);
    }

    public final void deletePressed() {

        Log.d("QuizID", String.valueOf(quiz.getQuizID()));
        DatabaseHandler.deleteQuiz(quiz.getQuizID());

        view.closeWithResult(true);

    }



}
