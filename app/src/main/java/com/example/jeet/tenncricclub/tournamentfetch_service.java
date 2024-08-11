package com.example.jeet.tenncricclub;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
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

public class tournamentfetch_service extends Service {

    public static List<String> teamjson1, teamjson2, tdate, ttime, tvenue, tgname,tmobile;
    String result;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("label", "Started Service");
        MyTask m = new MyTask();
        m.execute();

        return super.onStartCommand(intent, flags, startId);
    }

    public void jsonget() {
        teamjson1 = new ArrayList<String>();
        tdate = new ArrayList<>();
        ttime = new ArrayList<>();
        teamjson2 = new ArrayList<>();
        tvenue = new ArrayList<>();
        tgname = new ArrayList<>();
        tmobile = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray != null) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObject = jsonArray.getJSONObject(i);

                    teamjson1.add(jObject.getString("teamone"));
                    tdate.add(jObject.getString("date"));
                    ttime.add(jObject.getString("time"));
                    teamjson2.add(jObject.getString("teamtwo"));
                    tvenue.add(jObject.getString("venue"));
                    tgname.add(jObject.getString("gname"));
                    tmobile.add(jObject.getString("mobile"));
                    //  Log.e("harsh",""+tvenue.get(i));
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

                URL url = new URL("http://tenncricclub.000webhostapp.com/GETGAMEDATA.php");
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
            Tournaments_Fragment.setData();

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
