package com.example.festapptabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

// Fragment with displayed saved tickets
public class MyTicketsFragment extends Fragment {

    public final static String ID_EXTRA = "com.example.festlistdb._ID";
    private static final ArrayList<Integer> ticketsIds = new ArrayList<>();
    private static final ArrayList<Integer> resourceList = new ArrayList<>();
    private int intKey = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mytickets_frag, container, false);
        final ListView myListView = (ListView) rootView.findViewById(R.id.ticketList);
        myListView.setAdapter(new TicketsAdapter(getContext()));
        loadSharedPreferences();
        return rootView;
    }
    //method to load shared preferences and getting the image resource
    private void loadSharedPreferences(){

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SavedTickets", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry: allEntries.entrySet()) {
            String tickets = entry.getValue().toString();
            String key = entry.getKey();
            intKey = Integer.parseInt(key);
            int imgResource = getResources().getIdentifier(tickets, "drawable", getContext().getPackageName());
            // String List Array
            ticketsIds.add(intKey);
            resourceList.add(imgResource);
        }
    }
    // returning the list of resource ids to display
    public static ArrayList<Integer> getTicketList(){
        return resourceList;
    }
    public static ArrayList<Integer> getIdList(){
        return ticketsIds;
    }
    public int getIntKey(){
        return intKey;
    }
}

