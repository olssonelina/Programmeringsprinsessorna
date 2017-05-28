package com.example.nightingale.qwalk.Model;

import com.example.nightingale.qwalk.Model.Actor.AI;
import com.example.nightingale.qwalk.Model.Actor.IActor;
import com.example.nightingale.qwalk.Model.Actor.Player;
import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Model.Quiz.QuizDifficulty;
import com.example.nightingale.qwalk.Presenter.Maps.MapsPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.example.nightingale.qwalk.Model.Quiz.QuizSetting.*;

/**
 * Created by Kraft on 2017-05-12.
 */

public class QwalkGame {

    private MapsPresenter presenter;
    private Quiz quiz;
    private List<Question> currentQuestions = new ArrayList<>();
    private int answeredQuestions;
    private IActor player;
    private IActor ai;
    private GameTimer quizTimer;

    /**
     * The distance in meters for a question to be considered in range.
     */
    private final double IN_RANGE = 25;

    /**
     * @param presenter the model-view-presenter presenter
     * @param quiz      the quiz to be played
     */
    public QwalkGame(MapsPresenter presenter, Quiz quiz) {
        this.presenter = presenter;
        this.quiz = quiz;
        answeredQuestions = 0;
    }

    /**
     * Starts the game
     */
    public final void startQuiz() {

        player = new Player(quiz.size());

        if (quiz.getSetting(QUESTIONS_IN_ORDER)) {
            nextQuestion();
        } else {
            placeAllQuestions();
        }

        if (quiz.getSetting(HAS_QUIZ_TIMER)) {
            quizTimer = new GameTimer();
            quizTimer.startTimer();
        }

        if (quiz.getSetting(QUESTIONS_ARE_HIDDEN)) {
            presenter.setShowClosestEnabled(false);
        }

        presenter.setProgress(0, quiz.size());

    }

    private void nextQuestion() {
        if (!quiz.getSetting(QUESTIONS_IN_ORDER)) {
            throw new IllegalAccessError("Illegal method with current settings, specifically QUESTIONS_IN_ORDER");
        }

        currentQuestions.clear();

        currentQuestions.add(quiz.get(answeredQuestions));
        if (!quiz.getSetting(QUESTIONS_ARE_HIDDEN)) {
            presenter.placeMarker(currentQuestions.get(0));
            presenter.focusOn(currentQuestions.get(0).getLocation());
        }
    }

    private void end() {
        long time = -1;

        if (quiz.getSetting(HAS_QUIZ_TIMER)) {
            quizTimer.stopTimer();
            time = quizTimer.getTime();
        }

        presenter.showResults(quiz, player.getAnswers(), quiz.getSetting(WITH_AI) ? ai.getAnswers() : null, time);

        presenter.close();
    }

    private void placeAllQuestions() {
        if (quiz.getSetting(QUESTIONS_IN_ORDER)) {
            throw new IllegalAccessError("Illegal method with current settings, specifically QUESTIONS_IN_ORDER");
        }

        for (Question q : quiz.getQuestions()) {
            currentQuestions.add(q);
            if (!quiz.getSetting(QUESTIONS_ARE_HIDDEN)) {
                presenter.placeMarker(q);
            }
        }
    }

    /**
     * Recors the players answer to a specific question
     * @param question the question at hand
     * @param answer the players answer to the specified question
     */
    public final void setAnswer(Question question, int answer) {
        player.setAnswer(quiz.getQuestionIndex(question), answer);
        answeredQuestions++;
        presenter.setProgress(answeredQuestions, quiz.size());

        currentQuestions.remove(question);
        presenter.removeMarker(question);
        if (quiz.getSetting(QUESTIONS_IN_ORDER)) {
            if (answeredQuestions >= quiz.size()) {
                end();
                return;
            }
            nextQuestion();
        } else if (currentQuestions.size() == 0) {
            end();
        }
    }

    /**
     * Updates the game
     * @param userLocation the players current location
     */
    public final void update(QLocation userLocation) {
        if (player.getLocation() == null) { // If the player hasn't yet got a location, focus the camera on the player and initalize the ai
            presenter.focusOn(userLocation);
            if (quiz.getSetting(WITH_AI)) {
                initializeAi(userLocation);
            }
        }
        player.setLocation(userLocation);

        List<Question> questionsInRange = questionsInRange(currentQuestions);
        if (questionsInRange.size() > 0) {
            for (Question q : questionsInRange) {
                if (quiz.getSetting(QUESTIONS_ARE_HIDDEN)) {
                    presenter.placeMarker(q);
                }
                presenter.enableMarker(q);
            }
        }

        if (hasAi()) {
            presenter.moveBot(ai.getLocation());
        }
    }

    private void initializeAi(QLocation userLocation) {

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

        AI ai = new AI(quiz.getCorrectAnswers(), quiz.hasTieBreaker(), quiz.getLowerBounds(), quiz.getUpperBounds(), quiz.getDifficulty(), quiz.getLocations(), userLocation);
        Thread aiThread = new Thread(ai);
        ai.setLocation(userLocation);
        aiThread.start();

        presenter.initializeAi(userLocation);
        this.ai = ai;
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

    /**
     * @return returns the question closest to the player
     */
    public final Question getClosestQuestion() {
        Question a = currentQuestions.get(0);
        double distance = player.getLocation().distanceTo(a.getLocation());

        for (Question q : currentQuestions) {
            if (!q.equals(a) && q.getLocation().distanceTo(player.getLocation()) < distance) {
                distance = q.getLocation().distanceTo(player.getLocation());
                a = q;
            }
        }

        return a;
    }

    /**
     * Update the arrow pointing to the closest question
     */
    public final void updateArrow() {
        if (quiz.getSetting(QUESTIONS_ARE_HIDDEN)) {
            return;
        }

        Question closestQuestion = getClosestQuestion();
        if (presenter.isOnScreen(closestQuestion.getLocation())) {
            presenter.updateArrow(getClosestQuestion().getLocation());
        } else {
            presenter.hideArrow();
        }
    }

    /**
     * @return returns true if the game has an ai
     */
    public final boolean hasAi() {
        return ai != null;
    }

    /**
     * Returns the index of a specified question
     * @param question the question to know the index of
     * @return returns the index of the specified question
     */
    public final int getQuestionIndex(Question question) {
        return quiz.getQuestionIndex(question);
    }

    /**
     * Fetches the ai's answer to a specified question
     * @param question the question to know the ai's answer to
     * @return returns the ai's answer to the specified question
     */
    public final int getAiAnswer(Question question) {
        return ai.getAnswer(quiz.getQuestionIndex(question));
    }
}
