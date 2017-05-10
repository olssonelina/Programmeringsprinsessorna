package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IAnswerTiebreaker;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Tiebreaker;

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

    public void closePressed(){
        view.closeWithResult(chosenAnswer);
    }


    public void sliderChanged(int value){
        chosenAnswer = value + question.getLowerBounds();
        view.setRange(question.getLowerBounds(), question.getUpperBounds());
    }
}
