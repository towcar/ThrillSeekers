package com.carsonskjerdal.app.thrillseekers.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.carsonskjerdal.app.thrillseekers.models.Places;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Carson on 4/17/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "myDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_LOCATIONS = "locations";
    //private static final String TABLE_USERS = "users";

    // Post Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_GROUP = "groupName";
    private static final String KEY_LATITUDE = "lat";
    private static final String KEY_LONGITUDE = "lon";

    // User Table Columns
    // private static final String KEY_USER_ID = "id";
    // private static final String KEY_USER_NAME = "userName";
    //private static final String KEY_USER_PROFILE_PICTURE_URL = "profilePictureUrl";

    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_NAME + " TEXT," +
                KEY_GROUP + " TEXT," +
                KEY_LATITUDE + " TEXT," +
                KEY_LONGITUDE + " TEXT" +
                ")";

        /*String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_NAME + " TEXT," +
                KEY_USER_PROFILE_PICTURE_URL + " TEXT" +
                ")";*/

        db.execSQL(CREATE_POSTS_TABLE);
        //db.execSQL(CREATE_USERS_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }

    public void insertPlace(Places place) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, place.name);
            values.put(KEY_GROUP, place.group);
            values.put(KEY_LONGITUDE, place.lon);
            values.put(KEY_LATITUDE, place.lat);


            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_LOCATIONS, values, KEY_NAME + "= ?", new String[]{place.name});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                db.setTransactionSuccessful();

            } else {
                // user with this userName did not already exist, so insert new user
                db.insertOrThrow(TABLE_LOCATIONS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");

        } finally {
            db.endTransaction();
        }

    }

    // Insert a post into the database
    public void addPlace(Places places) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            long userId = addOrUpdatePlaces(places);

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, userId);
            values.put(KEY_LATITUDE, places.lat);
            values.put(KEY_LONGITUDE, places.lon);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            //throws fail if the value already exists
            db.insertOrThrow(TABLE_LOCATIONS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    private long addOrUpdatePlaces(Places place) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, place.name);
            values.put(KEY_LONGITUDE, place.lon);
            values.put(KEY_LATITUDE, place.lat);


            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique, returns number of rows updated (should be one)
            int rows = db.update(TABLE_LOCATIONS, values, KEY_NAME + "= ?", new String[]{place.name});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_ID, TABLE_LOCATIONS, KEY_NAME);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(place.name)});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_LOCATIONS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }


    // Get all posts in the database
    public List<Places> getAllPlaces() {
        List<Places> placesList = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String POSTS_SELECT_QUERY =
                "SELECT * FROM TABLE_LOCATIONS";

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                    String group = cursor.getString(cursor.getColumnIndex(KEY_GROUP));
                    String lon = cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE));
                    String lat = cursor.getString(cursor.getColumnIndex(KEY_LATITUDE));
                    Places newPlace = new Places(name, group, lon, lat);
                    placesList.add(newPlace);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return placesList;
    }


    // Delete all posts and users in the database
    public void deleteAllPostsAndUsers() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_LOCATIONS, null, null);
            //db.delete(TABLE_USERS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }
}
