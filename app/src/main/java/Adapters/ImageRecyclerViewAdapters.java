package Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import Info.EventsData;
import ac.nita.advaitam4.Categories;
import ac.nita.advaitam4.HomePart2;
import ac.nita.advaitam4.R;
import ac.nita.advaitam4.ScrollingActivity;
import ac.nita.advaitam4.TabbedActivity;

public class ImageRecyclerViewAdapters extends RecyclerView.Adapter<ImageRecyclerViewAdapters.ViewHolder> {

    private ProgressBar progressBar;
    private List<EventsData> eventsData = new ArrayList<>();
    private Integer[] imagesPosition = {R.drawable.gate, R.drawable.dj, R.drawable.cultural, R.drawable.robot, R.drawable.sports};
    private List<Uri> imagesPosition1 = new ArrayList<>();
    Context mContext;
    int event_pos;

    int position = 0;

    public ImageRecyclerViewAdapters(Context context, List<EventsData> eventsData,int event_pos) {
        this.mContext = context;
        this.eventsData = eventsData;
        this.event_pos = event_pos;
        if (eventsData != null) {
            for (EventsData op : eventsData) {
                   imagesPosition1.add(Uri.parse(op.getImageUri()));
            }
        }
//        Log.d("tag", "myTag " + imagesPosition1.size());

    }

    @Override
    public ImageRecyclerViewAdapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ImageRecyclerViewAdapters.ViewHolder holder, int position) {

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .placeholder(R.drawable.background)
                .error(R.drawable.background);
        Glide.with(mContext).load(imagesPosition1.get(position)).apply(options).into(holder.images);

//        Log.d("tag", "myTag " + imagesPosition1.get(position));
}

    @Override
    public int getItemCount() {
//        Log.d("tag", "myTag " + imagesPosition1.size());
        return imagesPosition1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View v;
        CardView cardView;
        ImageView images;
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            images = itemView.findViewById(R.id.cardImage);
            mContext = itemView.getContext();
            cardView = itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(mContext, "clicked " + eventsData.get(getAdapterPosition()).getDescription(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, TabbedActivity.class);
//                    intent.putExtra("object",)
                    intent.putExtra("object",eventsData.get(getAdapterPosition()));
                    intent.putExtra("reference",getAdapterPosition());
                    intent.putExtra("eventPos",event_pos);
                    intent.putExtra("KEY",mContext.getResources().getResourceEntryName(view.getId()));
                    mContext.startActivity(intent);

                }
            });
        }

    }
}
