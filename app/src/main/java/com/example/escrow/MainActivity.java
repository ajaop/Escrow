package com.example.escrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.escrow.onboarding.Onboarding1Fragment;
import com.example.escrow.onboarding.Onboarding2Fragment;
import com.example.escrow.onboarding.Onboarding3Fragment;
import com.example.escrow.onboarding.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button lgnbtn,regbtn;
    private  int current;
    private  static final  String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);

        lgnbtn =  findViewById(R.id.onbLoginBtn);
        regbtn =  findViewById(R.id.onbRegisterBtn);


        // setting up the adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // add the fragments
        viewPagerAdapter.add(new Onboarding1Fragment());
        viewPagerAdapter.add(new Onboarding2Fragment());
        viewPagerAdapter.add(new Onboarding3Fragment());



        // Set the adapter
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);


        //Get current page
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //change button colour when swipe changes
                switch (position){
                    case 0:
                        lgnbtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.loginBtn));
                        lgnbtn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.onbTitle));
                        regbtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.onbTitle));
                        regbtn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.registerBtnText));
                        Log.i(TAG, "my position is : " + position);
                        break;

                    case 1:
                        lgnbtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.loginBtn2));
                        lgnbtn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.registerBtn2));
                        regbtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.registerBtn2));
                        regbtn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.registerBtnText));
                        Log.i(TAG, "my position is : " + position);
                        break;

                    case 2:
                        lgnbtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.loginBtn2));
                        lgnbtn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.registerBtn2));
                        regbtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.registerBtn2));
                        regbtn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.registerBtnText));
                        Log.i(TAG, "my position is : " + position);
                        break;


                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



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


    }


}

