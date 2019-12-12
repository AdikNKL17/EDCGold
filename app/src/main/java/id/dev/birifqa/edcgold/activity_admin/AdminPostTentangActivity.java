package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

public class AdminPostTentangActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText etPostTentang;
    private AppCompatButton btnSimpan;
    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post_tentang);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminPostTentangActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        etPostTentang = findViewById(R.id.et_post_tentang);
        btnSimpan = findViewById(R.id.btn_simpan);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminPostTentangActivity.this.finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etPostTentang.getText().toString().equals("")){
                    postUpdate();
                }
            }
        });
    }

    private void postUpdate(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.updatePost(Session.get("token"), etPostTentang.getText().toString(), AdminPostTentangActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        etPostTentang.setText("");
                        Toast.makeText(AdminPostTentangActivity.this, "Update Tentang Berhasil", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        etPostTentang.setText("");
                        Toast.makeText(AdminPostTentangActivity.this, "Gagal Update Tentang", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminPostTentangActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminPostTentangActivity.this, call, cBack, false, "Loading");
    }
}
