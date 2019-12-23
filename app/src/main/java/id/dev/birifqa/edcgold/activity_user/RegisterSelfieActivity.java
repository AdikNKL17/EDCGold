package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.FileUtil;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CameraCharacteristics;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class RegisterSelfieActivity extends AppCompatActivity {

    private ImageView btnCameraSelfie, imageSelfie;
    private AppCompatButton btnDaftar;
    private Toolbar toolbar;
    private AlertDialog dialog;


    private static final int PICK_IMAGE_REQUEST = 201;
    private static final int REQUEST_CAPTURE_IMAGE = 209;

    String encodedImage = "";
    String mCurrentPhotoPath = "";
    String filePath = "";
    String filePath1;
    private Uri picUri;
    private File actualImage;
    private File compressedImage;
    private Callback<ResponseBody> cBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_selfie);

        findViewById();
        onAction();
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(RegisterSelfieActivity.this).build();

        toolbar = findViewById(R.id.toolbar);
        btnCameraSelfie = findViewById(R.id.btn_camera_selfie);
        imageSelfie = findViewById(R.id.img_foto_selfie);
        btnDaftar = findViewById(R.id.btn_daftar);
    }

    private void onAction(){
        Intent getIntent =getIntent();
        String username = getIntent.getStringExtra("username");
        String nama_depan = getIntent.getStringExtra("nama_depan");
        String nama_belakang = getIntent.getStringExtra("nama_belakang");
        String jk = getIntent.getStringExtra("jk");
        String bod = getIntent.getStringExtra("bod");
        String phone = getIntent.getStringExtra("phone");
        String email = getIntent.getStringExtra("email");
        String password = getIntent.getStringExtra("password");
        String idprov = getIntent.getStringExtra("idprov");
        String idkab = getIntent.getStringExtra("idkab");
        String idkec = getIntent.getStringExtra("idkec");
        String alamat = getIntent.getStringExtra("alamat");
        String kodepos = getIntent.getStringExtra("kodepos");
        String referral = getIntent.getStringExtra("referral");
        String nik = getIntent.getStringExtra("nik");
        String img_ktp = getIntent.getStringExtra("img_ktp");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                RegisterSelfieActivity.this.finish();
            }
        });

        btnCameraSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!filePath.isEmpty()){
                    encodeImage();

                    requestRegister(username,nama_depan, nama_belakang, jk, bod, phone, email, password, idprov, idkab, idkec, kodepos, alamat,
                            referral, nik, img_ktp, encodedImage);

                } else {
                    Toast.makeText(RegisterSelfieActivity.this, "Harap diisi NIK beserta foto KTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestRegister(String username ,String nama_depan, String nama_belakang, String jk, String bod, String phone,  String email,
                                 String password, String idprov, String idkab, String idkec, String kodepos, String alamat, String referral, String nik, String foto_ktp,
                                 String foto_selfi){
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestRegister(username, nama_depan, nama_belakang, jk, bod, phone, email, password,
                idprov, idkab, idkec, kodepos, alamat, referral, nik, foto_ktp, foto_selfi,RegisterSelfieActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    boolean handle = Handle.handleRequestRegister(response.body().string(), RegisterSelfieActivity.this);
                    if (handle) {
                        Intent intent = new Intent(RegisterSelfieActivity.this, RegisterSuksesActivity.class);
                        intent.putExtra("NAME", nama_depan);
                        startActivity(intent);
                    } else {
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(RegisterSelfieActivity.this, call, cBack, 1, false);
            }
        };

        Api.enqueueWithRetry(RegisterSelfieActivity.this, call, cBack, false, "Loading");
    }

    private void encodeImage(){
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] b = baos.toByteArray();

        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("ENCODEDIMAGE", encodedImage);

    }

    public void chooseImage() {
        Dialog dialog = new Dialog(RegisterSelfieActivity.this);
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                intent.putExtra("android.intent.extras.CAMERA_FACING", CameraCharacteristics.LENS_FACING_FRONT) ;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                intent.putExtra("android.intent.extras.CAMERA_FACING", CameraCharacteristics.LENS_FACING_FRONT);
                intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            } else {
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            }
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
            new Compressor(RegisterSelfieActivity.this)
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
        Glide.with(RegisterSelfieActivity.this).load(BitmapFactory.decodeFile(compressedImage.getAbsolutePath())).into(imageSelfie);
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
                actualImage = FileUtil.from(RegisterSelfieActivity.this, data.getData());
                compressImage();
            } catch (IOException e) {
                showError("Failed to read picture data!");
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            mCurrentPhotoPath = getImagePath();
            if (mCurrentPhotoPath.isEmpty()){
                Toast.makeText(RegisterSelfieActivity.this, "Sorry, try to take picture again !", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(RegisterSelfieActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
