package id.dev.birifqa.edcgold.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminListFeedbackAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminListFeedbackModel;

public class AdminPostFeedbackActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminListFeedbackAdapter feedbackAdapter;
    private ArrayList<AdminListFeedbackModel> feedbackModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post_feedback);

        findViewById();
        onAction();
    }

    private void findViewById(){
        recyclerView = findViewById(R.id.rv_list_feedback);
    }

    private void onAction(){
        feedbackModels = new ArrayList<>();
        feedbackAdapter = new AdminListFeedbackAdapter(AdminPostFeedbackActivity.this, feedbackModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminPostFeedbackActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(feedbackAdapter);

        getData();
    }

    private void getData(){
        feedbackModels.clear();

        AdminListFeedbackModel user1 = new AdminListFeedbackModel();
        user1.setId("ID. 42802000611111");
        user1.setStatus("1");
        feedbackModels.add(user1);

        AdminListFeedbackModel user2 = new AdminListFeedbackModel();
        user2.setId("ID. 42802000611112");
        user2.setStatus("2");
        feedbackModels.add(user2);

        AdminListFeedbackModel user3 = new AdminListFeedbackModel();
        user3.setId("ID. 42802000611113");
        user3.setStatus("1");
        feedbackModels.add(user3);

        AdminListFeedbackModel user4 = new AdminListFeedbackModel();
        user4.setId("ID. 42802000611114");
        user4.setStatus("1");
        feedbackModels.add(user4);

        AdminListFeedbackModel user5 = new AdminListFeedbackModel();
        user5.setId("ID. 42802000611115");
        user5.setStatus("1");
        feedbackModels.add(user5);

        AdminListFeedbackModel user6 = new AdminListFeedbackModel();
        user6.setId("ID. 42802000611115");
        user6.setStatus("1");
        feedbackModels.add(user6);

        AdminListFeedbackModel user7 = new AdminListFeedbackModel();
        user7.setId("ID. 42802000611115");
        user7.setStatus("2");
        feedbackModels.add(user7);

        AdminListFeedbackModel user8 = new AdminListFeedbackModel();
        user8.setId("ID. 42802000611115");
        user8.setStatus("2");
        feedbackModels.add(user8);

        AdminListFeedbackModel user9 = new AdminListFeedbackModel();
        user9.setId("ID. 42802000611115");
        user9.setStatus("2");
        feedbackModels.add(user9);

        AdminListFeedbackModel user10 = new AdminListFeedbackModel();
        user10.setId("ID. 42802000611115");
        user10.setStatus("1");
        feedbackModels.add(user10);

        AdminListFeedbackModel user11 = new AdminListFeedbackModel();
        user11.setId("ID. 42802000611115");
        user11.setStatus("1");
        feedbackModels.add(user11);

        AdminListFeedbackModel user12 = new AdminListFeedbackModel();
        user12.setId("ID. 42802000611115");
        user12.setStatus("2");
        feedbackModels.add(user12);

        feedbackAdapter.notifyDataSetChanged();
    }
}
