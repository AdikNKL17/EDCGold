package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    private Callback<ResponseBody> cBack;
    private Toolbar toolbar;
    private LinearLayout btnEditNama, btnEditPhone, btnEditEmail, btnEditAlamat, btnEditRekening, btnEditPassword;
    private TextView tvName, tvCoin;
    private ImageView imgFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViewById();
        onAction();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        btnEditNama = findViewById(R.id.btn_edit_nama);
        btnEditPhone = findViewById(R.id.btn_edit_phone);
        btnEditEmail = findViewById(R.id.btn_edit_email);
        btnEditAlamat = findViewById(R.id.btn_edit_alamat);
        btnEditRekening = findViewById(R.id.btn_edit_rekening);
        btnEditPassword = findViewById(R.id.btn_edit_password);
        imgFoto = findViewById(R.id.img_foto);
        tvName = findViewById(R.id.tv_name);
        tvCoin = findViewById(R.id.tv_coin);
    }

    private void onAction(){
        getUserDetail();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                SettingActivity.this.finish();
            }
        });

        btnEditNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, UbahUsernameActivity.class));
            }
        });

        btnEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, UbahNomorActivity.class));
            }
        });

        btnEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, UbahEmailActivity.class));
            }
        });

        btnEditAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, UbahAlamatActivity.class));
            }
        });

        btnEditRekening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, RekeningBankActivity.class));
            }
        });

        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, UbahPasswordActivity.class));
            }
        });
    }

    private void getUserDetail(){
        Call<ResponseBody> call = ParamReq.requestUserDetail(Session.get("token"), SettingActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleProfileSetting(response.body().string(), tvName, tvCoin, imgFoto, SettingActivity.this);
                    if (handle) {

                    } else {
                        Api.mProgressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(SettingActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(SettingActivity.this, call, cBack, false, "Loading");
    }

}
