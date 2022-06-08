package com.example.escrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.escrow.quickActionRecyclerView.ActionItem;
import com.example.escrow.quickActionRecyclerView.RecyclerViewAdapter;
import com.example.escrow.quickActionRecyclerView.RecyclerViewItemDecorator;
import com.example.escrow.viewPager2Card.CardItem;
import com.example.escrow.viewPager2Card.CardViewPager2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy {

    private ViewPager2 mViewPager;
    private RecyclerView mrecyclerView;
    private TabLayout tabLayout;
    private CardViewPager2Adapter mCardAdapter;
    private RecyclerViewAdapter recyclerAdapter;


    private boolean mShowingFragments = false;


    //FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //view pager for purple card
        mViewPager = (ViewPager2) findViewById(R.id.pager);
        //view pager for Action Card
        mrecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //Item class for purple card
        List<CardItem> cardItem = new ArrayList<>();

        //Item class for action card
        ArrayList<ActionItem> actionItems = new ArrayList<>();

        //Object for CardVIewPager2Adapter
        mCardAdapter = new CardViewPager2Adapter(cardItem);
        cardItem.add(new CardItem(CardItem.LayoutCardOne, R.string.in_escrow, R.string.escorBalance, R.string.escrowBalanceCredit,R.string.escrowBalanceDebit, R.string.view_transaction, R.drawable.ic_path1, R.drawable.ic_path2));
        cardItem.add(new CardItem(CardItem.LayoutCardTwo, R.string.availableBal, R.string.availableBalanceCredit, R.string.openWallet));

        //Object for QuickActionAdapter
        recyclerAdapter = new RecyclerViewAdapter(actionItems, this);
        actionItems.add(new ActionItem(R.string.initiate));
        actionItems.add(new ActionItem(R.string.disp));
        actionItems.add(new ActionItem(R.string.deposit));
        actionItems.add(new ActionItem(R.string.wihdraw));
        actionItems.add(new ActionItem(R.string.wihdraw));

        //Set CardAdapter as viewpager2adapter
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.setClipToPadding(false);
        mViewPager.setClipChildren(false);
        mViewPager.setOffscreenPageLimit(3);

        int pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        int offsetPx = getResources().getDimensionPixelOffset(R.dimen.offset);

        mViewPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                ViewPager2 viewPager = (ViewPager2) page.getParent().getParent();
                float offset = position * -(2 * offsetPx + pageMarginPx);

                if (viewPager.getOrientation()  == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.setTranslationX(-offset);
                    } else {
                        page.setTranslationX(offset);
                    }
                } else {
                    page.setTranslationY(offset);
                }
            }
        });

        new TabLayoutMediator(tabLayout, mViewPager, this).attach();

        //Set QuickActionAdapter as viewpager2adapter
        mrecyclerView.setAdapter(recyclerAdapter);
        // set a GridLayoutManager with 3 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mrecyclerView.setLayoutManager(gridLayoutManager);


        //Use ItemDecorator class to adjust spacing between cards
        RecyclerViewItemDecorator itemDecoration = new RecyclerViewItemDecorator(getApplicationContext(), R.dimen.item_offset);
        mrecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
    }
}
