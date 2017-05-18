package com.example.nightingale.qwalk.Presenter;

import android.graphics.Path;

import com.example.nightingale.qwalk.InterfaceView.ICreateOptionQuestion;
import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-05-11.
 */

public class CreateOptionQuestionPresenter {

    private ICreateOptionQuestion view;

    private ArrayList<Question> questions = new ArrayList<>();

    public CreateOptionQuestionPresenter(ICreateOptionQuestion view) {
        this.view = view;
    }

    public boolean addQuestion(boolean reset) {
        if(validateQuestion()) {
            OptionQuestion q = buildQuestion();
            questions.add(q);
            if (reset) { view.reset(); }
            return true;
        }
        return false;
    }

    private OptionQuestion buildQuestion() {
        String[] opts = view.getOptions();
        return new OptionQuestion(view.getQuestionTitle(), opts[0], opts[1], opts[2],opts[3], view.getAnswer(), view.getLatitude(), view.getLongitude());
    }

    public void finishQuestions() {
        if(addQuestion(false)) {
            view.closeWithResult(questions);
        }
    }

    private boolean validateQuestion() {
        if (!OptionQuestion.validateQuestion(view.getQuestionTitle())) {
            view.sendError("Skriv titel på frågan");
        } else if (!OptionQuestion.validateOptions(view.getOptions())) {
            view.sendError(("Skriv minst 2 alternativ"));
        } else if (!view.hasAnswer()) {
            view.sendError("Välj rätt svar");
        } else if (!OptionQuestion.validateLocation(view.getLatitude(), view.getLongitude())) {
            view.sendError("Välj position");
        } else {
            return true;
        }
        return false;
    }


    public void backButtonPressed(){
        view.closeWithResult(questions);
    }

}
