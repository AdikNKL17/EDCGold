package id.dev.birifqa.edcgold.fragment_user;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.HomeActivity;
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
    private ImageView noData;
    private RecyclerView recyclerView;
    private UserAktifitasAdapter aktifitasAdapter;
    private ArrayList<UserAktifitasModel> aktifitasModels;
    private AppCompatButton btnSend, btnReceive;
    private TextView tvName, tvCoin;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;


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

        noData = view.findViewById(R.id.no_data);
        recyclerView = view.findViewById(R.id.rv_aktifitas);
        btnSend = view.findViewById(R.id.btn_send);
        btnReceive = view.findViewById(R.id.btn_receive);
        tvName = view.findViewById(R.id.tv_name);
        tvCoin = view.findViewById(R.id.tv_coin);
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
                    tvCoin.setText(coinObject.getString("balance_coin"));
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
                    JSONArray dataObject = jsonObject.getJSONArray("data");

                    if (dataObject.length() > 0){
                        for (int i = 0; i<= dataObject.length() ; i++){
                            UserAktifitasModel model = new UserAktifitasModel();
                            model.setId(dataObject.getJSONObject(i).getString("id"));
                            model.setBuyer_id(dataObject.getJSONObject(i).getString("buyer_id"));
                            model.setSeller_id(dataObject.getJSONObject(i).getString("seller_id"));
                            model.setTransaction_code(dataObject.getJSONObject(i).getString("transaction_code"));
                            model.setMethod(dataObject.getJSONObject(i).getString("method"));
                            model.setNominal(dataObject.getJSONObject(i).getString("nominal"));
                            model.setBalance_point(dataObject.getJSONObject(i).getString("balance_point"));
                            model.setBalance_coin(dataObject.getJSONObject(i).getString("balance_coin"));
                            model.setAmount_point(dataObject.getJSONObject(i).getString("amount_point"));
                            model.setAmount_coin(dataObject.getJSONObject(i).getString("amount_coin"));
                            model.setStatus(dataObject.getJSONObject(i).getString("status"));
                            model.setDescription(dataObject.getJSONObject(i).getString("description"));
                            model.setCreated_at(dataObject.getJSONObject(i).getString("created_at"));
                            model.setUpdated_at(dataObject.getJSONObject(i).getString("updated_at"));
                            model.setType_transfer(dataObject.getJSONObject(i).getString("type_transfer"));

                            aktifitasModels.add(model);
                        }
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
                    JSONArray dataObject = jsonObject.getJSONArray("data");

                    if (dataObject.length() > 0){
                        dialog.dismiss();
                    } else {

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
