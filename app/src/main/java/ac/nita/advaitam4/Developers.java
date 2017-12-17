package ac.nita.advaitam4;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class Developers extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_developers, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("History");


        ListView listView = (ListView) view.findViewById(R.id.listview);


        int[] IMAGES = {R.drawable.chayan, R.drawable.krishna, R.drawable.subham, R.drawable.subharaj, R.drawable.hritik, R.drawable.sourav};


        String[] NAMES = {"CHAYAN DAS", "VAMSI KRISHNA PENDYALA", "SUBHAM SAHA", "SUBHARAJ BHOWMIK", "HRITIK KUMAR", "SOURAV MANDAL"};

        String[] BRANCH = {"CSE", "CSE", "CSE", "CSE", "CSE", "EIE"};

        String[] ENROLL = {"15UCS019", "15UCS037", "15UCS001", "15UCS026", "16UCS037", "15UEI005"};

        String[] MOBILE = {"9485023928", "9892580818", "8974279359", "9436193664", "8340179806", "8256995686"};

        String[] EMAIL = {"chayan.cse234@gmail.com", "krishna.pendiala@gmail.com", "subhamsahadeep@gmail.com", "subharajbhowmik@gmail.com", "hritikkumar.rd@gmail.com", "souravmandalm@gmail.com"};


        CustomAdapter customAdapter = new CustomAdapter(getContext(), IMAGES, NAMES, BRANCH, ENROLL, MOBILE, EMAIL);
        listView.setAdapter(customAdapter);


    }
}
