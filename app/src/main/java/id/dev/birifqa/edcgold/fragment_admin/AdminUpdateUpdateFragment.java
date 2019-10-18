package id.dev.birifqa.edcgold.fragment_admin;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_admin.AdminFeedbackListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminUpdateUpdateFragment extends Fragment {

    private View view;
    private AppCompatButton btnPenarikan, btnToupup, btnBeli, btnTransfer, btnFeedback;

    public AdminUpdateUpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_update_update, container, false);

        findViewById(view);
        onAction();

        return view;
    }

    private void findViewById(View view){
        btnPenarikan = view.findViewById(R.id.btn_penarikan);
        btnToupup = view.findViewById(R.id.btn_topup);
        btnBeli = view.findViewById(R.id.btn_beli);
        btnTransfer = view.findViewById(R.id.btn_transfer);
        btnFeedback = view.findViewById(R.id.btn_feedback);
    }

    private void onAction(){
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdminFeedbackListActivity.class));
            }
        });
    }

}
