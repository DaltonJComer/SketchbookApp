package com.example.sketchbookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * this class shows the events by visiting the comic book shop's facebook page and displaying them where you can scroll through if you desire
 */
public class Events extends AppCompatActivity {
    /**
     * this function at the bottom that is where the view is presented
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        //sets up spinner for switching between activities
        Spinner menu = findViewById(R.id.menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.menu_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu.setAdapter(adapter);
        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString()!="Change Menu"){
                    Intent intent;
                    switch (parent.getItemAtPosition(position).toString()){
                        case "Home":
                            intent = new Intent(getBaseContext(),MainActivity.class);
                            startActivity(intent);
                            break;
                        case "My Watchlist":
                            intent = new Intent(getBaseContext(),Watchlist.class);
                            startActivity(intent);
                            break;
                        case "Contact Info":
                            intent = new Intent(getBaseContext(),Contact.class);
                            startActivity(intent);
                            break;
                        case "Releases":
                            intent = new Intent(getBaseContext(),Releases.class);
                            startActivity(intent);
                            break;
                        case "Request Comics":
                            intent = new Intent(getBaseContext(), RequestForm.class);
                            startActivity(intent);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //presenting facebook view
        WebView faceView =(WebView)findViewById(R.id.facebookPage);

        faceView.setWebViewClient(new WebViewClient());
        faceView.getSettings().setJavaScriptEnabled(true);
        faceView.getSettings().setDomStorageEnabled(true);
        faceView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        faceView.loadUrl("https://www.facebook.com/pg/SketchbookComicsandGames/events/?ref=page_internal");

    }
}