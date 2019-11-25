package id.dev.birifqa.edcgold.fragment_admin;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_admin.AdminProfileInformationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminProfile extends Fragment {

    private View view;
    private LinearLayout btnProfil, btnLock, btnPengaturan;

    public FragmentAdminProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_admin_profile, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        btnProfil = view.findViewById(R.id.btn_profil);
    }

    private void onAction(){
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminProfileInformationActivity.class));
            }
        });
    }

}
