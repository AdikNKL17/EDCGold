package id.dev.birifqa.edcgold.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import id.dev.birifqa.edcgold.fragment_user.FragmentUserWalletReceiveBank;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserWalletReceiveConfirmation;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserWalletReceiveDetail;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserWalletSendDetail;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserWalletSendType;

public class PagerWalletReceiveAdapter extends FragmentPagerAdapter {
    public PagerWalletReceiveAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentUserWalletReceiveDetail();
            case 1:
                return new FragmentUserWalletReceiveBank();
            case 2:
                return new FragmentUserWalletReceiveConfirmation();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
