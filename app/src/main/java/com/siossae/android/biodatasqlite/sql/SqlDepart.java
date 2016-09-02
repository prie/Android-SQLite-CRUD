package com.siossae.android.biodatasqlite.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.siossae.android.biodatasqlite.db.DataHelper;
import com.siossae.android.biodatasqlite.model.DepartModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pri on 23/08/16.
 */
public class SqlDepart {
    private final Context context;
    private SQLiteDatabase db;
    private DataHelper dataHelper;
    private static final String TABLE_NAME1 = "tbl_biodata";
    private static final String TABLE_NAME2 = "tbl_depart";
    private static final String ROW_ID = "_id";
    private static final String ROW_DEPART = "depart";
    private static final String ROW_KEPALA = "kepala_id";
    private static final String ROW_NAMA = "nama";

    public SqlDepart(Context context, boolean write) {
        this.context = context;
        dataHelper = new DataHelper(context);
        if (write == true) {
            db = dataHelper.getWritableDatabase();
        } else {
            db = dataHelper.getReadableDatabase();
        }
    }

    // Mengambil semua data dalam tabel tbl_depart database.
    public List<DepartModel> getAllDeparts() {
        List<DepartModel> departs = new ArrayList<>();                    // Persiapkan object/array untuk menapung data dari tabel
        String sql = "SELECT d."+ROW_ID+", d."+ROW_KEPALA+", d."+ROW_DEPART+", b."+ROW_NAMA+" FROM " + TABLE_NAME1 + " AS b JOIN " + TABLE_NAME2 + " AS d ON b." + ROW_ID + " = d." + ROW_KEPALA;
        Cursor cursor = db.rawQuery(sql, null);                             // Data query ditampung didalam cursor
        cursor.moveToFirst();                                               // Pindahkan cursor ke baris pertama data
        while (!cursor.isAfterLast()) {                                     // Selama cursor tidak melewati melebihi data terakhir
            DepartModel dm = new DepartModel();                           // Buat object baru untuk menyimpan data ke model
            dm.setId(cursor.getLong(0));                                    // Menyimpan id dari tabel ke model
            dm.setIdKepala(cursor.getLong(1));
            dm.setDepart(cursor.getString(2));
            dm.setNmKepala(cursor.getString(3));                                // Menyimpan nama dari tabel ke model.
            departs.add(dm);                                               // memasukkan data model kedalam array.
            cursor.moveToNext();                                            // Pindahkan cursor ke baris berikutnya
        }
        cursor.close();
        db.close();

        return departs;
    }

    /**
     * Ambil data berdasarkan kata kunci, untuk kebutuhan search
     * @param depart
     * @return
     */
    public List<DepartModel> getDepartByName(String depart) {
        List<DepartModel> departs = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + ROW_DEPART + " LIKE '%" + depart + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DepartModel dm = new DepartModel();
            dm.setId(cursor.getLong(0));                                    // Menyimpan id dari tabel ke model
            dm.setDepart(cursor.getString(1));                                // Menyimpan nama dari tabel ke model.
            dm.setIdKepala(cursor.getLong(2));                             // Menyimpan tanggal dari tabel ke model.
            departs.add(dm);                                               // memasukkan data model kedalam array.
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return departs;
    }

    /**
     * Ambil baris data by _id
     * Digunakan untuk ambil data yang akan di edit/update
     * Bisa juga digunakan untuk search data by id
     **/
    public DepartModel getDepartById(Long departId) {
        String selId = Long.toString(departId);
        String sql = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + ROW_ID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{selId});
        cursor.moveToFirst();                                       // Perintah ini tetap diperlukan meskipun hanya 1 row yg dihasilkan.
        DepartModel dm = new DepartModel();
        dm.setDepart(cursor.getString(1));
        dm.setIdKepala(cursor.getLong(2));

        cursor.close();
        db.close();

        return dm;
    }

    // Insert data ke dalam database
    public void insertDepart(DepartModel departModel) {
        ContentValues values = new ContentValues();
        values.put(ROW_DEPART, departModel.getDepart());
        values.put(ROW_KEPALA, departModel.getIdKepala());

        db.insert(TABLE_NAME2, null, values);
        db.close();
    }

    // Update tabel depart
    public void updateDepart(DepartModel departModel, Long departId) {
        String updateId = Long.toString(departId);
        ContentValues values = new ContentValues();
        values.put(ROW_DEPART, departModel.getDepart());
        values.put(ROW_KEPALA, departModel.getIdKepala());

        db.update(TABLE_NAME2, values, ROW_ID + "=?", new String[]{updateId});
        db.close();
    }

    // Delete data by _id
    public void deleteDepart(Long departId) {
        String delId = Long.toString(departId);
        db.delete(TABLE_NAME2, ROW_ID + "=?", new String[]{delId});
        db.close();
    }
}
