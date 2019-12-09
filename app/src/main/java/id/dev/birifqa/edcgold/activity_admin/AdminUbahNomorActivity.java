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
import id.dev.birifqa.edcgold.activity_user.GantiNomorSuksesActivity;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUbahNomorActivity extends AppCompatActivity {

    private TextInputEditText etPhoneLama, etPhoneBaru, etPhoneKonfirmasi;
    private AppCompatButton btnSimpan, btnBatal;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ubah_nomor);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminUbahNomorActivity.this).build();


        etPhoneLama = findViewById(R.id.et_phone_lama);
        etPhoneBaru = findViewById(R.id.et_phone_baru);
        etPhoneKonfirmasi = findViewById(R.id.et_phone_konfirmasi);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnBatal = findViewById(R.id.btn_batal);
    }

    private void onAction(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePhone();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPhoneLama.setText("");
                etPhoneBaru.setText("");
                etPhoneKonfirmasi.setText("");
            }
        });
    }

    private void changePhone(){
        if (!etPhoneLama.getText().toString().isEmpty() && !etPhoneBaru.getText().toString().isEmpty() && !etPhoneKonfirmasi.getText().toString().isEmpty()){
            dialog.show();
            Call<ResponseBody> call = ParamReq.changePhone(Session.get("token"),etPhoneLama.getText().toString(), etPhoneBaru.getText().toString(), etPhoneKonfirmasi.getText().toString(), AdminUbahNomorActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangePhone(response.body().string(), AdminUbahNomorActivity.this);
                        if (handle) {
                            dialog.dismiss();
                            Session.save("phone", etPhoneBaru.getText().toString());
                            startActivity(new Intent(AdminUbahNomorActivity.this, GantiNomorSuksesActivity.class));
                        } else {
                            dialog.dismiss();
                            Toast.makeText(AdminUbahNomorActivity.this, "Nomor telepon baru dan nomor konfirmasi harus sama !!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(AdminUbahNomorActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(AdminUbahNomorActivity.this, call, cBack, false, "Loading");
        } else {
            Toast.makeText(AdminUbahNomorActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }
}
