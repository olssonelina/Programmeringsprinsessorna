package com.example.nightingale.qwalk.Presenter.QuizDetails;

import com.example.nightingale.qwalk.Model.Quiz.Quiz;

/**
 * Created by Kraft on 2017-05-18.
 */

public interface IQuizDetails {
    void playQuiz(Quiz quiz);

    void setTitle(String title);

    void setDescription(String description);

    void editQuiz(Quiz quiz);

    void openSettings(Quiz quiz);

    void setEditable(boolean value);

    void closeWithResult(boolean shouldMenuUpdate);

}
