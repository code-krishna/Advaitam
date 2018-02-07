package Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Info.EventsClass;
import ac.nita.advaitam4.R;

/**
 * Created by HRITIK on 2/7/2018.
 */

 class EventAdapterForP extends ArrayAdapter<EventsClass> {
    Context mContext;
    int mLayoutResourceId;
    ArrayList<EventsClass> mData = new ArrayList<>();

    public EventAdapterForP(Context context, int row, ArrayList<EventsClass> myPlacesArray) {
        super(context, row, myPlacesArray);
        this.mContext = context;
        this.mLayoutResourceId = R.layout.row;
        this.mData = myPlacesArray;
    }


    @Override
    public EventsClass getItem(int position)
    {
        return super.getItem(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        EventAdapterForP.PlaceHolder holder = null;
        if(row==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mLayoutResourceId,parent,false);
            holder = new EventAdapterForP.PlaceHolder();
            holder.eventName = (TextView) row.findViewById(ac.nita.advaitam4.R.id.rowTextView);
            holder.date = (TextView) row.findViewById(R.id.rowSmallTextView);
            holder.time = (TextView) row.findViewById(R.id.rowSmallTextView1);
            holder.iv = (ImageView) row.findViewById(R.id.rowImageView);
            row.setTag(holder);
        }else {
            holder = (EventAdapterForP.PlaceHolder) row.getTag();
        }

        EventsClass eventsClass = mData.get(position);
        holder.eventName.setText(eventsClass.getName());
        holder.date.setText(eventsClass.getDate());
        holder.time.setText(eventsClass.getTime());

        return row;
    }
    private static class PlaceHolder {
        TextView eventName;
        TextView date;
        TextView time;
        ImageView iv;
    }
}





