package com.mymovielib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arthur on 19.12.2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static final String TABLE_NAME = "MOVIES";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_MOVIE_NAME = "MOVIE_NAME";
    public static final String COLUMN_MOVIE_YEAR = "MOVIE_YEAR";
    public static final String COLUMN_MOVIE_POSTER = "MOVIE_POSTER";
    public static final String COLUMN_MOVIE_DIRECTOR = "MOVIE_DIRECTOR";
    public static final String COLUMN_MOVIE_DIRECTOR_IMG = "MOVIE_DIRECTOR_IMG";
    public static final String COLUMN_MOVIE_CAST_ONE = "MOVIE_CAST_ONE";
    public static final String COLUMN_MOVIE_CAST_TWO = "MOVIE_CAST_TWO";
    public static final String COLUMN_MOVIE_CAST_THREE = "MOVIE_CAST_THREE";
    public static final String COLUMN_MOVIE_CAST_FOUR = "MOVIE_CAST_FOUR";

    public static final String COLUMN_MOVIE_CAST_IMG_ONE = "MOVIE_CAST_IMG_ONE";
    public static final String COLUMN_MOVIE_CAST_IMG_TWO = "MOVIE_CAST_IMG_TWO";
    public static final String COLUMN_MOVIE_CAST_IMG_THREE = "MOVIE_CAST_IMG_THREE";
    public static final String COLUMN_MOVIE_CAST_IMG_FOUR = "MOVIE_CAST_IMG_FOUR";
    public static final String COLUMN_MOVIE_DESCRIPTION = "MOVIE_DESCRIPTION";
    public static final String COLUMN_MOVIE_GENRE = "MOVIE_GENRE";
    public static final String COLUMN_MOVIE_PATH = "MOVIE_PATH";
    public static final String COLUMN_MOVIE_COUNTRY = "MOVIE_COUNTRY";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_MOVIE_NAME + " VARCHAR, "  + COLUMN_MOVIE_YEAR + " VARCHAR, " + COLUMN_MOVIE_POSTER + " VARCHAR, " + COLUMN_MOVIE_DIRECTOR + " VARCHAR, " + COLUMN_MOVIE_DIRECTOR_IMG + " VARCHAR, " + COLUMN_MOVIE_CAST_ONE + " VARCHAR, " + COLUMN_MOVIE_CAST_TWO + " VARCHAR, " + COLUMN_MOVIE_CAST_THREE + " VARCHAR, " + COLUMN_MOVIE_CAST_FOUR + " VARCHAR, " + COLUMN_MOVIE_CAST_IMG_ONE + " VARCHAR, " + COLUMN_MOVIE_CAST_IMG_TWO + " VARCHAR, " + COLUMN_MOVIE_CAST_IMG_THREE + " VARCHAR, "+COLUMN_MOVIE_CAST_IMG_FOUR + " VARCHAR, "+COLUMN_MOVIE_DESCRIPTION + " VARCHAR, "+COLUMN_MOVIE_GENRE + " VARCHAR, "+COLUMN_MOVIE_PATH + " VARCHAR, "+ COLUMN_MOVIE_COUNTRY + " VARCHAR);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
/**
 * All CRUD(Create, Read, Update, Delete) Operations
 */
// Adding new contact
public void addContact(Movies movie) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_MOVIE_NAME, movie.get_name());
    values.put(COLUMN_MOVIE_YEAR, movie.get_year());
    values.put(COLUMN_MOVIE_POSTER, movie.get_poster());
    values.put(COLUMN_MOVIE_DIRECTOR, movie.get_director());
    values.put(COLUMN_MOVIE_DIRECTOR_IMG, movie.get_director_img());
    values.put(COLUMN_MOVIE_CAST_ONE, movie.get_cast_one());
    values.put(COLUMN_MOVIE_CAST_TWO, movie.get_cast_two());
    values.put(COLUMN_MOVIE_CAST_THREE, movie.get_cast_three());
    values.put(COLUMN_MOVIE_CAST_FOUR, movie.get_cast_four());
    values.put(COLUMN_MOVIE_CAST_IMG_ONE, movie.get_cast_img_one());
    values.put(COLUMN_MOVIE_CAST_IMG_TWO, movie.get_cast_img_two());
    values.put(COLUMN_MOVIE_CAST_IMG_THREE, movie.get_cast_img_three());
    values.put(COLUMN_MOVIE_CAST_IMG_FOUR, movie.get_cast_img_four());
    values.put(COLUMN_MOVIE_DESCRIPTION, movie.get_description());
    values.put(COLUMN_MOVIE_GENRE, movie.get_genre());
    values.put(COLUMN_MOVIE_PATH, movie.get_path());
    values.put(COLUMN_MOVIE_COUNTRY, movie.get_country());


    // Inserting Row
    db.insert(TABLE_NAME, null, values);
    db.close(); // Closing database connection
}

    // Getting single contact
    public Movies getMovies(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,
                        COLUMN_MOVIE_NAME,COLUMN_MOVIE_YEAR,COLUMN_MOVIE_POSTER,COLUMN_MOVIE_DIRECTOR,COLUMN_MOVIE_DIRECTOR_IMG,COLUMN_MOVIE_CAST_ONE,COLUMN_MOVIE_CAST_TWO,COLUMN_MOVIE_CAST_THREE,COLUMN_MOVIE_CAST_FOUR,COLUMN_MOVIE_CAST_IMG_ONE,COLUMN_MOVIE_CAST_IMG_TWO,COLUMN_MOVIE_CAST_IMG_THREE,COLUMN_MOVIE_CAST_IMG_FOUR,COLUMN_MOVIE_DESCRIPTION,COLUMN_MOVIE_GENRE,COLUMN_MOVIE_PATH,COLUMN_MOVIE_COUNTRY }, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17));
        // return contact

        db.close();
        return movies;
    }


    public List<Movies> getLatestMovies() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Movies> contactList = new ArrayList<Movies>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        if (cursor.moveToPosition(count-30)) {
            do {
                Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17));
                contactList.add(movies);
            } while (cursor.moveToNext());
        }

        db.close();
        return contactList;
    }


    public List<Movies> getByGenre(String genre){
        List<Movies> contactList = new ArrayList<Movies>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] {COLUMN_ID,
                        COLUMN_MOVIE_NAME,COLUMN_MOVIE_YEAR,COLUMN_MOVIE_POSTER,COLUMN_MOVIE_DIRECTOR,COLUMN_MOVIE_DIRECTOR_IMG,COLUMN_MOVIE_CAST_ONE,COLUMN_MOVIE_CAST_TWO,COLUMN_MOVIE_CAST_THREE,COLUMN_MOVIE_CAST_FOUR,COLUMN_MOVIE_CAST_IMG_ONE,COLUMN_MOVIE_CAST_IMG_TWO,COLUMN_MOVIE_CAST_IMG_THREE,COLUMN_MOVIE_CAST_IMG_FOUR,COLUMN_MOVIE_DESCRIPTION,COLUMN_MOVIE_GENRE,COLUMN_MOVIE_PATH, COLUMN_MOVIE_COUNTRY},
                COLUMN_MOVIE_GENRE + " LIKE ?", new String[] {"%" + genre + "%"},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
               Movies contact = new Movies(Integer.parseInt(cursor.getString(0)),
                       cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17));
contactList.add(contact);
            } while (cursor.moveToNext());
        }
db.close();
        return contactList;
    }

    public List<Movies> getByDirector(String director){
        List<Movies> contactList = new ArrayList<Movies>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] {COLUMN_ID,
                        COLUMN_MOVIE_NAME,COLUMN_MOVIE_YEAR,COLUMN_MOVIE_POSTER,COLUMN_MOVIE_DIRECTOR,COLUMN_MOVIE_DIRECTOR_IMG,COLUMN_MOVIE_CAST_ONE,COLUMN_MOVIE_CAST_TWO,COLUMN_MOVIE_CAST_THREE,COLUMN_MOVIE_CAST_FOUR,COLUMN_MOVIE_CAST_IMG_ONE,COLUMN_MOVIE_CAST_IMG_TWO,COLUMN_MOVIE_CAST_IMG_THREE,COLUMN_MOVIE_CAST_IMG_FOUR,COLUMN_MOVIE_DESCRIPTION,COLUMN_MOVIE_GENRE,COLUMN_MOVIE_PATH, COLUMN_MOVIE_COUNTRY},
                COLUMN_MOVIE_DIRECTOR + " LIKE ?", new String[] {"%" + director + "%"},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Movies contact = new Movies(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        return contactList;
    }

    public List<Movies> getByCountry(String country){
        List<Movies> contactList = new ArrayList<Movies>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = null;
        String[] sqlarray;
        if (country.equals("СССР") || country.equals("Россия")){
            sql = " LIKE ?";
            sqlarray = new String[]{"%" + country + "%"};
        }else{
            sql = " NOT LIKE ?";
            sqlarray = new String[]{"%" + "СССР" + "%"+" AND "+COLUMN_MOVIE_COUNTRY+" NOT LIKE "+"%" + "Россия" + "%"};

        }
        Cursor cursor = db.query(TABLE_NAME, new String[] {COLUMN_ID,
                        COLUMN_MOVIE_NAME,COLUMN_MOVIE_YEAR,COLUMN_MOVIE_POSTER,COLUMN_MOVIE_DIRECTOR,COLUMN_MOVIE_DIRECTOR_IMG,COLUMN_MOVIE_CAST_ONE,COLUMN_MOVIE_CAST_TWO,COLUMN_MOVIE_CAST_THREE,COLUMN_MOVIE_CAST_FOUR,COLUMN_MOVIE_CAST_IMG_ONE,COLUMN_MOVIE_CAST_IMG_TWO,COLUMN_MOVIE_CAST_IMG_THREE,COLUMN_MOVIE_CAST_IMG_FOUR,COLUMN_MOVIE_DESCRIPTION,COLUMN_MOVIE_GENRE,COLUMN_MOVIE_PATH, COLUMN_MOVIE_COUNTRY},
                COLUMN_MOVIE_COUNTRY + sql, sqlarray,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Movies contact = new Movies(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        return contactList;
    }
public ArrayList<String> getAllPath(){
    ArrayList<String> s = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(true, TABLE_NAME, new String[] { COLUMN_MOVIE_PATH }, null, null, COLUMN_MOVIE_PATH, null, null, null);
    if (cursor.moveToFirst()) {
        do {
            s.add(cursor.getString(0));
        } while (cursor.moveToNext());
    }
    db.close();

    return s;
}

    public List<Directors> getDirectors(){
        List<Directors> directors = new ArrayList<Directors>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_NAME, new String[] { COLUMN_MOVIE_DIRECTOR, COLUMN_MOVIE_DIRECTOR_IMG }, null, null, COLUMN_MOVIE_DIRECTOR, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Directors director = new Directors(cursor.getString(0), cursor.getString(1));
               directors.add(director);
            } while (cursor.moveToNext());
        }
        db.close();
        return directors;
    }

    public ArrayList<String> fetchMyRowid()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select  "+COLUMN_MOVIE_DIRECTOR+" From " + TABLE_NAME;

        Cursor mCursor =db.rawQuery(query, null);
        ArrayList<String> buf = new ArrayList<>();
        while(mCursor.moveToNext()) {

            buf.add(mCursor.getString(mCursor.getColumnIndex(COLUMN_MOVIE_DIRECTOR)));

        }
db.close();
        return buf;
    }

    // Getting All Contacts
  //  public List<Movies> getAllContacts() {}

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);


        // return count
        return cursor.getCount();
    }

    // Updating single contact
  //  public int updateContact(Movies movie) {}

    // Deleting single contact
//    public void deleteContact(Movies movie) {}
}