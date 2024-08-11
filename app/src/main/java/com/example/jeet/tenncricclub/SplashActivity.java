package com.example.jeet.tenncricclub;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;


public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    public static final int RequestPermissionCode = 7;
    String id, passw;
    SharedPreferences shared;
    Animation anim;
    String per[] = {
            Manifest.permission.INTERNET,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE};

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        // set animation listener
        anim.setAnimationListener(SplashActivity.this);
        requestPermission();


    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // check for fade in animation
        anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);

        // set animation listener
        anim.setAnimationListener(this);


    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(SplashActivity.this, per, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean InternetPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadsmsPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean RecievesmsPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean SendsmsPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadStoragePermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean WriteStoragePermission = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhonePermission = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessNetworkPermission = grantResults[7] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessWifiPermission = grantResults[8] == PackageManager.PERMISSION_GRANTED;

                    if (InternetPermission && ReadsmsPermission && RecievesmsPermission && SendsmsPermission && ReadStoragePermission && WriteStoragePermission && ReadPhonePermission && AccessNetworkPermission && AccessWifiPermission) {

                        shared = getSharedPreferences("loginprefrence", 0);
                        id = shared.getString("user", null);
                        passw = shared.getString("pass", null);


                        boolean bkpatel = checknetwork();
                        if (bkpatel) {


                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {



                                @Override
                                public void run() {

                                    if (id == null && passw == null) {
                                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                    } else {
                                        startActivity(new Intent(SplashActivity.this, Home.class));
                                    }
                                }
                            }, 2000);

                        } else {
                            Snackbar.make(findViewById(R.id.splashll), "Please Check your Internet Connection", Snackbar.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(SplashActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        showSettingsDialog();
                    }
                }

                break;
        }
    }

    private void showSettingsDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private boolean checknetwork() {
        boolean haveWifi = false;
        boolean haveData = false;
        boolean returndata;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] nf = cm.getAllNetworkInfo();
        for (NetworkInfo networkInfo : nf) {
            if (networkInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                if (networkInfo.isConnected()) {
                    haveWifi = true;
                }

            }
            if (networkInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (networkInfo.isConnected()) {
                    haveData = true;
                }
            }
        }
        if (haveData == true || haveWifi == true) {
            returndata = true;
        } else {
            returndata = false;
        }
        return returndata;
    }

}