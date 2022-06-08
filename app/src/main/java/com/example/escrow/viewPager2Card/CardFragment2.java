package com.example.escrow.viewPager2Card;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.escrow.R;
import com.google.android.material.card.MaterialCardView;

public class CardFragment2 extends Fragment {

    private CardView mCardView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_card2, container, false);
        mCardView = (MaterialCardView) view.findViewById(R.id.Greencard1);

        return view;
    }


}