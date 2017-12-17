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

    int[] IMAGES;
    String[] BRANCH;
    String[] ENROLL;
    String[] MOBILE;
    String[] EMAIL;
    String[] NAMES;
    Context context;

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
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.customlayout,null);

        ImageView imageView=(ImageView)view.findViewById(R.id.imageView);

        TextView textViewname= (TextView)view.findViewById(R.id.textViewName);
        TextView textViewbranch= (TextView)view.findViewById(R.id.textViewbranch);
        TextView textViewenroll= (TextView)view.findViewById(R.id.textViewenroll);
        TextView textViewmobile= (TextView)view.findViewById(R.id.textViewmobile);
        TextView textViewemail= (TextView)view.findViewById(R.id.textViewemail);

        imageView.setImageResource(IMAGES[i]);
        textViewname.setText(NAMES[i]);
        textViewbranch.setText(BRANCH[i]);
        textViewenroll.setText(ENROLL[i]);
        textViewmobile.setText(MOBILE[i]);
        textViewemail.setText(EMAIL[i]);



        return view;
    }


}