package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminListFaqAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminListFaqModel;

public class AdminPostFaqActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminListFaqAdapter faqAdapter;
    private ArrayList<AdminListFaqModel> faqModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post_faq);

        findViewById();
        onAction();
    }

    private void findViewById(){
        recyclerView = findViewById(R.id.rv_list_faq);
    }

    private void onAction(){
        faqModels = new ArrayList<>();
        faqAdapter = new AdminListFaqAdapter(AdminPostFaqActivity.this, faqModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminPostFaqActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(faqAdapter);

        getData();
    }

    private void getData(){
        faqModels.clear();

        AdminListFaqModel user1 = new AdminListFaqModel();
        user1.setTitle("Gagal  Top Up");
        user1.setNama("Oleh : Admin ");
        user1.setDate("24/09/2019 - 09:00:08 ");
        faqModels.add(user1);

        AdminListFaqModel user2 = new AdminListFaqModel();
        user2.setTitle("Gagal  Mining");
        user2.setNama("Oleh : Admin ");
        user2.setDate("24/09/2019 - 09:00:08 ");
        faqModels.add(user2);

        AdminListFaqModel user3 = new AdminListFaqModel();
        user3.setTitle("Gagal  Ganti Password");
        user3.setNama("Oleh : Admin ");
        user3.setDate("24/09/2019 - 09:00:08 ");
        faqModels.add(user3);

        AdminListFaqModel user4 = new AdminListFaqModel();
        user4.setTitle("Gagal  Nikah");
        user4.setNama("Oleh : Admin ");
        user4.setDate("24/09/2019 - 09:00:08 ");
        faqModels.add(user4);

        AdminListFaqModel user5 = new AdminListFaqModel();
        user5.setTitle("Gagal  Naik Haji");
        user5.setNama("Oleh : Admin ");
        user5.setDate("24/09/2019 - 09:00:08 ");
        faqModels.add(user5);


        faqAdapter.notifyDataSetChanged();
    }
}
