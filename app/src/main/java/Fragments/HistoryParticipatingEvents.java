package Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import Info.EventsClass;
import ac.nita.advaitam4.R;

public class HistoryParticipatingEvents extends Fragment {

    Context context;

    Map<String,EventsClass> list = new HashMap<>();
    ArrayList<EventsClass> myPlacesArray;
    ListView mListView;
    ProgressBar progressBar;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public void ListOfParticipantFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View rootView = inflater.inflate(R.layout.history_participating_events,container,false);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress_bar);
        mListView = (ListView) rootView.findViewById(R.id.passed_events_listview);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callFirebase(view);

    }


    void callFirebase(final View view){
        FirebaseApp.initializeApp(getContext());
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("USER/"+user.getUid()+"/PARTICIPATING_EVENTS");
        Toast.makeText(getContext(),"database",Toast.LENGTH_SHORT).show();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myPlacesArray = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    EventsClass eventsClass = dataSnapshot1.getValue(EventsClass.class);
                    Log.d("mylog", dataSnapshot1.getValue().toString());
//                    Log.d("mylogggg", eventsClass.getDate().toString());
                    myPlacesArray.add(eventsClass);
//                    Log.d("mylog logg", myPlacesArray.get(0).getDate().toString());

                }

            populateListView(view,myPlacesArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    void populateListView(View view,ArrayList<EventsClass> arrayList){

        EventAdapterForP eventAdapter = new EventAdapterForP(getContext(),1,arrayList);
        if(eventAdapter!=null)
            mListView.setAdapter(eventAdapter);
    }
//    ArrayList<EventsClass> sortEvents(ArrayList<EventsClass> arrayList){
//       Collections.sort(arrayList,);
//        return null;
//    }
    ArrayList<EventsClass> getAllEvents(DataSnapshot dataSnapshot){

        ArrayList<EventsClass> events = new ArrayList<>();

        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                EventsClass eventsClass = dataSnapshot2.getValue(EventsClass.class);
                if(eventsClass!=null)
                    events.add(eventsClass);
            }
        }

        return events;
    }



}
