package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ac.nita.advaitam4.R;


/**
 * Created by HRITIK on 12/18/2017.
 */

public class ListOfParticipantFragment extends Fragment {

    private TextView a,b;


    public void ListOfParticipantFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_list_of_participant,container,false);
        a = (TextView)rootView.findViewById(R.id.a);
        b = (TextView)rootView.findViewById(R.id.b);



        return rootView;
    }




}
