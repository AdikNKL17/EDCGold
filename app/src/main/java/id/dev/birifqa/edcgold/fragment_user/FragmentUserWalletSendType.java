package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.dev.birifqa.edcgold.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserWalletSendType extends Fragment {


    public FragmentUserWalletSendType() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_wallet_send_type, container, false);
    }

}
