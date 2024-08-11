package com.example.jeet.tenncricclub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

public class Verifyotp extends AppCompatActivity implements View.OnClickListener {

    static EditText otpinput;
    Button submitotp, otpsms, generate;
    String otp;
    String getvalue = otpreciever.otpmsg;
    TextInputLayout inputotp;
    String no;
    String number;
    String username;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyotp);

        dialog = ProgressDialog.show(Verifyotp.this, "",
                "Authenticating User...", false, true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                number = getIntent().getStringExtra("mob");

                //username = getIntent().getStringExtra("user");

                try {

                 /*   String p = URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                    Log.e("var",p);
                    Verifyotp.MyTask m = new Verifyotp.MyTask();
                    m.execute(p);*/

                    try {
                        String p = URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number.toString(), "UTF-8");
                        Verifyotp.MyTask m = new Verifyotp.MyTask();
                        m.execute(p);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
/*
                    if(number!=null)
                    {
                        try {
                            String p = URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number.toString(), "UTF-8");
                            Verifyotp.MyTask m = new Verifyotp.MyTask();
                            m.execute(p);

                        } catch (UnsupportedEncodingException e ) {
                            e.printStackTrace();
                        }
                    } else if(username != null){
                        try {
                            String p = URLEncoder.encode("uname", "UTF-8") + "=" + URLEncoder.encode(username.toString(), "UTF-8");
                            Verifyotp.MyTask m = new Verifyotp.MyTask();
                            m.execute(p);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5000);

        findview();
        clicklistners();

        otpinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*int aavelo = Integer.parseInt(otpreciever.otpmsg);
                int gayelo = Integer.parseInt(otp);
                if (aavelo == gayelo) {
                    submitotp.setEnabled(true);
                    otpinput.setEnabled(true);
                }*/

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        //Log.e("hello", "i am personal otp" + getvalue);
        if (v.getId() == R.id.submitotp) {
            if (otpinput.getText().toString().length() != 0) {
                //do coding here
                String aavelo = otpreciever.otpmsg;
                String gayelo = otpinput.getText().toString();

                Log.e("aavcelo", aavelo);
                Log.e("gayelo", gayelo);
                if (aavelo.equals(gayelo)) {
                    Intent intent = new Intent(Verifyotp.this, ResetActivity.class);
                    intent.putExtra("number", number);
                    startActivity(intent);
                } else {
                    Toast.makeText(Verifyotp.this, "Invalid OTP...", Toast.LENGTH_SHORT).show();
//                    inputotp.setError("Please Input Correct OTP");
                }
            }
        } else if (v.getId() == R.id.otpsms) {
            try {

                final SecureRandom random = new SecureRandom();
                otp = String.format("%06d", random.nextInt(1000000));
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(no, null, "Your Verification Code " + otp + " This is your private code please do not share this code.", null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //do otpsms coding here
        } else if (v.getId() == R.id.generate) {
            //do otpemail coding here

            final SecureRandom random = new SecureRandom();
            otp = String.format("%06d", random.nextInt(1000000));
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(no, null, "Your Verification Code " + otp + " This is your private code please do not share this code.", null, null);
        }
    }

    public void clicklistners() {
        submitotp.setOnClickListener(Verifyotp.this);
        otpsms.setOnClickListener(Verifyotp.this);
        generate.setOnClickListener(Verifyotp.this);
    }

    public void findview() {
        otpinput = findViewById(R.id.otpinput);

        submitotp = findViewById(R.id.submitotp);
        otpsms = findViewById(R.id.otpsms);
        generate = findViewById(R.id.generate);

    }

    class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://tenncricclub.000webhostapp.com/USERVERIFY.php");
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
            no = s;
            if (no.length() != 0) {
                dialog.dismiss();
            } else {
                inputotp.setError("Invalid Mobile number or Username");
            }
        }
    }
}
