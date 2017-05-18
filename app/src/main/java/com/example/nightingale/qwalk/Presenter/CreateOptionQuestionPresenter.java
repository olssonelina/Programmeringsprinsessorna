package com.example.nightingale.qwalk.Presenter;

import android.content.Context;

import com.example.nightingale.qwalk.InterfaceView.ICreateOptionQuestion;
import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.R;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-05-11.
 */

public class CreateOptionQuestionPresenter {

    private static Context ctx;

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
        if (view.getQuestionTitle().equals("")) {
            view.sendError(ctx.getResources().getString(R.string.please_set_title));
        } else if (!hasTwoOptions()) {
            view.sendError(ctx.getResources().getString(R.string.please_add_alternatives));
        } else if (!view.hasAnswer()) {
            view.sendError(ctx.getResources().getString(R.string.choose_correct_answer));
        } else if (view.getLongitude() == 0 && view.getLatitude() == 0) {
            view.sendError(ctx.getResources().getString(R.string.choose_position));
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

    public void backButtonPressed(){
        view.closeWithResult(questions);
    }

}
