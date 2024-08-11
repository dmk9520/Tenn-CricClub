package com.example.jeet.tenncricclub;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class game_data_service extends Service {
    String datagname, datavenue, datadate, datatime, dataphone, datausername, datateamone, datateamtwo;
    String result;
    ProgressDialog progressdialog;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        datagname = intent.getStringExtra("datagname");
        datavenue = intent.getStringExtra("datavenue");
        datadate = intent.getStringExtra("datadate");
        datatime = intent.getStringExtra("datatime");
        dataphone = intent.getStringExtra("dataphone");
        datateamone = intent.getStringExtra("datateamone");
        datateamtwo = intent.getStringExtra("datateamtwo");


        insert();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        SharedPreferences sp = getSharedPreferences("gameresponse", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("response", result);
        editor.apply();

    }

    public void insert() {
        JSONObject j = new JSONObject();
        try {

            j.put("datagname", datagname);
            j.put("datavenue", datavenue);
            j.put("datadate", datadate);
            j.put("datatime", datatime);
            j.put("dataphone", dataphone);
            j.put("datateamone", datateamone);
            j.put("datateamtwo", datateamtwo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, j.toString(), Toast.LENGTH_LONG).show();
        String jOutput = j.toString();
        Log.e("Data of JOutput : ", "" + jOutput);
        try {

            String p = URLEncoder.encode("jdata", "UTF-8") + "=" + URLEncoder.encode(jOutput, "UTF-8");
            game_data_service.MyTask m = new game_data_service.MyTask();
            Log.e("Data of json   :   ", "" + p);
            m.execute(p);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
/*            progressdialog = ProgressDialog.show(game_data_service.this, "Creating Game...",
                    "Please Wait...", false,true);*/
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://tenncricclub.000webhostapp.com/GAMEDATA.php");
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
            //return "Test";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            result = s;
            // progressdialog.dismiss();
            Log.e("Data in Service : ", "" + result);

        }
    }
}
