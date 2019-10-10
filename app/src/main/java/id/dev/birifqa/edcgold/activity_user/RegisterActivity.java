package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.KabupatenAdapter;
import id.dev.birifqa.edcgold.adapter.KecamatanAdapter;
import id.dev.birifqa.edcgold.adapter.ProvinsiAdapter;
import id.dev.birifqa.edcgold.model.KabupatenModel;
import id.dev.birifqa.edcgold.model.KecamatanModel;
import id.dev.birifqa.edcgold.model.ProvinsiModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.Helper;
import id.dev.birifqa.edcgold.utils.ParamReq;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextInputEditText etNamaDepan, etNamaBelakang, etBOD, etPhone, etEmail, etPassword, etProvinsi, etKabupaten, etKecamatan, etKodepos, etAlamat, etReferral;
    private AppCompatButton btnDaftar;
    private RadioGroup groupJK;

    private String jk = "";
    public static String idProv = "";
    public static String idKab = "";
    public static String idKec = "";

    private DatePickerDialog datePickerDialog;
    private Callback<ResponseBody> cBack;


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


        groupJK.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_laki:
                        jk = "1";
                        break;
                    case R.id.rb_perempuan:
                        jk = "2";
                        break;
                }
            }
        });

        etBOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        etProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etKabupaten.setText("");
                etKecamatan.setText("");
                idProv="";
                idKab= "";
                idKec="";
                showProvinsiDialog();
            }
        });

        etKabupaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etKecamatan.setText("");
                idKec="";
                showKabupatenDialog();
            }
        });

        etKecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKecamatanDialog();
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etNamaDepan.getText().toString().isEmpty() && !etNamaBelakang.getText().toString().isEmpty() &&
                        !jk.isEmpty() && !etBOD.getText().toString().isEmpty() && !etPhone.getText().toString().isEmpty() &&
                        !etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty() &&
                        !etReferral.getText().toString().isEmpty() && !idProv.isEmpty() &&
                        !idKab.isEmpty() && !idKec.isEmpty() &&
                        !etAlamat.getText().toString().isEmpty() && !etKodepos.getText().toString().isEmpty()){

                    if (etPassword.getText().length() >= 6){
                        requestRegister();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (etNamaDepan.getText().toString().isEmpty()){
                        etNamaDepan.setError("Harus diisi");
                    }
                    if (etNamaBelakang.getText().toString().isEmpty()){
                        etNamaBelakang.setError("Harus diisi");
                    }
                    if (etBOD.getText().toString().isEmpty()){
                        etBOD.setError("Harus diisi");
                    }
                    if (etPhone.getText().toString().isEmpty()){
                        etPhone.setError("Harus diisi");
                    }
                    if (etEmail.getText().toString().isEmpty()){
                        etEmail.setError("Harus diisi");
                    }
                    if (etPassword.getText().toString().isEmpty()){
                        etPassword.setError("Harus diisi");
                    }
                    if (etReferral.getText().toString().isEmpty()){
                        etReferral.setError("Harus diisi");
                    }
                    if (etProvinsi.getText().toString().isEmpty()){
                        etProvinsi.setError("Harus diisi");
                    }
                    if (etKabupaten.getText().toString().isEmpty()){
                        etKabupaten.setError("Harus diisi");
                    }
                    if (etKecamatan.getText().toString().isEmpty()){
                        etKecamatan.setError("Harus diisi");
                    }if (etAlamat.getText().toString().isEmpty()){
                        etAlamat.setError("Harus diisi");
                    }if (etKodepos.getText().toString().isEmpty()){
                        etKodepos.setError("Harus diisi");
                    }
                    Toast.makeText(RegisterActivity.this, "Teeeeeet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestRegister(){
        Call<ResponseBody> call = ParamReq.requestRegister(etNamaDepan.getText().toString(), etNamaBelakang.getText().toString(), jk, etBOD.getText().toString(),
                etPhone.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(), idProv,
                idKab, idKec, etKodepos.getText().toString(), etAlamat.getText().toString(),
                etReferral.getText().toString(), RegisterActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    boolean handle = Handle.handleRequestRegister(response.body().string(), RegisterActivity.this);
                    if (handle) {
                        Intent intent = new Intent(RegisterActivity.this, RegisterSuksesActivity.class);
                        intent.putExtra("NAME", etNamaDepan.getText().toString());
                        startActivity(intent);
                    } else {
                        Api.mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(RegisterActivity.this, call, cBack, 1, false);
            }
        };

        Api.enqueueWithRetry(RegisterActivity.this, call, cBack, true, "Loading");
    }

    private void showDatePicker(){
        Calendar now = Calendar.getInstance();
            /*
            It is recommended to always create a new instance whenever you need to show a Dialog.
            The sample app is reusing them because it is useful when looking for regressions
            during testing
             */
        if (datePickerDialog == null) {
            datePickerDialog = DatePickerDialog.newInstance(
                    RegisterActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        } else {
            datePickerDialog.initialize(
                    RegisterActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }
        datePickerDialog.setThemeDark(true);
        datePickerDialog.dismissOnPause(true);
        datePickerDialog.showYearPickerFirst(true);
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.setTitle("Tanggal Lahir");


        datePickerDialog.setOkColor(getResources().getColor(R.color.colorAccent));
        datePickerDialog.setCancelColor(getResources().getColor(R.color.colorAccent));
        datePickerDialog.setOnCancelListener(dialog -> {
            Log.d("DatePickerDialog", "Dialog was cancelled");
            datePickerDialog = null;
        });

        datePickerDialog.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    private void showProvinsiDialog(){
        final Dialog dialog1 = new Dialog(RegisterActivity.this);
        dialog1.setContentView(R.layout.address_dialog);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_tittle;
        EditText et_hint;
        ImageView btn_close;
        final ProvinsiAdapter provinsiAdapter;
        RecyclerView recyclerProvinsi;

        et_hint = dialog1.findViewById(R.id.et_hint);
        tv_tittle = dialog1.findViewById(R.id.dialog_title);
        btn_close = dialog1.findViewById(R.id.btn_close);
        recyclerProvinsi = dialog1.findViewById(R.id.recycler_address);

        tv_tittle.setText("Pilih Provinsi");
        et_hint.setHint("Cari Provinsi");

        Api.provinsiModels = new ArrayList<>();
        provinsiAdapter = new ProvinsiAdapter(RegisterActivity.this, Api.provinsiModels, dialog1, etProvinsi, "1");
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

        et_hint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String textSearch = editable.toString();
                textSearch=textSearch.toLowerCase();
                List<ProvinsiModel> newList=new ArrayList<>();
                if (textSearch.isEmpty()){
                    newList = Api.provinsiModels;
                }else {
                    for (ProvinsiModel provinsi : Api.provinsiModels){
                        String title=provinsi.getName().toLowerCase();
                        if (title.contains(textSearch)){
                            newList.add(provinsi);
                        }
                    }
                }
                provinsiAdapter.setFilter(newList);
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        Api.enqueueWithRetry(RegisterActivity.this, call, cBack, true, "Loading");
        dialog1.show();
    }

    private void showKabupatenDialog(){
        final Dialog dialog1 = new Dialog(RegisterActivity.this);
        dialog1.setContentView(R.layout.address_dialog);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_tittle;
        EditText et_hint;
        ImageView btn_close;

        final KabupatenAdapter kabupatenAdapter;
        RecyclerView recyclerKabupaten;

        et_hint = dialog1.findViewById(R.id.et_hint);
        tv_tittle = dialog1.findViewById(R.id.dialog_title);
        btn_close = dialog1.findViewById(R.id.btn_close);
        recyclerKabupaten = dialog1.findViewById(R.id.recycler_address);

        tv_tittle.setText("Pilih Kabupaten");
        et_hint.setHint("Cari Kabupaten");

        Api.kabupatenModels = new ArrayList<>();
        kabupatenAdapter = new KabupatenAdapter(RegisterActivity.this, Api.kabupatenModels, dialog1, etKabupaten, "1");
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

            et_hint.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String textSearch = editable.toString();
                    textSearch=textSearch.toLowerCase();
                    List<KabupatenModel> newList=new ArrayList<>();
                    if (textSearch.isEmpty()){
                        newList = Api.kabupatenModels;
                    }else {
                        for (KabupatenModel kabupaten : Api.kabupatenModels){
                            String title=kabupaten.getName().toLowerCase();
                            if (title.contains(textSearch)){
                                newList.add(kabupaten);
                            }
                        }
                    }
                    kabupatenAdapter.setFilter(newList);
                }
            });

            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog1.dismiss();
                }
            });

            Api.enqueueWithRetry(RegisterActivity.this, call, cBack, true, "Loading");
            dialog1.show();
        }else{
            Toast.makeText(RegisterActivity.this, "Silahkan pilih provinsi terlebih dahulu!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showKecamatanDialog(){
        final Dialog dialog1 = new Dialog(RegisterActivity.this);
        dialog1.setContentView(R.layout.address_dialog);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_tittle;
        EditText et_hint;
        ImageView btn_close;
        final KecamatanAdapter kecamatanAdapter;
        RecyclerView recyclerKecamatan;

        et_hint = dialog1.findViewById(R.id.et_hint);
        tv_tittle = dialog1.findViewById(R.id.dialog_title);
        btn_close = dialog1.findViewById(R.id.btn_close);
        recyclerKecamatan = dialog1.findViewById(R.id.recycler_address);

        tv_tittle.setText("Pilih Kecamatan");
        et_hint.setHint("Cari Kecamatan");


        Api.kecamatanModels = new ArrayList<>();
        kecamatanAdapter = new KecamatanAdapter(RegisterActivity.this, Api.kecamatanModels, dialog1, etKecamatan, "1");
        recyclerKecamatan.setLayoutManager(new LinearLayoutManager(RegisterActivity.this, RecyclerView.VERTICAL, false));
        recyclerKecamatan.setItemAnimator(new DefaultItemAnimator());
        recyclerKecamatan.setAdapter(kecamatanAdapter);

        if (!RegisterActivity.idKab.equals("")){
            Log.d("ID_KAB", RegisterActivity.idKab);
            Call<ResponseBody> call = ParamReq.requestKecamatan(RegisterActivity.idKab,RegisterActivity.this);
            Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Api.kecamatanModels.clear();
                        boolean handle = Handle.handleGetKecamatan(response.body().string(), RegisterActivity.this);
                        if (handle) {
                            kecamatanAdapter.notifyDataSetChanged();
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

            et_hint.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String textSearch = editable.toString();
                    textSearch=textSearch.toLowerCase();
                    List<KecamatanModel> newList=new ArrayList<>();
                    if (textSearch.isEmpty()){
                        newList = Api.kecamatanModels;
                    }else {
                        for (KecamatanModel kecamatan : Api.kecamatanModels){
                            String title=kecamatan.getName().toLowerCase();
                            if (title.contains(textSearch)){
                                newList.add(kecamatan);
                            }
                        }
                    }
                    kecamatanAdapter.setFilter(newList);
                }
            });

            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog1.dismiss();
                }
            });

            Api.enqueueWithRetry(RegisterActivity.this, call, cBack, true, "Loading");
            dialog1.show();
        }else{
            Toast.makeText(RegisterActivity.this, "Silahkan pilih kabupaten terlebih dahulu!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year+"-"+(++monthOfYear)+"-"+dayOfMonth;
        etBOD.setText(date);
        datePickerDialog = null;
    }
}
