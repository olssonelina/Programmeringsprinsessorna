package com.example.nightingale.qwalk.InterfaceView;

import com.example.nightingale.qwalk.Model.Tiebreaker;

/**
 * Created by Kraft on 2017-05-10.
 */

public interface ICreateTiebreaker {
    int getLowerBounds();
    int getHigherBounds();
    int getAnswer();
    String getQuestionTitle();
    void closeWithResult(Tiebreaker question);
    double getLatitude();
    double getLongitude();
    void sendError(String error);

}
