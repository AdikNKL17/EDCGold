package id.dev.birifqa.edcgold.activity_user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiPasswordActivity extends AppCompatActivity {

    private EditText etVerification;
    private Button btnVerification;
    private TextView btnResendVerification;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_password);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(VerifikasiPasswordActivity.this).build();

        etVerification = findViewById(R.id.et_kode_verifikasi);
        btnVerification = findViewById(R.id.btn_verifikasi);
        btnResendVerification = findViewById(R.id.btn_resend_verifikasi);
    }

    private void onAction(){
        btnVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationCode();
            }
        });

        btnResendVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode();
            }
        });
    }

    private void verificationCode(){
        if (!etVerification.getText().toString().isEmpty()){
            dialog.show();
            Call<ResponseBody> call = ParamReq.requestVerification(etVerification.getText().toString(), VerifikasiPasswordActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleVerificationEmail(response.body().string(), VerifikasiPasswordActivity.this);
                        if (handle) {
                            dialog.dismiss();
                            Intent intent = new Intent(VerifikasiPasswordActivity.this, NewPasswordActivity.class);
                            intent.putExtra("VERIFICATION", etVerification.getText().toString());
                            startActivity(intent);
                        } else {
                            dialog.dismiss();
                            Toast.makeText(VerifikasiPasswordActivity.this, "Code verifikasi tidak sesuai!!", Toast.LENGTH_SHORT).show();
                            Api.mProgressDialog.dismiss();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(VerifikasiPasswordActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(VerifikasiPasswordActivity.this, call, cBack, false, "Loading");
        } else {
            Toast.makeText(VerifikasiPasswordActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resendVerificationCode(){
        Intent getIntent = getIntent();
        if (!getIntent.getStringExtra("EMAIL").isEmpty()){
            dialog.show();
            Call<ResponseBody> call = ParamReq.reqForgotPassword(getIntent.getStringExtra("EMAIL"), VerifikasiPasswordActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleForgotPassword(response.body().string(), VerifikasiPasswordActivity.this);
                        if (handle) {
                            dialog.dismiss();
                            Toast.makeText(VerifikasiPasswordActivity.this, "Resend Verification Code Success, Please Check Your Email!!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(VerifikasiPasswordActivity.this, "Resend Verification Code Failed", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(VerifikasiPasswordActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(VerifikasiPasswordActivity.this, call, cBack, false, "Loading");
        } else {
            Toast.makeText(VerifikasiPasswordActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }
}
