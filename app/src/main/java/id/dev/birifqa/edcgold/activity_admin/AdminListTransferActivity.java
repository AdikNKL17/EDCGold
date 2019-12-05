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
import id.dev.birifqa.edcgold.adapter.AdminReportTransferAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminReportTransferModel;

public class AdminListTransferActivity extends AppCompatActivity {

    private View view;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AdminReportTransferAdapter transferAdapter;
    private ArrayList<AdminReportTransferModel> transferModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_transfer);

        findViewById();
        onAction();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rv_list_transfer);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminListTransferActivity.this.finish();
            }
        });

        transferModels = new ArrayList<>();
        transferAdapter = new AdminReportTransferAdapter(AdminListTransferActivity.this, transferModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminListTransferActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(transferAdapter);

        getData();
    }

    private void getData(){
        transferModels.clear();

        AdminReportTransferModel user1 = new AdminReportTransferModel();
        user1.setId_transaksi("No. 42802000611111");
        transferModels.add(user1);

        AdminReportTransferModel user2 = new AdminReportTransferModel();
        user2.setId_transaksi("No. 42802000611112");
        transferModels.add(user2);

        AdminReportTransferModel user3 = new AdminReportTransferModel();
        user3.setId_transaksi("No. 42802000611113");
        transferModels.add(user3);

        AdminReportTransferModel user4 = new AdminReportTransferModel();
        user4.setId_transaksi("No. 42802000611114");
        transferModels.add(user4);

        AdminReportTransferModel user5 = new AdminReportTransferModel();
        user5.setId_transaksi("No. 42802000611115");
        transferModels.add(user5);

        AdminReportTransferModel user6 = new AdminReportTransferModel();
        user6.setId_transaksi("No. 42802000611115");
        transferModels.add(user6);

        AdminReportTransferModel user7 = new AdminReportTransferModel();
        user7.setId_transaksi("No. 42802000611115");
        transferModels.add(user7);

        AdminReportTransferModel user8 = new AdminReportTransferModel();
        user8.setId_transaksi("No. 42802000611115");
        transferModels.add(user8);

        AdminReportTransferModel user9 = new AdminReportTransferModel();
        user9.setId_transaksi("No. 42802000611115");
        transferModels.add(user9);

        AdminReportTransferModel user10 = new AdminReportTransferModel();
        user10.setId_transaksi("No. 42802000611115");
        transferModels.add(user10);

        AdminReportTransferModel user11 = new AdminReportTransferModel();
        user11.setId_transaksi("No. 42802000611115");
        transferModels.add(user11);

        AdminReportTransferModel user12 = new AdminReportTransferModel();
        user12.setId_transaksi("No. 42802000611115");
        transferModels.add(user12);

        AdminReportTransferModel user13 = new AdminReportTransferModel();
        user13.setId_transaksi("No. 42802000611115");
        transferModels.add(user13);

        AdminReportTransferModel user14 = new AdminReportTransferModel();
        user14.setId_transaksi("No. 42802000611115");
        transferModels.add(user14);

        transferAdapter.notifyDataSetChanged();
    }
}
