package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import id.dev.birifqa.edcgold.R;

public class ProfilePengaturanActivity extends AppCompatActivity {

    private ConstraintLayout btnChangePhone, btnChangeEmail, btnChangeBank, btnChangeAddress, btnChangePassword;
    private TextView tvName;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pengaturan);

        toolbar = findViewById(R.id.toolbar);
        tvName = findViewById(R.id.tv_name);
        btnChangePhone = findViewById(R.id.btn_change_phone);
        btnChangeEmail = findViewById(R.id.btn_change_email);
        btnChangeBank = findViewById(R.id.btn_change_bank);
        btnChangeAddress = findViewById(R.id.btn_change_address);
        btnChangePassword = findViewById(R.id.btn_change_password);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent getIntent = getIntent();

        tvName.setText(getIntent.getStringExtra("NAME"));

        btnChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilePengaturanActivity.this, UbahNomorActivity.class));
            }
        });

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilePengaturanActivity.this, UbahEmailActivity.class));
            }
        });
    }
}
