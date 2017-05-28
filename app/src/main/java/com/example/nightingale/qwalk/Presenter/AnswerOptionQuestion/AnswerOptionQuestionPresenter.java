package com.example.nightingale.qwalk.Presenter.AnswerOptionQuestion;


import com.example.nightingale.qwalk.Model.Question.OptionQuestion;

/**
 * Created by Kraft on 2017-05-09.
 */

public class AnswerOptionQuestionPresenter {

    private IAnswerOption view;
    private int chosenAnswer = 0;
    private OptionQuestion question;
    private int aiAnswer = -1;

    public AnswerOptionQuestionPresenter(IAnswerOption view, OptionQuestion question, int aiAnswer) {
        this.view = view;
        this.question = question;
        this.aiAnswer = aiAnswer;

        view.setTitle(question.getQuestionTitle());

        String[] options = {question.getOption(0), question.getOption(1), question.getOption(2), question.getOption(3)};
        view.setOptions(options);
        for (int i = 0; i < options.length; i++) {
            view.setOptionColour(i, false);
        }

    }


    public final void optionPressed(int index) {
        view.setCloseButtonEnabled(true);

        for (int i = 0; i < 4; i++) { //TODO Magic number
            view.setOptionColour(i, i == index);
        }

        chosenAnswer = index;
    }

    public boolean isSameAnswer() {
        if(chosenAnswer == aiAnswer) {
            return true;
        }
        return false;
    }


    public final void submitClicked() {
        if (aiAnswer == -1 || view.getButtonText().equals("Stäng")) {
            view.closeWithResult(chosenAnswer, question);
        } else {
            view.setButtonText("Stäng");
            view.showBotAnswer(aiAnswer);
        }
    }
}
