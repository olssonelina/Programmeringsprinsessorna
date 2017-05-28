package com.example.nightingale.qwalk.Presenter.QuizSettings;

import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Model.Quiz.QuizDifficulty;
import com.example.nightingale.qwalk.Model.Quiz.QuizSetting;
import java.util.ArrayList;
import java.util.List;

import static com.example.nightingale.qwalk.Model.Quiz.QuizDifficulty.*;
import static com.example.nightingale.qwalk.Model.Quiz.QuizSetting.*;

/**
 * Created by Kraft on 2017-05-18.
 */

public class QuizSettingsPresenter {
    private IQuizSettings view;
    private boolean quizTimer, hiddenQuestions, inOrder, withBot;
    private QuizDifficulty difficulty = MEDIUM;

    public QuizSettingsPresenter(IQuizSettings view, Quiz quiz) {
        this.view = view;

        QuizSetting[] settings = {HAS_QUIZ_TIMER, WITH_AI, QUESTIONS_ARE_HIDDEN, QUESTIONS_IN_ORDER};
        for (QuizSetting qs: settings) {
            setSetting(qs, quiz.getSetting(qs));
            view.setChecked(qs, quiz.getSetting(qs));
        }

        view.setDifficultiesVisible(withBot);
    }

    public final void setSetting(QuizSetting setting, boolean value) {
        switch (setting) {
            case QUESTIONS_ARE_HIDDEN:
                hiddenQuestions = value;
                break;

            case HAS_QUIZ_TIMER:
                quizTimer = value;
                break;

            case QUESTIONS_IN_ORDER:
                inOrder = value;
                break;

            case WITH_AI:
                withBot = value;
                view.setDifficultiesVisible(value);
                break;

            default:
                throw new IllegalArgumentException("No such setting!");
        }
    }

    public final void setDifficulty(QuizDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public final void backPressed() {
        ArrayList<QuizSetting> setTrue = new ArrayList<>(), setFalse = new ArrayList<>();
        addToLists(setTrue, setFalse, QUESTIONS_IN_ORDER, inOrder);
        addToLists(setTrue, setFalse, QUESTIONS_ARE_HIDDEN, hiddenQuestions);
        addToLists(setTrue, setFalse, HAS_QUIZ_TIMER, quizTimer);
        addToLists(setTrue, setFalse, WITH_AI, withBot);

        view.closeWithResult(setTrue, setFalse, difficulty);
    }

    private void addToLists(List<QuizSetting> setTrue, List<QuizSetting> setFalse, QuizSetting setting, boolean value) {
        if (value) {
            setTrue.add(setting);
        } else {
            setFalse.add(setting);
        }
    }

}
