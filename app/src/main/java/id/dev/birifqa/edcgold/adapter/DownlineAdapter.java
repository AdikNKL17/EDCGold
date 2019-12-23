package id.dev.birifqa.edcgold.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_admin.AdminDownlineChildActivity;
import id.dev.birifqa.edcgold.model.DownlineModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownlineAdapter extends RecyclerView.Adapter<DownlineAdapter.MyViewHolder> {

    private View itemView;
    private Context mContext;
    private List<DownlineModel> downlineModels;
    private Callback<ResponseBody> cBack;
    private AlertDialog dialog;

    public DownlineAdapter(Context mContext, List<DownlineModel> downlineModels) {
        this.mContext = mContext;
        this.downlineModels = downlineModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_downline, parent, false);

        dialog = new SpotsDialog.Builder().setContext(mContext).build();

        return new DownlineAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DownlineModel model = downlineModels.get(position);
        holder.tvUserId.setText(model.getUserid());
        holder.tvName.setText(model.getName());

        if (model.getHasDownline().equals("1")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, AdminDownlineChildActivity.class);
                    intent.putExtra("idparent", model.getUserid_parent());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return downlineModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserId, tvName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tv_user_id_downlin);
            tvName = itemView.findViewById(R.id.tv_nama_downline);

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

}
