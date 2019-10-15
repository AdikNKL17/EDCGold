package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import id.dev.birifqa.edcgold.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView bgSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViewById();
        onAction();

    }

    private void findViewById(){
        bgSplash = findViewById(R.id.bg_splash);
    }

    private void onAction(){
        RequestOptions myOptions = new RequestOptions()
                .fitCenter() // or centerCrop
                .override(600, 1200);

        Glide.with(SplashActivity.this)
                .asBitmap()
                .apply(myOptions)
                .load(getImage("bg_splash"))
                .into(bgSplash);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, 3000L); //3000 L = 3 detik
    }

    public int getImage(String imageName) {

        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

        return drawableResourceId;
    }
}
