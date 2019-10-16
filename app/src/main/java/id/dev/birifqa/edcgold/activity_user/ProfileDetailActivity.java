package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDetailActivity extends AppCompatActivity {

    private TextInputEditText etId, etName, etEmail, etPhone, etAddress;
    private TextView tvName;
    private Toolbar toolbar;
    private Session session;
    private Callback<ResponseBody> cBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        findViewById();
        onAction();

        getUserDetail();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);
        tvName = findViewById(R.id.tv_name);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                ProfileDetailActivity.this.finish();
            }
        });
    }

    private void getUserDetail(){
        Call<ResponseBody> call = ParamReq.requestUserDetail(session.get("token"), ProfileDetailActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleProfileDetail(response.body().string(), tvName, etName, etId, etPhone, etEmail, etAddress, ProfileDetailActivity.this);
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
                Api.retryDialog(ProfileDetailActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(ProfileDetailActivity.this, call, cBack, false, "Loading");
    }
}
