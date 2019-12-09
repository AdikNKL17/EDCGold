package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

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

public class UbahUsernameActivity extends AppCompatActivity {

    private TextInputEditText etUsernameLama, etUsernameBaru;
    private AppCompatButton btnConfirm;
    private Toolbar toolbar;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_username);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(UbahUsernameActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        etUsernameLama = findViewById(R.id.et_username_lama);
        etUsernameBaru = findViewById(R.id.et_username_baru);
        btnConfirm = findViewById(R.id.btn_confirm);

    }

    private void onAction(){
        etUsernameLama.setText(Session.get("name"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUsername();
            }
        });
    }

    private void changeUsername(){
        if (!etUsernameBaru.getText().toString().isEmpty()){
            dialog.show();
            Call<ResponseBody> call = ParamReq.changeUsername(Session.get("token"),etUsernameBaru.getText().toString(), UbahUsernameActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangeUsername(response.body().string(), UbahUsernameActivity.this);
                        if (handle) {
                            dialog.dismiss();
                            Session.save("name", etUsernameBaru.getText().toString());
                            startActivity(new Intent(UbahUsernameActivity.this, GantiUsernameSuksesActivity.class));
                        } else {
                            dialog.dismiss();
                            Toast.makeText(UbahUsernameActivity.this, "Change Username Failed, Check again later !!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(UbahUsernameActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(UbahUsernameActivity.this, call, cBack, false, "Loading");
        } else {
            Toast.makeText(UbahUsernameActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }
}
