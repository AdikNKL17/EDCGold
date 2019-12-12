package id.dev.birifqa.edcgold.fragment_admin;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminListKomunitasAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminListKomunitasModel;
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
public class FragmentAdminKomunitasKomunitas extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminListKomunitasAdapter komunitasAdapter;
    private ArrayList<AdminListKomunitasModel> komunitasModels;
    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;
    private TextInputEditText etNamaKomunitas, etKetuaKomunitas, etAlamatKomunitas;
    private AppCompatButton btnSimpan, btnBatal;

    public FragmentAdminKomunitasKomunitas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_komunitas_komunitas, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        recyclerView = view.findViewById(R.id.rv_list_komunitas);
        etNamaKomunitas = view.findViewById(R.id.et_nama_komunitas);
        etKetuaKomunitas = view.findViewById(R.id.et_ketua_komunitas);
        etAlamatKomunitas = view.findViewById(R.id.et_alamat_komunitas);
        btnSimpan = view.findViewById(R.id.btn_simpan);
        btnBatal = view.findViewById(R.id.btn_batal);
    }

    private void onAction(){
        komunitasModels = new ArrayList<>();
        komunitasAdapter = new AdminListKomunitasAdapter(getActivity(), komunitasModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(komunitasAdapter);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNamaKomunitas.getText().toString().equals("") || !etKetuaKomunitas.getText().toString().equals("") || !etAlamatKomunitas.getText().toString().equals("")){
                    addKomunitas();
                } else {
                    Toast.makeText(getActivity(), "Harap diisi semua", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNamaKomunitas.setText("");
                etKetuaKomunitas.setText("");
                etAlamatKomunitas.setText("");
            }
        });

        getData();
    }

    private void addKomunitas(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.reqAddKomunitas(Session.get("token"), etNamaKomunitas.getText().toString(), etKetuaKomunitas.getText().toString(), etAlamatKomunitas.getText().toString(), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        etNamaKomunitas.setText("");
                        etKetuaKomunitas.setText("");
                        etAlamatKomunitas.setText("");
                        Toast.makeText(getActivity(), "Tambah Komunitas Berhasil", Toast.LENGTH_SHORT).show();
                        komunitasAdapter.notifyDataSetChanged();
                    } else {
                        dialog.dismiss();
                        etNamaKomunitas.setText("");
                        etKetuaKomunitas.setText("");
                        etAlamatKomunitas.setText("");
                        Toast.makeText(getActivity(), "Gagal Tambah Komunitas Tentang", Toast.LENGTH_SHORT).show();
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

    private void getData(){
        komunitasModels.clear();
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestKomunitas(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray dataArray = dataObject.getJSONArray("data");

                    if (dataArray.length() > 0){
                        dialog.dismiss();
                        for (int i = 0; i < dataArray.length() ; i++){
                            AdminListKomunitasModel model = new AdminListKomunitasModel();
                            model.setId(dataArray.getJSONObject(i).getString("id"));
                            model.setNama_komunitas(dataArray.getJSONObject(i).getString("name"));
                            model.setNama_ketua(dataArray.getJSONObject(i).getString("ketua"));
                            model.setAlamat(dataArray.getJSONObject(i).getString("alamat"));
                            komunitasModels.add(model);
                        }
                        komunitasAdapter.notifyDataSetChanged();
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
