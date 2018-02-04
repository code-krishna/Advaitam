package Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Info.items_for_list_of_participants;
import ac.nita.advaitam4.R;

class PlaceArrayAdapter extends ArrayAdapter<items_for_list_of_participants> {

    Context mContext;
    int mLayoutResourceId;
    ArrayList<items_for_list_of_participants> mData = new ArrayList<>();

    public PlaceArrayAdapter(Context context, int row, ArrayList<items_for_list_of_participants> myPlacesArray) {
        super(context, row, myPlacesArray);
        this.mContext = context;
        this.mLayoutResourceId = row;
        this.mData = myPlacesArray;
    }
    @Override
    public items_for_list_of_participants getItem(int position)
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
        items_for_list_of_participants place = mData.get(position);
        holder.nameView.setText(place.getName());
        holder.enroll.setText(place.getEnroll());
        holder.phoneNo.setText(place.getContact());
        return row;
    }
    private static class PlaceHolder {
        TextView nameView;
        TextView enroll;
        TextView phoneNo;
        ImageView rowImage;
    }
}