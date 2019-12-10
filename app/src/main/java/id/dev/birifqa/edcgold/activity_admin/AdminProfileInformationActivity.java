package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.LoginActivity;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProfileInformationActivity extends AppCompatActivity {

    private LinearLayout btnUbahTelepon, btnUbahEmail, btnUbahAlamat, btnUbahPassword;
    private Toolbar toolbar;
    private EditText etNama;
    private AppCompatButton btnSimpan;
    private ImageView imgFoto;
    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile_information);

        dialog = new SpotsDialog.Builder().setContext(AdminProfileInformationActivity.this).build();


        toolbar = findViewById(R.id.toolbar);
        btnUbahTelepon = findViewById(R.id.btn_ubah_telepon);
        btnUbahEmail = findViewById(R.id.btn_ubah_email);
        btnUbahAlamat = findViewById(R.id.btn_ubah_alamat);
        btnUbahPassword = findViewById(R.id.btn_ubah_password);
        etNama = findViewById(R.id.et_nama);
        btnSimpan = findViewById(R.id.btn_simpan);
        imgFoto = findViewById(R.id.img_foto);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminProfileInformationActivity.this.finish();
            }
        });

        btnUbahTelepon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfileInformationActivity.this, AdminUbahNomorActivity.class));
            }
        });
        btnUbahEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfileInformationActivity.this, AdminUbahEmailActivity.class));
            }
        });
        btnUbahAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfileInformationActivity.this, AdminUbahAlamatActivity.class));
            }
        });
        btnUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(AdminProfileInformationActivity.this, .class));
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUsername();
            }
        });

        getUserDetail();
    }

    private void getUserDetail(){
        Call<ResponseBody> call = ParamReq.requestUserDetail(Session.get("token"), AdminProfileInformationActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    etNama.setText(dataObject.getString("name"));
                    Glide.with(imgFoto).load(dataObject.getString("avatar"))
                            .apply(RequestOptions.circleCropTransform()).into(imgFoto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminProfileInformationActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminProfileInformationActivity.this, call, cBack, false, "Loading");
    }

    private void changeUsername(){
        if (!etNama.getText().toString().isEmpty()){
            dialog.show();
            Call<ResponseBody> call = ParamReq.changeUsername(Session.get("token"),etNama.getText().toString(), AdminProfileInformationActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangeUsername(response.body().string(), AdminProfileInformationActivity.this);
                        if (handle) {
                            dialog.dismiss();
                            Session.save("name", etNama.getText().toString());
                            Toast.makeText(AdminProfileInformationActivity.this, "Berhasil merubah nama", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(AdminProfileInformationActivity.this, "Change Username Failed, Check again later !!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(AdminProfileInformationActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(AdminProfileInformationActivity.this, call, cBack, false, "Loading");
        } else {
            Toast.makeText(AdminProfileInformationActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }
}
