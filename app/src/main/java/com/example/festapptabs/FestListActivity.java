package com.example.festapptabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
// Activity with a list of festivals
public class FestListActivity extends Activity {

    private final static String ID_EXTRA = "com.example.festlistdb._ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.festlist_activity);
        // views for list and logo
        ListView genresListView = (ListView) findViewById(R.id.genresListView);
        ImageView genreLogoImage = (ImageView) findViewById(R.id.genreLogo);
        //checking which genre was picked
        String[] myStringArray = getIntent().getStringArrayExtra(GenresTabFragment.ID_EXTRA);
        String genrePassed = myStringArray[0];
        switch (genrePassed) {
            case "electro":
                genreLogoImage.setImageResource(R.drawable.technobanner);
                break;
            case "hiphop":
                genreLogoImage.setImageResource(R.drawable.hiphopbanner);
                break;
            case "reggae":
                genreLogoImage.setImageResource(R.drawable.reggaebanner);
                break;
            case "rock":
                genreLogoImage.setImageResource(R.drawable.rockbanner);
                break;
        }
        //opening database
        ListHelper dbListHelper = new ListHelper(FestListActivity.this);
        dbListHelper.openDataBase();
        String whereCl = "genre = ?";
        Cursor cursor = dbListHelper.getGenreCursor(whereCl, myStringArray);
        startManagingCursor(cursor);
        FestivalAdapter adapter = new FestivalAdapter(cursor);
        genresListView.setAdapter(adapter);
        genresListView.setOnItemClickListener(onListClick);
    }


    // content for CardViews
    static class FestivalHolder {
        final String euro = "\u20ac";
        private TextView fname = null;
        private TextView fplace = null;
        private TextView fdate = null;
        private TextView fdays = null;
        private TextView fprice = null;

        FestivalHolder(View row) {
            fname = (TextView) row.findViewById(R.id.fname);
            fplace = (TextView) row.findViewById(R.id.fplace);
            fdate = (TextView) row.findViewById(R.id.fdate);
            fdays = (TextView) row.findViewById(R.id.fdays);
            fprice = (TextView) row.findViewById(R.id.fprice);

        }
        void populateFrom(Cursor c) {
            fname.setText(Festival.getName(c));
            fplace.setText(Festival.getPlace(c));
            fdate.setText(Festival.getDate(c));
            String festivalPrice = euro + " " + Integer.toString(Festival.getPrice(c));
            fprice.setText(festivalPrice);
            int numDays = Festival.getDays(c);
            String festivalDays = Integer.toString(numDays) + FestDesc.getDaysCorrectly(numDays);
            fdays.setText(festivalDays);
        }
    }
    //cursor adapter
    class FestivalAdapter extends CursorAdapter {
        FestivalAdapter(Cursor c) {
            super(FestListActivity.this, c);
        }
        @Override
        public View newView(Context ctxt, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = FestListActivity.this.getLayoutInflater();
            View row = inflater.inflate(R.layout.cardview, parent, false);
            FestivalHolder holder = new FestivalHolder(row);
            row.setTag(holder);
            return (row);
        }
        @Override
        public void bindView(View row, Context ctxt, Cursor c) {
            FestivalHolder holder = (FestivalHolder) row.getTag();
            holder.populateFrom(c);
        }
    }
    //intent to festival description
    private final AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent,
                                View view, int position,
                                long id) {
            Intent i = new Intent(view.getContext(), FestDesc.class);
            i.putExtra(ID_EXTRA, String.valueOf(id));
            startActivity(i);
        }
    };
}
