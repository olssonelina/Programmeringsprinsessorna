package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nightingale.qwalk.InterfaceView.ILogin;
import com.example.nightingale.qwalk.Presenter.LoginPresenter;
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
    protected void onCreate(Bundle savedInstanceState) {
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
    public void guestButtonClicked(View view) {
        presenter.guestButtonPressed();
    }

    public void registerButtonClicked(View view) {
        presenter.registerButtonPressed();
    }


    public void loginButtonClicked(View view) {
        presenter.loginButtonPressed();
    }

    @Override
    public void setSpinnerVisible(boolean value) {
        spinner.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @Override
    public void openMenu() {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void openRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void enableButtons(boolean value) {
        loginButton.setEnabled(value);
        registerButton.setEnabled(value);
        guestButton.setEnabled(value);
    }

    @Override
    public String getUsername() {
        return usernameInput.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordInput.getText().toString();
    }

    @Override
    public void showText(String message) {
        if(!(message.equals(""))){

        }Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
}

