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
import id.dev.birifqa.edcgold.adapter.AdminReportKoinAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminReportKoinModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminReportKoin extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminReportKoinAdapter koinAdapter;
    private ArrayList<AdminReportKoinModel> adminReportKoinModels;
    public FragmentAdminReportKoin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_report_koin, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        recyclerView = view.findViewById(R.id.rv_report_koin);
    }

    private void onAction(){
        adminReportKoinModels = new ArrayList<>();
        koinAdapter = new AdminReportKoinAdapter(getActivity(), adminReportKoinModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(koinAdapter);

        getData();
    }

    private void getData(){
        adminReportKoinModels.clear();

        AdminReportKoinModel koin1 = new AdminReportKoinModel();
        koin1.setStatus("1");
        koin1.setCoin("15 EDCG ( 954.000 )");
        koin1.setDate("04-11-2019  -  09 : 08 : 34");
        adminReportKoinModels.add(koin1);

        AdminReportKoinModel koin2 = new AdminReportKoinModel();
        koin2.setStatus("2");
        koin2.setCoin("45 EDCG ( 916.000 )");
        koin2.setDate("01-11-2019  -  19 : 45 : 14");
        adminReportKoinModels.add(koin2);

        AdminReportKoinModel koin3 = new AdminReportKoinModel();
        koin3.setStatus("1");
        koin3.setCoin(" 60 EDCG ( 856.000 )");
        koin3.setDate("03-11-2019  -  21 : 08 : 34");
        adminReportKoinModels.add(koin3);

        AdminReportKoinModel koin4 = new AdminReportKoinModel();
        koin4.setStatus("1");
        koin4.setCoin("35 EDCG ( 816.000 )");
        koin4.setDate("04-11-2019  -  09 : 08 : 34");
        adminReportKoinModels.add(koin4);

        AdminReportKoinModel koin5 = new AdminReportKoinModel();
        koin5.setStatus("2");
        koin5.setCoin("35 EDCG ( 816.000 )");
        koin5.setDate("04-11-2019  -  09 : 08 : 34");
        adminReportKoinModels.add(koin5);

        AdminReportKoinModel koin6 = new AdminReportKoinModel();
        koin6.setStatus("1");
        koin6.setCoin("35 EDCG ( 816.000 )");
        koin6.setDate("04-11-2019  -  09 : 08 : 34");
        adminReportKoinModels.add(koin6);

        koinAdapter.notifyDataSetChanged();
    }
}
