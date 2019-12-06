package id.dev.birifqa.edcgold.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import id.dev.birifqa.edcgold.activity_user.MainActivity;
import id.dev.birifqa.edcgold.request.RequestChangeAddress;
import id.dev.birifqa.edcgold.request.RequestChangeBank;
import id.dev.birifqa.edcgold.request.RequestChangeEmail;
import id.dev.birifqa.edcgold.request.RequestChangePassword;
import id.dev.birifqa.edcgold.request.RequestChangePhone;
import id.dev.birifqa.edcgold.request.RequestChangeRate;
import id.dev.birifqa.edcgold.request.RequestChangeUsername;
import id.dev.birifqa.edcgold.request.RequestRentalProcess;
import id.dev.birifqa.edcgold.request.RequestTopupProcess;
import id.dev.birifqa.edcgold.request.RequestUpdateRate;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;


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

    public static Call<ResponseBody> requestRate(String token, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getRate("Bearer " +token);
    }

    public static Call<ResponseBody> requestNominalRental(String token, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getNominalRental("Bearer " +token);
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

    public static Call<ResponseBody> requestTransactionHistory(String token, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getTransactionHistory("Bearer " +token);
    }

    public static Call<ResponseBody> requestTopupList(String token, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getTopupList("Bearer " +token);
    }

    public static Call<ResponseBody> requestDetailTopup(String token, String idTopup,Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getDetailTopup("Bearer " +token, idTopup);
    }

    public static Call<ResponseBody> requestRentalList(String token, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getRentalList("Bearer " +token);
    }

    public static Call<ResponseBody> requestDetailRental(String token, String idRental,Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getDetailRental("Bearer " +token, idRental);
    }

    public static Call<ResponseBody> reqTopup(String token,Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        final Map<String, RequestBody> map = new HashMap<>();

        map.put("method_id", RequestBody.create(MediaType.parse("multipart/form-data"), "1"));
        map.put("nominal", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("topup_nominal")));
        map.put("label", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("topup_label")));
        map.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("topup_description")));
        return APIInterface.requestTopup("Bearer " +token,map);
    }

    public static Call<ResponseBody> reqTopupConfirmation(String token, String image,Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        final Map<String, RequestBody> map = new HashMap<>();

        map.put("method_id", RequestBody.create(MediaType.parse("multipart/form-data"), "1"));
        map.put("nominal", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("topup_nominal")));
        map.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("topup_description")));
        map.put("bank_name", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("topup_bank_name")));
        map.put("account_name", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("topup_account_name")));
        map.put("transfer_amount", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("topup_nominal")));
        map.put("transfer_date", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("topup_date")));
        map.put("images", RequestBody.create(MediaType.parse("multipart/form-data"), image));

        return APIInterface.requestTopupConfirmation("Bearer " +token,map);
    }

    public static Call<ResponseBody> reqRentalMining(String token, String image,Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        final Map<String, RequestBody> map = new HashMap<>();

        map.put("method_id", RequestBody.create(MediaType.parse("multipart/form-data"), "3"));
        map.put("nominal", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("rental_nominal")));
        map.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), "Sewa mining 1 bulan"));
        map.put("bank_name", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("rental_nama_bank")));
        map.put("account_name", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("rental_nama")));
        map.put("transfer_amount", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("rental_jumlah_transfer")));
        map.put("transfer_date", RequestBody.create(MediaType.parse("multipart/form-data"), Session.get("rental_date")));
        map.put("images", RequestBody.create(MediaType.parse("multipart/form-data"), image));

        return APIInterface.requestRentalMining("Bearer " +token,map);
    }

    public static Call<ResponseBody> reqAddBank(String token, String bankName, String bankNumber, String accountNumber,Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        final Map<String, RequestBody> map = new HashMap<>();

        map.put("bank_name", RequestBody.create(MediaType.parse("multipart/form-data"), bankName));
        map.put("bank_number", RequestBody.create(MediaType.parse("multipart/form-data"), bankNumber));
        map.put("account_name", RequestBody.create(MediaType.parse("multipart/form-data"), accountNumber));


        return APIInterface.requestAddBank("Bearer " +token,map);
    }

    public static Call<ResponseBody> changeUsername(String token,String name,Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);

        RequestChangeUsername requestChangeUsername = new RequestChangeUsername();
        requestChangeUsername.setName(name);

        return APIInterface.changeUsername("Bearer " +token,requestChangeUsername);
    }

    public static Call<ResponseBody> changeEmailRequest(String token, String old_email, String new_email, String confirmed,Context context) {
        final Map<String, RequestBody> map = new HashMap<>();

        map.put("old_email", RequestBody.create(MediaType.parse("multipart/form-data"), old_email));
        map.put("new_email", RequestBody.create(MediaType.parse("multipart/form-data"), new_email));
        map.put("confirmed", RequestBody.create(MediaType.parse("multipart/form-data"), confirmed));
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.requestChangeEmail("Bearer " +token,map);
    }

    public static Call<ResponseBody> requestVerification(String verification,Context context) {
        final Map<String, RequestBody> map = new HashMap<>();

        map.put("verification", RequestBody.create(MediaType.parse("multipart/form-data"), verification));
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.requestVerification(map);
    }

    public static Call<ResponseBody> changeAddress(String token,String country, String region, String regency, String district, String address, String postcode,Context context) {
        RequestChangeAddress requestChangeAddress = new RequestChangeAddress();
        requestChangeAddress.setCountry(country);
        requestChangeAddress.setRegion(region);
        requestChangeAddress.setRegency(regency);
        requestChangeAddress.setDistrict(district);
        requestChangeAddress.setAddress(address);
        requestChangeAddress.setPostcode(postcode);
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.changeAddress("Bearer " +token,requestChangeAddress);
    }

    public static Call<ResponseBody> changePhone(String token, String old_phone, String new_phone, String confirmed,Context context) {
        RequestChangePhone requestChangePhone = new RequestChangePhone();
        requestChangePhone.setOld_phone(old_phone);
        requestChangePhone.setNew_phone(new_phone);
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

    public static Call<ResponseBody> changePassword(String token, String verification, String password, String password_confirmation,Context context) {
        RequestChangePassword requestChangePassword = new RequestChangePassword();
        requestChangePassword.setVerification(verification);
        requestChangePassword.setPassword(password);
        requestChangePassword.setConfirmed(password_confirmation);
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.changePassword(requestChangePassword);
    }

    public static Call<ResponseBody> changeRate(String token, String saleRate, String buyRate,Context context) {
        RequestChangeRate requestChangeRate = new RequestChangeRate();
        requestChangeRate.setBuy_rate(buyRate);
        requestChangeRate.setSale_rate(saleRate);
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.changeRate("Bearer " +token, requestChangeRate);
    }

    public static Call<ResponseBody> requestRekeningBank(String token, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.getRekeningBank("Bearer " +token);
    }

    public static Call<ResponseBody> topupProcess(String token, String idTopup, String status,Context context) {
        RequestTopupProcess requestTopupProcess = new RequestTopupProcess();
        requestTopupProcess.setStatus(status);
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.topupProses("Bearer " +token, idTopup, requestTopupProcess);
    }

    public static Call<ResponseBody> rentalProcess(String token, String idRental, String status,Context context) {
        RequestRentalProcess requestRentalProcess = new RequestRentalProcess();
        requestRentalProcess.setStatus(status);
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.rentalProses("Bearer " +token, idRental, requestRentalProcess);
    }

    public static Call<ResponseBody> updateRate(String token, String buyRate, String saleRate,Context context) {
        RequestUpdateRate requestUpdateRate = new RequestUpdateRate();
        requestUpdateRate.setBuy_rate(buyRate);
        requestUpdateRate.setSale_rate(saleRate);
        APIInterface = Api.initRetrofit(Api.showLog);
        return APIInterface.updateRate("Bearer " +token, requestUpdateRate);
    }

}
