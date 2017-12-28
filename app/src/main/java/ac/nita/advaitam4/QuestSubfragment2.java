package ac.nita.advaitam4;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestSubfragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestSubfragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestSubfragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuestSubfragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestSubfragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestSubfragment2 newInstance(String param1, String param2) {
        QuestSubfragment2 fragment = new QuestSubfragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quest_subfragment2, container, false);
    }


    ShareButton shareButton;

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = databaseReference.child("data/user");


        SharePhoto sharePhoto1 = new SharePhoto.Builder()
                .setBitmap(BitmapFactory.decodeResource(null,R.drawable.advaitam_4_logo))
                .build();

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .setQuote("I'm playing this cool game! Download the Advaitam app to see how many quest you can complete!")
                .build();




        shareButton = (ShareButton)view.findViewById(R.id.fb_share);
        shareButton.setShareContent(content);

        users.addValueEventListener(new ValueEventListener() {

            ArrayList<Long> pointsArray = new ArrayList<Long>();
            ArrayList<String> namesArray = new ArrayList<String>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        long sum=0;
                        for(DataSnapshot snapshot1 : snapshot.child("points").getChildren()) {
                            long value = (long)snapshot1.getValue();
                            sum+=value;
                        }

                        try {
                            namesArray.add(snapshot.child("name").getValue().toString());
                            Log.d("mylog","namesarray entries "+snapshot.child("name").getValue().toString());
                        }catch (Exception e){
                            namesArray.add("Name Not Found");
                        }

                        pointsArray.add(sum);
                    }

                    String[] names = new String[5];
                    long[] points = new long[5];

                    String textviewData = "";

                Log.d("mylog","namesarray "+namesArray.toString());
                Log.d("mylog","pointsarray "+pointsArray.toString());

                Log.d("mylog","Points array size "+pointsArray.size());

                    for (int i = 0; i < 5; i++) {

                        int maxIndex = getMax(pointsArray);

                        points[i] = pointsArray.get(maxIndex);
                        names[i] = namesArray.get(maxIndex);
                        textviewData = textviewData + names[i] + " ["+points[i]+"]" + "\n";
                        Log.d("mylog","textviewData "+names[i] + " ["+points[i]+"]");
                        long ele = 0;
                        pointsArray.set(maxIndex,ele);
                        Log.d("mylog","pointsarray every loop "+pointsArray.toString());
                    }


                    TextView leadsrBoards = (TextView) view.findViewById(R.id.leader_board);
                    leadsrBoards.setText(textviewData);



                }

                @Override
                public void onCancelled (DatabaseError databaseError){

                }





        });


    }


    public int getMax(ArrayList<Long> arrayList){

        int maxIndex = 0;

        long max = arrayList.get(0);


            for (int i = 0; i < arrayList.size(); i++) {
                if (max < arrayList.get(i)) {
                    max = arrayList.get(i);
                    maxIndex = i;
                }
            }

        return maxIndex;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
   //                 + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
