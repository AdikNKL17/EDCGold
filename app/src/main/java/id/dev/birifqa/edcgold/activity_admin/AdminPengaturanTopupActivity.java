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

public class AdminPengaturanTopupActivity extends AppCompatActivity {

    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;
    private Toolbar toolbar;
    private EditText etNominal;
    private AppCompatButton btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pengaturan_topup);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminPengaturanTopupActivity.this).build();
        toolbar = findViewById(R.id.toolbar);
        etNominal = findViewById(R.id.et_nominal);
        btnSimpan = findViewById(R.id.btn_simpan);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminPengaturanTopupActivity.this.finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNominal.getText().toString().isEmpty()){
                    updateTopup();
                } else {
                    Toast.makeText(AdminPengaturanTopupActivity.this, "Harap diisi nominal topup", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateTopup(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.updateTopup(Session.get("token"), etNominal.getText().toString(),AdminPengaturanTopupActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        onBackPressed();
                        AdminPengaturanTopupActivity.this.finish();
                        Toast.makeText(AdminPengaturanTopupActivity.this, "Rental berhasil update", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AdminPengaturanTopupActivity.this, "Gagal update nominal topup", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminPengaturanTopupActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminPengaturanTopupActivity.this, call, cBack, false, "Loading");
    }
}
