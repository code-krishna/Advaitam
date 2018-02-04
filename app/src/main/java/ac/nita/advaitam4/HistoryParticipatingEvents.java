package ac.nita.advaitam4;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import Fragments.EventsClassNew;
import Fragments.Place;
import ac.nita.advaitam4.R;

import android.util.Log;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;


/**
 * Created by HRITIK on 12/18/2017.
 */

public class HistoryParticipatingEvents extends Fragment {

    Context context;

    ArrayList<EventsClassNew> myPlacesArray = new ArrayList<>();

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

        View rootView = inflater.inflate(R.layout.history_participating_events,container,false);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress_bar);

        return rootView;
    }

    ListView mListView;

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.events_listview);
        callFirebase(view);

    }

    void callFirebase(final View view) {
        FirebaseApp.initializeApp(getContext());
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("EVENTS_INFO");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<EventsClassNew> allEventClasses = getAllEvents(dataSnapshot);
                callFirebaseForParticipatedEvents(view,dataSnapshot,allEventClasses);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    void callFirebaseForParticipatedEvents(final View view,final DataSnapshot allEventsData,final ArrayList<EventsClassNew> allEventClasses) {
        FirebaseApp.initializeApp(getContext());
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("EVENTS_INFO").child(FirebaseAuth.getInstance().getUid()).child("PARTICIPATING_EVENTS");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 ArrayList<EventsClassNew> eventsClasses = new ArrayList<>();
               for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                   String eventName = dataSnapshot1.getValue().toString();
                   EventsClassNew eventsClass = getEventClass(eventName,allEventClasses);
                   eventsClasses.add(eventsClass);
               }
                //eventsClasses = sortEvents(eventsClasses);
                populateListView(view,eventsClasses);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    ArrayList<EventsClassNew> sortEvents(ArrayList<EventsClassNew> arrayList){
        Collections.sort(arrayList);
        return null;
    }
    void populateListView(View view,ArrayList<EventsClassNew> arrayList){
        ListView mListView = (ListView) view.findViewById(R.id.events_listview);
        EventsAdapter eventsAdapter = new EventsAdapter(getContext(),1,arrayList);
        if(arrayList!=null && eventsAdapter!=null)
        mListView.setAdapter(eventsAdapter);
    }
    ArrayList<EventsClassNew> getAllEvents(DataSnapshot dataSnapshot){

        ArrayList<EventsClassNew> events = new ArrayList<>();

        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                EventsClassNew eventsClass = dataSnapshot2.getValue(EventsClassNew.class);
                if(eventsClass!=null)
                events.add(eventsClass);
            }
        }

        return events;
    }
    EventsClassNew getEventClass(String eventName, ArrayList<EventsClassNew> allEventClasses){
        for(int i=0;i<allEventClasses.size();i++){
            if(eventName.equals(allEventClasses.get(i).getName())){
                return allEventClasses.get(i);
            }
        }
        return null;
    }
}
