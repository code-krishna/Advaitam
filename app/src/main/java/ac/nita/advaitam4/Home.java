package ac.nita.advaitam4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import ac.nita.advaitam4.R;

public class Home extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_home, container, false);
    }




    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    public static String PACKAGE_NAME;

    public void myClick(View view)
    {
        Intent in = new Intent(getContext(), TabbedActivity.class);
        in.putExtra("KEY",getResources().getResourceEntryName(view.getId()));
        startActivity(in);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");

        PACKAGE_NAME=getContext().getPackageName();

        viewPager =(ViewPager) view.findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i=0;i<dotscount;i++)
        {
            dots[i]=new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.nonactive));
            LinearLayout.LayoutParams params=new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDotspanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<dotscount;i++)
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.nonactive));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active));

            }



            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }




    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            try {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(1);
                        } else if (viewPager.getCurrentItem() == 1) {
                            viewPager.setCurrentItem(2);
                        } else {
                            viewPager.setCurrentItem(0);
                        }


                    }
                });

            } catch (Exception e){
                Log.d("mylog","Error :: "+e.getMessage());
            }

        }
    }



}
