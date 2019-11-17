package id.dev.birifqa.edcgold.fragment_admin;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_admin.AdminFaqListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminUpdateLihatFragment extends Fragment {

    private View view;
    private AppCompatButton btnBeranda, btnKomunitas, btnTentang, btnFaq;

    public AdminUpdateLihatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_update_lihat, container, false);

        findViewById(view);
        onAction();

        return view;
    }

    private void findViewById(View view){
        btnBeranda = view.findViewById(R.id.btn_beranda);
        btnKomunitas = view.findViewById(R.id.btn_komunitas);
        btnTentang = view.findViewById(R.id.btn_tentang);
        btnFaq = view.findViewById(R.id.btn_faq);
    }

    private void onAction(){
        btnFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdminFaqListActivity.class));
            }
        });
    }

}
