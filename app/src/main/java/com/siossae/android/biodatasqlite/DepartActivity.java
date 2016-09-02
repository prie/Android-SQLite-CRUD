package com.siossae.android.biodatasqlite;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.siossae.android.biodatasqlite.model.DepartModel;
import com.siossae.android.biodatasqlite.sql.SqlDepart;

import java.util.List;

public class DepartActivity extends AppCompatActivity {

    private TableLayout tblDepart;
    private TableRow trHead, trData;
    private TextView lblNomor, tvNomor, lblDepart, tvDepart, lblKepala, tvKepala;
    private List<DepartModel> dm;
    private SqlDepart sqlDepart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);

        tblDepart = (TableLayout) findViewById(R.id.tbl_depart);

        trHead = new TableRow(this);
        trHead.setBackgroundColor(Color.GRAY);
        trHead.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        lblNomor = new TextView(this);
        lblNomor.setText("NO");
        lblNomor.setTypeface(null, Typeface.BOLD);
        lblNomor.setGravity(Gravity.CENTER);
        lblNomor.setTextColor(Color.WHITE);
        lblNomor.setPadding(5, 5, 5, 5);
        trHead.addView(lblNomor);

        lblDepart = new TextView(this);
        lblDepart.setText("DEPARTEMEN");
        lblDepart.setTypeface(null, Typeface.BOLD);
        lblDepart.setGravity(Gravity.CENTER);
        lblDepart.setTextColor(Color.WHITE);
        lblDepart.setPadding(7, 5, 7, 5);
        trHead.addView(lblDepart);

        lblKepala = new TextView(this);
        lblKepala.setText("KEPALA");
        lblKepala.setTypeface(null, Typeface.BOLD);
        lblKepala.setGravity(Gravity.CENTER);
        lblKepala.setTextColor(Color.WHITE);
        lblKepala.setPadding(7, 5, 7, 5);
        trHead.addView(lblKepala);

        tblDepart.addView(trHead, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        sqlDepart = new SqlDepart(this, false);
        dm = sqlDepart.getAllDeparts();

        for (int i = 0; i < dm.size(); i++) {
            trData = new TableRow(this);
            if (i % 2 != 0) trData.setBackgroundColor(Color.parseColor("#EEEEEE"));
            trData.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            tvNomor = new TextView(this);
            tvNomor.setText(String.valueOf(i + 1));
            tvNomor.setGravity(Gravity.CENTER);
            trData.addView(tvNomor);

            tvDepart = new TextView(this);
            tvDepart.setText(dm.get(i).getDepart());
            tvDepart.setPadding(7, 0, 7, 0);
            trData.addView(tvDepart);

            tvKepala = new TextView(this);
            tvKepala.setText(dm.get(i).getNmKepala());
            tvKepala.setPadding(7, 0, 7, 0);
            trData.addView(tvKepala);

            tblDepart.addView(trData, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
