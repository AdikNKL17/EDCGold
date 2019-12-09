package id.dev.birifqa.edcgold.fragment_user;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.UserHistoryAdapter;
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
public class FragmentUserHistoryProses extends Fragment {

    private View view;
    private ImageView noData;
    private RecyclerView recyclerView;
    private UserHistoryAdapter historyAdapter;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;


    public FragmentUserHistoryProses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_proses, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        noData = view.findViewById(R.id.no_data);
        recyclerView = view.findViewById(R.id.rv_history_proses);
    }

    private void onAction() {
//        noData.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);
        Api.userHistoryProsesModels = new ArrayList<>();
        Api.userHistoryProsesModels.clear();
        historyAdapter = new UserHistoryAdapter(getActivity(), Api.userHistoryProsesModels, 1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyAdapter);

        getData();
    }

    private void getData(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestTransactionHistory(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleGetTransactionHistory(response.body().string(), "1", getActivity());
                    if (handle) {
                        dialog.dismiss();
                        historyAdapter.notifyDataSetChanged();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Gagal mengambil data history", Toast.LENGTH_SHORT).show();
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
