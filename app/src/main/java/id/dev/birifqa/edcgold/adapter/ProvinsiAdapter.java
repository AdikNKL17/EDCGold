package id.dev.birifqa.edcgold.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.RegisterActivity;
import id.dev.birifqa.edcgold.model.ProvinsiModel;

public class ProvinsiAdapter  extends RecyclerView.Adapter<ProvinsiAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<ProvinsiModel> provinsiModels;
    private Dialog dialog;
    private TextInputEditText text;

    public ProvinsiAdapter(Context mContext, List<ProvinsiModel> provinsiModels, Dialog dialog, TextInputEditText text) {
        this.mContext = mContext;
        this.provinsiModels = provinsiModels;
        this.dialog = dialog;
        this.text = text;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new ProvinsiAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ProvinsiModel provinsi = provinsiModels.get(position);

        holder.tvName.setText(provinsi.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText(provinsi.getName());
                RegisterActivity.idProv = provinsi.getId();
                dialog.dismiss();

            }
        });
    }

    @Override
    public int getItemCount() {
        return provinsiModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
