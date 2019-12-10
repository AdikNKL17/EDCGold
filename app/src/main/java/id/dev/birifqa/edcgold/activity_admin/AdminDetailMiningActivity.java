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
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminMiningUserAdapter;
import id.dev.birifqa.edcgold.model.HistoryMiningModel;
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
    private ImageView imgCoin, imgNoData;
    private TextView tvNama, tvIdUser, tvStartMining, tvEndMining, tvPoint, tvRemainingTime, tvRemainingAging, tvAging, tvDate, tvPersen;

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
        tvPoint = findViewById(R.id.tv_point);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rv_user_mining);
        tvAging = findViewById(R.id.tv_aging);
        tvDate = findViewById(R.id.tv_date);
        tvPersen = findViewById(R.id.tv_persen);
        imgCoin = findViewById(R.id.img_coin);
        imgNoData = findViewById(R.id.img_nodata);
        tvRemainingTime = findViewById(R.id.tv_remaining_time);
        tvRemainingAging = findViewById(R.id.tv_remaining_aging);
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
                    JSONObject userObject = rentalObject.getJSONObject("user");
                    JSONObject coinObject = userObject.getJSONObject("coin");
                    JSONObject pointObject = userObject.getJSONObject("point");
                    JSONObject agingObject = dataObject.getJSONObject("aging");

                    JSONArray historyObject = rentalObject.getJSONArray("history_minings");

                    String point = pointObject.getString("balance_point");
                    String remainingTime = rentalObject.getString("remaining_time");
                    String[] remainingTimeParts = remainingTime.split(":");
                    String remainingTime1 = remainingTimeParts[0];
                    String remainingTime2 = remainingTimeParts[1];
                    String remainingTime3 = remainingTimeParts[2];

                    String remainingAging = rentalObject.getString("remaining_aging");
                    String[] remainingAgingParts = remainingAging.split(":");
                    String remainingAging1 = remainingAgingParts[0];
                    String remainingAging2 = remainingAgingParts[1];
                    String remainingAging3 = remainingAgingParts[2];

                    tvIdUser.setText(userObject.getString("userid"));
                    tvNama.setText(userObject.getString("name"));
                    tvStartMining.setText(rentalObject.getString("start_mining"));
                    tvEndMining.setText(rentalObject.getString("end_mining"));
                    tvAging.setText(pointObject.getString("balance_point"));
                    tvPoint.setText(point);
                    tvRemainingTime.setText(remainingTime1+" min");
                    tvRemainingAging.setText(remainingAging+" MIN");
                    tvAging.setText(agingObject.getString("result"));
                    tvDate.setText(agingObject.getString("date"));

                    int agingProses = Integer.parseInt(remainingAging1);
                    if (agingProses <= 24 && agingProses >= 18 ){
                        tvPersen.setText("100%");
                        imgCoin.setImageResource(R.drawable.icon_100_mining);
                    } else if (agingProses < 18 && agingProses >= 12){
                        tvPersen.setText("75%");
                        imgCoin.setImageResource(R.drawable.icon_75_mining);
                    } else if (agingProses <= 12 && agingProses >= 6){
                        tvPersen.setText("50%");
                        imgCoin.setImageResource(R.drawable.icon_50_mining);
                    } else if (agingProses <= 5 && agingProses >= 0){
                        tvPersen.setText("25%");
                        imgCoin.setImageResource(R.drawable.icon_25_mining);
                    }

                    if (historyObject.length() > 0){
                        imgNoData.setVisibility(View.GONE);
                        for (int i=0; i < historyObject.length() ; i++){
                            AdminUserMiningHistoryModel model = new AdminUserMiningHistoryModel();
                            model.setId(historyObject.getJSONObject(i).getString("id"));
                            model.setUser_id(historyObject.getJSONObject(i).getString("user_id"));
                            model.setSewa_mining_id(historyObject.getJSONObject(i).getString("sewa_mining_id"));
                            model.setCoin_balance(historyObject.getJSONObject(i).getString("coin_balance"));
                            model.setAging_result(historyObject.getJSONObject(i).getString("aging_result"));
                            model.setDays_to(historyObject.getJSONObject(i).getString("days_to"));
                            model.setCreated_at(historyObject.getJSONObject(i).getString("created_at"));
                            model.setUpdated_at(historyObject.getJSONObject(i).getString("updated_at"));

                            historyModels.add(model);
                        }
                        historyAdapter.notifyDataSetChanged();
                    } else {
                        imgNoData.setVisibility(View.VISIBLE);
                    }
                    dialog.dismiss();

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

}
