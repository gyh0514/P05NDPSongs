package android.myapplicationdev.com.p05_ndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 127589 on 23/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simplenotes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGER = "singers";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT "
                + COLUMN_SINGER + " TEXT "
                + COLUMN_YEAR + " INTEGER "
                + COLUMN_STARS + " INTEGER ) ";
        db.execSQL(createNoteTableSql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_SONG + " ADD COLUMN module_name TEXT ");
        onCreate(db);
    }

    public long insertSong(String noteContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, noteContent);
        values.put(COLUMN_SINGER, noteContent);
        values.put(COLUMN_YEAR, noteContent);
        values.put(COLUMN_STARS, noteContent);
        long result = db.insert(TABLE_SONG, null, values);
        db.close();
        Log.d("SQL Insert",""+ result); //id returned, shouldn’t be -1
        return result;
    }
    public ArrayList<String> getAllSongs() {
        ArrayList<String> songs = new ArrayList<String>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE + COLUMN_SINGER + COLUMN_YEAR + COLUMN_STARS + " FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String content = cursor.getString(1);
                songs.add("ID:" + id + ", " + content);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }
    // Update data from database
    public int updateSong(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SONG, values, condition, args);
        db.close();
        return result;
    }
    // Delete data from database
    public int deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        db.close();
        return result;
    }
    // Filtered Selection
    public ArrayList<Song> getAllSongs(String keyword) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE};
        String condition = COLUMN_TITLE + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singer = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(title, singer, year ,stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

}