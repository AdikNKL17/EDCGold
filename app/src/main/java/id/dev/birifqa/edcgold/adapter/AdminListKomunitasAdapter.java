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
import id.dev.birifqa.edcgold.model.admin.AdminListKomunitasModel;

public class AdminListKomunitasAdapter extends RecyclerView.Adapter<AdminListKomunitasAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminListKomunitasModel> komunitasModels;

    public AdminListKomunitasAdapter(Context mContext, List<AdminListKomunitasModel> komunitasModels) {
        this.mContext = mContext;
        this.komunitasModels = komunitasModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_list_komunitas, parent, false);
        return new AdminListKomunitasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminListKomunitasModel komunitas = komunitasModels.get(position);
        holder.tvNamaKomunitas.setText(komunitas.getNama_komunitas());
        holder.tvNamaKetua.setText(komunitas.getNama_ketua());
        holder.tvAlamat.setText(komunitas.getAlamat());

    }

    @Override
    public int getItemCount() {
        return komunitasModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKomunitas, tvNamaKetua, tvAlamat;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaKomunitas = itemView.findViewById(R.id.tv_nama_komunitas);
            tvNamaKetua = itemView.findViewById(R.id.tv_nama_ketua);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);

        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
