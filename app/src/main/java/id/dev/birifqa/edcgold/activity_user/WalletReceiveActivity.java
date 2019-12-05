package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.PagerWalletReceiveAdapter;
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

public class WalletReceiveActivity extends AppCompatActivity {
    private HeightWrappingViewPager pager;
    private StepperIndicator indicator;
    private ImageView btnBack, btnNext;
    private Callback<ResponseBody> cBack;

    private Integer pagerPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_receive);

        pager = findViewById(R.id.view_pager_wallet_receive);
        btnBack = findViewById(R.id.btn_back);
        btnNext = findViewById(R.id.btn_next);

        pager.setAdapter(new PagerWalletReceiveAdapter(getSupportFragmentManager()));
        pager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        indicator = findViewById(R.id.stepper);
        indicator.showLabels(false);
        indicator.setViewPager(pager, pager.getAdapter().getCount());

        pagerPosition = 0;

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagerPosition == 0){
                    if (!Session.get("topup_nominal").equals("")){
                        pager.setCurrentItem(pagerPosition+1, true);
                    }else {
                        Toast.makeText(WalletReceiveActivity.this, "Harap pilih nominal topup", Toast.LENGTH_SHORT).show();
                    }
                }else if (pagerPosition == 1){
                    if (!Session.get("topup_bank_name").equals("")){
                        pager.setCurrentItem(pagerPosition+1, true);
                    }else {
                        Toast.makeText(WalletReceiveActivity.this, "Harap pilih bank", Toast.LENGTH_SHORT).show();
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
