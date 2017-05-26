package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nightingale.qwalk.Presenter.Login.ILogin;
import com.example.nightingale.qwalk.Presenter.Login.LoginPresenter;
import com.example.nightingale.qwalk.R;


public class LoginActivity extends AppCompatActivity implements ILogin {
    private EditText usernameInput;
    private EditText passwordInput;
    private ProgressBar spinner;
    private Button loginButton;
    private Button registerButton;
    private Button guestButton;

    private LoginPresenter presenter;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.LoginButton);
        registerButton = (Button) findViewById(R.id.Registerbutton);
        guestButton = (Button) findViewById(R.id.Guestbutton);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);

        presenter = new LoginPresenter(this);
    }

    /**
     * Called when the user taps the Send button
     */
    public final void guestButtonClicked(View view) {
        presenter.guestButtonPressed();
    }

    public final void registerButtonClicked(View view) {
        presenter.registerButtonPressed();
    }


    public final void loginButtonClicked(View view) {
        presenter.loginButtonPressed();
    }

    @Override
    public final void setSpinnerVisible(boolean value) {
        spinner.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @Override
    public final void openMenu() {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public final void openRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public final void enableButtons(boolean value) {
        loginButton.setEnabled(value);
        registerButton.setEnabled(value);
        guestButton.setEnabled(value);
    }

    @Override
    public final String getUsername() {
        return usernameInput.getText().toString();
    }

    @Override
    public final String getPassword() {
        return passwordInput.getText().toString();
    }

    @Override
    public final void showText(String message) {
        if(!(message.equals(""))){
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

    }
}

