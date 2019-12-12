package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKomunitasActivity extends AppCompatActivity {
    private TextView tvKomunitas, tvTitle, tvDate, tvContent;
    private ImageView imgFoto;
    private AlertDialog dialog;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_komunitas);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(DetailKomunitasActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        tvKomunitas = findViewById(R.id.tv_nama_komunitas);
        tvTitle = findViewById(R.id.tv_title);
        tvDate = findViewById(R.id.tv_date);
        tvContent = findViewById(R.id.tv_content);
        imgFoto = findViewById(R.id.img_foto);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                DetailKomunitasActivity.this.finish();
            }
        });

        getKomunitas();
    }

    private void getKomunitas(){
        dialog.show();
        Intent getIntent = getIntent();
        Call<ResponseBody> call = ParamReq.requestKomunitasPostDetail(Session.get("token"), getIntent.getStringExtra("ID_KOMUNITAS_POST"),DetailKomunitasActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        JSONObject dataObject = jsonObject.getJSONObject("data");

                        tvKomunitas.setText(dataObject.getString("komunitas_name"));
                        tvTitle.setText(dataObject.getString("title"));
                        tvDate.setText(dataObject.getJSONObject("date").getString("date"));
                        tvContent.setText(dataObject.getString("content"));

                        if (!dataObject.getString("images").equals("")){
                            Glide.with(imgFoto).load(dataObject.getString("images")).into(imgFoto);
                        } else {
                            imgFoto.setVisibility(View.GONE);
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
        Api.enqueueWithRetry(DetailKomunitasActivity.this, call, cBack, false, "Loading");
    }
}
