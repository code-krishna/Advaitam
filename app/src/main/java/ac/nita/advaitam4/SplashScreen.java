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

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseApp.initializeApp(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();


        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);


        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{


                    String userMode = sharedPreferences.getString("user_mode","NULL");
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
                         intent = new Intent(SplashScreen.this, Login.class);
                    } else if(userMode.equals("ORGANISER")){
                         intent = new Intent(SplashScreen.this, Categories.class);
                    }


                    if(userMode.equals("NULL")){
                        intent = new Intent(SplashScreen.this, UserMode.class);
                    } else if(userMode.equals("PARTICIPANT")){
                        if(loggedIn){
                            intent = new Intent(SplashScreen.this, Categories.class);
                        } else {
                            intent = new Intent(SplashScreen.this, Login.class);
                        }
                    } else if(userMode.equals("ORGANISER")){

                    }

                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }

}
