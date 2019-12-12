package id.dev.birifqa.edcgold.fragment_admin;


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

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import dmax.dialog.SpotsDialog;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.model.admin.AdminListKomunitasModel;
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
public class FragmentAdminKomunitasPost extends Fragment {

    private View view;
    private AlertDialog dialog;
    private Callback<ResponseBody> cBack;
    private AppCompatSpinner spinnerKomunitas;
    private TextInputEditText etTitle, etContent, etImages;
    private ImageView btnCamera;
    private AppCompatButton btnSimpan, btnBatal;

    private static final int PICK_IMAGE_REQUEST = 201;
    private static final int REQUEST_CAPTURE_IMAGE = 209;

    private ArrayList<AdminListKomunitasModel> komunitasModels;
    String idKomunitas = "";
    String encodedImage = "";
    String mCurrentPhotoPath = "";
    String filePath = "";
    String filePath1;
    private Uri picUri;
    private File actualImage;
    private File compressedImage;

    public FragmentAdminKomunitasPost() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_komunitas_post, container, false);

        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        findViewById();
        onAction();

        return view;
    }

    private void findViewById(){
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

        spinnerKomunitas = view.findViewById(R.id.spinner_komunitas);
        etTitle = view.findViewById(R.id.et_title);
        etContent = view.findViewById(R.id.et_content);
        etImages = view.findViewById(R.id.et_images);
        btnCamera = view.findViewById(R.id.btn_camera);
        btnSimpan = view.findViewById(R.id.btn_simpan);
        btnBatal = view.findViewById(R.id.btn_batal);
    }

    private void onAction(){
        komunitasModels = new ArrayList<>();
        getKomunitas();

        spinnerKomunitas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getSelectedItem().equals("Pilih Komunitas")){
                    idKomunitas = komunitasModels.get(position-1).getId();
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!idKomunitas.equals("")){
                    encodeImage();
                    addPostKomunitas();
                } else {
                    Toast.makeText(getActivity(), "Harap pilih komunitas terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTitle.setText("");
                etContent.setText("");
                etImages.setText("");
                encodedImage = "";
            }
        });

    }

    private void addPostKomunitas(){
        dialog.show();
        Call<ResponseBody> call = ParamReq.reqAddPostKomunitas(Session.get("token"), idKomunitas, etTitle.getText().toString(), etContent.getText().toString(), encodedImage, getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getBoolean("success")){
                        dialog.dismiss();
                        etTitle.setText("");
                        etContent.setText("");
                        etImages.setText("");
                        encodedImage = "";
                        Toast.makeText(getActivity(), "Tambah Post Komunitas Berhasil", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Gagal Tambah Post Komunitas", Toast.LENGTH_SHORT).show();
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
        Api.enqueueWithRetry(getActivity(), call, cBack, false, "Loading");
    }

    private void getKomunitas(){
        komunitasModels.clear();
        dialog.show();
        Call<ResponseBody> call = ParamReq.requestKomunitas(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray dataArray = dataObject.getJSONArray("data");

                    if (dataArray.length() > 0){
                        dialog.dismiss();
                        for (int i = 0; i < dataArray.length() ; i++){
                            AdminListKomunitasModel model = new AdminListKomunitasModel();
                            model.setId(dataArray.getJSONObject(i).getString("id"));
                            model.setNama_komunitas(dataArray.getJSONObject(i).getString("name"));
                            model.setNama_ketua(dataArray.getJSONObject(i).getString("ketua"));
                            model.setAlamat(dataArray.getJSONObject(i).getString("alamat"));
                            komunitasModels.add(model);
                        }

                        ArrayList<String> komunitasLabel = new ArrayList<>();
                        komunitasLabel.clear();
                        komunitasLabel.add("Pilih Komunitas");
                        for (int i = 0; i < komunitasModels.size(); i++){
                            komunitasLabel.add(komunitasModels.get(i).getNama_komunitas());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_dropdown_item, komunitasLabel);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerKomunitas.setAdapter(adapter);
                    } else {
                        dialog.dismiss();
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
        Api.enqueueWithRetry(getActivity(), call, cBack, false, "Loading");
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
        etImages.setText(compressedImage.getName());
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
