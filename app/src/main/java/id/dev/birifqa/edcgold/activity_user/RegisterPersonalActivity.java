package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.FileUtil;
import id.dev.birifqa.edcgold.utils.Session;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class RegisterPersonalActivity extends AppCompatActivity {

    private TextInputEditText etNik;
    private ImageView btnCameraKtp, imageKtp;
    private AppCompatButton btnLanjut;
    private Toolbar toolbar;

    private static final int PICK_IMAGE_REQUEST = 201;
    private static final int REQUEST_CAPTURE_IMAGE = 209;

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
        setContentView(R.layout.activity_register_personal);

        findViewById();
        onAction();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        etNik = findViewById(R.id.et_nik);
        btnCameraKtp = findViewById(R.id.btn_camera_ktp);
        imageKtp = findViewById(R.id.img_foto_ktp);
        btnLanjut = findViewById(R.id.btn_lanjut);
    }

    private void onAction(){
        Intent getIntent = getIntent();
        String username = getIntent.getStringExtra("username");
        String fullname = getIntent.getStringExtra("fullname");
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


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                RegisterPersonalActivity.this.finish();
            }
        });

        btnCameraKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!filePath.isEmpty() && !etNik.getText().toString().isEmpty()){
                    encodeImage();

                    Intent intent = new Intent(RegisterPersonalActivity.this, RegisterSelfieActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("jk", jk);
                    intent.putExtra("bod", bod);
                    intent.putExtra("phone", phone);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("idprov", idprov);
                    intent.putExtra("idkab", idkab);
                    intent.putExtra("idkec", idkec);
                    intent.putExtra("alamat", alamat);
                    intent.putExtra("kodepos", kodepos);
                    intent.putExtra("referral", referral);
                    intent.putExtra("nik", etNik.getText().toString());
                    intent.putExtra("img_ktp", encodedImage);
                    startActivity(intent);

                } else {
                    Toast.makeText(RegisterPersonalActivity.this, "Harap diisi NIK beserta foto KTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        Dialog dialog = new Dialog(RegisterPersonalActivity.this);
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
            new Compressor(RegisterPersonalActivity.this)
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
        Glide.with(RegisterPersonalActivity.this).load(BitmapFactory.decodeFile(compressedImage.getAbsolutePath())).into(imageKtp);
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
                actualImage = FileUtil.from(RegisterPersonalActivity.this, data.getData());
                compressImage();
            } catch (IOException e) {
                showError("Failed to read picture data!");
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            mCurrentPhotoPath = getImagePath();
            if (mCurrentPhotoPath.isEmpty()){
                Toast.makeText(RegisterPersonalActivity.this, "Sorry, try to take picture again !", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(RegisterPersonalActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
