package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class AdminDetailSewaMiningActivity extends AppCompatActivity {

    private TextView tvNama, tvId, tvTransactionCode, tvDate, tvDescription, tvNamaBank, tvAtasNama, tvNoRekening, tvTransfer;
    private AppCompatButton btnProses;
    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_sewa_mining);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminDetailSewaMiningActivity.this).build();

        tvId = findViewById(R.id.tv_id);
        tvNama = findViewById(R.id.tv_name);
        tvTransactionCode = findViewById(R.id.tv_transaction_code);
        tvDate = findViewById(R.id.tv_date);
        tvDescription = findViewById(R.id.tv_description);
        tvNamaBank = findViewById(R.id.tv_nama_bank);
        tvAtasNama = findViewById(R.id.tv_atas_nama);
        tvNoRekening = findViewById(R.id.tv_no_rekening);
        tvTransfer = findViewById(R.id.tv_transfer);
        btnProses = findViewById(R.id.btn_proses);
    }
    private void onAction(){
        getDetailRental();

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rentalProcess("1");
            }
        });

    }

    private void rentalProcess(String status){
        Intent intent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.rentalProcess(Session.get("token"), intent.getStringExtra("id_rental"), status, AdminDetailSewaMiningActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        Toast.makeText(AdminDetailSewaMiningActivity.this, "Berhasil menerima rental", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        AdminDetailSewaMiningActivity.this.finish();
                    } else {
                        Toast.makeText(AdminDetailSewaMiningActivity.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        AdminDetailSewaMiningActivity.this.finish();
                    }

                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailSewaMiningActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailSewaMiningActivity.this, call, cBack, false, "Loading");
    }

    private void getDetailRental(){
        Intent intent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestDetailRental(Session.get("token"), intent.getStringExtra("id_rental"), AdminDetailSewaMiningActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    tvId.setText(dataObject.getString("id"));
                    tvNama.setText(dataObject.getString("name"));
                    tvTransactionCode.setText(dataObject.getString("transaction_code"));
                    tvDate.setText(dataObject.getString("transaction_date"));
                    tvDescription.setText(dataObject.getString("description"));
                    tvNamaBank.setText(dataObject.getString("bank_name"));
                    tvAtasNama.setText(dataObject.getString("account_name"));
                    tvNoRekening.setText(dataObject.getString("ga ada di api"));
                    tvTransfer.setText(Helper.getNumberFormatCurrency(Integer.parseInt(dataObject.getString("transfer_amount"))));

                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailSewaMiningActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailSewaMiningActivity.this, call, cBack, false, "Loading");
    }
}
