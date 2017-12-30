package ac.nita.advaitam4;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import Info.user_info;

import static android.content.ContentValues.TAG;


public class EditProfile extends Fragment {
    private static final int GALLERY_DATA = 2 ;
    private ImageView img;
    private EditText editName,editEnroll,editNumber;
    private TextView nameView,enrollView,cntView;
    private Spinner spinner1;
    private Button btn;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private StorageReference sRef;
    private String uid;
    private Uri imageuri;
    private ProgressDialog mProgressDialog;

    SharedPreferences preferences ;
    SharedPreferences.Editor editor;


    public EditProfile(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Initializing Views
        img             = rootView.findViewById(R.id.profile_image_edit);
        editName        = rootView.findViewById(R.id.editTextName);
        editEnroll      = rootView.findViewById(R.id.editTextEnroll);
        editNumber      = rootView.findViewById(R.id.editTextContact);
        btn             = rootView.findViewById(R.id.button_upload);
        nameView        = rootView.findViewById(R.id.textViewName);
        enrollView      = rootView.findViewById(R.id.textViewEnroll);
        spinner1        = rootView.findViewById(R.id.spinnerEdit);

        preferences = this.getActivity().getSharedPreferences("USER",0);
        editor = preferences.edit();

        //Initiliazing Variables
        mProgressDialog  = new ProgressDialog(getContext());
        storage          = FirebaseStorage.getInstance();
        mAuth            = FirebaseAuth.getInstance();
        mUser            = mAuth.getCurrentUser();
        database         = FirebaseDatabase.getInstance();
        mRef             = database.getReference();
        sRef             = storage.getReference();
        uid              = mUser.getUid();


        mRef.child("USER").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
//                    Log.d("valueName:", "DATA : "+ dataSnapshot);
                    editor.putString("NAME",(String)dataSnapshot.child("name").getValue()).apply();
                    editor.putString("CONTACT",(String)dataSnapshot.child("contact").getValue()).apply();
                    editor.putString("ENROLL",(String)dataSnapshot.child("enroll").getValue()).apply();
                    editor.putString("COLLEGE",(String)dataSnapshot.child("college").getValue()).apply();
//                    editName.setText((String)dataSnapshot.child("name").getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editName.setText( preferences.getString("NAME","NAME"));
        editEnroll.setText( preferences.getString("ENROLL","ENROLL"));
        editNumber.setText(preferences.getString("CONTACT","CONTACT"));

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .error(R.drawable.ic_account_circle_black_24dp);
            Glide.with(img.getContext())
                    .load(mUser.getPhotoUrl())
                    .apply(options)
                    .into(img);
        return rootView;
    }


    private void upload_data(String uid) {

        Log.d("myLog","Uploding :"+ uid);

        String name = editName.getText().toString();
        String number = editNumber.getText().toString();
        String enroll = editEnroll.getText().toString();
        String spinnerItem = spinner1.getSelectedItem().toString();
        Uri uri = Uri.parse(preferences.getString("ProfileImage_path"," "));

        editor.putString("NAME",name).apply();
        editor.putString("CONTACT",number).apply();
        editor.putString("ENROLL",enroll).apply();

            user_info user_data = new user_info(uid,name,enroll,number,uri.toString(),spinnerItem);
            Log.d("data" ,"" +user_data.getUid()+"  "+user_data.getName()+"  "+user_data.getContact()+"  "+user_data.getEnroll()+"  "+user_data.getDownload_uri() + user_data.getCollege());
            mRef.child("USER").child(uid).setValue(user_data);
            updateProfile(name,uri);
            Log.d("user data" ,"msg "+ user_data.getName()+ user_data.getEnroll() + user_data.getContact() + spinnerItem);
            Toast.makeText(getActivity()," Uploaded ",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getContext(),Categories.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }


    private void updateProfile(String name,Uri imageuri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(preferences.getString("NAME", "NAME"))
                .setPhotoUri(Uri.parse(preferences.getString("ProfileImage_path"," ")))
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("user profile : ", "User profile updated.");
                        }
                    }
                });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Edit Profile");
        //Button to save data
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preferences.getString("ProfileImage_path"," ").equals(" ")){
                    Toast.makeText(getContext(),"First set Profile Pic",Toast.LENGTH_SHORT).show();
                }else {
                    upload_data(uid);
                }
            }
        });

        //Image button to upload imagefile
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALLERY_DATA);
            }
        });


    }

        @Override
        public void onActivityResult(int requestCode,int resultCode,Intent data){
            super.onActivityResult(requestCode,resultCode,data);
            //Progress Dialog to show

            mProgressDialog.setMessage("Uploading...");
            if(requestCode == GALLERY_DATA && resultCode == Activity.RESULT_OK){
                final Uri uri = data.getData();
                mProgressDialog.show();
                img.setEnabled(false);
                StorageReference filePath = sRef.child("USERS").child(uid).child("pic");
                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        // Using SharedPreferences to store Downloading uri
                        editor.putString("ProfileImage_path",downloadUri.toString()).apply();
                        Toast.makeText(getContext(),"Upload one",Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                        img.setEnabled(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getContext(),"failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }
}
