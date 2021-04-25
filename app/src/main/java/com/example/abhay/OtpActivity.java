package com.example.abhay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {


    ImageButton verify_btn;
    TextInputEditText phoneNoEnterbytheUser;
    ProgressBar progressBar;
    private String number,id;
    private FirebaseAuth mAuth;
    private TextView resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_otp);

        verify_btn = findViewById(R.id.verify);
        phoneNoEnterbytheUser = findViewById(R.id.edittext);
        progressBar = findViewById(R.id.progress_bar);
        resend = findViewById(R.id.resend);

        mAuth = FirebaseAuth.getInstance();
        number = getIntent().getStringExtra("phoneNo");

        sendVerificationCode();


        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(phoneNoEnterbytheUser.getText().toString())){
                    Toast.makeText(OtpActivity.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                }
                else if (phoneNoEnterbytheUser.getText().toString().replace("","").length()!=6){
                    Toast.makeText(OtpActivity.this, "Enter Right OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id,phoneNoEnterbytheUser.getText().toString().replace("",""));
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });

    }


    private void sendVerificationCode() {

        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resend.setText(""+1/1000);
                resend.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resend.setText(" Resend");
                resend.setEnabled(true);
            }
        }.start();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + number,      // Phone number to verify
                60,          // Timeout and unit
                TimeUnit.SECONDS,        // Unit of timeout
                this,        // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        OtpActivity.this.id = id;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(OtpActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                    }
                });// OnVerificationStateChangedCallbacks





    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            startActivity(new Intent(OtpActivity.this,MainActivity.class));
                            Toast.makeText(OtpActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                            finish();
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            Toast.makeText(OtpActivity.this, "Verification Field", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




}