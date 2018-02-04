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

import android.util.Log;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by HRITIK on 12/18/2017.
 */

public class HistoryAllEvents extends Fragment {

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

        View rootView = inflater.inflate(R.layout.history_all_events,container,false);
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
                myPlacesArray = new ArrayList<>();
                myPlacesArray = getAllEvents(dataSnapshot);
                //myPlacesArray = sortEvents(myPlacesArray);
                populateListView(view,myPlacesArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    void populateListView(View view,ArrayList<EventsClassNew> arrayList){
        ListView mListView = (ListView) view.findViewById(R.id.events_listview);
        EventsAdapter eventsAdapter = new EventsAdapter(getContext(),1,arrayList);
        if(eventsAdapter!=null)
        mListView.setAdapter(eventsAdapter);
    }
    ArrayList<EventsClassNew> sortEvents(ArrayList<EventsClassNew> arrayList){
        Collections.sort(arrayList);
        return null;
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

}
