package com.android.smendon.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.smendon.sqlite.models.MovieRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanket Mendon on 27-06-2018.
 */

public class DBRenderer extends SQLiteOpenHelper {
    private DBSchema dbSchema;

    public DBRenderer(Context context) {
        super(context, DBConfiguration.DB_NAME, null, DBConfiguration.DB_VERSION);
        dbSchema = new DBSchema();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(dbSchema.query_create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dbSchema.query_drop_table);
        onCreate(db);
    }

    public long addRecord(MovieRecord mRecord) {
        try {
            SQLiteDatabase mDb = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(dbSchema.col_title, mRecord.getTitle());
            values.put(dbSchema.col_language, mRecord.getLanguage());
            values.put(dbSchema.col_genre, mRecord.getGenre());
            values.put(dbSchema.col_runtime, mRecord.getRuntime());
            values.put(dbSchema.col_rating, mRecord.getRating());
            long insert_result = mDb.insert(dbSchema.table_name, null, values);
            mDb.close();
            return insert_result;
        } catch (Exception ex) {
            return 0;
        }
    }

    public List<MovieRecord> getAllRecords() {
        try {
            List<MovieRecord> listRecords = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + dbSchema.table_name;
            SQLiteDatabase mDb = this.getWritableDatabase();
            Cursor cursor = mDb.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    MovieRecord mRecord = new MovieRecord();
                    mRecord.setTitle(cursor.getString(1));
                    mRecord.setLanguage(cursor.getString(2));
                    mRecord.setGenre(cursor.getString(3));
                    mRecord.setRuntime(cursor.getString(4));
                    mRecord.setRating(cursor.getString(5));
                    listRecords.add(mRecord);
                } while (cursor.moveToNext());
            }
            return listRecords;
        } catch (Exception ex) {
            return null;
        }
    }
}
