package com.example.jeet.tenncricclub;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class score extends Fragment {

    FloatingActionButton fab;
    FloatingActionButton uploadimg;
    SharedPreferences sp1,sp2;
    String type;
    RequestHandler requestHandler;
    ProgressDialog progressDialog;
    Bitmap bitmap;
    boolean check = true;

    FloatingActionButton SelectImageGallery;


    EditText imageName;

    String username;

    String ImageName = "image_name";

    String ImagePath = "image_path";

    String ServerUploadPath = "https://tenncricclub.000webhostapp.com/scorecard.php";


    String id;
    String serimgpath;
    ImageView scorecard;
    /*Image Coding End*/


    public score() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        uploadimg = view.findViewById(R.id.uploadimg);
        scorecard = view.findViewById(R.id.scorecard);

        sp1 = getActivity().getSharedPreferences("loginprefrence", MODE_PRIVATE);
        sp2 = getActivity().getSharedPreferences("matchdetails", MODE_PRIVATE);

        username = sp2.getString("gname", "N/A").toString();
        type = sp1.getString("type", null);

        if (type.equals("Player")) {
            uploadimg.setVisibility(View.INVISIBLE);
        }

        requestHandler = new RequestHandler();
        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);


            }
        });

        requestHandler = new RequestHandler();
        return view;
    }


    public void uploadimgdb() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {


                //     Log.e("username", "" + username);

                ImageUploadToServerFunction();

            }
        }, 5000);

    }

    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {


                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                uploadimgdb();

//                 progressDialog.dismiss();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void fetchdata() {
        Home.c.startService(new Intent(Home.c, prof_service.class));
        Log.e("Service ", "Called");
        //Intent intent=new Intent();


        SharedPreferences sp1 = getActivity().getSharedPreferences("imgpath", MODE_PRIVATE);

        serimgpath = sp1.getString("path", null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Glide.with(getActivity())
                        .load(serimgpath)
                        .into(scorecard);


            }
        }, 0);

    }

    public void ImageUploadToServerFunction() {

        ByteArrayOutputStream byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.WEBP, 0, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                //// progressDialog = ProgressDialog.show(getActivity(), "Image is Uploading", "Please Wait", false, false);

            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                //\progressDialog.dismiss();

                Toast.makeText(getActivity(), "Please Restart App to see changes..", Toast.LENGTH_LONG).show();

                // Printing uploading success message coming from server on android app.

                Log.e("Recieved :", "" + string1);

                //   Toast.makeText(getActivity(), "Please Restart App to see Changes.", Toast.LENGTH_LONG).show();
                // Setting image as transparent after done uploading.
                //imageView.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {

//                Glide.get(getActivity()).clearDiskCache();

                score.ImageProcessClass imageProcessClass = new score.ImageProcessClass();

                HashMap<String, String> HashMapParams = new HashMap<String, String>();

                HashMapParams.put(ImageName, username);

                HashMapParams.put(ImagePath, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject;
                BufferedReader bufferedReaderObject;
                int RC;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


    }
}
