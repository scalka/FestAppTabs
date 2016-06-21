package com.example.festapptabs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
// Adapter for ticket images
class TicketsAdapter extends BaseAdapter {
    private final Context mContext;
    // references to images
    private final ArrayList<Integer> ticketList = MyTicketsFragment.getTicketList();
    ArrayList<Integer> ticketIds = MyTicketsFragment.getIdList();
    private final ArrayList<Integer> mTicketsIds = ticketList;
    public TicketsAdapter(Context c) {
        mContext = c;
    }
    public int getCount() {
        //    return mThumbIds.length;
        return mTicketsIds.size();
    }
    public Object getItem(int position) {
        return null;
    }
    public long getItemId(int position) {
        return 0;
    }
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.WRAP_CONTENT, ListView.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(10, 10, 10, 10);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mTicketsIds.get(position));
        return imageView;
    }



}