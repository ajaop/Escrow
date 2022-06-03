package com.example.escrow;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity2 extends AppCompatActivity {

    private static final String TAG = "RegisterActivity2";
    private Button registerBtn;
    private TextInputLayout  username,password,phone;
    private  TextInputEditText usernameEditText, passwordEditText, phoneEditText;
    String emailText, firstnameText, lastnameText, usernameText, phoneText,userID;
    //Access cloud firestore instance from your activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        //  Initialize buttons and textfields
        registerBtn = findViewById(R.id.registerBtn);
        username = findViewById(R.id.userNameOutlinedTextField);
        password = findViewById(R.id.passwordOutlinedTextField);
        phone = findViewById(R.id.phoneNumOutlinedTextField);

        // Intitialize TextInputEditText
        usernameEditText = findViewById(R.id.usernameTextInputEditText);
        phoneEditText = findViewById(R.id.phoneTextInputEditText);
        passwordEditText = findViewById(R.id.passwordTextInputEditText);

        //Create object for text watcher class which is used with the textinputedittext
        usernameEditText.addTextChangedListener(new RegisterActivity2.ValidationTextWatcher(usernameEditText));
        phoneEditText.addTextChangedListener(new RegisterActivity2.ValidationTextWatcher(phoneEditText));
        passwordEditText.addTextChangedListener(new RegisterActivity2.ValidationTextWatcher(passwordEditText));

        //Initialize mauth
        mAuth = FirebaseAuth.getInstance();


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // registerBtn.setEnabled(false);
                if (!validateUsername() || !validatephone() || !validatepassword()) {
                    return;
                }

                checkUsername();


                //Object for intent
                Intent intent = getIntent();
                //get variables from RegisterActivity using intent object
                emailText = intent.getStringExtra("email");
                firstnameText = intent.getStringExtra("firstname");
                lastnameText = intent.getStringExtra("lastname");

                //get user input value
                usernameText = username.getEditText().getText().toString();
                phoneText = phone.getEditText().getText().toString();
                String passwordText = password.getEditText().getText().toString();

                if(mAuth.getCurrentUser() == null){
                    createAccount(emailText, passwordText);

                } else {
                    Log.d(TAG, "Error registering user");
                }
            }
        });



    }

    //change color of error field to red
    public void changefieldColortoRed(){
        username.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.design_default_color_error)));
        username.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.design_default_color_error)));
        username.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.design_default_color_error)));
        username.setHintTextColor(ColorStateList.valueOf((getColor(R.color.design_default_color_error))));
    }

    //change color of error field to green
    public void changefieldColortoGreen(){
        username.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.success)));
        username.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.success)));
        username.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.success)));
        username.setHintTextColor(ColorStateList.valueOf((getColor(R.color.success))));
    }


    // method to validate username
    public boolean validateUsername() {
        if (usernameEditText.getText().toString().trim().isEmpty()) {
           changefieldColortoRed();
            username.setError("Username already exists");
            username.setError("Required");
            return false;
        } else {
           changefieldColortoRed();
            username.setError("Username already exists");
            username.setErrorEnabled(false);
        }

        return true;

    }

    //Method to validate phone number
    public boolean validatephone(){
        if(phoneEditText.getText().toString().trim().isEmpty()){
            phone.setError("Required");
            return false;
        } else{
            String number = phoneEditText.getText().toString().trim();
            boolean isValid = Patterns.PHONE.matcher(number).matches();
            if(!isValid){
                phone.setError("Invalid phone number ex 08099923456");
                return false;
            } else{
                if(number.length() > 11){
                    phone.setError("Phone number should be 11 characters at most");
                    return false;
                } else {
                    phone.setErrorEnabled(false);
                }
            }
        }

        return true;

    }

    //Method to validate password
    public boolean validatepassword(){
        if(passwordEditText.getText().toString().trim().isEmpty()){
            password.setError("Required");
            return false;
        } else{
            int passwordLength = passwordEditText.getText().toString().trim().length();
            if(passwordLength < 6){
                password.setError("Password length should be at least 6 characters");
                return false;
            } else {
                password.setErrorEnabled(false);
            }

        }

        return true;

    }

    //Method to chevk if username already exist
    public void checkUsername(){

        String userId = usernameEditText.getText().toString().trim();
        Query mQuery = db.collection("customer _user")
                .whereEqualTo("username", userId);

        mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "checkingIfUsernameExist: checking if username exists");

                if(task.isSuccessful()){
                    for (DocumentSnapshot ds: task.getResult()){

                        String userName = ds.getString("username");
                        if(userName.equals(userId)){

                            Log.d(TAG, "checkingIfusernameExist: FOUND A MATCH: " +userName );
                            changefieldColortoRed();
                            username.setError("Username already exists");
                            registerBtn.setEnabled(false);
                        }

                    }

                }
                //Check if task contains any payload if no then update
                if(task.getResult().size() == 0){
                    try{
                        Log.d(TAG, "User not Exists");
                        changefieldColortoGreen();
                        username.setError("Username is available");
                        registerBtn.setEnabled(true);
                        //password.setEndIconMode();

                    }catch (NullPointerException e){
                        Log.e(TAG, "NullPointerException: "+ e.getMessage());
                    }

                }
            }



        });

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
                case R.id.usernameTextInputEditText:
                    validateUsername();
                    checkUsername();
                    break;

                case R.id.phoneTextInputEditText:
                    validatephone();
                    break;

                case R.id.passwordTextInputEditText:
                    validatepassword();
                    break;
            }
        }

    }

    //Method used to create account with user email and password using firebase authentication
    private void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userID = user.getUid();
                            storeUserDet();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            updateUI(null);
                        }
                    }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if (e instanceof FirebaseAuthUserCollisionException) {
                    notifyUser("Email already in use ");
                } else if(e instanceof FirebaseAuthWeakPasswordException){
                    String errorCode = ((FirebaseAuthWeakPasswordException) e).getReason();
                    notifyUser(errorCode);
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


     //Create method to store other user details
     public void storeUserDet(){
         Map<String, Object> userInfo = new HashMap<>();
         userInfo.put("uid", userID);
         userInfo.put("email", emailText);
         userInfo.put("firstname", firstnameText);
         userInfo.put("lastname", lastnameText);
         userInfo.put("username", usernameText);
         userInfo.put("Phone", phoneText);
         db.collection("customer _user")
                 .add(userInfo)
                 .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                     @Override
                     public void onSuccess(DocumentReference documentReference) {
                         Log.d(TAG, "DocumentSnapshot successfully written with ID: " + documentReference);
                         Toast.makeText(getApplicationContext(), "User Registered",
                                 Toast.LENGTH_SHORT).show();
                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Log.w(TAG, "Error writing document", e);
                         notifyUser(e.getLocalizedMessage());

                     }
                 });
     }






}