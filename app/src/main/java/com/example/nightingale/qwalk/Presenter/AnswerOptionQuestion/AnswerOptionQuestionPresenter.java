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

        //TODO det borde vara en array från början
        String[] options = {question.getOption(0), question.getOption(1), question.getOption(2), question.getOption(3)};
        view.setOptions(options);


    }


    public final void optionPressed(int index) {
        view.setCloseButtonEnabled(true);

        chosenAnswer = index;
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
