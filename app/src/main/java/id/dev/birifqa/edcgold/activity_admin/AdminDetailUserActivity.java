package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

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

public class AdminDetailUserActivity extends AppCompatActivity {

    private TextView tvNama, tvStatus;
    private TextInputEditText etNama, etId, etReferral, etPhone, etEmail, etAlamat;
    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_user);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminDetailUserActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        tvNama = findViewById(R.id.tv_nama);
        tvStatus = findViewById(R.id.tv_status_active);
        etNama = findViewById(R.id.et_nama);
        etId = findViewById(R.id.et_id);
        etReferral = findViewById(R.id.et_referral);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etAlamat = findViewById(R.id.et_alamat);

    }
    private void onAction(){
        getDetailUser();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminDetailUserActivity.this.finish();
            }
        });
    }

    private void getDetailUser(){
        Intent intent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestDetailUser(Session.get("token"), intent.getStringExtra("id_user"), AdminDetailUserActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");


                    tvNama.setText(dataObject.getString("name"));
                    if (dataObject.getString("status_active").equals("1")){
                        tvStatus.setText("Status Akun: Aktif");
                    } else {
                        tvStatus.setText("Status Akun: Tidak Aktif");
                    }
                    etNama.setText(dataObject.getString("name"));
                    etId.setText(dataObject.getString("userid"));
                    etReferral.setText(dataObject.getString("referral_code"));
                    etPhone.setText(dataObject.getString("phone"));
                    etEmail.setText(dataObject.getString("email"));
                    etAlamat.setText(dataObject.getString("address"));

                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailUserActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailUserActivity.this, call, cBack, false, "Loading");
    }
}
