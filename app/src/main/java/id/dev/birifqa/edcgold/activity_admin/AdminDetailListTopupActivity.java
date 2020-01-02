package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Helper;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDetailListTopupActivity extends AppCompatActivity {

    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;
    private Toolbar toolbar;
    private TextView tvName, tvUserId, tvTransactionCode, tvCoin, tvDate, tvApproveDate, tvTransfer, tvBankName, tvBankNumber, tvAtasNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_list_topup);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminDetailListTopupActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        tvName = findViewById(R.id.tv_name);
        tvUserId = findViewById(R.id.tv_id_user);
        tvTransactionCode = findViewById(R.id.tv_transaction_code);
        tvCoin = findViewById(R.id.tv_coin);
        tvDate = findViewById(R.id.tv_date);
        tvApproveDate = findViewById(R.id.tv_approve_date);
        tvTransfer = findViewById(R.id.tv_transfer);
        tvBankName = findViewById(R.id.tv_nama_bank);
        tvBankNumber = findViewById(R.id.tv_no_rekening);
        tvAtasNama = findViewById(R.id.tv_atas_nama);
    }

    private void onAction(){
        Intent getIntent =getIntent();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminDetailListTopupActivity.this.finish();
            }
        });

        getData(getIntent.getStringExtra("ID"));
    }

    private void getData(String id){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestDetailKoinTopup(Session.get("token"), id, AdminDetailListTopupActivity.this);
        cBack = new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        tvName.setText(dataObject.getString("name"));
                        tvUserId.setText("ID - "+dataObject.getString("userid"));
                        tvTransactionCode.setText(": "+dataObject.getString("code"));
                        tvDate.setText(": "+dataObject.getString("date"));
                        tvCoin.setText(": "+dataObject.getString("coin")+" Coin EDCG");
                        tvApproveDate.setText(": "+dataObject.getString("approve_date"));
                        tvTransfer.setText(": "+Helper.getNumberFormatCurrency(Integer.parseInt(dataObject.getString("transfer_amount"))));
                        tvBankName.setText(": "+dataObject.getString("bank_name"));
                        tvBankNumber.setText(": "+dataObject.getString("bank_number"));
                        tvAtasNama.setText(": "+dataObject.getString("account_name"));


                    } else {
                        dialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailListTopupActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailListTopupActivity.this, call, cBack, false, "Loading");
    }
}
