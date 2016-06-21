package com.example.festapptabs;

import android.annotation.SuppressLint;
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
import java.util.Locale;

//class to manage database creation and version management
class ListHelper extends SQLiteOpenHelper {
    //table name
    private static final String TABLE_NAME = "festivallist";
    private static final String COLUMN_ID = "_id";
    //column names
    private static final String COLUMN_TITLE = "festname";
    private static final String COLUMN_PLACE = "place";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_DESCR = "deswcription";
    private static final String COLUMN_LOGO = "logo";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DAYS = "days";
    private static final String COLUMN_WEBSITE = "website";
    private static final String COLUMN_TICKETIMG = "ticketimg";
    private static final String COLUMN_SAVEDID = "savedId";
    // database info
    private static final String DATABASE_PATH = "/data/data/com.example.festapptabs/databases";
    private static final String DATABASE_NAME = "festlist.db";
    private static final int VERSION = 1;
    private final Context myContext;
    private SQLiteDatabase dbSqlite;

    //constructor
    public ListHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.myContext = context;
    }

    //called when the database is created for the first time
    //if a database already exists on disk with the same DATABASE_NAME, this method will not be called
    @Override
    public void onCreate(SQLiteDatabase db) {
        copyDBFromResource();
        openDataBase();
    }

    //called when the database needs to be upgraded
    //This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    //but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public void createDB() {
        boolean dbExist = DBExists();
        if (!dbExist) {
            //calling this method creates an empty database into the default system location
            //it is needed to overwrite the db
            copyDBFromResource();
            this.getWritableDatabase();
            //copy the database included
            openDataBase();
        }
    }

    //check if the database exist and can be read
    private boolean DBExists() {
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
        return db != null;
    }

    //copy database from assets folder
    private void copyDBFromResource() {
        InputStream inputStream;
        OutputStream outputStream;
        String dbFilePath = DATABASE_PATH + DATABASE_NAME;
        try {
            //open local db as the input stream
            inputStream = myContext.getAssets().open(DATABASE_NAME);
            //open the db as the output stream
            outputStream = new FileOutputStream(dbFilePath);
            //transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            //close the streams
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new Error("problem copying db from resource file");
        }
    }

    // opening database
    public void openDataBase() {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        dbSqlite = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    //closing database
    @Override
    public synchronized void close() {
        if (dbSqlite != null) {
            dbSqlite.close();
        }
        super.close();
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
        return queryBuilder.query(dbSqlite, asColumnsToReturn, whereClause,
                whereArgs, null, null, COLUMN_DATE);
    }
}

