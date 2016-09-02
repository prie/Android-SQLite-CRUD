package com.siossae.android.biodatasqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.siossae.android.biodatasqlite.R;
import com.siossae.android.biodatasqlite.model.BiodataModel;

import java.util.List;

/**
 * Created by pri on 23/08/16.
 */
public class BiodataAdapter extends ArrayAdapter {
    private  Context context;
    private List<BiodataModel> arrBiodata;
    private LayoutInflater beInflater;

    public BiodataAdapter(Context context, List<BiodataModel> arrBiodata) {
        super(context, R.layout.activity_list_biodata, arrBiodata);
        this.context = context;
        this.arrBiodata = arrBiodata;
        this.beInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = beInflater.inflate(R.layout.activity_list_biodata, null);
        }
        //int a = 1;
        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView tgl = (TextView) convertView.findViewById(R.id.tanggal);
        TextView jenis = (TextView) convertView.findViewById(R.id.jenisk);
        TextView almt = (TextView) convertView.findViewById(R.id.alamat);

        id.setText(Long.toString(arrBiodata.get(position).getId()));
        nama.setText(arrBiodata.get(position).getNama());
        tgl.setText(arrBiodata.get(position).getTanggal());
        jenis.setText(arrBiodata.get(position).getJenisKelamin());
        almt.setText(arrBiodata.get(position).getAlamat());

        return (convertView);
    }
}
