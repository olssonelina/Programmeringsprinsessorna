package com.example.nightingale.qwalk.Presenter.CreateOptionQuestion;

import com.example.nightingale.qwalk.Model.Question.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Elina Olsson on 2017-05-11.
 */

public class CreateOptionQuestionPresenter {

    //private static Context ctx;

    private int questionID = -1;

    private ICreateOptionQuestion view;

    private List<Question> questions = new ArrayList<>();

    public CreateOptionQuestionPresenter(ICreateOptionQuestion view) {
        this.view = view;
    }

    public final boolean addQuestion(boolean reset) {
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
        return new OptionQuestion(view.getQuestionTitle(), new ArrayList<String>(Arrays.asList(opts)), view.getAnswer(), view.getLatitude(), view.getLongitude(), questionID );
    }

    public final void finishQuestions() {
        if(addQuestion(false)) {
            view.closeWithResult(questions);
        }
    }

    private boolean validateQuestion() {
        if (!OptionQuestion.validateQuestion(view.getQuestionTitle())) {
            //view.sendError(ctx.getResources().getString(R.string.please_set_title));
            view.sendError("Ange en fråga!");
        } else if (!OptionQuestion.validateOptions(view.getOptions())) {
            //view.sendError(ctx.getResources().getString(R.string.please_add_alternatives));
            view.sendError("Skriv minst 2 alternativ");
        } else if (!view.hasAnswer()) {
            //view.sendError(ctx.getResources().getString(R.string.choose_correct_answer));
            view.sendError("Välj ett svar");
        } else if (!OptionQuestion.validateLocation(view.getLatitude(), view.getLongitude())) {
            //view.sendError(ctx.getResources().getString(R.string.choose_position));
            view.sendError("Välj position");
        } else {
            return true;
        }
        return false;
    }


    public final void backButtonPressed(){
        view.closeWithResult(questions);
    }


    public final void updateLocationText() {
        if (view.getLatitude() == 0 && view.getLongitude() == 0) {
            view.setLocationText("Lägg till position");
        } else {
            view.setLocationText("Ändra position");
        }
    }

    public final void setAllFields(OptionQuestion question){
        questionID = question.getQuestionID();
        view.setAnswer(question.getCorrectAnswer());
        view.setLatitude(question.getLatitude());
        view.setLongitude(question.getLongitude());
        view.setOptions(question.getOptions());
        view.setQuestionTitle(question.getQuestionTitle());
    }
}
