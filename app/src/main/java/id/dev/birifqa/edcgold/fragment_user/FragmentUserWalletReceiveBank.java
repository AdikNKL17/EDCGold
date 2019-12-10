package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Session;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserWalletReceiveBank extends Fragment {

    private View view;
    private TextInputEditText etNama, etNamaBank, etNoRekening;

    public FragmentUserWalletReceiveBank() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_wallet_receive_bank, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        etNama = view.findViewById(R.id.et_nama);
        etNamaBank = view.findViewById(R.id.et_nama_bank);
        etNoRekening = view.findViewById(R.id.et_no_rekening);
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
                Session.save("wallet_receive_account_name", editable.toString());
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
                Session.save("wallet_receive_bank_name", editable.toString());
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
                Session.save("wallet_receive_bank_number", editable.toString());
            }
        });
    }

}
