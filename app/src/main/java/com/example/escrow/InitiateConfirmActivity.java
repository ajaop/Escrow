package com.example.escrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.internal.Platform;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

public class InitiateConfirmActivity extends AppCompatActivity {

    private String Name, Amount, EscrowLength, InspectionPeriod;
    private double charges, amount;
    private TextView nameTextView, amountTextView, escrowLengthTextView, inspectionPeriodTextView, chargesTextView;
    private Button proceedBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_confirm);

        //Initialize Textviews
        nameTextView = findViewById(R.id.nameOfUserText);
        amountTextView = findViewById(R.id.amountText);
        escrowLengthTextView = findViewById(R.id.escrowLengthText);
        inspectionPeriodTextView = findViewById(R.id.inspectionPeriodText);
        chargesTextView = findViewById(R.id.chargesText);

        //Initialize button
        proceedBtn = findViewById(R.id.proceedBtn);

        Intent intent = getIntent();
        Name = intent.getStringExtra("Username");
        Amount = intent.getStringExtra("Amount");
        EscrowLength = intent.getStringExtra("EscrowLength");
        InspectionPeriod = intent.getStringExtra("InspectionPeriod");

        //calculate charges as 10% of amount
        amount = Integer.parseInt(Amount.replace(",", ""));

        charges = (0.00175 * amount);

        //set textview to appropriatevariable
        nameTextView.setText(Name);
        amountTextView.setText("₦"+Amount+".00");
        escrowLengthTextView.setText(EscrowLength);
        inspectionPeriodTextView.setText(InspectionPeriod);
        chargesTextView.setText("₦"+String.valueOf(charges)+"0");

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), InitiateSuccessActivity.class);
                intent1.putExtra("Name", Name);
                startActivity(intent1);
            }
        });
    }

}
