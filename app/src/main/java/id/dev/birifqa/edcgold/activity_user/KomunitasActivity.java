package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.KomunitasAdapter;
import id.dev.birifqa.edcgold.model.KomunitasModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KomunitasActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Callback<ResponseBody> cBack;
    private KomunitasAdapter komunitasAdapter;
    private ArrayList<KomunitasModel> komunitasModels;
    private AlertDialog dialog;
    private RecyclerView recyclerKomunitas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komunitas);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(KomunitasActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        recyclerKomunitas = findViewById(R.id.rv_komunitas);

    }

    private void onAction(){
        komunitasModels = new ArrayList<>();
        komunitasAdapter = new KomunitasAdapter(KomunitasActivity.this, komunitasModels);
        recyclerKomunitas.setLayoutManager(new LinearLayoutManager(KomunitasActivity.this, RecyclerView.VERTICAL, false));
        recyclerKomunitas.setItemAnimator(new DefaultItemAnimator());
        recyclerKomunitas.setAdapter(komunitasAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                KomunitasActivity.this.finish();
            }
        });

        getKomunitas();
    }

    private void getKomunitas(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestKomunitasPost(Session.get("token"),KomunitasActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONArray dataArray = dataObject.getJSONArray("data");

                        if (dataArray.length() > 0){
                            for (int i = 0; i < dataArray.length() ; i++){
                                KomunitasModel model = new KomunitasModel();
                                model.setId(dataArray.getJSONObject(i).getString("id"));
                                model.setSlug(dataArray.getJSONObject(i).getString("slug"));
                                model.setKomunitas_id(dataArray.getJSONObject(i).getString("komunitas_id"));
                                model.setKomunitas_name(dataArray.getJSONObject(i).getString("komunitas_name"));
                                model.setTitle(dataArray.getJSONObject(i).getString("title"));
                                model.setContent(dataArray.getJSONObject(i).getString("content"));
                                model.setImages(dataArray.getJSONObject(i).getString("images"));
                                model.setDate(dataArray.getJSONObject(i).getJSONObject("date").getString("date"));

                                komunitasModels.add(model);
                            }
                            dialog.dismiss();
                            komunitasAdapter.notifyDataSetChanged();
                        }
                    } else {
                        dialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        Api.enqueueWithRetry(KomunitasActivity.this, call, cBack, false, "Loading");
    }
}
