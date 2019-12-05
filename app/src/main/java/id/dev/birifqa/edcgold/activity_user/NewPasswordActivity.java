package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class NewPasswordActivity extends AppCompatActivity {

    private TextInputEditText etPassword, etPasswordConfirmation;
    private AppCompatButton btnNext;
    private TextView btnResendVerification;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(NewPasswordActivity.this).build();

        etPassword = findViewById(R.id.et_password);
        etPasswordConfirmation = findViewById(R.id.et_password_confirmation);
        btnNext = findViewById(R.id.btn_next);
    }

    private void onAction(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

    }

    private void changePassword(){
        Intent intent = getIntent();
        if (!etPassword.getText().toString().isEmpty() || !etPasswordConfirmation.getText().toString().isEmpty()){
            dialog.show();
            Call<ResponseBody> call = ParamReq.changePassword(Session.get("token"), intent.getStringExtra("VERIFICATION"), etPassword.getText().toString(), etPasswordConfirmation.getText().toString(), NewPasswordActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangePassword(response.body().string(), NewPasswordActivity.this);
                        if (handle) {
                            dialog.dismiss();
                            showDialog();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(NewPasswordActivity.this, "Gagal reset password!!", Toast.LENGTH_SHORT).show();
                            Api.mProgressDialog.dismiss();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(NewPasswordActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(NewPasswordActivity.this, call, cBack, false, "Loading");
        } else {
            Toast.makeText(NewPasswordActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog(){
        final Dialog dialog1 = new Dialog(NewPasswordActivity.this);
        dialog1.setContentView(R.layout.dialog_reset_password);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton btnYes;

        btnYes = dialog1.findViewById(R.id.btn_yes);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewPasswordActivity.this, LoginActivity.class));
                dialog1.dismiss();
            }
        });

        dialog1.show();
    }
}
