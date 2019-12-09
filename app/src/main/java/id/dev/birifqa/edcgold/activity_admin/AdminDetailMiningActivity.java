package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminMiningUserAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminUserMiningHistoryModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Helper;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDetailMiningActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminMiningUserAdapter historyAdapter;
    private ArrayList<AdminUserMiningHistoryModel> historyModels;
    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;
    private Toolbar toolbar;
    private TextView tvNama, tvIdUser, tvStartMining, tvEndMining, tvAging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_mining);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminDetailMiningActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        tvNama = findViewById(R.id.tv_nama);
        tvIdUser = findViewById(R.id.tv_id_user);
        tvStartMining = findViewById(R.id.tv_start_mining);
        tvEndMining = findViewById(R.id.tv_end_mining);
        tvAging = findViewById(R.id.tv_aging);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rv_user_mining);
    }

    private void onAction(){
        historyModels = new ArrayList<>();
        historyAdapter = new AdminMiningUserAdapter(AdminDetailMiningActivity.this, historyModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminDetailMiningActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminDetailMiningActivity.this.finish();
            }
        });

        getDetailMining();
        getData();
    }

    private void getDetailMining(){
        Intent intent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestDetailMining(Session.get("token"), intent.getStringExtra("id_mining"), AdminDetailMiningActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject rentalObject = dataObject.getJSONObject("rental");
                    JSONObject agingObject = dataObject.getJSONObject("aging");
                    JSONObject userObject = rentalObject.getJSONObject("user");

                    tvIdUser.setText(userObject.getString("userid"));
                    tvNama.setText(userObject.getString("name"));
                    tvStartMining.setText(rentalObject.getString("start_mining"));
                    tvEndMining.setText(rentalObject.getString("end_mining"));
                    tvAging.setText(agingObject.getString("result"));


                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailMiningActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailMiningActivity.this, call, cBack, false, "Loading");
    }

    private void getData(){
        historyModels.clear();

        AdminUserMiningHistoryModel user1 = new AdminUserMiningHistoryModel();
        user1.setCoin("+ 0.09 EDCG");
        user1.setProses("Proses");
        user1.setDate("24/09/2019");
        historyModels.add(user1);

        AdminUserMiningHistoryModel user2 = new AdminUserMiningHistoryModel();
        user2.setCoin("+ 1.187 EDCG");
        user2.setProses("Sukses");
        user2.setDate("24/09/2019 - 09:00:08 ");
        historyModels.add(user2);

        AdminUserMiningHistoryModel user3 = new AdminUserMiningHistoryModel();
        user3.setCoin("+ 1.187 EDCG");
        user3.setProses("Sukses");
        user3.setDate("24/09/2019 - 09:00:08 ");
        historyModels.add(user3);

        AdminUserMiningHistoryModel user4 = new AdminUserMiningHistoryModel();
        user4.setCoin("+ 1.187 EDCG");
        user4.setProses("Sukses");
        user4.setDate("24/09/2019 - 09:00:08 ");
        historyModels.add(user4);


        historyAdapter.notifyDataSetChanged();
    }
}
