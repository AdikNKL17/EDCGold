package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.Helper;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterSuksesActivity extends AppCompatActivity {

    private AppCompatButton btnMulai;
    private TextView tvName;
    private View view;
    private Session session;
    private Callback<ResponseBody> cBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sukses);
        view = getWindow().getDecorView().getRootView();

        tvName = findViewById(R.id.tv_name);
        btnMulai = findViewById(R.id.btn_mulai);

        Intent getIntent = getIntent();
        tvName.setText(getIntent.getStringExtra("NAME"));

        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterSuksesActivity.this, LoginActivity.class));
            }
        });

    }
}
