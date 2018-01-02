package ac.nita.advaitam4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sourav9674 on 12/13/2017.
 */
class CustomAdapter extends BaseAdapter {

    public int[] IMAGES;
    public String[] BRANCH;
    public String[] ENROLL;
    public String[] MOBILE;
    public String[] EMAIL;
    public String[] NAMES;
    public Context context;

    public CustomAdapter(Context context,int[] IMAGES,String[] BRANCH,String[] ENROLL,String[] MOBILE,String[] EMAIL,String[] NAMES){

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        PlaceHolder holder = null;
        if(view == null)
        {LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.customlayout,null);
        holder = new PlaceHolder();
        holder.imageView=(ImageView)view.findViewById(R.id.imageView);

        holder.textViewname= (TextView)view.findViewById(R.id.textViewName);
        holder.textViewbranch= (TextView)view.findViewById(R.id.textViewbranch);
        holder.textViewenroll= (TextView)view.findViewById(R.id.textViewenroll);
        holder.textViewmobile= (TextView)view.findViewById(R.id.textViewmobile);
        holder.textViewemail= (TextView)view.findViewById(R.id.textViewemail);
        view.setTag(holder);}
        else
        {
            holder = (PlaceHolder) view.getTag();
        }
        holder.imageView.setImageResource(IMAGES[i]);
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
}
}