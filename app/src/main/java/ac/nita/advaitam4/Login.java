package ac.nita.advaitam4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Arrays;

import ac.nita.advaitam4.R;

import static android.content.ContentValues.TAG;

public class Login extends AppCompatActivity {


    private View.OnScrollChangeListener onScrollChangeListener;
    private View.OnClickListener onClickListener;
    private Button selectLogin,selectRegister;
    private RelativeLayout loginPart,registerPart;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference ref;
    private CallbackManager callbackManager;
    private Button mFacebookBtn;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Uri imageuri = null;
    private AuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Context context = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        FirebaseApp.initializeApp(context);




        ImageView bgHeader = (ImageView)findViewById(R.id.bgheader);
        Glide.with(Login.this).load(R.drawable.advaitam_4_logo).into(bgHeader);


        preferences = getSharedPreferences("USER",0);
        editor = preferences.edit();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");
        mFacebookBtn = findViewById(R.id.fb_login);
        mAuth = FirebaseAuth.getInstance();

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setTitle("Login");
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorPrimary));

        loginPart = (RelativeLayout) findViewById(R.id.login_part);
        registerPart = (RelativeLayout) findViewById(R.id.register_part);
        selectLogin = (Button) findViewById(R.id.select_login);
        selectRegister = (Button) findViewById(R.id.select_register);

        onClickListener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.select_login:

                        loginPart.setVisibility(View.VISIBLE);
                        registerPart.setVisibility(View.GONE);
                        selectLogin.setTextColor(getResources().getColor(R.color.colorPrimary));
                        selectLogin.setBackground(getResources().getDrawable(R.drawable.selected_button));
                        selectRegister.setTextColor(getResources().getColor(R.color.yellow));
                        selectRegister.setBackground(getResources().getDrawable(R.drawable.unselected_button));

                        break;

                    case R.id.select_register:

                        loginPart.setVisibility(View.GONE);
                        registerPart.setVisibility(View.VISIBLE);
                        selectRegister.setTextColor(getResources().getColor(R.color.colorPrimary));
                        selectRegister.setBackground(getResources().getDrawable(R.drawable.selected_button));
                        selectLogin.setTextColor(getResources().getColor(R.color.yellow));
                        selectLogin.setBackground(getResources().getDrawable(R.drawable.unselected_button));

                        break;

                }
            }
        };

        selectLogin.setOnClickListener(onClickListener);
        selectRegister.setOnClickListener(onClickListener);


        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText emailET = (EditText) findViewById(R.id.username_login);
                final EditText passwordET = (EditText) findViewById(R.id.password_login);
                Toast.makeText(Login.this, "Signing in...",
                        Toast.LENGTH_SHORT).show();
                signIn(mAuth, emailET.getText().toString(), passwordET.getText().toString());

            }
        });


        Button register = (Button) findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText emailET = (EditText) findViewById(R.id.username_register);
                final EditText passwordET = (EditText) findViewById(R.id.password_register);
                final EditText confirmPasswordET = (EditText) findViewById(R.id.password_confirmation_register);

                if (passwordET.getText().toString().equals(confirmPasswordET.getText().toString())) {
                    Toast.makeText(Login.this, "Registering...",
                            Toast.LENGTH_SHORT).show();
                    signUp(mAuth, emailET.getText().toString(), passwordET.getText().toString());
                } else {
                    Toast.makeText(Login.this, "Passwords Don't Match",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



        ImageButton fbLoginButton = (ImageButton)findViewById(R.id.fb_login_button);
        ImageButton googleLoginButton = (ImageButton)findViewById(R.id.google_login_button);
        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFacebookBtn.performClick();
            }
        });

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("mylog", "onclick registeres");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    signIn();
                } else {


                }
            }
        });


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                //.requestIdToken("400127801580-vldk5pudin30e13e7megpb9s9grr2drv.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //signInButton.setVisibility(View.GONE);
                //signOutButton.setVisibility(View.VISIBLE);
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if (user.getDisplayName() != null) {
                        ref.addValueEventListener(valueEventListener);
                    }

                    //emailTextView.setText(user.getEmail().toString());

                } else {
                    // User is signed out

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        ref.addValueEventListener(valueEventListener);
        SignInButton signInButton = (SignInButton) findViewById(R.id.google_signin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("mylog", "onclick registeres");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    signIn();
                } else {


                }
            }
        });

        callbackManager = CallbackManager.Factory.create();
        mFacebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFacebookBtn.setEnabled(false);
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        getFbInfo();
                    }

                    @Override
                    public void onCancel() {
                        Log.d("Facebook :"," onCancelled");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("Facebook Error "," : "+ error);
                    }
                });
            }
        });
    }


    public void signUp(final FirebaseAuth mAuth,String email,String password){
        try{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(Login.this,Categories.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(Login.this, "Error : "+e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void signIn(final FirebaseAuth mAuth,String email,String password){

        try {
            credential = EmailAuthProvider.getCredential(email,password);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("mylog", "signInWithEmail:success");

                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUserProfilData();
                                startActivity(new Intent(Login.this,Categories.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(Login.this, "Error : "+ e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }



    }

    private void updateUserProfilData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("userName")
                .setPhotoUri(null)
                .build();
        Log.d("profile Data" , "imageuri"   + imageuri);
        if (user != null) {
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



    }

    public class AccountData{

        public String email;
        public String password;
        public String confirmPassword;

        public AccountData(){

        }

        public AccountData(String email,String password){
            this.email = email;
            this.password = password;
            this.confirmPassword = password;
        }
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            try {


            } catch (Exception e){
                e=e;
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent,123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (signInResult.isSuccess()) {
                GoogleSignInAccount account = signInResult.getSignInAccount();
                firebaseAuthWithGoogle(account);
                updateUI();
            } else {
                Log.d("mylog", "Google Login failed");
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            updateUI();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ( progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Login.this, "Authentication failed."+ task.getException(), Toast.LENGTH_SHORT).show();
                            updateUI();
                        }
                    }
                });

    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            mFacebookBtn.setEnabled(true);
                            user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed." + task.getException() ,
                                    Toast.LENGTH_SHORT).show();
                            mFacebookBtn.setEnabled(true);
                        }

                        // ...
                    }
                });



    }

    private void updateUI() {
        Log.d("Fb Login ","u are logged in");
        editor.putString("Signed in via fb or google","true");
        startActivity(new Intent(Login.this,Categories.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    private void getFbInfo() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            Log.d("Fb data", "fb json object: " + object);
                            Log.d("Fb data", "fb graph response: " + response);

                            String id = object.getString("id");
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String gender = object.getString("gender");
                            String birthday = object.getString("birthday");
                            String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";

                            String email;
                            if (object.has("email")) {
                                email = object.getString("email");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,gender,birthday"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
        request.setParameters(parameters);
        request.executeAsync();
    }
}
