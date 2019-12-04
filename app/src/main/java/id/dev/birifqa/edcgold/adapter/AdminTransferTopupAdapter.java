package id.dev.birifqa.edcgold.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_admin.AdminDetailTopupActivity;
import id.dev.birifqa.edcgold.model.admin.AdminTransferTopupModel;

public class AdminTransferTopupAdapter extends RecyclerView.Adapter<AdminTransferTopupAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminTransferTopupModel> transferTopups;

    public AdminTransferTopupAdapter(Context mContext, List<AdminTransferTopupModel> transferTopups) {
        this.mContext = mContext;
        this.transferTopups = transferTopups;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_transfer_topup, parent, false);
        return new AdminTransferTopupAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminTransferTopupModel transferTopup = transferTopups.get(position);

        if (transferTopup.getStatus_proses().equals("Belum di proses")){
            holder.icStatus.setVisibility(View.VISIBLE);
        } else {
            holder.icStatus.setVisibility(View.INVISIBLE);
        }
        holder.tvNama.setText(transferTopup.getNama_user());
        holder.tvId.setText(transferTopup.getId_user());
        holder.tvStatusProses.setText(transferTopup.getStatus_proses());
        holder.tvTglTopup.setText(transferTopup.getTgl_topup());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AdminDetailTopupActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return transferTopups.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvId, tvStatusProses, tvTglTopup;
        ImageView icStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama_user);
            tvId = itemView.findViewById(R.id.tv_id_user);
            tvStatusProses = itemView.findViewById(R.id.tv_status_proses);
            tvTglTopup = itemView.findViewById(R.id.tv_tgl_topup);
            icStatus = itemView.findViewById(R.id.icon_status);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
