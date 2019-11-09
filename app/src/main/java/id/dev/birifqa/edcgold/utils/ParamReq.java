package id.dev.birifqa.edcgold.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import id.dev.birifqa.edcgold.activity_user.MainActivity;
import id.dev.birifqa.edcgold.request.RequestChangeBank;
import id.dev.birifqa.edcgold.request.RequestChangeEmail;
import id.dev.birifqa.edcgold.request.RequestChangePhone;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by palapabeta on 02/02/18.
 */

public class ParamReq {

    public static Context context;
    private static Interface APIInterface;

    public static MainActivity apotekku() {
        return (MainActivity) context;
    }

    public static Call<ResponseBody> reqLogin(String email, String password, String brainkey, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        final Map<String, RequestBody> map = new HashMap<>();

        map.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), email));
        map.put("password", RequestBody.create(MediaType.parse("multipart/form-data"), password));
        map.put("brainkey", RequestBody.create(MediaType.parse("multipart/form-data"), brainkey));
        return APIInterface.requestLogin(map);
    }

    //add
    public static Call<ResponseBody> requestRegister(String nama_depan, String nama_belakang, String jk,
                                                     String bod, String phone, String email, String password,
                                                     String provinsi, String kabupaten, String kecamatan,
                                                     String kodepos, String alamat, String referral, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        final Map<String, RequestBody> data = new HashMap<>();

        data.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), nama_depan));
        data.put("lastname", RequestBody.create(MediaType.parse("multipart/form-data"), nama_belakang));
        data.put("gender", RequestBody.create(MediaType.parse("multipart/form-data"), jk));
        data.put("bod", RequestBody.create(MediaType.parse("multipart/form-data"), bod));
        data.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), phone));
        data.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), email));
        data.put("password", RequestBody.create(MediaType.parse("multipart/form-data"), password));
        data.put("countries_id", RequestBody.create(MediaType.parse("multipart/form-data"), "95"));
        data.put("regions_id", RequestBody.create(MediaType.parse("multipart/form-data"), provinsi));
        data.put("regencies_id", RequestBody.create(MediaType.parse("multipart/form-data"), kabupaten));
        data.put("districts_id", RequestBody.create(MediaType.parse("multipart/form-data"), kecamatan));
        data.put("postcode", RequestBody.create(MediaType.parse("multipart/form-data"), kodepos));
        data.put("address", RequestBody.create(MediaType.parse("multipart/form-data"), alamat));
        data.put("referral", RequestBody.create(MediaType.parse("multipart/form-data"), referral));
        return APIInterface.requestRegister(data);
    }

    public static Call<ResponseBody> reqForgotPassword(String email, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        final Map<String, RequestBody> map = new HashMap<>();

        map.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), email));
        return APIInterface.requestForgotPassword(map);
    }

    public static Call<ResponseBody> requestUserDetail(String token, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getUserDetail("Bearer " +token);
    }

    public static Call<ResponseBody> requestProvinsi(Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getProvinsi();
    }
    public static Call<ResponseBody> requestKabupaten(String idProv,Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getKabupaten(idProv);
    }
    public static Call<ResponseBody> requestKecamatan(String idKab,Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getKecamatan(idKab);
    }

    public static Call<ResponseBody> requestNominalTopup(String token, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getNominalTopup("Bearer " +token);
    }

    public static Call<ResponseBody> changeEmail(String token,String verification, String old_email, String new_email, String confirmed,Context context) {
        RequestChangeEmail requestChangeEmail = new RequestChangeEmail();
        requestChangeEmail.setVerification(verification);
        requestChangeEmail.setOld_email(old_email);
        requestChangeEmail.setNew_email(new_email);
        requestChangeEmail.setConfirmed(confirmed);
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.changeEmail("Bearer " +token,requestChangeEmail);
    }

    public static Call<ResponseBody> changePhone(String token, String old_email, String new_email, String confirmed,Context context) {
        RequestChangePhone requestChangePhone = new RequestChangePhone();
        requestChangePhone.setOld_phone(old_email);
        requestChangePhone.setNew_phone(new_email);
        requestChangePhone.setConfirmed(confirmed);
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.changePhone("Bearer " +token, requestChangePhone);
    }

    public static Call<ResponseBody> changeBank(String token, String id, String bank_name, String bank_number, String account_name,Context context) {
        RequestChangeBank requestChangeBank = new RequestChangeBank();
        requestChangeBank.setBank_name(bank_name);
        requestChangeBank.setBank_number(bank_number);
        requestChangeBank.setAccount_name(account_name);
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.changeBank("Bearer " +token, id, requestChangeBank);
    }

    public static Call<ResponseBody> requestRekeningBank(String token, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getRekeningBank("Bearer " +token);
    }

}
