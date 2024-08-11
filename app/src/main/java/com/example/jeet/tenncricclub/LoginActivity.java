package com.example.jeet.tenncricclub;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.DigestException;
import java.security.MessageDigest;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    static String enc;
    static String passcheck;
    Button sigin, sigup;
    EditText user, pass;
    TextInputLayout inputuser, inputpass;
    TextView forgot;
    SharedPreferences shared;
    boolean isvalid = true;
    boolean username, password;
    String passcheck2;
    String Data = "";
    SQLiteDatabase dal = null;
    LinearLayout mainlinear;
    RadioButton radioplayer, radioorganizer;
    String str, type, result;
    ProgressDialog dialog;
    String passwordcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findview();
        clicklistener();
        username = user.getText().toString().isEmpty();
        password = pass.getText().toString().isEmpty();

        shared = getSharedPreferences("loginprefrence", 0);

        shared = getApplicationContext().getSharedPreferences("loginprefrence", 0);
        String id = shared.getString("user", null);
        String passw = shared.getString("pass", null);
        if (id == null && passw == null) {
            username = false;
            password = false;
            //startActivity(new Intent(LoginActivity.this, Home.class));
            //Toast.makeText(LoginActivity.this, "Login or Signup first", Toast.LENGTH_SHORT).show();

        } else {
            username = true;
            password = true;
            startActivity(new Intent(LoginActivity.this, Home.class));

        }


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.forgot) {
            //do coding for login here
            startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
        } else if (v.getId() == R.id.sigup) {
            //do coding for login here
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        } else if (v.getId() == R.id.sigin) {

            dialog = ProgressDialog.show(LoginActivity.this, "Authenticating User...",
                    "Please Wait...", false, true);
            try {
                str = user.getText().toString();
                String str2 = pass.getText().toString();

                //MD5 code
                try {
                    passcheck = convertToMd5(str2);
                    String p = URLEncoder.encode("uname", "UTF-8") + "=" + URLEncoder.encode(str, "UTF-8");
                    MyTask m = new MyTask();
                    m.execute(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }
    }

    public void check() {
    /*dialog = ProgressDialog.show(LoginActivity.this, "Authenticating User...",
            "Please Wait...", false);*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (passcheck.equals(passwordcheck)) {

                    SharedPreferences sp = getSharedPreferences("loginprefrence", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("user", str);
                    editor.putString("pass", passcheck);
                    editor.putString("type", type);
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, Home.class));
                } else {

                    dialog.dismiss();
                    //      Snackbar.make(mainlinear, "Signup or Login properly", Snackbar.LENGTH_LONG).show();
                    inputpass.setError("Username or password incorrect");
                    inputuser.setError(" ");
                    isvalid = false;
                    sigin.clearFocus();
                }

            }
        }, 0);
    }

    public void findview() {
        sigin = findViewById(R.id.sigin);
        sigup = findViewById(R.id.sigup);

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);

        inputuser = findViewById(R.id.inputuser);
        inputpass = findViewById(R.id.inputpass);

        forgot = findViewById(R.id.forgot);

        mainlinear = findViewById(R.id.mainlinear);

        radioplayer = findViewById(R.id.radioplayer);
        radioorganizer = findViewById(R.id.radioorganizer);
    }

    public void clicklistener() {
        forgot.setOnClickListener(LoginActivity.this);
        sigin.setOnClickListener(LoginActivity.this);
        sigup.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to go back?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /*private static String convertToMd5(final String md5) throws UnsupportedEncodingException {

        StringBuffer sb=null;
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

    public String convertToMd5(String text) throws DigestException {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] b = text.getBytes();
            md.update(b);
            byte[] hash = md.digest();
            return Integer.toHexString(Arrays.hashCode(hash));
        } catch (Exception e) {
            return null;
        }
    }

 /*   private boolean checkDataBase() {
        try {
            dal = SQLiteDatabase.openDatabase("user_database", null,
                    SQLiteDatabase.OPEN_READONLY);
            dal.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return dal != null;
    }

*/

    public void jsonget() {
        try {
            JSONObject jObject = new JSONObject(result);
            try {

                // Pulling items from the array
                //First,Second,Username,DOB,Phone,Type,Address

                type = jObject.getString("Type");
                passwordcheck = jObject.getString("Password");


                //String oneObjectsItem2 = oneObject.getString("anotherSTRINGNAMEINtheARRAY");
                // Toast.makeText(getActivity(), "" + First, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                // Oops
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://tenncricclub.000webhostapp.com/LOGIN.php");
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
            jsonget();
            check();
        }
    }
}
