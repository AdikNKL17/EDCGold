package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahNomorActivity extends AppCompatActivity {

    private TextInputEditText etPhoneLama, etPhoneBaru, etKonfirmasi;
    private AppCompatButton btnConfirm;
    private Toolbar toolbar;
    private Callback<ResponseBody> cBack;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_nomor);

        findViewById();
        onAction();

    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        etPhoneLama = findViewById(R.id.et_nomor_lama);
        etPhoneBaru = findViewById(R.id.et_nomor_baru);
        etKonfirmasi = findViewById(R.id.konfirmasi);
        btnConfirm = findViewById(R.id.btn_confirm);

    }

    private void onAction(){
        Intent getIntent = getIntent();
        etPhoneLama.setText(getIntent.getStringExtra("PHONE"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UbahNomorActivity.this, GantiNomorSuksesActivity.class));
            }
        });
    }

    private void changePhone(){
        if (!etPhoneLama.getText().toString().isEmpty() && !etPhoneBaru.getText().toString().isEmpty() && !etKonfirmasi.getText().toString().isEmpty()){
            Call<ResponseBody> call = ParamReq.changePhone(session.get("token"),etPhoneLama.getText().toString(), etPhoneBaru.getText().toString(), etKonfirmasi.getText().toString(), UbahNomorActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangePhone(response.body().string(), UbahNomorActivity.this);
                        if (handle) {
                            onBackPressed();
                            Toast.makeText(UbahNomorActivity.this, "Ubah Nomor Telepon Berhasil!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Api.mProgressDialog.dismiss();
                            Toast.makeText(UbahNomorActivity.this, "Change Phone Number Failed, Check again later !!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(UbahNomorActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(UbahNomorActivity.this, call, cBack, true, "Loading");
        } else {
            Toast.makeText(UbahNomorActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }
}
