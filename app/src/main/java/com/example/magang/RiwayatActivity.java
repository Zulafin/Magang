package com.example.magang;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.example.magang.database.DatabaseHelper;

public class RiwayatActivity extends AppCompatActivity {

    private ListView listViewRiwayat;
    private List<ItemRiwayat> riwayatList;
    private RiwayatAdapter riwayatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        listViewRiwayat = findViewById(R.id.listRiwayat);
        riwayatList = new ArrayList<>();
        riwayatAdapter = new RiwayatAdapter(this, R.layout.riwayat_item, riwayatList);
        listViewRiwayat.setAdapter(riwayatAdapter);

        loadRiwayatData();
    }

    private void loadRiwayatData() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COL_RESI,
                DatabaseHelper.COL_NAMA_BARANG
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_BARANG,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String noResi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESI));
            String namaBarang = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAMA_BARANG));
            String tanggal = getCurrentDate();
            String jam = getCurrentTime();
            riwayatList.add(new ItemRiwayat(noResi, namaBarang, tanggal, jam));
        }

        cursor.close();
        db.close();
        riwayatAdapter.notifyDataSetChanged();
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date();
        return timeFormat.format(date);
    }
}
