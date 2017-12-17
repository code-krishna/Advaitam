package ac.nita.advaitam4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;



public class QrScanner extends Fragment {


    private IntentIntegrator qrScan;
    TextView textView1,textView2;


        @Nullable
        @Override

        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //returning our layout file
            //change R.layout.yourlayoutfilename for each of your fragments


            View v = inflater.inflate(R.layout.fragment_qr_scanner, container, false);
            qrScan = new IntentIntegrator(getActivity());
            textView1 = (TextView)v.findViewById(R.id.textView5);
            textView2 = (TextView)v.findViewById(R.id.textView6);
            Button button = (Button)v.findViewById(R.id.scan);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    qrScan.initiateScan();
                }
            });

            return v;
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
                    textView1.setText(obj.getString("name"));
                    textView2.setText(obj.getString("address"));
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




    @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            //you can set the title for your toolbar here for different fragments different titles
            getActivity().setTitle("Qr Scanner");
        }


    }


