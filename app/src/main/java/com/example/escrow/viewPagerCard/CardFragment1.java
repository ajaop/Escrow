package com.example.escrow.viewPagerCard;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.escrow.R;
import com.google.android.material.card.MaterialCardView;

public class CardFragment1 extends Fragment {

    private CardView mCardView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card1, container, false);
        mCardView = (CardView) view.findViewById(R.id.Greencard1);
        TextView title = (TextView) view.findViewById(R.id.availableBal);

        return view;
    }

    public CardView getCardView() {
        return mCardView;
    }
}