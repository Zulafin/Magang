package com.example.magang;

import static com.example.magang.LoginActivity.setWindowFlag;
import static com.example.magang.database.DatabaseHelper.COL_PASSWORD;
import static com.example.magang.database.DatabaseHelper.COL_USERNAME;
import static com.example.magang.database.DatabaseHelper.TABLE_USER;

import android.content.ContentValues;
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
        db = dbHelper.getReadableDatabase();

        session = new SessionManager(getApplicationContext());

        txtUsername = findViewById(R.id.email);
        txtPasswordLama = findViewById(R.id.pass_lama);
        txtPasswordBaru = findViewById(R.id.pass_baru);
        btnReset = findViewById(R.id.reset_password);


        btnReset = (Button) findViewById(R.id.reset_password);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                db = dbHelper.getWritableDatabase();
                System.out.print("Password" +txtPasswordBaru.getText().toString());

                ContentValues values = new ContentValues();
                values.put(COL_PASSWORD, txtPasswordBaru.getText().toString());
                db.update(TABLE_USER,values,"username=?",new String[]{txtUsername.getText().toString()});

                Toast.makeText(getApplicationContext(), "Password Berhasil di Reset", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}