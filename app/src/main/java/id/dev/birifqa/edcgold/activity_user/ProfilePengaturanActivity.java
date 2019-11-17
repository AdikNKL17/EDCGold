package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePengaturanActivity extends AppCompatActivity {

    private ConstraintLayout btnChangePhone, btnChangeEmail, btnChangeBank, btnChangeAddress, btnChangePassword;
    private TextView tvName;
    private Toolbar toolbar;
    private Session session;
    private Callback<ResponseBody> cBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pengaturan);

        findViewById();
        onAction();

        getUserDetail();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        tvName = findViewById(R.id.tv_name);
        btnChangePhone = findViewById(R.id.btn_change_phone);
        btnChangeEmail = findViewById(R.id.btn_change_email);
        btnChangeBank = findViewById(R.id.btn_change_bank);
        btnChangeAddress = findViewById(R.id.btn_change_address);
        btnChangePassword = findViewById(R.id.btn_change_password);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                ProfilePengaturanActivity.this.finish();
            }
        });
    }

    private void getUserDetail(){
        Call<ResponseBody> call = ParamReq.requestUserDetail(session.get("token"), ProfilePengaturanActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleProfilePengaturan(response.body().string(), tvName, btnChangePhone, btnChangeEmail, btnChangeBank, btnChangeAddress, btnChangePassword, ProfilePengaturanActivity.this);
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
                Api.retryDialog(ProfilePengaturanActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(ProfilePengaturanActivity.this, call, cBack, false, "Loading");
    }
}
