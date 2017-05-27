package com.example.nightingale.qwalk.Presenter.AnswerOptionQuestion;

import com.example.nightingale.qwalk.Model.Question.OptionQuestion;

/**
 * Created by Kraft on 2017-05-09.
 */

public interface IAnswerOption {
    void setOptions(String[] options);

    void closeWithResult(int chosenIndex, OptionQuestion question);

    void setOptionColour(int index, boolean isSelectedColour);

    void setCloseButtonEnabled(boolean enabled);

    void setTitle(String title);

    void showBotAnswer(int index);

    String getButtonText();

    void setButtonText(String text);
}
