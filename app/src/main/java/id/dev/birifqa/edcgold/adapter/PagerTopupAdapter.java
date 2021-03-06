package id.dev.birifqa.edcgold.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import id.dev.birifqa.edcgold.fragment_user.FragmentTopupBank;
import id.dev.birifqa.edcgold.fragment_user.FragmentTopupConfirmation;
import id.dev.birifqa.edcgold.fragment_user.FragmentTopupNominal;

public class PagerTopupAdapter extends FragmentPagerAdapter {
    public PagerTopupAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentTopupNominal();
            case 1:
                return new FragmentTopupBank();
            case 2:
                return new FragmentTopupConfirmation();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
