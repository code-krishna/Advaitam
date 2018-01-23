package ac.nita.advaitam4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Fragments.EventsClass;
import Fragments.Place;
import ac.nita.advaitam4.R;

/**
 * Created by HP on 12/29/2017.
 */

class EventsAdapter extends ArrayAdapter<EventsClass> {

    Context mContext;
    int mLayoutResourceId;
    ArrayList<EventsClass> mData = new ArrayList<>();

    public EventsAdapter(Context context, int row, ArrayList<EventsClass> myPlacesArray) {
        super(context, row, myPlacesArray);
        this.mContext = context;
        this.mLayoutResourceId = row;
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
        PlaceHolder holder = null;
        if(row==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mLayoutResourceId,parent,false);
            holder = new PlaceHolder();
            holder.eventName = (TextView) row.findViewById(ac.nita.advaitam4.R.id.rowTextView);
            holder.date = (TextView) row.findViewById(R.id.rowSmallTextView);
            holder.time = (TextView) row.findViewById(R.id.rowSmallTextView1);
            holder.iv = (ImageView) row.findViewById(R.id.rowImageView);
            row.setTag(holder);}
        else {
            holder = (PlaceHolder) row.getTag();
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