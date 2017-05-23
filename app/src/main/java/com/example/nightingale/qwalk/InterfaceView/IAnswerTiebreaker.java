package com.example.nightingale.qwalk.InterfaceView;

import com.example.nightingale.qwalk.Model.Tiebreaker;

/**
 * Created by Kraft on 2017-05-10.
 */

public interface IAnswerTiebreaker {
    void closeWithResult(int value, Tiebreaker question);

    void setTitle(String title);

    void setRange(int from, int to);

    int getChoice(int from);
}
