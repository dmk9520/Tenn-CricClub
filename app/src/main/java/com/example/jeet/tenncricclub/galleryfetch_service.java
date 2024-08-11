package com.example.jeet.tenncricclub;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class galleryfetch_service extends Service {

    public static List<String> imgurl;
    String result;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("lol", "Started Service");
        galleryfetch_service.MyTask m = new galleryfetch_service.MyTask();
        m.execute();

        return super.onStartCommand(intent, flags, startId);
    }

    public void jsonget() {
        imgurl = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray != null) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObject = jsonArray.getJSONObject(i);

                    imgurl.add(jObject.getString("imgurl"));
                    Gallery_Fragment.setImage(imgurl);

                    Log.e("harsh", "" + imgurl.get(i));
                }
            }
            // Pulling items from the array
            //First,Second,Username,DOB,Phone,Type,Address


        } catch (JSONException e) {
            // Oops
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Service Destroyed", "");

    }

    class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                Log.e("Do in background", "call thayo");
                URL url = new URL("http://tenncricclub.000webhostapp.com/GALLERYIMGFETCH.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                /*OutputStream os = con.getOutputStream();
                os.write(strings[0].getBytes());*/
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
                Log.e("Resultbackground", "" + s);
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
            Log.e("Result data : ", "" + s);
            jsonget();


            // jsonget();
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
}
