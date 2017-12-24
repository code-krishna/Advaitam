package ac.nita.advaitam4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

import Info.user_info;


public class Profile extends Fragment {

    private ImageView img1;
    private TextView textName, textEnroll, textContact;
    private Button btn;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseStorage storage;
    private StorageReference sRef;
    user_info user_data_to_show;
    String uid;
    Map<String, String> map;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    public Profile(){}

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        preferences = this.getActivity().getSharedPreferences("USER",0);
        editor = preferences.edit();
        img1 = rootView.findViewById(R.id.showImageProf);
        textName = rootView.findViewById(R.id.showNameProf);
        textEnroll = rootView.findViewById(R.id.showEnrollProf);
        textContact = rootView.findViewById(R.id.showContProf);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();



        mRef.child("USER").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Log.d("valueName:", "DATA : "+ dataSnapshot);
                    textName.setText((String)dataSnapshot.child("name").getValue());
                    textEnroll.setText((String)dataSnapshot.child("enroll").getValue());
                    textContact.setText((String)dataSnapshot.child("contact").getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d("valueName:", "DATA : "+preferences.getString("NAME","My Name")+preferences.getString("ENROLL","EnrollMent")+preferences.getString("CONTACT","Contact"));
        storage = FirebaseStorage.getInstance();
        sRef = storage.getReference();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");

        Button btn = (Button)view.findViewById(R.id.buttonprofile);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_holder,new EditProfile()).addToBackStack("this").commit();
            }
        });


        sRef.child("USERS/"+uid+"/pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("myLog ", "Data :  " + uri);
                Glide.with(img1.getContext())
                        .load(uri)
//                        .priority(Priority.IMMEDIATE)
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .skipMemoryCache(true)
//                        .centerCrop()
                        .into(img1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("myLog ", "Data :  " + uid +"/pic");
            }
        });


    }
}
