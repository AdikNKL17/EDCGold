package id.dev.birifqa.edcgold.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.model.admin.AdminHistoryModel;

public class AdminHistoryAdapter extends RecyclerView.Adapter<AdminHistoryAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminHistoryModel> historyModels;

    public AdminHistoryAdapter(Context mContext, List<AdminHistoryModel> historyModels) {
        this.mContext = mContext;
        this.historyModels = historyModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_history, parent, false);
        return new AdminHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminHistoryModel history = historyModels.get(position);

        if (position % 2 == 0){
            holder.itemView.setBackgroundResource(R.color.colorGray);
        }
        holder.tvStatus.setText(history.getStatus());
        holder.tvDate.setText(history.getDate());
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus, tvDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStatus = itemView.findViewById(R.id.tv_status);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
