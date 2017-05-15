package com.example.nightingale.qwalk.Model;

import com.example.nightingale.qwalk.Presenter.MapsPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.example.nightingale.qwalk.Model.QuizSetting.*;

/**
 * Created by Kraft on 2017-05-12.
 */

public class QwalkGame {

    private MapsPresenter presenter;
    private Quiz quiz;
    private QLocation user = new QLocation(0, 0); //TODO ha denna tills user har en egen klass

    private List<Question> currentQuestions = new ArrayList<>();
    private int questionIndex;

    public final static int IN_RANGE = 25;


    public QwalkGame(MapsPresenter presenter, Quiz quiz) {
        this.presenter = presenter;
        this.quiz = quiz;

        questionIndex = quiz.getSetting(IN_ORDER) ? 0 : -1;
    }

    public void startQuiz(){
        if (quiz.getSetting(IN_ORDER) && !quiz.getSetting(HIDDEN_QUESTIONS)){

        }
    }

    public void setUserLocation(QLocation location){
        user = location;
    }

    private List<Question> questionsInRange(List<Question> questions){
        List<Question> questionsInRange = new ArrayList<>();
        for (Question q: questions) {
            if (q.getQLocation().distanceTo(user) <= IN_RANGE){
                questionsInRange.add(q);
            }
        }
        return questionsInRange;
    }
}
