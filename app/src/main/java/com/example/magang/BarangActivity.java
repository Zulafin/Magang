package com.example.magang;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.magang.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BarangActivity extends AppCompatActivity {
    private EditText NoResi, NamaBarang, NamaPenerima;
    private ImageView imageViewFoto;
    private Button buttonUpload, buttonKirim;
    private byte[] fotoByteArray;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        NoResi = findViewById(R.id.resi);
        NamaBarang = findViewById(R.id.namabarang);
        NamaPenerima = findViewById(R.id.penerima);
        imageViewFoto = findViewById(R.id.image);
        buttonUpload = findViewById(R.id.fotobarang);
        buttonKirim = findViewById(R.id.kirim);

        buttonUpload.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK);
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            imagePickerLauncher.launch(pickIntent);
        });

        dbHelper = new DatabaseHelper(this);
        dbHelper.open(); // Open the database

        buttonKirim.setOnClickListener(v -> {
            String noResi = NoResi.getText().toString();
            String namaBarang = NamaBarang.getText().toString();
            String namaPenerima = NamaPenerima.getText().toString();

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COL_RESI, noResi);
            values.put(DatabaseHelper.COL_NAMA_BARANG, namaBarang);
            values.put(DatabaseHelper.COL_NAMA_PENERIMA, namaPenerima);
            values.put(DatabaseHelper.COL_FOTO, fotoByteArray);

            long newRowId = db.insert(DatabaseHelper.TABLE_BARANG, null, values);

            if (newRowId != -1) {
                Toast.makeText(this, "Data barang berhasil dikirim", Toast.LENGTH_SHORT).show();

                Intent riwayatIntent = new Intent(BarangActivity.this, RiwayatActivity.class);
                riwayatIntent.putExtra("noResi", noResi);
                riwayatIntent.putExtra("namaBarang", namaBarang);
                startActivity(riwayatIntent);
            } else {
                Toast.makeText(this, "Gagal menyimpan data barang", Toast.LENGTH_SHORT).show();
            }
        });

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                        imageViewFoto.setImageBitmap(imageBitmap);
                        fotoByteArray = getByteArrayFromBitmap(imageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close(); // Close the database
    }
}