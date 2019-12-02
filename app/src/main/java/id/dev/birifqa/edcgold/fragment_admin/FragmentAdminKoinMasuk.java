package id.dev.birifqa.edcgold.fragment_admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminReportAktifitasAdapter;
import id.dev.birifqa.edcgold.model.AdminReportAktifitasModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminKoinMasuk extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminReportAktifitasAdapter aktifitasAdapter;
    private ArrayList<AdminReportAktifitasModel> aktifitasModels;
    public FragmentAdminKoinMasuk() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_koin_masuk, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        recyclerView = view.findViewById(R.id.rv_koin_masuk);
    }

    private void onAction(){
        aktifitasModels = new ArrayList<>();
        aktifitasAdapter = new AdminReportAktifitasAdapter(getActivity(), aktifitasModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(aktifitasAdapter);

        getData();
    }

    private void getData(){
        aktifitasModels.clear();

        AdminReportAktifitasModel user1 = new AdminReportAktifitasModel();
        user1.setId("NTR. 42802000611111");
        aktifitasModels.add(user1);

        AdminReportAktifitasModel user2 = new AdminReportAktifitasModel();
        user2.setId("NTR. 42802000611112");
        aktifitasModels.add(user2);

        AdminReportAktifitasModel user3 = new AdminReportAktifitasModel();
        user3.setId("NTR. 42802000611113");
        aktifitasModels.add(user3);

        AdminReportAktifitasModel user4 = new AdminReportAktifitasModel();
        user4.setId("NTR. 42802000611114");
        aktifitasModels.add(user4);

        AdminReportAktifitasModel user5 = new AdminReportAktifitasModel();
        user5.setId("NTR. 42802000611115");
        aktifitasModels.add(user5);

        AdminReportAktifitasModel user6 = new AdminReportAktifitasModel();
        user6.setId("NTR. 42802000611115");
        aktifitasModels.add(user6);

        AdminReportAktifitasModel user7 = new AdminReportAktifitasModel();
        user7.setId("NTR. 42802000611115");
        aktifitasModels.add(user7);

        AdminReportAktifitasModel user8 = new AdminReportAktifitasModel();
        user8.setId("NTR. 42802000611115");
        aktifitasModels.add(user8);

        AdminReportAktifitasModel user9 = new AdminReportAktifitasModel();
        user9.setId("NTR. 42802000611115");
        aktifitasModels.add(user9);

        AdminReportAktifitasModel user10 = new AdminReportAktifitasModel();
        user10.setId("NTR. 42802000611115");
        aktifitasModels.add(user10);

        AdminReportAktifitasModel user11 = new AdminReportAktifitasModel();
        user11.setId("NTR. 42802000611115");
        aktifitasModels.add(user11);

        AdminReportAktifitasModel user12 = new AdminReportAktifitasModel();
        user12.setId("NTR. 42802000611115");
        aktifitasModels.add(user12);

        AdminReportAktifitasModel user13 = new AdminReportAktifitasModel();
        user13.setId("NTR. 42802000611115");
        aktifitasModels.add(user13);

        AdminReportAktifitasModel user14 = new AdminReportAktifitasModel();
        user14.setId("NTR. 42802000611115");
        aktifitasModels.add(user14);

        aktifitasAdapter.notifyDataSetChanged();
    }

}
