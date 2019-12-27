package id.dev.birifqa.edcgold.fragment_admin;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
public class FragmentAdminUserClosed extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminUserAdapter userAdapter;
    private ArrayList<AdminUserModel> userModels;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;
    private EditText etCari;

    public FragmentAdminUserClosed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_user_closed, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        etCari = view.findViewById(R.id.et_cari);
        recyclerView = view.findViewById(R.id.rv_user_closed);
    }

    private void onAction(){
        Api.adminUserClosedModels = new ArrayList<>();
        userAdapter = new AdminUserAdapter(getActivity(), Api.adminUserClosedModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);


//        etCari.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String textSearch = s.toString();
//                textSearch=textSearch.toLowerCase();
//                List<AdminUserModel> newList=new ArrayList<>();
//                if (textSearch.isEmpty()){
//                    newList = Api.adminUserAllUserModels;
//                }else {
//                    for (AdminUserModel adminUserModel : Api.adminUserAllUserModels){
//                        String title=adminUserModel.getUserId().toLowerCase();
//                        if (title.contains(textSearch)){
//                            newList.add(adminUserModel);
//                        }
//                    }
//                }
//                userAdapter.setFilter(newList);
//            }
//        });
        getData();
    }

    private void getData(){
        Api.adminUserClosedModels.clear();

        dialog.show();
        Call<ResponseBody> call = ParamReq.requestUserList(Session.get("token"), "0", "1", "", getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleUserListClose(response.body().string(), "4", getActivity());
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
