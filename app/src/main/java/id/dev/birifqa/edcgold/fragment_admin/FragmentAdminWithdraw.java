package id.dev.birifqa.edcgold.fragment_admin;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.AdminWithdrawAdapter;
import id.dev.birifqa.edcgold.model.admin.AdminWithdrawModel;
import okhttp3.ResponseBody;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminWithdraw extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdminWithdrawAdapter withdrawAdapter;
    private ArrayList<AdminWithdrawModel> withdrawModels;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    public FragmentAdminWithdraw() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_withdraw, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        recyclerView = view.findViewById(R.id.rv_withdraw);
    }

    private void onAction(){
        withdrawModels = new ArrayList<>();
        withdrawAdapter = new AdminWithdrawAdapter(getActivity(), withdrawModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(withdrawAdapter);

        getData();
    }

    private void getData(){
        withdrawModels.clear();

        AdminWithdrawModel withdrawModel1 = new AdminWithdrawModel();
        withdrawModel1.setNama_user("Habib A.M");
        withdrawModel1.setId_user("ID. 52802000611111");
        withdrawModel1.setStatus_proses("Belum di proses");
        withdrawModel1.setTgl_withdraw("11-10-2019");
        withdrawModels.add(withdrawModel1);

        AdminWithdrawModel withdrawModel2 = new AdminWithdrawModel();
        withdrawModel2.setNama_user("Ujang S.");
        withdrawModel2.setId_user("ID. 206503444405");
        withdrawModel2.setStatus_proses("Belum di proses");
        withdrawModel2.setTgl_withdraw("05-10-2019");
        withdrawModels.add(withdrawModel2);

        AdminWithdrawModel withdrawModel3 = new AdminWithdrawModel();
        withdrawModel3.setNama_user("Nadia H");
        withdrawModel3.setId_user("ID. 5800048450311");
        withdrawModel3.setStatus_proses("Berhasil di proses ");
        withdrawModel3.setTgl_withdraw("15-09-2019");
        withdrawModels.add(withdrawModel3);

        AdminWithdrawModel withdrawModel4 = new AdminWithdrawModel();
        withdrawModel4.setNama_user("Rani H. A");
        withdrawModel4.setId_user("ID. 1220364544044");
        withdrawModel4.setStatus_proses("Berhasil di proses ");
        withdrawModel4.setTgl_withdraw("08-09-2019");
        withdrawModels.add(withdrawModel4);

        withdrawAdapter.notifyDataSetChanged();
    }
}
