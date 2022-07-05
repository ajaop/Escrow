package com.example.escrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class InitiateSuccessActivity extends AppCompatActivity {

    private static final String TAG="InitiateSuccessActivity";
    private String Name,extraText,extraText1,username;
    private Button awesomeBtn;
    private TextView extraTextView;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_success);

        //Initialize mAuth and Check if user is logged in
        mAuth = FirebaseAuth.getInstance();
        updateUI(mAuth.getCurrentUser());

        //Initialize textview
        extraTextView = findViewById(R.id.extraSuccessTextView);

        //Initialize button
        awesomeBtn = findViewById(R.id.awesomeBtn);

        //Initialize stringd
        extraText="You have requested to enter an escrow transaction with";
        extraText1=". A request has been sent and you will be notified.";

        //Get user name from intent
        Intent intent = getIntent();
        Name = intent.getStringExtra("Name");

        //Set the textview with the initialized strings
        extraTextView.setText(Html.fromHtml(extraText +
                "<b>"+"<font color=\"#202046\">" + "<br />" + Name + "</color>"+"</b>" + extraText1));

        awesomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent1);
            }
        });


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
    public void getUser(FirebaseUser user) {
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

                } else {
                    Log.d(TAG, "gettingUsername: Getting username from DB failed");
                }
            }
        });
    }
}