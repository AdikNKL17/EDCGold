package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiActivity extends AppCompatActivity {

    private EditText etVerification;
    private Button btnVerification;
    private TextView btnResendVerification;
    private Callback<ResponseBody> cBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);

        findViewById();
        onAction();
    }

    private void findViewById(){
        etVerification = findViewById(R.id.et_kode_verifikasi);
        btnVerification = findViewById(R.id.btn_verifikasi);
        btnResendVerification = findViewById(R.id.btn_resend_verifikasi);
    }

    private void onAction(){
        btnVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail();
            }
        });

        btnResendVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendChangeEmail();
            }
        });
    }

    private void changeEmail(){
        Intent getIntent = getIntent();
        if (!getIntent.getStringExtra("OLD_EMAIL").isEmpty() && !getIntent.getStringExtra("NEW_EMAIL").isEmpty() && !getIntent.getStringExtra("CONFIRMATION").isEmpty() && !etVerification.getText().toString().isEmpty()){
            Call<ResponseBody> call = ParamReq.changeEmail(Session.get("token"), etVerification.getText().toString(), getIntent.getStringExtra("OLD_EMAIL"), getIntent.getStringExtra("NEW_EMAIL"), getIntent.getStringExtra("CONFIRMATION"), VerifikasiActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangeEmail(response.body().string(), VerifikasiActivity.this);
                        if (handle) {
                            Session.save("email", getIntent.getStringExtra("NEW_EMAIL"));
                            startActivity(new Intent(VerifikasiActivity.this, GantiEmailSukses.class));
                        } else {
                            Api.mProgressDialog.dismiss();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(VerifikasiActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(VerifikasiActivity.this, call, cBack, true, "Loading");
        } else {
            Toast.makeText(VerifikasiActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resendChangeEmail(){
        Intent getIntent = getIntent();
        if (!getIntent.getStringExtra("OLD_EMAIL").isEmpty() && !getIntent.getStringExtra("NEW_EMAIL").isEmpty() && !getIntent.getStringExtra("CONFIRMATION").isEmpty()){
            Call<ResponseBody> call = ParamReq.changeEmailRequest(Session.get("token"), getIntent.getStringExtra("OLD_EMAIL"), getIntent.getStringExtra("NEW_EMAIL"), getIntent.getStringExtra("CONFIRMATION"), VerifikasiActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangeEmail(response.body().string(), VerifikasiActivity.this);
                        if (handle) {

                        } else {
                            Toast.makeText(VerifikasiActivity.this, "Gagal resend !!!", Toast.LENGTH_SHORT).show();
                            Api.mProgressDialog.dismiss();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(VerifikasiActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(VerifikasiActivity.this, call, cBack, true, "Loading");
        } else {
            Toast.makeText(VerifikasiActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }
}
