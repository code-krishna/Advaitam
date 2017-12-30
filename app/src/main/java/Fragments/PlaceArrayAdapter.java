package Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ac.nita.advaitam4.R;

/**
 * Created by HP on 12/29/2017.
 */

class PlaceArrayAdapter extends ArrayAdapter<Place> {

    Context mContext;
    int mLayoutResourceId;
    Place mData[] = null;

    public PlaceArrayAdapter(Context context, int row, Place[] myPlacesArray) {
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
        Place place = mData[position];
        holder.nameView.setText(place.nameOfStudent);
        holder.enroll.setText(place.enroll);
        holder.phoneNo.setText(place.phoneNo);
        return row;
    }
    private static class PlaceHolder {
        TextView nameView;
        TextView enroll;
        TextView phoneNo;
        ImageView rowImage;
    }
}
