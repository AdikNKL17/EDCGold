package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import id.dev.birifqa.edcgold.R;

public class UbahRekeningBankActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatButton btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_rekening_bank);

        findViewById();
        onAction();

    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        btnConfirm = findViewById(R.id.btn_confirm);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                UbahRekeningBankActivity.this.finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UbahRekeningBankActivity.this, GantiRekeningSuksesActivity.class));
            }
        });
    }
}
