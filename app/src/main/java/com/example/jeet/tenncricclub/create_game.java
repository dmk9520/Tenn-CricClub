package com.example.jeet.tenncricclub;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class create_game extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout gname, venue, dateinput, timeinput, phoneinput, teamtwoinput, teamoneinput;
    EditText input_gname, input_venue, input_date, input_time, input_phone, teamtwo_input, teamone_input;
    Button btn_create;
    LinearLayout mainlinear;
    int year;
    int month;
    int day;
    int hour;
    int min;
    SharedPreferences shared;
    String username, datagname, datavenue, datadate, datatime, dataphone, datateamone, datateamtwo;
    ProgressDialog progress;
    ;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        findview();

        shared = getApplicationContext().getSharedPreferences("loginprefrence", 0);
        username = shared.getString("user", null);
        dateinput.setOnClickListener(create_game.this);
        input_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    final Calendar calendar = Calendar.getInstance();
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    month = calendar.get(Calendar.MONTH);
                    year = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(create_game.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                            i1 = i1 + 1;
                            input_date.setText(i2 + "/" + i1 + "/" + i);
                        }
                    }, year, month, day);
                    datePickerDialog.setTitle("Select Date");
                    datePickerDialog.show();

                }
                input_date.clearFocus();
            }
        });

        timeinput.setOnClickListener(create_game.this);
        input_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    final Calendar calendar = Calendar.getInstance();
                    hour = calendar.get(Calendar.HOUR);
                    min = calendar.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(create_game.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            input_time.setText(hourOfDay + " : " + minute);
                        }

                    }, hour, min, false);
                    timePickerDialog.setTitle("Select Time");
                    timePickerDialog.show();

                }
                //input_time.clearFocus();
            }
        });
    }

    public void findview() {
        input_gname = findViewById(R.id.input_gname);
        input_date = findViewById(R.id.input_date);
        input_time = findViewById(R.id.input_time);
        input_phone = findViewById(R.id.input_phone);
        input_venue = findViewById(R.id.input_venue);
        teamone_input = findViewById(R.id.teamone_input);
        teamtwo_input = findViewById(R.id.teamtwo_input);

        gname = findViewById(R.id.gname);
        dateinput = findViewById(R.id.dateinput);
        timeinput = findViewById(R.id.timeinput);
        phoneinput = findViewById(R.id.phoneinput);
        venue = findViewById(R.id.venue);
        teamtwoinput = findViewById(R.id.teamtwoinput);
        teamoneinput = findViewById(R.id.teamoneinput);

        btn_create = findViewById(R.id.btn_create);
        btn_create.setOnClickListener(create_game.this);
        mainlinear = findViewById(R.id.mainlinear);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_create) {

            boolean isvalid = true;
            if (input_gname.getText().toString().isEmpty()) {
                gname.setError("Game Name Necessary");
                isvalid = false;
            } else {
                gname.setErrorEnabled(false);
                datagname = input_gname.getText().toString();
            }

            if (input_venue.getText().toString().isEmpty()) {
                venue.setError("Venue Required");
                isvalid = false;
            } else {
                venue.setErrorEnabled(false);
                datavenue = input_venue.getText().toString();
            }
            if (input_date.getText().toString().isEmpty()) {
                dateinput.setError("Date is Necessary");
                isvalid = false;
            } else {
                dateinput.setErrorEnabled(false);
                datadate = input_date.getText().toString();
            }
            if (input_phone.getText().toString().isEmpty()) {
                phoneinput.setError("Phone Number Necessary");
                isvalid = false;
            } else {
                phoneinput.setErrorEnabled(false);
                dataphone = input_phone.getText().toString();
            }
            if (input_time.getText().toString().isEmpty()) {
                timeinput.setError("Select Time");
                isvalid = false;
            } else {
                timeinput.setErrorEnabled(false);
                datatime = input_time.getText().toString();
            }
            if (teamone_input.getText().toString().isEmpty()) {
                teamoneinput.setError("Team name required");
                isvalid = false;
            } else {
                teamoneinput.setErrorEnabled(false);
                datateamone = teamone_input.getText().toString();
            }
            if (teamtwo_input.getText().toString().isEmpty()) {
                teamtwoinput.setError("Team name required");
                isvalid = false;
            } else {
                teamtwoinput.setErrorEnabled(false);
                datateamtwo = teamtwo_input.getText().toString();
            }


            if (isvalid) {

                progress = ProgressDialog.show(this, "Creating Match...",
                        "Please Wait...", false, false);

                Intent intentser = new Intent(create_game.this.getApplicationContext(), game_data_service.class);
                intentser.putExtra("datagname", datagname);
                intentser.putExtra("datavenue", datavenue);
                intentser.putExtra("datadate", datadate);
                intentser.putExtra("datatime", datatime);
                intentser.putExtra("dataphone", dataphone);
                intentser.putExtra("datateamone", datateamone);
                intentser.putExtra("datateamtwo", datateamtwo);
                Log.e("phooooon no.  : ", "" + datateamone);
                Log.e("phooooon no.  : ", "" + datateamtwo);
                startService(intentser);

                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        SharedPreferences sp = getSharedPreferences("gameresponse", MODE_PRIVATE);
                        response = sp.getString("response", null);
                        progress.dismiss();
                        startActivity(new Intent(create_game.this, Home.class));

                    }
                }, 5000);


                /*if(response.equals("Game Created"))
                {
                    Toast.makeText(create_game.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(create_game.this,Home.class));
                }else if(response.equals("Gamename already taken"))
                {
                    Toast.makeText(create_game.this, response, Toast.LENGTH_SHORT).show();
                }*/
            }
        }
    }

}
