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

    public AnswerOptionPresenter(IAnswerOption view, OptionQuestion question, int questionIndex, int aiAnswer) {
        this.view = view;
        this.question = question;
        this.questionIndex = questionIndex;
        this.aiAnswer = aiAnswer;

        view.setTitle(question.getQuestionTitle());

        //TODO det borde vara en array från början
        String[] options = {question.getOption(0), question.getOption(1), question.getOption(2), question.getOption(3)};
        view.setOptions(options);


    }


    public void optionPressed(int index) {
        view.setCloseButtonEnabled(true);

        chosenAnswer = index;
    }


    public void submitClicked() {
        if (aiAnswer == -1 || view.getButtonText().equals("Stäng")) {
            view.closeWithResult(chosenAnswer, question);
        } else {
            view.setButtonText("Stäng");
            view.showBotAnswer(aiAnswer);
        }
    }
}
