package com.example.nightingale.qwalk.Presenter.ShowResult;

import com.example.nightingale.qwalk.Model.Quiz.Quiz;

/**
 * Created by PiaLocal on 2017-05-10.
 */

public interface IShowResult {
    void showRightAnswers(int right);

    void showTotalAnswers(int total);

    void showTime(long min, long sec);

    void showMonkeyResult(int monkeyRight);

    void showTieBreakerResult(int rightAnswer, int playerAnswer);

    void showMonkeyTieBreaker(int monkeyAnswer);

    void showCompetitionResult(boolean playerWins);

    void openMenu();

    void openDetailed(Quiz quiz, int[]answers);
}
