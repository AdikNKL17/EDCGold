package id.dev.birifqa.edcgold.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import id.dev.birifqa.edcgold.activity_user.MainActivity;
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

    public static Call<ResponseBody> reqLogin(String email, String password, Context context) {
        APIInterface = Api.initRetrofit(Api.showLog);
        final Map<String, RequestBody> map = new HashMap<>();

        map.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), email));
        map.put("password", RequestBody.create(MediaType.parse("multipart/form-data"), password));
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

}
