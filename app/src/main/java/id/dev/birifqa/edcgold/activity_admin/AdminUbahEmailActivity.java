package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.VerifikasiActivity;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUbahEmailActivity extends AppCompatActivity {

    private TextInputEditText etEmailLama, etEmailBaru, etEmailKonfirmasi;
    private AppCompatButton btnSimpan, btnBatal;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ubah_email);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminUbahEmailActivity.this).build();


        etEmailLama = findViewById(R.id.et_email_lama);
        etEmailBaru = findViewById(R.id.et_email_baru);
        etEmailKonfirmasi = findViewById(R.id.et_email_konfirmasi);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnBatal = findViewById(R.id.btn_batal);
    }

    private void onAction(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeEmail();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmailLama.setText("");
                etEmailBaru.setText("");
                etEmailKonfirmasi.setText("");
            }
        });
    }

    private void changeEmail() {
        if (!etEmailLama.getText().toString().isEmpty() && !etEmailBaru.getText().toString().isEmpty() && !etEmailKonfirmasi.getText().toString().isEmpty()) {
            dialog.show();
            Call<ResponseBody> call = ParamReq.changeEmailRequest(Session.get("token"), etEmailLama.getText().toString(), etEmailBaru.getText().toString(), etEmailKonfirmasi.getText().toString(), AdminUbahEmailActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangeEmail(response.body().string(), AdminUbahEmailActivity.this);
                        if (handle) {
                            Intent intent = new Intent(AdminUbahEmailActivity.this, VerifikasiActivity.class);
                            intent.putExtra("OLD_EMAIL", etEmailLama.getText().toString());
                            intent.putExtra("NEW_EMAIL", etEmailBaru.getText().toString());
                            intent.putExtra("CONFIRMATION", etEmailKonfirmasi.getText().toString());
                            startActivity(intent);
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(AdminUbahEmailActivity.this, "Email baru dan email konfirmasi harus sama !!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(AdminUbahEmailActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(AdminUbahEmailActivity.this, call, cBack, false, "Loading");
        } else {
            Toast.makeText(AdminUbahEmailActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }
}
