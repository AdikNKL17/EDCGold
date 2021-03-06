package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import id.dev.birifqa.edcgold.R;

public class GantiKataSandiSukses extends AppCompatActivity {

    private AppCompatButton btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_kata_sandi_sukses);

        findViewById();
        onAction();

    }

    private void findViewById(){
        btnHome = findViewById(R.id.btn_home);

    }

    private void onAction(){
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GantiKataSandiSukses.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
