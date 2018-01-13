package Adapters;

/**
 * Created by sourav9674 on 1/10/2018.
 */

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ac.nita.advaitam4.R;

/**
 * Created by sourav9674 on 12/13/2017.
 */
public class SearchListAdapter extends ArrayAdapter<String> {


    public ArrayList<String> NAMES;
    public Activity context;


    public SearchListAdapter(Activity context,ArrayList<String> NAMES){
        super(context,-1,NAMES);
        this.context = context;
        this.NAMES = NAMES;

    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.search_list_view,null);
        TextView textView = (TextView)view.findViewById(R.id.result_tv);
        textView.setText(NAMES.get(pos));
        return view;
    }

}