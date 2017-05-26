package com.example.nightingale.qwalk.Presenter.AnswerTiebreaker;

import com.example.nightingale.qwalk.Model.Question.Tiebreaker;

/**
 * Created by Kraft on 2017-05-10.
 */

public class AnswerTiebreakerPresenter {
    private IAnswerTiebreaker view;
    private Tiebreaker question;
    private int chosenAnswer;

    public AnswerTiebreakerPresenter(IAnswerTiebreaker view, Tiebreaker question){
        this.view = view;
        this.question = question;
        view.setRange(question.getLowerBounds(), question.getUpperBounds());
        view.setTitle(question.getQuestionTitle());
    }

    public final void closePressed(){
        view.closeWithResult(chosenAnswer, question);
    }


    public final void sliderChanged(int value){
        chosenAnswer = value + question.getLowerBounds();
        view.setRange(question.getLowerBounds(), question.getUpperBounds());
    }
}
