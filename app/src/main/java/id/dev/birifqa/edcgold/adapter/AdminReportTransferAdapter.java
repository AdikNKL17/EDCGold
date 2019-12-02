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
import id.dev.birifqa.edcgold.model.AdminReportTopupModel;
import id.dev.birifqa.edcgold.model.AdminReportTransferModel;

public class AdminReportTransferAdapter extends RecyclerView.Adapter<AdminReportTransferAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminReportTransferModel> transferModels;

    public AdminReportTransferAdapter(Context mContext, List<AdminReportTransferModel> transferModels) {
        this.mContext = mContext;
        this.transferModels = transferModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_report_transfer, parent, false);
        return new AdminReportTransferAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminReportTransferModel aktifitas = transferModels.get(position);
        holder.tvId.setText(aktifitas.getId_transaksi());
    }

    @Override
    public int getItemCount() {
        return transferModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id_transfer);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
