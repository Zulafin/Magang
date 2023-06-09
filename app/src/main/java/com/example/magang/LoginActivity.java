package com.example.magang;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.magang.adapter.AlertDialogManager;
import com.example.magang.database.DatabaseHelper;
import com.example.magang.session.SessionManager;


public class LoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin, btnSignUp, btnReset;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        session = new SessionManager(getApplicationContext());

        txtUsername = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);

        btnLogin = findViewById(R.id.masuk);
        btnSignUp = findViewById(R.id.ke_daftar);
        btnReset = findViewById(R.id.updatepass);

        btnLogin.setOnClickListener(arg0 -> {
            // Get username, password from EditText
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();

            // Check if username, password is filled
            try {
                // Check if username, password is filled
                if (username.trim().length() > 0 && password.trim().length() > 0) {
                    dbHelper.open();

                    if (dbHelper.Login(username, password)) {
                        session.createLoginSession(username);

                        finish();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);

                    } else {
                        alert.showAlertDialog(LoginActivity.this, "Login gagal..", "Email atau Password salah!", false);

                    }
                } else {
                    alert.showAlertDialog(LoginActivity.this, "Login gagal..", "Form tidak boleh kosong!", false);
                }
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        btnSignUp.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(i);
        });

        btnReset.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(), ResetPassword.class);
            startActivity(intent);
        });

    }


    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
