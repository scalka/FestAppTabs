package com.example.festapptabs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;


public class MyTicketsFragment extends Fragment {

    public final static String ID_EXTRA = "com.example.festlistdb._ID";
    // object
    private ListHelper dbListHelper = null;
    private Cursor ourCursor = null;
    private FestivalAdapter adapter = null;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.festlist_tab_frag_4, container, false);
        ListView myListView = (ListView) rootView.findViewById(R.id.myListView);

        dbListHelper = new ListHelper(getActivity());
        try {
            dbListHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ourCursor = dbListHelper.getCursor();
        getActivity().startManagingCursor(ourCursor);
        adapter = new FestivalAdapter(ourCursor);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(onListClick);
        return rootView;
    }

    static class FestivalHolder {
        private TextView name = null;
        private ImageView logo;

        FestivalHolder(View row) {
            name = (TextView) row.findViewById(R.id.festivaltext);

        }

        void populateFrom(Cursor c, ListHelper r) {
            name.setText(r.getName(c) + ("\n") + r.getPlace(c) + ("\n") + r.getDate(c) + ("\n") + r.getGenre(c) + ("\n") + "Price:" + r.getPrice(c) + ("\n") + "Number of days:" + r.getDays(c));

        }
    }

    class FestivalAdapter extends CursorAdapter {
        FestivalAdapter(Cursor c) {
            super(getContext(), c);
        }

        @Override
        public View newView(Context ctxt, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
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
