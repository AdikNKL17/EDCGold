package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.ProfileActivity;
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
public class AgingAgingFragment extends Fragment {

    private Session session;
    private Callback<ResponseBody> cBack;
    private View view;

    private TextView tvSaldo, tvProfit;

    public AgingAgingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_aging_aging, container, false);

        findViewById(view);
        onAction();

        return view;
    }

    private void findViewById(View view){
        tvSaldo = view.findViewById(R.id.tv_saldo);
        tvProfit = view.findViewById(R.id.tv_profit);
    }

    private void onAction(){
        getUserDetail();
    }

    private void getUserDetail(){
        Call<ResponseBody> call = ParamReq.requestUserDetail(session.get("token"), getContext());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleAgingAging(response.body().string(), tvSaldo, tvProfit, getContext());
                    if (handle) {

                    } else {
                        Toast.makeText(getContext(), "Tidak Dapat mengambil data Aging", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(getContext(), call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(getContext(), call, cBack, false, "Loading");
    }

}
