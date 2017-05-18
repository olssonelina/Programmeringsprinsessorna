package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.ICreateTiebreaker;
import com.example.nightingale.qwalk.Model.Tiebreaker;

/**
 * Created by Kraft on 2017-05-10.
 */

public class CreateTiebreakerPresenter {

    private ICreateTiebreaker view;

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
            view.sendError("Lägg till en position!");
            return false;
        }
        else if (!Tiebreaker.validateBounds(min, max)){
            view.sendError("Ogiligt intervall");
            return false;
        }
        else if (!Tiebreaker.validateAnswer(min, ans, max)){
            view.sendError("Svaret ligger utanför intervallet!");
            return false;
        }
        else if (!Tiebreaker.validateQuestion(view.getQuestionTitle())){
            view.sendError("Du måste ange en fråga!");
            return false;
        }
            return true;
    }

    private Tiebreaker buildQuestion(){
        return new Tiebreaker(
                view.getQuestionTitle(),
                view.getAnswer(),
                view.getLongitude(),
                view.getLatitude(),
                view.getLowerBounds(),
                view.getHigherBounds()
        );
    }
}
