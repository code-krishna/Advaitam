package ac.nita.advaitam4;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Map;

import Fragments.DescriptionFragment;
import Fragments.ListOfParticipantFragment;
import Fragments.OrganisersFragment;
import Fragments.Place;
import Fragments.ResultsFragment;
import Info.CommonInfo;
import Info.EventsData;

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

    private EventsData data;
    private ViewPager mViewPager;
    private CharSequence[] titles={"Info","Organisers","Results","Participants"};
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        sharedPreferences = this.getApplicationContext().getSharedPreferences("USER",0);

        Bundle bundle=getIntent().getExtras();
        final String idName = bundle.getString("KEY");
        data = bundle.getParcelable("object");

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
//        Intent intent = new Intent();
//        EventsData data = (EventsData)this.getIntent().getSerializableExtra("object");
        // b = intent.getBundleExtra("bundle");
        //if(data != null)
            //Toast.makeText(getApplicationContext(),data.toString(),Toast.LENGTH_SHORT).show();
          //  Log.d("myTag1",data.getName());



        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),titles,idName,flag);

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Button registerButton = (Button) findViewById(R.id.register_button);

        final Place[] place = new Place[1];
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                DatabaseReference eventReference = firebaseDatabase.getReference("data/events/"+"event123"+"/participants/"+user.getUid());
                eventReference.setValue(new Place(sharedPreferences.getString("NAME", "NAME"), sharedPreferences.getString("ENROLL", "ENROLL"), sharedPreferences.getString("CONTACT", "CONTACT")), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        progressBar.setVisibility(View.GONE);
                        if(databaseError==null){
                            showDialogDox("You Have Been Successfully Registered.");
                        } else {
                            showDialogDox("Sorry There Was A Problem With Your Registration.");
                        }
                    }
                });
            }
        });

    }


    public void showDialogDox(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(message)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
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
                Bundle bundle = new Bundle();
                bundle.putString("DESC",data.getDescription());
                bundle.putString("NAME",data.getName());
                bundle.putString("DATE",data.getDate());
                bundle.putString("TIME",data.getTime());

                descriptionFragment.setArguments(bundle);
                return descriptionFragment;
            }
            else
            if (position == 1)
            {
                OrganisersFragment organisersFragment = new OrganisersFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("KEY2",idName);
                organisersFragment.setArguments(bundle1);
                return new OrganisersFragment();
            }
            if (position == 2)
            {
                ResultsFragment result = new ResultsFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putString("KEY3",idName);
                result.setArguments(bundle2);
                return new ResultsFragment();
            }else{
                ListOfParticipantFragment listOfParticipantFragment = new ListOfParticipantFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("K", (Serializable) data.getListOfParticipants());
                Log.d("TAG",bundle3.getSerializable("K").toString());
                listOfParticipantFragment.setArguments(bundle3);
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
