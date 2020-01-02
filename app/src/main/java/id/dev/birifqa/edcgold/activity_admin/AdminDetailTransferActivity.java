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
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDetailTransferActivity extends AppCompatActivity {

    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;
    private Toolbar toolbar;
    private TextView tvSendName, tvSendUserId, tvReceiveName, tvReceiveUserId, tvTransactionCode, tvCoin, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_transfer);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminDetailTransferActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        tvSendName = findViewById(R.id.tv_send_name);
        tvSendUserId = findViewById(R.id.tv_send_userid);
        tvReceiveName = findViewById(R.id.tv_receive_name);
        tvReceiveUserId = findViewById(R.id.tv_receive_userid);
        tvTransactionCode = findViewById(R.id.tv_transaction_code);
        tvCoin = findViewById(R.id.tv_coin);
        tvDate = findViewById(R.id.tv_date);
    }

    private void onAction(){
        Intent getIntent = getIntent();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminDetailTransferActivity.this.finish();
            }
        });

        getData(getIntent.getStringExtra("ID"));
    }

    private void getData(String id){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestDetailKoinTransfer(Session.get("token"), id, AdminDetailTransferActivity.this);
        cBack = new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        tvSendName.setText(": "+dataObject.getString("send_name"));
                        tvSendUserId.setText(": "+dataObject.getString("send_userid"));
                        tvReceiveName.setText(": "+dataObject.getString("receive_name"));
                        tvReceiveUserId.setText(": "+dataObject.getString("receive_userid"));
                        tvTransactionCode.setText(": "+dataObject.getString("code"));
                        tvCoin.setText(": "+dataObject.getString("coin")+" Coin EDCG");
                        tvDate.setText(": "+dataObject.getString("date"));
                    } else {
                        dialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailTransferActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailTransferActivity.this, call, cBack, false, "Loading");
    }
}
