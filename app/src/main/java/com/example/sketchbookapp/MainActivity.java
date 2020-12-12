package com.example.sketchbookapp;
/**
 * @author Dalton Comer| Brendan Park| Joel Liju Jacob
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * this is the main class which presents the user with the instagram page in addition to just the image of the comic book shop.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        case "Releases":
                            intent = new Intent(getBaseContext(),Releases.class);
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
                        case "Events":
                            intent = new Intent(getBaseContext(),Events.class);
                            startActivity(intent);
                            break;
                        case "Request Comics":
                            intent = new Intent(getBaseContext(), RequestForm.class);
                            startActivity(intent);
                        default:
                            Toast.makeText(getBaseContext(),"ERROR",Toast.LENGTH_SHORT);
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


            Uri uri = Uri.parse("http://instagram.com/sketchbookcomicsandgames/");


        });
    }

}