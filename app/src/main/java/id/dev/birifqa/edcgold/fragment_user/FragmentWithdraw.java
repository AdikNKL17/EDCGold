package id.dev.birifqa.edcgold.fragment_user;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
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
public class FragmentWithdraw extends Fragment {

    private View view;
    private TextView tvName, tvCoin;
    private Callback<ResponseBody> cBack;
    private TextInputEditText etCoin, etSaleRate, etNominal, etTotal;
    private AlertDialog dialog;
    private ImageView imgFoto;
    private AppCompatButton btnKonfirmasi;

    private double myCoin;
    private int saleRate = 0;
    private String nominal;
    private String description;
    public FragmentWithdraw() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_withdraw, container, false);

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        tvName = view.findViewById(R.id.tv_name);
        tvCoin = view.findViewById(R.id.tv_coin);
        etCoin = view.findViewById(R.id.et_coin);
        etSaleRate = view.findViewById(R.id.et_sale_rate);
        etNominal = view.findViewById(R.id.et_nominal);
        etTotal = view.findViewById(R.id.et_total);
        imgFoto = view.findViewById(R.id.img_foto);
        btnKonfirmasi = view.findViewById(R.id.btn_konfirmasi);
    }

    private void onAction(){
        getUserDetail();
        getRate();

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nominal.equals("") || !description.equals("")){
                    requestWithdraw();
                }
            }
        });

    }

    private void setMaxNominal(){
        etNominal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")){
                    if (Double.parseDouble(s.toString()) > myCoin){
                        etNominal.setText(String.valueOf(myCoin));

                        double total = Double.parseDouble(etNominal.getText().toString()) * saleRate;
                        etTotal.setText(Helper.getNumberFormatCurrencyDoub(total));

                        nominal = String.valueOf(total);
                        description = "Withdraw sebesar "+Helper.getNumberFormatCurrencyDoub(total);
                    }else {
                        double total = Double.parseDouble(s.toString()) * saleRate;
                        etTotal.setText(Helper.getNumberFormatCurrencyDoub(total));

                        nominal = String.valueOf(total);
                        description = "Withdraw sebesar "+Helper.getNumberFormatCurrencyDoub(total);
                    }
                } else {
                    etTotal.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void requestWithdraw(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.reqWithdraw(Session.get("token"), nominal, description, getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Permintaan Withdraw Berhasil!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(getActivity(), call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(getActivity(), call, cBack, false, "Loading");
    }

    private void getRate(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestRate(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    etSaleRate.setText(Helper.getNumberFormatCurrency(dataObject.getInt("sale_rate")));
                    saleRate = Integer.parseInt(dataObject.getString("sale_rate"));
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(getActivity(), call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(getActivity(), call, cBack, false, "Loading");
    }

    private void getUserDetail(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestUserDetail(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject coinObject = dataObject.getJSONObject("coin");
                    tvName.setText(dataObject.getString("name"));
                    tvCoin.setText(coinObject.getString("balance_coin"));
                    etCoin.setText(coinObject.getString("balance_coin"));
                    setMaxNominal();
                    myCoin = Double.parseDouble(coinObject.getString("balance_coin"));

                    Glide.with(imgFoto).load(dataObject.getString("avatar"))
                            .apply(RequestOptions.circleCropTransform()).into(imgFoto);
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(getActivity(), call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(getActivity(), call, cBack, false, "Loading");
    }

}
