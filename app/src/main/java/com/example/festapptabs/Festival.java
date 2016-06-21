package com.example.festapptabs;

import android.database.Cursor;

// class with getters for a Festival
class Festival {

    public static int getId(Cursor c) {
        return (c.getInt(0));
    }

    public static String getName(Cursor c) {
        return (c.getString(1));
    }

    public static String getPlace(Cursor c) {
        return (c.getString(2));
    }

    public static String getDate(Cursor c) {
        String dateNum = c.getString(3);
        String day = dateNum.substring(6);
        String month = dateNum.substring(4, 6);
        String year = dateNum.substring(0, 4);
        return (day + "." + month + "." + year);
    }

    public static String getDescr(Cursor c) {
        return (c.getString(4));
    }

    public static String getLogo(Cursor c) {
        return (c.getString(5));
    }

    public static String getGenre(Cursor c) {
        return (c.getString(6).toUpperCase());
    }

    public static int getPrice(Cursor c) {
        return (c.getInt(7));
    }

    public static int getDays(Cursor c) {
        return (c.getInt(8));
    }

    public static String getWebsite(Cursor c) {
        return (c.getString(9));
    }

    public static String getTicketImg(Cursor c) {
        return (c.getString(10));
    }

    public static int getSavedId(Cursor c) {
        return (c.getInt(11));
    }

}
