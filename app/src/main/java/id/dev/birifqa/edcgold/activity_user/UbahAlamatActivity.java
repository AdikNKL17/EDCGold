package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import id.dev.birifqa.edcgold.R;
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

public class UbahAlamatActivity extends AppCompatActivity {

    private TextInputEditText etProvinsi, etKabupaten, etKecamatan, etKodepos, etAlamat;
    private AppCompatButton btnConfirm;
    private Session session;
    private Callback<ResponseBody> cBack;
    private Toolbar toolbar;

    public static String idProv;
    public static String idKab;
    public static String idKec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_alamat);

        findViewById();
        onAction();

        getUserDetail();
        getProvinsi();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        etProvinsi = findViewById(R.id.et_provinsi);
        etKabupaten = findViewById(R.id.et_kabkota);
        etKecamatan = findViewById(R.id.et_kecamatan);
        etKodepos = findViewById(R.id.et_kodepos);
        etAlamat = findViewById(R.id.et_alamat);
        btnConfirm = findViewById(R.id.btn_confirm);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                UbahAlamatActivity.this.finish();
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

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UbahAlamatActivity.this, GantiAlamatSuksesActivity.class));
            }
        });
    }

    private void getUserDetail(){
        Call<ResponseBody> call = ParamReq.requestUserDetail(session.get("token"), UbahAlamatActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleChangeAddressDetail(response.body().string(), etKodepos, etAlamat, UbahAlamatActivity.this);
                    if (handle) {

                    } else {
                        Api.mProgressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(UbahAlamatActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(UbahAlamatActivity.this, call, cBack, false, "Loading");
    }

    private void getProvinsi(){
        Call<ResponseBody> call = ParamReq.requestProvinsi(UbahAlamatActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleGetProvinsiName(response.body().string(), etProvinsi, UbahAlamatActivity.this);
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
        Api.enqueueWithRetry(UbahAlamatActivity.this, call, cBack, false, "Loading");

    }

    private void getKabupaten(){
        Call<ResponseBody> call = ParamReq.requestKabupaten(idProv,UbahAlamatActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleGetKabupatenName(response.body().string(), etKabupaten, UbahAlamatActivity.this);
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
        Api.enqueueWithRetry(UbahAlamatActivity.this, call, cBack, false, "Loading");

    }

    private void getKecamatan(){
        Call<ResponseBody> call = ParamReq.requestKecamatan(idKab,UbahAlamatActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleGetKecamatanName(response.body().string(), etKecamatan, UbahAlamatActivity.this);
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
        Api.enqueueWithRetry(UbahAlamatActivity.this, call, cBack, false, "Loading");

    }

    private void showProvinsiDialog(){
        final Dialog dialog1 = new Dialog(UbahAlamatActivity.this);
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
        provinsiAdapter = new ProvinsiAdapter(UbahAlamatActivity.this, Api.provinsiModels, dialog1, etProvinsi, "2");
        recyclerProvinsi.setLayoutManager(new LinearLayoutManager(UbahAlamatActivity.this, RecyclerView.VERTICAL, false));
        recyclerProvinsi.setItemAnimator(new DefaultItemAnimator());
        recyclerProvinsi.setAdapter(provinsiAdapter);

        Call<ResponseBody> call = ParamReq.requestProvinsi(UbahAlamatActivity.this);
        Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Api.provinsiModels.clear();
                    boolean handle = Handle.handleGetProvinsi2(response.body().string(), UbahAlamatActivity.this);
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

        Api.enqueueWithRetry(UbahAlamatActivity.this, call, cBack, true, "Loading");
        dialog1.show();
    }

    private void showKabupatenDialog(){
        final Dialog dialog1 = new Dialog(UbahAlamatActivity.this);
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
        kabupatenAdapter = new KabupatenAdapter(UbahAlamatActivity.this, Api.kabupatenModels, dialog1, etKabupaten, "2");
        recyclerKabupaten.setLayoutManager(new LinearLayoutManager(UbahAlamatActivity.this, RecyclerView.VERTICAL, false));
        recyclerKabupaten.setItemAnimator(new DefaultItemAnimator());
        recyclerKabupaten.setAdapter(kabupatenAdapter);

        if (!UbahAlamatActivity.idProv.equals("")){
            Call<ResponseBody> call = ParamReq.requestKabupaten(UbahAlamatActivity.idProv,UbahAlamatActivity.this);
            Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Api.kabupatenModels.clear();
                        boolean handle = Handle.handleGetKabupaten2(response.body().string(), UbahAlamatActivity.this);
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

            Api.enqueueWithRetry(UbahAlamatActivity.this, call, cBack, true, "Loading");
            dialog1.show();
        }else{
            Toast.makeText(UbahAlamatActivity.this, "Silahkan pilih provinsi terlebih dahulu!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showKecamatanDialog(){
        final Dialog dialog1 = new Dialog(UbahAlamatActivity.this);
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
        kecamatanAdapter = new KecamatanAdapter(UbahAlamatActivity.this, Api.kecamatanModels, dialog1, etKecamatan, "2");
        recyclerKecamatan.setLayoutManager(new LinearLayoutManager(UbahAlamatActivity.this, RecyclerView.VERTICAL, false));
        recyclerKecamatan.setItemAnimator(new DefaultItemAnimator());
        recyclerKecamatan.setAdapter(kecamatanAdapter);

        if (!UbahAlamatActivity.idKab.equals("")){
            Call<ResponseBody> call = ParamReq.requestKecamatan(UbahAlamatActivity.idKab,UbahAlamatActivity.this);
            Callback<ResponseBody> cBack = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Api.kecamatanModels.clear();
                        boolean handle = Handle.handleGetKecamatan2(response.body().string(), UbahAlamatActivity.this);
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

            Api.enqueueWithRetry(UbahAlamatActivity.this, call, cBack, true, "Loading");
            dialog1.show();
        }else{
            Toast.makeText(UbahAlamatActivity.this, "Silahkan pilih kabupaten terlebih dahulu!!", Toast.LENGTH_SHORT).show();
        }
    }
}
