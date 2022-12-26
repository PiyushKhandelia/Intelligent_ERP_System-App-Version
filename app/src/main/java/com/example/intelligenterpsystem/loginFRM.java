package com.example.intelligenterpsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.intelligenterpsystem.databinding.ActivityLoginFrmBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginFRM extends AppCompatActivity {

    private ActivityLoginFrmBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginFrmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        //Auto Load Captcha
        String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890@-_";
        String str2 = "";
        int len = str1.length() - 1;
        double r;
        for (int i = 0; i < 6; i++) {
            r = (Math.random()) * len;
            str2 = str2 + str1.charAt((int) r);
        }
        binding.CaptchaLb.setText(str2);

        //Function for Captcha
        binding.refreshCaptchaBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890@_-";
                String str2 = "";
                int len = str1.length() - 1;
                double r;
                for (int i = 0; i < 6; i++) {
                    r = (Math.random()) * len;
                    str2 = str2 + str1.charAt((int) r);
                }
                binding.CaptchaLb.setText(str2);
            }
        });

        //Function for Proceed
        binding.loginBTN.setOnClickListener(v -> {

            String email = binding.useridTl.getEditText().getText().toString();
            String password = binding.passwordTl.getEditText().getText().toString();
            String user_capt = binding.CaptchaTl.getEditText().getText().toString();
            String display_capt = binding.CaptchaLb.getText().toString();

            //to check email, password and captcha should not be empty
            if(email.isEmpty() && password.isEmpty() && user_capt.isEmpty()) {
                binding.useridTl.setError("Field can't be empty");
                binding.passwordTl.setError("Field can't be empty");
                binding.CaptchaTl.setError("Field can't be empty");
            }
            else {
                if(email.isEmpty() && password.isEmpty()) {
                    binding.useridTl.setError("Field can't be empty");
                    binding.passwordTl.setError("Field can't be empty");
                }
                else if(password.isEmpty() && user_capt.isEmpty()) {
                    binding.passwordTl.setError("Field can't be empty");
                    binding.CaptchaTl.setError("Field can't be empty");
                }
                else if(user_capt.isEmpty() && email.isEmpty()) {
                    binding.useridTl.setError("Field can't be empty");
                    binding.CaptchaTl.setError("Field can't be empty");
                }
                else {
                    if (email.isEmpty()) {
                        binding.useridTl.setError("Field can't be empty");
                    }
                    else if (password.isEmpty()) {
                        binding.passwordTl.setError("Field can't be empty");
                    }
                    else if (user_capt.isEmpty()) {
                        binding.CaptchaTl.setError("Field can't be empty");
                    }
                    else {
                        binding.useridTl.setError(null);
                        binding.passwordTl.setError(null);
                        binding.CaptchaTl.setError(null);
                        binding.useridTl.setErrorEnabled(false);
                        binding.passwordTl.setErrorEnabled(false);
                        binding.CaptchaTl.setErrorEnabled(false);

                        if (user_capt.equals(display_capt)) {
                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Intent intent = new Intent(loginFRM.this, homeFRM.class);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(loginFRM.this, "Invalid USER ID or Password",
                                            Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(loginFRM.this, loginFRM.class);
                                        startActivity(intent);
                                    }
                                });
                        }
                        else {
                            Toast.makeText(loginFRM.this, "Captcha didn't match",
                                Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loginFRM.this, loginFRM.class);
                            startActivity(intent);
                        }
                    }
                }
            }
            /*
            //to check email, password and captcha should not be empty
            if(email.isEmpty() && password.isEmpty() && user_capt.isEmpty()) {
                binding.useridTl.setError("Field can't be empty");
                binding.passwordTl.setError("Field can't be empty");
                binding.CaptchaTl.setError("Field can't be empty");
            }
            else {
                binding.useridTl.setError(null);
                binding.passwordTl.setError(null);
                binding.CaptchaTl.setError(null);
                binding.useridTl.setErrorEnabled(false);
                binding.passwordTl.setErrorEnabled(false);
                binding.CaptchaTl.setErrorEnabled(false);
                if(email.isEmpty()){
                    binding.useridTl.setError("Field can't be empty");
                }
                else {
                    binding.useridTl.setError(null);
                    binding.useridTl.setErrorEnabled(false);
                    if(password.isEmpty()) {
                        binding.passwordTl.setError("Field can't be empty");
                    }
                    else {
                        binding.passwordTl.setError(null);
                        binding.passwordTl.setErrorEnabled(false);
                        if(user_capt.isEmpty()){
                            binding.CaptchaTl.setError("Field can't be empty");
                        }
                        else {
                            binding.CaptchaTl.setError(null);
                            binding.CaptchaTl.setErrorEnabled(false);

                            if(user_capt.equals(display_capt)) {
                                firebaseAuth.signInWithEmailAndPassword(email, password)
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                Intent intent = new Intent(loginFRM.this, homeFRM.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(loginFRM.this, "Invalid USER ID or Password",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(loginFRM.this, loginFRM.class);
                                                startActivity(intent);
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(loginFRM.this, "Captcha didn't match",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(loginFRM.this, loginFRM.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
             */
        });

        //Function to Reset Password
        binding.forgotpassBTN.setOnClickListener( v -> {
            String email = binding.useridTl.getEditText().getText().toString();

            if(email.isEmpty()) {
                binding.useridTl.setError("Field can't be empty");
            } else {
                binding.useridTl.setError(null);
                binding.useridTl.setErrorEnabled(false);

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(loginFRM.this, "Reset Email Sent! If not check spam",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(loginFRM.this, loginFRM.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(loginFRM.this, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(loginFRM.this, loginFRM.class);
                                startActivity(intent);
                            }
                        });
            }
        });
    }
}