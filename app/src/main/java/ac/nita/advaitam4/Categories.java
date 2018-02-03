package ac.nita.advaitam4;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.FirebaseApp;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Categories extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean profile ;

    String name1, enroll1, cont1,uid;
    private CallbackManager callbackManager;
    String profileImageUrl;
    private ProgressDialog progressDialog;
    ArrayAdapter<String> arrayAdapter;
    ListView searchLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        progressDialog = new ProgressDialog(getApplicationContext());
        FirebaseApp.initializeApp(this);


        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();

        ArrayList<String> searchArrayList = new ArrayList<>();
        searchArrayList.add("Robo War");
        searchArrayList.add("Dumb Charades");
        searchArrayList.add("Line Runner");
        searchArrayList.add("Tech Race");
        searchArrayList.add("Counter Strike");
        searchArrayList.add("Beg Borrow Steal");
        searchArrayList.add("Blind Date");
        searchArrayList.add("Random Event");

        searchLV = (ListView)findViewById(R.id.search_list_view);
        arrayAdapter = new Adapters.SearchListAdapter(Categories.this,searchArrayList);
        searchLV.setAdapter(arrayAdapter);

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                progressDialog.setMessage("Wait ! While data is Uploading....");
                progressDialog.setCancelable(false);
                progressDialog.show();
                TextView navUsername = navigationView.getHeaderView(0).findViewById(R.id.nav_username);
                TextView navEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_email);
                user = firebaseAuth.getCurrentUser();
                if(user==null)
                    return;
                uid = user.getUid();
                navUsername.setText(user.getDisplayName());
                navEmail.setText(user.getEmail());
                if (user.getPhotoUrl() != null)
                    profileImageUrl = user.getPhotoUrl().toString();
                ImageView navImage = navigationView.getHeaderView(0).findViewById(R.id.nav_image_view);
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.ic_account_circle_black_24dp)
                        .error(R.drawable.ic_account_circle_black_24dp);

                if(profileImageUrl!=null)
                Glide.with(Categories.this).load(profileImageUrl).apply(options).into(navImage);

                mRef.child("USER").child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            progressDialog.setCancelable(true);
                            progressDialog.dismiss();
                            Log.d("valueName:", "DATA : " + dataSnapshot);
                            editor.putString("NAME", (String) dataSnapshot.child("name").getValue()).apply();
                            editor.putString("CONTACT", (String) dataSnapshot.child("contact").getValue()).apply();
                            editor.putString("ENROLL", (String) dataSnapshot.child("enroll").getValue()).apply();
                            editor.putString("COLLEGE", (String) dataSnapshot.child("college").getValue()).apply();
                            editor.putString("ProfileImage_path", (String) dataSnapshot.child("download_uri").getValue()).apply();
                            updateProfile(sharedPreferences.getString("NAME", " "), sharedPreferences.getString("ProfileImage_path", " "));
                            Log.d("dataSnapshot", dataSnapshot.toString());
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        profile = true;
                        startActivity(new Intent(Categories.this, Categories.class));
                        Log.d("Profile Check ", "   checking  cancellled " + databaseError.toString());
                    }
                });
            }
        });


        progressDialog = new ProgressDialog(Categories.this);
        navigationView.setNavigationItemSelectedListener(this);
        sharedPreferences = this.getSharedPreferences("USER", 0);
        editor = sharedPreferences.edit();

        //String profileImageUrl = user.getPhotoUrl().toString();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        callbackManager = CallbackManager.Factory.create();

        progressDialog.setMessage("Profile is Updating");
        progressDialog.show();


        name1 = sharedPreferences.getString("NAME", " ");
        enroll1 = sharedPreferences.getString("ENROLL", " ");
        cont1 = sharedPreferences.getString("CONTACT", " ");


        if ((name1.equals(" ") && enroll1.equals(" ") && cont1.equals(" "))) {
            profile = false;

        } else {
            profile = true;
        }
        progressDialog.dismiss();
        Log.d("checking", name1 + "   " + enroll1 + "   " + cont1 + "  " + profile);

        if (!profile) {
            Log.d("Profile Check ", " Entered   checking  cancellled ");
            Toast.makeText(getApplicationContext(), "Oops you didn't Completed Your Profile Yet !!", Toast.LENGTH_SHORT).show();
            setFragment(new HomePart2());
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_holder, new EditProfile()).addToBackStack("hii");

            ft.commit();

        } else {
            setFragment(new HomePart2());
        }
    }


    public class LoadUserData extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private void updateProfile(String name, String profileImage_path) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(Uri.parse(profileImage_path))
                .build();
        assert user != null;
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("user profile : ", "User profile updated.");
                        }
                    }
                });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            setFragment(new HomePart2());
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.categories, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchLV.setVisibility(View.VISIBLE);
                arrayAdapter.getFilter().filter(s);

                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchLV.setVisibility(View.GONE);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
            LoginManager.getInstance().logOut();
            editor.putString("user_mode","NULL").apply();
            editor.putBoolean("Flag",false).apply();
            editor.putString("NAME"," ").apply();
            editor.putString("CONTACT"," ").apply();
            editor.putString("ENROLL"," ").apply();
            editor.putString("ProfileImage_path"," ").apply();
            editor.putString("COLLEGE"," ").apply();
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

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if (id == R.id.nav_history) {
            fragment = new History();
        } else if (id == R.id.nav_account) {
            fragment = new Profile();
        } else if (id == R.id.nav_home) {
            fragment = new HomePart2();
        } else if (id == R.id.nav_events) {
            fragment = new Events();
        }   else if (id == R.id.nav_gallery) {
            fragment = new Gallery();
        }  else if (id == R.id.nav_quest) {
            fragment = new Quest();
        }  else if (id == R.id.nav_qrscanner) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

}
