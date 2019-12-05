package id.dev.birifqa.edcgold.fragment_user;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import dmax.dialog.SpotsDialog;
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
public class FragmentSewaCloud1 extends Fragment {

    private View view;
    private Callback<ResponseBody> cBack;
    private Session session;
    private TextInputEditText etNominal1, etNominal2;
    private TextView tvNamaBank, tvAtasNama;
    private AlertDialog dialog;


    public FragmentSewaCloud1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_sewa_cloud1, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();


        etNominal1 = view.findViewById(R.id.et_nominal_1);
        etNominal2 = view.findViewById(R.id.et_nominal_2);
        tvNamaBank = view.findViewById(R.id.tv_nama_bank);
        tvAtasNama = view.findViewById(R.id.tv_atas_nama);
    }

    private void onAction(){
        getUserDetail();
    }

    private void getUserDetail(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestNominalRental(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleNominalRental(response.body().string(), etNominal1, etNominal2, tvNamaBank, tvAtasNama, getActivity());
                    if (handle) {
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
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
