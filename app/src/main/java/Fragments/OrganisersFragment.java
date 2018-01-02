package Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by HP on 06-08-2017.
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Info.CommonInfo;
import ac.nita.advaitam4.R;
public class OrganisersFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	/* *
	* 1. Inflate the xml
	* 2. Setup the UI
	* 3. Return the rootView
	* */
        return inflater.inflate(R.layout.fragment_organisers, container, false);



    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String idName="event123";

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("data/events/"+idName+"/organisers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {

                    String devList="The Organisers Are .:\n\n";

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        devList+=snapshot.getValue()+"\n\n";
                    }

                    TextView devListTV = (TextView)view.findViewById(R.id.dev_list);
                    devListTV.setText(devList);


                } catch (Exception e){
                    e=e;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
