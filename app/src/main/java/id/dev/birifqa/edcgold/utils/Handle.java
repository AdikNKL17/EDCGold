package id.dev.birifqa.edcgold.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import id.dev.birifqa.edcgold.activity_admin.AdminHomeActivity;
import id.dev.birifqa.edcgold.activity_user.UbahRekeningBankActivity;
import id.dev.birifqa.edcgold.activity_user.HomeActivity;
import id.dev.birifqa.edcgold.activity_user.UbahAlamatActivity;
import id.dev.birifqa.edcgold.activity_user.UbahEmailActivity;
import id.dev.birifqa.edcgold.activity_user.UbahNomorActivity;
import id.dev.birifqa.edcgold.activity_user.UbahPasswordActivity;
import id.dev.birifqa.edcgold.model.address.KabupatenModel;
import id.dev.birifqa.edcgold.model.address.KecamatanModel;
import id.dev.birifqa.edcgold.model.address.ProvinsiModel;

/**
 * Created by palapabeta on 03/02/18.
 */

public class Handle {

    private Session session;

    public static boolean handleLogin(String sjson, Context context) {

        Session session = new Session(context);
        try {
            JSONObject jsonObject = new JSONObject(sjson);

            if (!TextUtils.isEmpty(jsonObject.getJSONObject("data").getString("token"))) {

                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray roles = data.getJSONArray("roles");

                if (roles.length() >= 0) {
                    for (int i = 0; i < data.length(); i++) {
                        if (roles.getJSONObject(i).getString("name").equals("member")){
                            session.save("token", jsonObject.getJSONObject("data").getString("token"));
                            session.save("id", jsonObject.getJSONObject("data").getString("id"));
                            session.save("name", jsonObject.getJSONObject("data").getString("name"));
                            session.save("email", jsonObject.getJSONObject("data").getString("email"));
                            session.save("regions_id", jsonObject.getJSONObject("data").getString("regions_id"));
                            session.save("regencies_id", jsonObject.getJSONObject("data").getString("regencies_id"));
                            session.save("districts_id", jsonObject.getJSONObject("data").getString("districts_id"));
                            session.save("postcode", jsonObject.getJSONObject("data").getString("postcode"));
                            session.save("address", jsonObject.getJSONObject("data").getString("address"));
                            session.save("roles_name", roles.getJSONObject(i).getString("name"));

                            Intent intent = new Intent(context, HomeActivity.class);
                            context.startActivity(intent);
                            return true;

                        } else if (roles.getJSONObject(i).getString("name").equals("admin")){
                            session.save("token", jsonObject.getJSONObject("data").getString("token"));
                            session.save("id", jsonObject.getJSONObject("data").getString("id"));
                            session.save("name", jsonObject.getJSONObject("data").getString("name"));
                            session.save("email", jsonObject.getJSONObject("data").getString("email"));
                            session.save("regions_id", jsonObject.getJSONObject("data").getString("regions_id"));
                            session.save("regencies_id", jsonObject.getJSONObject("data").getString("regencies_id"));
                            session.save("districts_id", jsonObject.getJSONObject("data").getString("districts_id"));
                            session.save("postcode", jsonObject.getJSONObject("data").getString("postcode"));
                            session.save("address", jsonObject.getJSONObject("data").getString("address"));
                            session.save("roles_name", roles.getJSONObject(i).getString("name"));

                            Intent intent = new Intent(context, AdminHomeActivity.class);
                            context.startActivity(intent);
                            return true;
                        }
                    }
                    return true;
                } else {
                    Log.d("trip", "data not found");
                }


                return true;

            } else {

                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleGetProvinsi1(String sjson, Context context) {

        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.length() >= 0) {
                    for (int i = 0; i < data.length(); i++) {
                        ProvinsiModel provinsi = new ProvinsiModel();
                        provinsi.setId(data.getJSONObject(i).getString("id"));
                        provinsi.setCountry_id(data.getJSONObject(i).getString("country_id"));
                        provinsi.setName(data.getJSONObject(i).getString("name"));
                        Api.provinsiModels.add(provinsi);
                    }
                    return true;
                } else {
                    Log.d("trip", "data not found");
                }
                return true;

            } else {

                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleGetProvinsi2(String sjson, Context context) {

        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.length() >= 0) {
                    for (int i = 0; i < data.length(); i++) {
                        ProvinsiModel provinsi = new ProvinsiModel();
                        provinsi.setId(data.getJSONObject(i).getString("id"));
                        provinsi.setCountry_id(data.getJSONObject(i).getString("country_id"));
                        provinsi.setName(data.getJSONObject(i).getString("name"));
                        Api.provinsiModels.add(provinsi);
                    }
                    return true;
                } else {
                    Log.d("trip", "data not found");
                }
                return true;

            } else {

                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleGetProvinsiName(String sjson, TextInputEditText etProvinsi, Context context) {
        Session session = new Session(context);
        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.length() >= 0) {
                    for (int i = 0; i < data.length(); i++) {
                        if (data.getJSONObject(i).getString("id").equals(session.get("regions_id"))){
                            etProvinsi.setText(data.getJSONObject(i).getString("name"));
                            UbahAlamatActivity.idProv = data.getJSONObject(i).getString("id");
                            return true;
                        }
                    }
                } else {
                    Log.d("trip", "data not found");
                }
                return true;

            } else {

                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleGetKabupaten1(String sjson, Context context) {

        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.length() >= 0) {
                    for (int i = 0; i < data.length(); i++) {
                        KabupatenModel kabupaten = new KabupatenModel();
                        kabupaten.setId(data.getJSONObject(i).getString("id"));
                        kabupaten.setRegion_id(data.getJSONObject(i).getString("region_id"));
                        kabupaten.setName(data.getJSONObject(i).getString("name"));
                        kabupaten.setCreated_at(data.getJSONObject(i).getString("created_at"));
                        kabupaten.setUpdated_at(data.getJSONObject(i).getString("updated_at"));

                        Api.kabupatenModels.add(kabupaten);
                    }
                    return true;
                } else {
                    Log.d("trip", "data not found");
                }
                return true;

            } else {

                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleGetKabupaten2(String sjson, Context context) {

        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.length() >= 0) {
                    for (int i = 0; i < data.length(); i++) {
                        KabupatenModel kabupaten = new KabupatenModel();
                        kabupaten.setId(data.getJSONObject(i).getString("id"));
                        kabupaten.setRegion_id(data.getJSONObject(i).getString("region_id"));
                        kabupaten.setName(data.getJSONObject(i).getString("name"));
                        kabupaten.setCreated_at(data.getJSONObject(i).getString("created_at"));
                        kabupaten.setUpdated_at(data.getJSONObject(i).getString("updated_at"));

                        Api.kabupatenModels.add(kabupaten);
                    }
                    return true;
                } else {
                    Log.d("trip", "data not found");
                }
                return true;

            } else {

                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleGetKabupatenName(String sjson, TextInputEditText etKabupaten, Context context) {
        Session session = new Session(context);
        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.length() >= 0) {
                    for (int i = 0; i < data.length(); i++) {
                        if (data.getJSONObject(i).getString("id").equals(session.get("regencies_id"))){
                            etKabupaten.setText(data.getJSONObject(i).getString("name"));
                            UbahAlamatActivity.idKab = data.getJSONObject(i).getString("id");
                            return true;
                        }
                    }
                } else {
                    Log.d("trip", "data not found");
                }
                return true;

            } else {

                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleGetKecamatan1(String sjson, Context context) {

        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.length() >= 0) {
                    for (int i = 0; i < data.length(); i++) {
                        KecamatanModel kecamatan = new KecamatanModel();
                        kecamatan.setId(data.getJSONObject(i).getString("id"));
                        kecamatan.setRegency_id(data.getJSONObject(i).getString("regency_id"));
                        kecamatan.setName(data.getJSONObject(i).getString("name"));
                        kecamatan.setCreated_at(data.getJSONObject(i).getString("created_at"));
                        kecamatan.setUpdated_at(data.getJSONObject(i).getString("updated_at"));

                        Api.kecamatanModels.add(kecamatan);
                    }
                    return true;
                } else {

                }
                return true;

            } else {

                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleGetKecamatan2(String sjson, Context context) {

        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.length() >= 0) {
                    for (int i = 0; i < data.length(); i++) {
                        KecamatanModel kecamatan = new KecamatanModel();
                        kecamatan.setId(data.getJSONObject(i).getString("id"));
                        kecamatan.setRegency_id(data.getJSONObject(i).getString("regency_id"));
                        kecamatan.setName(data.getJSONObject(i).getString("name"));
                        kecamatan.setCreated_at(data.getJSONObject(i).getString("created_at"));
                        kecamatan.setUpdated_at(data.getJSONObject(i).getString("updated_at"));

                        Api.kecamatanModels.add(kecamatan);
                    }
                    return true;
                } else {

                }
                return true;

            } else {

                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleGetKecamatanName(String sjson, TextInputEditText etKecamatan, Context context) {
        Session session = new Session(context);
        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.length() >= 0) {
                    for (int i = 0; i < data.length(); i++) {
                        if (data.getJSONObject(i).getString("id").equals(session.get("districts_id"))){
                            etKecamatan.setText(data.getJSONObject(i).getString("name"));
                            UbahAlamatActivity.idKec = data.getJSONObject(i).getString("id");
                            Log.d("Kecamatan name", data.getJSONObject(i).getString("name"));
                            return true;
                        }
                    }
                } else {
                    Log.d("trip", "data not found");
                }
                return true;

            } else {

                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleRequestRegister(String sjson, Context context) {
        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {

                return true;

            } else {
                Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleChangePhone(String sjson, Context context) {
        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {

                return true;

            } else {
                Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleChangeEmail(String sjson, Context context) {
        try {
            JSONObject jsonObject = new JSONObject(sjson);
            boolean succses = jsonObject.getBoolean("success");
            if (succses) {

                return true;

            } else {
                Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                return false;

            }

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleChangeAddressDetail(String sjson, TextInputEditText etKodepos, TextInputEditText etAddress, Context context) {
        try {
            JSONObject jsonObject = new JSONObject(sjson);
            UbahAlamatActivity.idProv = jsonObject.getJSONObject("data").getString("regions_id");
            UbahAlamatActivity.idKab = jsonObject.getJSONObject("data").getString("regencies_id");
            UbahAlamatActivity.idKec = jsonObject.getJSONObject("data").getString("districts_id");
            etKodepos.setText(jsonObject.getJSONObject("data").getString("postcode"));
            etAddress.setText(jsonObject.getJSONObject("data").getString("address"));

            return true;

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleHome(String sjson, TextView tvName, TextView tvEmail, Context context) {
        try {
            JSONObject jsonObject = new JSONObject(sjson);

            tvName.setText(jsonObject.getJSONObject("data").getString("name"));
            tvEmail.setText(jsonObject.getJSONObject("data").getString("email"));

            return true;

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleProfile(String sjson, TextView tvName, Context context) {
        try {
            JSONObject jsonObject = new JSONObject(sjson);

            tvName.setText(jsonObject.getJSONObject("data").getString("name"));

            return true;

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleProfileDetail(String sjson, TextView tvName, TextInputEditText etName,
                                              TextInputEditText etId, TextInputEditText etPhone,
                                              TextInputEditText etEmail, TextInputEditText etAddress,
                                              Context context) {
        try {
            JSONObject jsonObject = new JSONObject(sjson);

            tvName.setText(jsonObject.getJSONObject("data").getString("name"));
            etName.setText(jsonObject.getJSONObject("data").getString("name"));
            etId.setText(jsonObject.getJSONObject("data").getString("id"));
            etPhone.setText(jsonObject.getJSONObject("data").getString("phone"));
            etEmail.setText(jsonObject.getJSONObject("data").getString("email"));
            etAddress.setText(jsonObject.getJSONObject("data").getString("address"));

            return true;

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleProfilePengaturan(String sjson, TextView tvName, ConstraintLayout btnChangePhone,
                                                  ConstraintLayout btnChangeEmail, ConstraintLayout btnChangeBank,
                                                  ConstraintLayout btnChangeAddress, ConstraintLayout btnChangePassword,
                                                  Context context) {
        try {
            JSONObject jsonObject = new JSONObject(sjson);

            tvName.setText(jsonObject.getJSONObject("data").getString("name"));

            btnChangePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UbahNomorActivity.class);
                    try {
                        intent.putExtra("PHONE", jsonObject.getJSONObject("data").getString("phone"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    context.startActivity(intent);
                }
            });

            btnChangeEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UbahEmailActivity.class);
                    try {
                        intent.putExtra("EMAIL", jsonObject.getJSONObject("data").getString("email"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    context.startActivity(intent);
                }
            });

            btnChangeBank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UbahRekeningBankActivity.class);
                    context.startActivity(intent);
                }
            });

            btnChangeAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UbahAlamatActivity.class);
                    context.startActivity(intent);
                }
            });

            btnChangePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UbahPasswordActivity.class);
                    context.startActivity(intent);
                }
            });

            return true;

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean handleAgingAging(String sjson, TextView tvSaldo, TextView tvProfit, Context context) {
        try {
            JSONObject jsonObject = new JSONObject(sjson);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray coins = data.getJSONArray("coins");
            if (coins.length() >= 0) {
                for (int i = 0; i < coins.length(); i++) {
                    Log.d("COIN123", coins.getJSONObject(i).getString("balance_coin"));
                    tvSaldo.setText(coins.getJSONObject(i).getString("balance_coin"));
                }
            }

            JSONArray point = data.getJSONArray("point");
            if (point.length() >= 0){
                for (int i = 0; i < point.length(); i++){
                    Log.d("POINT123", point.getJSONObject(i).getString("balance_point"));
                    tvProfit.setText(point.getJSONObject(i).getString("balance_point"));
                }

                return true;
            }

            return true;

        } catch (JSONException e) {

        } catch (Exception e) {

        }

        return false;
    }
//    public static boolean handleLogin(String sjson, Context context) {
//
//        Session session = new Session(context);
//        try {
//            JSONObject jsonObject = new JSONObject(sjson);
//
//            if (jsonObject.getString(context.getString(R.string.status)).equals("200")) {
//
//                session.save(context.getString(R.string.apikey), jsonObject.getJSONObject("data").getString(context.getString(R.string.apikey)).toString());
//                return true;
//
//            } else {
//
//                return false;
//
//            }
//
//        } catch (JSONException e) {
//
//        } catch (Exception e) {
//
//        }
//
//        return false;
//    }
//
//    public static boolean handleProd(String sjson, Context context, String code){
//
//        try {
//            JSONObject jsonObject = new JSONObject(sjson);
//            int succses = Integer.parseInt(jsonObject.getString("status").toString());
//            if (succses == 200) {
//                JSONArray data = new JSONArray();
//                data = jsonObject.getJSONArray("data");
//
//                if (data.length() >= 0) {
//                    for (int i = 0; i < data.length(); i++) {
//                        ModelProduct mprod = new ModelProduct();
//                        mprod.setHeader(code);
//                        mprod.setIdProd(data.getJSONObject(i).getString("id_product"));
//                        mprod.setItem(data.getJSONObject(i).getString("nama"));
//                        mprod.setDesc(data.getJSONObject(i).getString("description"));
//                        mprod.setPrice(data.getJSONObject(i).getString("harga"));
//                        mprod.setDisc(data.getJSONObject(i).getString("discount"));
//                        mprod.setCrystal(data.getJSONObject(i).getString("crystal"));
//                        mprod.setType(data.getJSONObject(i).getString("type"));
//                        mprod.setDetail(data.getJSONObject(i).getString("detail"));
//
//                        Api.prodList.add(mprod);
//                    }
//                    return true;
//
//                } else {
//
//                    return false;
//
//                }
//
//            } else {
//                return false;
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    public static boolean handleHome(String sjson, Context context, View view) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(sjson);
//
//            if (jsonObject.getString(context.getString(R.string.status)).equals("200")) {
//
//
//
//                JSONObject json = new JSONObject(jsonObject.getJSONObject("data")+"");
//                Helper.setText(R.id.txt_tgl, view, json.getString("tanggal"));
//                Helper.setText(R.id.txt_usrtotal,view,Helper.getNumberFormat(Integer.parseInt(json.getString("user_all"))));
//                Helper.setText(R.id.txt_usronline,view,Helper.getNumberFormat(Integer.parseInt(json.getString("user_online"))));
//                Helper.setText(R.id.txt_usronlinepercent,view,json.getString("user_online_persentase") + "%");
//                Helper.setText(R.id.txt_usroffline,view, Helper.getNumberFormat(Integer.parseInt(json.getString("user_ofline"))));
//                Helper.setText(R.id.txt_omseth1,view,Helper.getNumberFormatCurrency(Integer.parseInt(json.getString("omset_bulan_lalu"))));
//                Helper.setText(R.id.txt_cashout,view,Helper.getNumberFormatCurrency(Integer.parseInt(json.getString("pengeluaran_bulan_lalu"))));
//                Helper.setText(R.id.txt_netto,view,Helper.getNumberFormatCurrency(Integer.parseInt(json.getString("omset_bulan_lalu"))- Integer.parseInt(json.getString("pengeluaran_bulan_lalu"))));
//                Helper.setText(R.id.txt_registration, view, Helper.getNumberFormat(Integer.parseInt(json.getString("user_registrasi_week"))));
//                Helper.setText(R.id.txt_active, view, Helper.getNumberFormat(Integer.parseInt(json.getString("user_active_week"))));
//
//                return true;
//
//            } else {
//
//                return false;
//
//            }
//
//        } catch (JSONException e) {
//
//        } catch (Exception e) {
//
//        }
//
//        return false;
//    }
//
//    public static boolean handleDoUn(String sjson, Context context, View view) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(sjson);
//
//            if (jsonObject.getString(context.getString(R.string.status)).equals("200")) {
//
//                JSONObject json_data = new JSONObject(jsonObject.getJSONObject("data")+"");
//                JSONObject json_day = new JSONObject(json_data.getJSONObject("day")+"");
//                JSONObject json_month = new JSONObject(json_data.getJSONObject("month")+"");
//                JSONObject json_year = new JSONObject(json_data.getJSONObject("year")+"");
//
//                //install
//                Helper.setText(R.id.txt_todown,view, Helper.getNumberFormat(Integer.parseInt(json_day.getString("install"))));
//                Helper.setText(R.id.txt_modown,view, Helper.getNumberFormat(Integer.parseInt(json_month.getString("install"))));
//                Helper.setText(R.id.txt_yedown,view, Helper.getNumberFormat(Integer.parseInt(json_year.getString("install"))));
//
//                return true;
//
//            } else {
//
//                return false;
//
//            }
//
//        } catch (JSONException e) {
//
//        } catch (Exception e) {
//
//        }
//
//        return false;
//    }
//
//    public static boolean handleTransaksi(String sjson, Context context, View view) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(sjson);
//
//            if (jsonObject.getString(context.getString(R.string.status)).equals("200")) {
//
//                JSONObject json_data = new JSONObject(jsonObject.getJSONObject("data")+"");
//
//                Helper.setText(R.id.txt_trxnow,view, Helper.getNumberFormatCurrency(Integer.parseInt(json_data.getString("day"))));
//                Helper.setText(R.id.txt_trxmonth,view, Helper.getNumberFormatCurrency(Integer.parseInt(json_data.getString("month"))));
//                Helper.setText(R.id.txt_trxyear,view, Helper.getNumberFormatCurrency(Integer.parseInt(json_data.getString("year"))));
//
//                JSONArray data = jsonObject.getJSONArray("data");
//                if (data.length() > 0) {
//                    for (int i = 0; i < data.length(); i++) {
//                        ModelTransaksi transaksi = new ModelTransaksi();
//                        transaksi.setNama(data.getJSONObject(i).getString("nama"));
//                        transaksi.setQty(data.getJSONObject(i).getString("qty"));
//                        transaksi.setHarga(data.getJSONObject(i).getString("harga"));
//                        transaksi.setTotal(data.getJSONObject(i).getString("total"));
//                        Api.transList.add(transaksi);
//
//                    }
//                    return true;
//
//                } else {
//
//                    return false;
//                }
//
//            } else {
//
//                return false;
//
//            }
//
//        } catch (JSONException e) {
//
//        } catch (Exception e) {
//
//        }
//
//        return false;
//    }
//
//
//    public static boolean handleTransaksiDetail(String sjson, Context context) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(sjson);
//
//            if (jsonObject.getString(context.getString(R.string.status)).equals("200")) {
//
//                JSONArray data = jsonObject.getJSONArray("data");
//                if (data.length() > 0) {
//                    for (int i = 0; i < data.length(); i++) {
//                        ModelTransaksi transaksi = new ModelTransaksi();
//                        transaksi.setNama(data.getJSONObject(i).getString("nama"));
//                        transaksi.setQty(data.getJSONObject(i).getString("qty"));
//                        transaksi.setHarga(data.getJSONObject(i).getString("harga"));
//                        transaksi.setTotal(data.getJSONObject(i).getString("total"));
//                        Api.transList.add(transaksi);
//
//                    }
//                    return true;
//
//                } else {
//
//                    return false;
//                }
//
//            } else {
//
//                return false;
//
//            }
//
//        } catch (JSONException e) {
//
//        } catch (Exception e) {
//
//        }
//
//        return false;
//    }
//
//    public static boolean handleUserActive(String sjson, Context context) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(sjson);
//
//            if (jsonObject.getString(context.getString(R.string.status)).equals("200")) {
//
//                JSONArray data = jsonObject.getJSONArray("data");
//                if (data.length() > 0) {
//                    for (int i = 0; i < data.length(); i++) {
//                        ModelUser user = new ModelUser();
//                        user.setEmail(data.getJSONObject(i).getString("email"));
//                        user.setRegistrasi(data.getJSONObject(i).getString("registrasi"));
//                        user.setLast_login(data.getJSONObject(i).getString("last_login"));
//
//                        Api.userList.add(user);
//
//                    }
//                    return true;
//
//                } else {
//
//                    return false;
//                }
//
//            } else {
//
//                return false;
//
//            }
//
//        } catch (JSONException e) {
//
//        } catch (Exception e) {
//
//        }
//
//        return false;
//    }
//
//    public static boolean handleAnggaran(String sjson, Context context){
//
//        try {
//            JSONObject jsonObject = new JSONObject(sjson);
//            if (jsonObject.getString(context.getString(R.string.status)).equals("200")) {
//                JSONArray data = jsonObject.getJSONArray("data");
//                if (data.length() > 0) {
//                    for (int i = 0; i < data.length(); i++) {
//                        ModelAnggaran manggaran = new ModelAnggaran();
//                        manggaran.setIdrab(data.getJSONObject(i).getString("id_rab"));
//                        manggaran.setRabname(data.getJSONObject(i).getString("rab_name"));
//                        manggaran.setCreatedat(data.getJSONObject(i).getString("created_at"));
//                        manggaran.setNominal(data.getJSONObject(i).getString("nominal"));
//
//                       Api.anggaranList.add(manggaran);
//
//                    }
//                    return true;
//
//                } else {
//
//                    return false;
//
//                }
//
//            } else {
//                return false;
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    public static String handleApproveRAB(String sjson, Context context) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(sjson);
//
//            if (jsonObject.getString(context.getString(R.string.status)).equals("200")) {
//
//
//                return jsonObject.getString(context.getString(R.string.status));
//
//            } else {
//
//                return jsonObject.getString(context.getString(R.string.status));
//
//            }
//
//        } catch (JSONException e) {
//
//        } catch (Exception e) {
//
//        }
//
//        return "Gagal, Silahkan coba lagi";
//    }
//
//    public static boolean handleApprovePlay(String sjson, Context context) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(sjson);
//
//            if (jsonObject.getString(context.getString(R.string.status)).equals("200")) {
//
//
//                return true;
//
//            } else {
//
//                return false;
//
//            }
//
//        } catch (JSONException e) {
//
//        } catch (Exception e) {
//
//        }
//
//        return false;
//    }
}
