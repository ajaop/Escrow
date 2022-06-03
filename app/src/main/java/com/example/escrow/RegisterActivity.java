package com.example.escrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity2";
    Button nextbtn ;
    TextInputLayout email, firstname, lastname;
    TextInputEditText emailEditText, firstnameEditText, lastnameEditText;
    String emailText, firstnameText, lastnameText;
    //Access cloud firestore instance from your activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        

        nextbtn = findViewById(R.id.nextBtn);
        email = findViewById(R.id.emailOutlinedTextField);
        firstname = findViewById(R.id.firstNameOutlinedTextField);
        lastname = findViewById(R.id.lastNameOutlinedTextField);

        emailEditText = findViewById(R.id.emailTextInputEditText);
        firstnameEditText = findViewById(R.id.firstnameTextInputEditText);
        lastnameEditText = findViewById(R.id.lastnameTextInputEditText);

        //Create object for text watcher class which is used with the textinputedittext
        emailEditText.addTextChangedListener(new ValidationTextWatcher(emailEditText));
        firstnameEditText.addTextChangedListener(new ValidationTextWatcher(firstnameEditText));
        lastnameEditText.addTextChangedListener(new ValidationTextWatcher(lastnameEditText));
        

            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nextbtn.setEnabled(false);
                    if (!validateEmail() || !validatefirstName() || !validatelastName()) {
                        return;
                    }

                    emailText =  email.getEditText().getText().toString().trim();
                    firstnameText =  firstname.getEditText().getText().toString().trim();
                    lastnameText =  lastname.getEditText().getText().toString().trim();


                    Intent intent = new Intent(v.getContext(), RegisterActivity2.class);
                    intent.putExtra("email", emailText);
                    intent.putExtra("firstname", firstnameText);
                    intent.putExtra("lastname", lastnameText);

                    startActivity(intent);


                }
            });



    }

    //change color of error field to red
    public void changefieldColortoRed(){
        email.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.design_default_color_error)));
        email.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.design_default_color_error)));
        email.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.design_default_color_error)));
        email.setHintTextColor(ColorStateList.valueOf((getColor(R.color.design_default_color_error))));
    }

    //change color of error field to green
    public void changefieldColortoGreen(){
        email.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.success)));
        email.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.success)));
        email.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.success)));
        email.setHintTextColor(ColorStateList.valueOf((getColor(R.color.success))));
    }




    // method to validateEmail email address
    public boolean validateEmail() {
        if (emailEditText.getText().toString().trim().isEmpty()) {
            changefieldColortoRed();
           email.setError("Required");
           return false;
        } else {
            String emailId = emailEditText.getText().toString();
            Boolean isValid = Patterns.EMAIL_ADDRESS.matcher(emailId).matches();

            if (!isValid) {
                changefieldColortoRed();
                email.setError("Invalid Email Address, ex: abc@example.com");
                return false;
            } else {
                checkemail();
                email.setErrorEnabled(false);
            }

        }

        return true;

    }

    //Method to ensure firstname has input else show error
    public boolean validatefirstName(){
        if(firstnameEditText.getText().toString().trim().isEmpty()){
            firstname.setError("Required");
            return false;
        } else{
            firstname.setErrorEnabled(false);
        }

        return true;

    }

    //Method to ensure lastname field have input else show error
    public boolean validatelastName(){
        if(lastnameEditText.getText().toString().trim().isEmpty()){
            lastname.setError("Required");
            return false;
        } else{
            lastname.setErrorEnabled(false);
        }

        return true;

    }

    //Method to check if email already exist
    public void checkemail(){

        String emailId = emailEditText.getText().toString().trim();
        Query mQuery = db.collection("customer _user")
                .whereEqualTo("email", emailId);

        mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "checkingIfemailExist: checking if email exists");

                if(task.isSuccessful()){
                    for (DocumentSnapshot ds: task.getResult()){

                        String mail = ds.getString("email");
                        if(mail.equals(emailId)){

                            Log.d(TAG, "checkingIfemailExist: FOUND A MATCH: " +mail );
                            changefieldColortoRed();
                            email.setError("email already exists");
                            nextbtn.setEnabled(false);
                        }

                    }

                }
                //Check if task contains any payload if no then update
                if(task.getResult().size() == 0){
                    try{
                        Log.d(TAG, "email not Exists");
                        changefieldColortoGreen();
                        email.setError("email is available");
                        nextbtn.setEnabled(true);
                        //password.setEndIconMode();

                    }catch (NullPointerException e){
                        Log.e(TAG, "NullPointerException: "+ e.getMessage());
                    }

                }
            }



        });

    }



    //Text watcher class which will be used with the validateEmail method
    private class ValidationTextWatcher implements TextWatcher{
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
                    //checkemail();
                    break;

                case R.id.firstnameTextInputEditText:
                    validatefirstName();
                    break;

                case R.id.lastnameTextInputEditText:
                    validatelastName();
                    break;
            }
        }

    }




}