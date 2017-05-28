package com.example.nightingale.qwalk.Presenter.ShowResult;

import com.example.nightingale.qwalk.Model.Quiz.Quiz;

/**
 * Created by PiaLocal on 2017-05-10.
 */

public class ShowResultPresenter {

    private IShowResult view;
    private Quiz quiz;
    private int[] playerAnswers;

    public ShowResultPresenter(IShowResult view, int[] playerAnswers, int[] aiAnswers, Quiz quiz, long time){ //byt ut "results" mot en player som innehåller resultat
        this.view=view;
        this.quiz=quiz;
        this.playerAnswers=playerAnswers;
        view.showRightAnswers(calculateScore(playerAnswers));
        if(quiz.hasTieBreaker()){
            view.showTotalAnswers(quiz.size()-1);
            view.showTieBreakerResult(quiz.getCorrectAnswers()[quiz.size()-1], playerAnswers[playerAnswers.length-1]);
        }else{
            view.showTotalAnswers(quiz.size());
        }
        view.showTime(time/60,time%60);
        if(aiAnswers!=null){
            view.showMonkeyResult(calculateScore(aiAnswers));
            view.showCompetitionResult(playerWins(calculateScore(playerAnswers), calculateScore(aiAnswers), playerAnswers[playerAnswers.length-1], aiAnswers[aiAnswers.length-1],quiz.getCorrectAnswers()[quiz.size()-1]));
            if(quiz.hasTieBreaker()){
                view.showMonkeyTieBreaker(aiAnswers[aiAnswers.length-1]);
            }
        }
    }

    /**
     * Calculates the score of some object
     *
     * @param answers the answers from the Player or AI
     * @return returns the score
     */
    private int calculateScore(int[] answers){
        int correctCount = 0;
        int numberOfOptionQuestions=quiz.getCorrectAnswers().length;
        if(quiz.hasTieBreaker()){numberOfOptionQuestions--;}   //för att poäng för optionquestions räknas separat från tiebreaker.
        for (int i = 0; i < numberOfOptionQuestions; i++) {
            if (quiz.getCorrectAnswers()[i] == answers[i]) {
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

    public final void playNewPressed(){
        view.openMenu();
    }

    public void detailedButtonPressed(){view.openDetailed(quiz, playerAnswers);}

}
