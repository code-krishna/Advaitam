package ac.nita.advaitam4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class UserMode extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Context context = this;

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setTitle("User");


        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context,R.color.colorPrimary));

        //CardView choiceOrganisr = (CardView)findViewById(R.id.organiser_cv);
        //CardView choiceParticipant = (CardView)findViewById(R.id.participant_cv);



        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId() == R.id.participant){
                    editor.putString("user_mode","PARTICIPANT").commit();
                } else if(v.getId() == R.id.organiser){
                    editor.putString("user_mode","ORGANISER").commit();
                }

                Intent intent = new Intent(UserMode.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        Button participantButton = (Button)findViewById(R.id.participant);
        Button organiserButton = (Button)findViewById(R.id.organiser);

        participantButton.setOnClickListener(onClickListener);
        organiserButton.setOnClickListener(onClickListener);

        //choiceOrganisr.setOnClickListener(onClickListener);
        //choiceParticipant.setOnClickListener(onClickListener);

    }

}
