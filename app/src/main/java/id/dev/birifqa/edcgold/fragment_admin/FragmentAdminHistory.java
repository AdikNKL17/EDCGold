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
import id.dev.birifqa.edcgold.adapter.AdminHistoryAdapter;
import id.dev.birifqa.edcgold.model.AdminHistoryModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminHistory extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminHistoryAdapter historyAdapter;
    private ArrayList<AdminHistoryModel> historyModels;

    public FragmentAdminHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_history, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        recyclerView = view.findViewById(R.id.rv_history);
    }

    private void onAction(){
        historyModels = new ArrayList<>();
        historyAdapter = new AdminHistoryAdapter(getActivity(), historyModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyAdapter);

        getData();
    }

    private void getData(){
        historyModels.clear();

        AdminHistoryModel history1 = new AdminHistoryModel();
        history1.setStatus("Anda telah menyetujui proses Topup ");
        history1.setDate("19/09/2019  -  09 : 43 : 00");

        historyModels.add(history1);

        AdminHistoryModel history2 = new AdminHistoryModel();
        history2.setStatus("Anda Memblock akun ID 24190990900");
        history2.setDate("18/09/2019  -  20 : 12 : 00\n");

        historyModels.add(history2);

        AdminHistoryModel history3 = new AdminHistoryModel();
        history3.setStatus("Anda menyetujui sewa mining");
        history3.setDate("18/09/2019  -  17 : 43 : 0");

        historyModels.add(history3);

        AdminHistoryModel history4 = new AdminHistoryModel();
        history4.setStatus("Anda melakukan withdraw");
        history4.setDate("18/09/2019  -  16 : 12 : 00");

        historyModels.add(history4);

        AdminHistoryModel history5 = new AdminHistoryModel();
        history5.setStatus("Anda Memiliki tambahan koin 12 EDGC");
        history5.setDate("18/09/2019  -  15 : 12 : 00\n");

        historyModels.add(history5);

        AdminHistoryModel history6 = new AdminHistoryModel();
        history6.setStatus("Anda Memiliki tambahan koin 20 EDGC");
        history6.setDate("18/09/2019  -  15 : 34 : 56");

        historyModels.add(history6);

        historyAdapter.notifyDataSetChanged();
    }
}
