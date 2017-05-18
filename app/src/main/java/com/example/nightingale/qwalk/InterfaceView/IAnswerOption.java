package com.example.nightingale.qwalk.InterfaceView;

import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question;

/**
 * Created by Kraft on 2017-05-09.
 */

public interface IAnswerOption {
    void setOptions(String[] options);
    void setOptionColour(int index, boolean isSelectedColour);
    void closeWithResult(int chosenIndex, OptionQuestion question);
    void setCloseButtonEnabled(boolean enabled);
    void setTitle(String title);
    void showBotAnswer(int index);
    String getButtonText();
    void setButtonText();
}
