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
    private QLocation user = new QLocation(0, 0); //TODO ha denna tills user har en egen klass
    private List<Question> currentQuestions = new ArrayList<>();
    private int questionIndex;
    private Actor player, bot;

    public final static int IN_RANGE = 25;

    /**
     *
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
    public void startQuiz(){

        //TODO Initialize player

        if (quiz.getSetting(IN_ORDER)){
            nextQuestion();
        }
        else {
            placeAllQuestions();
        }

        if (quiz.getSetting(WITH_BOT)){
            //TODO initialize bot
        }

        if (quiz.getSetting(QUIZ_TIMER)){
            //TODO starta timer
        }
    }

    //
    private void nextQuestion(){
        if (!quiz.getSetting(IN_ORDER)){
            throw new RuntimeException("Illegal method with current settings, specifically IN_ORDER");
        }

        currentQuestions.clear();
        questionIndex++;
        if (questionIndex >= quiz.getQuestions().size()){
            end();
        }else {
            currentQuestions.add(quiz.get(questionIndex));
        }

        presenter.placeMarker(currentQuestions.get(0), quiz.getSetting(IS_HIDDEN));

    }

    private void end(){
        if (quiz.getSetting(WITH_BOT)){
            presenter.showResults(quiz, player, bot);
        }
        else {
            presenter.showResults(quiz, player);
        }
    }

    private void placeAllQuestions(){
        if (quiz.getSetting(IN_ORDER)){
            throw new RuntimeException("Illegal method with current settings, specifically IN_ORDER");
        }

        for (Question q: quiz.getQuestions()) {
            currentQuestions.add(q);
            presenter.placeMarker(q, !quiz.getSetting(IS_HIDDEN));
        }
    }

    /**
     *
     * @param answer
     */
    public void setAnswer(Question question, int answer){
        //TODO ge svaret till spelaren
        currentQuestions.remove(question);
        presenter.removeMarker(question);
        if(quiz.getSetting(IN_ORDER)){ nextQuestion(); }
    }

    /**
     *
     * @param userLocation
     */
    public void update(QLocation userLocation){
        user = userLocation;

        List<Question> questionsInRange = questionsInRange(currentQuestions);
        if (questionsInRange.size() > 0){
            for (Question q: questionsInRange) {
                presenter.enableMarker(q);
            }
        }

        updateArrow();
    }

    private List<Question> questionsInRange(List<Question> questions){
        List<Question> questionsInRange = new ArrayList<>();
        for (Question q: questions) {
            if (q.getLocation().distanceTo(user) <= IN_RANGE){
                questionsInRange.add(q);
            }
        }
        return questionsInRange;
    }

    private Question getClosestQuestion(){
        Question a = currentQuestions.get(0);
        double distance = user.distanceTo(a.getLocation());

        for (Question q: currentQuestions) {
            if (q != a && q.getLocation().distanceTo(user) < distance){
                distance = q.getLocation().distanceTo(user);
                a = q;
            }
        }

        return a;
    }

    /*public Question getQuestion(QLocation l){
        for (Question q: currentQuestions) {
            if (l.equals(q.getQLocation())){
                return q;
            }
        }
        throw new IllegalArgumentException("No question at this place");
    }*/


    private void updateArrow(){
        presenter.updateArrow(getClosestQuestion().getLocation());
    }

}
