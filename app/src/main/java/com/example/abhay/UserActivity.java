package com.example.abhay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class UserActivity extends AppCompatActivity {

    TextInputLayout fullname, email, phoneno, password, username;
    TextView fullnameLabel, usernameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_user);

        //hooks
        fullname = findViewById(R.id.fullname4);
        phoneno = findViewById(R.id.Number4);
        email = findViewById(R.id.Email1);
        username = findViewById(R.id.Username4);
        password = findViewById(R.id.password4);
        fullnameLabel = findViewById(R.id.welcometext2);
        usernameLabel = findViewById(R.id.textView3);

        //ShowallData
        showAllData();
    }

    private void showAllData() {
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_email = intent.getStringExtra("email");
        String user_phoneno = intent.getStringExtra("phoneno");
        String user_password = intent.getStringExtra("password");

        fullnameLabel.setText(user_name);
        usernameLabel.setText(user_username);
        phoneno.getEditText().setText(user_phoneno);
        email.getEditText().setText(user_email);
        username.getEditText().setText(user_username);
        password.getEditText().setText(user_password);
        fullname.getEditText().setText(user_name);

    }
}