package id.dev.birifqa.edcgold.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.RegisterActivity;
import id.dev.birifqa.edcgold.activity_user.UbahAlamatActivity;
import id.dev.birifqa.edcgold.model.address.KecamatanModel;

public class KecamatanAdapter extends RecyclerView.Adapter<KecamatanAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<KecamatanModel> kecamatanModels;
    private Dialog dialog;
    private TextInputEditText text;
    private String status;

    public KecamatanAdapter(Context mContext, List<KecamatanModel> kecamatanModels, Dialog dialog, TextInputEditText text, String status) {
        this.mContext = mContext;
        this.kecamatanModels = kecamatanModels;
        this.dialog = dialog;
        this.text = text;
        this.status = status;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new KecamatanAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final KecamatanModel kecamatan = kecamatanModels.get(position);

        holder.tvName.setText(kecamatan.getName());

        if (status.equals("1")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    text.setText(kecamatan.getName());
                    RegisterActivity.idKec = kecamatan.getId();
                    dialog.dismiss();

                }
            });
        } else if (status.equals("2")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    text.setText(kecamatan.getName());
                    UbahAlamatActivity.idKec = kecamatan.getId();
                    dialog.dismiss();
                }
            });
        } else {
            Toast.makeText(mContext, "Status Unauthenticated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return kecamatanModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
        }
    }


    public void setFilter(List<KecamatanModel> newList){
        kecamatanModels=new ArrayList<>();
        kecamatanModels.addAll(newList);
        notifyDataSetChanged();
    }
}
