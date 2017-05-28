package com.example.nightingale.qwalk.Presenter.Register;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public interface IRegister {
    void setSpinnerVisible(Boolean enabled);
    void enableRegisterButton(boolean enabled);
    String getUsername();
    String getPasswordField1();
    String getPasswordField2();
    void showError(String message);
    void close();
    void closeWithResult(String username, String password);
}
