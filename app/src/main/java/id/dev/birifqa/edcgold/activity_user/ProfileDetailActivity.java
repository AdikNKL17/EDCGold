package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import id.dev.birifqa.edcgold.R;

public class ProfileDetailActivity extends AppCompatActivity {

    TextInputEditText etId, etName, etEmail, etPhone, etAddress;
    TextView tvName;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        toolbar = findViewById(R.id.toolbar);
        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);
        tvName = findViewById(R.id.tv_name);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent getIntent = getIntent();
        etId.setText(getIntent.getStringExtra("ID"));
        etName.setText(getIntent.getStringExtra("NAME"));
        etEmail.setText(getIntent.getStringExtra("EMAIL"));
        etPhone.setText(getIntent.getStringExtra("PHONE"));
        etAddress.setText(getIntent.getStringExtra("ADDRESS"));
        tvName.setText(getIntent.getStringExtra("NAME"));

    }
}
