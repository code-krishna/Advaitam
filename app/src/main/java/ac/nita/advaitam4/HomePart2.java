package ac.nita.advaitam4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Adapters.ImageRecyclerViewAdapters;
import Info.EventsData;

/**
 * Created by HRITIK on 12/24/2017.
 */

public class HomePart2 extends Fragment{
    private RecyclerView recyclerView1,recyclerView2,recyclerView3;
    private RecyclerView.LayoutManager llm1,llm2,llm3;
    private List<EventsData> values1,values2,values3 ;
    private Integer [] images = {R.drawable.gate,R.drawable.dj,R.drawable.cultural,R.drawable.robot,R.drawable.sports};

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;




    public HomePart2(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_home_part_2,container,false);
        return rootView;
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
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        getActivity().setTitle("Home");

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        PACKAGE_NAME = getContext().getPackageName();

        viewPager =(ViewPager) view.findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new HomePart2.MyTimerTask(), 2000, 4000);
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

        mRef.child("EVENTS_INFO").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    EventsData values1 = dataSnapshot.child("CULTURAL_INFO").getValue(EventsData.class);
                    EventsData values2 = dataSnapshot.child("TECHNICAL_INFO").getValue(EventsData.class);
                    EventsData values3 = dataSnapshot.child("SPORTS_INFO").getValue(EventsData.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView1 = view.findViewById(R.id.rec1);
        recyclerView2 = view.findViewById(R.id.rec2);
        recyclerView3 = view.findViewById(R.id.rec3);
        recyclerView1.setHasFixedSize(true); recyclerView2.setHasFixedSize(true); recyclerView3.setHasFixedSize(true);
        llm1 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        llm2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        llm3 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        recyclerView1.setLayoutManager(llm1);
        recyclerView2.setLayoutManager(llm2);
        recyclerView3.setLayoutManager(llm3);

        ImageRecyclerViewAdapters adapter1 = new ImageRecyclerViewAdapters(getActivity(),values1);
        ImageRecyclerViewAdapters adapter2 = new ImageRecyclerViewAdapters(getActivity(),values2);
        ImageRecyclerViewAdapters adapter3 = new ImageRecyclerViewAdapters(getActivity(),values3);

        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);











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

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };








}
