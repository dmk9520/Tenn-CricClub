package com.example.jeet.tenncricclub;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class matchinfo extends Fragment implements View.OnClickListener {


    ImageView imgteam1, imgteam2;
    TextView nameteam1, nameteam2, series_name, matchdate, matchtime, matchvenue,contact;

    Button t1, t2, jointeam;
    LinearLayout teamchoose;

    SharedPreferences sp, sp1;

    String pteam, puname, pgname, result, type;
    View view;

    public matchinfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_matchinfo, container, false);

        findview();

        jointeam.setOnClickListener(matchinfo.this);
        t1.setOnClickListener(matchinfo.this);
        t2.setOnClickListener(matchinfo.this);

        sp1 = getActivity().getSharedPreferences("loginprefrence", MODE_PRIVATE);
        sp = getActivity().getSharedPreferences("matchdetails", MODE_PRIVATE);

        type = sp1.getString("type", null);
        puname = sp1.getString("user", null);
        pgname = sp.getString("gname", "N/A").toString();

        nameteam1.setText(sp.getString("teamone", "N/A").toString());
        nameteam2.setText(sp.getString("teamtwo", "N/A").toString());
        matchvenue.setText(sp.getString("venue", "N/A").toString());
        matchdate.setText(sp.getString("date", "N/A").toString());
        matchtime.setText(sp.getString("time", "N/A").toString());
        contact.setText(sp.getString("mobile", "N/A").toString());
        series_name.setText(pgname);
        t1.setText(sp.getString("teamone", "N/A").toString());
        t2.setText(sp.getString("teamtwo", "N/A").toString());


        if (type.equals("Player")) {
            jointeam.setVisibility(VISIBLE);
        }
        return view;
    }

    public void findview() {
        nameteam1 = view.findViewById(R.id.nameteam1);
        nameteam2 = view.findViewById(R.id.nameteam2);
        series_name = view.findViewById(R.id.series_name);
        matchdate = view.findViewById(R.id.matchdate);
        matchtime = view.findViewById(R.id.matchtime);
        matchvenue = view.findViewById(R.id.matchvenue);
        contact = view.findViewById(R.id.contact);
        t1 = view.findViewById(R.id.t1);
        t2 = view.findViewById(R.id.t2);
        teamchoose = view.findViewById(R.id.teamchoose);
        jointeam = view.findViewById(R.id.jointeam);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.jointeam) {
            teamchoose.setVisibility(view.VISIBLE);
        } else if (v.getId() == R.id.t1) {
            pteam = sp.getString("teamone", "N/A").toString();
            insert();
            teamchoose.setVisibility(View.INVISIBLE);
            jointeam.setVisibility(View.INVISIBLE);
        } else if (v.getId() == R.id.t2) {
            pteam = sp.getString("teamtwo", "N/A").toString();
            insert();
            teamchoose.setVisibility(View.INVISIBLE);
            jointeam.setVisibility(View.INVISIBLE);
        }
    }

    public void insert() {
        JSONObject j = new JSONObject();
        try {
            j.put("uname", puname);
            j.put("team", pteam);
            j.put("gname", pgname);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, j.toString(), Toast.LENGTH_LONG).show();
        String jOutput = j.toString();
        try {
            String p = URLEncoder.encode("jdata", "UTF-8") + "=" + URLEncoder.encode(jOutput, "UTF-8");
            matchinfo.MyTask m = new matchinfo.MyTask();
            m.execute(p);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://tenncricclub.000webhostapp.com/userjoin.php");
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
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), "Recieved Response: " + s, Toast.LENGTH_LONG).show();
            //passwordcheck = s;
            result = s;

        }
    }
}
