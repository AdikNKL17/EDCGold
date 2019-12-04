package id.dev.birifqa.edcgold.fragment_admin;


import android.content.Intent;
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
import id.dev.birifqa.edcgold.activity_admin.AdminPengaturanSewaActivity;
import id.dev.birifqa.edcgold.adapter.AdminSewaMiningAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminSewaMiningModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminSewaMining extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminSewaMiningAdapter sewaMiningAdapter;
    private ArrayList<AdminSewaMiningModel> sewaMiningModels;

    private ImageView btnSetting;

    public FragmentAdminSewaMining() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_sewa_mining, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        recyclerView = view.findViewById(R.id.rv_sewa_mining);
        btnSetting = view.findViewById(R.id.btn_setting);
    }

    private void onAction(){
        sewaMiningModels = new ArrayList<>();
        sewaMiningAdapter = new AdminSewaMiningAdapter(getActivity(), sewaMiningModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sewaMiningAdapter);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminPengaturanSewaActivity.class));
            }
        });

        getData();
    }

    private void getData(){
        sewaMiningModels.clear();

        AdminSewaMiningModel sewaMining1 = new AdminSewaMiningModel();
        sewaMining1.setNama_user("Olivier Straw ");
        sewaMining1.setId_user("ID - 52802000611111");
        sewaMining1.setNo_transaksi("No. Transaksi : 4124242411414");
        sewaMining1.setTgl_transaksi("Tgl Transaksi : 14/09/2019");

        sewaMiningModels.add(sewaMining1);

        AdminSewaMiningModel sewaMining2 = new AdminSewaMiningModel();
        sewaMining2.setNama_user("Jane ");
        sewaMining2.setId_user("ID - 32362362887879");
        sewaMining2.setNo_transaksi("No. Transaksi : 4364367347");
        sewaMining2.setTgl_transaksi("Tgl Transaksi : 14/09/2019");

        sewaMiningModels.add(sewaMining2);

        AdminSewaMiningModel sewaMining3 = new AdminSewaMiningModel();
        sewaMining3.setNama_user("Madanda Suharta");
        sewaMining3.setId_user("ID - 3522362624626");
        sewaMining3.setNo_transaksi("No. Transaksi : 2422429000");
        sewaMining3.setTgl_transaksi("Tgl Transaksi : 14/09/2019");

        sewaMiningModels.add(sewaMining3);

        AdminSewaMiningModel sewaMining4 = new AdminSewaMiningModel();
        sewaMining4.setNama_user("Soeharto");
        sewaMining4.setId_user("ID - 3423500000007");
        sewaMining4.setNo_transaksi("No. Transaksi : 3523532522");
        sewaMining4.setTgl_transaksi("Tgl Transaksi : 09/09/2019");

        sewaMiningModels.add(sewaMining4);

        sewaMiningAdapter.notifyDataSetChanged();
    }

}
