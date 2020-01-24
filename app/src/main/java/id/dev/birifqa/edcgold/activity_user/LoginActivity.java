package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_admin.AdminHomeActivity;
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

    private AppCompatButton buttonForgot, buttonRegister, buttonLogin;
    private TextInputEditText etUsername, etPassword;
    private EditText etBrainkey;
    private View view;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById();
        onAction();
        session();
    }

    private void findViewById(){
        view = getWindow().getDecorView().getRootView();
        dialog = new SpotsDialog.Builder().setContext(LoginActivity.this).build();

        buttonForgot = findViewById(R.id.button_forgot);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etBrainkey = findViewById(R.id.et_brainkey);
        buttonRegister = findViewById(R.id.button_daftar);
        buttonLogin = findViewById(R.id.btn_login);

    }

    private void onAction(){
        buttonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ForgotPasswordActivity.class));
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), RegisterActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                onImeiCheckPermission();
            }
        });
    }

    private void session(){
        Session session = new Session(getApplicationContext());
        try{
            if(!session.get("token").equals("null")){

                if (session.get("roles_name").equals("admin")){
                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                } else if (session.get("roles_name").equals("superadmin")){
                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else if (session.get("roles_name").equals("member")){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }

            }
        }catch (Exception e){

        }
    }

    private void login(){
        if (etUsername.getText().toString().isEmpty()){
            etUsername.setError("Harus Diisi !!");
        }
        if (etPassword.getText().toString().isEmpty()){
            etPassword.setError("Harus Diisi !!");
        }

        if (!etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty() && !etBrainkey.getText().toString().isEmpty()){
            Call<ResponseBody> call = ParamReq.reqLogin(etUsername.getText().toString(), etPassword.getText().toString(), etBrainkey.getText().toString(), LoginActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleLogin(response.body().string(), LoginActivity.this);
                        if (handle) {
                            dialog.dismiss();
                            LoginActivity.this.finish();

                        } else {
                            dialog.dismiss();
                            showDialog();
                        }

                    } catch (Exception e) {
                        dialog.dismiss();

                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dialog.dismiss();

                    Api.retryDialog(LoginActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(LoginActivity.this, call, cBack, false, "Loading");

        } else {
            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                    Helper.constraintLayout(R.id.notif_error, view).setVisibility(View.VISIBLE);
//                    Helper.setText(R.id.text_error, view, "Email or Password must not be empty!!!");
        }
    }

    private void onImeiCheckPermission(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_PHONE_STATE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            login();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void showDialog(){
        final Dialog dialog1 = new Dialog(LoginActivity.this);
        dialog1.setContentView(R.layout.dialog_login);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton btnYes;

        btnYes = dialog1.findViewById(R.id.btn_yes);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog1.dismiss();
            }
        });

        dialog1.show();
    }
}
