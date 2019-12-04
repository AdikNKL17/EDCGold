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
import id.dev.birifqa.edcgold.model.UserNotificationModel;
import id.dev.birifqa.edcgold.model.admin.AdminNotificationModel;

public class UserNotificationAdapter extends RecyclerView.Adapter<UserNotificationAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<UserNotificationModel> notificationModels;

    public UserNotificationAdapter(Context mContext, List<UserNotificationModel> notificationModels) {
        this.mContext = mContext;
        this.notificationModels = notificationModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_notifikasi, parent, false);
        return new UserNotificationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UserNotificationModel notification = notificationModels.get(position);

        if (position % 2 == 0){
            holder.itemView.setBackgroundResource(R.color.colorGray);
        }
        holder.tvStatus.setText(notification.getStatus());
        holder.tvDate.setText(notification.getDate());
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
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
