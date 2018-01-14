package Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ac.nita.advaitam4.R;

/**
 * Created by sourav9674 on 1/1/2018.
 */

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.MyHolder>{


    Activity context;
    String[][] imageUrls;


    RequestOptions options = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .skipMemoryCache(false)
            .placeholder(R.mipmap.ic_launcher);



    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10;

        public MyHolder(View view) {
            super(view);

            imageView1 = (ImageView)view.findViewById(R.id.image_view1);
            imageView2 = (ImageView)view.findViewById(R.id.image_view2);
            imageView3 = (ImageView)view.findViewById(R.id.image_view3);
            imageView4 = (ImageView)view.findViewById(R.id.image_view4);
            imageView5 = (ImageView)view.findViewById(R.id.image_view5);
            imageView6 = (ImageView)view.findViewById(R.id.image_view6);
            imageView7 = (ImageView)view.findViewById(R.id.image_view7);
            imageView8 = (ImageView)view.findViewById(R.id.image_view8);
            imageView9 = (ImageView)view.findViewById(R.id.image_view9);
            imageView10 = (ImageView)view.findViewById(R.id.image_view10);

        }

    }


    public GalleryRecyclerAdapter(Activity context,String[][] imageUrls){
        this.context = context;
        this.imageUrls = imageUrls;
    }




    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_frame, parent, false);

        //itemView.setOnLongClickListener(onItemLongClickListener);

        return new MyHolder(itemView);
    }







  

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        Glide.with(context).load(R.drawable.advaitam_4_logo).apply(options).into(holder.imageView1);
        Glide.with(context).load(R.drawable.advaitam_4_logo).apply(options).into(holder.imageView2);
        Glide.with(context).load(R.drawable.advaitam_4_logo).apply(options).into(holder.imageView3);
        Glide.with(context).load(R.drawable.advaitam_4_logo).apply(options).into(holder.imageView4);
        Glide.with(context).load(R.drawable.advaitam_4_logo).apply(options).into(holder.imageView5);
        Glide.with(context).load(R.drawable.advaitam_4_logo).apply(options).into(holder.imageView6);
        Glide.with(context).load(R.drawable.advaitam_4_logo).apply(options).into(holder.imageView7);
        Glide.with(context).load(R.drawable.advaitam_4_logo).apply(options).into(holder.imageView8);
        Glide.with(context).load(R.drawable.advaitam_4_logo).apply(options).into(holder.imageView9);
        Glide.with(context).load(R.drawable.advaitam_4_logo).apply(options).into(holder.imageView10);

        /*
        Glide.with(context).load(imageUrls[position][0]).apply(options).into(holder.imageView1);
        Glide.with(context).load(imageUrls[position][1]).apply(options).into(holder.imageView2);
        Glide.with(context).load(imageUrls[position][2]).apply(options).into(holder.imageView3);
        Glide.with(context).load(imageUrls[position][3]).apply(options).into(holder.imageView4);
        Glide.with(context).load(imageUrls[position][4]).apply(options).into(holder.imageView5);
        Glide.with(context).load(imageUrls[position][5]).apply(options).into(holder.imageView6);
        Glide.with(context).load(imageUrls[position][6]).apply(options).into(holder.imageView7);
        Glide.with(context).load(imageUrls[position][7]).apply(options).into(holder.imageView8);
        Glide.with(context).load(imageUrls[position][8]).apply(options).into(holder.imageView9);
        Glide.with(context).load(imageUrls[position][9]).apply(options).into(holder.imageView10);
        */
       
    }


    @Override
    public int getItemCount() {
        return imageUrls.length;
    }


}
