package com.crimekiller.safetrip.DBLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.crimekiller.safetrip.model.Post;

/**
 * Created by shuangliang on 4/12/16.
 */
public class DBConnector {
    // database name
    private static final String DATABASE_NAME = "SelfPost";
    private static final String TABLE_NAME = "Post";
    private SQLiteDatabase database; // database object
    private DatabaseOpenHelper databaseOpenHelper; // database helper

    // public constructor for DatabaseConnector
    public DBConnector(Context context)
    {
        // create a new DatabaseOpenHelper
        databaseOpenHelper =
                new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }

    // open the database connection
    public void open() throws SQLException
    {
        // create or open a database for reading/writing
        database = databaseOpenHelper.getWritableDatabase();
    }

    // close the database connection
    public void close()
    {
        if (database != null)
            database.close(); // close the database connection
    } // end method close

    // inserts a new post in the database
    public void insertRecord(Post post)
    {
        ContentValues newPost = new ContentValues();
        newPost.put("date", post.getDate());
        newPost.put("licenseplate", post.getLicensePlate());
        newPost.put("destination", post.getDestination());
        newPost.put("model", post.getModel());
        newPost.put("color", post.getColor());
        newPost.put("departure", post.getDeparture());
        newPost.put("owner",post.getOwner());

        open(); // open the database
        database.insert(TABLE_NAME, null, newPost);
        Log.v("TAG", "Insert successfully!");
        close(); // close the database
    } // end method insertPost


    public void saveRecord(Post post) {

        this.insertRecord(post);
    }

    // return a Cursor with all posts information in the database
    public Cursor getAllRecords()
    {
        return database.query(TABLE_NAME,null,null,null,null,null,null);
    } // end method getAllRecords

    // get a Cursor containing all information about the score specified
    // by the given id


    // delete the result specified by the given String name
    public void deleteRecord(Post post)
    {
        open(); // open the database
        String where = "date = ?" + " AND owner = ?" + " AND licenseplate = ?";
        String[] whereArgs = {post.getDate(),post.getOwner(),post.getLicensePlate()};
        database.delete(TABLE_NAME,where,whereArgs);

        close(); // close the database
    } // end method


    private class DatabaseOpenHelper extends SQLiteOpenHelper {

        public DatabaseOpenHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        } // end DatabaseOpenHelper constructor

        @Override
        public void onCreate(SQLiteDatabase db) {
            // query to create a new table named result
            String createQuery = "CREATE TABLE " + TABLE_NAME
                    + "(_id integer primary key autoincrement, date text NOT NULL, "
                    + "licenseplate text NOT NULL, "
                    + "destination text NOT NULL, "
                    + "model text, color text, departure text, owner text); ";

            db.execSQL(createQuery); // execute the query
            Log.v("TAG", "Schema Set up Successfully!");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            db.execSQL("drop table if exists Post");
            onCreate(db);
        } // end method onUpgrade
    }

}
