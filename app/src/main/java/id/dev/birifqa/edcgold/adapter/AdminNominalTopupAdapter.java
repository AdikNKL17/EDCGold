package id.dev.birifqa.edcgold.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.model.NominalTopupModel;
import id.dev.birifqa.edcgold.model.admin.AdminListFaqModel;

public class AdminNominalTopupAdapter extends RecyclerView.Adapter<AdminNominalTopupAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<NominalTopupModel> topupModels;

    public AdminNominalTopupAdapter(Context mContext, List<NominalTopupModel> topupModels) {
        this.mContext = mContext;
        this.topupModels = topupModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nominal_topup, parent, false);
        return new AdminNominalTopupAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NominalTopupModel faq = topupModels.get(position);
        holder.tvLabel.setText(faq.getLabel());
        holder.etNominal.setText(faq.getNominal());

    }

    @Override
    public int getItemCount() {
        return topupModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvLabel;
        EditText etNominal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLabel = itemView.findViewById(R.id.tv_label);
            etNominal = itemView.findViewById(R.id.et_nominal);
        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
