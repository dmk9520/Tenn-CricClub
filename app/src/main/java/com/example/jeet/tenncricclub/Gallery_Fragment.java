package com.example.jeet.tenncricclub;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Dhruvit Katrodiya on 17-02-2018.
 */

public class Gallery_Fragment extends Fragment {


    static Context c;
    private static ListView rnListView;
    private static ImageListAdapter mImageListAdapter;
    FloatingActionButton uploading;
    ImageView image;
    Button choose, upload;
    int PICK_IMAGE_REQUEST = 111;
    String URL = "https://tenncricclub.000webhostapp.com/gallery/";
    ArrayList<Uri> imagesUriList;
    ArrayList<String> encodedImageList;
    String imageURI;
    int nameno = 0;
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
    String ServerUploadPath = "https://tenncricclub.000webhostapp.com/UPLOADGALLERY.php";
    private List<String> mList;
    private ProgressDialog dialog = null;
    private JSONObject jsonObject;
    private RequestHandler requestHandler;
    int i=0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        this.c = getActivity();
      //  getActivity().startService(new Intent(getActivity(), galleryfetch_service.class));

        rnListView = (ListView) view.findViewById(R.id.rnListView);

        mList = new ArrayList<>();
        mList.add("https://tenncricclub.000webhostapp.com/gallery/tccimg0.png");
        mList.add("https://tenncricclub.000webhostapp.com/gallery/tccimg1.png");
        mList.add("https://tenncricclub.000webhostapp.com/gallery/tccimg2.png");
        mList.add("https://tenncricclub.000webhostapp.com/gallery/tccimg3.png");
        mList.add("https://tenncricclub.000webhostapp.com/gallery/tccimg4.png");
        mList.add("https://tenncricclub.000webhostapp.com/gallery/tccimg5.png");
        mList.add("https://tenncricclub.000webhostapp.com/gallery/tccimg6.png");
        mImageListAdapter = new ImageListAdapter(c, mList);
        rnListView.setAdapter(mImageListAdapter);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Uploading Image...");
        dialog.setCancelable(false);

        jsonObject = new JSONObject();
        encodedImageList = new ArrayList<>();

        uploading = view.findViewById(R.id.uploadimg);
        uploading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

                //   progressDialog = ProgressDialog.show(getActivity(),"Image is Uploading","Please Wait",false,false);

            }
        });

        return view;
    }

    /*img upload onactivity*/

    public static void setImage(List<String> mList) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        View view = getView();


    }


    public void uploadimgdb() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {


                //     Log.e("username", "" + username);

                ImageUploadToServerFunction();

            }
        }, 5000);

    }

    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {


                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                uploadimgdb();

//                 progressDialog.dismiss();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public void ImageUploadToServerFunction() {

        ByteArrayOutputStream byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.WEBP, 0, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
               //// progressDialog = ProgressDialog.show(getActivity(), "Image is Uploading", "Please Wait", false, false);

            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                //\progressDialog.dismiss();
               Toast.makeText(getActivity(), "Please Restart App to see changes..", Toast.LENGTH_LONG).show();

                // Printing uploading success message coming from server on android app.

                //   Toast.makeText(getActivity(), "Please Restart App to see Changes.", Toast.LENGTH_LONG).show();
                // Setting image as transparent after done uploading.
                //imageView.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {

//                Glide.get(getActivity()).clearDiskCache();

                Gallery_Fragment.ImageProcessClass imageProcessClass = new Gallery_Fragment.ImageProcessClass();

                HashMap<String, String> HashMapParams = new HashMap<String, String>();


                HashMapParams.put(ImageName, "img"+i);
                i++;
                HashMapParams.put(ImagePath, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject;
                BufferedReader bufferedReaderObject;
                int RC;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }


}
