package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IAnswerOption;
import com.example.nightingale.qwalk.Model.OptionQuestion;

/**
 * Created by Kraft on 2017-05-09.
 */

public class AnswerOptionPresenter {

    private IAnswerOption view;
    private int chosenAnswer = 0;
    private OptionQuestion question;

    public AnswerOptionPresenter(IAnswerOption view, OptionQuestion question) {
        this.view = view;
        this.question = question;

        view.setTitle(question.getQuestionTitle());

        //TODO det borde vara en array från början
        String[] options = {question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4()};
        view.setOptions(options);

        for (int i = 0; i < options.length; i++) {
            view.setOptionColour(i, false);
        }
    }

    public void optionPressed(int index) {
        view.setCloseButtonEnabled(true);
        for (int i = 0; i < 4; i++) { //TODO detta kan inte vara en 4, samma problem som innan
            view.setOptionColour(i, i == index);
        }
        chosenAnswer = index;
    }

    public void closePressed() {
        view.closeWithResult(chosenAnswer, question);
    }
}
