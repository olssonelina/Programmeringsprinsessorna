package com.example.nightingale.qwalk.InterfaceView;

/**
 * Created by Kraft on 2017-05-10.
 */

public interface IAnswerTiebreaker {
    void closeWithResult(int value);
    void setTitle(String title);
    void setRange(int from, int to);
    int getChoice(int from);
}
