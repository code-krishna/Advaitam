package ac.nita.advaitam4;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ac.nita.advaitam4.CustomScrollView.OnScrollViewListener;

public class ScrollingActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private TextView actionBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.advaitam_image_1);
        final BitmapDrawable bd = new BitmapDrawable(bitmap);
        final ColorDrawable cd = new ColorDrawable(Color.rgb(68, 74, 83));
        mActionBar = getSupportActionBar();
        if(mActionBar == null){
            Log.d("mylog","Error : Action bar is null");
        }
        mActionBar.setBackgroundDrawable(bd);

        cd.setAlpha(0);

        mActionBar.setDisplayHomeAsUpEnabled(true); //to activate back pressed on home button press
        mActionBar.setDisplayShowHomeEnabled(false); //
        mActionBar.setTitle(Html.fromHtml("<b><font color='#ffffff'>Title </font></b>"));
        mActionBar.setSubtitle(Html.fromHtml("<font color='#ffffff'>The Title </font>"));


        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        addTextViews(ll);


        CustomScrollView scrollView = (CustomScrollView)findViewById(R.id.scroll_view);

        if(scrollView == null){
            Log.d("mylog","Error : Scrollview is null");
        }

        scrollView.setOnScrollViewListener(new CustomScrollView.OnScrollViewListener() {

            @Override
            public void onScrollChanged(CustomScrollView v, int l, int t, int oldl, int oldt) {

                cd.setAlpha(getAlphaforActionBar(v.getScrollY()));
            }

            private int getAlphaforActionBar(int scrollY) {
                int minDist = 0, maxDist = 650;
                if (scrollY > maxDist) {
                    return 255;
                } else if (scrollY < minDist) {
                    return 0;
                } else {
                    int alpha = 0;
                    alpha = (int) ((255.0 / maxDist) * scrollY);
                    return alpha;
                }
            }
        });

    }

    private void addTextViews(LinearLayout ll) {

        for (int i = 0; i < 26; i++) {
            TextView tv1 = new TextView(this);
            tv1.setText(String.valueOf(i));
            tv1.setTextSize(10);
            tv1.setWidth(500);
            tv1.setHeight(500);
            tv1.setBackgroundColor(Color.rgb(255-10*i, 255-10*i, 255-10*i)); //just for fun , varying back grounds
            tv1.setGravity(Gravity.CENTER);
            ll.addView(tv1);

        }
    }


}
