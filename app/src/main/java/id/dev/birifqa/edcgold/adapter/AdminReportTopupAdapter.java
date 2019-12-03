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
import id.dev.birifqa.edcgold.activity_admin.AdminDetailListTopupActivity;
import id.dev.birifqa.edcgold.model.AdminReportTopupModel;
import id.dev.birifqa.edcgold.model.AdminReportWithdrawModel;

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
        holder.tvId.setText(aktifitas.getId_transaksi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AdminDetailListTopupActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return topupModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id_topup);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
