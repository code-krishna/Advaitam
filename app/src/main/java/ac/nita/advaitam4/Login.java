package ac.nita.advaitam4;

import android.content.Context;
import android.content.Intent;
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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import ac.nita.advaitam4.R;

import static android.content.ContentValues.TAG;

public class Login extends AppCompatActivity {


    View.OnScrollChangeListener onScrollChangeListener;
    View.OnClickListener onClickListener;

    Button selectLogin,selectRegister;
    RelativeLayout loginPart,registerPart;

    GoogleApiClient googleApiClient;
    GoogleSignInOptions gso;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference ref;

    CallbackManager callbackManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Context context = this;

        FirebaseApp.initializeApp(context);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setTitle("Login");


        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context,R.color.colorPrimary));

        loginPart = (RelativeLayout)findViewById(R.id.login_part);
        registerPart = (RelativeLayout)findViewById(R.id.register_part);
        selectLogin = (Button)findViewById(R.id.select_login);
        selectRegister = (Button)findViewById(R.id.select_register);


        onClickListener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                switch (v.getId()){

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

        //ImageView imageView = (ImageView)findViewById(R.id.background_image);
        //imageView.getLayoutParams().height -= 20;


/*
        final ScrollView scrollView = (ScrollView)findViewById(R.id.main_sv);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0,500);
            }
        });
*/










        Button login = (Button)findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText emailET = (EditText)findViewById(R.id.username_login);
                final EditText passwordET = (EditText)findViewById(R.id.password_login);

                Toast.makeText(Login.this, "Signing in...",
                        Toast.LENGTH_SHORT).show();

                signIn(mAuth,emailET.getText().toString(),passwordET.getText().toString());

            }
        });


        Button register = (Button)findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText emailET = (EditText)findViewById(R.id.username_register);
                final EditText passwordET = (EditText)findViewById(R.id.password_register);
                final EditText confirmPasswordET = (EditText)findViewById(R.id.password_confirmation_register);

                if(passwordET.getText().toString().equals(confirmPasswordET.getText().toString())) {
                    Toast.makeText(Login.this, "Registering...",
                            Toast.LENGTH_SHORT).show();
                    signUp(mAuth, emailET.getText().toString(), passwordET.getText().toString());
                } else {
                    Toast.makeText(Login.this, "Passwords Don't Match",
                            Toast.LENGTH_SHORT).show();
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
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //signInButton.setVisibility(View.GONE);
                //signOutButton.setVisibility(View.VISIBLE);
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if(user.getDisplayName() != null) {
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


        //String[] senders = {"sender","sender","sender","sender","sender","sender","sender","sender","sender","sender"};
        //String[] messages = {"message","message","message","message","message","message","message","message","message","message"};


        SignInButton signInButton = (SignInButton)findViewById(R.id.google_signin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("mylog","onclick registeres");

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null) {
                    signIn();
                } else {


                }
            }
        });







         callbackManager = CallbackManager.Factory.create();



        LoginButton loginButton = (LoginButton) findViewById(R.id.fb_login);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        //loginButton.setFragment(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;

                if(!loggedIn){

                    LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile"));
                }

            }
        });

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                handleFacebookAccessToken(loginResult.getAccessToken());
                startActivity(new Intent(Login.this,Categories.class));
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
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
                            startActivity(new Intent(Login.this,Categories.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
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
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("mylog", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(Login.this,Categories.class));

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Authentication failed.",
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

        if(requestCode == 123){
            GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(signInResult.isSuccess()){
                GoogleSignInAccount account = signInResult.getSignInAccount();
                firebaseAuthWithGoogle(account);
                startActivity(new Intent(Login.this,Categories.class));
            } else {
                Log.d("mylog","Google Login failed");
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
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
                            Toast.makeText(Login.this,"Authentication failed.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,Categories.class));
                        }
                    }
                });
    }






    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }




}
