package com.example.jeet.tenncricclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ResetActivity extends AppCompatActivity implements View.OnClickListener {

    EditText newpass, confnewpass;
    TextInputLayout newpassinput, confnewpassinput;
    Button submit;
    boolean isvalid = true;
    boolean check;
    String pass, number, p, cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        number = getIntent().getStringExtra("number");
        Log.e("Number : ", "" + number);


        findview();
        clicklistener();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.submit) {
            if (newpass.getText().toString().isEmpty()) {
                newpassinput.setError("Field can't be left empty");
                isvalid = false;
            } else {
                newpassinput.setErrorEnabled(false);
            }

            p = newpass.getText().toString();
            cp = confnewpass.getText().toString();
            if (cp.equals(p)) {

                confnewpassinput.setErrorEnabled(false);
                try {
                    LoginActivity l = new LoginActivity();
                    cp = l.convertToMd5(cp);
                    insert();
                   startActivity(new Intent(ResetActivity.this, LoginActivity.class));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                confnewpassinput.setError("Input Same Password");
                isvalid = false;

            }


        }
    }

    public void insert() {
        JSONObject j = new JSONObject();
        try {
            j.put("phone", number);
            j.put("newpass", cp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jOutput = j.toString();
        try {
            String p = URLEncoder.encode("jdata", "UTF-8") + "=" + URLEncoder.encode(jOutput, "UTF-8");
            ResetActivity.MyTask m = new ResetActivity.MyTask();
            m.execute(p);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void findview() {
        newpass = findViewById(R.id.newpass);
        confnewpass = findViewById(R.id.confnewpass);
        submit = findViewById(R.id.submit);
        newpassinput = findViewById(R.id.newpassinput);
        confnewpassinput = findViewById(R.id.confnewpassinput);
    }

    public void clicklistener() {

        newpass.setOnClickListener(ResetActivity.this);
        confnewpass.setOnClickListener(ResetActivity.this);
        submit.setOnClickListener(ResetActivity.this);

    }

    class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://tenncricclub.000webhostapp.com/RESETPASS.php");
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
            Toast.makeText(ResetActivity.this, "Password Updated" + s, Toast.LENGTH_LONG).show();
            check = Boolean.parseBoolean(s);
        }
    }
}
