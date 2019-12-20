package id.dev.birifqa.edcgold.fragment_user;


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
    }

    private void getMiningDetail1(){
        dialog.show();
        miningModels.clear();
        Call<ResponseBody> call = ParamReq.requestMyRental(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
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
                                HistoryMiningModel model = new HistoryMiningModel();
                                model.setId(historyObject.getJSONObject(i).getString("id"));
                                model.setUser_id(historyObject.getJSONObject(i).getString("user_id"));
                                model.setSewa_mining_id(historyObject.getJSONObject(i).getString("sewa_mining_id"));
                                model.setCoin_balance(historyObject.getJSONObject(i).getString("coin_balance"));
                                model.setAging_result(historyObject.getJSONObject(i).getString("aging_result"));
                                model.setDays_to(historyObject.getJSONObject(i).getString("days_to"));
                                model.setCreated_at(historyObject.getJSONObject(i).getString("created_at"));
                                model.setUpdated_at(historyObject.getJSONObject(i).getString("updated_at"));

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
