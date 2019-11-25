package id.dev.birifqa.edcgold.fragment_admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.dev.birifqa.edcgold.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminNotification extends Fragment {


    public FragmentAdminNotification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_notification, container, false);
    }

}
