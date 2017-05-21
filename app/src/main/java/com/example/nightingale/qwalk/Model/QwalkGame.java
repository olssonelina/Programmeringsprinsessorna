package com.example.nightingale.qwalk.Model;

import com.example.nightingale.qwalk.Presenter.MapsPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.example.nightingale.qwalk.Model.QuizSetting.*;

/**
 * Created by Kraft on 2017-05-12.
 */

public class QwalkGame implements IOnAIMoveListener {

    //
    private MapsPresenter presenter;
    private Quiz quiz;
    private List<Question> currentQuestions = new ArrayList<>();
    private int answeredQuestions;
    private Player player;
    private AI ai;
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

        answeredQuestions = 0;
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
            //TODO initialize ai
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

            ai = new AI(quiz, difficulty);
            ai.setOnAImovedListener(this);
            Thread botThread = new Thread(ai);
            botThread.start();
            //presenter.initializeBot(); //TODO
        }

        if (quiz.getSetting(QUIZ_TIMER)) {
            quizTimer = new GameTimer();
            quizTimer.startTimer();
        }

        if (quiz.getSetting(IS_HIDDEN)){
            presenter.setShowClosestEnabled(false);
        }

        presenter.setProgress(0, quiz.getQuestions().size());

    }

    //
    private void nextQuestion() {
        if (!quiz.getSetting(IN_ORDER)) {
            throw new RuntimeException("Illegal method with current settings, specifically IN_ORDER");
        }

        currentQuestions.clear();

        currentQuestions.add(quiz.get(answeredQuestions));
        if (!quiz.getSetting(IS_HIDDEN)) {
            presenter.placeMarker(currentQuestions.get(0));
            presenter.focusOn(currentQuestions.get(0).getLocation());
        }
    }

    private void end() {
        long time = -1;
        if (quiz.getSetting(QUIZ_TIMER)){
            quizTimer.stopTimer();
            time = quizTimer.getTime();
        }

        if (quiz.getSetting(WITH_BOT)) {
            presenter.showResults(quiz, player.getAnswers(), ai.getAnswers(), time);
        }
        else {
            presenter.showResults(quiz, player.getAnswers(), null, time);

        }
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
        answeredQuestions++;
        presenter.setProgress(answeredQuestions, quiz.getQuestions().size());

        currentQuestions.remove(question);
        presenter.removeMarker(question);
        if (quiz.getSetting(IN_ORDER)) {
            if (answeredQuestions >= quiz.getQuestions().size()) {
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
        if (player.getLocation().equals(new QLocation(0,0))){
            presenter.focusOn(userLocation);
        }
        player.updateLocation(userLocation);

        List<Question> questionsInRange = questionsInRange(currentQuestions);
        if (questionsInRange.size() > 0) {
            for (Question q : questionsInRange) {
                if (quiz.getSetting(IS_HIDDEN)) {
                    presenter.placeMarker(q);
                }
                presenter.enableMarker(q);
            }
        }
        if (quiz.getSetting(WITH_BOT)){ updateBot(); }
    }

    private void updateBot() {
    }

    private List<Question> questionsInRange(List<Question> questions) {
        List<Question> questionsInRange = new ArrayList<>();
        for (Question q : questions) {
            if (q.getLocation().distanceTo(player.getLocation()) <= IN_RANGE) {
                questionsInRange.add(q);
            }
        }
        return questionsInRange;
    }

    public Question getClosestQuestion() {
        Question a = currentQuestions.get(0);
        double distance = player.getLocation().distanceTo(a.getLocation());

        for (Question q : currentQuestions) {
            if (q != a && q.getLocation().distanceTo(player.getLocation()) < distance) {
                distance = q.getLocation().distanceTo(player.getLocation());
                a = q;
            }
        }

        return a;
    }


    public void updateArrow() {
        if (quiz.getSetting(IS_HIDDEN)){
            return;
        }

        Question closestQuestion = getClosestQuestion();
        if (presenter.isOnScreen(closestQuestion.getLocation())) {
            presenter.updateArrow(getClosestQuestion().getLocation());
        } else {
            presenter.hideArrow();
        }
    }

    @Override
    public void AIMoved(QLocation location) {
        presenter.moveBot(location);
    }
}
