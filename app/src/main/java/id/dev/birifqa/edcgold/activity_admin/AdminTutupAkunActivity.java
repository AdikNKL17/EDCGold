package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class AdminTutupAkunActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;
    private EditText etReasonClose;
    private AppCompatButton btnTutup;
    private TextView tvNama;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tutup_akun);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminTutupAkunActivity.this).build();

        tvNama = findViewById(R.id.tv_nama);
        etReasonClose = findViewById(R.id.et_reason_close);
        btnTutup = findViewById(R.id.btn_tutup);
        toolbar = findViewById(R.id.toolbar);
    }

    private void onAction(){

        Intent getIntent = getIntent();

        tvNama.setText(getIntent.getStringExtra("nama_user"));

        btnTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etReasonClose.getText().toString().isEmpty()){
                    updateUser(getIntent.getStringExtra("id_user"),getIntent.getStringExtra("status_active"), getIntent.getStringExtra("type_member"));
                } else {
                    Toast.makeText(AdminTutupAkunActivity.this, "Harap diisi alasannya terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminTutupAkunActivity.this.finish();
            }
        });
    }

    private void updateUser(String id_user, String status_active, String type_member){
        dialog.show();
        Call<ResponseBody> call = ParamReq.updateUser(Session.get("token"), id_user, status_active, type_member, etReasonClose.getText().toString(), AdminTutupAkunActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        onBackPressed();
                        AdminTutupAkunActivity.this.finish();
                        Toast.makeText(AdminTutupAkunActivity.this, "Berhasil Update Data User", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AdminTutupAkunActivity.this, "Gagal Update Data User", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminTutupAkunActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminTutupAkunActivity.this, call, cBack, false, "Loading");
    }
}
