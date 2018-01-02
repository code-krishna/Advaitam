package ac.nita.advaitam4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import Fragments.DescriptionFragment;
import Fragments.ListOfParticipantFragment;
import Fragments.OrganisersFragment;
import Fragments.ResultsFragment;

public class  TabbedActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    SharedPreferences sharedPreferences;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private CharSequence[] titles={"Info","Organisers","Results","Participants"};
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        Bundle bundle=getIntent().getExtras();
        String idName=bundle.getString("KEY");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sharedPreferences = getSharedPreferences("USER",0);
        flag = sharedPreferences.getBoolean("FLAG", false);

        // Set up the ViewPager with the sections adapter.
        //mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //mViewPager.setAdapter(mSectionsPagerAdapter);



        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),titles,idName,flag);

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Registration Activity Launch", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
//            return 3;
            if(flag){
                return 3;
            }else {
                return 4;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "Info";
//                case 1:
//                    return "Organisers";
//                case 2:
//                    return "Results";
//            }
            if (flag) {
                switch (position) {
                    case 0:
                        return "Info";
                    case 1:
                        return "Organisers";
                    case 2:
                        return "Results";
                }
            } else {
                switch (position) {
                    case 0:
                        return "Info";
                    case 1:
                        return "Organisers";
                    case 2:
                        return "Results";
                    case 3:
                        return "Participants";
                }
            }
                return null;
            }
        }

    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        CharSequence titles[];
        //Bundle bundle;
        String idName;
        Boolean flag;

        public SimpleFragmentPagerAdapter(FragmentManager fm, CharSequence mTitles[],String idName,boolean flag) {
            super(fm);
            this.titles=mTitles;
            this.flag = flag;
            this.idName=idName;
        }

        @Override
        public Fragment getItem(int position) {	//0- based index position
            if (position == 0)
            {
                DescriptionFragment descriptionFragment= new DescriptionFragment();
                Bundle bundle=new Bundle();
                bundle.putString("KEY",idName);
                descriptionFragment.setArguments(bundle);
                return descriptionFragment;
            }
            else
            if (position == 1)
            {
                return new OrganisersFragment();
            }
            if (position == 2)
            {
                return new ResultsFragment();
            }else{
                return new ListOfParticipantFragment();
            }


        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override public int getCount() {
//        return 3; // no. of fragments –can use a final int for this.
            if(flag)
                return 4; // no. of fragments –can use a final int for this.
            else
                return 3;
        }
    }
}
