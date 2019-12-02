package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminReportTopupAdapter;
import id.dev.birifqa.edcgold.adapter.AdminReportWithdrawAdapter;
import id.dev.birifqa.edcgold.model.AdminReportTopupModel;

public class AdminListTopupActivity extends AppCompatActivity {

    private View view;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AdminReportTopupAdapter topupAdapter;
    private ArrayList<AdminReportTopupModel> topupModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_topup);

        findViewById();
        onAction();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rv_list_topup);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminListTopupActivity.this.finish();
            }
        });

        topupModels = new ArrayList<>();
        topupAdapter = new AdminReportTopupAdapter(AdminListTopupActivity.this, topupModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminListTopupActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(topupAdapter);

        getData();
    }

    private void getData(){
        topupModels.clear();

        AdminReportTopupModel user1 = new AdminReportTopupModel();
        user1.setId_transaksi("No. 42802000611111");
        topupModels.add(user1);

        AdminReportTopupModel user2 = new AdminReportTopupModel();
        user2.setId_transaksi("No. 42802000611112");
        topupModels.add(user2);

        AdminReportTopupModel user3 = new AdminReportTopupModel();
        user3.setId_transaksi("No. 42802000611113");
        topupModels.add(user3);

        AdminReportTopupModel user4 = new AdminReportTopupModel();
        user4.setId_transaksi("No. 42802000611114");
        topupModels.add(user4);

        AdminReportTopupModel user5 = new AdminReportTopupModel();
        user5.setId_transaksi("No. 42802000611115");
        topupModels.add(user5);

        AdminReportTopupModel user6 = new AdminReportTopupModel();
        user6.setId_transaksi("No. 42802000611115");
        topupModels.add(user6);

        AdminReportTopupModel user7 = new AdminReportTopupModel();
        user7.setId_transaksi("No. 42802000611115");
        topupModels.add(user7);

        AdminReportTopupModel user8 = new AdminReportTopupModel();
        user8.setId_transaksi("No. 42802000611115");
        topupModels.add(user8);

        AdminReportTopupModel user9 = new AdminReportTopupModel();
        user9.setId_transaksi("No. 42802000611115");
        topupModels.add(user9);

        AdminReportTopupModel user10 = new AdminReportTopupModel();
        user10.setId_transaksi("No. 42802000611115");
        topupModels.add(user10);

        AdminReportTopupModel user11 = new AdminReportTopupModel();
        user11.setId_transaksi("No. 42802000611115");
        topupModels.add(user11);

        AdminReportTopupModel user12 = new AdminReportTopupModel();
        user12.setId_transaksi("No. 42802000611115");
        topupModels.add(user12);

        AdminReportTopupModel user13 = new AdminReportTopupModel();
        user13.setId_transaksi("No. 42802000611115");
        topupModels.add(user13);

        AdminReportTopupModel user14 = new AdminReportTopupModel();
        user14.setId_transaksi("No. 42802000611115");
        topupModels.add(user14);

        topupAdapter.notifyDataSetChanged();
    }
}
