package com.example.nightingale.qwalk.Presenter;

import android.view.View;

import com.example.nightingale.qwalk.InterfaceView.IAnswerOption;
import com.example.nightingale.qwalk.Model.AI;
import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.R;

/**
 * Created by Kraft on 2017-05-09.
 */

public class AnswerOptionPresenter {

    private IAnswerOption view;
    private int chosenAnswer = 0;
    private OptionQuestion question;
    private int aiAnswer = -1;
    private int questionIndex;

    public AnswerOptionPresenter(IAnswerOption view, OptionQuestion question, int questionIndex) {
        this.view = view;
        this.question = question;
        this.questionIndex=questionIndex;

        view.setTitle(question.getQuestionTitle());

        //TODO det borde vara en array från början
        String[] options = {question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4()};
        view.setOptions(options);

        for (int i = 0; i < options.length; i++) {
            view.setOptionColour(i, false);
        }
    }

    public AnswerOptionPresenter(IAnswerOption view, OptionQuestion question, int questionIndex, int aiAnswer){
        this(view, question, questionIndex);
        this.aiAnswer = aiAnswer;
    }



    public void optionPressed(int index) {
        view.setCloseButtonEnabled(true);
        for (int i = 0; i < 4; i++) { //TODO detta kan inte vara en 4, samma problem som innan
            view.setOptionColour(i, i == index);
        }
        chosenAnswer = index;
    }


    public void submitClicked() {
        if (view.getButtonText().equals("Svara")) {
            view.setButtonText();
            if (aiAnswer != -1) {
                view.showBotAnswer(aiAnswer);
            }
        } else {
            view.closeWithResult(chosenAnswer, question);
        }
    }
}
