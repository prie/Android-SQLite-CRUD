package com.siossae.android.biodatasqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.siossae.android.biodatasqlite.model.BiodataModel;
import com.siossae.android.biodatasqlite.sql.SqlBiodata;

public class BuatBiodataActivity extends AppCompatActivity {

    private BiodataModel biodataModel;
    private EditText nama, tgl, jk, almt;
    private Button simpan;
    private SqlBiodata sqlBio;
    private boolean tambah;
    private Long selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_biodata);

        nama = (EditText) findViewById(R.id.in_nama);
        tgl = (EditText) findViewById(R.id.in_tgl);
        jk = (EditText) findViewById(R.id.in_jk);
        almt = (EditText) findViewById(R.id.in_alamat);
        simpan = (Button) findViewById(R.id.simpan_biodata);

        responAksi();

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nama.getText().toString())) {
                    nama.setError("Nama harus diisi!");
                    nama.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(tgl.getText().toString())) {
                    tgl.setError("Tanggal lahir harus diisi!");
                    tgl.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(jk.getText().toString())) {
                    jk.setError("Jenis kelamin harus diisi!");
                    jk.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(almt.getText().toString())) {
                    //Toast.makeText(BuatBiodataActivity.this, "Alamat harus diisi", Toast.LENGTH_SHORT).show();
                    almt.setError("Alamat harus diisi!");
                    almt.requestFocus();
                    return;
                }

                biodataModel = new BiodataModel();
                biodataModel.setNama(nama.getText().toString());
                biodataModel.setTanggal(tgl.getText().toString());
                biodataModel.setJenisKelamin(jk.getText().toString());
                biodataModel.setAlamat(almt.getText().toString());

                sqlBio = new SqlBiodata(BuatBiodataActivity.this, true);

                if (tambah == true) {
                    try {
                        sqlBio.insertBio(biodataModel);
                        Toast.makeText(BuatBiodataActivity.this, "Data berhasil disimpan.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("INSERT FAIL : ", e.toString());
                    }
                } else {
                    try {
                        sqlBio.updateBio(biodataModel, selectedId);
                        Toast.makeText(BuatBiodataActivity.this, "Data berhasil di-update.", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        Log.e("INSERT FAIL : ", e.toString());
                    }
                }
                finish();
            }
        });
    }

    private void responAksi() {
        String aksi = getIntent().getStringExtra("aksi");

        if (aksi.equals("tambah")) {
            tambah = true;
            simpan.setText("Tambah Data");
        } else if (aksi.equals("edit")) {
            selectedId = getIntent().getLongExtra("selectedid", 0);
            sqlBio = new SqlBiodata(this, false);
            try {
                // biodataModel.setId(selectedId);                             // Pakai cara ini gagal
                biodataModel = sqlBio.getBiodatasById(selectedId);             // query get data utk ditampilkan di form edit
                nama.setText(biodataModel.getNama());
                tgl.setText(biodataModel.getTanggal());
                jk.setText(biodataModel.getJenisKelamin());
                almt.setText(biodataModel.getAlamat());
            } catch (Exception e) {
                Log.e("Get by ", "Gagal");
            }
            tambah = false;
            simpan.setText("Edit Data");
        }
    }
}
