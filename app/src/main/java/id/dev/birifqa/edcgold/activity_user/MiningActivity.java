package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.fragment_user.MiningAktifFragment;

public class MiningActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mining);

        findViewById();
        onAction();

    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);

    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                MiningActivity.this.finish();
            }
        });

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_mining, new MiningAktifFragment());
        ft.commit();
    }
}
