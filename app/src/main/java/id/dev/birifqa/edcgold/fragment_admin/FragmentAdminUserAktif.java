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

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminUserAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminUserModel;
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
public class FragmentAdminUserAktif extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminUserAdapter userAdapter;
    private ArrayList<AdminUserModel> userModels;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    public FragmentAdminUserAktif() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_user_aktif, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        recyclerView = view.findViewById(R.id.rv_user_aktif);
    }

    private void onAction(){
        Api.adminUserAktifModels = new ArrayList<>();
        userAdapter = new AdminUserAdapter(getActivity(), Api.adminUserAktifModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);

        getData();
    }

    private void getData(){
        Api.adminUserAktifModels.clear();

        dialog.show();
        Call<ResponseBody> call = ParamReq.requestUserList(Session.get("token"), "0", "0", "", getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleUserList(response.body().string(), "3", getActivity());
                    if (handle) {
                        dialog.dismiss();
                        userAdapter.notifyDataSetChanged();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Gagal mengambil data user", Toast.LENGTH_SHORT).show();
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
