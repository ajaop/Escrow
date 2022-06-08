package com.example.escrow.quickActionRecyclerView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.escrow.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>  {

    private ArrayList<ActionItem> actionItems;
    private Context mcontext;

    public RecyclerViewAdapter(ArrayList<ActionItem> actionItems, Context mcontex){
        this.actionItems = actionItems;
        this.mcontext = mcontex;
    }

    // This method returns our layout
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_quick_action, parent, false);
                return new RecyclerViewHolder(view);
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // This will set the images in imageview
       holder.setCardData(actionItems.get(position));
    }

    @Override
    public int getItemCount() {
        return actionItems.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
            private TextView titleTextView;

        public RecyclerViewHolder(@NonNull View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.Title);

        }

        void setCardData(ActionItem actionItem){
            titleTextView.setText(actionItem.getTitle());
        }

    }

}
