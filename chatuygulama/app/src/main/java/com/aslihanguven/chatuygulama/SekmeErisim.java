package com.aslihanguven.chatuygulama;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SekmeErisim extends FragmentStateAdapter {

    private static final FragmentActivity fragment = null;
            public SekmeErisim(FragmentManager fragmentManager) {
        super(fragment);

    }

    @NonNull
    @Override
    public Fragment createFragment(int i) {

        switch (i) {
            case 0:
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;

            case 1:
                KisilerFragment kisilerFragment = new KisilerFragment();
                return kisilerFragment;

            default:
                return null;

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "Sohbetler";

            case 1:
                return "Kişiler";

            default:
                return null;
        }

    }
}




