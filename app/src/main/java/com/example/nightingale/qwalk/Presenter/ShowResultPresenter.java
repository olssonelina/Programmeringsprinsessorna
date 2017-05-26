package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IShowResult;
import com.example.nightingale.qwalk.Model.Quiz;

/**
 * Created by PiaLocal on 2017-05-10.
 */

public class ShowResultPresenter {

    private IShowResult view;

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

    private boolean playerWins(int playerScore, int aiScore, int playerTie, int aiTie, int correctTie){
        if(playerScore>aiScore){
            return true;
        }else if(playerScore<aiScore || Math.abs(correctTie-playerTie)>Math.abs(correctTie-aiTie)){
            return false;
        }
        return true; //om det är precis lika på allt vinner spelaren
    }

}
