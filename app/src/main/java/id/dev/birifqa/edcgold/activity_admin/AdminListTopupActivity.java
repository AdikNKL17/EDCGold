package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminReportTopupAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminReportTopupModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminListTopupActivity extends AppCompatActivity {

    private View view;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AdminReportTopupAdapter topupAdapter;
    private ArrayList<AdminReportTopupModel> topupModels;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;
    private TextInputEditText etCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_topup);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminListTopupActivity.this).build();
        etCari = findViewById(R.id.et_cari);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rv_list_topup);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminListTopupActivity.this.finish();
            }
        });

        topupModels = new ArrayList<>();
        topupAdapter = new AdminReportTopupAdapter(AdminListTopupActivity.this, topupModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminListTopupActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(topupAdapter);

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
                List<AdminReportTopupModel> newList=new ArrayList<>();
                if (textSearch.isEmpty()){
                    newList = topupModels;
                }else {
                    for (AdminReportTopupModel model : topupModels){
                        String title=model.getTransaction_code().toLowerCase();
                        if (title.contains(textSearch)){
                            newList.add(model);
                        }
                    }
                }
                topupAdapter.setFilter(newList);
            }
        });

        getData();
    }

    private void getData(){
        topupModels.clear();

        dialog.show();
        Call<ResponseBody> call = ParamReq.requestReportTopup(Session.get("token"), AdminListTopupActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++){
                            AdminReportTopupModel model = new AdminReportTopupModel();
                            model.setId(dataArray.getJSONObject(i).getString("id"));
                            model.setTransaction_code(dataArray.getJSONObject(i).getString("transaction_code"));
                            model.setName(dataArray.getJSONObject(i).getString("name"));
                            model.setUserId(dataArray.getJSONObject(i).getString("userid"));

                            topupModels.add(model);
                        }

                        topupAdapter.notifyDataSetChanged();
                    } else {
                        dialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminListTopupActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminListTopupActivity.this, call, cBack, false, "Loading");

    }
}
