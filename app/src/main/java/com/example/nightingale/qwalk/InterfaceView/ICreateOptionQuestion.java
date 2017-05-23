package com.example.nightingale.qwalk.InterfaceView;

import com.example.nightingale.qwalk.Model.Question;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-05-11.
 */

public interface ICreateOptionQuestion {
    int getAnswer();

    String getQuestionTitle();

    void closeWithResult(ArrayList<Question> questions);

    double getLatitude();

    double getLongitude();

    void sendError(String error);

    boolean hasAnswer();

    String[] getOptions();

    void reset();

    void setLocationText(String text);

    void setOptions(String[] options);

    void setQuestionTitle(String questionTitle);

    void setLatitude(double latitude);

    void setLongitude(double longitude);

    void setAnswer(int answer);
}
