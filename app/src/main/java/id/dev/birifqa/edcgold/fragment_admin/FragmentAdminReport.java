package id.dev.birifqa.edcgold.fragment_admin;


import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_admin.AdminListTopupActivity;
import id.dev.birifqa.edcgold.activity_admin.AdminListTransferActivity;
import id.dev.birifqa.edcgold.activity_admin.AdminListWithdrawActivity;
import id.dev.birifqa.edcgold.activity_admin.AdminReportActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminReport extends Fragment {

    private View view;
    private ConstraintLayout btnReport, btnWithdraw, btnTopup, btnTransfer;

    public FragmentAdminReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_report, container, false);

        findViewById();
        onAction();

        return view;
    }
    private void findViewById(){
        btnReport = view.findViewById(R.id.btn_report);
        btnWithdraw = view.findViewById(R.id.btn_withdraw);
        btnTopup = view.findViewById(R.id.btn_topup);
        btnTransfer = view.findViewById(R.id.btn_transfer);

    }

    private void onAction(){
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminReportActivity.class));
            }
        });

        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminListWithdrawActivity.class));
            }
        });

        btnTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminListTopupActivity.class));
            }
        });

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminListTransferActivity.class));
            }
        });
    }
}
