package com.example.gopontext.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gopontext.views.scanCodeDecodeFragment;
import com.example.gopontext.views.scanCodeEncodeFragment;


public class scanCodeAdapter extends FragmentPagerAdapter {

    public scanCodeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new scanCodeEncodeFragment();
            case 1: return new scanCodeDecodeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Encode";
            case 1: return "Decode";
        }
        return null;
    }
}
