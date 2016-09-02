package com.siossae.android.biodatasqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pri on 19/08/16.
 */
public class DataHelper extends SQLiteOpenHelper {

    // Attribute
    private static final String DATABASE_NAME = "db_biodata";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method utk buat database dan table ketika aplikasi mengakses database dan database belum ada.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tbl_biodata (_id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT NULL, tgl TEXT NULL, jk TEXT NULL, alamat TEXT NULL)";
        String sql1 = "CREATE TABLE tbl_depart (_id INTEGER PRIMARY KEY AUTOINCREMENT, depart TEXT NULL, kepala_id INTEGER NULL)";
        db.execSQL(sql);
        db.execSQL(sql1);
        ContentValues values = new ContentValues();
        values.put("depart", "analis");
        values.put("kepala_id", 1);
        db.insert("tbl_depart", null, values);
        values.put("depart", "back-end");
        values.put("kepala_id", 2);
        db.insert("tbl_depart", null, values);

        //Log.d("DATA", "onCreate: " + sql);
    }

    // Method untuk upgrade versi database jika ditemukan versi baru
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS tbl_biodata";
        String sql1 = "DROP TABLE IF EXISTS tbl_depart";
        db.execSQL(sql);
        db.execSQL(sql1);
        onCreate(db);
    }

}
