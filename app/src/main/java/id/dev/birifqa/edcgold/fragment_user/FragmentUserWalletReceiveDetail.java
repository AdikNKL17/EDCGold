package id.dev.birifqa.edcgold.fragment_user;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Helper;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserWalletReceiveDetail extends Fragment {

    private View view;
    private Callback<ResponseBody> cBack;
    private TextView tvCoin, tvNominal;


    public FragmentUserWalletReceiveDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_wallet_receive_detail, container, false);

        tvCoin = view.findViewById(R.id.tv_coin);
        tvNominal = view.findViewById(R.id.tv_nominal);

        tvCoin.setText(Session.get("wallet_receive_balance_coin"));
        tvNominal.setText(Helper.getNumberFormatCurrency(Integer.parseInt(Session.get("wallet_receive_nominal"))));

        return view;
    }

}
