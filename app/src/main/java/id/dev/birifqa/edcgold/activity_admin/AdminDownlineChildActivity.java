package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.DownlineChildAdapter;
import id.dev.birifqa.edcgold.model.DownlineModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminDownlineChildActivity extends AppCompatActivity {
    private RecyclerView rvDownline;
    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;
    private DownlineChildAdapter downlineAdapter;
    private ArrayList<DownlineModel> downlineModels;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_downline_child);

        findViewById();
        onAction();
    }

    private void findViewById () {
        dialog = new SpotsDialog.Builder().setContext(AdminDownlineChildActivity.this).build();

        rvDownline = findViewById(R.id.rv_downline);
        toolbar = findViewById(R.id.toolbar);
    }

    private void onAction () {
        downlineModels = new ArrayList<>();
        downlineAdapter = new DownlineChildAdapter(AdminDownlineChildActivity.this, downlineModels);
        rvDownline.setLayoutManager(new LinearLayoutManager(AdminDownlineChildActivity.this, RecyclerView.VERTICAL, false));
        rvDownline.setItemAnimator(new DefaultItemAnimator());
        rvDownline.setAdapter(downlineAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                AdminDownlineChildActivity.this.finish();
            }
        });

        getDownline();
    }
    private void getDownline () {
        dialog.show();
        Intent getIntent = getIntent();
        Call<ResponseBody> call = ParamReq.requestDownline(Session.get("token"), Session.get("userid"), AdminDownlineChildActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONObject uplineObject = dataObject.getJSONObject("upline");
                        JSONArray downlineArray = dataObject.getJSONArray("downline");

                        if (downlineArray.length() > 0) {
                            for (int i = 0; i < downlineArray.length(); i++) {

                                if (downlineArray.getJSONObject(i).getString("userid").equals(getIntent.getStringExtra("idparent"))){
                                    DownlineModel model = new DownlineModel();
                                    model.setUserid(downlineArray.getJSONObject(i).getString("userid"));
                                    model.setName(downlineArray.getJSONObject(i).getString("name"));
                                    model.setUserid_parent(downlineArray.getJSONObject(i).getString("userid_parent"));
                                    model.setLevel(downlineArray.getJSONObject(i).getString("level"));

                                    if (downlineArray.getJSONObject(i).getJSONArray("downline").length() > 0) {
                                        model.setHasDownline("1");
                                    } else {
                                        model.setHasDownline("2");
                                    }
                                    downlineModels.add(model);
                                }

                            }
                            downlineAdapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
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
        Api.enqueueWithRetry(AdminDownlineChildActivity.this, call, cBack, false, "Loading");
    }

}
