package ac.nita.advaitam4;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class Categories extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean profile = true;
    String name1,enroll1,cont1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String profileImageUrl = user.getPhotoUrl().toString();


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        sharedPreferences = getSharedPreferences("USER",0);
        editor = sharedPreferences.edit();
        mRef = FirebaseDatabase.getInstance().getReference();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        TextView navUsername = (TextView)navigationView.getHeaderView(0).findViewById(R.id.nav_username);
        TextView navEmail = (TextView)navigationView.getHeaderView(0).findViewById(R.id.nav_email);

        navUsername.setText(user.getDisplayName());
        navEmail.setText(user.getEmail());

        ImageView navImage = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.nav_image_view);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .error(R.drawable.ic_account_circle_black_24dp);



        Glide.with(this).load(profileImageUrl).apply(options).into(navImage);



        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.d("myDebug","value " + sharedPreferences.getString("NAME", " ")+
                sharedPreferences.getString("CONTACT", " ")+
                sharedPreferences.getString("ENROLL", " "));

        mRef.child("USER").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                   profile = dataSnapshot.exists();
                }else{
                    profile = dataSnapshot.exists();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        if(!profile){
            Toast.makeText(getApplicationContext(), "Oops you didn't Completed Your Profile Yet !!", Toast.LENGTH_SHORT).show();

            setFragment(new Home());
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_holder,new Profile()).addToBackStack("hii");
            ft.commit();
        }else{
            setFragment(new Home());
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.categories, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            onSearchRequested();
            return true;
        }if(id == R.id.action_logout) {
            if(user != null)
                mAuth.signOut();
            editor.putString("user_mode","NULL").apply();
            editor.putBoolean("Flag",false).apply();
            editor.putString("NAME"," ").apply();
            editor.putString("CONTACT"," ").apply();
            editor.putString("ENROLL"," ").apply();
            startActivity(new Intent(Categories.this,SplashScreen.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_history) {
            fragment = new History();
        } else if (id == R.id.nav_account) {

            fragment = new Profile();

        } else if (id == R.id.nav_home) {
            fragment = new Home();
        } else if (id == R.id.nav_events) {

        } else if (id == R.id.nav_qrscanner) {
            fragment = new QrScanner();
        } else if (id == R.id.nav_developers){
            fragment = new Developers();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_holder, fragment).addToBackStack("yes");
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setFragment(Fragment fragment){
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_holder, fragment);
            ft.commit();
        }
    }


    public void myClick(View view)
    {
        Intent in = new Intent(this, TabbedActivity.class);
        in.putExtra("KEY",getResources().getResourceEntryName(view.getId()));
        startActivity(in);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
