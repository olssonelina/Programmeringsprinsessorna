package com.example.nightingale.qwalk.Presenter.QuizSettings;

import com.example.nightingale.qwalk.Model.Quiz.QuizDifficulty;
import com.example.nightingale.qwalk.Model.Quiz.QuizSetting;

import java.util.List;

/**
 * Created by Kraft on 2017-05-18.
 */

public interface IQuizSettings {
    void setDifficultiesVisible(boolean value);

    void closeWithResult(List<QuizSetting> setTrue, List<QuizSetting> setFalse, QuizDifficulty difficulty);

    void setChecked(QuizSetting quizSetting, boolean value);
}
