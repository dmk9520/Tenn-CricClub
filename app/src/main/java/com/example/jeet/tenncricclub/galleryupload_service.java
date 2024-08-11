package com.example.jeet.tenncricclub;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_OK;

public class galleryupload_service extends Service{

    InputStream is;

    URL url;

    String imgpath;

    /*Image Upload Coding*/
    Bitmap bitmap;

    boolean check = true;

    FloatingActionButton SelectImageGallery;


    EditText imageName;

    ProgressDialog progressDialog;

    String username;

    String ImageName = "image_name";

    String ImagePath = "image_path";

    String ServerUploadPath = "https://tenncricclub.000webhostapp.com/IMAGEGALLERY.php";
    String id;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service ","Started");


        return super.onStartCommand(intent, flags, startId);
    }


  }
