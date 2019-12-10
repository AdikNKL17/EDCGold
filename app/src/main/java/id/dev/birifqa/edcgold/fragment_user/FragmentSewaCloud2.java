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
public class FragmentSewaCloud2 extends Fragment {

    private View view;
    private TextInputEditText etNama, etNamaBank, etNoRekening;

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
    }

}
