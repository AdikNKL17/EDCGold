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
import id.dev.birifqa.edcgold.adapter.AdminNotificationAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminNotificationModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminNotification extends Fragment {


    private View view;
    private RecyclerView recyclerView;
    private AdminNotificationAdapter notificationAdapter;
    private ArrayList<AdminNotificationModel> notificationModels;

    public FragmentAdminNotification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_notification, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        recyclerView = view.findViewById(R.id.rv_notification);
    }

    private void onAction(){
        notificationModels = new ArrayList<>();
        notificationAdapter = new AdminNotificationAdapter(getActivity(), notificationModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notificationAdapter);

        getData();
    }

    private void getData(){
        notificationModels.clear();

        AdminNotificationModel notification1 = new AdminNotificationModel();
        notification1.setStatus("ID 0900980898 Melakukan Topup ");
        notification1.setDate("19/09/2019  -  09 : 43 : 00");

        notificationModels.add(notification1);

        AdminNotificationModel notification2 = new AdminNotificationModel();
        notification2.setStatus("ID 342353255 Melakukan Withdraw ");
        notification2.setDate("18/09/2019  -  20 : 12 : 00");

        notificationModels.add(notification2);

        AdminNotificationModel notification3 = new AdminNotificationModel();
        notification3.setStatus("ID 2141249999 Melakukan Mining ");
        notification3.setDate("18/09/2019  -  17 : 43 : 00");

        notificationModels.add(notification3);

        AdminNotificationModel notification4 = new AdminNotificationModel();
        notification4.setStatus("ID 35235238888 Melakukan Topup ");
        notification4.setDate("18/09/2019  -  16 : 12 : 00");

        notificationModels.add(notification4);

        AdminNotificationModel notification5 = new AdminNotificationModel();
        notification5.setStatus("ID 24148000000 Melakukan Withdraw ");
        notification5.setDate("18/09/2019  -  15 : 12 : 00");

        notificationModels.add(notification5);

        AdminNotificationModel notification6 = new AdminNotificationModel();
        notification6.setStatus("ID 12333366666 Melakukan Topup ");
        notification6.setDate("18/09/2019  -  15 : 34 : 56");

        notificationModels.add(notification6);

        notificationAdapter.notifyDataSetChanged();
    }

}
