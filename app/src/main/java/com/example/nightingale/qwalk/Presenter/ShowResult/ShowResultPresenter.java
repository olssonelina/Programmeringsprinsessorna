package com.example.nightingale.qwalk.Presenter.ShowResult;

import com.example.nightingale.qwalk.Model.Quiz.Quiz;

/**
 * Created by PiaLocal on 2017-05-10.
 */

public class ShowResultPresenter {

    private IShowResult view;

    /**
     *
     * Creates a ShowResultPresenter which performs the calculations for ShowResult view
     *
     * @param view The activity
     * @param playerAnswers The answers player has given to the questions of the quiz
     * @param aiAnswers The answers computer-opponent has given to the questions of the quiz
     * @param quiz The quiz played
     * @param time The time taken to finish the quiz
     */
    public ShowResultPresenter(IShowResult view, int[] playerAnswers, int[] aiAnswers, Quiz quiz, long time){ //byt ut "results" mot en player som innehåller resultat
        this.view=view;
        view.showRightAnswers(calculateScore(quiz.getCorrectAnswers(), playerAnswers, quiz.hasTieBreaker()));
        if(quiz.hasTieBreaker()){
            view.showTotalAnswers(quiz.size()-1);
            view.showTieBreakerResult(quiz.getCorrectAnswers()[quiz.size()-1], playerAnswers[playerAnswers.length-1]);
        }else{
            view.showTotalAnswers(quiz.size());
        }
        view.showTime(time/60,time%60);
        if(aiAnswers!=null){
            view.showMonkeyResult(calculateScore(quiz.getCorrectAnswers(), aiAnswers, quiz.hasTieBreaker()));
            view.showCompetitionResult(playerWins(calculateScore(quiz.getCorrectAnswers(), playerAnswers, true), calculateScore(quiz.getCorrectAnswers(),aiAnswers,true), playerAnswers[playerAnswers.length-1], aiAnswers[aiAnswers.length-1],quiz.getCorrectAnswers()[quiz.size()-1]));
            if(quiz.hasTieBreaker()){
                view.showMonkeyTieBreaker(aiAnswers[aiAnswers.length-1]);
            }
        }
    }

    /**
     * Calculates the score of some object
     *
     * @param correctAnswers the correct answers to every question in the quiz
     * @param answers the answers from the Player or AI
     * @return returns the score
     */
    private int calculateScore(int[] correctAnswers, int[] answers, boolean tiebreaker){
        int correctCount = 0;
        int numberOfOptionQuestions=correctAnswers.length;
        if(tiebreaker){numberOfOptionQuestions--;}   //för att poäng för optionquestions räknas separat från tiebreaker.
        for (int i = 0; i < numberOfOptionQuestions; i++) {
            if (correctAnswers[i] == answers[i]) {
                correctCount++;
            }
        }

        return correctCount;
    }

    /**
     *
     * Determines wether or not the player wins over the computer-opponent
     *
     * @param playerScore Amount of questions answered correctly by the player
     * @param aiScore Amount of questions answered correctly by the computer-opponent
     * @param playerTie Player's answer to the tiebreaker
     * @param aiTie Computer-opponent's answer to the tiebreaker
     * @param correctTie correct answer ti the tiebreaker
     * @return true if the player is winner, false if the computer-opponent is winner
     */
    private boolean playerWins(int playerScore, int aiScore, int playerTie, int aiTie, int correctTie){
        if(playerScore>aiScore){
            return true;
        }else if(playerScore<aiScore || Math.abs(correctTie-playerTie)>Math.abs(correctTie-aiTie)){
            return false;
        }
        return true; //om det är precis lika på allt vinner spelaren
    }

    /**
     * Calls for the activity to return to the menu
     */
    public final void playNewPressed(){
        view.openMenu();
    }

}
