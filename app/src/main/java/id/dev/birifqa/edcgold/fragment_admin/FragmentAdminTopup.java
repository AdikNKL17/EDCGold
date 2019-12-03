package id.dev.birifqa.edcgold.fragment_admin;


import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminTransferTopupAdapter;
import id.dev.birifqa.edcgold.model.AdminTransferTopupModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminTopup extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminTransferTopupAdapter transferTopupAdapter;
    private ArrayList<AdminTransferTopupModel> transferTopupModels;

    private ImageView btnSetting;

    public FragmentAdminTopup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_topup, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        recyclerView = view.findViewById(R.id.rv_transfer_topup);
        btnSetting = view.findViewById(R.id.btn_setting);
    }

    private void onAction(){
        transferTopupModels = new ArrayList<>();
        transferTopupAdapter = new AdminTransferTopupAdapter(getActivity(), transferTopupModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(transferTopupAdapter);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        getData();
    }

    private void getData(){
        transferTopupModels.clear();

        AdminTransferTopupModel transferTopup1 = new AdminTransferTopupModel();
        transferTopup1.setNama_user("Habib A.M");
        transferTopup1.setId_user("ID. 52802000611111");
        transferTopup1.setStatus_proses("Belum di proses");
        transferTopup1.setTgl_topup("11-10-2019");
        transferTopupModels.add(transferTopup1);

        AdminTransferTopupModel transferTopup2 = new AdminTransferTopupModel();
        transferTopup2.setNama_user("Ujang S.");
        transferTopup2.setId_user("ID. 206503444405");
        transferTopup2.setStatus_proses("Belum di proses");
        transferTopup2.setTgl_topup("05-10-2019");
        transferTopupModels.add(transferTopup2);

        AdminTransferTopupModel transferTopup3 = new AdminTransferTopupModel();
        transferTopup3.setNama_user("Nadia H");
        transferTopup3.setId_user("ID. 5800048450311");
        transferTopup3.setStatus_proses("Berhasil di proses ");
        transferTopup3.setTgl_topup("15-09-2019");
        transferTopupModels.add(transferTopup3);

        AdminTransferTopupModel transferTopup4 = new AdminTransferTopupModel();
        transferTopup4.setNama_user("Rani H. A");
        transferTopup4.setId_user("ID. 1220364544044");
        transferTopup4.setStatus_proses("Berhasil di proses ");
        transferTopup4.setTgl_topup("08-09-2019");
        transferTopupModels.add(transferTopup4);

        transferTopupAdapter.notifyDataSetChanged();
    }
}
