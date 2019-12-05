package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserWalletSendType extends Fragment {
    private View view;
    private Callback<ResponseBody> cBack;
    private Session session;
    private AppCompatSpinner spinnerTypeSend;

    public FragmentUserWalletSendType() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_wallet_send_type, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        spinnerTypeSend = view.findViewById(R.id.spinner_type_user);
    }
    private void onAction(){
        spinnerTypeSend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getDataSpinner();
    }

    private void getDataSpinner(){
        ArrayList<String> typeLabel = new ArrayList<>();
        typeLabel.clear();
        typeLabel.add("User");
        typeLabel.add("Exchanger");
        typeLabel.add("Admin");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, typeLabel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeSend.setAdapter(adapter);
    }

}
