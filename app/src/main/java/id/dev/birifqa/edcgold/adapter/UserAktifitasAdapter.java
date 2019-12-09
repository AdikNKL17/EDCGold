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
import id.dev.birifqa.edcgold.model.UserAktifitasModel;
import id.dev.birifqa.edcgold.model.UserHistoryModel;
import id.dev.birifqa.edcgold.utils.Helper;

public class UserAktifitasAdapter extends RecyclerView.Adapter<UserAktifitasAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<UserAktifitasModel> aktifitasModels;

    public UserAktifitasAdapter(Context mContext, List<UserAktifitasModel> aktifitasModels) {
        this.mContext = mContext;
        this.aktifitasModels = aktifitasModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_aktifitas, parent, false);
        return new UserAktifitasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UserAktifitasModel aktifitas = aktifitasModels.get(position);

        holder.tvNominal.setText(Helper.getNumberFormatCurrency(Integer.parseInt(aktifitas.getNominal())));
        holder.tvTitle.setText(aktifitas.getDescription());
        holder.tvDate.setText(aktifitas.getCreated_at());

        if (aktifitas.getStatus().equals("0")){
            holder.tvStatus.setText("Sedang di proses");
            holder.tvNominal.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else if (aktifitas.getStatus().equals("2")){
            holder.tvStatus.setText("Transaksi Selesai");
            holder.tvNominal.setTextColor(mContext.getResources().getColor(R.color.mdtp_ampm_text_color));
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.mdtp_ampm_text_color));
        } else {
            holder.tvStatus.setText("Transaksi di tolak");
            holder.tvNominal.setTextColor(mContext.getResources().getColor(R.color.error_color));
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.error_color));
        }
    }

    @Override
    public int getItemCount() {
        return aktifitasModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus, tvDate, tvNominal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvNominal = itemView.findViewById(R.id.tv_nominal);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
