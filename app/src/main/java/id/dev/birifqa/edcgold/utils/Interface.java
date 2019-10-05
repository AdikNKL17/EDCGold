package id.dev.birifqa.edcgold.utils;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by XGibar on 27/10/2016.
 */
public interface Interface {
    @Multipart
    @POST("http://45.77.252.55/api/login")
    Call<ResponseBody> requestLogin(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://45.77.252.55/api/login")
    Call<ResponseBody> requestRegister(@PartMap Map<String, RequestBody> params);

    @GET("http://45.77.252.55/api/region/95")
    Call<ResponseBody> getProvinsi();

    @GET("http://45.77.252.55/api/regency/{id}")
    Call<ResponseBody> getKabupaten(@Path("id") String id_prov);
}


