package id.dev.birifqa.edcgold.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_admin.AdminUbahKomunitasActivity;
import id.dev.birifqa.edcgold.activity_user.DetailKomunitasActivity;
import id.dev.birifqa.edcgold.model.KomunitasModel;
import id.dev.birifqa.edcgold.model.admin.AdminListKomunitasModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KomunitasAdapter extends RecyclerView.Adapter<KomunitasAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<KomunitasModel> komunitasModels;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    public KomunitasAdapter(Context mContext, List<KomunitasModel> komunitasModels) {
        this.mContext = mContext;
        this.komunitasModels = komunitasModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_komunitas, parent, false);

        dialog = new SpotsDialog.Builder().setContext(mContext).build();

        return new KomunitasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final KomunitasModel komunitas = komunitasModels.get(position);
        holder.tvNamaKomunitas.setText(komunitas.getKomunitas_name());
        holder.tvTitle.setText(komunitas.getTitle());
        holder.tvConten.setText(komunitas.getContent());
        holder.tvDate.setText(komunitas.getDate());

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailKomunitasActivity.class);
                intent.putExtra("ID_KOMUNITAS_POST", komunitas.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return komunitasModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKomunitas, tvTitle, tvConten, tvDate;
        AppCompatButton btnMore;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKomunitas = itemView.findViewById(R.id.tv_nama_komunitas);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvConten = itemView.findViewById(R.id.tv_content);
            tvDate = itemView.findViewById(R.id.tv_date);

            btnMore = itemView.findViewById(R.id.btn_more);

        }
    }

    private void deleteKomunitas(String id, int position){
        dialog.dismiss();
        Call<ResponseBody> call = ParamReq.deleteKomunitas(Session.get("token"), id, mContext);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    boolean handle = Handle.handleDeleteKomunitas(response.body().string(), mContext);
                    if (handle) {
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(mContext, "Gagal menghapus data bank", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(mContext, call, cBack, 1, false);
            }
        };

        Api.enqueueWithRetry(mContext, call, cBack, false, "Loading");
    }

    private void delete(String id, int position){
        final Dialog dialog1 = new Dialog(mContext);
        dialog1.setContentView(R.layout.dialog_delete);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnYes, btnCancel;

        btnYes = dialog1.findViewById(R.id.button_yes);
        btnCancel = dialog1.findViewById(R.id.button_cancel);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteKomunitas(id, position);
                komunitasModels.remove(position);
                notifyItemRemoved(position);
                dialog1.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        dialog1.show();
    }

//    public void setFilter(List<KabupatenModel> newList){
//        kabupatenModels=new ArrayList<>();
//        kabupatenModels.addAll(newList);
//        notifyDataSetChanged();
//    }
}
