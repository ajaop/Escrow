package com.example.escrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.escrow.quickActionRecyclerView.RecyclerViewAdapter;
import com.example.escrow.viewPager2Card.CardViewPager2Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class InitiateTransactionActivity extends AppCompatActivity {

    private static final String TAG = "InitiateTransactionActivity";
    private TextInputLayout usernameTextLayout, amountTextLayout, escrowLengthTextLayout, inspectionPeriodTextLayout;
    private TextInputEditText amountEditText,usernameEditText;
    private AutoCompleteTextView drop1,drop2;
    private Button proccedBtn;
    private String[] escrowLength = {"5 days", "15 days", "30 days", "45 days", "60 days", "75 days", "90 days", "105 days", "130 days", "160 days", "230 days", "260 days", "320 days", "365 days"};
    private String[] inspectionPeriod = {"2 days", "5 days", "7 days", "10 days", "14 days", "17 days", "21 days", "24 days", "27 days", "30 days", "45 days", "50 days", "60 days", "70 days"};
    private FirebaseFirestore mFirestore;
    private String username;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_transaction);

        //Initialize mAuth and Check if user is logged in
        mAuth = FirebaseAuth.getInstance();
        updateUI(mAuth.getCurrentUser());

        //Initiate edit text variables
        amountEditText =  findViewById(R.id.amountTextInputEditText);
        usernameEditText = findViewById(R.id.usernameTextInputEditText);

        //Initiate text inputlayout variables
        usernameTextLayout = findViewById(R.id.usernameOutlinedTextField);
        amountTextLayout =  findViewById(R.id.amountOutlinedTextField);
        escrowLengthTextLayout =  findViewById(R.id.escrowOutlinedTextField);
        inspectionPeriodTextLayout =  findViewById(R.id.inspectionPeriodOutlinedTextField);


        //Edit text drop down 1 and 2
        drop1 = (AutoCompleteTextView) findViewById(R.id.dropdown1);
        drop2 = (AutoCompleteTextView) findViewById(R.id.dropdown2);

        //Initiate button variable
        proccedBtn = (Button) findViewById(R.id.proceedBtn);


        //Create object for text watcher class which is used to ensure values are not empty
        usernameEditText.addTextChangedListener(new InitiateTransactionActivity.ValidationTextWatcher(usernameEditText));
        amountEditText.addTextChangedListener(new InitiateTransactionActivity.ValidationTextWatcher(amountEditText));
        drop1.addTextChangedListener(new InitiateTransactionActivity.ValidationTextWatcher(drop1));
        drop2.addTextChangedListener(new InitiateTransactionActivity.ValidationTextWatcher(drop2));

        //Use text Changed listener to add comma to amountEditText
        amountEditText.addTextChangedListener(new InitiateTransactionActivity.ValidationTextWatcher(amountEditText));


        //Array adapter for dropdown1 and 2
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.list_item, escrowLength);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.list_item, inspectionPeriod);

        //Set the adapter
        drop1.setAdapter(adapter1);
        drop2.setAdapter(adapter2);

        proccedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateUsername() || !validateAmount() || !validateEscrowLength() || !validateAInspectionPeriod()) {
                    return;
                } else {
                    String inputName, inputAmount, inputEscrowLength, inputInspectionPeriod;
                    inputName = usernameTextLayout.getEditText().getText().toString();
                    inputAmount = amountTextLayout.getEditText().getText().toString();
                    inputEscrowLength = escrowLengthTextLayout.getEditText().getText().toString();
                    inputInspectionPeriod = inspectionPeriodTextLayout.getEditText().getText().toString();

                    Intent intent1 = new Intent(getApplicationContext(), InitiateConfirmActivity.class);
                    intent1.putExtra("Username", inputName);
                    intent1.putExtra("Amount", inputAmount);
                    intent1.putExtra("EscrowLength", inputEscrowLength);
                    intent1.putExtra("InspectionPeriod", inputInspectionPeriod);
                    startActivity(intent1);


                }
            }
        });
    }


    //change color of error field to red
    public void changefieldColortoRed(){
        usernameTextLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.design_default_color_error)));
        usernameTextLayout.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.design_default_color_error)));
        usernameTextLayout.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.design_default_color_error)));
        usernameTextLayout.setHintTextColor(ColorStateList.valueOf((getColor(R.color.design_default_color_error))));
    }

    //change color of error field to green
    public void changefieldColortoGreen(){
        usernameTextLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.success)));
        usernameTextLayout.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.success)));
        usernameTextLayout.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.success)));
        usernameTextLayout.setHintTextColor(ColorStateList.valueOf((getColor(R.color.success))));
    }


    // method to validate username
    public boolean validateUsername() {
        if (usernameEditText.getText().toString().trim().isEmpty()) {
            changefieldColortoRed();
            usernameTextLayout.setError("Required");
            return false;
        } else {
            changefieldColortoGreen();
            usernameTextLayout.setError("Username exists");
            usernameTextLayout.setErrorEnabled(false);
        }

        return true;

    }

    // method to validateEmail amount
    public boolean validateAmount() {
        if (amountEditText.getText().toString().trim().isEmpty()) {
            amountTextLayout.setError("Required");
            return false;
        } else {
            amountTextLayout.setErrorEnabled(false);
        }
        return true;

    }


    // method to validateEmail escrow length
    public boolean validateEscrowLength() {

        if (drop1.getText().toString().trim().isEmpty()) {
            escrowLengthTextLayout.setError("Required");
            return false;
        } else {
            escrowLengthTextLayout.setErrorEnabled(false);
        }
        return true;

    }


    // method to validateEmail inspection period
    public boolean validateAInspectionPeriod() {
        if (drop2.getText().toString().trim().isEmpty()) {
            inspectionPeriodTextLayout.setError("Required");
            return false;
        } else {
            inspectionPeriodTextLayout.setErrorEnabled(false);
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
            amountEditText.removeTextChangedListener(this);

            try {
                String originalString = editable.toString();

                Long longval;
                if (originalString.contains(",")) {
                    originalString = originalString.replaceAll(",", "");
                }
                longval = Long.parseLong(originalString);

                DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                formatter.applyPattern("#,###,###,###");
                String formattedString = formatter.format(longval);

                //setting text after format to EditText
                amountEditText.setText(formattedString);
                amountEditText.setSelection(amountEditText.getText().length());
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

            amountEditText.addTextChangedListener(this);

            switch (view.getId()){
                case R.id.usernameTextInputEditText:
                    validateUsername();
                    checkUsername();
                    break;

                case R.id.amountTextInputEditText:
                    validateAmount();
                    break;

                case R.id.dropdown1:
                    validateEscrowLength();
                    break;

                case R.id.dropdown2:
                    validateAInspectionPeriod();
                    break;
            }
        }

    }


    public void updateUI(FirebaseUser user){
        if(user == null){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            getUser(user);
        }

    }

    //Method to get Logged in user usernmae
    public void getUser(FirebaseUser user){
        String UserId = user.getUid();
        mFirestore = FirebaseFirestore.getInstance();

        Query mQuery = mFirestore.collection("customer _user")
                .whereEqualTo("uid", UserId);


        mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "gettingUsername: Getting username from DB");

                if (task.isSuccessful()) {
                    for (DocumentSnapshot ds : task.getResult()) {
                        username = ds.getString("username");

                        Log.d(TAG, "gettingUsername: Getting username from DB successfull" + username);

                    }

                } else{
                    Log.d(TAG, "gettingUsername: Getting username from DB failed");
                }
            }
        });

    }


    //Method to chevk if username  exist
    public void checkUsername(){

        String userId = usernameEditText.getText().toString().trim();
        Query mQuery = mFirestore.collection("customer _user")
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
                            changefieldColortoGreen();
                            usernameTextLayout.setError("Username exists");
                            proccedBtn.setEnabled(true);

                        }

                    }

                }
                //Check if task contains any payload if no then update
                if(task.getResult().size() == 0){
                    try{
                        Log.d(TAG, "User not Exists");
                        changefieldColortoRed();
                        usernameTextLayout.setError("Username does not exist");
                        proccedBtn.setEnabled(false);
                        //password.setEndIconMode();

                    }catch (NullPointerException e){
                        Log.e(TAG, "NullPointerException: "+ e.getMessage());
                    }

                }
            }



        });

    }



}
