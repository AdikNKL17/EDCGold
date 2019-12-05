package id.dev.birifqa.edcgold.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.model.admin.AdminUserMiningHistoryModel;

public class AdminMiningUserAdapter extends RecyclerView.Adapter<AdminMiningUserAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminUserMiningHistoryModel> historyModels;

    public AdminMiningUserAdapter(Context mContext, List<AdminUserMiningHistoryModel> historyModels) {
        this.mContext = mContext;
        this.historyModels = historyModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_mining_user, parent, false);
        return new AdminMiningUserAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminUserMiningHistoryModel history = historyModels.get(position);

        holder.tvCoin.setText(history.getCoin());
        holder.tvDate.setText(history.getDate());
        holder.tvProses.setText(history.getProses());

        if (history.getProses().equals("Proses")){
            holder.imgProses.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_mining_proses));
            holder.imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_mining_mining));
        } else{
            holder.imgProses.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_mining_success));
            holder.imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_dompet_mining));
        }
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCoin, tvDate, tvProses;
        ImageView imgStatus, imgProses;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCoin = itemView.findViewById(R.id.tv_coin);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvProses = itemView.findViewById(R.id.tv_proses);
            imgStatus = itemView.findViewById(R.id.img_status);
            imgProses = itemView.findViewById(R.id.img_proses);

        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
