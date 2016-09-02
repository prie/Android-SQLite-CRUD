package com.siossae.android.biodatasqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.siossae.android.biodatasqlite.adapter.BiodataAdapter;
import com.siossae.android.biodatasqlite.model.BiodataModel;
import com.siossae.android.biodatasqlite.sql.SqlBiodata;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView01;
    private Button btnTambah, btnDepart, btnAll;
    private EditText searchKey;
    private ImageButton btnSearch;
    private List<BiodataModel> bioModel;
    private SqlBiodata sqlBio;
    private boolean cari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView01 = (ListView) findViewById(R.id.listview1);
        btnAll = (Button) findViewById(R.id.btn_all);
        btnTambah = (Button) findViewById(R.id.button2);
        btnDepart = (Button) findViewById(R.id.btn_depart);
        searchKey = (EditText) findViewById(R.id.cari_nama);
        btnSearch = (ImageButton) findViewById(R.id.btn_search);

        cari = false;
        refreshList();

        assert btnTambah != null;
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, BuatBiodataActivity.class);
            intent.putExtra("aksi", "tambah");
            startActivity(intent);
            }
        });

        btnDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DepartActivity.class);
                startActivity(intent);
            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cari = false;
                refreshList();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cari = true;
                refreshList();
            }
        });

        listView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                TextView itemId = (TextView) view.findViewById(R.id.id);
                final Long selectedId = Long.parseLong(itemId.getText().toString());

                String[] pilihan = {"Edit Data", "Delete Data"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                //dialog.setTitle("Aksi");
                dialog.setItems(pilihan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(MainActivity.this, BuatBiodataActivity.class);
                                intent.putExtra("aksi", "edit");
                                intent.putExtra("selectedid", selectedId);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "Kamu pilih update!", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                cari = false;
                                deleteData(selectedId);
                                break;
                        }
                    }
                });
                dialog.create().show();
            }
        });
    }

    // method onRestart dijalankan ketika activity diatas activity ini ditutup.
    // bedanya dg onResume, onResume juga dijalankan saat activity dibuka.
    @Override
    protected void onRestart() {
        super.onRestart();

        refreshList();
    }

    private void refreshList() {
        bioModel = new ArrayList<>();
        bioModel.clear();
        sqlBio = new SqlBiodata(this, false);

        if (cari == true) {
            try {
                bioModel = sqlBio.getBiodataByName(searchKey.getText().toString());
                searchKey.setText("");
            } catch (Exception e) {
                Log.e("Exception Search", e.toString());
            }
        } else  {
            try {
                bioModel = sqlBio.getAllBiodatas();
            } catch (Exception e) {
                Log.e("GET ALL FAILED", e.toString());
            }
        }

        BiodataAdapter adapter = new BiodataAdapter(this, bioModel);
        listView01.setAdapter(adapter);

        if (bioModel.isEmpty()) {
            Toast.makeText(MainActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
        }

    }

    private void deleteData(Long selectedId) {

        sqlBio = new SqlBiodata(MainActivity.this, true);
        try {
            sqlBio.deleteBio(selectedId);
            Toast.makeText(MainActivity.this, "Data berhasil dihapus.", Toast.LENGTH_SHORT).show();
            searchKey.setText("");
            refreshList();
        } catch (Exception e) {
            Log.e("onItemClick ", "SQL Error!");
        }
    }
}
