package com.example.nightingale.qwalk.Presenter.CreateQuiz;

import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.MessageMediator.IOnMessageRecievedListener;
import com.example.nightingale.qwalk.Model.Question.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.Model.Question.Tiebreaker;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class CreateQuizPresenter implements IOnMessageRecievedListener {

    private ICreateQuiz view;

    private Quiz editQuiz;
    private List<Question> questions = new ArrayList<>();
    private Tiebreaker tiebreaker;

    public CreateQuizPresenter(ICreateQuiz view, Quiz editQuiz) {
        this(view);
        this.editQuiz = editQuiz;
        view.fillFields(editQuiz);
        for (Question q : editQuiz.getQuestions()) {
            if (q instanceof Tiebreaker) {
                tiebreaker = (Tiebreaker) q;
            } else {
                questions.add(q);
            }
        }
        view.setListItems(getQuestionTitles());
    }

    public CreateQuizPresenter(ICreateQuiz view) {
        DatabaseHandler.setOnMessageRecievedListener(this);
        this.view = view;

    }

    @Override
    public final void messageRecieved(String message) {
        if (message.equals("Quiz Tillagd") || message.equals("Quiz Uppdaterad")) {
            view.closeWithResult();
        }
    }

    public final void onBackPressed() {
        view.close();
    }

    public final void addOptionQuestionButtonPressed() {
        view.openCreateOptionQuestion(questions.size() + 1);
    }

    public final void addTiebreakerQuestionButtonPressed() {
        view.openCreateTiebreakerQuestion();
    }

    public final void saveQuizButtonPressed() {
        if (isQuizComplete()) {
            try {
                saveQuizToDatabase();
            }
            catch (InterruptedException e){

            }

        } else {
            view.sendError(getErrorMessage());
        }
    }

    public final void addQuestions(List<Question> questionsToAdd) {
        questions.addAll(questionsToAdd);
        view.setListItems(getQuestionTitles());
    }

    public final void setTiebreaker(Tiebreaker tiebreaker) {
        this.tiebreaker = tiebreaker;
        view.setListItems(getQuestionTitles());
    }

    public final void questionPressed(int index) {
        try {
            Question q = questions.get(index); //Question pressed is an option question
            questions.remove(q);
            view.openCreateOptionQuestion((OptionQuestion) q, questions.size() + 1);
        } catch (IndexOutOfBoundsException e) { //Question pressed is the tiebreaker
            view.openCreateTiebreakerQuestion(tiebreaker);
        }
    }

    private String[] getQuestionTitles() {
        String[] questionTitles;

        if (tiebreaker == null){
            questionTitles = new String[questions.size()];
        }
        else {
            questionTitles = new String[questions.size() + 1];
            questionTitles[questionTitles.length - 1] = tiebreaker.getQuestionTitle();
        }

        for (int i = 0; i < questions.size() ; i++) {
            questionTitles[i] = questions.get(i).getQuestionTitle();
        }

        return questionTitles;
    }

    private boolean isQuizComplete() {
        return !view.getDescription().equals("") && !view.getQuestionTitle().equals("") && (questions.size() != 0 || tiebreaker != null ) ;
    }

    private String getErrorMessage() {
        if (view.getQuestionTitle().equals("")){
            return "Skriv en fråga!";
        }
        if (view.getDescription().equals("")){
            return "Skriv en beskrivning!";
        }
         if (questions.size() == 0 && tiebreaker == null){
             return "Lägg till minst en fråga!";
         }

         throw new IllegalAccessError("Don´t call this method if there are no errors!");
    }

    private void saveQuizToDatabase() throws InterruptedException {
        if (!questions.contains(tiebreaker)) {
            questions.add(tiebreaker);
        }
        DatabaseHandler.saveQuiz(view.getQuestionTitle(), view.getDescription(), questions, editQuiz);

    }


}
