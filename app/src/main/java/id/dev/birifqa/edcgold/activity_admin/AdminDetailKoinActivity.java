package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.model.admin.AdminReportAktifitasModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDetailKoinActivity extends AppCompatActivity {

    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;
    private TextView tvName, tvUserId, tvTransactionCode, tvStatus, tvDate, tvCoin, tvBalance;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_koin);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminDetailKoinActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        tvName = findViewById(R.id.tv_name);
        tvUserId = findViewById(R.id.tv_id_user);
        tvTransactionCode = findViewById(R.id.tv_transaction_code);
        tvStatus = findViewById(R.id.tv_status);
        tvDate = findViewById(R.id.tv_date);
        tvCoin = findViewById(R.id.tv_coin);
        tvBalance = findViewById(R.id.tv_balance_coin);
    }

    private void onAction(){
        Intent getIntent = getIntent();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminDetailKoinActivity.this.finish();
            }
        });

        getData(getIntent.getStringExtra("ID"));
    }

    private void getData(String id){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestDetailKoinAktifitas(Session.get("token"), id, AdminDetailKoinActivity.this);
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
                        tvTransactionCode.setText(dataObject.getString("code"));
                        tvStatus.setText(dataObject.getString("status"));
                        tvDate.setText(dataObject.getString("date"));
                        tvCoin.setText(" : "+dataObject.getString("coin")+" Coin EDCG");
                        tvBalance.setText(" : "+dataObject.getString("balance")+" Coin EDCG");

                    } else {
                        dialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailKoinActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailKoinActivity.this, call, cBack, false, "Loading");
    }
}
