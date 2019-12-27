package id.dev.birifqa.edcgold.fragment_user;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.FileUtil;
import id.dev.birifqa.edcgold.utils.Handle;
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

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSewaCloud3 extends Fragment {


    private View view;
    private ImageView btnCamera, imgPhoto;
    private Button btnKirim;
    private Callback<ResponseBody> cBack;

    private static final int PICK_IMAGE_REQUEST = 201;
    private static final int REQUEST_CAPTURE_IMAGE = 209;

    String encodedImage = "";
    String mCurrentPhotoPath = "";
    String filePath = "";
    String filePath1;
    private Uri picUri;
    private File actualImage;
    private File compressedImage;

    private AlertDialog dialog;

    public FragmentSewaCloud3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sewa_cloud3, container, false);

        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        btnCamera = view.findViewById(R.id.btn_camera);
        imgPhoto = view.findViewById(R.id.img_foto_struk);
        btnKirim = view.findViewById(R.id.btn_kirim);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!filePath.isEmpty()){
                    encodeImage();
                    Session.save("rental_date", getDate());
                    rentalMining();
                } else {
                    Toast.makeText(getActivity(), "Silahkan upload foto struk terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void rentalMining(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.reqRentalMining(Session.get("token"), encodedImage, getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleRentalMining(response.body().string(), getActivity());
                    if (handle) {
                        dialog.dismiss();
                        getActivity().onBackPressed();
                        getActivity().finish();
                        Toast.makeText(getActivity(), "Permintaan Sewa Cloud Mining Berhasil!! Permintaan akan segera diproses!!", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Gagal melakukan proses permintaan Sewa Cloud Mining", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(getActivity(), call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(getActivity(), call, cBack, false, "Uploading...");
    }

    private String getDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        return formattedDate;
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
        Dialog dialog = new Dialog(getActivity());
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
            new Compressor(getActivity())
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
        Glide.with(getActivity()).load(BitmapFactory.decodeFile(compressedImage.getAbsolutePath())).into(imgPhoto);
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
                actualImage = FileUtil.from(getActivity(), data.getData());
                compressImage();
            } catch (IOException e) {
                showError("Failed to read picture data!");
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            mCurrentPhotoPath = getImagePath();
            if (mCurrentPhotoPath.isEmpty()){
                Toast.makeText(getActivity(), "Sorry, try to take picture again !", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

}
