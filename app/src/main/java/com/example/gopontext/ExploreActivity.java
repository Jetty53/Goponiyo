package com.example.gopontext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gopontext.exploreCardSlider.exploreCardModel;
import com.example.gopontext.exploreCardSlider.exploreCardSliderAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExploreActivity extends AppCompatActivity {

    protected Button exploreCipherTextButton, exploreCipherFontButton, exploreHashCodeButton;
    protected int exploreCipherTextPressedState = 1, exploreCipherFontPressedState = 0, exploreHashCodePressedState = 0;
    protected ImageButton goPrevActivityButton;
    protected ConstraintLayout wholeExploreLayout;

    private ViewPager exploreCardSlider;
    private com.example.gopontext.exploreCardSlider.exploreCardSliderAdapter adapter;
    private List<com.example.gopontext.exploreCardSlider.exploreCardModel> exploreCardCipherTextModel, exploreCardCipherFontModel, exploreCardHashCodeModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        exploreCipherTextButton = findViewById(R.id.exploreCipherTextButton);
        exploreCipherFontButton = findViewById(R.id.exploreCipherFontButton);
        exploreHashCodeButton = findViewById(R.id.exploreHashCodeButton);
        goPrevActivityButton = findViewById(R.id.goPrevActivityButton);
        wholeExploreLayout = findViewById(R.id.wholeExploreLayout);

        if (exploreCipherTextPressedState == 0) {
            exploreCipherTextButton.setBackground(getResources().getDrawable(R.drawable.left_side_blue_btn));
            exploreCipherTextButton.setTextColor(getResources().getColor(R.color.btn_text_color));
        } else {
            exploreCipherTextButton.setBackground(getResources().getDrawable(R.drawable.left_side_blue_btn_on_pressed));
            exploreCipherTextButton.setTextColor(getResources().getColor(R.color.btn_text_color_pressed));
        }

        if (exploreCipherFontPressedState == 0) {
            exploreCipherFontButton.setBackground(getResources().getDrawable(R.drawable.middle_side_blue_btn));
            exploreCipherFontButton.setTextColor(getResources().getColor(R.color.btn_text_color));
        } else {
            exploreCipherFontButton.setBackground(getResources().getDrawable(R.drawable.middle_side_blue_btn_on_pressed));
            exploreCipherFontButton.setTextColor(getResources().getColor(R.color.btn_text_color_pressed));
        }

        if (exploreHashCodePressedState == 0) {
            exploreHashCodeButton.setBackground(getResources().getDrawable(R.drawable.right_side_blue_btn));
            exploreHashCodeButton.setTextColor(getResources().getColor(R.color.btn_text_color));
        } else {
            exploreHashCodeButton.setBackground(getResources().getDrawable(R.drawable.right_side_blue_btn_on_pressed));
            exploreHashCodeButton.setTextColor(getResources().getColor(R.color.btn_text_color_pressed));
        }

        goPrevActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        exploreCardCipherTextModel = new ArrayList<>();
        exploreCardCipherFontModel = new ArrayList<>();
        exploreCardHashCodeModel = new ArrayList<>();

        String testDesc = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        String testActualDesc;
        if (testDesc.length() > 110) {
            testActualDesc = testDesc.substring(0, 109) + "...";
        } else {
            testActualDesc = testDesc;
        }

        exploreCardCipherTextModel.add(new exploreCardModel("ASCII", testActualDesc, 101));
        exploreCardCipherTextModel.add(new exploreCardModel("ADFGVX", testActualDesc, 102));
        exploreCardCipherTextModel.add(new exploreCardModel("AFFINE", testActualDesc, 103));
        exploreCardCipherTextModel.add(new exploreCardModel("ATBASH", testActualDesc, 104));
        exploreCardCipherTextModel.add(new exploreCardModel("AUTOKEY", testActualDesc, 105));
        exploreCardCipherTextModel.add(new exploreCardModel("BINARY", testActualDesc, 106));
        exploreCardCipherTextModel.add(new exploreCardModel("BEAUFORT", testActualDesc, 107));
        exploreCardCipherTextModel.add(new exploreCardModel("BASE64", testActualDesc, 108));
        exploreCardCipherTextModel.add(new exploreCardModel("CAESER", testActualDesc, 109));
        exploreCardCipherTextModel.add(new exploreCardModel("HEXADECIMAL", testActualDesc, 110));
        exploreCardCipherTextModel.add(new exploreCardModel("OCTAL", testActualDesc, 111));
        exploreCardCipherTextModel.add(new exploreCardModel("PORTA", testActualDesc, 112));
        exploreCardCipherTextModel.add(new exploreCardModel("RAILFENCE", testActualDesc, 113));
        exploreCardCipherTextModel.add(new exploreCardModel("ROT13", testActualDesc, 114));
        exploreCardCipherTextModel.add(new exploreCardModel("VIGENERE", testActualDesc, 115));

        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_1", testActualDesc, 201));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_2", testActualDesc, 202));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_3", testActualDesc, 203));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_4", testActualDesc, 204));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_5", testActualDesc, 205));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_6", testActualDesc, 206));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_7", testActualDesc, 207));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_8", testActualDesc, 208));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_9", testActualDesc, 209));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_10", testActualDesc, 210));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_11", testActualDesc, 211));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_12", testActualDesc, 212));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_13", testActualDesc, 213));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_14", testActualDesc, 214));
        exploreCardCipherFontModel.add(new exploreCardModel("TITLE_2_15", testActualDesc, 215));

        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_1", testActualDesc, 301));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_2", testActualDesc, 302));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_3", testActualDesc, 303));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_4", testActualDesc, 304));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_5", testActualDesc, 305));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_6", testActualDesc, 306));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_7", testActualDesc, 307));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_8", testActualDesc, 308));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_9", testActualDesc, 309));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_10", testActualDesc, 310));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_11", testActualDesc, 311));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_12", testActualDesc, 312));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_13", testActualDesc, 313));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_14", testActualDesc, 314));
        exploreCardHashCodeModel.add(new exploreCardModel("TITLE_3_15", testActualDesc, 315));

        exploreCardSlider = findViewById(R.id.cardSliderLayout);

        adapter = new exploreCardSliderAdapter(exploreCardCipherTextModel, this);
        exploreCardSlider.setAdapter(adapter);

        exploreCardSlider.setPadding(60, 0, 60, 0);

        exploreCipherTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exploreCipherTextPressedState == 0) {
                    exploreCipherTextPressedState = 1;
                    exploreCipherFontPressedState = 0;
                    exploreHashCodePressedState = 0;
                    exploreCipherTextButton.setBackground(getResources().getDrawable(R.drawable.left_side_blue_btn_on_pressed));
                    exploreCipherTextButton.setTextColor(getResources().getColor(R.color.btn_text_color_pressed));

                    exploreCipherFontButton.setBackground(getResources().getDrawable(R.drawable.middle_side_blue_btn));
                    exploreCipherFontButton.setTextColor(getResources().getColor(R.color.btn_text_color));

                    exploreHashCodeButton.setBackground(getResources().getDrawable(R.drawable.right_side_blue_btn));
                    exploreHashCodeButton.setTextColor(getResources().getColor(R.color.btn_text_color));

                    adapter = new exploreCardSliderAdapter(exploreCardCipherTextModel, getApplicationContext());
                    exploreCardSlider.setAdapter(adapter);


                }
            }
        });

        exploreCipherFontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exploreCipherFontPressedState == 0) {
                    exploreCipherTextPressedState = 0;
                    exploreCipherFontPressedState = 1;
                    exploreHashCodePressedState = 0;
                    exploreCipherFontButton.setBackground(getResources().getDrawable(R.drawable.middle_side_blue_btn_on_pressed));
                    exploreCipherFontButton.setTextColor(getResources().getColor(R.color.btn_text_color_pressed));

                    exploreCipherTextButton.setBackground(getResources().getDrawable(R.drawable.left_side_blue_btn));
                    exploreCipherTextButton.setTextColor(getResources().getColor(R.color.btn_text_color));

                    exploreHashCodeButton.setBackground(getResources().getDrawable(R.drawable.right_side_blue_btn));
                    exploreHashCodeButton.setTextColor(getResources().getColor(R.color.btn_text_color));

                    adapter = new exploreCardSliderAdapter(exploreCardCipherFontModel, getApplicationContext());
                    exploreCardSlider.setAdapter(adapter);


                }
            }
        });

        exploreHashCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exploreHashCodePressedState == 0) {
                    exploreCipherTextPressedState = 0;
                    exploreCipherFontPressedState = 0;
                    exploreHashCodePressedState = 1;
                    exploreHashCodeButton.setBackground(getResources().getDrawable(R.drawable.right_side_blue_btn_on_pressed));
                    exploreHashCodeButton.setTextColor(getResources().getColor(R.color.btn_text_color_pressed));

                    exploreCipherTextButton.setBackground(getResources().getDrawable(R.drawable.left_side_blue_btn));
                    exploreCipherTextButton.setTextColor(getResources().getColor(R.color.btn_text_color));

                    exploreCipherFontButton.setBackground(getResources().getDrawable(R.drawable.middle_side_blue_btn));
                    exploreCipherFontButton.setTextColor(getResources().getColor(R.color.btn_text_color));

                    adapter = new exploreCardSliderAdapter(exploreCardHashCodeModel, getApplicationContext());
                    exploreCardSlider.setAdapter(adapter);

                }
            }
        });

//        Integer[] color_temp = {
//                getResources().getColor(R.color.color1),
//                getResources().getColor(R.color.color2),
//                getResources().getColor(R.color.color3),
//                getResources().getColor(R.color.color4),
//                getResources().getColor(R.color.color5)
//        };
//
//        colors = color_temp;

//        exploreCardSlider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////                if ((position < (adapter.getCount() - 1)) && (position < (colors.length) - 1)) {
////                    wholeExploreLayout.setBackgroundColor(
////                            (Integer) argbEvaluator.evaluate(
////                                    positionOffset,
////                                    colors[position],
////                                    colors[position + 1]
////                            )
////                    );
////                    exploreCardSlider.setBackgroundColor(
////                            (Integer) argbEvaluator.evaluate(
////                                    positionOffset,
////                                    colors[position],
////                                    colors[position + 1]
////                            )
////                    );
////                }
////                else {
////                    wholeExploreLayout.setBackgroundColor(getResources().getColor(R.color.color1));
////                    exploreCardSlider.setBackgroundColor(getResources().getColor(R.color.color1));
////                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });





    }
}