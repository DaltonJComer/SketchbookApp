package com.example.sketchbookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RequestForm extends AppCompatActivity {

    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("ENTERED REQUEST");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);

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
                        default:
                            Toast.makeText(getBaseContext(),"ERROR",Toast.LENGTH_SHORT);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        LinearLayout layout = findViewById(R.id.comicsScroll);

/*        Spinner comics = findViewById(R.id.comicSpinner);
        ArrayAdapter<String> spinAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Releases.listItems);
        comics.setAdapter(spinAdapt);*/

        LinearLayout rg = new LinearLayout(this);
        RadioButton rb;
        int id = 0;

        Cursor cursor = db.getComics();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            rb = new RadioButton(this);
            rb.setText(cursor.getString(1)+" ("+cursor.getString(2)+")");
            rb.setId(id);
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton currentButton = view.findViewById(view.getId());
                }
            });
            id++;
            rg.addView(rb);
            cursor.moveToNext();
        }

/*        for (String s:Releases.listItems) {
            rb = new RadioButton(this);
            rb.setText(s);
            rb.setId(id);
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton currentButton = view.findViewById(view.getId());
                }
            });
            id++;
            rg.addView(rb);
        }*/

        final int numRadButtons = id;

        rg.setOrientation(RadioGroup.VERTICAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.WRAP_CONTENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
        //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.leftMargin =150;
        params.topMargin = 100;


        rg.setLayoutParams(params);
        rg.setTag("ComicList");

        layout.addView(rg);

        Button clearRequests = findViewById(R.id.clearRequests);
        clearRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<numRadButtons;i++){
                    RadioButton currButton = findViewById(i);
                    if(currButton.isChecked()){
                        currButton.setChecked(false);
                    }
                }
            }
        });

        Button sendRequest = findViewById(R.id.sendRequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String requests = "";
                for(int i=0;i<numRadButtons;i++){
                    RadioButton currButton = findViewById(i);
                    if(currButton.isChecked()){
                        requests = requests+currButton.getText()+"\n";
                    }
                }
                EditText insert = findViewById(R.id.enterName);
                String name = insert.getText().toString();
                insert = findViewById(R.id.enterEmail);
                String email = insert.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(RequestForm.this,R.style.MyAlertDialogStyle);

                builder.setTitle("Send Request?");

                builder.setMessage("Name: "+name+"\n\nEmail: "+email+"\n\nRequest:\n"+requests);
                builder.setPositiveButton("Send Request", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
}