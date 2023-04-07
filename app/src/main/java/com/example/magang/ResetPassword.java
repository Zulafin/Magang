package com.example.magang;

import static com.example.magang.LoginActivity.setWindowFlag;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.magang.adapter.AlertDialogManager;
import com.example.magang.database.DatabaseHelper;
import com.example.magang.session.SessionManager;


public class ResetPassword extends AppCompatActivity {
    EditText txtUsername, txtPasswordLama, txtPasswordBaru;
    Button btnReset;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    protected Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        session = new SessionManager(getApplicationContext());

        txtUsername = findViewById(R.id.email);
        txtPasswordLama = findViewById(R.id.pass_lama);
        txtPasswordBaru = findViewById(R.id.pass_baru);
        btnReset = findViewById(R.id.reset_password);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM TABLE_USER WHERE email = '" +
                getIntent().getStringExtra("email") + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            txtUsername.setText(cursor.getString(0).toString());
            txtPasswordLama.setText(cursor.getString(1).toString());
            txtPasswordBaru.setText(cursor.getString(2).toString());
        }

        btnReset = (Button) findViewById(R.id.reset_password);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("update TABLE_USER set nama='" +
                        txtPasswordLama.getText().toString() + "', password lama='" +
                        txtPasswordBaru.getText().toString() + "' where password baru ='" +
                        txtUsername.getText().toString() + "'");
                Toast.makeText(getApplicationContext(), "Password Berhasil di Reset", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}