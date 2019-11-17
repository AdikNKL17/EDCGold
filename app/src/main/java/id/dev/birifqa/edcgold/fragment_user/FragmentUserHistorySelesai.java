package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import id.dev.birifqa.edcgold.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserHistorySelesai extends Fragment {

    private View view;
    private ImageView noData;
    private RecyclerView recyclerView;

    public FragmentUserHistorySelesai() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_selesai, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        noData = view.findViewById(R.id.no_data);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void onAction() {
        noData.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}
