package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;

import androidx.viewpager.widget.ViewPager;
import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.PagerTopupAdapter;
import id.dev.birifqa.edcgold.adapter.PagerWalletSendAdapter;
import id.dev.birifqa.edcgold.utils.HeightWrappingViewPager;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserWalletSend extends Fragment {

    private View view;
    private HeightWrappingViewPager pager;
    private StepperIndicator indicator;
    private ImageView btnBack, btnNext;
    private Callback<ResponseBody> cBack;

    private Integer pagerPosition;

    public FragmentUserWalletSend() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_wallet_send, container, false);

        pager = view.findViewById(R.id.view_pager_topup);
        btnBack = view.findViewById(R.id.btn_back);
        btnNext = view.findViewById(R.id.btn_next);

        pager.setAdapter(new PagerWalletSendAdapter(getChildFragmentManager()));
        pager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        indicator = view.findViewById(R.id.stepper);
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
                        Toast.makeText(getActivity(), "Harap pilih nominal topup", Toast.LENGTH_SHORT).show();
                    }
                }else if (pagerPosition == 1){
                    if (!Session.get("topup_bank_name").equals("")){
                        pager.setCurrentItem(pagerPosition+1, true);
                    }else {
                        Toast.makeText(getActivity(), "Harap pilih bank", Toast.LENGTH_SHORT).show();
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

        return view;
    }

}
