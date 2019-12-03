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
import id.dev.birifqa.edcgold.adapter.AdminListKomunitasAdapter;
import id.dev.birifqa.edcgold.model.AdminListKomunitasModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminKomunitasKomunitas extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminListKomunitasAdapter komunitasAdapter;
    private ArrayList<AdminListKomunitasModel> komunitasModels;

    public FragmentAdminKomunitasKomunitas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_komunitas_komunitas, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        recyclerView = view.findViewById(R.id.rv_list_komunitas);
    }

    private void onAction(){
        komunitasModels = new ArrayList<>();
        komunitasAdapter = new AdminListKomunitasAdapter(getActivity(), komunitasModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(komunitasAdapter);

        getData();
    }

    private void getData(){
        komunitasModels.clear();

        AdminListKomunitasModel user1 = new AdminListKomunitasModel();
        user1.setNama_komunitas("EDGC Bandung");
        user1.setNama_ketua("Heri Huis");
        user1.setAlamat("Jl. Kopo No 23 Bandung ");
        komunitasModels.add(user1);

        AdminListKomunitasModel user2 = new AdminListKomunitasModel();
        user2.setNama_komunitas("EDGC Jakarta");
        user2.setNama_ketua("Jajang Sumirna ");
        user2.setAlamat("Jl. Slippi Barat No. 34");
        komunitasModels.add(user2);

        AdminListKomunitasModel user3 = new AdminListKomunitasModel();
        user3.setNama_komunitas("EDGC Jakarta II");
        user3.setNama_ketua("Hans P. Amirsa");
        user3.setAlamat("Jl. Penjaringan No. 12");
        komunitasModels.add(user3);

        AdminListKomunitasModel user4 = new AdminListKomunitasModel();
        user4.setNama_komunitas("EDCG Surabaya");
        user4.setNama_ketua("Adik Nurkholis");
        user4.setAlamat("Jl. Perjuangan No 08 Surabaya");
        komunitasModels.add(user4);

        AdminListKomunitasModel user5 = new AdminListKomunitasModel();
        user5.setNama_komunitas("EDCG Yogyakarta");
        user5.setNama_ketua("Rifqi Darmawan");
        user5.setAlamat("Jl. Wonosari No 18 Yogyakarta");
        komunitasModels.add(user5);


        komunitasAdapter.notifyDataSetChanged();
    }

}
