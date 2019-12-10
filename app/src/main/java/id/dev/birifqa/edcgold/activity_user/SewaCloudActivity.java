package id.dev.birifqa.edcgold.activity_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.PagerSewaCloudAdapter;
import id.dev.birifqa.edcgold.adapter.PagerWalletSendAdapter;
import id.dev.birifqa.edcgold.utils.CustomViewPager;
import id.dev.birifqa.edcgold.utils.HeightWrappingViewPager;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Callback;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SewaCloudActivity extends AppCompatActivity {

    private CustomViewPager pager;
    private StepperIndicator indicator;
    private ImageView btnBack, btnNext;
    private Callback<ResponseBody> cBack;
    private Toolbar toolbar;

    private static final int PERMISSION_REQUEST_CODE = 1100;

    String[] appPermission = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private Integer pagerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_cloud);

        toolbar = findViewById(R.id.toolbar);
        pager = findViewById(R.id.view_pager_sewa_cloud);
        btnBack = findViewById(R.id.btn_back);
        btnNext = findViewById(R.id.btn_next);

        pager.setAdapter(new PagerSewaCloudAdapter(getSupportFragmentManager()));
        pager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        indicator = findViewById(R.id.stepper);
        indicator.showLabels(false);
        indicator.setViewPager(pager, pager.getAdapter().getCount());

        pagerPosition = 0;

        Session.save("rental_nominal", "");
        Session.save("rental_nama", "");
        Session.save("rental_nama_bank", "");
        Session.save("rental_no_rekening", "");
        Session.save("rental_jumlah_transfer", "");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagerPosition == 0){
                    if (!Session.get("rental_nominal").equals("")){
                        pager.setCurrentItem(pagerPosition+1, true);
                    }else {
                        Toast.makeText(SewaCloudActivity.this, "Gagal Mengambil Data Nominal, Silahkan Ulangi beberapa saat algi", Toast.LENGTH_SHORT).show();
                    }
                }else if (pagerPosition == 1){
                    if (!Session.get("rental_nama").equals("") || !Session.get("rental_nama_bank").equals("") || !Session.get("rental_no_rekening").equals("")){
                        pager.setCurrentItem(pagerPosition+1, true);
                    }else {
                        Toast.makeText(SewaCloudActivity.this, "Harap diisi semua!!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                SewaCloudActivity.this.finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pagerPosition-1, true);
            }
        });

        pager.beginFakeDrag();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    btnBack.setVisibility(View.INVISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    btnBack.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    btnBack.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                pagerPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (checkAndRequestPermission()){

        }
    }

    public boolean checkAndRequestPermission(){
        List<String> listPermissionNeeded = new ArrayList<>();
        for (String perm: appPermission){
            if (ContextCompat.checkSelfPermission(SewaCloudActivity.this, perm) != PackageManager.PERMISSION_GRANTED){
                listPermissionNeeded.add(perm);
            }
        }
        if (!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(SewaCloudActivity.this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), PERMISSION_REQUEST_CODE);
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

                    if (ActivityCompat.shouldShowRequestPermissionRationale(SewaCloudActivity.this, permName)){
                        AlertDialog.Builder alertDialog= new AlertDialog.Builder(SewaCloudActivity.this);
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
                                System.exit(0);
                            }
                        });
                        alertDialog.setCancelable(false);
                        AlertDialog alert = alertDialog.create();
                        alert.show();

                    }
                }
            }
        }
    }
}
