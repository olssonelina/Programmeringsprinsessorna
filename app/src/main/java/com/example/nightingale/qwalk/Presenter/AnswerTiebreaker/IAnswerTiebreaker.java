package com.example.nightingale.qwalk.Presenter.AnswerTiebreaker;

import com.example.nightingale.qwalk.Model.Question.Tiebreaker;

/**
 * Created by Kraft on 2017-05-10.
 */

public interface IAnswerTiebreaker {
    void closeWithResult(int value, Tiebreaker question);

    void setTitle(String title);

    void setRange(int from, int to);

    int getChoice(int from);
}
