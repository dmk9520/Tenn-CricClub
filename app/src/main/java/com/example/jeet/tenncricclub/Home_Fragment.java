package com.example.jeet.tenncricclub;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by Dhruvit Katrodiya on 17-02-2018.
 */

public class Home_Fragment extends Fragment {
    TextView tv;
    WebView webview;
    String url = "http://www.cricbuzz.com/cricket-news";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        webview = view.findViewById(R.id.webview);

        WebSettings webSettings = webview.getSettings();
        webSettings.setBuiltInZoomControls(false);

        webview.setWebViewClient(new Callback());  //HERE IS THE MAIN CHANGE
        webview.loadUrl(url);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        View view = getView();

        webview = view.findViewById(R.id.webview);


    }

    private class Callback extends WebViewClient {  //HERE IS THE MAIN CHANGE.

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }

    }

}
