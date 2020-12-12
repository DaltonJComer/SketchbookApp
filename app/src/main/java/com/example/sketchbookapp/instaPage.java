package com.example.sketchbookapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link instaPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class instaPage extends Fragment {



    public instaPage() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_insta_page,container,false);
        WebView wv = (WebView)v.findViewById(R.id.web);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());//important to open url in your app
        wv.loadUrl("http://instagram.com/sketchbookcomicsandgames/");
        return v;
    }
}