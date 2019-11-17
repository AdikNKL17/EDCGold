package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatButton btnNext;
    private TextInputEditText etEmail;
    private Callback<ResponseBody> cBack;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        findViewById();
        onAction();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        btnNext = findViewById(R.id.btn_next);
        etEmail = findViewById(R.id.et_email);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                ForgotPasswordActivity.this.finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });
    }

    private void forgotPassword(){
        if (!etEmail.getText().toString().isEmpty()){
            Call<ResponseBody> call = ParamReq.reqForgotPassword(etEmail.getText().toString(), ForgotPasswordActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleForgotPassword(response.body().string(), ForgotPasswordActivity.this);
                        if (handle) {
                            ForgotPasswordActivity.this.finish();
                        } else {
                            Api.mProgressDialog.dismiss();
                            Toast.makeText(ForgotPasswordActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(ForgotPasswordActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(ForgotPasswordActivity.this, call, cBack, true, "Loading");
        }
    }
}
