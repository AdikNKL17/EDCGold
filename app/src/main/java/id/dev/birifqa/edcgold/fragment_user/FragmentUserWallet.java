package id.dev.birifqa.edcgold.fragment_user;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
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
import id.dev.birifqa.edcgold.activity_user.HomeActivity;
import id.dev.birifqa.edcgold.activity_user.WalletReceiveActivity;
import id.dev.birifqa.edcgold.activity_user.WalletSendActivity;
import id.dev.birifqa.edcgold.adapter.UserAktifitasAdapter;
import id.dev.birifqa.edcgold.model.UserAktifitasModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserWallet extends Fragment {

    private View view;
    private ImageView noData;
    private RecyclerView recyclerView;
    private UserAktifitasAdapter aktifitasAdapter;
    private ArrayList<UserAktifitasModel> aktifitasModels;
    private AppCompatButton btnSend, btnReceive;

    public FragmentUserWallet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_wallet, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        noData = view.findViewById(R.id.no_data);
        recyclerView = view.findViewById(R.id.rv_aktifitas);
        btnSend = view.findViewById(R.id.btn_send);
        btnReceive = view.findViewById(R.id.btn_receive);
    }

    private void onAction(){
//        noData.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);

        aktifitasModels = new ArrayList<>();
        aktifitasAdapter = new UserAktifitasAdapter(getActivity(), aktifitasModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(aktifitasAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WalletSendActivity.class));
            }
        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WalletReceiveActivity.class));
            }
        });

        getData();
    }

    private void getData(){
        aktifitasModels.clear();

        UserAktifitasModel data1 = new UserAktifitasModel();
        data1.setTipe("1");
        data1.setTitle("Anda Melakukan Deposit");
        data1.setStatus("Transaksi Selesai");
        data1.setDate("05-06-2019 ");
        data1.setNominal("Rp. 2.500.000");

        aktifitasModels.add(data1);

        UserAktifitasModel data2 = new UserAktifitasModel();
        data2.setTipe("1");
        data2.setTitle("Anda Melakukan Top Up");
        data2.setStatus("Transaksi Selesai");
        data2.setDate("05-06-2019 ");
        data2.setNominal("Rp. 2.500.000");

        aktifitasModels.add(data2);

        UserAktifitasModel data3 = new UserAktifitasModel();
        data3.setTipe("2");
        data3.setTitle("Anda Melakukan Pembelian Koin");
        data3.setStatus("Transaksi Selesai");
        data3.setDate("25-05-2019 ");
        data3.setNominal("Rp. 50.000");

        aktifitasModels.add(data3);


        aktifitasAdapter.notifyDataSetChanged();
    }

}
