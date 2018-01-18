package ac.nita.advaitam4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Sourav on 07/07/2017.
 */

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public boolean flag1 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        ImageView splashImageView = (ImageView)findViewById(R.id.splash_image_view);
        Glide.with(SplashScreen.this).load(R.drawable.splash_image).into(splashImageView);


        FirebaseApp.initializeApp(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();


        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ImageView imageView = (ImageView) findViewById(R.id.splash_image_view);

        
        Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        
        imageView.setAnimation(zoomout);


        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{


                    String userMode = sharedPreferences.getString("user_mode","NULL");
                    flag1 = sharedPreferences.getBoolean("FLAG",false);
                    Log.d("mylog","usermode is "+userMode);
                    //boolean loggedIn = sharedPreferences.getBoolean("logged_in",false);
                    boolean loggedIn=false;

                    if(user==null){
                        loggedIn=false;
                    } else {
                        loggedIn = true;
                    }


                    Intent intent = new Intent(SplashScreen.this, UserMode.class);

                    if(userMode.equals("NULL") && !loggedIn){
                         intent = new Intent(SplashScreen.this, UserMode.class);
                    } else if(userMode.equals("PARTICIPANT") && !loggedIn) {
                        editor.putBoolean("FLAG",false).apply();
                         intent = new Intent(SplashScreen.this, Login.class);
                    } else if(userMode.equals("ORGANISER")){
                        editor.putBoolean("FLAG",true).apply();
                         intent = new Intent(SplashScreen.this, Categories.class);
                    }


                    if(userMode.equals("NULL")){
                        intent = new Intent(SplashScreen.this, UserMode.class);
                    } else if(userMode.equals("PARTICIPANT")){
                        editor.putBoolean("FLAG",false).apply();
                        if(loggedIn){
                            intent = new Intent(SplashScreen.this, Categories.class);
                        } else {
                            intent = new Intent(SplashScreen.this, Login.class);
                        }
                    } else if(userMode.equals("ORGANISER")){
                        editor.putBoolean("FLAG",true).apply();
                        if(loggedIn){
                            intent = new Intent(SplashScreen.this, Categories.class);
                        } else {
                            intent = new Intent(SplashScreen.this, Login.class);
                        }
                    }

                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }

}
