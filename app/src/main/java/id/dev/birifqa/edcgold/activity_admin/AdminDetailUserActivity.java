package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Helper;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDetailUserActivity extends AppCompatActivity {

    private TextView tvNama, tvStatus;
    private TextInputEditText etNama, etId, etReferral, etPhone, etEmail, etAlamat;
    private Spinner spinnerStatus, spinnerType;
    private AppCompatButton btnSimpan;
    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;
    private Toolbar toolbar;

    private String status_active = "";
    private String type_member = "";
    private String reason_close = "";

    private String id_user = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_user);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminDetailUserActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        tvNama = findViewById(R.id.tv_nama);
        tvStatus = findViewById(R.id.tv_status_active);
        etNama = findViewById(R.id.et_nama);
        etId = findViewById(R.id.et_id);
        etReferral = findViewById(R.id.et_referral);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etAlamat = findViewById(R.id.et_alamat);
        spinnerStatus = findViewById(R.id.spinner_status_user);
        spinnerType = findViewById(R.id.spinner_type_user);
        btnSimpan = findViewById(R.id.btn_simpan);

    }
    private void onAction(){
        getDataSpinnerStatus();
        getDataSpinnerType();
        getDetailUser();

        Intent getIntent =getIntent();
        id_user = getIntent.getStringExtra("id_user");

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status_active.isEmpty() && !type_member.isEmpty()){
                    if (status_active.equals("2")){
                        Intent intent = new Intent(AdminDetailUserActivity.this, AdminTutupAkunActivity.class);
                        intent.putExtra("id_user", id_user);
                        intent.putExtra("nama_user", etNama.getText().toString());
                        intent.putExtra("status_active", status_active);
                        intent.putExtra("type_member", type_member);
                        startActivity(intent);
                    } else {
                        updateUser();
                    }
                } else {
                    Toast.makeText(AdminDetailUserActivity.this, "Status Active & Type Member harus dipilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminDetailUserActivity.this.finish();
            }
        });

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status_active = parent.getItemAtPosition(position).toString();

                if (parent.getItemAtPosition(position).toString().equals("Aktif")){
                    status_active = "1";
                } else if (parent.getItemAtPosition(position).toString().equals("Block")){
                    status_active = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("User")){
                    type_member = "1";
                } else if (parent.getItemAtPosition(position).toString().equals("Exchanger")){
                    type_member = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDetailUser(){
        Intent intent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestDetailUser(Session.get("token"), intent.getStringExtra("id_user"), AdminDetailUserActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");


                    tvNama.setText(dataObject.getString("name"));
                    if (dataObject.getString("status_active").equals("1")){
                        tvStatus.setText("Status Akun: Aktif");
                    } else {
                        tvStatus.setText("Status Akun: Tidak Aktif");
                    }
                    etNama.setText(dataObject.getString("name"));
                    etId.setText(dataObject.getString("userid"));
                    etReferral.setText(dataObject.getString("referral_code"));
                    etPhone.setText(dataObject.getString("phone"));
                    etEmail.setText(dataObject.getString("email"));
                    etAlamat.setText(dataObject.getString("address"));

                    status_active = dataObject.getString("status_active");
                    type_member = dataObject.getString("type_member");

                    if (!status_active.isEmpty()){
                        spinnerStatus.setSelection(Integer.parseInt(status_active) - 1);
                    } else {
                        Toast.makeText(AdminDetailUserActivity.this, "Gagal mengambil status aktif", Toast.LENGTH_SHORT).show();
                    }
                    if (!type_member.isEmpty()){
                        spinnerType.setSelection(Integer.parseInt(type_member) - 1);
                    } else {
                        Toast.makeText(AdminDetailUserActivity.this, "Gagal mengambil type member", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailUserActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailUserActivity.this, call, cBack, false, "Loading");
    }

    private void updateUser(){
        Intent intent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.updateUser(Session.get("token"), id_user, status_active, type_member, "", AdminDetailUserActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        onBackPressed();
                        AdminDetailUserActivity.this.finish();
                        Toast.makeText(AdminDetailUserActivity.this, "Berhasil Update Data User", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AdminDetailUserActivity.this, "Gagal Update Data User", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailUserActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailUserActivity.this, call, cBack, false, "Loading");
    }

    private void getDataSpinnerStatus(){
        ArrayList<String> typeLabel = new ArrayList<>();
        typeLabel.clear();
        typeLabel.add("Aktif");
        typeLabel.add("Block");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminDetailUserActivity.this,
                android.R.layout.simple_spinner_dropdown_item, typeLabel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private void getDataSpinnerType(){
        ArrayList<String> typeLabel = new ArrayList<>();
        typeLabel.clear();
        typeLabel.add("User");
        typeLabel.add("Exchanger");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminDetailUserActivity.this,
                android.R.layout.simple_spinner_dropdown_item, typeLabel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
    }
}
