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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActvity";
    Button login;
    TextInputLayout email,password;
    TextInputEditText emailEditText,passwordEditText;
    String emailText,passwordText;
    FirebaseAuth mAuth;
    TextView forgotPass;

    //When login button is clicked
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize variables
        login = findViewById(R.id.loginBtn);

        email = findViewById(R.id.emailOutlinedTextField);
        password = findViewById(R.id.passwordOutlinedTextField);

        emailEditText = findViewById(R.id.emailTextInputEditText);
        passwordEditText = findViewById(R.id.passwordTextInputEditText);

        forgotPass = findViewById(R.id.forgotPassword);

        //Create object for text watcher class which is used with the textinputedittext
        emailEditText.addTextChangedListener(new LoginActivity.ValidationTextWatcher(emailEditText));
        passwordEditText.addTextChangedListener(new LoginActivity.ValidationTextWatcher(passwordEditText));

        //Initialize mauth
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            updateUI(mAuth.getCurrentUser());
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //login.setEnabled(false);
                if (!validateEmail() || !validatePassword()) {
                    return;
                }

                emailText =  email.getEditText().getText().toString().trim();
                passwordText = password.getEditText().getText().toString().trim();

                if(mAuth.getCurrentUser() == null){
                    signIn(emailText, passwordText);
                } else {
                    updateUI(mAuth.getCurrentUser());
                    //Log.d(TAG, "Error registering user");
                }


            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPass.setTextColor(ColorStateList.valueOf(getColor(R.color.colorPrimaryDark)));
                Intent intent = new Intent(view.getContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


    }


    // method to validateEmail email address
    public boolean validateEmail() {
        if (emailEditText.getText().toString().trim().isEmpty()) {
            email.setError("Required");
            return false;
        } else {
            email.setErrorEnabled(false);
        }
        return true;

    }

    // method to validateEmail email address
    public boolean validatePassword() {
        if (passwordEditText.getText().toString().trim().isEmpty()) {
            password.setError("Required");
            return false;
        } else {
            password.setErrorEnabled(false);
        }
        return true;

    }


    //Text watcher class which will be used with the validateEmail method
    private class ValidationTextWatcher implements TextWatcher {
        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (view.getId()){
                case R.id.emailTextInputEditText:
                    validateEmail();
                    break;

                case R.id.passwordTextInputEditText:
                    validatePassword();
                    break;

            }
        }

    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    notifyUser("Invalid email or password");
                } else if(e instanceof FirebaseAuthInvalidUserException){
                    String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();

                    if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                        notifyUser("Invalid email or password");
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


    public void updateUI(FirebaseUser user){
        if(user != null){
            Intent intent1 = new Intent(this, DashboardActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        } else {
            Log.d(TAG, "MovetoNewActvityAfterSignIN: Failed");
        }

    }


}