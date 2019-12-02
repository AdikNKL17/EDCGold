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
import id.dev.birifqa.edcgold.adapter.AdminUserAdapter;
import id.dev.birifqa.edcgold.model.AdminUserModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminUserAktif extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminUserAdapter userAdapter;
    private ArrayList<AdminUserModel> userModels;

    public FragmentAdminUserAktif() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_user_aktif, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        recyclerView = view.findViewById(R.id.rv_user_aktif);
    }

    private void onAction(){
        userModels = new ArrayList<>();
        userAdapter = new AdminUserAdapter(getActivity(), userModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);

        getData();
    }

    private void getData(){
        userModels.clear();

        AdminUserModel user1 = new AdminUserModel();
        user1.setId("ID. 42802000611111");
        userModels.add(user1);

        AdminUserModel user2 = new AdminUserModel();
        user2.setId("ID. 42802000611112");
        userModels.add(user2);

        AdminUserModel user3 = new AdminUserModel();
        user3.setId("ID. 42802000611113");
        userModels.add(user3);

        AdminUserModel user4 = new AdminUserModel();
        user4.setId("ID. 42802000611114");
        userModels.add(user4);

        AdminUserModel user5 = new AdminUserModel();
        user5.setId("ID. 42802000611115");
        userModels.add(user5);

        AdminUserModel user6 = new AdminUserModel();
        user6.setId("ID. 42802000611115");
        userModels.add(user6);

        AdminUserModel user7 = new AdminUserModel();
        user7.setId("ID. 42802000611115");
        userModels.add(user7);

        AdminUserModel user8 = new AdminUserModel();
        user8.setId("ID. 42802000611115");
        userModels.add(user8);

        AdminUserModel user9 = new AdminUserModel();
        user9.setId("ID. 42802000611115");
        userModels.add(user9);

        AdminUserModel user10 = new AdminUserModel();
        user10.setId("ID. 42802000611115");
        userModels.add(user10);

        AdminUserModel user11 = new AdminUserModel();
        user11.setId("ID. 42802000611115");
        userModels.add(user11);

        AdminUserModel user12 = new AdminUserModel();
        user12.setId("ID. 42802000611115");
        userModels.add(user12);

        AdminUserModel user13 = new AdminUserModel();
        user13.setId("ID. 42802000611115");
        userModels.add(user13);

        AdminUserModel user14 = new AdminUserModel();
        user14.setId("ID. 42802000611115");
        userModels.add(user14);

        userAdapter.notifyDataSetChanged();
    }

}
