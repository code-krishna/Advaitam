package Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ac.nita.advaitam4.R;

/**
 * Created by HP on 12/29/2017.
 */

class PlaceArrayAdapter extends ArrayAdapter<Place> {

    Context mContext;
    int mLayoutResourceId;
    ArrayList<Place> mData = new ArrayList<>();

    public PlaceArrayAdapter(Context context, int row, ArrayList<Place> myPlacesArray) {
        super(context, row, myPlacesArray);
        this.mContext = context;
        this.mLayoutResourceId = row;
        this.mData = myPlacesArray;
    }
    @Override
    public Place getItem(int position)
    {
        return super.getItem(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        PlaceHolder holder = null;
        if(row==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
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
        Place place = mData.get(position);
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