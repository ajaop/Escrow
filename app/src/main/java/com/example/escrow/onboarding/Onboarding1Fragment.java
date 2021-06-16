package com.example.escrow.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.escrow.LoginActivity;
import com.example.escrow.R;
import com.example.escrow.RegisterActivity;


public class Onboarding1Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_onboarding1, container, false);

        /*
        Button lgnbtn = (Button) view.findViewById(R.id.onbLoginBtn);
        Button regbtn = (Button) view.findViewById(R.id.onbRegisterBtn);

        lgnbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

        regbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });

*/
        return view;
    }
}