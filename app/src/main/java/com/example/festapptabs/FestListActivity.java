package com.example.festapptabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;


public class FestListActivity extends Activity {

    public final static String ID_EXTRA = "com.example.festlistdb._ID";
    public String whereCl = null;
    public String[] myStringArray;
    public int genreClicked;
    public int count = 1;
    String genrePassed;
    ListView genresListView = null;
    ImageView genreLogoImage = null;
    private ListHelper dbListHelper = null;
    private Cursor ourCursor = null;
    private FestivalAdapter adapter = null;
    //intent to festival description
    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent,
                                View view, int position,
                                long id) {
            Intent i = new Intent(view.getContext(), FestDesc.class);
            i.putExtra(ID_EXTRA, String.valueOf(id));
            startActivity(i);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.festlist_fragment);

        genresListView = (ListView) findViewById(R.id.genresListView);
        genreLogoImage = (ImageView) findViewById(R.id.genreLogo);
        //checking which genre was picked
        myStringArray = getIntent().getStringArrayExtra(GenresTabFragment.ID_EXTRA);
        genrePassed = myStringArray[0];
        switch (genrePassed) {
            case "electro":
                genreLogoImage.setImageResource(R.drawable.technosquare);
                break;
            case "hiphop":
                genreLogoImage.setImageResource(R.drawable.hiphopsquare);
                break;
            case "reggae":
                genreLogoImage.setImageResource(R.drawable.reggaesquare);
                break;
            case "rock":
                genreLogoImage.setImageResource(R.drawable.rocksquare);
                break;
        }
        //opening database
        dbListHelper = new ListHelper(FestListActivity.this);
        try {
            dbListHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String whereCl = "genre = ?";
        ourCursor = dbListHelper.getGenreCursor(whereCl, myStringArray);
        startManagingCursor(ourCursor);
        adapter = new FestivalAdapter(ourCursor);
        genresListView.setAdapter(adapter);
        genresListView.setOnItemClickListener(onListClick);
    }

    // content for CardViews
    static class FestivalHolder {
        String euro = "\u20ac";
        String logo;
        private TextView fname = null;
        private TextView fplace = null;
        private TextView fdate = null;
        private TextView fdays = null;
        private TextView fprice = null;
        private ImageView flogo;

        FestivalHolder(View row) {
            fname = (TextView) row.findViewById(R.id.fname);
            fplace = (TextView) row.findViewById(R.id.fplace);
            fdate = (TextView) row.findViewById(R.id.fdate);
            fdays = (TextView) row.findViewById(R.id.fdays);
            fprice = (TextView) row.findViewById(R.id.fprice);

        }

        void populateFrom(Cursor c, ListHelper r) {
            fname.setText(r.getName(c));
            fplace.setText(r.getPlace(c));
            fdate.setText(r.getDate(c));
            fprice.setText(euro + " " + Integer.toString(r.getPrice(c)));
            fdays.setText(Integer.toString(r.getDays(c)) + " days");

        }
    }

    class FestivalAdapter extends CursorAdapter {
        FestivalAdapter(Cursor c) {
            super(FestListActivity.this, c);
        }

        @Override
        public View newView(Context ctxt, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = FestListActivity.this.getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            FestivalHolder holder = new FestivalHolder(row);
            row.setTag(holder);
            return (row);
        }

        @Override
        public void bindView(View row, Context ctxt, Cursor c) {
            FestivalHolder holder = (FestivalHolder) row.getTag();
            holder.populateFrom(c, dbListHelper);
        }
    }
}
