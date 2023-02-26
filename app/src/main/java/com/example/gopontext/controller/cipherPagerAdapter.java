package com.example.gopontext.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gopontext.views.cipherFontFragment;
import com.example.gopontext.views.cipherTextFragment;
import com.example.gopontext.views.hashCodeFragment;

public class cipherPagerAdapter extends FragmentPagerAdapter {

    public cipherPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new cipherTextFragment();
            case 1: return new cipherFontFragment();
            case 2: return new hashCodeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Cipher Text";
            case 1: return "Cipher Font";
            case 2: return "Hash Code";
        }
        return null;
    }
}
