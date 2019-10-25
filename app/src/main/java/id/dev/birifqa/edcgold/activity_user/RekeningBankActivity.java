package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekening_bank);

        findViewById();
        onAction();
    }


    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        recyclerBank = findViewById(R.id.recycler_bank);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                RekeningBankActivity.this.finish();
            }
        });

        getRekeningBank();
    }

    private void getRekeningBank(){
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
}
