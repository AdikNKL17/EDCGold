package id.dev.birifqa.edcgold.utils;

import java.util.Map;

import id.dev.birifqa.edcgold.request.RequestChangeBank;
import id.dev.birifqa.edcgold.request.RequestChangeEmail;
import id.dev.birifqa.edcgold.request.RequestChangePhone;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by XGibar on 27/10/2016.
 */
public interface Interface {
    //GET
    @GET("http://45.77.252.55/api/user")
    Call<ResponseBody> getUserDetail(@Header("Authorization") String authorization);

    @GET("http://45.77.252.55/api/region/95")
    Call<ResponseBody> getProvinsi();

    @GET("http://45.77.252.55/api/regency/{id}")
    Call<ResponseBody> getKabupaten(@Path("id") String id_prov);

    @GET("http://45.77.252.55/api/district/{id}")
    Call<ResponseBody> getKecamatan(@Path("id") String id_kab);

    @GET("http://45.77.252.55/api/bank_user")
    Call<ResponseBody> getRekeningBank(@Header("Authorization") String authorization);

    @GET("http://45.77.252.55/api/topup_nominal")
    Call<ResponseBody> getNominalTopup(@Header("Authorization") String authorization);

    //POST
    @Multipart
    @POST("http://45.77.252.55/api/login")
    Call<ResponseBody> requestLogin(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/register")
    Call<ResponseBody> requestRegister(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/forgot")
    Call<ResponseBody> requestForgotPassword(@PartMap Map<String, RequestBody> params);

    //PUT
    @PUT("http://45.77.252.55/api/phone")
    Call<ResponseBody> changePhone(@Header("Authorization") String authorization,@Body RequestChangePhone requestChangePhone);

    @PUT("http://45.77.252.55/api/email")
    Call<ResponseBody> changeEmail(@Header("Authorization") String authorization,@Body RequestChangeEmail requestChangeEmail);

    @PUT("http://45.77.252.55/api/bank_user/{id}")
    Call<ResponseBody> changeBank(@Header("Authorization") String authorization,@Path("id") String id_bank, @Body RequestChangeBank requestChangeBank);
}


