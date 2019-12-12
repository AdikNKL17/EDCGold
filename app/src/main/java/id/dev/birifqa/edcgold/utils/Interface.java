package id.dev.birifqa.edcgold.utils;

import java.util.Map;

import id.dev.birifqa.edcgold.request.RequestChangeAddress;
import id.dev.birifqa.edcgold.request.RequestChangeBank;
import id.dev.birifqa.edcgold.request.RequestChangeEmail;
import id.dev.birifqa.edcgold.request.RequestChangePassword;
import id.dev.birifqa.edcgold.request.RequestChangePassword1;
import id.dev.birifqa.edcgold.request.RequestChangePhone;
import id.dev.birifqa.edcgold.request.RequestChangeRate;
import id.dev.birifqa.edcgold.request.RequestChangeUsername;
import id.dev.birifqa.edcgold.request.RequestPostTentang;
import id.dev.birifqa.edcgold.request.RequestRentalProcess;
import id.dev.birifqa.edcgold.request.RequestTopupProcess;
import id.dev.birifqa.edcgold.request.RequestUpdateKomunitas;
import id.dev.birifqa.edcgold.request.RequestUpdateRate;
import id.dev.birifqa.edcgold.request.RequestUpdateRental;
import id.dev.birifqa.edcgold.request.RequestUpdateTopup;
import id.dev.birifqa.edcgold.request.RequestUpdateUser;
import id.dev.birifqa.edcgold.request.RequestWithdrawProcess;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET("http://45.77.252.55/api/rate")
    Call<ResponseBody> getRate(@Header("Authorization") String authorization);

    @GET("http://45.77.252.55/api/bank_user")
    Call<ResponseBody> getRekeningBank(@Header("Authorization") String authorization);

    @GET("http://45.77.252.55/api/topup_nominal")
    Call<ResponseBody> getNominalTopup(@Header("Authorization") String authorization);

    @GET("http://45.77.252.55/api/rental_nominal")
    Call<ResponseBody> getNominalRental(@Header("Authorization") String authorization);

    @GET("http://45.77.252.55/api/topup_list")
    Call<ResponseBody> getTopupList(@Header("Authorization") String authorization, @Query("offset") String offset, @Query("limit") String limit);

    @GET("http://45.77.252.55/api/topup_detail/{id}")
    Call<ResponseBody> getDetailTopup(@Header("Authorization") String authorization, @Path("id") String id_topup);

    @GET("http://45.77.252.55/api/withdraw_list")
    Call<ResponseBody> getWithdrawList(@Header("Authorization") String authorization, @Query("offset") String offset, @Query("limit") String limit);

    @GET("http://45.77.252.55/api/withdraw_detail/{id}")
    Call<ResponseBody> getDetailWithdraw(@Header("Authorization") String authorization, @Path("id") String id_withdraw);

    @GET("http://45.77.252.55/api/rental_list")
    Call<ResponseBody> getRentalList(@Header("Authorization") String authorization, @Query("offset") String offset, @Query("limit") String limit);

    @GET("http://45.77.252.55/api/rental_detail/{id}")
    Call<ResponseBody> getDetailRental(@Header("Authorization") String authorization, @Path("id") String id_rental);

    @GET("http://45.77.252.55/api/user_list")
    Call<ResponseBody> getUserList(@Header("Authorization") String authorization, @Query("is_new") String is_new, @Query("is_close") String is_close,
                                   @Query("keyword") String keyword);

    @GET("http://45.77.252.55/api/user_detail/{id}")
    Call<ResponseBody> getDetailUser(@Header("Authorization") String authorization, @Path("id") String id_user);

    @GET("http://45.77.252.55/api/mining_list")
    Call<ResponseBody> getUserMiningList(@Header("Authorization") String authorization);

    @GET("http://45.77.252.55/api/my_rental")
    Call<ResponseBody> getMyRental(@Header("Authorization") String authorization);

    @GET("http://45.77.252.55/api/mining_detail/{id}")
    Call<ResponseBody> getDetailMining(@Header("Authorization") String authorization, @Path("id") String id_mining);

    @GET("http://45.77.252.55/api/user_list")
    Call<ResponseBody> getAktifitasList(@Header("Authorization") String authorization, @Query("transfer") String transfer);

    @GET("http://45.77.252.55/api/history")
    Call<ResponseBody> getTransactionHistory(@Header("Authorization") String authorization, @Query("offset") String offset, @Query("limit") String limit);

    @GET("http://45.77.252.55/api/history")
    Call<ResponseBody> getHistoryReceive(@Header("Authorization") String authorization, @Query("transfer") String transfer, @Query("offset") String offset, @Query("limit") String limit);

    @GET("http://45.77.252.55/api/komunitas")
    Call<ResponseBody> getKomunitas(@Header("Authorization") String authorization);

    @GET("http://45.77.252.55/api/komunitas_post")
    Call<ResponseBody> getKomunitasPost(@Header("Authorization") String authorization);

    @GET("http://45.77.252.55/api/komunitas_post/{id}")
    Call<ResponseBody> getKomunitasPostDetail(@Header("Authorization") String authorization, @Path("id") String id_post);

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

    @Multipart
    @POST("http://45.77.252.55/api/verification")
    Call<ResponseBody> requestVerification(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/topup_nominal")
    Call<ResponseBody> requestTopup(@Header("Authorization") String authorization,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/topup")
    Call<ResponseBody> requestTopupConfirmation(@Header("Authorization") String authorization,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/transfer_confirmation")
    Call<ResponseBody> requestReceiveConfirmation(@Header("Authorization") String authorization,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/rental")
    Call<ResponseBody> requestRentalMining(@Header("Authorization") String authorization,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/bank_user")
    Call<ResponseBody> requestAddBank(@Header("Authorization") String authorization,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/email")
    Call<ResponseBody> requestChangeEmail(@Header("Authorization") String authorization,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/withdraw")
    Call<ResponseBody> requestWithdraw(@Header("Authorization") String authorization,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/check_user")
    Call<ResponseBody> requestCheckUser(@Header("Authorization") String authorization,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/komunitas")
    Call<ResponseBody> requestAddKomunitas(@Header("Authorization") String authorization,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/komunitas_post")
    Call<ResponseBody> requestAddPostKomunitas(@Header("Authorization") String authorization,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/transfer")
    Call<ResponseBody> requestSend(@Header("Authorization") String authorization,@PartMap Map<String, RequestBody> params);

    //PUT
    @PUT("http://45.77.252.55/api/username")
    Call<ResponseBody> changeUsername(@Header("Authorization") String authorization, @Body RequestChangeUsername requestChangeUsername);

    @PUT("http://45.77.252.55/api/phone")
    Call<ResponseBody> changePhone(@Header("Authorization") String authorization,@Body RequestChangePhone requestChangePhone);

    @PUT("http://45.77.252.55/api/email")
    Call<ResponseBody> changeEmail(@Header("Authorization") String authorization,@Body RequestChangeEmail requestChangeEmail);

    @PUT("http://45.77.252.55/api/address")
    Call<ResponseBody> changeAddress(@Header("Authorization") String authorization, @Body RequestChangeAddress requestChangeAddress);

    @PUT("http://45.77.252.55/api/bank_user/{id}")
    Call<ResponseBody> changeBank(@Header("Authorization") String authorization,@Path("id") String id_bank, @Body RequestChangeBank requestChangeBank);

    @PUT("http://45.77.252.55/api/password")
    Call<ResponseBody> requestChangePassword(@Header("Authorization") String authorization, @Body RequestChangePassword1 requestChangePassword1);

    @PUT("http://45.77.252.55/api/reset")
    Call<ResponseBody> changePassword(@Body RequestChangePassword requestChangePassword);

    @PUT("http://45.77.252.55/api/rate")
    Call<ResponseBody> changeRate(@Header("Authorization") String authorization, @Body RequestChangeRate requestChangeRate);

    @PUT("http://45.77.252.55/api/process_transaction/{id}")
    Call<ResponseBody> topupProses(@Header("Authorization") String authorization, @Path("id") String id_topup, @Body RequestTopupProcess requestTopupProcess);

    @PUT("http://45.77.252.55/api/process_transaction/{id}")
    Call<ResponseBody> rentalProses(@Header("Authorization") String authorization, @Path("id") String id_rental, @Body RequestRentalProcess requestRentalProcess);

    @PUT("http://45.77.252.55/api/rate")
    Call<ResponseBody> updateRate(@Header("Authorization") String authorization, @Body RequestUpdateRate requestUpdateRate);

    @PUT("http://45.77.252.55/api/topup_nominal")
    Call<ResponseBody> updateTopup(@Header("Authorization") String authorization, @Body RequestUpdateTopup requestUpdateTopup);

    @PUT("http://45.77.252.55/api/rate")
    Call<ResponseBody> updateRental(@Header("Authorization") String authorization, @Body RequestUpdateRental requestUpdateRental);

    @PUT("http://45.77.252.55/api/user_update/{id}")
    Call<ResponseBody> updateUser(@Header("Authorization") String authorization, @Path("id") String id_user, @Body RequestUpdateUser requestUpdateUser);

    @PUT("http://45.77.252.55/api/tentang")
    Call<ResponseBody> updateTentang(@Header("Authorization") String authorization, @Body RequestPostTentang requestPostTentang);

    @PUT("http://45.77.252.55/api/komunitas/{id}")
    Call<ResponseBody> updateKomunitas(@Header("Authorization") String authorization, @Path("id") String id_komunitas, @Body RequestUpdateKomunitas requestUpdateKomunitas);

    @PUT("http://45.77.252.55/api/process_transaction_withdraw/{id}")
    Call<ResponseBody> withdrawProcess(@Header("Authorization") String authorization, @Path("id") String id_withdraw, @Body RequestWithdrawProcess requestWithdrawProcess);

    //DELETE
    @DELETE("http://45.77.252.55/api/bank_user/{id}")
    Call<ResponseBody> deleteBank(@Header("Authorization") String authorization,@Path("id") String id_bank);

    @DELETE("http://45.77.252.55/api/komunitas/{id}")
    Call<ResponseBody> deleteKomunitas(@Header("Authorization") String authorization,@Path("id") String id_komunitas);
}


