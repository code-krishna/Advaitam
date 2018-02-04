package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import Info.items_for_list_of_participants;
import ac.nita.advaitam4.R;
import android.app.Activity;
import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


/**
 * Created by HRITIK on 12/18/2017.
 */

public class ListOfParticipantFragment extends Fragment {

    private String event_pos,event_name;
    Context context;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    ProgressBar progressBar;

    public void ListOfParticipantFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        final View rootView = inflater.inflate(R.layout.fragment_list_of_participant,container,false);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress_bar);
        String add1 = getArguments().getString("K");
        final Map<String ,items_for_list_of_participants> ItemsData = new HashMap<>();
        final ArrayList<items_for_list_of_participants> myPlacesArray = new ArrayList<>();
        DatabaseReference participantsRef = firebaseDatabase.getReference(add1);


        participantsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myPlacesArray.clear();
                if(dataSnapshot.exists()){
                    int i=0;
                    for(DataSnapshot  op : dataSnapshot.getChildren()){

                        items_for_list_of_participants items = op.getValue(items_for_list_of_participants.class);
                        ItemsData.put("Name"+i,items);
                        myPlacesArray.add(items);
                        i++;
                    }
                }

                Log.d("Tag",myPlacesArray.toString());
                ListView mListView =  rootView.findViewById(R.id.events_listview);
                PlaceArrayAdapter mArrayAdapter = new PlaceArrayAdapter(getActivity(), R.layout.row, myPlacesArray);
                mListView.setAdapter(mArrayAdapter);
////

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseApp.initializeApp(getContext());
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    }
}
