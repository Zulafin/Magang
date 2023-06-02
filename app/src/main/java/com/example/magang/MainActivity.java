package com.example.magang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.magang.adapter.AlertDialogManager;
import com.example.magang.session.SessionManager;


public class MainActivity extends AppCompatActivity {

    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    Button btnLogout;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        btnLogout = findViewById(R.id.out);
        btnLogout.setOnClickListener(arg0 -> {
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Anda yakin ingin keluar ?")
                    .setPositiveButton("Ya", (dialog1, which) -> {
                        finish();
                        session.logoutUser();
                    })
                    .setNegativeButton("Tidak", null)
                    .create();
            dialog.show();
        });
    }

    public void profileMenu(View v) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void penerimaanBarang(View v) {
        Intent i = new Intent(this, BarangActivity.class);
        startActivity(i);
    }

    public void historyMenu(View v) {
        Intent i = new Intent(this, RiwayatActivity.class);
        startActivity(i);
    }
}
