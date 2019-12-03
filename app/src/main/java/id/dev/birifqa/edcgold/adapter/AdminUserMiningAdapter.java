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
import id.dev.birifqa.edcgold.activity_admin.AdminDetailMiningActivity;
import id.dev.birifqa.edcgold.model.AdminSewaMiningModel;
import id.dev.birifqa.edcgold.model.AdminUserMiningModel;

public class AdminUserMiningAdapter extends RecyclerView.Adapter<AdminUserMiningAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminUserMiningModel> userMinings;

    public AdminUserMiningAdapter(Context mContext, List<AdminUserMiningModel> userMinings) {
        this.mContext = mContext;
        this.userMinings = userMinings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_user_mining, parent, false);
        return new AdminUserMiningAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminUserMiningModel userMining = userMinings.get(position);

        holder.tvNama.setText(userMining.getNama_user());
        holder.tvId.setText(userMining.getId_user());
        holder.tvMulaiMining.setText(userMining.getMulai_mining());
        holder.tvSisaWaktu.setText(userMining.getSisa_waktu());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AdminDetailMiningActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userMinings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvId, tvMulaiMining, tvSisaWaktu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama_user);
            tvId = itemView.findViewById(R.id.tv_id_user);
            tvMulaiMining = itemView.findViewById(R.id.tv_mulai_mining);
            tvSisaWaktu = itemView.findViewById(R.id.tv_sisa_waktu);

        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
