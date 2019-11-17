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

public class UbahEmailActivity extends AppCompatActivity {

    private TextInputEditText etEmailLama, etEmailBaru, etKonfirmasi;
    private AppCompatButton btnConfirm;
    private Toolbar toolbar;
    private Callback<ResponseBody> cBack;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_email);


        findViewById();
        onAction();

    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        etEmailLama = findViewById(R.id.et_email_lama);
        etEmailBaru = findViewById(R.id.et_email_baru);
        etKonfirmasi = findViewById(R.id.konfirmasi);
        btnConfirm = findViewById(R.id.btn_confirm);
    }

    private void onAction(){
        etEmailLama.setText(Session.get("email"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeEmail();
            }
        });
    }

    private void changeEmail(){
        if (!etEmailLama.getText().toString().isEmpty() && !etEmailBaru.getText().toString().isEmpty() && !etKonfirmasi.getText().toString().isEmpty()){
            Call<ResponseBody> call = ParamReq.changeEmailRequest(session.get("token"), etEmailLama.getText().toString(), etEmailBaru.getText().toString(), etKonfirmasi.getText().toString(), UbahEmailActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangeEmail(response.body().string(), UbahEmailActivity.this);
                        if (handle) {
                            Intent intent = new Intent(UbahEmailActivity.this, VerifikasiActivity.class);
                            intent.putExtra("OLD_EMAIL", etEmailLama.getText().toString());
                            intent.putExtra("NEW_EMAIL", etEmailBaru.getText().toString());
                            intent.putExtra("CONFIRMATION", etKonfirmasi.getText().toString());
                            startActivity(intent);
                        } else {
                            Api.mProgressDialog.dismiss();
                            Toast.makeText(UbahEmailActivity.this, "Email baru dan email konfirmasi harus sama !!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(UbahEmailActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(UbahEmailActivity.this, call, cBack, true, "Loading");
        } else {
            Toast.makeText(UbahEmailActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }
}
