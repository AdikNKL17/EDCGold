package id.dev.birifqa.edcgold.utils;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

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

                session.save("token", jsonObject.getJSONObject("data").getString("token"));
                session.save("id", jsonObject.getJSONObject("data").getString("id"));
                session.save("name", jsonObject.getJSONObject("data").getString("name"));
                session.save("email", jsonObject.getJSONObject("data").getString("email"));

                return true;

            } else {

                return false;

            }

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
