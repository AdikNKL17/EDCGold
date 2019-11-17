package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

public class UbahRekeningBankActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatButton btnConfirm;
    private TextInputEditText etBankName, etBankNumber, etAccountName;
    private Callback<ResponseBody> cBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_rekening_bank);

        findViewById();
        onAction();

    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        btnConfirm = findViewById(R.id.btn_confirm);
        etBankName = findViewById(R.id.et_nama_bank);
        etBankNumber = findViewById(R.id.et_nomor_bank);
        etAccountName = findViewById(R.id.et_nama_pemilik);
    }

    private void onAction(){
        Intent getIntent = getIntent();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                UbahRekeningBankActivity.this.finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBank(getIntent.getStringExtra("ID"));
            }
        });

        etBankNumber.setText(getIntent.getStringExtra("BANK_NUMBER"));
        etBankName.setText(getIntent.getStringExtra("BANK_NAME"));
        etAccountName.setText(getIntent.getStringExtra("ACCOUNT_NAME"));
    }

    private void changeBank(String id){
        Call<ResponseBody> call = ParamReq.changeBank(Session.get("token"), id, etBankName.getText().toString(), etBankNumber.getText().toString(), etAccountName.getText().toString(), UbahRekeningBankActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    boolean handle = Handle.handleChangeBank(response.body().string(), UbahRekeningBankActivity.this);
                    if (handle) {
                        startActivity(new Intent(UbahRekeningBankActivity.this, GantiRekeningSuksesActivity.class));
                        UbahRekeningBankActivity.this.finish();
                    } else {
                        Api.mProgressDialog.dismiss();
                        Toast.makeText(UbahRekeningBankActivity.this, "Change Bank Failed, Check again later !!!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(UbahRekeningBankActivity.this, call, cBack, 1, false);
            }
        };

        Api.enqueueWithRetry(UbahRekeningBankActivity.this, call, cBack, true, "Loading");
    }
}
