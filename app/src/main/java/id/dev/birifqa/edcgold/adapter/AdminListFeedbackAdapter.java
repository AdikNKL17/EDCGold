package id.dev.birifqa.edcgold.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.model.AdminListFeedbackModel;
import id.dev.birifqa.edcgold.model.AdminUserModel;

public class AdminListFeedbackAdapter extends RecyclerView.Adapter<AdminListFeedbackAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminListFeedbackModel> feedbackModels;

    public AdminListFeedbackAdapter(Context mContext, List<AdminListFeedbackModel> feedbackModels) {
        this.mContext = mContext;
        this.feedbackModels = feedbackModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_list_feedback, parent, false);
        return new AdminListFeedbackAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminListFeedbackModel feedback = feedbackModels.get(position);
        holder.tvId.setText(feedback.getId());
        if (feedback.getStatus().equals("1")){
            holder.tvStatus.setText("Status: Dijawab");
        } else {
            holder.tvStatus.setText("Status: Belum Dijawab");
        }

    }

    @Override
    public int getItemCount() {
        return feedbackModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        TextView tvStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id_user);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
