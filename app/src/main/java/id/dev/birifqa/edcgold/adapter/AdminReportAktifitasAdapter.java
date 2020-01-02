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
import id.dev.birifqa.edcgold.activity_admin.AdminDetailKoinActivity;
import id.dev.birifqa.edcgold.model.admin.AdminReportAktifitasModel;

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
        holder.tvId.setText(aktifitas.getTransaction_code());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AdminDetailKoinActivity.class);
                intent.putExtra("ID", aktifitas.getId());
                mContext.startActivity(intent);
            }
        });
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


    public void setFilter(List<AdminReportAktifitasModel> newList){
        aktifitasModels=new ArrayList<>();
        aktifitasModels.addAll(newList);
        notifyDataSetChanged();
    }
}
