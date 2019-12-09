package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.PagerWalletSendAdapter;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.HeightWrappingViewPager;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;

import org.json.JSONObject;

public class WalletSendActivity extends AppCompatActivity {
    private HeightWrappingViewPager pager;
    private StepperIndicator indicator;
    private ImageView btnBack, btnNext;
    private Callback<ResponseBody> cBack;
    private TextView tvName, tvCoin;
    private AlertDialog dialog;
    private Toolbar toolbar;

    private Integer pagerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_send);

        dialog = new SpotsDialog.Builder().setContext(WalletSendActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        pager = findViewById(R.id.view_pager_wallet_send);
        btnBack = findViewById(R.id.btn_back);
        btnNext = findViewById(R.id.btn_next);
        tvName = findViewById(R.id.tv_name);
        tvCoin = findViewById(R.id.tv_coin);

        pager.setAdapter(new PagerWalletSendAdapter(getSupportFragmentManager()));
        pager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        indicator = findViewById(R.id.stepper);
        indicator.showLabels(false);
        indicator.setViewPager(pager, pager.getAdapter().getCount());

        pagerPosition = 0;

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagerPosition == 0){
                    if (!Session.get("wallet_id_penerima").equals("")){
                        checkUser();
                    }else {
                        Toast.makeText(WalletSendActivity.this, "Harap isi id penerima terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pagerPosition-1, true);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                WalletSendActivity.this.finish();
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    btnBack.setVisibility(View.INVISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    btnBack.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                pagerPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getUserDetail();
    }

    private void checkUser(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.checkUser(Session.get("token"), Session.get("wallet_id_penerima"), WalletSendActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        Session.save("wallet_nama_penerima", dataObject.getString("receive_name"));
                        Session.save("wallet_sale_rate", dataObject.getString("sale_rate"));
                        Session.save("wallet_buy_rate", dataObject.getString("buy_rate"));
                        Session.save("wallet_fee", dataObject.getString("fee"));
                        pager.setCurrentItem(pagerPosition+1, true);
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(WalletSendActivity.this, "The selected userid is invalid", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(WalletSendActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(WalletSendActivity.this, call, cBack, false, "Loading");
    }

    private void getUserDetail(){
        Call<ResponseBody> call = ParamReq.requestUserDetail(Session.get("token"), WalletSendActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject coinObject = dataObject.getJSONObject("coin");
                    tvName.setText(dataObject.getString("name") + " "+dataObject.getString("lastname"));
                    tvCoin.setText(coinObject.getString("balance_coin"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(WalletSendActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(WalletSendActivity.this, call, cBack, false, "Loading");
    }
}
