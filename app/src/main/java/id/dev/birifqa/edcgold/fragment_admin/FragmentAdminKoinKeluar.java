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

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminReportAktifitasAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminReportAktifitasModel;
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
public class FragmentAdminKoinKeluar extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminReportAktifitasAdapter aktifitasAdapter;
    private ArrayList<AdminReportAktifitasModel> aktifitasModels;
    private TextInputEditText etCari;

    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    public FragmentAdminKoinKeluar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_koin_keluar, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();
        etCari = view.findViewById(R.id.et_cari);
        recyclerView = view.findViewById(R.id.rv_koin_keluar);
    }

    private void onAction(){
        aktifitasModels = new ArrayList<>();
        aktifitasAdapter = new AdminReportAktifitasAdapter(getActivity(), aktifitasModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(aktifitasAdapter);


        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String textSearch = s.toString();
                textSearch=textSearch.toLowerCase();
                List<AdminReportAktifitasModel> newList=new ArrayList<>();
                if (textSearch.isEmpty()){
                    newList = aktifitasModels;
                }else {
                    for (AdminReportAktifitasModel adminUserModel : aktifitasModels){
                        String title=adminUserModel.getTransaction_code().toLowerCase();
                        if (title.contains(textSearch)){
                            newList.add(adminUserModel);
                        }
                    }
                }
                aktifitasAdapter.setFilter(newList);
            }
        });
        getData();
    }

    private void getData(){
        aktifitasModels.clear();

        dialog.show();
        Call<ResponseBody> call = ParamReq.requestReportKoinAktifitas(Session.get("token"), "out", getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++){
                            AdminReportAktifitasModel model = new AdminReportAktifitasModel();
                            model.setId(dataArray.getJSONObject(i).getString("id"));
                            model.setTransaction_code(dataArray.getJSONObject(i).getString("transaction_code"));
                            model.setStatus(dataArray.getJSONObject(i).getString("status"));

                            aktifitasModels.add(model);
                        }

                        aktifitasAdapter.notifyDataSetChanged();
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
