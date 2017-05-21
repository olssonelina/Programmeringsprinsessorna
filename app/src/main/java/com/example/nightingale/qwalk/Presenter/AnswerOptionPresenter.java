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

    public AnswerOptionPresenter(IAnswerOption view, OptionQuestion question) {
        this.view = view;
        this.question = question;
        view.setTitle(question.getQuestionTitle());

        //TODO det borde vara en array från början
        String[] options = {question.getOption(0), question.getOption(1), question.getOption(2), question.getOption(3)};
        view.setOptions(options);

        for (int i = 0; i < options.length; i++) {
            view.setOptionColour(i, false);
        }
    }

    public AnswerOptionPresenter(IAnswerOption view, OptionQuestion question, int aiAnswer){
        this(view, question);
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
