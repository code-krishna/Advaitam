package ac.nita.advaitam4;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by sourav9674 on 12/13/2017.
 */
class CustomAdapter extends ArrayAdapter {

    public int[] IMAGES;
    public String[] BRANCH;
    public String[] ENROLL;
    public String[] MOBILE;
    public String[] EMAIL;
    public String[] NAMES;
    public Activity context;
    final int random = (int )(Math.random() * 7);


    public CustomAdapter(Activity context,int[] IMAGES,String[] BRANCH,String[] ENROLL,String[] MOBILE,String[] EMAIL,String[] NAMES){
        super(context,-1,NAMES);
        this.context = context;
        this.IMAGES = IMAGES;
        this.BRANCH = BRANCH;
        this.ENROLL = ENROLL;
        this.MOBILE = MOBILE;
        this.EMAIL = EMAIL;
        this.NAMES = NAMES;

    }


    @Override
    public int getCount() {
        return IMAGES.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        Log.d("mylog", "Random is "+random);
        int i=(pos+random)%6;

        PlaceHolder holder = null;
        if(view == null)
        {LayoutInflater inflater =context.getLayoutInflater();
        view = inflater.inflate(R.layout.customlayout,viewGroup,false);
        holder = new PlaceHolder(view);
        view.setTag(holder);}
        else
        {
            holder = (PlaceHolder) view.getTag();
        }

        Glide.with(context).load(IMAGES[i]).into(holder.imageView);
        //holder.imageView.setImageResource(IMAGES[i]);
        holder.textViewname.setText(NAMES[i]);
        holder.textViewbranch.setText(BRANCH[i]);
        holder.textViewenroll.setText(ENROLL[i]);
        holder.textViewmobile.setText(MOBILE[i]);
        holder.textViewemail.setText(EMAIL[i]);



        return view;
    }

private static class PlaceHolder {
        TextView textViewname;
        TextView textViewbranch;
        TextView textViewenroll;
        TextView textViewmobile;
        TextView textViewemail;
        ImageView imageView;

        public PlaceHolder(View view){
            imageView=(ImageView)view.findViewById(R.id.imageView);
            textViewname= (TextView)view.findViewById(R.id.textViewName);
            textViewbranch= (TextView)view.findViewById(R.id.textViewbranch);
            textViewenroll= (TextView)view.findViewById(R.id.textViewenroll);
            textViewmobile= (TextView)view.findViewById(R.id.textViewmobile);
            textViewemail= (TextView)view.findViewById(R.id.textViewemail);
        }

}
}