package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.model.NominalTopupModel;
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
public class PembayaranBeliFragment extends Fragment {

    private TextView tvNominal, tvSaldo;
    private TextInputEditText etNominal;
    private View view;
    private Callback<ResponseBody> cBack;
    private Session session;
    private AppCompatSpinner spinnerNominalTopup;
    public PembayaranBeliFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_beli_pembayaran, container, false);

        session = new Session(getActivity());

        tvNominal = view.findViewById(R.id.tv_nominal_coin);
        tvSaldo = view.findViewById(R.id.tv_saldo);
//        etNominal = view.findViewById(R.id.et_nominal_coin);
        spinnerNominalTopup = view.findViewById(R.id.spinner_nominal);

//        etNominal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setupSpinner();
//            }
//        });
        spinnerNominalTopup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getSelectedItem().equals("Pilih Nominal")){
                    String ids = Api.nominalTopupModels.get(position-1).getId();
                    String label = Api.nominalTopupModels.get(position-1).getLabel();
                    String nominal = Api.nominalTopupModels.get(position-1).getNominal();
                    tvNominal.setText(nominal);
                    Log.e("Log123", "id " + ids + " label " + label);
                } else {
                    tvNominal.setText("0.00");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getNominalTopup();
        return view;
    }

    private void getNominalTopup(){
        Call<ResponseBody> call = ParamReq.requestNominalTopup(session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleNominalTopup(response.body().string(), getActivity());
                    if (handle) {
                        ArrayList<String> nominalLabel = new ArrayList<>();
                        nominalLabel.clear();
                        nominalLabel.add("Pilih Nominal");
                        for (int i = 0; i < Api.nominalTopupModels.size(); i++){
                            nominalLabel.add(Api.nominalTopupModels.get(i).getLabel());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_dropdown_item, nominalLabel);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerNominalTopup.setAdapter(adapter);
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
