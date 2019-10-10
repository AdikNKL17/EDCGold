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
import id.dev.birifqa.edcgold.model.address.KabupatenModel;

public class KabupatenAdapter extends RecyclerView.Adapter<KabupatenAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<KabupatenModel> kabupatenModels;
    private Dialog dialog;
    private TextInputEditText text;
    private String status;

    public KabupatenAdapter(Context mContext, List<KabupatenModel> kabupatenModels, Dialog dialog, TextInputEditText text, String status) {
        this.mContext = mContext;
        this.kabupatenModels = kabupatenModels;
        this.dialog = dialog;
        this.text = text;
        this.status = status;
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

        if (status.equals("1")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    text.setText(kabupaten.getName());
                    RegisterActivity.idKab = kabupaten.getId();
                    dialog.dismiss();

                }
            });
        } else if (status.equals("2")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    text.setText(kabupaten.getName());
                    UbahAlamatActivity.idKab = kabupaten.getId();
                    dialog.dismiss();
                }
            });
        } else {
            Toast.makeText(mContext, "Status Unauthenticated", Toast.LENGTH_SHORT).show();
        }
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


    public void setFilter(List<KabupatenModel> newList){
        kabupatenModels=new ArrayList<>();
        kabupatenModels.addAll(newList);
        notifyDataSetChanged();
    }
}
