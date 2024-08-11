package com.example.jeet.tenncricclub;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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

public class playerjoin_service extends Service {

    InputStream is;
    SharedPreferences shared, sp;
    String user, gname;
    String username, result;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("Service ", "Started lolwa");
        shared = getSharedPreferences("loginprefrence", 0);
        sp = getSharedPreferences("matchdetails", 0);
        user = shared.getString("user", null);
        insert();
        return super.onStartCommand(intent, flags, startId);
    }

    public void insert() {
        JSONObject j = new JSONObject();
        try {
            j.put("user", user);
            //j.put("p_contact",contact);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, j.toString(), Toast.LENGTH_LONG).show();
        String jOutput = j.toString();
        Log.e("sadasdasd", jOutput);
        try {
            String p = URLEncoder.encode("jdata", "UTF-8") + "=" + URLEncoder.encode(jOutput, "UTF-8");
            playerjoin_service.MyTask m = new playerjoin_service.MyTask();
            m.execute(p);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL("http://tenncricclub.000webhostapp.com/playermatchcmp.php");
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
            Log.e("Recieved data : ", "" + result);
           if (s.trim().equals("1")) {
                //tournamentAdapter.linearcard.setBackgroundColor(getResources().getColor(R.color.Blue1));
            }
        }
    }


}
