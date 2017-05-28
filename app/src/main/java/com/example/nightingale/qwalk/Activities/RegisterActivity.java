package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nightingale.qwalk.Presenter.Register.IRegister;
import com.example.nightingale.qwalk.Presenter.Register.RegisterPresenter;
import com.example.nightingale.qwalk.R;

public class RegisterActivity extends AppCompatActivity implements IRegister{
    private RegisterPresenter presenter;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button registerbutton;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = (EditText) findViewById(R.id.usernameRegister);
        passwordInput = (EditText) findViewById(R.id.passwordRegister);
        confirmPasswordInput = (EditText) findViewById(R.id.confirmPasswordRegister);
        registerbutton = (Button) findViewById(R.id.registerbutton);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);

        presenter = new RegisterPresenter(this);
    }

    @Override
    public void setSpinnerVisible(Boolean enabled) {
        spinner.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    @Override
    public void enableRegisterButton(boolean enabled) {
        registerbutton.setEnabled(enabled);
    }

    @Override
    public String getUsername() {
        return usernameInput.getText().toString();
    }

    @Override
    public String getPasswordField1() {
        return passwordInput.getText().toString();
    }

    @Override
    public String getPasswordField2() {
        return confirmPasswordInput.getText().toString();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void closeWithResult(String username, String password) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("username", username);
        returnIntent.putExtra("password", password);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void registerButtonClicked(View view) {
        presenter.registerPressed();
    }

    public void onBackPressed(View view) {
        presenter.onBackPressed();
    }

    @Override
    public void onBackPressed(){
        presenter.onBackPressed();
    }
}
