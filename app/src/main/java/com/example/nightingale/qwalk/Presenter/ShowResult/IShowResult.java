package com.example.nightingale.qwalk.Presenter.ShowResult;

/**
 * Created by PiaLocal on 2017-05-10.
 */

public interface IShowResult {

    /**
     *
     * Views the amount of OptionQuestions answered correctly by the player
     *
     * @param right
     */
    void showRightAnswers(int right);

    /**
     *
     * Views the total amount of option-questions of the quiz
     *
     * @param total
     */
    void showTotalAnswers(int total);

    /**
     *
     * Views time taken by the player to finish the quiz
     *
     * @param min
     * @param sec
     */
    void showTime(long min, long sec);

    /**
     *
     * Views the amount of OptionQuestions answered correctly by the computer-opponent
     *
     * @param monkeyRight
     */
    void showMonkeyResult(int monkeyRight);

    /**
     *
     * Views the correct answer and the players answer to a tiebreaker
     *
     * @param rightAnswer
     * @param playerAnswer
     */
    void showTieBreakerResult(int rightAnswer, int playerAnswer);

    /**
     *
     * Views the ccomputer-opponent's answer to a tiebreaker
     *
     * @param monkeyAnswer
     */
    void showMonkeyTieBreaker(int monkeyAnswer);

    /**
     *
     * Views the winner, either the player or the computer-opponent
     *
     * @param playerWins
     */
    void showCompetitionResult(boolean playerWins);

    /**
     * Goes back to the Qwalk menu
     */
    void openMenu();
}
