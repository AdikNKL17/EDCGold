package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUbahKomunitasActivity extends AppCompatActivity {

    private TextInputEditText etNama, etKetua, etAlamat;
    private AppCompatButton btnSimpan, btnBatal;
    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ubah_komunitas);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminUbahKomunitasActivity.this).build();

        etNama = findViewById(R.id.et_nama_komunitas);
        etKetua = findViewById(R.id.et_ketua_komunitas);
        etAlamat = findViewById(R.id.et_alamat_komunitas);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnBatal = findViewById(R.id.btn_batal);
    }

    private void onAction(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNama.getText().toString().equals("") || !etKetua.getText().toString().equals("") || !etAlamat.getText().toString().equals("")){
                    updateKomunitas();
                } else {
                    Toast.makeText(AdminUbahKomunitasActivity.this, "Harus diisi semua", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateKomunitas(){
        Intent getIntent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.updateKomunitas(Session.get("token"), getIntent.getStringExtra("ID_KOMUNITAS"), etNama.getText().toString(), etKetua.getText().toString(), etAlamat.getText().toString(), AdminUbahKomunitasActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        onBackPressed();
                        AdminUbahKomunitasActivity.this.finish();
                        Toast.makeText(AdminUbahKomunitasActivity.this, "Update Komunitas Berhasil", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AdminUbahKomunitasActivity.this, "Gagal Update Komunitas", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminUbahKomunitasActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminUbahKomunitasActivity.this, call, cBack, false, "Loading");
    }

}
