package com.jhonatan.docredtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jhonatan.docredtest.register.RegisterActivity;
import com.jhonatan.docredtest.signin.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterToLogin(View view) {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    public void enterToRegister(View view) {
        Intent login = new Intent(this, RegisterActivity.class);
        startActivity(login);
    }
}
