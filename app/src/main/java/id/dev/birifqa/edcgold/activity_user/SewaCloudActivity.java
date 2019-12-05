package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.PagerSewaCloudAdapter;
import id.dev.birifqa.edcgold.adapter.PagerWalletSendAdapter;
import id.dev.birifqa.edcgold.utils.HeightWrappingViewPager;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Callback;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;

public class SewaCloudActivity extends AppCompatActivity {

    private HeightWrappingViewPager pager;
    private StepperIndicator indicator;
    private ImageView btnBack, btnNext;
    private Callback<ResponseBody> cBack;

    private Integer pagerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_cloud);

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
                    if (!Session.get("rental_nama").equals("") || !Session.get("rental_nama_bank").equals("") || !Session.get("rental_no_rekening").equals("") || !Session.get("rental_jumlah_transfer").equals("")){
                        pager.setCurrentItem(pagerPosition+1, true);
                    }else {
                        Toast.makeText(SewaCloudActivity.this, "Harap diisi semua!!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pagerPosition-1, true);
            }
        });

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
    }
}
