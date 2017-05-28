package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Presenter.Login.LoginPresenter;
import com.example.nightingale.qwalk.Presenter.QuizDetails.QuizDetailsPresenter;
import com.example.nightingale.qwalk.Presenter.Register.IRegister;
import com.example.nightingale.qwalk.Presenter.Register.RegisterPresenter;
import com.example.nightingale.qwalk.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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
        presenter = new RegisterPresenter(this);
        setContentView(R.layout.activity_register);

        usernameInput = (EditText) findViewById(R.id.usernameRegister);
        passwordInput = (EditText) findViewById(R.id.passwordRegister);
        confirmPasswordInput = (EditText) findViewById(R.id.confirmPasswordRegister);
        registerbutton = (Button) findViewById(R.id.registerbutton);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);

        spinner.setVisibility(View.GONE);

    }

    public void registerButtonClicked(View view) {
        if (passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())) {
            registerbutton.setEnabled(false);
            spinner.setVisibility(View.VISIBLE);
            DatabaseHandler.registerAccount(usernameInput.getText().toString(),passwordInput.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.password_no_match), Toast.LENGTH_SHORT).show(); // "Passwords don't match" ->
        }
    }

    public void onBackPressed(View view) {
        finish();
    }


    @Override
    public void DatabaseComplete(String message) {
        spinner.setVisibility(View.GONE);
        registerbutton.setEnabled(true);

        if(message.equals("Registrering lyckad")){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("username", usernameInput.getText().toString());
            returnIntent.putExtra("password", passwordInput.getText().toString());
            setResult(GetPositionActivity.RESULT_OK, returnIntent);
            finish();
        }
    }
}
