package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserProfile extends Fragment {

    private View view;
    private Callback<ResponseBody> cBack;
    private TextInputEditText etNama, etId, etPhone, etEmail, etAlamat;
    private TextView tvName, tvCoin;

    public FragmentUserProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        etNama = view.findViewById(R.id.et_name);
        etId = view.findViewById(R.id.et_id);
        etPhone = view.findViewById(R.id.et_phone);
        etEmail = view.findViewById(R.id.et_email);
        etAlamat = view.findViewById(R.id.et_alamat);
        tvName = view.findViewById(R.id.tv_name);
        tvCoin = view.findViewById(R.id.tv_coin);
    }

    private void onAction(){
        getUserDetail();
        getUserDetail1();
    }

    private void getUserDetail1(){
        Call<ResponseBody> call = ParamReq.requestUserDetail(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject coinObject = dataObject.getJSONObject("coin");
                    tvName.setText(dataObject.getString("name") + " "+dataObject.getString("lastname"));
                    tvCoin.setText(coinObject.getString("balance_coin"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(getActivity(), call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(getActivity(), call, cBack, false, "Loading");
    }

    private void getUserDetail(){
        Call<ResponseBody> call = ParamReq.requestUserDetail(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleProfileDetail(response.body().string(), etNama, etId, etPhone, etEmail, etAlamat, getActivity());
                    if (handle) {

                    } else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(getActivity(), call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(getActivity(), call, cBack, false, "Loading");
    }

}
