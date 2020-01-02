package id.dev.birifqa.edcgold.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_admin.AdminDetailListTopupActivity;
import id.dev.birifqa.edcgold.model.admin.AdminReportTopupModel;

public class AdminReportTopupAdapter extends RecyclerView.Adapter<AdminReportTopupAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminReportTopupModel> topupModels;

    public AdminReportTopupAdapter(Context mContext, List<AdminReportTopupModel> topupModels) {
        this.mContext = mContext;
        this.topupModels = topupModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_report_topup, parent, false);
        return new AdminReportTopupAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminReportTopupModel aktifitas = topupModels.get(position);
        holder.tvId.setText(aktifitas.getUserId());
        holder.tvName.setText(aktifitas.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AdminDetailListTopupActivity.class);
                intent.putExtra("ID", aktifitas.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topupModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        TextView tvName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvId = itemView.findViewById(R.id.tv_id_topup);
        }
    }

    public void setFilter(List<AdminReportTopupModel> newList){
        topupModels=new ArrayList<>();
        topupModels.addAll(newList);
        notifyDataSetChanged();
    }
}
