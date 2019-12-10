package id.dev.birifqa.edcgold.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.DetailWalletReceiveActivity;
import id.dev.birifqa.edcgold.model.UserAktifitasModel;
import id.dev.birifqa.edcgold.model.UserReceiveModel;
import id.dev.birifqa.edcgold.utils.Helper;
import id.dev.birifqa.edcgold.utils.Session;

public class UserReceiveAdapter extends RecyclerView.Adapter<UserReceiveAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<UserReceiveModel> receiveModels;

    public UserReceiveAdapter(Context mContext, List<UserReceiveModel> receiveModels) {
        this.mContext = mContext;
        this.receiveModels = receiveModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_receive, parent, false);
        return new UserReceiveAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UserReceiveModel model = receiveModels.get(position);

        holder.tvNominal.setText(Helper.getNumberFormatCurrency(Integer.parseInt(model.getNominal())));
        holder.tvTitle.setText(model.getDescription());
        holder.tvDate.setText(model.getCreated_at());

        if (model.getStatus().equals("0")){
            holder.tvStatus.setText("Belum di proses");
            holder.tvNominal.setTextColor(mContext.getResources().getColor(R.color.mdtp_ampm_text_color));
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.mdtp_ampm_text_color));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Session.save("wallet_receive_balance_coin", model.getBalance_coin());
                    Session.save("wallet_receive_nominal", model.getNominal());
                    Session.save("wallet_receive_transaction_code", model.getTransaction_code());
                    Session.save("wallet_receive_transfer_amount", model.getNominal());
                    mContext.startActivity(new Intent(mContext, DetailWalletReceiveActivity.class));
                }
            });
        } else if (model.getStatus().equals("1")){
            holder.tvStatus.setText("Transaksi Selesai");
            holder.tvNominal.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.tvStatus.setText("Transaksi di tolak");
            holder.tvNominal.setTextColor(mContext.getResources().getColor(R.color.error_color));
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.error_color));
        }
    }

    @Override
    public int getItemCount() {
        return receiveModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus, tvDate, tvNominal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvNominal = itemView.findViewById(R.id.tv_nominal);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
