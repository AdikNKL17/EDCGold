package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class AdminPengaturanSewaActivity extends AppCompatActivity {

    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;
    private Toolbar toolbar;
    private EditText etNominal, etLamaSewa, etNamaBank, etNoRekening, etNamaPemilik;
    private AppCompatButton btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pengaturan_sewa);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminPengaturanSewaActivity.this).build();
        toolbar = findViewById(R.id.toolbar);
        etNominal = findViewById(R.id.et_nominal);
        etLamaSewa = findViewById(R.id.et_lama_sewa);
        etNamaBank = findViewById(R.id.et_nama_bank);
        etNoRekening = findViewById(R.id.et_no_rekening);
        etNamaPemilik = findViewById(R.id.et_nama_pemilik);
        btnSimpan = findViewById(R.id.btn_simpan);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminPengaturanSewaActivity.this.finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNominal.getText().toString().isEmpty() || !etLamaSewa.getText().toString().isEmpty() ||
                        !etNamaBank.getText().toString().isEmpty() || !etNoRekening.getText().toString().isEmpty() ||
                        !etNamaPemilik.getText().toString().isEmpty()){
                    updateRate();
                } else {
                    Toast.makeText(AdminPengaturanSewaActivity.this, "Harap diisi semua", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateRate(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.updateRental(Session.get("token"), etNominal.getText().toString(), etLamaSewa.getText().toString(),
                etNamaBank.getText().toString(), etNoRekening.getText().toString(), etNamaPemilik.getText().toString(),AdminPengaturanSewaActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        onBackPressed();
                        AdminPengaturanSewaActivity.this.finish();
                        Toast.makeText(AdminPengaturanSewaActivity.this, "Rental berhasil update", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AdminPengaturanSewaActivity.this, "Gagal update rental", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminPengaturanSewaActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminPengaturanSewaActivity.this, call, cBack, false, "Loading");
    }
}
