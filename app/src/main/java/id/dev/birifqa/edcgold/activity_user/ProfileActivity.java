package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Session;

public class ProfileActivity extends AppCompatActivity {

    private ConstraintLayout btnProfil, btnLock, btnPengaturan, btnLogout;
    private TextView tvName;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tv_name);
        btnProfil = findViewById(R.id.btn_profil);
        btnLock = findViewById(R.id.btn_lock);
        btnPengaturan = findViewById(R.id.btn_pengaturan);
        btnLogout = findViewById(R.id.btn_logout);

        Intent getIntent = getIntent();

        tvName.setText(getIntent.getStringExtra("NAME"));

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileDetailActivity.class);
                intent.putExtra("ID", getIntent.getStringExtra("ID"));
                intent.putExtra("NAME", getIntent.getStringExtra("NAME"));
                intent.putExtra("PHONE", getIntent.getStringExtra("PHONE"));
                intent.putExtra("EMAIL", getIntent.getStringExtra("EMAIL"));
                intent.putExtra("ADDRESS", getIntent.getStringExtra("ADDRESS"));
                startActivity(intent);
            }
        });

        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, ProfileLockActivity.class));
            }
        });

        btnPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfilePengaturanActivity.class);
                intent.putExtra("NAME", getIntent.getStringExtra("NAME"));
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.clear();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                ProfileActivity.this.finish();
            }
        });
    }
}
