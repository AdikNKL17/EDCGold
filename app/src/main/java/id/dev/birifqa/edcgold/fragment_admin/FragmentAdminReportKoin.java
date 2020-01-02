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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminReportKoinAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminReportKoinModel;
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
public class FragmentAdminReportKoin extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminReportKoinAdapter koinAdapter;
    private ArrayList<AdminReportKoinModel> adminReportKoinModels;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    private TextView tvKoinServer;

    public FragmentAdminReportKoin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_report_koin, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        recyclerView = view.findViewById(R.id.rv_report_koin);
        tvKoinServer = view.findViewById(R.id.tv_koin_server);
    }

    private void onAction(){
        adminReportKoinModels = new ArrayList<>();
        koinAdapter = new AdminReportKoinAdapter(getActivity(), adminReportKoinModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(koinAdapter);

        getData();
    }

    private void getData(){
        adminReportKoinModels.clear();

        dialog.show();
        Call<ResponseBody> call = ParamReq.requestReportKoin(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONArray historyArray = dataObject.getJSONArray("history");

                        tvKoinServer.setText(dataObject.getString("coin_server"));

                        for (int i = 0; i < historyArray.length(); i++){
                            AdminReportKoinModel model = new AdminReportKoinModel();
                            model.setCoin(historyArray.getJSONObject(i).getString("coin")+" EDCG ( "+historyArray.getJSONObject(i).getString("balance") +")");
                            model.setDate(historyArray.getJSONObject(i).getString("date"));
                            if (historyArray.getJSONObject(i).getString("status").equals("in")){
                                model.setStatus("1");
                            } else {
                                model.setStatus("2");
                            }
                            adminReportKoinModels.add(model);
                        }

                        koinAdapter.notifyDataSetChanged();
                    } else {
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
