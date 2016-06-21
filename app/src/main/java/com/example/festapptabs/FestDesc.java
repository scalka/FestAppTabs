package com.example.festapptabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// Activity with description of a particular festival with content from the database
public class FestDesc extends Activity {

    private String idMarker;
    private int id = 0;
    private int position = 0;
    private String website;
    private String ticketImg;
    private Button saveEvent;
    private Button buyTickets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fest_desc_activity);
        //text views
        TextView passedView = (TextView) findViewById(R.id.festDescName);
        TextView festPlace = (TextView) findViewById(R.id.festPlace);
        TextView festDate = (TextView) findViewById(R.id.festDate);
        TextView Genre = (TextView) findViewById(R.id.festGenre);
        TextView festPrice = (TextView) findViewById(R.id.festPrice);
        TextView festDaysNum = (TextView) findViewById(R.id.festDaysNum);
        TextView festDescr = (TextView) findViewById(R.id.festDescr);
        saveEvent = (Button) findViewById(R.id.saveEvent); // save event button
        buyTickets = (Button) findViewById(R.id.buyTickets); //but tickets button
        ImageView logo = (ImageView) findViewById(R.id.logoImg);
        // ListHelper - A SQLiteOpenHelper class to manage database creation and version management.
        ListHelper dbFestListHelper = new ListHelper(FestDesc.this);
        // opening database method
        dbFestListHelper.openDataBase();
        Cursor cursor = dbFestListHelper.getCursor();
        //get passed variables from extras
        String passedMarkerId = getIntent().getStringExtra(MapTabFragment.MARKER_ID);
        String passedVar = getIntent().getStringExtra(MyTicketsFragment.ID_EXTRA);
        //picked festival
        try {
            if (passedVar == null) {
                passedMarkerId = passedMarkerId.substring(1);
                idMarker = passedMarkerId;
                position = Integer.parseInt(idMarker);
            } else if (idMarker == null) {
                position = Integer.parseInt(passedVar) - 1;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        // moving to the correct position in a database table
        cursor.moveToPosition(position);
        //getting content from database
        id = Festival.getId(cursor);
        String name = Festival.getName(cursor);
        String place = Festival.getPlace(cursor);
        String date = Festival.getDate(cursor);
        String description = Festival.getDescr(cursor);
        String logoImg = Festival.getLogo(cursor);
        String genre = Festival.getGenre(cursor);
        int price = Festival.getPrice(cursor);
        int daysNum = Festival.getDays(cursor);
        website = Festival.getWebsite(cursor);
        ticketImg = Festival.getTicketImg(cursor);
        //resource Id for a logo
        int resID = getResources().getIdentifier(logoImg,
                "drawable", getPackageName());
        logo.setImageResource(resID);
        //setting content in views
        passedView.setText(name);
        festPlace.setText(place);
        festDate.setText(date);
        Genre.setText(genre);
        String festivalPrice = getString(R.string.euro) + price;
        festPrice.setText(festivalPrice);
        String numberOfDays = daysNum + getDaysCorrectly(daysNum);
        festDaysNum.setText(numberOfDays);
        festDescr.setText(description);
        // closing database
        dbFestListHelper.close();
        cursor.close();
        //button listener and intent to go to the external website
        buyTicketsButton();
        //button listener to save the event in shared preferences
        saveEventButton();
    }
    // Web view method
    private void buyTicketsButton() {
        buyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                startActivity(i);
            }
        });
    }
    // saving button listener
    private void saveEventButton() {
            saveEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveSharedPreferences();
                }
            });
    }
    // saving ticket image name as shared preferences
    private void saveSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("SavedTickets", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String savedId = String.valueOf(id);
        editor.putString(savedId, ticketImg);
        // Problem synopsis: Consider using apply() instead; commit writes its data to persistent storage immediately, whereas apply will handle it in the background (at line 167)
        editor.apply();
        Toast.makeText(this, "Ticket was saved successfully", Toast.LENGTH_LONG).show();
    }
    // check if it is one day or more days and display correct word
    public static String getDaysCorrectly(int daysNum){
        String dayOrDays = null;
        if (daysNum == 1) {
            dayOrDays = " day";
        } else if (daysNum > 1) {
            dayOrDays = " days";
        }
        return dayOrDays;
    }
}

