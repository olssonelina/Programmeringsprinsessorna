package com.example.nightingale.qwalk.Model;

import com.example.nightingale.qwalk.Presenter.MapsPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.example.nightingale.qwalk.Model.QuizSetting.*;

/**
 * Created by Kraft on 2017-05-12.
 */

public class QwalkGame {

    //
    private MapsPresenter presenter;
    private Quiz quiz;
    private QLocation userLocation = new QLocation(0, 0); //TODO ha denna tills user har en egen klass
    private List<Question> currentQuestions = new ArrayList<>();
    private int questionIndex;
    private Player player;
    private Bot bot;
    private GameTimer quizTimer;

    /**
     * The distance in meters for a question to be considered in range.
     */
    public final static double IN_RANGE = 25;

    /**
     * @param presenter
     * @param quiz
     */
    public QwalkGame(MapsPresenter presenter, Quiz quiz) {
        this.presenter = presenter;
        this.quiz = quiz;

        questionIndex = -1;
    }

    /**
     *
     */
    public void startQuiz() {

        player = new Player(quiz.getQuestions().size());

        if (quiz.getSetting(IN_ORDER)) {
            nextQuestion();
        } else {
            placeAllQuestions();
        }

        if (quiz.getSetting(WITH_BOT)) {
            //TODO initialize bot
            int difficulty = 0;
            switch (quiz.getDifficulty()) {
                case EASY:
                    difficulty = 35;
                    break;
                case MEDIUM:
                    difficulty = 50;
                    break;
                case HARD:
                    difficulty = 75;
                    break;
            }

            bot = new Bot(quiz, difficulty);
            //presenter.initializeBot(); //TODO
        }

        if (quiz.getSetting(QUIZ_TIMER)) {
            //TODO starta timer
            quizTimer = new GameTimer();
            quizTimer.startTimer();
        }
    }

    //
    private void nextQuestion() {
        if (!quiz.getSetting(IN_ORDER)) {
            throw new RuntimeException("Illegal method with current settings, specifically IN_ORDER");
        }

        currentQuestions.clear();
        questionIndex++;

        currentQuestions.add(quiz.get(questionIndex));
        if (!quiz.getSetting(IS_HIDDEN)) {
            presenter.placeMarker(currentQuestions.get(0));
        }
        presenter.focusOn(currentQuestions.get(0).getLocation());


    }

    private void end() {
        long time = -1;
        if (quiz.getSetting(QUIZ_TIMER)){
            quizTimer.stopTimer();
            time = quizTimer.getTime();
        }
        presenter.showResults(quiz, player, bot, time);
        presenter.close();
    }

    private void placeAllQuestions() {
        if (quiz.getSetting(IN_ORDER)) {
            throw new RuntimeException("Illegal method with current settings, specifically IN_ORDER");
        }

        for (Question q : quiz.getQuestions()) {
            currentQuestions.add(q);
            if (!quiz.getSetting(IS_HIDDEN)) {
                presenter.placeMarker(q);
            }
        }
    }

    /**
     * @param answer
     */
    public void setAnswer(Question question, int answer) {
        player.setAnswer(quiz.getQuestionIndex(question), answer);

        currentQuestions.remove(question);
        presenter.removeMarker(question);
        if (quiz.getSetting(IN_ORDER)) {
            if (questionIndex + 1 >= quiz.getQuestions().size()) {
                end();
                return;
            }
            nextQuestion();
        } else if (currentQuestions.size() == 0) {
            end();
        }
    }

    /**
     * @param userLocation
     */
    public void update(QLocation userLocation) {
        this.userLocation = userLocation;

        List<Question> questionsInRange = questionsInRange(currentQuestions);
        if (questionsInRange.size() > 0) {
            for (Question q : questionsInRange) {
                if (quiz.getSetting(IS_HIDDEN)) {
                    presenter.placeMarker(q);
                }
                presenter.enableMarker(q);
            }
        }
        updateBot();
    }

    private List<Question> questionsInRange(List<Question> questions) {
        List<Question> questionsInRange = new ArrayList<>();
        for (Question q : questions) {
            if (q.getLocation().distanceTo(userLocation) <= IN_RANGE) {
                questionsInRange.add(q);
            }
        }
        return questionsInRange;
    }

    public Question getClosestQuestion() {
        Question a = currentQuestions.get(0);
        double distance = userLocation.distanceTo(a.getLocation());

        for (Question q : currentQuestions) {
            if (q != a && q.getLocation().distanceTo(userLocation) < distance) {
                distance = q.getLocation().distanceTo(userLocation);
                a = q;
            }
        }

        return a;
    }

    private void updateBot() {
        //TODO
    }


    public void updateArrow() {
        Question closestQuestion = getClosestQuestion();
        if (presenter.isOnScreen(closestQuestion.getLocation())) {
            presenter.updateArrow(getClosestQuestion().getLocation());
        } else {
            presenter.hideArrow();
        }
    }

}
