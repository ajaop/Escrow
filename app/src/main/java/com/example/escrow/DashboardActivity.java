package com.example.escrow;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;

import com.example.escrow.viewPagerCard.CardFragment1;
import com.example.escrow.viewPagerCard.CardFragmentPagerAdapter;
import com.example.escrow.viewPagerCard.CardItem;
import com.example.escrow.viewPagerCard.CardPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private CardPagerAdapter mCardAdapter;
    private CardFragmentPagerAdapter mFragmentCardAdapter;

    private boolean mShowingFragments = false;


    //FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem( R.string.in_escrow, R.string.escorBalance, R.string.escrowBalanceCredit,R.string.escrowBalanceDebit, R.string.view_transaction, R.drawable.ic_path1, R.drawable.ic_path2, CardItem.LayoutCardOne));
      //  mCardAdapter.addCardItem(new CardItem( R.string.in_escrow, R.string.escorBalance, R.string.view_transaction, CardItem.LayoutCardTwo));

      //  mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
        //        dpToPixels(10, this));


        tabLayout.setupWithViewPager(mViewPager, true);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setOffscreenPageLimit(3);

        int pagerPadding = 86;
        mViewPager.setClipToPadding(false);
        mViewPager.setPadding(pagerPadding, 0, pagerPadding, 0);



    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


}
