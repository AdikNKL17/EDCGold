package id.dev.birifqa.edcgold.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.RegisterActivity;
import id.dev.birifqa.edcgold.activity_user.UbahAlamatActivity;
import id.dev.birifqa.edcgold.activity_user.UbahRekeningBankActivity;
import id.dev.birifqa.edcgold.model.BankModel;
import id.dev.birifqa.edcgold.model.address.KabupatenModel;

public class RekeningBankAdapter extends RecyclerView.Adapter<RekeningBankAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<BankModel> bankModels;

    public RekeningBankAdapter(Context mContext, List<BankModel> bankModels) {
        this.mContext = mContext;
        this.bankModels = bankModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_rekening, parent, false);
        return new RekeningBankAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final BankModel bank = bankModels.get(position);

        holder.bankName.setText(bank.getBank_name());
        holder.accNumber.setText(bank.getBank_number());
        holder.accName.setText(bank.getAccount_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UbahRekeningBankActivity.class);
                intent.putExtra("ID", bank.getId());
                intent.putExtra("BANK_NAME", bank.getBank_name());
                intent.putExtra("BANK_NUMBER", bank.getBank_number());
                intent.putExtra("ACCOUNT_NAME", bank.getAccount_name());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bankName, accNumber, accName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bankName = itemView.findViewById(R.id.bank_name);
            accNumber = itemView.findViewById(R.id.acc_number);
            accName = itemView.findViewById(R.id.acc_name);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
