package id.dev.birifqa.edcgold.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.model.HistoryMiningModel;
import id.dev.birifqa.edcgold.model.UserMiningModel;
import id.dev.birifqa.edcgold.model.admin.AdminUserMiningHistoryModel;

public class UserMiningAdapter extends RecyclerView.Adapter<UserMiningAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<HistoryMiningModel> miningModels;

    public UserMiningAdapter(Context mContext, List<HistoryMiningModel> miningModels) {
        this.mContext = mContext;
        this.miningModels = miningModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_mining, parent, false);
        return new UserMiningAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final HistoryMiningModel mining = miningModels.get(position);

        holder.tvCoin.setText(mining.getCoin_balance());
        holder.tvDate.setText(mining.getCreated_at());
        holder.tvProses.setText("Sukses");

        holder.imgProses.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_mining_success));
        holder.imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_dompet_mining));
//        if (mining.getProses().equals("Proses")){
//            holder.imgProses.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_mining_proses));
//            holder.imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_mining_mining));
//        } else{
//            holder.imgProses.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_mining_success));
//            holder.imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_dompet_mining));
//        }
    }

    @Override
    public int getItemCount() {
        return miningModels.size();
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
