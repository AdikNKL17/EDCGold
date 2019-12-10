package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminNominalTopupAdapter;
import id.dev.birifqa.edcgold.model.NominalTopupModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPengaturanTopupActivity extends AppCompatActivity {

    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;
    private Toolbar toolbar;
    private EditText etNominal;
    private AppCompatButton btnSimpan;

    private RecyclerView recyclerView;
    private AdminNominalTopupAdapter nominalTopupAdapter;
    private ArrayList<NominalTopupModel> nominalTopupModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pengaturan_topup);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminPengaturanTopupActivity.this).build();
        toolbar = findViewById(R.id.toolbar);
        etNominal = findViewById(R.id.et_nominal);
        btnSimpan = findViewById(R.id.btn_simpan);
        recyclerView = findViewById(R.id.rv_nominal_topup);

    }

    private void onAction(){
        nominalTopupModels = new ArrayList<>();
        nominalTopupAdapter = new AdminNominalTopupAdapter(AdminPengaturanTopupActivity.this, nominalTopupModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminPengaturanTopupActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(nominalTopupAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminPengaturanTopupActivity.this.finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNominal.getText().toString().isEmpty()){
                    updateTopup();
                } else {
                    Toast.makeText(AdminPengaturanTopupActivity.this, "Harap diisi nominal topup", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getNominalTopup();
    }

    private void getNominalTopup(){
        dialog.show();
        nominalTopupModels.clear();
        Call<ResponseBody> call = ParamReq.requestNominalTopup(Session.get("token"),AdminPengaturanTopupActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < dataArray.length();i++){
                            NominalTopupModel model = new NominalTopupModel();
                            model.setId(dataArray.getJSONObject(i).getString("id"));
                            model.setLabel(dataArray.getJSONObject(i).getString("label"));
                            model.setNominal(dataArray.getJSONObject(i).getString("nominal"));
                            model.setCreated_at(dataArray.getJSONObject(i).getString("created_at"));
                            model.setUpdated_at(dataArray.getJSONObject(i).getString("updated_at"));

                            nominalTopupModels.add(model);
                        }
                        nominalTopupAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AdminPengaturanTopupActivity.this, "Gagal mengambil nominal topup", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminPengaturanTopupActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminPengaturanTopupActivity.this, call, cBack, false, "Loading");
    }

    private void updateTopup(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.updateTopup(Session.get("token"), etNominal.getText().toString(),AdminPengaturanTopupActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        getNominalTopup();
                        Toast.makeText(AdminPengaturanTopupActivity.this, "Nominal topup berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AdminPengaturanTopupActivity.this, "Gagal update nominal topup", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminPengaturanTopupActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminPengaturanTopupActivity.this, call, cBack, false, "Loading");
    }
}
