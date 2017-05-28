package com.example.nightingale.qwalk.Presenter.DetailedResults;

import com.example.nightingale.qwalk.Model.Quiz.Quiz;

/**
 * Created by PiaLocal on 2017-05-28.
 */

public class DetailedResultsPresenter {

    private IDetailedResults view;
    private Quiz quiz;
    private int[] answers;

    public DetailedResultsPresenter(IDetailedResults view, Quiz quiz, int[] answers){
        this.view = view;
        this.quiz=quiz;
        this.answers=answers;
        view.setListColors(isCorrect());
    }

    public boolean[] isCorrect(){
        boolean[] isCorrect=new boolean[answers.length];
        for (int i=0; i<answers.length;i++){
            isCorrect[i]=(answers[i]==quiz.getCorrectAnswers()[i]);
        }
        return isCorrect;
    }
}
