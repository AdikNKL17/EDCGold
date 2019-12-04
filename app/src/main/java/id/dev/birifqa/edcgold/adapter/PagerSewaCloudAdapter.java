package id.dev.birifqa.edcgold.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import id.dev.birifqa.edcgold.fragment_user.FragmentSewaCloud1;
import id.dev.birifqa.edcgold.fragment_user.FragmentSewaCloud2;
import id.dev.birifqa.edcgold.fragment_user.FragmentSewaCloud3;
import id.dev.birifqa.edcgold.fragment_user.FragmentTopupBank;
import id.dev.birifqa.edcgold.fragment_user.FragmentTopupConfirmation;
import id.dev.birifqa.edcgold.fragment_user.FragmentTopupNominal;

public class PagerSewaCloudAdapter extends FragmentPagerAdapter {
    public PagerSewaCloudAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentSewaCloud1();
            case 1:
                return new FragmentSewaCloud2();
            case 2:
                return new FragmentSewaCloud3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
