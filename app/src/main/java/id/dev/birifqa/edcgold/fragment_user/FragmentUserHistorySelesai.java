package id.dev.birifqa.edcgold.fragment_user;


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
import id.dev.birifqa.edcgold.adapter.UserHistoryAdapter;
import id.dev.birifqa.edcgold.model.UserHistoryModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserHistorySelesai extends Fragment {

    private View view;
    private ImageView noData;
    private RecyclerView recyclerView;
    private UserHistoryAdapter historyAdapter;
    private ArrayList<UserHistoryModel> historyModels;

    public FragmentUserHistorySelesai() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_selesai, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        noData = view.findViewById(R.id.no_data);
        recyclerView = view.findViewById(R.id.rv_history_selesai);
    }

    private void onAction() {
//        noData.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);

        historyModels = new ArrayList<>();
        historyAdapter = new UserHistoryAdapter(getActivity(), historyModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyAdapter);

        getData();
    }

    private void getData(){
        historyModels.clear();

        UserHistoryModel data1 = new UserHistoryModel();
        data1.setTitle("Anda Melakukan Deposit Rp. 1.500.000");
        data1.setStatus("Transaksi Selesai  ");
        data1.setDate("05-06-2019 ");

        historyModels.add(data1);

        UserHistoryModel data2 = new UserHistoryModel();
        data2.setTitle("Anda Mendapatkan Downline Dari #13210454");
        data2.setStatus("Transaksi Selesai  ");
        data2.setDate("05-06-2019 ");

        historyModels.add(data2);

        UserHistoryModel data3 = new UserHistoryModel();
        data3.setTitle("Anda Melakuan Top Up Rp 250.000");
        data3.setStatus("Transaksi Selesai ");
        data3.setDate("25-05-2019 ");

        historyModels.add(data3);

        UserHistoryModel data4 = new UserHistoryModel();
        data4.setTitle("Anda Melakuan Top Up Rp 50.000");
        data4.setStatus("Transaksi Selesai ");
        data4.setDate("25-05-2019 ");

        historyModels.add(data3);

        UserHistoryModel data5 = new UserHistoryModel();
        data5.setTitle("Anda Melakuan Top Up Rp 200.000");
        data5.setStatus("Transaksi Selesai ");
        data5.setDate("25-05-2019 ");

        historyModels.add(data5);


        historyAdapter.notifyDataSetChanged();
    }
}
