package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Helper;

public class RegisterSuksesActivity extends AppCompatActivity {

    AppCompatButton btnMulai;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sukses);
        view = getWindow().getDecorView().getRootView();

        btnMulai = findViewById(R.id.btn_mulai);

        Intent getIntent = getIntent();

        Helper.setText(R.id.tv_name, view, getIntent.getStringExtra("NAME"));

        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterSuksesActivity.this, LoginActivity.class));
            }
        });
    }
}
