package com.example.sketchbookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * this class handles displaying the information that was got from {@link fetchData}, here the user is offered the options to pick the comics, the user wants to add to their
 * list.
 */
public class Releases extends AppCompatActivity {
    public static ListView data;
    public static String[] listItems;
    private DBHelper dbhelper = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releases);

        //initializes the view
        data = (ListView) findViewById(R.id.data);

        fetchData process = new fetchData();
        //retrieves data from API call
        try {
            Void result = process.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //adding elements into a list
        listItems =process.arrayList;
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < listItems.length; ++i) {
            list.add(listItems[i]);
        }
        //array adapter to display releases
        final StableArrayAdapter listAdapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        data.setAdapter(listAdapter); //sets adapter for listview with the releases data
        //building the spinner menu for switching views
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
                        case "Events":
                            intent = new Intent(getBaseContext(),Events.class);
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
        //onclick for user selecting comic on list. Prompts user to add it to watchlist
        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String comicDetails = adapterView.getItemAtPosition(position).toString();
                String comicName = comicDetails.split("\n")[0];
                System.out.println("SELECTED:"+comicName);
                AlertDialog.Builder builder = new AlertDialog.Builder(Releases.this,R.style.MyAlertDialogStyle);

                builder.setTitle("Add to watchlist?");

                builder.setMessage("Add "+comicName+" to wish list?");
                builder.setPositiveButton("Add to watchlist", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbhelper.addData(comicDetails.split("\n"));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    //takes in the input data and creates an adapter to be added to a list
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}