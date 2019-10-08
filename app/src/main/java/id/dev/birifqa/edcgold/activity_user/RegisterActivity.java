package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.KabupatenAdapter;
import id.dev.birifqa.edcgold.adapter.ProvinsiAdapter;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etNamaDepan, etNamaBelakang, etBOD, etPhone, etEmail, etPassword, etProvinsi, etKabupaten, etKecamatan, etKodepos, etAlamat, etReferral;
    AppCompatButton btnDaftar;
    RadioGroup groupJK;
    RadioButton radioJK;
    String namaDepan, namaBelakang, jk, bod, phone, email, password, referral;

    public static String idProv = "";
    public static String idKab = "";
    public static String idKec = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNamaDepan = findViewById(R.id.et_nama_depan);
        etNamaBelakang = findViewById(R.id.et_nama_belakang);
        etBOD = findViewById(R.id.et_bod);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etProvinsi = findViewById(R.id.et_provinsi);
        etKabupaten = findViewById(R.id.et_kabupaten);
        etKecamatan = findViewById(R.id.et_kecamatan);
        etKodepos = findViewById(R.id.et_kodepos);
        etAlamat = findViewById(R.id.et_alamat);
        etReferral = findViewById(R.id.et_referral);
        groupJK = findViewById(R.id.radioJK);
        btnDaftar = findViewById(R.id.btn_daftar);

        namaDepan = etNamaDepan.getText().toString();
        namaBelakang = etNamaBelakang.getText().toString();
        int selectedJK = groupJK.getCheckedRadioButtonId();
        radioJK = findViewById(selectedJK);
//        jk = radioJK.getText().toString();
        bod = etBOD.getText().toString();
        phone = etPhone.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        referral = etReferral.getText().toString();

        etProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    final Dialog dialog1 = new Dialog(RegisterActivity.this);
                    dialog1.setContentView(R.layout.address_dialog);
                    dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    TextView tv_tittle;
                    EditText et_hint;
                    final ProvinsiAdapter provinsiAdapter;
                    RecyclerView recyclerProvinsi;

                    et_hint = dialog1.findViewById(R.id.et_hint);
                    tv_tittle = dialog1.findViewById(R.id.dialog_title);
                    recyclerProvinsi = dialog1.findViewById(R.id.recycler_address);

                    tv_tittle.setText("Pilih Provinsi");
                    et_hint.setHint("Cari Provinsi");

                    Api.provinsiModels = new ArrayList<>();
                    provinsiAdapter = new ProvinsiAdapter(RegisterActivity.this, Api.provinsiModels, dialog1, etProvinsi);
                    recyclerProvinsi.setLayoutManager(new LinearLayoutManager(RegisterActivity.this, RecyclerView.VERTICAL, false));
                    recyclerProvinsi.setItemAnimator(new DefaultItemAnimator());
                    recyclerProvinsi.setAdapter(provinsiAdapter);

                    Call<ResponseBody> call = ParamReq.requestProvinsi(RegisterActivity.this);
                    Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                Api.provinsiModels.clear();
                                boolean handle = Handle.handleGetProvinsi(response.body().string(), RegisterActivity.this);
                                if (handle) {
                                    provinsiAdapter.notifyDataSetChanged();
                                } else {
                                    Api.mProgressDialog.dismiss();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    };

                    Api.enqueueWithRetry(RegisterActivity.this, call, cBack, true, "Loading");
                    dialog1.show();
            }
        });

        etKabupaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(RegisterActivity.this);
                dialog1.setContentView(R.layout.address_dialog);
                dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView tv_tittle;
                EditText et_hint;
                final KabupatenAdapter kabupatenAdapter;
                RecyclerView recyclerKabupaten;

                et_hint = dialog1.findViewById(R.id.et_hint);
                tv_tittle = dialog1.findViewById(R.id.dialog_title);
                recyclerKabupaten = dialog1.findViewById(R.id.recycler_address);

                tv_tittle.setText("Pilih Kabupaten");
                et_hint.setHint("Cari Kabupaten");

                Api.kabupatenModels = new ArrayList<>();
                kabupatenAdapter = new KabupatenAdapter(RegisterActivity.this, Api.kabupatenModels, dialog1, etKabupaten);
                recyclerKabupaten.setLayoutManager(new LinearLayoutManager(RegisterActivity.this, RecyclerView.VERTICAL, false));
                recyclerKabupaten.setItemAnimator(new DefaultItemAnimator());
                recyclerKabupaten.setAdapter(kabupatenAdapter);

                if (!RegisterActivity.idProv.equals("")){
                    Call<ResponseBody> call = ParamReq.requestKabupaten(RegisterActivity.idProv,RegisterActivity.this);
                    Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                Api.kabupatenModels.clear();
                                boolean handle = Handle.handleGetKabupaten(response.body().string(), RegisterActivity.this);
                                if (handle) {
                                    kabupatenAdapter.notifyDataSetChanged();
                                } else {
                                    Api.mProgressDialog.dismiss();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    };

                    Api.enqueueWithRetry(RegisterActivity.this, call, cBack, true, "Loading");
                    dialog1.show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Silahkan pilih provinsi terlebih dahulu!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!namaDepan.isEmpty() && !namaBelakang.isEmpty() && !jk.isEmpty() && !bod.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !password.isEmpty() && !referral.isEmpty()){
//
//                }
                Log.d("Provinsi", RegisterActivity.idProv);
            }
        });
    }
}
