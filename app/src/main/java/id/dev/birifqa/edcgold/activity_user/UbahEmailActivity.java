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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahEmailActivity extends AppCompatActivity {

    TextInputEditText etEmailLama, etEmailBaru, etKonfirmasi;
    AppCompatButton btnKonfirmasi;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_email);

        toolbar = findViewById(R.id.toolbar);
        etEmailLama = findViewById(R.id.et_email_lama);
        etEmailBaru = findViewById(R.id.et_email_baru);
        etKonfirmasi = findViewById(R.id.konfirmasi);
        btnKonfirmasi = findViewById(R.id.btn_confirm);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeEmail();
            }
        });
    }

    private void changeEmail(){
        if (!etEmailLama.getText().toString().isEmpty() && !etEmailBaru.getText().toString().isEmpty() && !etKonfirmasi.getText().toString().isEmpty()){
            Call<ResponseBody> call = ParamReq.changeEmail("", etEmailLama.getText().toString(), etEmailBaru.getText().toString(), etKonfirmasi.getText().toString(), UbahEmailActivity.this);
            Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangeEmail(response.body().string(), UbahEmailActivity.this);
                        if (handle) {
                            onBackPressed();
                            Toast.makeText(UbahEmailActivity.this, "Ubah Email Berhasil!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Api.mProgressDialog.dismiss();
                            Toast.makeText(UbahEmailActivity.this, "Change Email Failed, Check again later !!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            };

            Api.enqueueWithRetry(UbahEmailActivity.this, call, cBack, true, "Loading");
        } else {
            Toast.makeText(UbahEmailActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }
}
