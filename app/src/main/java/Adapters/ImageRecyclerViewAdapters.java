package Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import Info.EventsData;
import ac.nita.advaitam4.HomePart2;
import ac.nita.advaitam4.R;
import ac.nita.advaitam4.ScrollingActivity;

/**
 * Created by HRITIK on 12/24/2017.
 */

public class ImageRecyclerViewAdapters extends RecyclerView.Adapter<ImageRecyclerViewAdapters.ViewHolder> {

    private List<EventsData> eventsData ;
    private Integer [] imagesPosition;
    Context mContext;
    int position = 0;

    public ImageRecyclerViewAdapters(Context context,List<EventsData> eventsData){
        this.mContext = context;
//        this.ImagesUri = uriS;
        this.eventsData = eventsData;
    }

    @Override
    public ImageRecyclerViewAdapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ImageRecyclerViewAdapters.ViewHolder holder, int position) {
        holder.images.setImageResource(imagesPosition[position]);
    }

    @Override
    public int getItemCount() {
        return imagesPosition.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View v;
        CardView cardView;
        ImageView images;
        public ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            images = itemView.findViewById(R.id.cardImage);
            mContext = itemView.getContext();
            cardView = itemView.findViewById(R.id.card_view);

        }

        @Override
        public void onClick(View view) {
            final Intent intent;
            position = getAdapterPosition();
            Bundle b = new Bundle();
            b.putString("position",imagesPosition.toString());
            intent =  new Intent(mContext, ScrollingActivity.class);

            intent.putExtras(b);
            mContext.startActivity(intent);

        }
    }
}

