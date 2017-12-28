package ac.nita.advaitam4;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestSubfragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestSubfragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestSubfragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    //final DatabaseReference mCounterRef = mRootRef.child("data/user/"+user.getUid()+"/points");
    final DatabaseReference mCounterRef = mRootRef.child("data/user/");



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuestSubfragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestSubfragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestSubfragment1 newInstance(String param1, String param2) {
        QuestSubfragment1 fragment = new QuestSubfragment1();
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
        return inflater.inflate(R.layout.fragment_quest_subfragment1, container, false);
    }


    TextView points_textview;
    TextView questCountTextview;
    DataSnapshot checkingDatasnapshot,userDataSnapshot;

    ShareButton shareButton;
    ShareDialog shareDialog;


    long totalPoints=0;
    long totalQuests=0;
    long questsCompleted=0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        points_textview = (TextView)view.findViewById(R.id.points);
        points_textview.setText(String.valueOf(totalPoints));
        questCountTextview = (TextView)view.findViewById(R.id.quest_count);
        Button button = (Button)view.findViewById(R.id.qr_scan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //qrScan.initiateScan();
                IntentIntegrator.forSupportFragment(QuestSubfragment1.this).initiateScan();
            }
        });



        SharePhoto sharePhoto1 = new SharePhoto.Builder()
                .setBitmap(BitmapFactory.decodeResource(null,R.drawable.advaitam_4_logo))
                .build();

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .setQuote("I'm playing this cool game! Download the Advaitam app to see how many quest you can complete!")
                .build();




        shareButton = (ShareButton)view.findViewById(R.id.fb_share);
        shareButton.setShareContent(content);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Just a moment...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final ProgressDialog progressDialog2 = new ProgressDialog(getContext());
        progressDialog2.setTitle("Processing...");
        progressDialog2.setMessage("Just a moment...");
        progressDialog2.setCancelable(false);
        progressDialog2.show();

        DatabaseReference checkingReference = mRootRef.child("data/games/quest/");

        checkingReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("mylog","inside checkingSnapshot collectorr");
                checkingDatasnapshot = dataSnapshot;
                totalQuests = checkingDatasnapshot.getChildrenCount();
                questCountTextview.setText(questsCompleted+"/"+totalQuests);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mCounterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("mylog","inside quest points collector");

                userDataSnapshot = dataSnapshot;

                // getValue() returns Long

                //long points = (long)dataSnapshot.child(user.getUid()+"/points").getValue();
                //points_textview.setText(String.valueOf(points));


                long points=0;
                for(DataSnapshot snapshot : dataSnapshot.child(user.getUid()+"/points").getChildren()){
                    try {
                        points += (long) snapshot.getValue();
                    }catch (Exception e){

                    }
                }
                points_textview.setText(String.valueOf(points));



                long sum=0;
                questsCompleted = dataSnapshot.child(user.getUid()+"/points").getChildrenCount();
                questCountTextview.setText(questsCompleted+"/"+totalQuests);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        sum += (long) snapshot.child("points").getValue();
                    }catch (Exception e){

                    }
                }


                System.out.println("count before setValue()=" + sum);

                totalPoints = sum;

                progressDialog2.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // throw an error if setValue() is rejected
                //throw databaseError.toException();
            }
        });




    }


    public void postPicture() {
            //share dialog

        Log.d("mylog","post picture");


        AlertDialog.Builder shareDialog = new AlertDialog.Builder(getContext());
            shareDialog.setTitle("Share Screen Shot");
            shareDialog.setMessage("Share image to Facebook?");
            shareDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //share the image to Facebook
                    SharePhoto photo = new SharePhoto.Builder().setBitmap(BitmapFactory.decodeResource(null,R.drawable.advaitam_4_logo)).build();
                    SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
                    shareButton.setShareContent(content);
                    shareButton.performClick();
                }
            });
            shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            shareDialog.show();


    }



    public boolean updatePoints(String key, final String value){


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Just a moment...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.d("mylog","after prog Dialog");

        long points;


        String returnedValue = checkingDatasnapshot.child(key).child("value").getValue().toString();
        Object isPresent = userDataSnapshot.child(user.getUid()).child("points/"+key).getValue();
        final int questPoints = Integer.parseInt(checkingDatasnapshot.child(key).child("points").getValue().toString());
        totalQuests = checkingDatasnapshot.getChildrenCount();

        if(value.equals(returnedValue)) {


            if(isPresent==null) {


                mCounterRef.child(user.getUid()).child("points/" + key + "/").setValue((totalPoints += questPoints), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError,
                                           DatabaseReference databaseReference) {
                        if (databaseError != null) {
                                        System.out.println("Error: " + databaseError.getMessage());
                        } else {
                                        Context context = getContext();

                                        AlertDialog.Builder builder;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                                        } else {
                                            builder = new AlertDialog.Builder(context);
                                        }
                                        builder.setTitle("Congratulations...!")
                                                .setMessage("You completed one more Quest! "+questPoints+" Points have been added to your account...")
                                                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // do nothing
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                        }
                    }
                });


            } else {


                                        Context context = getContext();

                                        AlertDialog.Builder builder;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                                        } else {
                                            builder = new AlertDialog.Builder(context);
                                        }
                                        builder.setTitle("Invalid!")
                                                .setMessage("Ye to ho chuka hai beta!")
                                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // do nothing
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();


            }

            //mCounterRef.child(user.getUid()).child("points"+key+"/").setValue(totalPoints += questPoints);

        }

        progressDialog.dismiss();
        return true;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {

            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    // Toast.makeText(getContext(),obj.getString("key")+obj.getString("value"),Toast.LENGTH_LONG).show();

                    Log.d("mylog","");
                    if(updatePoints(obj.getString("key"),obj.getString("value"))){
                        //points_textview.setText(String.valueOf(totalPoints));
                        //questCountTextview.setText(questsCompleted+"/"+totalQuests);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
           //         + " must implement OnFragmentInteractionListener");
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
