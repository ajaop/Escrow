package com.example.escrow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.escrow.DashboardActivity;
import com.example.escrow.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class OngoingActivity extends AppCompatActivity {

    private TabLayout ongoingTabLayout;
    private ViewPager2 ongoingViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing);

        ongoingTabLayout = findViewById(R.id.ongoingTabLayout);
        ongoingViewPager2 = findViewById(R.id.ongoinviewPager2);

        ongoingTabLayout.addTab(ongoingTabLayout.newTab().setText("Ongoing"));
        ongoingTabLayout.addTab(ongoingTabLayout.newTab().setText("Completed"));
        ongoingTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ActivitiesTabdapter adapter = new ActivitiesTabdapter(this, getSupportFragmentManager(), getLifecycle(), ongoingTabLayout.getTabCount());
        ongoingViewPager2.setAdapter(adapter);
        ongoingTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ongoingViewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        ongoingViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
                ongoingTabLayout.selectTab(ongoingTabLayout.getTabAt(position));
            }
        });




        //Onclick listener for bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.activities);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.activities:
                        return true;
                }



                return false;
            }
        });
    }
}