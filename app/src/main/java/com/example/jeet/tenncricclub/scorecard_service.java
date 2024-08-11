package com.example.jeet.tenncricclub;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class scorecard_service extends Service {
    String gname,imgpath;
    InputStream is;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences sp = getApplication().getSharedPreferences("matchdetails", MODE_PRIVATE);
        gname = sp.getString("gname", null);

        try {

            String p = URLEncoder.encode("gname", "UTF-8") + "=" + URLEncoder.encode(gname, "UTF-8");
            scorecard_service.imgfetch m = new scorecard_service.imgfetch();
            m.execute(p);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    class imgfetch extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://tenncricclub.000webhostapp.com/scorecardfetch.php");
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

            onDestroy();
            //Toast.makeText(getActivity(), "Recieved : " +s, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Service Destroyed", "");
        SharedPreferences sp1 = getApplication().getSharedPreferences("imgpath1", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit();
        editor.putString("path", imgpath);
        editor.apply();

    }
}
