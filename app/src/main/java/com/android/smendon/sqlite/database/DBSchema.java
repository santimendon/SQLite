package com.android.smendon.sqlite.database;

/**
 * Created by Sanket Mendon on 27-06-2018.
 */

public class DBSchema {
    public final String table_name = "TblMovieRecords";

    public final String col_record_id = "record_id";
    public final String col_title = "title";
    public final String col_language = "language";
    public final String col_genre = "genre";
    public final String col_runtime = "runtime";
    public final String col_rating = "rating";

    public final String query_create_table = "CREATE TABLE " + table_name
            + " (" + col_record_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_title + " TEXT,"
            + col_language + " TEXT,"
            + col_genre + " TEXT,"
            + col_runtime + " TEXT,"
            + col_rating + " TEXT)";

    public final String query_drop_table = "DROP TABLE IF EXISTS " + table_name;
}
