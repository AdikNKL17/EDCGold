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
import id.dev.birifqa.edcgold.model.admin.AdminUserMiningModel;

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

        holder.tvNama.setText(userMining.getName());
        holder.tvId.setText("ID - "+userMining.getUserid());
        holder.tvMulaiMining.setText(userMining.getStart_mining());
        holder.tvSisaWaktu.setText(userMining.getEnd_mining());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AdminDetailMiningActivity.class);
                intent.putExtra("id_mining", userMining.getId());
                mContext.startActivity(intent);
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
