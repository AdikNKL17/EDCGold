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
import id.dev.birifqa.edcgold.adapter.AdminHistoryAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminHistoryModel;
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
public class FragmentAdminHistory extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminHistoryAdapter historyAdapter;
    private ArrayList<AdminHistoryModel> historyModels;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    public FragmentAdminHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_history, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        recyclerView = view.findViewById(R.id.rv_history);
    }

    private void onAction(){
        historyModels = new ArrayList<>();
        historyAdapter = new AdminHistoryAdapter(getActivity(), historyModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyAdapter);

        getData();
    }

    private void getData(){
        historyModels.clear();

        dialog.show();
        Call<ResponseBody> call = ParamReq.requestTransactionHistoryAll(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        if (dataArray.length() > 0){
                            for (int i =0; i < dataArray.length(); i++){
                                AdminHistoryModel model = new AdminHistoryModel();
                                model.setStatus(dataArray.getJSONObject(i).getString("description"));
                                model.setDate(dataArray.getJSONObject(i).getString("created_at"));

                                historyModels.add(model);
                            }
                            historyAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else{
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Tidak bisa mengambil data", Toast.LENGTH_SHORT).show();
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
