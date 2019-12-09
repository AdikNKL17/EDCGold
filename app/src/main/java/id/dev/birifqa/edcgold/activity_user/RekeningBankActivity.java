package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.ProvinsiAdapter;
import id.dev.birifqa.edcgold.adapter.RekeningBankAdapter;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekeningBankActivity extends AppCompatActivity {

    private RecyclerView recyclerBank;
    private RekeningBankAdapter rekeningBankAdapter;
    private Toolbar toolbar;
    private Callback<ResponseBody> cBack;

    private AppCompatButton btnSimpan, btnBatal;
    private TextInputEditText etNamaBank, etNoRekening, etNamaRekening;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekening_bank);

        findViewById();
        onAction();
    }


    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(RekeningBankActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        recyclerBank = findViewById(R.id.rv_list_rekening);
        btnBatal = findViewById(R.id.btn_batal);
        btnSimpan = findViewById(R.id.btn_simpan);
        etNamaBank = findViewById(R.id.et_nama_bank);
        etNoRekening = findViewById(R.id.et_no_rekening);
        etNamaRekening = findViewById(R.id.et_nama_rekening);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                RekeningBankActivity.this.finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etNamaBank.getText().toString().isEmpty() || !etNoRekening.getText().toString().isEmpty() || !etNamaRekening.getText().toString().isEmpty()){
                    addBank();
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNamaBank.setText("");
                etNamaRekening.setText("");
                etNoRekening.setText("");
            }
        });

        getRekeningBank();
    }

    private void addBank(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.reqAddBank(Session.get("token"), etNamaBank.getText().toString(), etNoRekening.getText().toString(), etNamaRekening.getText().toString(), RekeningBankActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    boolean handle = Handle.handleAddBank(response.body().string(), RekeningBankActivity.this);
                    if (handle) {
                        getRekeningBank();
                        etNamaBank.setText("");
                        etNamaRekening.setText("");
                        etNoRekening.setText("");
                        Toast.makeText(RekeningBankActivity.this, "Add Bank Success!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Api.mProgressDialog.dismiss();
                        Toast.makeText(RekeningBankActivity.this, "Change Bank Failed, Check again later !!!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(RekeningBankActivity.this, call, cBack, 1, false);
            }
        };

        Api.enqueueWithRetry(RekeningBankActivity.this, call, cBack, false, "Loading");
    }

    private void getRekeningBank(){
        dialog.show();
        Api.bankModels = new ArrayList<>();
        rekeningBankAdapter = new RekeningBankAdapter(RekeningBankActivity.this, Api.bankModels);
        recyclerBank.setLayoutManager(new LinearLayoutManager(RekeningBankActivity.this, RecyclerView.VERTICAL, false));
        recyclerBank.setItemAnimator(new DefaultItemAnimator());
        recyclerBank.setAdapter(rekeningBankAdapter);

        Call<ResponseBody> call = ParamReq.requestRekeningBank(Session.get("token"),RekeningBankActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Api.bankModels.clear();
                    boolean handle = Handle.handleGetRekeningBank(response.body().string(), RekeningBankActivity.this);
                    if (handle) {
                        dialog.dismiss();
                        rekeningBankAdapter.notifyDataSetChanged();
                    } else {
                        Api.mProgressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        Api.enqueueWithRetry(RekeningBankActivity.this, call, cBack, false, "Loading");
    }

    @Override
    public void onResume(){
        super.onResume();
        getRekeningBank();
    }
}
