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
import id.dev.birifqa.edcgold.model.admin.AdminListFaqModel;

public class AdminListFaqAdapter extends RecyclerView.Adapter<AdminListFaqAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminListFaqModel> faqModels;

    public AdminListFaqAdapter(Context mContext, List<AdminListFaqModel> faqModels) {
        this.mContext = mContext;
        this.faqModels = faqModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_list_faq, parent, false);
        return new AdminListFaqAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminListFaqModel faq = faqModels.get(position);
        holder.tvTitle.setText(faq.getTitle());
        holder.tvNama.setText(faq.getNama());
        holder.tvDate.setText(faq.getDate());

    }

    @Override
    public int getItemCount() {
        return faqModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvNama, tvDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvDate = itemView.findViewById(R.id.tv_date);

        }
    }


//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
