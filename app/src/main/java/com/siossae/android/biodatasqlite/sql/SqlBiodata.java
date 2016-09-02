package com.siossae.android.biodatasqlite.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.siossae.android.biodatasqlite.db.DataHelper;
import com.siossae.android.biodatasqlite.model.BiodataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pri on 23/08/16.
 */
public class SqlBiodata {
    private final Context context;
    private SQLiteDatabase db;
    private DataHelper dataHelper;
    private static final String TABLE_NAME = "tbl_biodata";
    private static final String ROW_ID = "_id";
    private static final String ROW_NAMA = "nama";
    private static final String ROW_TGL = "tgl";
    private static final String ROW_JK = "jk";
    private static final String ROW_ALMT = "alamat";

    public SqlBiodata(Context context, boolean write) {
        this.context = context;
        dataHelper = new DataHelper(context);
        if (write == true) {
            db = dataHelper.getWritableDatabase();
        } else {
            db = dataHelper.getReadableDatabase();
        }
    }

    // Mengambil semua data dalam tabel tbl_biodata database.
    public List<BiodataModel> getAllBiodatas() {
        List<BiodataModel> biodatas = new ArrayList<>();                    // Persiapkan object/array untuk menapung data dari tabel
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ROW_NAMA + " ASC";
        Cursor cursor = db.rawQuery(sql, null);                             // Data query ditampung didalam cursor
        cursor.moveToFirst();                                               // Pindahkan cursor ke baris pertama data
        while (!cursor.isAfterLast()) {                                     // Selama cursor tidak melewati melebihi data terakhir
            BiodataModel bd = new BiodataModel();                           // Buat object baru untuk menyimpan data ke model
            bd.setId(cursor.getLong(0));                                    // Menyimpan id dari tabel ke model
            bd.setNama(cursor.getString(1));                                // Menyimpan nama dari tabel ke model.
            bd.setTanggal(cursor.getString(2));                             // Menyimpan tanggal dari tabel ke model.
            bd.setJenisKelamin(cursor.getString(3));                        // Menyimpan jeniskelamin dari tabel ke model.
            bd.setAlamat(cursor.getString(4));                              // Menyimpan alamat dari tabel ke model.
            biodatas.add(bd);                                               // memasukkan data model kedalam array.
            cursor.moveToNext();                                            // Pindahkan cursor ke baris berikutnya
        }
        cursor.close();
        db.close();

        return biodatas;
    }

    /**
     * Ambil data berdasarkan kata kunci, untuk kebutuhan search
     * @param nama
     * @return
     */
    public List<BiodataModel> getBiodataByName(String nama) {
        List<BiodataModel> biodatas = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ROW_NAMA + " LIKE '%" + nama + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BiodataModel bd = new BiodataModel();
            bd.setId(cursor.getLong(0));                                    // Menyimpan id dari tabel ke model
            bd.setNama(cursor.getString(1));                                // Menyimpan nama dari tabel ke model.
            bd.setTanggal(cursor.getString(2));                             // Menyimpan tanggal dari tabel ke model.
            bd.setJenisKelamin(cursor.getString(3));                        // Menyimpan jeniskelamin dari tabel ke model.
            bd.setAlamat(cursor.getString(4));                              // Menyimpan alamat dari tabel ke model.
            biodatas.add(bd);                                               // memasukkan data model kedalam array.
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return biodatas;
    }

    /**
     * Ambil baris data by _id
     * Digunakan untuk ambil data yang akan di edit/update
     * Bisa juga digunakan untuk search data by id
     **/
    public BiodataModel getBiodatasById(Long bioId) {
        String selId = Long.toString(bioId);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ROW_ID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{selId});
        cursor.moveToFirst();                                       // Perintah ini tetap diperlukan meskipun hanya 1 row yg dihasilkan.
        BiodataModel bd = new BiodataModel();
        bd.setNama(cursor.getString(1));
        bd.setTanggal(cursor.getString(2));
        bd.setJenisKelamin(cursor.getString(3));
        bd.setAlamat(cursor.getString(4));

        cursor.close();
        db.close();

        return bd;
    }

    // Insert data ke dalam database
    public void insertBio(BiodataModel biodataModel) {
        ContentValues values = new ContentValues();
        values.put(ROW_NAMA, biodataModel.getNama());
        values.put(ROW_TGL, biodataModel.getTanggal());
        values.put(ROW_JK, biodataModel.getJenisKelamin());
        values.put(ROW_ALMT, biodataModel.getAlamat());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Update tabel biodata
    public void updateBio(BiodataModel biodat, Long bioId) {
        String updateId = Long.toString(bioId);
        ContentValues values = new ContentValues();
        values.put(ROW_NAMA, biodat.getNama());
        values.put(ROW_TGL, biodat.getTanggal());
        values.put(ROW_JK, biodat.getJenisKelamin());
        values.put(ROW_ALMT, biodat.getAlamat());

        db.update(TABLE_NAME, values, ROW_ID + "=?", new String[]{updateId});
        db.close();
    }

    // Delete data by _id
    public void deleteBio(Long bioId) {
        String delId = Long.toString(bioId);
        db.delete(TABLE_NAME, ROW_ID + "=?", new String[]{delId});
        db.close();
    }
}
