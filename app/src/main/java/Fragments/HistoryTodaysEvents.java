package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Info.EventsClass;
import ac.nita.advaitam4.R;


public class HistoryTodaysEvents extends Fragment {

    Context context;

    ArrayList<EventsClass> myPlacesArray;

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

        View rootView = inflater.inflate(R.layout.history_todays_events,container,false);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress_bar);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callFirebase(view);

    }

    void callFirebase(final View view)
    {
        FirebaseApp.initializeApp(getContext());
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("data/events/event1/participants");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myPlacesArray = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    EventsClass eventsClass = dataSnapshot1.getValue(EventsClass.class);
                    //Log.d("mylog", place.getNameStudent()+place.getPhone()+place.getRoll()+" ");
                    myPlacesArray.add(eventsClass);
                }
                ListView mListView = (ListView) view.findViewById(R.id.todays_events_listview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
