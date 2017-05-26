package com.example.nightingale.qwalk.Presenter.QuizSettings;

import com.example.nightingale.qwalk.Model.Quiz.QuizDifficulty;
import com.example.nightingale.qwalk.Model.Quiz.QuizSetting;

import java.util.ArrayList;

/**
 * Created by Kraft on 2017-05-18.
 */

public interface IQuizSettings {
    void setDifficultiesVisible(boolean value);

    void closeWithResult(ArrayList<QuizSetting> setTrue, ArrayList<QuizSetting> setFalse, QuizDifficulty difficulty);

    void setChecked(QuizSetting quizSetting, boolean value);
}
