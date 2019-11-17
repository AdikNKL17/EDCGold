package id.dev.birifqa.edcgold.fragment_user;


import android.content.Context;
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

import java.util.ArrayList;

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
public class FragmentTopupNominal extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View view;
    private TextView tvNominalCoin;
    private Callback<ResponseBody> cBack;
    private AppCompatSpinner spinnerNominalTopup;

    public FragmentTopupNominal() {
        // Required empty public constructor
    }

    public static FragmentTopupNominal newInstance(String param1, String param2) {
        FragmentTopupNominal fragment = new FragmentTopupNominal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_topup_nominal, container, false);

        tvNominalCoin = view.findViewById(R.id.tv_nominal_coin);
        spinnerNominalTopup = view.findViewById(R.id.spinner_nominal);

        getNominalTopup();

        spinnerNominalTopup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getSelectedItem().equals("Pilih Nominal")){
                    String ids = Api.nominalTopupModels.get(position-1).getId();
                    String label = Api.nominalTopupModels.get(position-1).getLabel();
                    String nominal = Api.nominalTopupModels.get(position-1).getNominal();
                    Session.save("topup_nominal", "Topup sebesar "+label);
                    Session.save("topup_amount", nominal);
                    Session.save("topup_id", ids);
                    tvNominalCoin.setText(nominal);
                    Log.e("Log123", "id " + ids + " label " + label);
                } else {
                    tvNominalCoin.setText("0");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }


    private void getNominalTopup(){
        Call<ResponseBody> call = ParamReq.requestNominalTopup(Session.get("token"), getActivity());
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
