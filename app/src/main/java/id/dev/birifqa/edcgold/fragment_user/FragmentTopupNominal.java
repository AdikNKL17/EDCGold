package id.dev.birifqa.edcgold.fragment_user;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
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
public class FragmentTopupNominal extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Boolean isChecked = false;

    private View view;
    private TextView tvNominalCoin;
    private Callback<ResponseBody> cBack;
    private ConstraintLayout btnTopup1,btnTopup2,btnTopup3,btnTopup4,btnTopup5,btnTopup6;
    private TextView tvRp1,tvRp2,tvRp3,tvRp4,tvRp5,tvRp6;
    private TextView tvNominal1,tvNominal2,tvNominal3,tvNominal4,tvNominal5,tvNominal6;
    private TextView tvBilangan1,tvBilangan2,tvBilangan3,tvBilangan4,tvBilangan5,tvBilangan6;

    public FragmentTopupNominal() {
        // Required empty public constructor
    }

    public static FragmentTopupNominal newInstance(String param1, String param2) {
        FragmentTopupNominal fragment = new FragmentTopupNominal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_topup_nominal, container, false);

        findViewById();
        onAction();
        return view;
    }

    private void findViewById(){
        tvNominalCoin = view.findViewById(R.id.tv_nominal_coin);

        btnTopup1 = view.findViewById(R.id.btn_topup_1);
        btnTopup2 = view.findViewById(R.id.btn_topup_2);
        btnTopup3 = view.findViewById(R.id.btn_topup_3);
        btnTopup4 = view.findViewById(R.id.btn_topup_4);
        btnTopup5 = view.findViewById(R.id.btn_topup_5);
        btnTopup6 = view.findViewById(R.id.btn_topup_6);

        tvRp1 = view.findViewById(R.id.tv_rp_1);
        tvRp2 = view.findViewById(R.id.tv_rp_2);
        tvRp3 = view.findViewById(R.id.tv_rp_3);
        tvRp4 = view.findViewById(R.id.tv_rp_4);
        tvRp5 = view.findViewById(R.id.tv_rp_5);
        tvRp6 = view.findViewById(R.id.tv_rp_6);


        tvNominal1 = view.findViewById(R.id.tv_nominal_1);
        tvNominal2 = view.findViewById(R.id.tv_nominal_2);
        tvNominal3 = view.findViewById(R.id.tv_nominal_3);
        tvNominal4 = view.findViewById(R.id.tv_nominal_4);
        tvNominal5 = view.findViewById(R.id.tv_nominal_5);
        tvNominal6 = view.findViewById(R.id.tv_nominal_6);

        tvBilangan1 = view.findViewById(R.id.tv_bilangan_1);
        tvBilangan2 = view.findViewById(R.id.tv_bilangan_2);
        tvBilangan3 = view.findViewById(R.id.tv_bilangan_3);
        tvBilangan4 = view.findViewById(R.id.tv_bilangan_4);
        tvBilangan5 = view.findViewById(R.id.tv_bilangan_5);
        tvBilangan6 = view.findViewById(R.id.tv_bilangan_6);


    }

    private void onAction(){

        btnTopup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked = false;
                if (isChecked){
                    isChecked = false;
                    btnTopup1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                }else {
                    isChecked = true;
                    btnTopup1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_blue));
                    tvRp1.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvNominal1.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvBilangan1.setTextColor(getResources().getColor(R.color.colorWhite));

                    tvNominalCoin.setText("100.000");
                    Session.save("topup_nominal", "100000");
                    Session.save("topup_description", "Topup sebesar "+ Helper.getNumberFormatCurrency(Integer.parseInt("100000")));
                    Session.save("topup_label", Helper.getNumberFormatCurrency(Integer.parseInt("100000")));

                    btnTopup2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup4.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal4.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup5.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal5.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup6.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal6.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                }
            }
        });

        btnTopup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked = false;
                if (isChecked){
                    isChecked = false;
                    btnTopup2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                }else {
                    isChecked = true;
                    btnTopup2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_blue));
                    tvRp2.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvNominal2.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvBilangan2.setTextColor(getResources().getColor(R.color.colorWhite));

                    tvNominalCoin.setText("300.000");

                    Session.save("topup_nominal", "300000");
                    Session.save("topup_description", "Topup sebesar "+ Helper.getNumberFormatCurrency(Integer.parseInt("300000")));
                    Session.save("topup_label", Helper.getNumberFormatCurrency(Integer.parseInt("300000")));


                    btnTopup1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup4.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal4.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup5.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal5.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup6.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal6.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                }
            }
        });

        btnTopup3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked = false;
                if (isChecked){
                    isChecked = false;
                    btnTopup3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                }else {
                    isChecked = true;
                    btnTopup3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_blue));
                    tvRp3.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvNominal3.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvBilangan3.setTextColor(getResources().getColor(R.color.colorWhite));

                    tvNominalCoin.setText("500.000");

                    Session.save("topup_nominal", "500000");
                    Session.save("topup_description", "Topup sebesar "+ Helper.getNumberFormatCurrency(Integer.parseInt("500000")));
                    Session.save("topup_label", Helper.getNumberFormatCurrency(Integer.parseInt("500000")));


                    btnTopup1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup4.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal4.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup5.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal5.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup6.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal6.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                }
            }
        });

        btnTopup4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked = false;
                if (isChecked){
                    isChecked = false;
                    btnTopup4.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal4.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                }else {
                    isChecked = true;
                    btnTopup4.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_blue));
                    tvRp4.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvNominal4.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvBilangan4.setTextColor(getResources().getColor(R.color.colorWhite));

                    tvNominalCoin.setText("1.000.000");

                    Session.save("topup_nominal", "1000000");
                    Session.save("topup_description", "Topup sebesar "+ Helper.getNumberFormatCurrency(Integer.parseInt("1000000")));
                    Session.save("topup_label", Helper.getNumberFormatCurrency(Integer.parseInt("1000000")));


                    btnTopup1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup5.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal5.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup6.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal6.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                }
            }
        });

        btnTopup5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked = false;
                if (isChecked){
                    isChecked = false;
                    btnTopup5.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal5.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                }else {
                    isChecked = true;
                    btnTopup5.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_blue));
                    tvRp5.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvNominal5.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvBilangan5.setTextColor(getResources().getColor(R.color.colorWhite));

                    tvNominalCoin.setText("2.000.000");

                    Session.save("topup_nominal", "2000000");
                    Session.save("topup_description", "Topup sebesar "+ Helper.getNumberFormatCurrency(Integer.parseInt("2000000")));
                    Session.save("topup_label", Helper.getNumberFormatCurrency(Integer.parseInt("2000000")));


                    btnTopup1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup4.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal4.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup6.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal6.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                }
            }
        });

        btnTopup6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked = false;
                if (isChecked){
                    isChecked = false;
                    btnTopup6.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal6.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan6.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                }else {
                    isChecked = true;
                    btnTopup6.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_blue));
                    tvRp6.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvNominal6.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvBilangan6.setTextColor(getResources().getColor(R.color.colorWhite));

                    tvNominalCoin.setText("5.000.000");

                    Session.save("topup_nominal", "5000000");
                    Session.save("topup_description", "Topup sebesar "+ Helper.getNumberFormatCurrency(Integer.parseInt("5000000")));
                    Session.save("topup_label", Helper.getNumberFormatCurrency(Integer.parseInt("5000000")));


                    btnTopup1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan1.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan2.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan3.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup4.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal4.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan4.setTextColor(getResources().getColor(R.color.mdtp_light_gray));

                    btnTopup5.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_bg_white));
                    tvRp5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                    tvNominal5.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvBilangan5.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                }
            }
        });
    }

}
