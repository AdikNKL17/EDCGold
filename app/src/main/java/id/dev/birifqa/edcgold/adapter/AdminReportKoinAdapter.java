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
import id.dev.birifqa.edcgold.activity_admin.AdminDetailKoinActivity;
import id.dev.birifqa.edcgold.model.admin.AdminReportKoinModel;

public class AdminReportKoinAdapter extends RecyclerView.Adapter<AdminReportKoinAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminReportKoinModel> adminReportKoinModels;

    public AdminReportKoinAdapter(Context mContext, List<AdminReportKoinModel> adminReportKoinModels) {
        this.mContext = mContext;
        this.adminReportKoinModels = adminReportKoinModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_report_koin, parent, false);
        return new AdminReportKoinAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminReportKoinModel koin = adminReportKoinModels.get(position);

        if (koin.getStatus().equals("1")){
            holder.imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_coin_masuk));
        } else {
            holder.imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_coin_keluar));
        }
        holder.tvCoin.setText(koin.getCoin());
        holder.tvDate.setText(koin.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AdminDetailKoinActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminReportKoinModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCoin, tvDate;
        ImageView imgStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStatus = itemView.findViewById(R.id.img_status);
            tvCoin = itemView.findViewById(R.id.tv_coin);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
