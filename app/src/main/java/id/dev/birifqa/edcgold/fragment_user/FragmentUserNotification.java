package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.UserNotificationAdapter;
import id.dev.birifqa.edcgold.model.UserNotificationModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserNotification extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private UserNotificationAdapter notificationAdapter;
    private ArrayList<UserNotificationModel> notificationModels;
    public FragmentUserNotification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_notification, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        recyclerView = view.findViewById(R.id.rv_notification);
    }

    private void onAction(){
        notificationModels = new ArrayList<>();
        notificationAdapter = new UserNotificationAdapter(getActivity(), notificationModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notificationAdapter);

        getData();
    }

    private void getData(){
        notificationModels.clear();

        UserNotificationModel notification1 = new UserNotificationModel();
        notification1.setStatus("Topup anda berhasil di proses ");
        notification1.setDate("19/09/2019  -  09 : 43 : 00");

        notificationModels.add(notification1);

        UserNotificationModel notification2 = new UserNotificationModel();
        notification2.setStatus("Withdraw sedang di proses admin ");
        notification2.setDate("18/09/2019  -  20 : 12 : 00");

        notificationModels.add(notification2);

        UserNotificationModel notification3 = new UserNotificationModel();
        notification3.setStatus("Mining anda sisa 4 jam lagi ");
        notification3.setDate("18/09/2019  -  17 : 43 : 00");

        notificationModels.add(notification3);

        UserNotificationModel notification4 = new UserNotificationModel();
        notification4.setStatus("Anda mendapatkan 2 koin dari Mining ");
        notification4.setDate("18/09/2019  -  16 : 12 : 00");

        notificationModels.add(notification4);

        UserNotificationModel notification5 = new UserNotificationModel();
        notification5.setStatus("Anda mendapatkan 1 Koin dari mining ");
        notification5.setDate("18/09/2019  -  15 : 12 : 00");

        notificationModels.add(notification5);

        UserNotificationModel notification6 = new UserNotificationModel();
        notification6.setStatus("Proses Sewa Cloud mining berhasil ");
        notification6.setDate("18/09/2019  -  15 : 34 : 56");

        notificationModels.add(notification6);

        notificationAdapter.notifyDataSetChanged();
    }
}
