package id.dev.birifqa.edcgold.fragment_admin;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminWithdrawAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminWithdrawModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminWithdraw extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminWithdrawAdapter withdrawAdapter;
    private ArrayList<AdminWithdrawModel> withdrawModels;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    public FragmentAdminWithdraw() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_withdraw, container, false);

        findViewById();
        onAction();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        getData();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        recyclerView = view.findViewById(R.id.rv_withdraw);
    }

    private void onAction(){
        withdrawModels = new ArrayList<>();
        withdrawAdapter = new AdminWithdrawAdapter(getActivity(), withdrawModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(withdrawAdapter);

    }

    private void getData(){
        dialog.show();
        withdrawModels.clear();
        Call<ResponseBody> call = ParamReq.requestWithdrawList(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        if (dataArray.length() > 0){
                            for (int i = 0; i < dataArray.length() ; i++){
                                AdminWithdrawModel model = new AdminWithdrawModel();
                                model.setId(dataArray.getJSONObject(i).getString("id"));
                                model.setTransaction_code(dataArray.getJSONObject(i).getString("transaction_code"));
                                model.setUserid(dataArray.getJSONObject(i).getString("userid"));
                                model.setName(dataArray.getJSONObject(i).getString("name"));
                                model.setStatus(dataArray.getJSONObject(i).getString("status"));
                                model.setDate(dataArray.getJSONObject(i).getString("date"));

                                withdrawModels.add(model);
                            }
                            withdrawAdapter.notifyDataSetChanged();
                        } else {
                            dialog.dismiss();
                        }
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
