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
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * this class present the watchlist that was made by the user from the {@link Releases}, this is present and the option of deleting it.
 */
public class Watchlist extends AppCompatActivity {

    DBHelper db = new DBHelper(this);
    public static ListView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

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
                        case "Releases":
                            intent = new Intent(getBaseContext(),Releases.class);
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

        final ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.getComics();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String details = name+"\n"+price;
            list.add(details);
            cursor.moveToNext();
        }
        final Watchlist.StableArrayAdapter listAdapter = new Watchlist.StableArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        data = findViewById(R.id.comics);
        data.setAdapter(listAdapter);

        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String comicDetails = adapterView.getItemAtPosition(position).toString();
                String comicName = comicDetails.split("\n")[0];

                AlertDialog.Builder builder = new AlertDialog.Builder(Watchlist.this,R.style.MyAlertDialogStyle);

                builder.setTitle("Remove from watchlist?");

                builder.setMessage(comicName);
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.removeComic(comicDetails.split("\n"));

                        ArrayList<String> updatedList = new ArrayList<String>();
                        Cursor updateCursor = db.getComics();
                        updateCursor.moveToFirst();
                        while(!updateCursor.isAfterLast()){
                            String name = updateCursor.getString(1);
                            String price = updateCursor.getString(2);
                            String details = name+"\n"+price;
                            updatedList.add(details);
                            updateCursor.moveToNext();
                        }
                        final Watchlist.StableArrayAdapter listAdapter = new Watchlist.StableArrayAdapter(Watchlist.this,android.R.layout.simple_list_item_1, updatedList);
                        data = findViewById(R.id.comics);
                        data.setAdapter(listAdapter);
                        data.invalidateViews();
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