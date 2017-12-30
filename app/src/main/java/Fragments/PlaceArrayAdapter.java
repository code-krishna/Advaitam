package Fragments;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ac.nita.advaitam4.R;

/**
 * Created by HP on 12/29/2017.
 */

class PlaceArrayAdapter extends BaseAdapter {

    Context mContext;
    int mLayoutResourceId;
    //List<Place> mData = new ArrayList<Place>();
    Place[] mData = null;

    public PlaceArrayAdapter(Context context, int row, Place[] myPlacesArray) {
        //super(context, row, myPlacesArray);

        Log.d("mylog", "myplacesarray size is "+myPlacesArray.length);

        this.mContext = context;
        this.mLayoutResourceId = row;
        this.mData = myPlacesArray;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        View row = convertView;
        PlaceHolder holder = null;

        if(row==null){
        //LayoutInflater inflater = LayoutInflater.from(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(mLayoutResourceId,parent,false);
        holder = new PlaceHolder();
        holder.nameView = (TextView) row.findViewById(ac.nita.advaitam4.R.id.rowTextView);
        holder.enroll = (TextView) row.findViewById(R.id.rowSmallTextView);
        holder.phoneNo = (TextView) row.findViewById(R.id.rowSmallTextView1);
        holder.rowImage = (ImageView) row.findViewById(R.id.rowImageView);
         row.setTag(holder);}
         else {
            holder = (PlaceHolder) row.getTag();
        }
        Place place = mData[position];
        Log.d("mylog", place.getNameStudent()+place.getPhone()+place.getRoll()+" ");
        holder.nameView.setText(place.getNameStudent());
        holder.enroll.setText(place.getRoll());
        holder.phoneNo.setText(place.getPhone());
        return row;
    }
    private static class PlaceHolder {
        TextView nameView;
        TextView enroll;
        TextView phoneNo;
        ImageView rowImage;
    }
}
