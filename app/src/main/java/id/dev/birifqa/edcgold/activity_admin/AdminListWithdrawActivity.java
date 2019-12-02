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
import id.dev.birifqa.edcgold.adapter.AdminReportWithdrawAdapter;
import id.dev.birifqa.edcgold.model.AdminReportWithdrawModel;

public class AdminListWithdrawActivity extends AppCompatActivity {

    private View view;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AdminReportWithdrawAdapter withdrawAdapter;
    private ArrayList<AdminReportWithdrawModel> withdrawModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_withdraw);

        findViewById();
        onAction();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rv_list_withdraw);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminListWithdrawActivity.this.finish();
            }
        });

        withdrawModels = new ArrayList<>();
        withdrawAdapter = new AdminReportWithdrawAdapter(AdminListWithdrawActivity.this, withdrawModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminListWithdrawActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(withdrawAdapter);

        getData();
    }

    private void getData(){
        withdrawModels.clear();

        AdminReportWithdrawModel user1 = new AdminReportWithdrawModel();
        user1.setId_transaksi("No. 42802000611111");
        withdrawModels.add(user1);

        AdminReportWithdrawModel user2 = new AdminReportWithdrawModel();
        user2.setId_transaksi("No. 42802000611112");
        withdrawModels.add(user2);

        AdminReportWithdrawModel user3 = new AdminReportWithdrawModel();
        user3.setId_transaksi("No. 42802000611113");
        withdrawModels.add(user3);

        AdminReportWithdrawModel user4 = new AdminReportWithdrawModel();
        user4.setId_transaksi("No. 42802000611114");
        withdrawModels.add(user4);

        AdminReportWithdrawModel user5 = new AdminReportWithdrawModel();
        user5.setId_transaksi("No. 42802000611115");
        withdrawModels.add(user5);

        AdminReportWithdrawModel user6 = new AdminReportWithdrawModel();
        user6.setId_transaksi("No. 42802000611115");
        withdrawModels.add(user6);

        AdminReportWithdrawModel user7 = new AdminReportWithdrawModel();
        user7.setId_transaksi("No. 42802000611115");
        withdrawModels.add(user7);

        AdminReportWithdrawModel user8 = new AdminReportWithdrawModel();
        user8.setId_transaksi("No. 42802000611115");
        withdrawModels.add(user8);

        AdminReportWithdrawModel user9 = new AdminReportWithdrawModel();
        user9.setId_transaksi("No. 42802000611115");
        withdrawModels.add(user9);

        AdminReportWithdrawModel user10 = new AdminReportWithdrawModel();
        user10.setId_transaksi("No. 42802000611115");
        withdrawModels.add(user10);

        AdminReportWithdrawModel user11 = new AdminReportWithdrawModel();
        user11.setId_transaksi("No. 42802000611115");
        withdrawModels.add(user11);

        AdminReportWithdrawModel user12 = new AdminReportWithdrawModel();
        user12.setId_transaksi("No. 42802000611115");
        withdrawModels.add(user12);

        AdminReportWithdrawModel user13 = new AdminReportWithdrawModel();
        user13.setId_transaksi("No. 42802000611115");
        withdrawModels.add(user13);

        AdminReportWithdrawModel user14 = new AdminReportWithdrawModel();
        user14.setId_transaksi("No. 42802000611115");
        withdrawModels.add(user14);

        withdrawAdapter.notifyDataSetChanged();
    }
}
