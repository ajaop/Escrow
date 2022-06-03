package com.example.escrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ForgotPasswordctivity";
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Button forgotPass;
    TextView goBack;
    TextInputLayout email;
    TextInputEditText emailEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Initialix=ze variables
        forgotPass = findViewById(R.id.resetBtn);
        goBack = findViewById(R.id.goBackLink);
        email = findViewById(R.id.emailOutlinedTextField);
        emailEditText = findViewById(R.id.emailTextInputEditText);

        //Disable forgot password button
        forgotPass.setEnabled(false);

        //Textwatcher class will be used to disable button if email field is empty
        emailEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    forgotPass.setEnabled(false);
                } else {
                    forgotPass.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(auth.getCurrentUser() == null){
                    String mail = emailEditText.getText().toString().trim();
                    reset(mail);
                } else {
                    Log.d(TAG, "Error sending mail to  user");
                }

            }
        });


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                goBack.setTextColor(ColorStateList.valueOf(getColor(R.color.colorPrimaryDark)));

            }
        });

    }


    public void reset(String email){
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            updateUI();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

               if(e instanceof FirebaseAuthInvalidUserException){
                    String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();

                    if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                        notifyUser("There is no registered user with this email address");
                    } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                        notifyUser("User account has been disabled");
                    } else {
                        notifyUser(e.getLocalizedMessage());
                    }

                } else {
                   notifyUser(e.getLocalizedMessage());
               }

            }
        });
    }

    public void notifyUser(String message){
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();

    }


    public void updateUI(){
        notifyUser("The email reset link has been sent to your mail");
        forgotPass.setEnabled(false);
        email.setEnabled(false);
    }




}