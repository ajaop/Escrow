package com.example.escrow.activities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ActivitiesTabdapter extends FragmentStateAdapter {

   Context context;
   int totalTabs;
   public ActivitiesTabdapter(@NonNull Context c, FragmentManager fm, Lifecycle lifecycle, int totalTabs) {
      super(fm, lifecycle);
      context = c;
      this.totalTabs = totalTabs;
   }


   @NonNull
   @Override
   public Fragment createFragment(int position) {
      switch (position) {
         case 0:
            OngoingFragment ongoingFragment = new OngoingFragment();
            return ongoingFragment;
         case 1:
            CompletedFragment completedFragment = new CompletedFragment();
            return completedFragment;
         default:
            return null;
      }
   }

   @Override
   public int getItemCount() {
      return totalTabs;
   }

}
