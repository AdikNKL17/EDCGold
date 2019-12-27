package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.textfield.TextInputEditText;

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
public class FragmentSewaCloud2 extends Fragment {

    private View view;
    private TextInputEditText etNama, etNamaBank, etNoRekening;
    private AppCompatSpinner spinnerBank;
    private Callback<ResponseBody> cBack;

    public FragmentSewaCloud2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sewa_cloud2, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        etNama = view.findViewById(R.id.et_nama);
        etNamaBank = view.findViewById(R.id.et_nama_bank);
        etNoRekening = view.findViewById(R.id.et_no_rekening);
        spinnerBank = view.findViewById(R.id.spinner_bank);

    }

    private void onAction(){
        etNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Session.save("rental_nama", editable.toString());
            }
        });

        etNamaBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Session.save("rental_nama_bank", editable.toString());
            }
        });

        etNoRekening.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Session.save("rental_no_rekening", editable.toString());
            }
        });

        spinnerBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getSelectedItem().equals("Pilih Bank")){
                    String bank = Api.bankModels.get(position-1).getBank_name();
                    String rekening = Api.bankModels.get(position-1).getBank_number();
                    String account = Api.bankModels.get(position-1).getAccount_name();

                    etNamaBank.setText(bank);
                    etNoRekening.setText(rekening);
                    etNama.setText(account);

                    Session.save("topup_bank_name", bank);
                    Session.save("topup_bank_number", rekening);
                    Session.save("topup_account_name", account);
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getBank();
    }

    private void getBank(){
        Api.bankModels = new ArrayList<>();
        Call<ResponseBody> call = ParamReq.requestRekeningBank(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleGetRekeningBank(response.body().string(), getActivity());
                    if (handle) {
                        ArrayList<String> bankLabel = new ArrayList<>();
                        bankLabel.clear();
                        bankLabel.add("Pilih Bank");
                        for (int i = 0; i < Api.bankModels.size(); i++){
                            bankLabel.add(Api.bankModels.get(i).getBank_name());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_dropdown_item, bankLabel);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerBank.setAdapter(adapter);
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
