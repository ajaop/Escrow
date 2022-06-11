package com.example.escrow.quickActionRecyclerView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.escrow.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>  {

    private Context mContext;
    private ArrayList<ActionItem> actionItems;
     OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener {
        public void onClick(ActionItem items);
    }


    public RecyclerViewAdapter(Context context, ArrayList<ActionItem> actionItems){
        this.actionItems = actionItems;
        this.mContext = context;
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

       ActionItem data = actionItems.get(position);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               listener.onClick(data);
           }
       });
    }

    @Override
    public int getItemCount() {
        return actionItems.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
            private TextView titleTextView;
            private CardView cardView;
            private OnItemClickListener listener;

        public RecyclerViewHolder(@NonNull View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.Title);
            cardView = itemView.findViewById(R.id.actionBox);
        }

        void setCardData(ActionItem actionItem){
            titleTextView.setText(actionItem.getTitle());

        }

    }

}
