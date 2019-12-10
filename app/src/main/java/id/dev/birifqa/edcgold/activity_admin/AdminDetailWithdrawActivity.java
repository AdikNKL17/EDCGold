package id.dev.birifqa.edcgold.activity_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.FileUtil;
import id.dev.birifqa.edcgold.utils.Helper;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDetailWithdrawActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;
    private TextView tvId, tvNama, tvNominal, tvCoin;
    private AppCompatButton btnKirim;
    private ImageView btnCamera, imgPhoto;
    private Toolbar toolbar;

    private static final int PICK_IMAGE_REQUEST = 201;
    private static final int REQUEST_CAPTURE_IMAGE = 209;

    private static final int PERMISSION_REQUEST_CODE = 1100;

    String[] appPermission = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    String bankName = "";
    String accoutName = "";
    String bankNumber = "";
    String nominal = "";
    String encodedImage = "";
    String mCurrentPhotoPath = "";
    String filePath = "";
    String filePath1;
    private Uri picUri;
    private File actualImage;
    private File compressedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_withdraw);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(AdminDetailWithdrawActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        tvId = findViewById(R.id.tv_id);
        tvNama = findViewById(R.id.tv_nama);
        tvNominal = findViewById(R.id.tv_nominal);
        tvCoin = findViewById(R.id.tv_coin);
        btnKirim = findViewById(R.id.btn_kirim);
        btnCamera = findViewById(R.id.btn_camera);
        imgPhoto = findViewById(R.id.img_foto_struk);
    }
    private void onAction(){
        getDetailWithdraw();
        getUserBank();

        if (checkAndRequestPermission()){

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AdminDetailWithdrawActivity.this.finish();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bankName.equals("") || !accoutName.equals("") || !bankNumber.equals("") || !nominal.equals("")){
                    encodeImage();

                    withdrawProcess();
                } else {
                    Toast.makeText(AdminDetailWithdrawActivity.this, "Terjadi masalah pada sistem", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void withdrawProcess(){
        Intent intent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.withdrawProcess(Session.get("token"), intent.getStringExtra("id_withdraw"), bankName, accoutName, bankNumber, nominal,getDate(), encodedImage, AdminDetailWithdrawActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        Toast.makeText(AdminDetailWithdrawActivity.this, "Berhasil melakukan withdraw", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        AdminDetailWithdrawActivity.this.finish();
                    } else {
                        Toast.makeText(AdminDetailWithdrawActivity.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailWithdrawActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailWithdrawActivity.this, call, cBack, false, "Loading");
    }

    private void getDetailWithdraw(){
        Intent intent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestDetailWithdraw(Session.get("token"), intent.getStringExtra("id_withdraw"), AdminDetailWithdrawActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    tvId.setText(dataObject.getString("userid"));
                    tvNama.setText(dataObject.getString("name"));
                    tvNominal.setText(Helper.getNumberFormatCurrency(Integer.parseInt(dataObject.getString("nominal"))));
                    tvCoin.setText(dataObject.getString("amount_coin"));

                    nominal = dataObject.getString("nominal");
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailWithdrawActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailWithdrawActivity.this, call, cBack, false, "Loading");
    }

    private void getUserBank(){
        Intent intent = getIntent();
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestRekeningBank(Session.get("token"), AdminDetailWithdrawActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("success")){
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        if (dataArray.length() > 0){
                            bankName = dataArray.getJSONObject(0).getString("bank_name");
                            accoutName = dataArray.getJSONObject(0).getString("account_name");
                            bankNumber = dataArray.getJSONObject(0).getString("bank_number");

                        }

                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(AdminDetailWithdrawActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(AdminDetailWithdrawActivity.this, call, cBack, false, "Loading");
    }

    private String getDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public boolean checkAndRequestPermission(){
        List<String> listPermissionNeeded = new ArrayList<>();
        for (String perm: appPermission){
            if (ContextCompat.checkSelfPermission(AdminDetailWithdrawActivity.this, perm) != PackageManager.PERMISSION_GRANTED){
                listPermissionNeeded.add(perm);
            }
        }
        if (!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(AdminDetailWithdrawActivity.this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE){
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;

            for (int i=0; i<grantResults.length;i++){
                if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }

            if (deniedCount == 0){

            } else {
                for (Map.Entry<String, Integer> entry : permissionResults.entrySet()){
                    String permName = entry.getKey();
                    int permResult = entry.getValue();

                    if (ActivityCompat.shouldShowRequestPermissionRationale(AdminDetailWithdrawActivity.this, permName)){
                        androidx.appcompat.app.AlertDialog.Builder alertDialog= new androidx.appcompat.app.AlertDialog.Builder(AdminDetailWithdrawActivity.this);
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("This App need Camera Permission to work without and problems");
                        alertDialog.setPositiveButton("YES, Granted permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                checkAndRequestPermission();
                            }
                        });
                        alertDialog.setNegativeButton("NO, Cancel Taking Picture", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                onBackPressed();
                                AdminDetailWithdrawActivity.this.finish();
                            }
                        });
                        alertDialog.setCancelable(false);
                        androidx.appcompat.app.AlertDialog alert = alertDialog.create();
                        alert.show();

                    }
                }
            }
        }
    }

    private void encodeImage(){
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("base64", encodedImage);
    }

    public void chooseImage() {
        Dialog dialog = new Dialog(AdminDetailWithdrawActivity.this);
        dialog.setContentView(R.layout.dialog_photo);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnCamera, btnGallery;
        ImageButton btnClose;

        btnCamera = dialog.findViewById(R.id.button_camera);
        btnGallery = dialog.findViewById(R.id.button_gallery);
        btnClose = dialog.findViewById(R.id.btn_close);

        btnClose.setOnClickListener(view -> {
            dialog.dismiss();
        });

        btnCamera.setOnClickListener(v -> {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
            startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
            dialog.dismiss();

        });

        btnGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
            dialog.dismiss();

        });
        dialog.show();
    }

    @SuppressLint("CheckResult")
    public void compressImage() {
        if (actualImage == null) {
            showError("Please choose an image!");
        } else {
            new Compressor(AdminDetailWithdrawActivity.this)
                    .compressToFileAsFlowable(actualImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) {
                            compressedImage = file;
                            setCompressedImage();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            throwable.printStackTrace();
                            showError(throwable.getMessage());
                        }
                    });
        }
    }

    private void setCompressedImage() {
        filePath = compressedImage.getPath();
        Log.d("file path", filePath);
//        photo.setVisibility(View.VISIBLE);
//        Helper.button(R.id.text_plus, view).setVisibility(View.INVISIBLE);
//        Helper.tv(R.id.text_photo, view).setVisibility(View.INVISIBLE);
        Glide.with(AdminDetailWithdrawActivity.this).load(BitmapFactory.decodeFile(compressedImage.getAbsolutePath())).into(imgPhoto);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                showError("Failed to open picture!");
                return;
            }
            try {
                actualImage = FileUtil.from(AdminDetailWithdrawActivity.this, data.getData());
                compressImage();
            } catch (IOException e) {
                showError("Failed to read picture data!");
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            mCurrentPhotoPath = getImagePath();
            if (mCurrentPhotoPath.isEmpty()){
                Toast.makeText(AdminDetailWithdrawActivity.this, "Sorry, try to take picture again !", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("image path: ",mCurrentPhotoPath);
                compressImage();
//                Toast.makeText(this, "image path: " +selectedImagePath, Toast.LENGTH_SHORT).show();
//                Glide.with(getApplicationContext()).load(BitmapFactory.decodeFile(selectedImagePath)).into(photo);

            }
        }
    }

    public Uri setImageUri() {
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        actualImage = file.getAbsoluteFile();
        mCurrentPhotoPath = file.getAbsolutePath();
        return imgUri;
    }
    public String getImagePath() {
        return mCurrentPhotoPath;
    }

    public void showError(String errorMessage) {
        Toast.makeText(AdminDetailWithdrawActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
