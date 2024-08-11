package com.example.jeet.tenncricclub;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    static String enc;
    static String convpass;
    EditText input_firstname, input_lastname, input_address,
            input_phone, input_dob, input_username, input_password, input_confpassword;
    String String_input_firstname, String_input_lastname, String_input_address,
            String_input_phone, String_input_dob, String_input_username, String_input_password, String_input_type;
    TextInputLayout firstnameinput, lastnameinput, addressinput,
            phoneinput, dobinput, userinput, passinput, confpassinput;
    Button btn_signup;
    long noOfRowsAffected = 0;
    DatePicker datepick;
    int year;
    int month;
    int day;
    RadioButton radioplayer, radioorganizer;

    LinearLayout mainlinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findview();
        clicklistener();

    }

    public void findview() {
        input_firstname = findViewById(R.id.input_firstname);
        input_lastname = findViewById(R.id.input_lastname);
        input_address = findViewById(R.id.input_address);
        input_phone = findViewById(R.id.input_phone);
        input_dob = findViewById(R.id.input_dob);
        input_username = findViewById(R.id.input_username);
        input_password = findViewById(R.id.input_password);
        input_confpassword = findViewById(R.id.input_confpassword);


        firstnameinput = findViewById(R.id.firstnameinput);
        lastnameinput = findViewById(R.id.lastnameinput);
        addressinput = findViewById(R.id.addressinput);
        phoneinput = findViewById(R.id.phoneinput);
        dobinput = findViewById(R.id.dobinput);
        userinput = findViewById(R.id.userinput);
        passinput = findViewById(R.id.passinput);
        confpassinput = findViewById(R.id.confpassinput);

        btn_signup = findViewById(R.id.btn_signup);

        radioplayer = findViewById(R.id.radioplayer);
        radioorganizer = findViewById(R.id.radioorganizer);

        mainlinear = findViewById(R.id.mainlinear);

    }

    public void clicklistener() {
        btn_signup.setOnClickListener(SignupActivity.this);
        dobinput.setOnClickListener(SignupActivity.this);
        input_dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    final Calendar calendar = Calendar.getInstance();
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    month = calendar.get(Calendar.MONTH);
                    year = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
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


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_signup) {
            boolean isvalid = true;
            if (input_firstname.getText().toString().isEmpty()) {
                firstnameinput.setError("First Name Necessary");
                isvalid = false;
            } else {
                firstnameinput.setErrorEnabled(false);
                String_input_firstname = input_firstname.getText().toString();
            }

            if (input_lastname.getText().toString().isEmpty()) {
                lastnameinput.setError("Last Name Necessary");
                isvalid = false;
            } else {
                lastnameinput.setErrorEnabled(false);
                String_input_lastname = input_lastname.getText().toString();
            }
            if (input_address.getText().toString().isEmpty()) {
                addressinput.setError("Address Necessary");
                isvalid = false;
            } else {
                addressinput.setErrorEnabled(false);
            }
            if (input_phone.getText().toString().isEmpty()) {
                phoneinput.setError("Phone Number Necessary");
                isvalid = false;
            } else {
                phoneinput.setErrorEnabled(false);
                String_input_phone = input_phone.getText().toString();
            }
            if (input_dob.getText().toString().isEmpty()) {
                dobinput.setError("Select Date of Birth");
                isvalid = false;
            } else {
                dobinput.setErrorEnabled(false);
                String_input_dob = input_dob.getText().toString();
            }
            if (input_username.getText().toString().isEmpty()) {
                userinput.setError("Input Username");
                isvalid = false;
            } else {
                userinput.setErrorEnabled(false);
                String_input_username = input_username.getText().toString();
            }
            if (input_password.getText().toString().isEmpty()) {
                passinput.setError("Password Necessary");
                isvalid = false;
            } else {
                passinput.setErrorEnabled(false);

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

            if (!radioplayer.isChecked() && !radioorganizer.isChecked()) {
                Snackbar.make(mainlinear, "Please Select a Radio Button", Snackbar.LENGTH_LONG).show();
                isvalid = false;
            } else {
                if (radioplayer.isChecked()) {
                    String_input_type = (String) radioplayer.getText();
                } else if (radioorganizer.isChecked()) {
                    String_input_type = (String) radioorganizer.getText();
                }
            }

            if (isvalid) {
/*                String table  = "";
                if(radioplayer.isChecked()) {
                    table = "playerinfo";
                }else if(radioorganizer.isChecked())
                {
                    table = "organizerinfo";
                }
                table = "user1";*/
/*                SQLiteDatabase dal = openOrCreateDatabase("user_database", MODE_PRIVATE, null);

                dal.execSQL("create table if not exists user(_id integer primary key autoincrement,FirstName varchar(255),LastName varchar(255),Address varchar(500)*//*,Email varchar(255)*//*,Username varchar(20),DOB date,Mobile long(13),Password varchar(255))");
                ContentValues cv = new ContentValues();
                //Toast.makeText(SignupActivity.this, ""+table, Toast.LENGTH_SHORT).show();
                cv.put("FirstName", String_input_firstname);
                cv.put("LastName", String_input_lastname);
                cv.put("Address", String_input_address);
                //  cv.put("Email",input_email.getText().toString());
                cv.put("Username", String_input_username);
                cv.put("DOB", String_input_dob);
                cv.put("Mobile", String_input_phone);
                cv.put("Password", convpass);

                Long l = dal.insert("user", null, cv);

                Toast.makeText(SignupActivity.this, "Data Submitted", Toast.LENGTH_SHORT).show();

                Toast.makeText(SignupActivity.this, "MD5 " + l, Toast.LENGTH_LONG).show();
               */
                insert();

                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        }
    }

    /*private static String convertToMd5(final String md5) throws UnsupportedEncodingException {

        StringBuffer sb = null;
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] array = md.digest(md5.getBytes("UTF-8"));
            sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            enc = sb.toString();
            return enc;
        } catch (final java.security.NoSuchAlgorithmException e) {
        }
        return enc;
    }*/


    public void insert() {
        JSONObject j = new JSONObject();
        try {
            j.put("fname", String_input_firstname);
            j.put("lname", String_input_lastname);
            j.put("address", input_address.getText().toString());
            j.put("uname", String_input_username);
            j.put("dob", String_input_dob);
            j.put("phone", String_input_phone);
            j.put("password", String_input_password);
            j.put("type", String_input_type);
            //j.put("p_contact",contact);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, j.toString(), Toast.LENGTH_LONG).show();
        String jOutput = j.toString();
        try {
            String p = URLEncoder.encode("jdata", "UTF-8") + "=" + URLEncoder.encode(jOutput, "UTF-8");
            MyTask m = new MyTask();
            m.execute(p);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://tenncricclub.000webhostapp.com/SIGNUP.php");
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
           TextView tv = findViewById(R.id.tv);
            tv.setText(s);
        }
    }
}


