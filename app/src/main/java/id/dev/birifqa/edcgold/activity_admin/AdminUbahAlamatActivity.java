package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.GantiAlamatSuksesActivity;
import id.dev.birifqa.edcgold.adapter.KabupatenAdapter;
import id.dev.birifqa.edcgold.adapter.KecamatanAdapter;
import id.dev.birifqa.edcgold.adapter.ProvinsiAdapter;
import id.dev.birifqa.edcgold.model.address.KabupatenModel;
import id.dev.birifqa.edcgold.model.address.KecamatanModel;
import id.dev.birifqa.edcgold.model.address.ProvinsiModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUbahAlamatActivity extends AppCompatActivity {

    private TextInputEditText etProvinsi, etKabupaten, etKecamatan, etKodepos, etAlamat;
    private AppCompatButton btnSimpan;
    private Session session;
    private Callback<ResponseBody> cBack;
    private Toolbar toolbar;
    private AlertDialog dialog;

    public static String idNegara;
    public static String idProv;
    public static String idKab;
    public static String idKec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ubah_alamat);

        findViewById();
        onAction();

        getUserDetail();
        getProvinsi();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminUbahAlamatActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        etProvinsi = findViewById(R.id.et_provinsi);
        etKabupaten = findViewById(R.id.et_kabupaten);
        etKecamatan = findViewById(R.id.et_kecamatan);
        etKodepos = findViewById(R.id.et_kodepos);
        etAlamat = findViewById(R.id.et_alamat);
        btnSimpan = findViewById(R.id.btn_simpan);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                AdminUbahAlamatActivity.this.finish();
            }
        });

        etProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProvinsiDialog();
                etKabupaten.setText("");
                etKecamatan.setText("");
            }
        });

        etKabupaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKabupatenDialog();
                etKecamatan.setText("");
            }
        });

        etKecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKecamatanDialog();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAddress();
                Log.e("negara", idNegara);
                Log.e("provinsi", idProv);
                Log.e("kabupaten", idKab);
                Log.e("kecamanta", idKec);
                Log.e("postkode", etKodepos.getText().toString());
                Log.e("address", etAlamat.getText().toString());
            }
        });
    }

    private void getUserDetail(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestUserDetail(session.get("token"), AdminUbahAlamatActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleChangeAddressDetail(response.body().string(), etKodepos, etAlamat, AdminUbahAlamatActivity.this);
                    if (handle) {
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AdminUbahAlamatActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminUbahAlamatActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminUbahAlamatActivity.this, call, cBack, false, "Loading");
    }

    private void changeAddress(){
        if (!idNegara.isEmpty() && !idProv.isEmpty() && !idKab.isEmpty() && !idKec.isEmpty() && !etKodepos.getText().toString().isEmpty() && !etAlamat.getText().toString().isEmpty()){
            dialog.show();
            Call<ResponseBody> call = ParamReq.changeAddress(Session.get("token"),idNegara, idProv, idKab, idKec, etAlamat.getText().toString(), etKodepos.getText().toString(), AdminUbahAlamatActivity.this);
            cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        boolean handle = Handle.handleChangeAddress(response.body().string(), AdminUbahAlamatActivity.this);
                        if (handle) {
                            dialog.dismiss();
                            Session.save("countries_id", idNegara);
                            Session.save("regions_id", idProv);
                            Session.save("regencies_id", idKab);
                            Session.save("districts_id", idKec);
                            Session.save("postcode", etKodepos.getText().toString());
                            Session.save("address", etAlamat.getText().toString());
                            startActivity(new Intent(AdminUbahAlamatActivity.this, GantiAlamatSuksesActivity.class));
                        } else {
                            dialog.dismiss();
                            Toast.makeText(AdminUbahAlamatActivity.this, "Change Address Failed, Check again later !!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Api.retryDialog(AdminUbahAlamatActivity.this, call, cBack, 1, false);
                }
            };

            Api.enqueueWithRetry(AdminUbahAlamatActivity.this, call, cBack, false, "Loading");
        } else {
            Toast.makeText(AdminUbahAlamatActivity.this, "All data must be filled !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getProvinsi(){
        Call<ResponseBody> call = ParamReq.requestProvinsi(AdminUbahAlamatActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleGetProvinsiName(response.body().string(), etProvinsi, AdminUbahAlamatActivity.this);
                    if (handle) {
                        Log.d("prov id", idProv);
                        getKabupaten();
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
        Api.enqueueWithRetry(AdminUbahAlamatActivity.this, call, cBack, false, "Loading");

    }

    private void getKabupaten(){
        Call<ResponseBody> call = ParamReq.requestKabupaten(idProv,AdminUbahAlamatActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleGetKabupatenName(response.body().string(), etKabupaten, AdminUbahAlamatActivity.this);
                    if (handle) {
                        Log.d("kab id", idKab);
                        getKecamatan();
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
        Api.enqueueWithRetry(AdminUbahAlamatActivity.this, call, cBack, false, "Loading");

    }

    private void getKecamatan(){
        Call<ResponseBody> call = ParamReq.requestKecamatan(idKab,AdminUbahAlamatActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleGetKecamatanName(response.body().string(), etKecamatan, AdminUbahAlamatActivity.this);
                    if (handle) {
                        Log.d("kec id", idKec);
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
        Api.enqueueWithRetry(AdminUbahAlamatActivity.this, call, cBack, false, "Loading");

    }

    private void showProvinsiDialog(){
        final Dialog dialog1 = new Dialog(AdminUbahAlamatActivity.this);
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
        provinsiAdapter = new ProvinsiAdapter(AdminUbahAlamatActivity.this, Api.provinsiModels, dialog1, etProvinsi, "2");
        recyclerProvinsi.setLayoutManager(new LinearLayoutManager(AdminUbahAlamatActivity.this, RecyclerView.VERTICAL, false));
        recyclerProvinsi.setItemAnimator(new DefaultItemAnimator());
        recyclerProvinsi.setAdapter(provinsiAdapter);

        Call<ResponseBody> call = ParamReq.requestProvinsi(AdminUbahAlamatActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Api.provinsiModels.clear();
                    boolean handle = Handle.handleGetProvinsi2(response.body().string(), AdminUbahAlamatActivity.this);
                    if (handle) {
                        Log.e("prov123", response.body().string());
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

        Api.enqueueWithRetry(AdminUbahAlamatActivity.this, call, cBack, true, "Loading");
        dialog1.show();
    }

    private void showKabupatenDialog(){
        final Dialog dialog1 = new Dialog(AdminUbahAlamatActivity.this);
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
        kabupatenAdapter = new KabupatenAdapter(AdminUbahAlamatActivity.this, Api.kabupatenModels, dialog1, etKabupaten, "2");
        recyclerKabupaten.setLayoutManager(new LinearLayoutManager(AdminUbahAlamatActivity.this, RecyclerView.VERTICAL, false));
        recyclerKabupaten.setItemAnimator(new DefaultItemAnimator());
        recyclerKabupaten.setAdapter(kabupatenAdapter);

        if (!AdminUbahAlamatActivity.idProv.equals("")){
            Call<ResponseBody> call = ParamReq.requestKabupaten(AdminUbahAlamatActivity.idProv,AdminUbahAlamatActivity.this);
            Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Api.kabupatenModels.clear();
                        boolean handle = Handle.handleGetKabupaten2(response.body().string(), AdminUbahAlamatActivity.this);
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

            Api.enqueueWithRetry(AdminUbahAlamatActivity.this, call, cBack, true, "Loading");
            dialog1.show();
        }else{
            Toast.makeText(AdminUbahAlamatActivity.this, "Silahkan pilih provinsi terlebih dahulu!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showKecamatanDialog(){
        final Dialog dialog1 = new Dialog(AdminUbahAlamatActivity.this);
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
        kecamatanAdapter = new KecamatanAdapter(AdminUbahAlamatActivity.this, Api.kecamatanModels, dialog1, etKecamatan, "2");
        recyclerKecamatan.setLayoutManager(new LinearLayoutManager(AdminUbahAlamatActivity.this, RecyclerView.VERTICAL, false));
        recyclerKecamatan.setItemAnimator(new DefaultItemAnimator());
        recyclerKecamatan.setAdapter(kecamatanAdapter);

        if (!AdminUbahAlamatActivity.idKab.equals("")){
            Call<ResponseBody> call = ParamReq.requestKecamatan(AdminUbahAlamatActivity.idKab,AdminUbahAlamatActivity.this);
            Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Api.kecamatanModels.clear();
                        boolean handle = Handle.handleGetKecamatan2(response.body().string(), AdminUbahAlamatActivity.this);
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

            Api.enqueueWithRetry(AdminUbahAlamatActivity.this, call, cBack, true, "Loading");
            dialog1.show();
        }else{
            Toast.makeText(AdminUbahAlamatActivity.this, "Silahkan pilih kabupaten terlebih dahulu!!", Toast.LENGTH_SHORT).show();
        }
    }
}
