package com.example.festapptabs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class GenresTabFragment extends Fragment {

    public final static String ID_EXTRA = "com.example.festapptabs._ID";
    public String whereCl = null;
    public String[] myStringArray;
    public int genreClicked;
    public int count = 1;
    private ListHelper dbListHelper = null;
    private Cursor ourCursor = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.genres_tab_frag_2, container, false);

        final GridView myGridView = (GridView) rootView.findViewById(R.id.gridview);

        myGridView.setAdapter(new ImageAdapter(getContext()));
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    genreClicked = 0;
                } else if (position == 1) {
                    genreClicked = 1;
                } else if (position == 2) {
                    genreClicked = 2;
                } else if (position == 3) {
                    genreClicked = 3;
                }
                switch (genreClicked) {
                    case 0:
                        myStringArray = new String[]{"electro"};
                        break;
                    case 1:
                        myStringArray = new String[]{"hiphop"};
                        break;
                    case 2:
                        myStringArray = new String[]{"reggae"};
                        break;
                    case 3:
                        myStringArray = new String[]{"rock"};
                        break;
                }
                Intent i = new Intent(getActivity(), FestListActivity.class);
                i.putExtra(ID_EXTRA, myStringArray);
                startActivity(i);
            }


        });
        return rootView;
    }

}