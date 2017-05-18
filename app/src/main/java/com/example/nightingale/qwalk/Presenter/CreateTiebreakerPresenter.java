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

    private static Context ctx;

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
        if (view.getLatitude() == 0 || view.getLongitude() == 0){
            view.sendError(ctx.getResources().getString(R.string.choose_position));
            return false;
        }
        else if (min > max){
            view.sendError(ctx.getResources().getString(R.string.invalid_range));
            return false;
        }
        else if (ans < min || max < ans){
            view.sendError(ctx.getResources().getString(R.string.out_of_range));
            return false;
        }
        else if (view.getQuestionTitle().equals("")){
            view.sendError(ctx.getResources().getString(R.string.you_must_add_question));
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
