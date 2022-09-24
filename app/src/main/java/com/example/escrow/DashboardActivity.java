package com.example.escrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.escrow.activities.OngoingActivity;
import com.example.escrow.quickActionRecyclerView.ActionItem;
import com.example.escrow.quickActionRecyclerView.RecyclerViewAdapter;
import com.example.escrow.quickActionRecyclerView.RecyclerViewItemDecorator;
import com.example.escrow.viewPager2Card.CardItem;
import com.example.escrow.viewPager2Card.CardViewPager2Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy {

    private static final String TAG = "DashboardActivity";
    private ViewPager2 mViewPager;
    private RecyclerView mrecyclerView;
    private TabLayout tabLayout;
    private CardViewPager2Adapter mCardAdapter;
    private RecyclerViewAdapter recyclerAdapter;
    private FirebaseFirestore mFirestore;
    private String username;
    private TextView nameTextView;


    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Initialize mauth
        auth = FirebaseAuth.getInstance();
        updateUI(auth.getCurrentUser());

        //Initialize username TextView
        nameTextView = (TextView) findViewById(R.id.nameText);

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
         recyclerAdapter = new RecyclerViewAdapter(getApplicationContext(),actionItems);
        actionItems.add(new ActionItem(R.string.initiate));
        actionItems.add(new ActionItem(R.string.disp));
        actionItems.add(new ActionItem(R.string.deposit));
        actionItems.add(new ActionItem(R.string.wihdraw));
        actionItems.add(new ActionItem(R.string.wihdraw));

        //Set CardAdapter as viewpager2adapter
        mViewPager.setAdapter(mCardAdapter);

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

        recyclerAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(ActionItem items) {
                if(items.getTitle() == R.string.initiate){
                    Intent intent1 = new Intent(getApplicationContext(), InitiateTransactionActivity.class);
                    //TODO: put actual activity to move to
                    startActivity(intent1);
                } else if(items.getTitle() == R.string.disp){

                } else if(items.getTitle() == R.string.deposit){

                } else if(items.getTitle() == R.string.wihdraw){

                }
            }
        });

        //Use ItemDecorator class to adjust spacing between cards
        RecyclerViewItemDecorator itemDecoration = new RecyclerViewItemDecorator(getApplicationContext(), R.dimen.item_offset);
        mrecyclerView.addItemDecoration(itemDecoration);

        //Onclick listener for bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.activities:
                        startActivity(new Intent(getApplicationContext(), OngoingActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }



                return false;
            }
        });

    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
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

    //Method to get logged in user username
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
                        nameTextView.setText(username);
                        Log.d(TAG, "gettingUsername: Getting username from DB successfull" + username);

                    }

                } else{
                    Log.d(TAG, "gettingUsername: Getting username from DB failed");
                }
            }
        });

    }



}
