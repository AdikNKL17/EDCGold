package id.dev.birifqa.edcgold.fragment_admin;


import android.app.AlertDialog;
import android.content.Intent;
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
import id.dev.birifqa.edcgold.activity_admin.AdminPengaturanSewaActivity;
import id.dev.birifqa.edcgold.adapter.AdminSewaMiningAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminSewaMiningModel;
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
public class FragmentAdminSewaMining extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminSewaMiningAdapter sewaMiningAdapter;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    private ImageView btnSetting;

    public FragmentAdminSewaMining() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_sewa_mining, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        recyclerView = view.findViewById(R.id.rv_sewa_mining);
        btnSetting = view.findViewById(R.id.btn_setting);
    }

    private void onAction(){
        Api.adminSewaMiningModels = new ArrayList<>();
        sewaMiningAdapter = new AdminSewaMiningAdapter(getActivity(), Api.adminSewaMiningModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sewaMiningAdapter);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminPengaturanSewaActivity.class));
            }
        });

        getData();
    }

    private void getData(){
        Api.adminSewaMiningModels.clear();

        dialog.show();
        Call<ResponseBody> call = ParamReq.requestRentalList(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleRentalList(response.body().string(), getActivity());
                    if (handle) {
                        dialog.dismiss();
                        sewaMiningAdapter.notifyDataSetChanged();
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
