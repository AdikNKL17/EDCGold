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
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_admin.AdminUbahKomunitasActivity;
import id.dev.birifqa.edcgold.model.admin.AdminListKomunitasModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminListKomunitasAdapter extends RecyclerView.Adapter<AdminListKomunitasAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<AdminListKomunitasModel> komunitasModels;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    public AdminListKomunitasAdapter(Context mContext, List<AdminListKomunitasModel> komunitasModels) {
        this.mContext = mContext;
        this.komunitasModels = komunitasModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_list_komunitas, parent, false);

        dialog = new SpotsDialog.Builder().setContext(mContext).build();

        return new AdminListKomunitasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AdminListKomunitasModel komunitas = komunitasModels.get(position);
        holder.tvNamaKomunitas.setText(komunitas.getNama_komunitas());
        holder.tvNamaKetua.setText(komunitas.getNama_ketua());
        holder.tvAlamat.setText(komunitas.getAlamat());

        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, holder.btnOption);
                popup.inflate(R.menu.option_komunitas);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.komunitas_edit:
                                Intent intent = new Intent(mContext, AdminUbahKomunitasActivity.class);
                                intent.putExtra("ID_KOMUNITAS", komunitas.getId());
                                mContext.startActivity(intent);
                                break;
                            case R.id.komunitas_hapus:
                                delete(komunitas.getId(), position);
                                break;

                        }
                        return false;
                    }

                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return komunitasModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKomunitas, tvNamaKetua, tvAlamat;
        ImageView btnOption;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKomunitas = itemView.findViewById(R.id.tv_nama_komunitas);
            tvNamaKetua = itemView.findViewById(R.id.tv_nama_ketua);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            btnOption = itemView.findViewById(R.id.btn_option);

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
