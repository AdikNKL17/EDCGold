package id.dev.birifqa.edcgold.fragment_user;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.SewaCloudActivity;
import id.dev.birifqa.edcgold.adapter.UserMiningAdapter;
import id.dev.birifqa.edcgold.model.HistoryMiningModel;
import id.dev.birifqa.edcgold.model.UserMiningModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserMining extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private UserMiningAdapter miningAdapter;
    private Callback<ResponseBody> cBack;
    private ArrayList<HistoryMiningModel> miningModels;
    private AlertDialog dialog;
    private TextView tvPoint, tvRemainingTime, tvRemainingAging, tvAging, tvDate, tvPersen;
    private ImageView imgCoin, imgNoData;

    private ConstraintLayout btnSewaCloud, lyMining;
    private LinearLayout lyHistoryMining;

    public FragmentUserMining() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_mining, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        recyclerView = view.findViewById(R.id.rv_user_mining);
        btnSewaCloud = view.findViewById(R.id.btn_sewa_cloud);
        tvPoint = view.findViewById(R.id.tv_point);
        tvRemainingTime = view.findViewById(R.id.tv_remaining_time);
        tvRemainingAging = view.findViewById(R.id.tv_remaining_aging);
        tvAging = view.findViewById(R.id.tv_aging);
        tvDate = view.findViewById(R.id.tv_date);
        tvPersen = view.findViewById(R.id.tv_persen);
        imgCoin = view.findViewById(R.id.img_coin);
        imgNoData = view.findViewById(R.id.img_nodata);
        lyMining = view.findViewById(R.id.ly_mining);
        lyHistoryMining = view.findViewById(R.id.ly_history_mining);
    }

    private void onAction(){
        miningModels = new ArrayList<>();
        miningAdapter = new UserMiningAdapter(getActivity(), miningModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(miningAdapter);

        btnSewaCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SewaCloudActivity.class));
            }
        });

        getMiningDetail1();
        getMiningHistory();
    }

    private void getMiningDetail1(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestMyRental(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        lyHistoryMining.setVisibility(View.VISIBLE);
                        lyMining.setVisibility(View.VISIBLE);
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONObject rentalObject = dataObject.getJSONObject("rental");
                        JSONObject userObject = rentalObject.getJSONObject("user");
                        JSONObject agingObject = dataObject.getJSONObject("aging");
                        JSONObject pointObject = userObject.getJSONObject("point");

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
                        tvPoint.setText(point);
                        tvRemainingTime.setText(remainingTime1+" min");
                        tvRemainingAging.setText(remainingAging+" min");
                        tvAging.setText(agingObject.getString("result"));
                        tvDate.setText(agingObject.getString("date"));

                        int agingProses = Integer.parseInt(remainingAging1) * 60 + Integer.parseInt(remainingAging2);
                        if (agingProses >= 0 && agingProses <= 24 ){
                            tvPersen.setText("1.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/1.png").into(imgCoin);
                        } else if (agingProses > 24 && agingProses <= 48){
                            tvPersen.setText("3.2%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/2.png").into(imgCoin);
                        } else if (agingProses > 48 && agingProses <= 72){
                            tvPersen.setText("4.8%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/3.png").into(imgCoin);
                        } else if (agingProses > 72 && agingProses <= 96){
                            tvPersen.setText("6.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/4.png").into(imgCoin);
                        } else if (agingProses > 96 && agingProses <= 120){
                            tvPersen.setText("8%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/5.png").into(imgCoin);
                        } else if (agingProses > 120 && agingProses <= 144){
                            tvPersen.setText("9.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/6.png").into(imgCoin);
                        } else if (agingProses > 144 && agingProses <= 168){
                            tvPersen.setText("11.2%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/7.png").into(imgCoin);
                        } else if (agingProses > 168 && agingProses <= 192){
                            tvPersen.setText("12.8%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/8.png").into(imgCoin);
                        } else if (agingProses > 192 && agingProses <= 216){
                            tvPersen.setText("14.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/9.png").into(imgCoin);
                        } else if (agingProses > 216 && agingProses <= 240){
                            tvPersen.setText("16%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/10.png").into(imgCoin);
                        } else if (agingProses > 240 && agingProses <= 264){
                            tvPersen.setText("17.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/11.png").into(imgCoin);
                        } else if (agingProses > 264 && agingProses <= 288){
                            tvPersen.setText("19.2%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/12.png").into(imgCoin);
                        } else if (agingProses > 288 && agingProses <= 312){
                            tvPersen.setText("22.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/13.png").into(imgCoin);
                        } else if (agingProses > 312 && agingProses <= 336){
                            tvPersen.setText("24%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/14.png").into(imgCoin);
                        } else if (agingProses > 336 && agingProses <= 360){
                            tvPersen.setText("25.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/15.png").into(imgCoin);
                        } else if (agingProses > 360 && agingProses <= 384){
                            tvPersen.setText("27.2%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/16.png").into(imgCoin);
                        } else if (agingProses > 384 && agingProses <= 408){
                            tvPersen.setText("28.8%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/17.png").into(imgCoin);
                        } else if (agingProses > 408 && agingProses <= 432){
                            tvPersen.setText("30.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/18.png").into(imgCoin);
                        } else if (agingProses > 432 && agingProses <= 456){
                            tvPersen.setText("32%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/19.png").into(imgCoin);
                        } else if (agingProses > 456 && agingProses <= 480){
                            tvPersen.setText("33.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/20.png").into(imgCoin);
                        } else if (agingProses > 480 && agingProses <= 504){
                            tvPersen.setText("35.2%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/21.png").into(imgCoin);
                        } else if (agingProses > 504 && agingProses <= 528){
                            tvPersen.setText("36.8%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/22.png").into(imgCoin);
                        } else if (agingProses > 528 && agingProses <= 552){
                            tvPersen.setText("38.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/23.png").into(imgCoin);
                        } else if (agingProses > 552 && agingProses <= 576){
                            tvPersen.setText("40%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/24.png").into(imgCoin);
                        } else if (agingProses > 576 && agingProses <= 600){
                            tvPersen.setText("41.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/25.png").into(imgCoin);
                        } else if (agingProses > 600 && agingProses <= 624){
                            tvPersen.setText("43.2%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/26.png").into(imgCoin);
                        } else if (agingProses > 624 && agingProses <= 648){
                            tvPersen.setText("44.8%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/27.png").into(imgCoin);
                        } else if (agingProses > 648 && agingProses <= 672){
                            tvPersen.setText("46.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/28.png").into(imgCoin);
                        } else if (agingProses > 672 && agingProses <= 696){
                            tvPersen.setText("48%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/29.png").into(imgCoin);
                        } else if (agingProses > 696 && agingProses <= 720){
                            tvPersen.setText("49.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/30.png").into(imgCoin);
                        } else if (agingProses > 720 && agingProses <= 744){
                            tvPersen.setText("54.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/31.png").into(imgCoin);
                        } else if (agingProses > 744 && agingProses <= 768){
                            tvPersen.setText("56%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/32.png").into(imgCoin);
                        } else if (agingProses > 768 && agingProses <= 792){
                            tvPersen.setText("57.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/33.png").into(imgCoin);
                        } else if (agingProses > 792 && agingProses <= 816){
                            tvPersen.setText("59.2%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/34.png").into(imgCoin);
                        } else if (agingProses > 816 && agingProses <= 840){
                            tvPersen.setText("60.8%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/35.png").into(imgCoin);
                        } else if (agingProses > 840 && agingProses <= 864){
                            tvPersen.setText("62.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/36.png").into(imgCoin);
                        } else if (agingProses > 864 && agingProses <= 888){
                            tvPersen.setText("64%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/37.png").into(imgCoin);
                        } else if (agingProses > 888 && agingProses <= 912){
                            tvPersen.setText("65.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/38.png").into(imgCoin);
                        } else if (agingProses > 912 && agingProses <= 936){
                            tvPersen.setText("67.2%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/39.png").into(imgCoin);
                        } else if (agingProses > 936 && agingProses <= 960){
                            tvPersen.setText("68.8%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/40.png").into(imgCoin);
                        } else if (agingProses > 960 && agingProses <= 984){
                            tvPersen.setText("70.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/41.png").into(imgCoin);
                        } else if (agingProses > 984 && agingProses <= 1008){
                            tvPersen.setText("72%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/42.png").into(imgCoin);
                        } else if (agingProses > 1008 && agingProses <= 1036){
                            tvPersen.setText("73.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/43.png").into(imgCoin);
                        } else if (agingProses > 1036 && agingProses <= 1056){
                            tvPersen.setText("75.2%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/44.png").into(imgCoin);
                        } else if (agingProses > 1056 && agingProses <= 1080){
                            tvPersen.setText("76.8%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/45.png").into(imgCoin);
                        } else if (agingProses > 1080 && agingProses <= 1104){
                            tvPersen.setText("78.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/46.png").into(imgCoin);
                        } else if (agingProses > 1104 && agingProses <= 1128){
                            tvPersen.setText("80%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/47.png").into(imgCoin);
                        } else if (agingProses > 1128 && agingProses <= 1152){
                            tvPersen.setText("81.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/48.png").into(imgCoin);
                        } else if (agingProses > 1152 && agingProses <= 1176){
                            tvPersen.setText("83.2%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/49.png").into(imgCoin);
                        } else if (agingProses > 1176 && agingProses <= 1200){
                            tvPersen.setText("84.8%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/50.png").into(imgCoin);
                        } else if (agingProses > 1200 && agingProses <= 1224){
                            tvPersen.setText("86.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/51.png").into(imgCoin);
                        } else if (agingProses > 1224 && agingProses <= 1248){
                            tvPersen.setText("88%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/52.png").into(imgCoin);
                        } else if (agingProses > 1248 && agingProses <= 1272){
                            tvPersen.setText("89.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/53.png").into(imgCoin);
                        } else if (agingProses > 1272 && agingProses <= 1296){
                            tvPersen.setText("91.2%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/54.png").into(imgCoin);
                        } else if (agingProses > 1296 && agingProses <= 1320){
                            tvPersen.setText("92.8%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/55.png").into(imgCoin);
                        } else if (agingProses > 1320 && agingProses <= 1344){
                            tvPersen.setText("94.4%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/56.png").into(imgCoin);
                        } else if (agingProses > 1344 && agingProses <= 1368){
                            tvPersen.setText("96%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/57.png").into(imgCoin);
                        } else if (agingProses > 1368 && agingProses <= 1392){
                            tvPersen.setText("97.6%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/58.png").into(imgCoin);
                        } else if (agingProses > 1392 && agingProses <= 1416){
                            tvPersen.setText("99%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/59.png").into(imgCoin);
                        } else if (agingProses > 1416 && agingProses <= 1440){
                            tvPersen.setText("100%");
                            Glide.with(getActivity()).load("http://45.77.252.55/assets/edc_coin/60.png").into(imgCoin);
                        }
                        dialog.dismiss();
                    } else {
                        lyHistoryMining.setVisibility(View.INVISIBLE);
                        lyMining.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(getActivity(), call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(getActivity(), call, cBack, false, "Loading");
    }

    private void getMiningHistory(){
        dialog.show();
        miningModels.clear();
        Call<ResponseBody> call = ParamReq.requestMiningHistory(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        if (dataArray.length() > 0){
                            imgNoData.setVisibility(View.GONE);
                            for (int i=0; i < dataArray.length() ; i++){
                                HistoryMiningModel model = new HistoryMiningModel();
                                model.setId(dataArray.getJSONObject(i).getString("id"));
                                model.setUser_id(dataArray.getJSONObject(i).getString("user_id"));
                                model.setSewa_mining_id(dataArray.getJSONObject(i).getString("sewa_mining_id"));
                                model.setCoin_balance(dataArray.getJSONObject(i).getString("coin_balance"));
                                model.setAging_result(dataArray.getJSONObject(i).getString("aging_result"));
                                model.setDays_to(dataArray.getJSONObject(i).getString("days_to"));
                                model.setCreated_at(dataArray.getJSONObject(i).getString("created_at"));
                                model.setUpdated_at(dataArray.getJSONObject(i).getString("updated_at"));

                                miningModels.add(model);
                            }
                            miningAdapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    } else {
                        lyHistoryMining.setVisibility(View.INVISIBLE);
                        lyMining.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(getActivity(), call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(getActivity(), call, cBack, false, "Loading");
    }


}
