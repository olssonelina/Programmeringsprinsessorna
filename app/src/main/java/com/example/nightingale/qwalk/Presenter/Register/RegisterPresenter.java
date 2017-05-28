package com.example.nightingale.qwalk.Presenter.Register;

import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.MessageMediator.IOnMessageRecievedListener;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class RegisterPresenter implements IOnMessageRecievedListener {

    private IRegister view;

    public RegisterPresenter(IRegister view) {
        this.view = view;
        DatabaseHandler.setOnMessageRecievedListener(this);
        view.setSpinnerVisible(false);
    }

    public final void registerPressed(){
        if (view.getPasswordField1().equals(view.getPasswordField2())) {
            view.enableRegisterButton(false);
            view.setSpinnerVisible(true);
            DatabaseHandler.registerAccount(view.getUsername(), view.getPasswordField1());
        } else {
            view.showError("Lösenorden matchar ej");
        }
    }

    public final void onBackPressed(){
        view.close();
    }

    @Override
    public final void messageRecieved(String message) {
        view.setSpinnerVisible(false);
        view.enableRegisterButton(true);

        if(message.equals("Registrering lyckad")){
            view.closeWithResult(view.getUsername(), view.getPasswordField1());
        }
    }
}