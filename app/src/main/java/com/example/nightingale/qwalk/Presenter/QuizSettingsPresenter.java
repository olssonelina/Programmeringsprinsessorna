package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IQuizSettings;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.QuizDifficulty;
import com.example.nightingale.qwalk.Model.QuizSetting;

import java.util.ArrayList;

import static com.example.nightingale.qwalk.Model.QuizDifficulty.*;
import static com.example.nightingale.qwalk.Model.QuizSetting.*;

/**
 * Created by Kraft on 2017-05-18.
 */

public class QuizSettingsPresenter {
    private IQuizSettings view;
    private boolean questionTimer, quizTimer, hiddenQuestions, inOrder, withBot;
    private QuizDifficulty difficulty = MEDIUM;

    public QuizSettingsPresenter(IQuizSettings view, Quiz quiz) {
        this.view = view;

        QuizSetting[] settings = {QUESTION_TIMER, QUIZ_TIMER, WITH_BOT, IS_HIDDEN, IN_ORDER};
        for (QuizSetting qs: settings) {
            setSetting(qs, quiz.getSetting(qs));
            view.setChecked(qs, quiz.getSetting(qs));
        }

        view.setDifficultiesVisible(false);
    }

    public void setSetting(QuizSetting setting, boolean value) {
        switch (setting) {
            case IS_HIDDEN:
                hiddenQuestions = value;
                break;

            case QUESTION_TIMER:
                questionTimer = value;
                break;

            case QUIZ_TIMER:
                quizTimer = value;
                break;

            case IN_ORDER:
                inOrder = value;
                break;

            case WITH_BOT:
                withBot = value;
                view.setDifficultiesVisible(value);
                break;

            default:
                throw new IllegalArgumentException("No such setting!");
        }
    }

    public void setDifficulty(QuizDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void backPressed() {
        ArrayList<QuizSetting> setTrue = new ArrayList<>(), setFalse = new ArrayList<>();
        addToLists(setTrue, setFalse, IN_ORDER, inOrder);
        addToLists(setTrue, setFalse, IS_HIDDEN, hiddenQuestions);
        addToLists(setTrue, setFalse, QUESTION_TIMER, questionTimer);
        addToLists(setTrue, setFalse, QUIZ_TIMER, quizTimer);
        addToLists(setTrue, setFalse, WITH_BOT, withBot);

        view.closeWithResult(setTrue, setFalse, difficulty);
    }

    private void addToLists(ArrayList<QuizSetting> setTrue, ArrayList<QuizSetting> setFalse, QuizSetting setting, boolean value) {
        if (value) {
            setTrue.add(setting);
        } else {
            setFalse.add(setting);
        }
    }

}
