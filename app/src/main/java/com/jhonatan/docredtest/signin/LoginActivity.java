package com.jhonatan.docredtest.signin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jhonatan.docredtest.MainActivity;
import com.jhonatan.docredtest.R;

public class LoginActivity extends AppCompatActivity implements SigninContract.View {

    EditText fieldEmail;
    EditText fieldPassword;
    Button btnSignin;
    ProgressBar loaderSignin;

    private SigninContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new SigninPresenter(this);

        fieldEmail = findViewById(R.id.email_signin);
        fieldPassword = findViewById(R.id.pass_signin1);
        btnSignin = findViewById(R.id.btn_signin);
        loaderSignin = findViewById(R.id.loader_signin);
    }

    //Sign in button on click
    public void onClickMy(View view) {
        final String email = fieldEmail.getText().toString().trim();
        final String password = fieldPassword.getText().toString().trim();
        if(presenter.isValidForm(email, password)) {
            presenter.attemptSignin(email, password);
        }
    }

    @Override
    public void onNavigateHome() {
        showMessage("Ya estas logeado en el sistema");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", fieldEmail.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    @Override
    public void displayEmailError(String error) {
        fieldEmail.setError(error);
    }

    @Override
    public void displayPasswordError(String error) {
        fieldPassword.setError(error);
    }

    @Override
    public void displaySigninError(String error) {
        showMessage(error);
    }

    @Override
    public void displayLoader(boolean loader) {
        int stateLoader = loader ? View.VISIBLE : View.GONE;
        loaderSignin.setVisibility(stateLoader);
    }

    @Override
    public void setEnabledView(boolean enable) {
        fieldEmail.setEnabled(enable);
        fieldPassword.setEnabled(enable);
        btnSignin.setEnabled(enable);
    }


    @Override
    protected void onDestroy() {
        super .onDestroy();
        presenter.onDestroy();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}