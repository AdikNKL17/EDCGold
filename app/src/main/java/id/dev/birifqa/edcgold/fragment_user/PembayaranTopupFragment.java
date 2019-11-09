package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import id.dev.birifqa.edcgold.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PembayaranTopupFragment extends Fragment {

    private View view;

    public PembayaranTopupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_topup_pembayaran, container, false);

        return view;
    }

}
