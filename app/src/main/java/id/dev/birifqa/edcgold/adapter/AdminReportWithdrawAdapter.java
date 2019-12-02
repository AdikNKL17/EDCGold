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
import id.dev.birifqa.edcgold.model.AdminReportAktifitasModel;
import id.dev.birifqa.edcgold.model.AdminReportWithdrawModel;

public class AdminReportWithdrawAdapter extends RecyclerView.Adapter<AdminReportWithdrawAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminReportWithdrawModel> withdrawModels;

    public AdminReportWithdrawAdapter(Context mContext, List<AdminReportWithdrawModel> withdrawModels) {
        this.mContext = mContext;
        this.withdrawModels = withdrawModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_report_withdraw, parent, false);
        return new AdminReportWithdrawAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminReportWithdrawModel aktifitas = withdrawModels.get(position);
        holder.tvId.setText(aktifitas.getId_transaksi());
    }

    @Override
    public int getItemCount() {
        return withdrawModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id_withdraw);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
