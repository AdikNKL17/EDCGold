package id.dev.birifqa.edcgold.fragment_user;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;

import java.util.ArrayList;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.PagerTopupAdapter;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.HeightWrappingViewPager;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTopup extends Fragment{

    private View view;
    private HeightWrappingViewPager pager;
    private StepperIndicator indicator;
    private ImageView btnBack, btnNext;
    private Callback<ResponseBody> cBack;

    private Integer pagerPosition;

    public FragmentTopup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_topup, container, false);

        pager = view.findViewById(R.id.view_pager_topup);
        btnBack = view.findViewById(R.id.btn_back);
        btnNext = view.findViewById(R.id.btn_next);

        pager.setAdapter(new PagerTopupAdapter(getChildFragmentManager()));
        pager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        indicator = view.findViewById(R.id.stepper);
        indicator.showLabels(false);
        indicator.setViewPager(pager, pager.getAdapter().getCount());

        pagerPosition = 0;
        Log.e("PAGER POSITION", String.valueOf(pagerPosition));
        Session.save("topup_id", "");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Session.get("topup_id").equals("")){
                    if (pagerPosition == 0){
                        topupTransaction();
                    }else {
                        pager.setCurrentItem(pagerPosition+1, true);
                    }
                }else {
                    Toast.makeText(getActivity(), "Harap pilih nominal topup", Toast.LENGTH_SHORT).show();
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

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    private void topupTransaction(){
        Call<ResponseBody> call = ParamReq.reqTopup(Session.get("token"), getActivity());
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleTopup(response.body().string(), getActivity());
                    if (handle) {
                        pager.setCurrentItem(pagerPosition+1, true);
                    } else {
                        Toast.makeText(getActivity(), "Gagal topup", Toast.LENGTH_SHORT).show();
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
}
