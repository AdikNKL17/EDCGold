package id.dev.birifqa.edcgold.fragment_user;


import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.SewaCloudActivity;
import id.dev.birifqa.edcgold.adapter.UserMiningAdapter;
import id.dev.birifqa.edcgold.model.UserMiningModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserMining extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private UserMiningAdapter miningAdapter;
    private ArrayList<UserMiningModel> miningModels;

    private ConstraintLayout btnSewaCloud;

    public FragmentUserMining() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_mining, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        recyclerView = view.findViewById(R.id.rv_user_mining);
        btnSewaCloud = view.findViewById(R.id.btn_sewa_cloud);
    }

    private void onAction(){
        miningModels = new ArrayList<>();
        miningAdapter = new UserMiningAdapter(getActivity(), miningModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(miningAdapter);

        btnSewaCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SewaCloudActivity.class));
            }
        });

        getData();
    }

    private void getData(){
        miningModels.clear();

        UserMiningModel user1 = new UserMiningModel();
        user1.setCoin("+ 0.09 EDCG");
        user1.setProses("Proses");
        user1.setDate("24/09/2019");
        miningModels.add(user1);

        UserMiningModel user2 = new UserMiningModel();
        user2.setCoin("+ 1.187 EDCG");
        user2.setProses("Sukses");
        user2.setDate("24/09/2019 - 09:00:08 ");
        miningModels.add(user2);

        UserMiningModel user3 = new UserMiningModel();
        user3.setCoin("+ 1.187 EDCG");
        user3.setProses("Sukses");
        user3.setDate("24/09/2019 - 09:00:08 ");
        miningModels.add(user3);

        UserMiningModel user4 = new UserMiningModel();
        user4.setCoin("+ 1.187 EDCG");
        user4.setProses("Sukses");
        user4.setDate("24/09/2019 - 09:00:08 ");
        miningModels.add(user4);


        miningAdapter.notifyDataSetChanged();
    }

}
