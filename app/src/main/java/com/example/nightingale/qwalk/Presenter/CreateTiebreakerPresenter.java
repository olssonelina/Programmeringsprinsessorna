package com.example.nightingale.qwalk.Presenter;

import android.content.Context;

import com.example.nightingale.qwalk.InterfaceView.ICreateTiebreaker;
import com.example.nightingale.qwalk.Model.Tiebreaker;
import com.example.nightingale.qwalk.R;

/**
 * Created by Kraft on 2017-05-10.
 */

public class CreateTiebreakerPresenter {

    private ICreateTiebreaker view;

    //private static Context ctx;

    public CreateTiebreakerPresenter(ICreateTiebreaker view ){
        this.view = view;
    }

    public void doneButtonPressed(){
        if (validateQuestion()) {
            view.closeWithResult(buildQuestion());
        }
    }

    private boolean validateQuestion(){
        int min = view.getLowerBounds(), max = view.getHigherBounds(), ans = view.getAnswer();
        if (!Tiebreaker.validateLocation(view.getLatitude(), view.getLongitude())){
            //view.sendError(ctx.getResources().getString(R.string.choose_position));
            view.sendError("Välj position!");
            return false;
        }
        else if (!Tiebreaker.validateBounds(min, max)){
            //view.sendError(ctx.getResources().getString(R.string.invalid_range));
            view.sendError("Ogiltigt intervall!");
            return false;
        }
        else if (!Tiebreaker.validateAnswer(min, ans, max)){
            //view.sendError(ctx.getResources().getString(R.string.out_of_range));
            view.sendError("Svaret ligger utanför intervallet!");
            return false;
        }
        else if (!Tiebreaker.validateQuestion(view.getQuestionTitle())){
            //view.sendError(ctx.getResources().getString(R.string.you_must_add_question));
            view.sendError("Du måste ange en fråga!");
            return false;
        }
            return true;
    }

    private Tiebreaker buildQuestion(){
        return new Tiebreaker(
                view.getQuestionTitle(),
                view.getAnswer(),
                view.getLatitude(),
                view.getLongitude(),
                view.getLowerBounds(),
                view.getHigherBounds()
        );
    }

    public void setAllFields(Tiebreaker question){
        view.setQuestionTitle(question.getQuestionTitle());
        view.setLatitude(question.getLatitude());
        view.setLongitude(question.getLongitude());
        view.setLowerBounds(question.getLowerBounds());
        view.setUpperBounds(question.getUpperBounds());
        view.setAnswer(question.getCorrectAnswer());
    }

    public void updateLocationText() {
        if (view.getLatitude() == 0 && view.getLongitude() == 0) {
            view.setLocationText("Lägg till position");
        } else {
            view.setLocationText("Ändra position");
        }
    }
}
