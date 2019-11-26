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
import id.dev.birifqa.edcgold.model.AdminSewaMiningModel;

public class AdminSewaMiningAdapter extends RecyclerView.Adapter<AdminSewaMiningAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminSewaMiningModel> sewaMinings;

    public AdminSewaMiningAdapter(Context mContext, List<AdminSewaMiningModel> sewaMinings) {
        this.mContext = mContext;
        this.sewaMinings = sewaMinings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_sewa_mining, parent, false);
        return new AdminSewaMiningAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminSewaMiningModel sewaMining = sewaMinings.get(position);

        holder.tvNama.setText(sewaMining.getNama_user());
        holder.tvId.setText(sewaMining.getId_user());
        holder.tvNoTransaksi.setText(sewaMining.getNo_transaksi());
        holder.tvTglTransaksi.setText(sewaMining.getTgl_transaksi());

    }

    @Override
    public int getItemCount() {
        return sewaMinings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvId, tvNoTransaksi, tvTglTransaksi;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama_user);
            tvId = itemView.findViewById(R.id.tv_id_user);
            tvNoTransaksi = itemView.findViewById(R.id.tv_no_transaksi);
            tvTglTransaksi = itemView.findViewById(R.id.tv_tgl_transaksi);

        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}