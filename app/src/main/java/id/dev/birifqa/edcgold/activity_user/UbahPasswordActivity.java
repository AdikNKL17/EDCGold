package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPasswordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatButton btnConfirm;
    private Callback<ResponseBody> cBack;
    private Session session;
    private TextInputEditText etPasswordLama, etPasswordBaru, etKonfirmasi;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        findViewById();
        onAction();

    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(UbahPasswordActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        btnConfirm = findViewById(R.id.btn_confirm);
        etPasswordLama = findViewById(R.id.et_password_lama);
        etPasswordBaru = findViewById(R.id.et_password_baru);
        etKonfirmasi = findViewById(R.id.konfirmasi);
    }

    private void onAction(){
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                UbahPasswordActivity.this.finish();
            }
        });
    }

    private void changePassword(){
        if (!etPasswordLama.getText().toString().isEmpty() && !etPasswordBaru.getText().toString().isEmpty() && !etKonfirmasi.getText().toString().isEmpty()){
            dialog.show();
            Call<ResponseBody> call = ParamReq.changePasswordRequest(session.get("token"), etPasswordLama.getText().toString(), etPasswordBaru.getText().toString(), etKonfirmasi.getText().toString(), UbahPasswordActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangePassword(response.body().string(), UbahPasswordActivity.this);
                        if (handle) {
                            dialog.dismiss();
                            Intent intent = new Intent(UbahPasswordActivity.this, GantiPasswordSuksesActivity.class);
                            startActivity(intent);
                        } else {
                            dialog.dismiss();
                            Toast.makeText(UbahPasswordActivity.this, "Gagal update password, coba periksa kebali data anda", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(UbahPasswordActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(UbahPasswordActivity.this, call, cBack, false, "Loading");
        } else {
            Toast.makeText(UbahPasswordActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }
}
