package id.dev.birifqa.edcgold.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.model.UserHistoryModel;
import id.dev.birifqa.edcgold.model.UserNotificationModel;

public class UserHistoryAdapter extends RecyclerView.Adapter<UserHistoryAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<UserHistoryModel> historyModels;

    public UserHistoryAdapter(Context mContext, List<UserHistoryModel> historyModels) {
        this.mContext = mContext;
        this.historyModels = historyModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_history, parent, false);
        return new UserHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UserHistoryModel history = historyModels.get(position);

        if (position % 2 == 0){
            holder.itemView.setBackgroundResource(R.color.colorGray);
        }
        holder.tvTitle.setText(history.getTitle());
        holder.tvStatus.setText(history.getStatus());
        holder.tvDate.setText(history.getDate());
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus, tvDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvStatus = itemView.findViewById(R.id.tv_proses);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
