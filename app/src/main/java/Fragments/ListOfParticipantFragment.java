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

import ac.nita.advaitam4.R;

import android.util.Log;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by HRITIK on 12/18/2017.
 */

public class ListOfParticipantFragment extends Fragment {

    Context context;

    ArrayList<Place> myPlacesArray;

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

        View rootView = inflater.inflate(R.layout.fragment_list_of_participant,container,false);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress_bar);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseApp.initializeApp(getContext());
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("data/events/event1/participants");

/*
        Place[] myPlacesArray = new Place[]{
                new Place("Art House" , "15UCS037" ,"1234567890"),
                new Place("Bike Shop",  "15UCS036","1234567890"),
                new Place("Camera Fix", "15UCS035","1234567890"),
                new Place("YETspace","15UCS034", "1234567890"),
                new Place("Secret Space Pad","15UCS033","1234567890"),
                new Place("Taylor’s Tailor","15UCS032" , "1234567890"),
                new Place("Boathouse","15UCS031" ,"1234567890"),
                new Place("Not Apple Store","15UCS030", "1234567890"),
                new Place("Tool Battleground","15UCS029", "1234567890"),
                new Place("Travelpediocity","15UCS028" ,"1234567890"),
                new Place("UFO Pick-a-part","15UCS027","1234567890"),
                new Place("Spawrk’s House", "15UCS026", "1234567890"),
        };




        ListView mListView = (ListView) view.findViewById(R.id.myListView);
        PlaceArrayAdapter mArrayAdapter = new PlaceArrayAdapter(getContext(), R.layout.row, myPlacesArray);

        progressBar.setVisibility(View.INVISIBLE);
        */


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myPlacesArray = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Place place = dataSnapshot1.getValue(Place.class);
                    //Log.d("mylog", place.getNameStudent()+place.getPhone()+place.getRoll()+" ");
                    myPlacesArray.add(place);
                }



/*
                Place[] myPlacesArray = new Place[]{
                        new Place("Art House" , "15UCS037" ,"1234567890"),
                        new Place("Bike Shop",  "15UCS036","1234567890"),
                        new Place("Camera Fix", "15UCS035","1234567890"),
                        new Place("YETspace","15UCS034", "1234567890"),
                        new Place("Secret Space Pad","15UCS033","1234567890"),
                        new Place("Taylor’s Tailor","15UCS032" , "1234567890"),
                        new Place("Boathouse","15UCS031" ,"1234567890"),
                        new Place("Not Apple Store","15UCS030", "1234567890"),
                        new Place("Tool Battleground","15UCS029", "1234567890"),
                        new Place("Travelpediocity","15UCS028" ,"1234567890"),
                        new Place("UFO Pick-a-part","15UCS027","1234567890"),
                        new Place("Spawrk’s House", "15UCS026", "1234567890"),
                };

*/

                Log.d("mylog", " Arraylist size is "+myPlacesArray.size());

                 //Place[] toArrayPlaces = new Place[myPlacesArray.size()];
                 //myPlacesArray.toArray(toArrayPlaces);

                ListView mListView = (ListView) view.findViewById(R.id.events_listview);
                PlaceArrayAdapter mArrayAdapter = new PlaceArrayAdapter(getActivity(), R.layout.row, myPlacesArray);
                mListView.setAdapter(mArrayAdapter);

                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
