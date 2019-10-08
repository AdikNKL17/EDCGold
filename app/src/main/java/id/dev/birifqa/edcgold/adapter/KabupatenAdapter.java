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
import id.dev.birifqa.edcgold.model.KabupatenModel;

public class KabupatenAdapter extends RecyclerView.Adapter<KabupatenAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<KabupatenModel> kabupatenModels;
    private Dialog dialog;
    private TextInputEditText text;

    public KabupatenAdapter(Context mContext, List<KabupatenModel> kabupatenModels, Dialog dialog, TextInputEditText text) {
        this.mContext = mContext;
        this.kabupatenModels = kabupatenModels;
        this.dialog = dialog;
        this.text = text;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new KabupatenAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final KabupatenModel kabupaten = kabupatenModels.get(position);

        holder.tvName.setText(kabupaten.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText(kabupaten.getName());
                RegisterActivity.idKab = kabupaten.getRegion_id();
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return kabupatenModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
