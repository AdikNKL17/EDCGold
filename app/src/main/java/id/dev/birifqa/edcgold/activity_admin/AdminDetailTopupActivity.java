package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Helper;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDetailTopupActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;
    private TextView tvId, tvNama, tvNominal;
    private AppCompatButton btnTerima, btnTolak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_topup);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminDetailTopupActivity.this).build();

        tvId = findViewById(R.id.tv_id);
        tvNama = findViewById(R.id.tv_name);
        tvNominal = findViewById(R.id.tv_nominal);
        btnTerima = findViewById(R.id.btn_terima);
        btnTolak = findViewById(R.id.btn_tolak);
    }
    private void onAction(){
        getDetailTopup();

        btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topupProcess("1");
            }
        });

        btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topupProcess("2");
            }
        });
    }

    private void topupProcess(String status){
        Intent intent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.topupProcess(Session.get("token"), intent.getStringExtra("id_topup"), status, AdminDetailTopupActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        Toast.makeText(AdminDetailTopupActivity.this, "Berhasil menerima topup", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        AdminDetailTopupActivity.this.finish();
                    } else {
                        Toast.makeText(AdminDetailTopupActivity.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        AdminDetailTopupActivity.this.finish();
                    }

                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailTopupActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailTopupActivity.this, call, cBack, false, "Loading");
    }

    private void getDetailTopup(){
        Intent intent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestDetailTopup(Session.get("token"), intent.getStringExtra("id_topup"), AdminDetailTopupActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    tvId.setText(dataObject.getString("id"));
                    tvNama.setText(dataObject.getString("name"));
                    tvNominal.setText(Helper.getNumberFormatCurrency(Integer.parseInt(dataObject.getString("nominal"))));

                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailTopupActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailTopupActivity.this, call, cBack, false, "Loading");
    }
}
