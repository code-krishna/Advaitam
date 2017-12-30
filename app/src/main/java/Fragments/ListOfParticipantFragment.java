package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


/**
 * Created by HRITIK on 12/18/2017.
 */

public class ListOfParticipantFragment extends Fragment {


    public void ListOfParticipantFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_list_of_participant,container,false);
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
        ListView mListView = (ListView) rootView.findViewById(R.id.myListView);
        PlaceArrayAdapter mArrayAdapter = new PlaceArrayAdapter(this.getContext(), R.layout.row, myPlacesArray);
        return rootView;
    }


}
