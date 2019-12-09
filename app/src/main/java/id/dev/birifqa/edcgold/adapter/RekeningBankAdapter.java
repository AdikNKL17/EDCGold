package id.dev.birifqa.edcgold.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.RegisterActivity;
import id.dev.birifqa.edcgold.activity_user.UbahAlamatActivity;
import id.dev.birifqa.edcgold.activity_user.UbahRekeningBankActivity;
import id.dev.birifqa.edcgold.model.BankModel;
import id.dev.birifqa.edcgold.model.address.KabupatenModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekeningBankAdapter extends RecyclerView.Adapter<RekeningBankAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<BankModel> bankModels;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;


    public RekeningBankAdapter(Context mContext, List<BankModel> bankModels) {
        this.mContext = mContext;
        this.bankModels = bankModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_rekening, parent, false);

        dialog = new SpotsDialog.Builder().setContext(mContext).build();

        return new RekeningBankAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final BankModel bank = bankModels.get(position);

        holder.bankName.setText(bank.getBank_name());
        holder.accNumber.setText(bank.getBank_number());
        holder.accName.setText(bank.getAccount_name());

        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, holder.btnOption);
                popup.inflate(R.menu.option_bank);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.bank_edit:
                                Intent intent = new Intent(mContext, UbahRekeningBankActivity.class);
                                intent.putExtra("ID", bank.getId());
                                intent.putExtra("BANK_NAME", bank.getBank_name());
                                intent.putExtra("BANK_NUMBER", bank.getBank_number());
                                intent.putExtra("ACCOUNT_NAME", bank.getAccount_name());
                                mContext.startActivity(intent);
                                break;
                            case R.id.bank_hapus:
                                delete(bank.getId(), position);
                                break;

                        }
                        return false;
                    }

                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bankName, accNumber, accName, btnOption;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bankName = itemView.findViewById(R.id.bank_name);
            accNumber = itemView.findViewById(R.id.acc_number);
            accName = itemView.findViewById(R.id.acc_name);
            btnOption = itemView.findViewById(R.id.btn_option);
        }
    }

    private void deleteBank(String id, int position){
        dialog.dismiss();
        Call<ResponseBody> call = ParamReq.deleteBank(Session.get("token"), id, mContext);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    boolean handle = Handle.handleDeleteBank(response.body().string(), mContext);
                    if (handle) {
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(mContext, "Gagal menghapus data bank", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(mContext, call, cBack, 1, false);
            }
        };

        Api.enqueueWithRetry(mContext, call, cBack, false, "Loading");
    }

    private void delete(String id, int position){
        final Dialog dialog1 = new Dialog(mContext);
        dialog1.setContentView(R.layout.dialog_delete);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnYes, btnCancel;

        btnYes = dialog1.findViewById(R.id.button_yes);
        btnCancel = dialog1.findViewById(R.id.button_cancel);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBank(id, position);
                bankModels.remove(position);
                notifyItemRemoved(position);
                dialog1.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        dialog1.show();
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
