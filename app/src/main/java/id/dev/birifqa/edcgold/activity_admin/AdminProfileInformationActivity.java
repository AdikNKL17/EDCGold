package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import id.dev.birifqa.edcgold.R;

public class AdminProfileInformationActivity extends AppCompatActivity {

    private LinearLayout btnUbahTelepon, btnUbahEmail, btnUbahAlamat, btnUbahPassword;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile_information);

        toolbar = findViewById(R.id.toolbar);
        btnUbahTelepon = findViewById(R.id.btn_ubah_telepon);
        btnUbahEmail = findViewById(R.id.btn_ubah_email);
        btnUbahAlamat = findViewById(R.id.btn_ubah_alamat);
        btnUbahPassword = findViewById(R.id.btn_ubah_password);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminProfileInformationActivity.this.finish();
            }
        });

        btnUbahTelepon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfileInformationActivity.this, AdminUbahNomorActivity.class));
            }
        });
        btnUbahEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfileInformationActivity.this, AdminUbahEmailActivity.class));
            }
        });
        btnUbahAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfileInformationActivity.this, AdminUbahAlamatActivity.class));
            }
        });
        btnUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(AdminProfileInformationActivity.this, .class));
            }
        });
    }
}
