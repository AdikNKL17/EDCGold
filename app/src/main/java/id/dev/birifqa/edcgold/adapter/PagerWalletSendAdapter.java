package id.dev.birifqa.edcgold.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import id.dev.birifqa.edcgold.fragment_user.FragmentTopupBank;
import id.dev.birifqa.edcgold.fragment_user.FragmentTopupConfirmation;
import id.dev.birifqa.edcgold.fragment_user.FragmentTopupNominal;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserWalletSendDetail;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserWalletSendType;
import id.dev.birifqa.edcgold.utils.Session;

public class PagerWalletSendAdapter extends FragmentStatePagerAdapter {
    public PagerWalletSendAdapter(FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentUserWalletSendType();
            case 1:
                return new FragmentUserWalletSendDetail();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
