package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.UserAktifitasAdapter;
import id.dev.birifqa.edcgold.adapter.UserReceiveAdapter;
import id.dev.birifqa.edcgold.model.UserAktifitasModel;
import id.dev.birifqa.edcgold.model.UserReceiveModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletReceiveActivity extends AppCompatActivity {

    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;
    private ImageView noData, imgFoto;
    private TextView tvName, tvCoin;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private UserReceiveAdapter receiveAdapter;
    private ArrayList<UserReceiveModel> receiveModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_receive);

        dialog = new SpotsDialog.Builder().setContext(WalletReceiveActivity.this).build();

        recyclerView = findViewById(R.id.rv_receive);
        noData = findViewById(R.id.img_nodata);
        toolbar = findViewById(R.id.toolbar);
        imgFoto = findViewById(R.id.img_foto);
        tvName = findViewById(R.id.tv_name);
        tvCoin = findViewById(R.id.tv_coin);

        receiveModels = new ArrayList<>();
        receiveAdapter = new UserReceiveAdapter(WalletReceiveActivity.this, receiveModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(WalletReceiveActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(receiveAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                WalletReceiveActivity.this.finish();
            }
        });

        Session.save("wallet_receive_transaction_code", "");
        Session.save("wallet_receive_bank_name", "");
        Session.save("wallet_receive_account_name", "");
        Session.save("wallet_receive_bank_number", "");
        Session.save("wallet_receive_transfer_amount", "");
        Session.save("wallet_receive_transfer_date", "");
        Session.save("wallet_receive_images", "");
        Session.save("wallet_receive_balance_coin", "");
        Session.save("wallet_receive_nominal", "");

        getDataReceive();
        getUserDetail();
    }

    @Override
    public void onResume(){
        super.onResume();
        Session.save("wallet_receive_transaction_code", "");
        Session.save("wallet_receive_bank_name", "");
        Session.save("wallet_receive_account_name", "");
        Session.save("wallet_receive_bank_number", "");
        Session.save("wallet_receive_transfer_amount", "");
        Session.save("wallet_receive_transfer_date", "");

        Session.save("wallet_receive_images", "");
    }

    private void getDataReceive(){
        Call<ResponseBody> call = ParamReq.requestHistoryReceive(Session.get("token"), WalletReceiveActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray dataObject = jsonObject.getJSONArray("data");

                    if (dataObject.length() > 0){
                        noData.setVisibility(View.GONE);
                        dialog.dismiss();

                        for (int i =0; i<dataObject.length();i++){
                            UserReceiveModel model = new UserReceiveModel();
                            model.setId(dataObject.getJSONObject(i).getString("id"));
                            model.setBuyer_id(dataObject.getJSONObject(i).getString("buyer_id"));
                            model.setSeller_id(dataObject.getJSONObject(i).getString("seller_id"));
                            model.setTransaction_code(dataObject.getJSONObject(i).getString("transaction_code"));
                            model.setMethod(dataObject.getJSONObject(i).getString("method"));
                            model.setBalance_point(dataObject.getJSONObject(i).getString("balance_point"));
                            model.setBalance_coin(dataObject.getJSONObject(i).getString("balance_coin"));
                            model.setAmount_point(dataObject.getJSONObject(i).getString("amount_point"));
                            model.setAmount_coin(dataObject.getJSONObject(i).getString("amount_coin"));
                            model.setType_transfer(dataObject.getJSONObject(i).getString("status"));
                            model.setDescription(dataObject.getJSONObject(i).getString("description"));
                            model.setStatus(dataObject.getJSONObject(i).getString("status"));
                            model.setNominal(dataObject.getJSONObject(i).getString("nominal"));
                            model.setCreated_at(dataObject.getJSONObject(i).getString("created_at"));

                            receiveModels.add(model);
                        }
                        receiveAdapter.notifyDataSetChanged();
                    } else {
                        noData.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(WalletReceiveActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(WalletReceiveActivity.this, call, cBack, false, "Loading");
    }

    private void getUserDetail(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestUserDetail(Session.get("token"), WalletReceiveActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject coinObject = dataObject.getJSONObject("coin");
                    tvName.setText(dataObject.getString("name") + " "+dataObject.getString("lastname"));
                    tvCoin.setText(coinObject.getString("balance_coin"));
                    Glide.with(imgFoto).load(dataObject.getString("avatar"))
                            .apply(RequestOptions.circleCropTransform()).into(imgFoto);
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(WalletReceiveActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(WalletReceiveActivity.this, call, cBack, false, "Loading");
    }
}
