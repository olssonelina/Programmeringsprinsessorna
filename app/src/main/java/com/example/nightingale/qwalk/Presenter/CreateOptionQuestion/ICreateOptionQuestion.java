package com.example.nightingale.qwalk.Presenter.CreateOptionQuestion;

import com.example.nightingale.qwalk.Model.Question.Question;

import java.util.List;

/**
 * Created by Elina Olsson on 2017-05-11.
 */

public interface ICreateOptionQuestion {
    int getAnswer();

    String getQuestionTitle();
    void setDeleteVisible();
    void closeWithResult(List<Question> questions);
    void databaseComplete(String message);
    void close();

    double getLatitude();

    double getLongitude();

    int getQuestionID();

    void sendError(String error);

    boolean hasAnswer();

    String[] getOptions();

    void reset(int questionCounter);

    void setQuestionID(int id);

    void setLocationText(String text);

    void setOptions(String[] options);

    void setQuestionTitle(String questionTitle);

    void setLatitude(double latitude);

    void setLongitude(double longitude);

    void setAnswer(int answer);
}
