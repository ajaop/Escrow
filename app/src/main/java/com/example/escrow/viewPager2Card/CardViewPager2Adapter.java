package com.example.escrow.viewPager2Card;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.escrow.R;
import com.example.escrow.quickActionRecyclerView.RecyclerViewAdapter;

import java.util.List;

import static com.example.escrow.viewPager2Card.CardItem.LayoutCardOne;
import static com.example.escrow.viewPager2Card.CardItem.LayoutCardTwo;


public class CardViewPager2Adapter extends RecyclerView.Adapter<CardViewPager2Adapter.CardViewHolder> {

    private List<CardItem> cardItems;

    public CardViewPager2Adapter(List<CardItem> cardItems){
        this.cardItems = cardItems;
    }

    // Override the getItemViewType method.
    // This method uses a switch statement
    // to assign the layout to each item
    // depending on the viewType passed

    @Override
    public int getItemViewType(int position)
    {
        switch (cardItems.get(position).getViewType()) {
            case 0:
                return LayoutCardOne;
            case 1:
                return LayoutCardTwo;
            default:
                return -1;
        }
    }


    // Create classes for each layout ViewHolder
    class LayoutOneViewHolder extends CardViewHolder {

         TextView titleTextView, subtitle1TextView, subtitle2TextView, subtitle3TextView, linkTextView;
        ImageView image1ImageView, image2ImageView;


        public LayoutOneViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find the Views
            titleTextView = itemView.findViewById(R.id.in_escrow);
            subtitle1TextView = itemView.findViewById(R.id.balance);
            subtitle2TextView = itemView.findViewById(R.id.escrowBalanceCredit);
            subtitle3TextView = itemView.findViewById(R.id.escrowBalanceDebit);
            linkTextView = itemView.findViewById(R.id.view_transa);
            image1ImageView = itemView.findViewById(R.id.greenArrow);
            image2ImageView = itemView.findViewById(R.id.redArrow);


        }

        private void setView(int title, int subtitle1, int subtitle2, int subtitle3, int link, int image1, int image2){
           titleTextView.setText(title);
           subtitle1TextView.setText(subtitle1);
           subtitle2TextView.setText(subtitle2);
            subtitle3TextView.setText(subtitle3);
            linkTextView.setText(link);
            image1ImageView.setImageResource(image1);
            image2ImageView.setImageResource(image2);

        }

    }

    // similarly a class for the second layout is also
    // created.
    class LayoutTwoViewHolder extends CardViewHolder {

        private TextView title_2TextView, subtitle_2TextView, link_2TextView;

        public LayoutTwoViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find the Views
            title_2TextView = itemView.findViewById(R.id.availableBal);
            subtitle_2TextView = itemView.findViewById(R.id.availableBalanceCredit);
            link_2TextView = itemView.findViewById(R.id.open_wallet);

        }

        private void setView(int title_2, int subtitle_2, int link_2){
            title_2TextView.setText(title_2);
            subtitle_2TextView.setText(subtitle_2);
            link_2TextView.setText(link_2);

        }

    }


    // This method returns our layout
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {
        switch (viewType) {
            case LayoutCardOne:
                View layoutOne = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card1, parent, false);
                return new LayoutOneViewHolder(layoutOne);

            case LayoutCardTwo:
                View layoutTwo = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card2, parent, false);
                return new LayoutTwoViewHolder(layoutTwo);

            default:
                return null;

        }

    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        // This will set the images in imageview
        switch (cardItems.get(position).getViewType()){
            case LayoutCardOne:
                int title = cardItems.get(position).getTitle();
                int subtitle1 = cardItems.get(position).getSubtitle1();
                int subtitle2 = cardItems.get(position).getSubtitle2();
                int subtitle3 = cardItems.get(position).getSubtitle3();
                int link = cardItems.get(position).getLink();
                int image1 = cardItems.get(position).getImage1();
                int image2 = cardItems.get(position).getImage2();
                ((LayoutOneViewHolder)holder).setView(title,subtitle1,subtitle2,subtitle3,link,image1,image2);
                break;

            case LayoutCardTwo:
                int title_2 = cardItems.get(position).getTitle_2();
                int subtitle_2 = cardItems.get(position).getSubtitle_2();
                int link_2 = cardItems.get(position).getLink_2();
                ((LayoutTwoViewHolder)holder).setView(title_2,subtitle_2,link_2);
                break;

            default:
                return;
        }
    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder{
        CardViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }


}
