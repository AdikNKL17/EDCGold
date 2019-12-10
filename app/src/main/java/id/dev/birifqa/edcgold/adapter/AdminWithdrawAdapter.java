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
import id.dev.birifqa.edcgold.activity_admin.AdminDetailWithdrawActivity;
import id.dev.birifqa.edcgold.model.admin.AdminWithdrawModel;

public class AdminWithdrawAdapter extends RecyclerView.Adapter<AdminWithdrawAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminWithdrawModel> withdrawModels;

    public AdminWithdrawAdapter(Context mContext, List<AdminWithdrawModel> withdrawModels) {
        this.mContext = mContext;
        this.withdrawModels = withdrawModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_withdraw, parent, false);
        return new AdminWithdrawAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminWithdrawModel withdrawModel = withdrawModels.get(position);

        if (withdrawModel.getStatus().equals("0")){
            holder.tvStatusProses.setText("Belum di proses");
            holder.icStatus.setVisibility(View.VISIBLE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AdminDetailWithdrawActivity.class);
                    intent.putExtra("id_withdraw", withdrawModel.getId());
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.tvStatusProses.setText("Berhasil di proses");
            holder.icStatus.setVisibility(View.INVISIBLE);
        }
        holder.tvNama.setText(withdrawModel.getName());
        holder.tvId.setText("ID. "+withdrawModel.getUserid());

        holder.tvTglWithdraw.setText(withdrawModel.getDate());



    }

    @Override
    public int getItemCount() {
        return withdrawModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvId, tvStatusProses, tvTglWithdraw;
        ImageView icStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama_user);
            tvId = itemView.findViewById(R.id.tv_id_user);
            tvStatusProses = itemView.findViewById(R.id.tv_status_proses);
            tvTglWithdraw = itemView.findViewById(R.id.tv_tgl_withdraw);
            icStatus = itemView.findViewById(R.id.icon_status);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
