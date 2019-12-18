package id.dev.birifqa.edcgold.fragment_user;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.DetailWalletReceiveActivity;
import id.dev.birifqa.edcgold.activity_user.WalletReceiveActivity;
import id.dev.birifqa.edcgold.activity_user.WalletSendActivity;
import id.dev.birifqa.edcgold.adapter.UserAktifitasAdapter;
import id.dev.birifqa.edcgold.model.UserAktifitasModel;
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
public class FragmentUserWallet extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private UserAktifitasAdapter aktifitasAdapter;
    private ArrayList<UserAktifitasModel> aktifitasModels;
    private AppCompatButton btnSend, btnReceive;
    private TextView tvName, tvCoin, tvNotif;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;
    private ImageView imgFoto, imgNoData;


    public FragmentUserWallet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_wallet, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        recyclerView = view.findViewById(R.id.rv_aktifitas);
        btnSend = view.findViewById(R.id.btn_send);
        btnReceive = view.findViewById(R.id.btn_receive);
        tvName = view.findViewById(R.id.tv_name);
        tvCoin = view.findViewById(R.id.tv_coin);
        tvNotif = view.findViewById(R.id.tv_notif);
        imgFoto = view.findViewById(R.id.img_foto);
        imgNoData = view.findViewById(R.id.img_nodata);
    }

    private void onAction(){
//        noData.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);

        aktifitasModels = new ArrayList<>();
        aktifitasAdapter = new UserAktifitasAdapter(getActivity(), aktifitasModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(aktifitasAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WalletSendActivity.class));
            }
        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WalletReceiveActivity.class));
            }
        });

        getUserDetail();
        getDataAktifitas();
        getDataReceive();
    }

    private void getUserDetail(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestUserDetail(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject coinObject = dataObject.getJSONObject("coin");
                    tvName.setText(dataObject.getString("name") + " "+dataObject.getString("lastname"));
                    tvCoin.setText(coinObject.getString("amount_coin"));
                    Glide.with(imgFoto).load(dataObject.getString("avatar"))
                            .apply(RequestOptions.circleCropTransform()).into(imgFoto);
                } catch (Exception e) {
                    dialog.dismiss();
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

    private void getDataAktifitas(){
        aktifitasModels.clear();

        Call<ResponseBody> call = ParamReq.requestTransactionHistory(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    if (dataArray.length() > 0){
                        Log.e("DATALENGHT123", String.valueOf(dataArray.length()));
                        for (int j = 0; j< dataArray.length() ; j++){
                            UserAktifitasModel model = new UserAktifitasModel();
                            Log.e("123455", String.valueOf(j));
                            model.setId(dataArray.getJSONObject(j).getString("id"));
                            model.setBuyer_id(dataArray.getJSONObject(j).getString("buyer_id"));
                            model.setSeller_id(dataArray.getJSONObject(j).getString("seller_id"));
                            model.setTransaction_code(dataArray.getJSONObject(j).getString("transaction_code"));
                            model.setMethod(dataArray.getJSONObject(j).getString("method"));
                            model.setNominal(dataArray.getJSONObject(j).getString("nominal"));
                            model.setBalance_point(dataArray.getJSONObject(j).getString("balance_point"));
                            model.setBalance_coin(dataArray.getJSONObject(j).getString("balance_coin"));
                            model.setAmount_point(dataArray.getJSONObject(j).getString("amount_point"));
                            model.setAmount_coin(dataArray.getJSONObject(j).getString("amount_coin"));
                            model.setStatus(dataArray.getJSONObject(j).getString("status"));
                            model.setDescription(dataArray.getJSONObject(j).getString("description"));
                            model.setCreated_at(dataArray.getJSONObject(j).getString("created_at"));
                            model.setUpdated_at(dataArray.getJSONObject(j).getString("updated_at"));
                            model.setType_transfer(dataArray.getJSONObject(j).getString("type_transfer"));

                            aktifitasModels.add(model);
                        }
                        aktifitasAdapter.notifyDataSetChanged();
                        imgNoData.setVisibility(View.GONE);
                    } else{
                        imgNoData.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(getActivity(), call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(getActivity(), call, cBack, false, "Loading");


        aktifitasAdapter.notifyDataSetChanged();
    }
    private void getDataReceive(){
        Call<ResponseBody> call = ParamReq.requestHistoryReceive(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    int value = 0;
                    if (dataArray.length() > 0){
                        for (int i =0; i < dataArray.length();i++){
                            if (dataArray.getJSONObject(i).getString("status").equals("0")){
                                value++;
                            }
                        }
                        if (value > 0){
                            tvNotif.setVisibility(View.VISIBLE);
                            tvNotif.setText(String.valueOf(value));
                        }
                        dialog.dismiss();
                    } else {
                        tvNotif.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
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
