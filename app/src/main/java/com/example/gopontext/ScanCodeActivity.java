package com.example.gopontext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gopontext.controller.scanCodeAdapter;
import com.example.gopontext.scanCodeCardSlider.ScanCodeCardModel;
import com.example.gopontext.scanCodeCardSlider.ScanCodeSliderAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScanCodeActivity extends AppCompatActivity {

    protected ImageButton goPrevActivityButton;
    protected ViewPager encodeDecodeViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        goPrevActivityButton = findViewById(R.id.goPrevActivityButton);

        encodeDecodeViewPager = findViewById(R.id.scanCodeViewPager);
        TabLayout scanCodeTabLayout = findViewById(R.id.scanCodeTabLayout);

        encodeDecodeViewPager.setAdapter(new scanCodeAdapter(getSupportFragmentManager(), 2));
        scanCodeTabLayout.setupWithViewPager(encodeDecodeViewPager);
        scanCodeTabLayout.setTabTextColors(getResources().getColor(R.color.fourthThemeColor), Color.WHITE);

        goPrevActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanCodeActivity.this.finish();
            }
        });

    }
}