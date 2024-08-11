package com.example.jeet.tenncricclub;

import android.app.Fragment;
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
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * Created by Dhruvit Katrodiya on 14-02-2018.
 */

public class Profile_Fragment extends Fragment {

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String profilename;
    TextView tvusername, tvdob, tvphone, tvtype, tvaddress, userdata;

    String result, First, Second, Username, DOB, Phone, Type, Address;
    ImageView dp;
    int RESULT_LOAD_IMAGE;
    SharedPreferences shared;
    String Finalname;
    String user;

    Button logout, editprofile;
    SharedPreferences sp;

    InputStream is;

    URL url;

    String imgpath;

    /*Image Upload Coding*/
    Bitmap bitmap;

    boolean check = true;

    FloatingActionButton SelectImageGallery;


    EditText imageName;

    ProgressDialog progressDialog;

    String username;

    String ImageName = "image_name";

    String ImagePath = "image_path";

    String ServerUploadPath = "https://tenncricclub.000webhostapp.com/img_upload_to_server.php";
    String id;
    String serimgpath, dataname, datausername, datadob, dataphone, datatype, dataaddress;
    private RequestHandler requestHandler;
    /*Image Coding End*/

    @Override
    public void onStart() {
        super.onStart();
        fetchdata();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchdata();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //tvusername,tvdob,tvphone,tvtype,tvaddress
        tvusername = view.findViewById(R.id.tvusername);
        tvphone = view.findViewById(R.id.tvnumber);
        tvdob = view.findViewById(R.id.tvdob);
        tvtype = view.findViewById(R.id.tvtype);
        tvaddress = view.findViewById(R.id.tvaddress);
        collapsingToolbarLayout = view.findViewById(R.id.toolbar_layout);
        shared = getActivity().getSharedPreferences("loginprefrence", 0);
        SelectImageGallery = (FloatingActionButton) view.findViewById(R.id.editdp);
        dp = view.findViewById(R.id.dp);
        editprofile = view.findViewById(R.id.editprofile);




/*
        shared = getActivity().getSharedPreferences("loginprefrence", 0);
        user = shared.getString("user", null);
        try {
            String p = URLEncoder.encode("uname", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
            prof_service.MyTask m = new prof_service.MyTask();
            m.execute(p);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp2 = getActivity().getSharedPreferences("imgpath", MODE_PRIVATE);
                Username = sp2.getString("username", null);
                First = sp2.getString("first", null);
                Second = sp2.getString("second", null);
                DOB = sp2.getString("dob", null);
                Phone = sp2.getString("phone", null);
                Address = sp2.getString("address", null);

                Intent inte = new Intent(getActivity(), edit_profile.class);
                //First,Second,Username,DOB,Phone,Type,Address
                inte.putExtra("fname", First);
                Log.e("First : ", "" + First);
                inte.putExtra("lname", Second);
                inte.putExtra("username", Username);
                inte.putExtra("dob", DOB);
                inte.putExtra("phone", Phone);
                inte.putExtra("address", Address);
/*
                SharedPreferences.Editor editor = sp2.edit();
                editor.clear().apply();*/
                startActivity(inte);
            }
        });
        /*SelectImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("mr.bean","chaklasiya harsh");
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);


            }
        });*/


        SharedPreferences sp = getActivity().getSharedPreferences("loginprefrence", MODE_PRIVATE);
        username = sp.getString("user", null);


/*

        try {
            String p = URLEncoder.encode("uname", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            Profile_Fragment.imgfetch m = new Profile_Fragment.imgfetch();
            m.execute(p);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
*/



        /*Data check*/
/*
        shared = getActivity().getSharedPreferences("loginprefrence", 0);
        user = shared.getString("user", null);
        try {
            String p = URLEncoder.encode("uname", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
            Profile_Fragment.MyTask m = new Profile_Fragment.MyTask();
            m.execute(p);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        /*Data Check*/

        /*image upload find*/


        SelectImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);


            }
        });



        logout = view.findViewById(R.id.Logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getActivity().getSharedPreferences("loginprefrence", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user", null);
                editor.putString("pass", null);
                editor.commit();
                editor.apply();
                startActivity(new Intent(getActivity(), SplashActivity.class));
            }
        });


        return view;
    }

    public void uploadimgdb() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {


                Log.e("username", "" + username);

                ImageUploadToServerFunction();

            }
        }, 0);

    }

    public void fetchdata() {
        Home.c.startService(new Intent(Home.c, prof_service.class));
        Log.e("Service ", "Called");
        //Intent intent=new Intent();


        SharedPreferences sp1 = getActivity().getSharedPreferences("imgpath", MODE_PRIVATE);

        serimgpath = sp1.getString("path", null);
        dataname = sp1.getString("name", null);
        datausername = sp1.getString("username", null);
        datadob = sp1.getString("dob", null);
        dataaddress = sp1.getString("address", null);
        dataphone = sp1.getString("phone", null);
        datatype = sp1.getString("type", null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Glide.with(getActivity())
                        .load(serimgpath)
                        .into(dp);
                tvusername.setText(datausername);
                tvdob.setText(datadob);
                tvphone.setText(dataphone);
                tvtype.setText(datatype);
                tvaddress.setText(dataaddress);
                collapsingToolbarLayout.setTitle(dataname);

            }
        }, 0);

    }

    public void ImageUploadToServerFunction() {

        ByteArrayOutputStream byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                progressDialog = ProgressDialog.show(getActivity(), "Image is Uploading", "Please Wait", false, false);

            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                Toast.makeText(getActivity(), "Please Restart App to see changes..", Toast.LENGTH_LONG).show();

                // Printing uploading success message coming from server on android app.

                Log.e("Recieved :", "" + string1);

                //   Toast.makeText(getActivity(), "Please Restart App to see Changes.", Toast.LENGTH_LONG).show();
                // Setting image as transparent after done uploading.
                //imageView.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {

                Glide.get(getActivity()).clearDiskCache();

                ImageProcessClass imageProcessClass = new ImageProcessClass();

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

    /*img upload onactivity*/
    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                dp.setImageBitmap(bitmap);
                progressDialog = ProgressDialog.show(getActivity(), "Image is Uploading", "Please Wait", false, false);
                uploadimgdb();
                progressDialog.dismiss();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        View view = getView();

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


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


}





