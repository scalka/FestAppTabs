package com.example.festapptabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Locale;

//class to manage database creation and version management
public class ListHelper extends SQLiteOpenHelper {
    //table name
    public static final String TABLE_NAME = "festivallist";
    public static final String COLUMN_ID = "_id";
    //column names
    public static final String COLUMN_TITLE = "festname";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DESCR = "deswcription";
    public static final String COLUMN_LOGO = "logo";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DAYS = "days";
    public static final String COLUMN_WEBSITE = "website";
    public static final String COLUMN_TICKETIMG = "ticketimg";
    public static final String COLUMN_SAVEDID = "savedId";
    // database info
    private static final String DATABASE_PATH = "/data/data/com.example.festapptabs/databases";
    private static final String DATABASE_NAME = "festlist.db";
    private static final int VERSION = 1;
    private final Context myContext;
    public SQLiteDatabase dbSqlite;


    //constractor
    public ListHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.myContext = context;
        Log.d("list helper", "constractor");

    }
    //the table raw SQLite command to create it.
    private static final String FESTIVALLIST_TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_PLACE + " TEXT,"
                    + COLUMN_DATE + " INTEGER,"
                    + COLUMN_DESCR + " TEXT,"
                    + COLUMN_LOGO + " REAL,"
                    + COLUMN_GENRE +" REAL, "
                    + COLUMN_PRICE + " TEXT, "
                    + COLUMN_DAYS + "INTEGER, "
                    + COLUMN_WEBSITE + " TEXT,"
                    + COLUMN_TICKETIMG + " REAL,"
                    + COLUMN_SAVEDID + " INTEGER,"
                    + ");";



    //called when the database is created for the first time
    //if a database already exists on disk with the same DATABASE_NAME, this method will not be called
    @Override
    public void onCreate(SQLiteDatabase db) {
        copyDBFromResource();
        try {
            openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d("list helper", "on Create");
    }

    //called when the database needs to be upgraded
    //This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    //but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
            Log.d("list helper", "on Upgrade");
        }
    }

    public void createDB() {
        boolean dbExist = DBExists();
        if (!dbExist) {
            //calling this method creates an empty database into the default system location
            //it is needed to overwrite the db
            this.getReadableDatabase();
            //copy the database included
            copyDBFromResource();
        }
    }

    //check if the database exist and can be read
    public boolean DBExists() {
        SQLiteDatabase db = null;
        try {
            String databasePath = DATABASE_PATH + DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(databasePath, null,
                    SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setLockingEnabled(true);
            db.setVersion(2);
        } catch (SQLiteException e) {
            Log.e("SqlHelper", "database not found");
        }
        if (db != null) {
            db.close();
        }
        Log.d("list helper", "DBExists");
        return db != null ? true : false;
    }

    private void copyDBFromResource() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String dbFilePath = DATABASE_PATH + DATABASE_NAME;

        try {
            //open local db as the input stream
            inputStream = myContext.getAssets().open(DATABASE_NAME);
            //open the db as the output stream
            outputStream = new FileOutputStream(dbFilePath);
            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int lenght;
            while ((lenght = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, lenght);
            }
            //close the streams
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new Error("problem copying db from resource file");
        }
        Log.d("list helper", "copyDBfromResource");
    }

    // opening databae
    public void openDataBase() throws SQLException {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        dbSqlite = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
        Log.d("list helper", "openDatabase");
    }

    //closing database
    @Override
    public synchronized void close() {
        if (dbSqlite != null) {
            dbSqlite.close();
        }
        super.close();
        Log.d("list helper", "close");
    }

    //define cursor
    public Cursor getCursor() {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        String[] asColumnsToReturn = new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_PLACE, COLUMN_DATE, COLUMN_DESCR, COLUMN_LOGO, COLUMN_GENRE, COLUMN_PRICE, COLUMN_DAYS, COLUMN_WEBSITE, COLUMN_TICKETIMG, COLUMN_SAVEDID};
        Cursor mCursor = queryBuilder.query(dbSqlite, asColumnsToReturn, null,
                null, null, null, COLUMN_DATE);
        Log.d("list helper", "cursor");
        return mCursor;
    }

    public Cursor getGenreCursor(String whereClause, String[] whereArgs) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        String[] asColumnsToReturn = new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_PLACE, COLUMN_DATE, COLUMN_DESCR, COLUMN_LOGO, COLUMN_GENRE, COLUMN_PRICE, COLUMN_DAYS, COLUMN_WEBSITE, COLUMN_TICKETIMG, COLUMN_SAVEDID};
        Cursor mCursor = queryBuilder.query(dbSqlite, asColumnsToReturn, whereClause,
                whereArgs, null, null, COLUMN_DATE);
        return mCursor;
    }

    public int getId(Cursor c) {
        return (c.getInt(0));
    }

    public String getName(Cursor c) {
        return (c.getString(1));
    }

    public String getPlace(Cursor c) {
        return (c.getString(2));
    }

    public String getDate(Cursor c) {
        String dateNum = c.getString(3);
        String day = dateNum.substring(6);
        String month = dateNum.substring(4, 6);
        String year = dateNum.substring(0, 4);
        String date = day + "." + month + "." + year;
        return (date);
    }

    public String getDescr(Cursor c) {
        return (c.getString(4));
    }

    public String getLogo(Cursor c) {
        return (c.getString(5));
    }

    public String getGenre(Cursor c) {
        return (c.getString(6).toUpperCase());
    }

    public int getPrice(Cursor c) {
        return (c.getInt(7));
    }

    public int getDays(Cursor c) {
        return (c.getInt(8));
    }

    public String getWebsite(Cursor c) {
        return (c.getString(9));
    }

    public String getTicketImg(Cursor c) {
        return (c.getString(10));
    }

    public int getSavedId(Cursor c) {
        return (c.getInt(11));
    }


    public void updateSavedTicket(Cursor c) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        getCursor();
        int value = 1;
        values.put(COLUMN_ID, getId(c));
        values.put(COLUMN_TITLE, getName(c));
        values.put(COLUMN_PLACE, getPlace(c));
        values.put(COLUMN_DATE, getDate(c));
        values.put(COLUMN_DESCR, getDescr(c));
        values.put(COLUMN_LOGO, getLogo(c));
        values.put(COLUMN_GENRE, getGenre(c));
        values.put(COLUMN_PRICE, getPrice(c));
        values.put(COLUMN_DAYS, getDays(c));
        values.put(COLUMN_WEBSITE, getWebsite(c));
        values.put(COLUMN_TICKETIMG, getTicketImg(c));
        values.put(COLUMN_SAVEDID, value);


        db.update(TABLE_NAME, values, null, null);
        db.close();


    }

    ;


/*
    public boolean updateSavedTicket(String column){
    // Gets the data repository in write mode

        ListHelper mDbHelper = new ListHelper(myContext);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(column, "saved");

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
        TABLE_NAME,
        COLUMN_SAVEDID,
        values);

        return true;
    };*/

}

