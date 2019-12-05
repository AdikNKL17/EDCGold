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
import id.dev.birifqa.edcgold.adapter.AdminUserMiningAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminUserMiningModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminUserMining extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminUserMiningAdapter userMiningAdapter;
    private ArrayList<AdminUserMiningModel> userMiningModels;

    public FragmentAdminUserMining() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_user_mining, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        recyclerView = view.findViewById(R.id.rv_user_mining);
    }

    private void onAction(){
        userMiningModels = new ArrayList<>();
        userMiningAdapter = new AdminUserMiningAdapter(getActivity(), userMiningModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userMiningAdapter);

        getData();
    }

    private void getData(){
        userMiningModels.clear();

        AdminUserMiningModel userMining1 = new AdminUserMiningModel();
        userMining1.setNama_user("Olivier Straw");
        userMining1.setId_user("ID - 52802000611111");
        userMining1.setMulai_mining("Mulai Mining : 15/09/2019  09:05 PM");
        userMining1.setSisa_waktu("Sisa Waktu - 05 : 45 : 09");
        userMiningModels.add(userMining1);

        AdminUserMiningModel userMining2 = new AdminUserMiningModel();
        userMining2.setNama_user("Fachrul Roni");
        userMining2.setId_user("ID - 3523523626266");
        userMining2.setMulai_mining("Mulai Mining : 14/09/2019  16:05 PM ");
        userMining2.setSisa_waktu("Sisa Waktu - 00 : 35 : 05");
        userMiningModels.add(userMining2);

        AdminUserMiningModel userMining3 = new AdminUserMiningModel();
        userMining3.setNama_user("Amin Surya");
        userMining3.setId_user("ID - 2412414251500");
        userMining3.setMulai_mining("Mulai Mining : 14/09/2019  15:45 PM");
        userMining3.setSisa_waktu("Sisa Waktu - 00 : 25 : 00");
        userMiningModels.add(userMining3);

        AdminUserMiningModel userMining4 = new AdminUserMiningModel();
        userMining4.setNama_user("Soraya Larasati");
        userMining4.setId_user("ID - 353153207888");
        userMining4.setMulai_mining("Mulai Mining : 14/09/2019  15:35 PM");
        userMining4.setSisa_waktu("Sisa Waktu - 00 : 15 : 09");
        userMiningModels.add(userMining4);

        userMiningAdapter.notifyDataSetChanged();
    }

}
