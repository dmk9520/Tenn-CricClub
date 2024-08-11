package com.example.jeet.tenncricclub;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class prof_service extends Service {
    InputStream is;
    String ImageName = "image_name";
    String ServerUploadPath = "https://tenncricclub.000webhostapp.com/img_upload_to_server.php";
    String ImagePath = "image_path";
    String imgpath;
    String result, First, Second, Username, DOB, Phone, Type, Address, Finalname;
    SharedPreferences shared;
    String user;
    String username;
    Bitmap bitmap;
    Boolean check = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("Service ", "Started");
        shared = getSharedPreferences("loginprefrence", 0);
        user = shared.getString("user", null);
        try {
            String p = URLEncoder.encode("uname", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
            prof_service.MyTask m = new prof_service.MyTask();
            m.execute(p);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        SharedPreferences sp = getApplication().getSharedPreferences("loginprefrence", MODE_PRIVATE);
        username = sp.getString("user", null);

        try {

            String p = URLEncoder.encode("uname", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            prof_service.imgfetch m = new prof_service.imgfetch();
            m.execute(p);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void jsonget() {
        try {
            JSONObject jObject = new JSONObject(result);
            try {

                // Pulling items from the array
                //First,Second,Username,DOB,Phone,Type,Address

                First = jObject.getString("Firstname");
                Second = jObject.getString("Lastname");
                Username = jObject.getString("Username");
                DOB = jObject.getString("DOB");
                Phone = jObject.getString("Mobilenumber");
                Type = jObject.getString("Type");
                Address = jObject.getString("Address");
                Finalname = First + " " + Second;

               /* sp = getActivity().getSharedPreferences("userdata", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name",Finalname );
                editor.putString("username",Username );
                editor.putString("dob",DOB );
                editor.putString("phone",Phone );
                editor.putString("type",Type );
                editor.putString("address",Address );
                editor.apply();

*/

                //String oneObjectsItem2 = oneObject.getString("anotherSTRINGNAMEINtheARRAY");
                // Toast.makeText(getActivity(), "" + First, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                // Oops
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Service Destroyed", "");
        SharedPreferences sp1 = getApplication().getSharedPreferences("imgpath", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit();
        editor.putString("path", imgpath);
        editor.putString("name", Finalname);
        editor.putString("name", Finalname);
        editor.putString("first", First);
        editor.putString("second", Second);
        editor.putString("username", Username);
        editor.putString("dob", DOB);
        editor.putString("phone", Phone);
        editor.putString("type", Type);
        editor.putString("address", Address);
        editor.putBoolean("isfetched", true);
        editor.apply();

    }

    class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL("http://tenncricclub.000webhostapp.com/USERDATA.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                OutputStream os = con.getOutputStream();
                os.write(strings[0].getBytes());
                InputStream is = con.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder msg = new StringBuilder();
                String temp = br.readLine();
                while (temp != null) {
                    //Toast.makeText(getActivity(), ""+msg[], Toast.LENGTH_SHORT).show();
                    msg.append(temp);
                    temp = br.readLine();
                }
                String s = msg.toString();
                return s;
            } catch (Exception e) {
                String msg;
                msg = e.toString();
                return msg;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            result = s;
            Log.e("Result data : ", "" + result);
            jsonget();
            //Toast.makeText(getActivity(), "Recieved : " +s, Toast.LENGTH_LONG).show();
            //userdata.setText(First);
            //name,dob,type,address,phone

        /*    String usershared = sp.getString("username", null);
            String nameshared = sp.getString("name", null);
            String dobshared = sp.getString("dob", null);
            String typeshared = sp.getString("type", null);
            String phoneshared = sp.getString("phone", null);
            String addressshared = sp.getString("address", null);
*/


        }
    }

    ////Image Fetch
    class imgfetch extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://tenncricclub.000webhostapp.com/getImage.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                OutputStream os = con.getOutputStream();
                os.write(strings[0].getBytes());
                is = con.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder msg = new StringBuilder();
                String temp = br.readLine();
                while (temp != null) {
                    //Toast.makeText(getActivity(), ""+msg[], Toast.LENGTH_SHORT).show();
                    msg.append(temp);
                    temp = br.readLine();
                }
                String s = msg.toString();
                return s;
            } catch (Exception e) {
                String msg;
                msg = e.toString();
                return msg;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            imgpath = s;
            /*Intent intent=new Intent(prof_service.this, Profile_Fragment.class);
            intent.putExtra("serimgpath",imgpath);
            startActivity(intent);*/
/*

            Glide.with(getApplicationContext())
                    .load(imgpath)
                    .into(dp);
*/

            Log.e("Path Recieved : ", "" + imgpath);

            onDestroy();
            //Toast.makeText(getActivity(), "Recieved : " +s, Toast.LENGTH_LONG).show();
        }
    }
}

