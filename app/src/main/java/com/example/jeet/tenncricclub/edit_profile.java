package com.example.jeet.tenncricclub;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;

public class edit_profile extends AppCompatActivity implements View.OnClickListener {
    EditText input_firstname, input_lastname, input_address,
            input_phone, input_dob, input_username, input_password, input_confpassword, input_existpassword;
    TextInputLayout firstnameinput, lastnameinput, addressinput,
            phoneinput, dobinput, userinput, passinput, confpassinput, existpass;
    Button btn_update;
    String fname, lname, mobile, username, address, dob, String_input_password, String_input_existpassword, md5exist;
    String result;
    DatePicker datepick;
    int year;
    int month;
    int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        input_firstname = findViewById(R.id.input_firstname);
        input_lastname = findViewById(R.id.input_lastname);
        input_address = findViewById(R.id.input_address);
        input_phone = findViewById(R.id.input_phone);
        input_dob = findViewById(R.id.input_dob);
        input_existpassword = findViewById(R.id.input_existpassword);
        input_password = findViewById(R.id.input_password);
        input_confpassword = findViewById(R.id.input_confpassword);


        firstnameinput = findViewById(R.id.firstnameinput);
        lastnameinput = findViewById(R.id.lastnameinput);
        addressinput = findViewById(R.id.addressinput);
        phoneinput = findViewById(R.id.phoneinput);
        dobinput = findViewById(R.id.dobinput);
        existpass = findViewById(R.id.existpass);
        passinput = findViewById(R.id.passinput);
        confpassinput = findViewById(R.id.confpassinput);

        //fname,lname,mobile,username,address,dob,String_input_password,String_input_existpassword

        fname = getIntent().getStringExtra("fname");
        lname = getIntent().getStringExtra("lname");
        mobile = getIntent().getStringExtra("phone");
        address = getIntent().getStringExtra("address");
        username = getIntent().getStringExtra("username");
        dob = getIntent().getStringExtra("dob");


        input_firstname.setText(fname);
        input_lastname.setText(lname);
        input_phone.setText(mobile);
        input_address.setText(address);
        input_dob.setText(dob);

        btn_update = findViewById(R.id.btn_update);
        dobinput.setOnClickListener(edit_profile.this);
        input_dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    final Calendar calendar = Calendar.getInstance();
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    month = calendar.get(Calendar.MONTH);
                    year = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(edit_profile.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                            i1 = i1 + 1;
                            input_dob.setText(i2 + "/" + i1 + "/" + i);
                        }
                    }, year, month, day);
                    datePickerDialog.setTitle("Select Birth Date");
                    datePickerDialog.show();

                }
                input_dob.clearFocus();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isvalid = true;
                if (input_password.getText().toString().isEmpty()) {
                    passinput.setError("Password Necessary");
                    isvalid = false;
                } else {
                    passinput.setErrorEnabled(false);
                }
                if (input_existpassword.getText().toString().isEmpty()) {
                    existpass.setError("Input your existing password");
                    isvalid = false;
                } else {
                    existpass.setErrorEnabled(false);
                    md5exist = input_existpassword.getText().toString();

                    try {
                        LoginActivity l1 = new LoginActivity();
                        md5exist = l1.convertToMd5(md5exist);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                String p = input_password.getText().toString();
                String cp = input_confpassword.getText().toString();

                if (p.equals(cp)) {

                    confpassinput.setErrorEnabled(false);
                    String_input_password = cp;
                    try {
                        LoginActivity l = new LoginActivity();
                        String_input_password = l.convertToMd5(String_input_password);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    confpassinput.setError("Input Same Password");
                    isvalid = false;
                }


                if (isvalid) {
                    insert();
                    startActivity(new Intent(edit_profile.this, LoginActivity.class));
                } else {
                    Toast.makeText(edit_profile.this, "" + result, Toast.LENGTH_SHORT).show();
                }
            }

            /* input_firstname, input_lastname, input_address,
             input_phone, input_dob, input_username, input_password, input_confpassword,input_existpassword;*/
            public void insert() {
                JSONObject j = new JSONObject();
                try {
                    j.put("fname", input_firstname.getText().toString());
                    j.put("lname", input_lastname.getText().toString());
                    j.put("address", input_address.getText().toString());
                    j.put("uname", username);
                    j.put("dob", input_dob.getText().toString());
                    j.put("phone", input_phone.getText().toString());
                    j.put("existpassword", md5exist);
                    j.put("password", String_input_password);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, j.toString(), Toast.LENGTH_LONG).show();
                String jOutput = j.toString();
                try {
                    String p = URLEncoder.encode("jdata", "UTF-8") + "=" + URLEncoder.encode(jOutput, "UTF-8");
                    edit_profile.MyTask m = new MyTask();
                    m.execute(p);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    @Override
    public void onClick(View v) {

    }

    class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://tenncricclub.000webhostapp.com/UPDATEPROF.php");
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
            result = s;

        }
    }
}