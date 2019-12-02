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

public class AdminReportAktifitasAdapter extends RecyclerView.Adapter<AdminReportAktifitasAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminReportAktifitasModel> aktifitasModels;

    public AdminReportAktifitasAdapter(Context mContext, List<AdminReportAktifitasModel> aktifitasModels) {
        this.mContext = mContext;
        this.aktifitasModels = aktifitasModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_report_aktifitas, parent, false);
        return new AdminReportAktifitasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminReportAktifitasModel aktifitas = aktifitasModels.get(position);
        holder.tvId.setText(aktifitas.getId());
    }

    @Override
    public int getItemCount() {
        return aktifitasModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id_aktifitas);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
