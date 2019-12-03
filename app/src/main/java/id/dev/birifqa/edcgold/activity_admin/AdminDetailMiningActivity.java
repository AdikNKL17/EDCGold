package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminMiningUserAdapter;
import id.dev.birifqa.edcgold.model.AdminUserMiningHistoryModel;

public class AdminDetailMiningActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminMiningUserAdapter historyAdapter;
    private ArrayList<AdminUserMiningHistoryModel> historyModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_mining);

        findViewById();
        onAction();
    }

    private void findViewById(){
        recyclerView = findViewById(R.id.rv_user_mining);
    }

    private void onAction(){
        historyModels = new ArrayList<>();
        historyAdapter = new AdminMiningUserAdapter(AdminDetailMiningActivity.this, historyModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminDetailMiningActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyAdapter);

        getData();
    }

    private void getData(){
        historyModels.clear();

        AdminUserMiningHistoryModel user1 = new AdminUserMiningHistoryModel();
        user1.setCoin("+ 0.09 EDCG");
        user1.setProses("Proses");
        user1.setDate("24/09/2019");
        historyModels.add(user1);

        AdminUserMiningHistoryModel user2 = new AdminUserMiningHistoryModel();
        user2.setCoin("+ 1.187 EDCG");
        user2.setProses("Sukses");
        user2.setDate("24/09/2019 - 09:00:08 ");
        historyModels.add(user2);

        AdminUserMiningHistoryModel user3 = new AdminUserMiningHistoryModel();
        user3.setCoin("+ 1.187 EDCG");
        user3.setProses("Sukses");
        user3.setDate("24/09/2019 - 09:00:08 ");
        historyModels.add(user3);

        AdminUserMiningHistoryModel user4 = new AdminUserMiningHistoryModel();
        user4.setCoin("+ 1.187 EDCG");
        user4.setProses("Sukses");
        user4.setDate("24/09/2019 - 09:00:08 ");
        historyModels.add(user4);


        historyAdapter.notifyDataSetChanged();
    }
}
