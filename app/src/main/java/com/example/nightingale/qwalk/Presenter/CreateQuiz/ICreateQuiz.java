package com.example.nightingale.qwalk.Presenter.CreateQuiz;

import com.example.nightingale.qwalk.Model.Question.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.Model.Question.Tiebreaker;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public interface ICreateQuiz {
    void fillFields(Quiz editQuiz);
    void setListItems(String[] questionTitles);
    void close();
    void closeWithResult();
    void openCreateOptionQuestion(int questionCounter);
    void openCreateOptionQuestion(OptionQuestion question, int questionCounter);
    void openCreateTiebreakerQuestion();
    void openCreateTiebreakerQuestion(Tiebreaker tiebreaker);
    String getQuestionTitle();
    String getDescription();
    void sendError(String message);
}
