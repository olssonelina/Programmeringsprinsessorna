package com.example.nightingale.qwalk.Presenter;

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

    public boolean addQuestion() {
        if(validateQuestion()) {
            OptionQuestion q = buildQuestion();
            questions.add(q);
            view.reset();
            return true;
        }
        return false;
    }

    private OptionQuestion buildQuestion() {
        String[] opts = view.getOptions();
        return new OptionQuestion(view.getQuestionTitle(), opts[0], opts[1], opts[2],opts[3], view.getAnswer(), view.getLatitude(), view.getLongitude());
    }

    public void finishQuestions() {
        if(addQuestion()) {
            view.closeWithResult(questions);
        }
    }

    private boolean validateQuestion() {
        if (view.getQuestionTitle().equals("")) {
            view.sendError("Skriv titel på frågan");
        } else if (!hasTwoOptions()) {
            view.sendError(("Skriv minst 2 alternativ"));
        } else if (!view.hasAnswer()) {
            view.sendError("Välj rätt svar");
        } else if (view.getLongitude() == 0 && view.getLatitude() == 0) {
            view.sendError("Välj position");
        } else {
            return true;
        }
        return false;
    }


    private boolean hasTwoOptions() {
        String[] options = view.getOptions();
        int count = 0;
        for (int i = 0; i < options.length; i++) {
            if (!options[i].equals("")) {
                count++;
            }
        }
        return count >= 2;
    }

}
