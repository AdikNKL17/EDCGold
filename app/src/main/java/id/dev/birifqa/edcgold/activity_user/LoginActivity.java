package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.Helper;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private AppCompatButton buttonForgot, buttonRegister;
    private TextInputEditText etUsername, etPassword;
    private View view;
    private Callback<ResponseBody> cBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        view = getWindow().getDecorView().getRootView();

        buttonForgot = findViewById(R.id.button_forgot);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        Session session = new Session(getApplicationContext());
        try{
            if(!session.get("token").equals("null")){
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        }catch (Exception e){

        }

        buttonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ForgotPasswordActivity.class));
            }
        });

        buttonRegister = findViewById(R.id.button_daftar);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), RegisterActivity.class));
            }
        });

        Helper.appCompatButton(R.id.btn_login, view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUsername.getText().toString().isEmpty()){
                    etUsername.setError("Harus Diisi !!");
                }
                if (etPassword.getText().toString().isEmpty()){
                    etPassword.setError("Harus Diisi !!");
                }

                if (!etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
                    Call<ResponseBody> call = ParamReq.reqLogin(etUsername.getText().toString(), etPassword.getText().toString(), LoginActivity.this);
                    cBack = new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {

                                boolean handle = Handle.handleLogin(response.body().string(), LoginActivity.this);
                                if (handle) {
                                    LoginActivity.this.finish();

                                } else {
                                    Api.mProgressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Api.retryDialog(LoginActivity.this, call, cBack, 1, false);
                        }
                    };

                    Api.enqueueWithRetry(LoginActivity.this, call, cBack, true, "Loading");

                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                    Helper.constraintLayout(R.id.notif_error, view).setVisibility(View.VISIBLE);
//                    Helper.setText(R.id.text_error, view, "Email or Password must not be empty!!!");
                }
            }
        });
    }
}
