package com.example.abhay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class SignupActivity extends AppCompatActivity {


    private CountryCodePicker countryCodePicker;
    TextInputLayout regNumber, regUsername, regName, regEmail, regPassword;
    Button regToLoginBtn;
    ImageButton regButton;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_signup);




        //hooks
        regName = findViewById(R.id.Username2);
        regUsername = findViewById(R.id.Username);
        regNumber = findViewById(R.id.Number);
        regEmail = findViewById(R.id.Email);
        regPassword = findViewById(R.id.password2);
        regButton = findViewById(R.id.signupb);
        regToLoginBtn  = findViewById(R.id.signIN);


        //save data
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("user");

                //get all values
                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneno = regNumber.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();


                if (TextUtils.isEmpty(regNumber.getEditText().getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Enter No.....", Toast.LENGTH_SHORT).show();

                } else if (regNumber.getEditText().getText().toString().replace("", "").length() != 10) {
                    Toast.makeText(SignupActivity.this, "Enter Correct No.....", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(SignupActivity.this, OtpActivity.class);
                    intent.putExtra("phoneNo",phoneno);
                    startActivity(intent);
                    finish();

                }




                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneno, password);
                reference.child(email).setValue(helperClass);

            }
        });

    }
}