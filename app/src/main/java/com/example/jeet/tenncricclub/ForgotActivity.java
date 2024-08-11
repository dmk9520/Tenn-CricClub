package com.example.jeet.tenncricclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {

    TextView rtnlogin;
    Button submit;
    TextInputLayout inputuser;
    EditText user;
    String i;
    String type = null;
    RadioButton userforgot, mobforgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);


        findview();
        clicklistener();

        userforgot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        mobforgot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.submit) {


            boolean isvalid = true;
            if (user.getText().toString().isEmpty()) {
                inputuser.setError("Username or Email Necessary");
                isvalid = false;

            } else {
                inputuser.setErrorEnabled(false);
                //type = user.getText().toString();
                i = user.getText().toString();
                Intent intent = new Intent(ForgotActivity.this, Verifyotp.class);
                intent.putExtra("mob", i);
                /* if(mobforgot.isChecked())
                {
                    Log.e("uname",""+i);
                    intent.putExtra("mob",i);

                }else if(userforgot.isChecked())
                {
                    Log.e("uname",""+type);
                    intent.putExtra("user",type);
                }*/
                startActivity(intent);
            }

        }

    }

    public void clicklistener() {
        rtnlogin.setOnClickListener(ForgotActivity.this);
        submit.setOnClickListener(ForgotActivity.this);
    }

    public void findview() {
        inputuser = findViewById(R.id.inputuser);
        user = findViewById(R.id.user);
        rtnlogin = findViewById(R.id.rtnlogin);
        submit = findViewById(R.id.submit);

        mobforgot = findViewById(R.id.mobforgot);
        userforgot = findViewById(R.id.userforgot);

    }


}

