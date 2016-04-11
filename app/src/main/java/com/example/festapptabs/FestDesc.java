package com.example.festapptabs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;

// Activity with description of a particular festival
public class FestDesc extends Activity {
    public final static String ID_TICKET = "com.example.festlistdb._ID";
    public static final String EURO = "\u20AC";
    public ListHelper dbFestListHelper = null;
    String passedVar = null;
    String passedMarkerId = null;
    String idMarker = null;
    String name = null;
    String place = null;
    String date = null;
    String description = null;
    int position = 0;
    String logoImg = null;
    String genre = null;
    int price = 0;
    int daysNum = 0;
    String website;
    String ticketImg;
    int savedOldValue;
    ImageView logo;
    private TextView passedView = null;
    private TextView festDate = null;
    private TextView festPlace = null;
    private TextView festGenere = null;
    private TextView festPrice = null;
    private TextView festDaysNum = null;
    private TextView festDescr = null;
    private Button saveEvent;
    private Button buyTickets;
    private Cursor ourCursor = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fest_desc);

        passedView = (TextView) findViewById(R.id.festDescName); // festival name
        festPlace = (TextView) findViewById(R.id.festPlace);
        festDate = (TextView) findViewById(R.id.festDate);
        festGenere = (TextView) findViewById(R.id.festGenere);
        festPrice = (TextView) findViewById(R.id.festPrice);
        festDaysNum = (TextView) findViewById(R.id.festDaysNum);
        festDescr = (TextView) findViewById(R.id.festDescr);

        saveEvent = (Button) findViewById(R.id.saveEvent);
        buyTickets = (Button) findViewById(R.id.buyTickets);


        logo = (ImageView) findViewById(R.id.logoImg);
        // ListHelper - A SQLiteOpenHelper class to manage database creation and version management.
        dbFestListHelper = new ListHelper(FestDesc.this);
        try {
            dbFestListHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ourCursor = dbFestListHelper.getCursor();


        //get passed var from extras
        passedMarkerId = getIntent().getStringExtra(MapTabFragment.MARKER_ID);
        passedVar = getIntent().getStringExtra(MyTicketsFragment.ID_EXTRA);
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
        ourCursor.moveToPosition(position);
        //getting content from database
        name = dbFestListHelper.getName(ourCursor);
        place = dbFestListHelper.getPlace(ourCursor);
        date = dbFestListHelper.getDate(ourCursor);
        description = dbFestListHelper.getDescr(ourCursor);
        logoImg = dbFestListHelper.getLogo(ourCursor);
        genre = dbFestListHelper.getGenre(ourCursor);
        price = dbFestListHelper.getPrice(ourCursor);
        daysNum = dbFestListHelper.getDays(ourCursor);
        website = dbFestListHelper.getWebsite(ourCursor);
        ticketImg = dbFestListHelper.getTicketImg(ourCursor);


        savedOldValue = dbFestListHelper.getSavedId(ourCursor);


        int resID = getResources().getIdentifier(logoImg,
                "drawable", getPackageName());
        logo.setImageResource(resID);
        //setting content in views
        passedView.setText(name);
        festPlace.setText(place);
        festDate.setText(date);
        festGenere.setText(genre);
        festPrice.setText(EURO + price);
        festDaysNum.setText(daysNum + " days");
        festDescr.setText(description);
        //button listener and intent to go to the website

        buyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("buytickets clicked", website);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                startActivity(i);
            }
        });

        saveEventButton();


    }

    public void saveEventButton() {
        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbFestListHelper = new ListHelper(FestDesc.this);
                try {
                    dbFestListHelper.openDataBase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dbFestListHelper.updateSavedTicket(ourCursor);
            }
        });
    }
}

