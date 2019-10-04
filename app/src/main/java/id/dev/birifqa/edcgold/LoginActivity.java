package id.dev.birifqa.edcgold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton buttonForgot, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        buttonForgot = findViewById(R.id.button_forgot);
        buttonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ForgotPasswordActivity.class));
            }
        });

        buttonRegister = findViewById(R.id.button_daftar);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), RegisterActivity.class));
            }
        });
    }
}
