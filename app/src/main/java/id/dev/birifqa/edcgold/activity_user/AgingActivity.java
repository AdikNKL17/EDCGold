package id.dev.birifqa.edcgold.activity_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.fragment_user.AgingAgingFragment;
import id.dev.birifqa.edcgold.fragment_user.AgingBalanceFragment;
import id.dev.birifqa.edcgold.fragment_user.AgingWithdrawFragment;

public class AgingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aging);

        findViewById();
        onAction();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.view_pager);
    }

    private void onAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                AgingActivity.this.finish();
            }
        });

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Aging");
        tabLayout.getTabAt(1).setText("Balance");
        tabLayout.getTabAt(2).setText("Withdraw");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new AgingAgingFragment();
                case 1:
                    return new AgingBalanceFragment();
                case 2:
                    return new AgingWithdrawFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
