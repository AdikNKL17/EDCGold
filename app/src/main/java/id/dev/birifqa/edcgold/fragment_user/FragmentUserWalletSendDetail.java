package id.dev.birifqa.edcgold.fragment_user;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.HomeActivity;
import id.dev.birifqa.edcgold.activity_user.WalletSendActivity;
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
public class FragmentUserWalletSendDetail extends Fragment {

    private View view;
    private Callback<ResponseBody> cBack;
    private TextView tvNama;
    private TextInputEditText etJumlahCoin, etRate, etFee, etTotalPembayaran;
    private AppCompatButton btnProses;
    private AlertDialog dialog;
    private WalletSendActivity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_wallet_send_detail, container, false);

        findViewById();

        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        activity = (WalletSendActivity) getActivity();
        tvNama = view.findViewById(R.id.tv_nama);
        etJumlahCoin = view.findViewById(R.id.et_jumlah_coin);
        etRate = view.findViewById(R.id.et_rate);
        etFee = view.findViewById(R.id.et_fee);
        etTotalPembayaran = view.findViewById(R.id.et_total_pembayaran);
        btnProses = view.findViewById(R.id.btn_proses);

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendProsesSend();
            }
        });
    }

    private void onAction(){
        String nama = ((WalletSendActivity) getActivity()).getName();
        tvNama.setText(nama);
        if (!Session.get("wallet_sale_rate").equals("")){
            etRate.setText(Helper.getNumberFormatCurrency(Integer.parseInt(Session.get("wallet_sale_rate"))));
        }

        etJumlahCoin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etJumlahCoin.getText().toString().trim().equals("")){
                    int fee = Integer.parseInt(s.toString()) *Integer.parseInt(Session.get("wallet_sale_rate")) * Integer.parseInt(Session.get("wallet_fee")) / 100;
                    Log.e("FEE123", String.valueOf(fee));
                    etFee.setText(Helper.getNumberFormatCurrency(fee));

                    int totalPembayaran = Integer.parseInt(s.toString()) * Integer.parseInt(Session.get("wallet_sale_rate")) + fee;
                    etTotalPembayaran.setText(Helper.getNumberFormatCurrency(totalPembayaran));
                    Session.save("wallet_nominal", totalPembayaran);
                    Session.save("wallet_description", "Transfer sebesar "+Helper.getNumberFormatCurrency(totalPembayaran));
                } else {
                    etFee.setText("");
                    etTotalPembayaran.setText("");

                    Session.save("wallet_nominal", "");
                    Session.save("wallet_description", "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }




    private void sendProsesSend(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.reqSendWallet(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        Session.save("wallet_id_penerima", "");
                        Session.save("wallet_nama_penerima", "");
                        Session.save("wallet_sale_rate", "");
                        Session.save("wallet_buy_rate", "");
                        Session.save("wallet_fee", "");
                        dialog.dismiss();
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                    } else{
                        dialog.dismiss();
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
}
