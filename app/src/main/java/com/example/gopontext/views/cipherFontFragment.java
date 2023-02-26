package com.example.gopontext.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.gopontext.CodedResultActivity;
import com.example.gopontext.MainActivity;
import com.example.gopontext.R;

public class cipherFontFragment extends Fragment {

    protected CardView betamazeCard, brailleCard, elianScriptCard, aslCard, morseCard, pigpenCard, semaphoreCard, upsideDownCard, wingdingsCard;

    protected EditText mainInputGetText;

    protected RadioGroup dEncodeRadioGroup;
    protected RadioButton encode, decode;

    protected int dEncodeStatus;

    public cipherFontFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cipherFontView = inflater.inflate(R.layout.fragment_cipher_font, container, false);

        final MainActivity mainActivityGetActivity = (MainActivity) getActivity();

        mainInputGetText = mainActivityGetActivity.getMainInputEditText();

        dEncodeRadioGroup = mainActivityGetActivity.getMainRadioGroup();
        encode = mainActivityGetActivity.getMainEncodeRadioButton();
        decode = mainActivityGetActivity.getMainDecodeRadioButton();


        dEncodeStatus = mainActivityGetActivity.getDEncodeStatus();


        betamazeCard = cipherFontView.findViewById(R.id.betaMazeCipherCard);
        brailleCard = cipherFontView.findViewById(R.id.brailleCipherCard);
        elianScriptCard = cipherFontView.findViewById(R.id.elianScriptCipherCard);
        aslCard = cipherFontView.findViewById(R.id.aslCipherCard);
        morseCard = cipherFontView.findViewById(R.id.morseCipherCard);
        pigpenCard = cipherFontView.findViewById(R.id.pigpenCipherCard);
        semaphoreCard = cipherFontView.findViewById(R.id.semaphoreCipherCard);
        upsideDownCard = cipherFontView.findViewById(R.id.upsideDownCipherCard);
        wingdingsCard = cipherFontView.findViewById(R.id.wingdingsCipherCard);

//        if(encode.isChecked()){
//
//            if (mainInputGetText.getText().toString().equals("")) {
//                cardActivation(false, betamazeCard);
//                cardActivation(false, brailleCard);
//                cardActivation(false, elianScriptCard);
//                cardActivation(false, aslCard);
//                cardActivation(false, morseCard);
//                cardActivation(false, pigpenCard);
//                cardActivation(false, semaphoreCard);
//                cardActivation(false, upsideDownCard);
//                cardActivation(false, wingdingsCard);
//            }else{
//                cardActivation(true, betamazeCard);
//                cardActivation(true, brailleCard);
//                cardActivation(true, elianScriptCard);
//                cardActivation(true, aslCard);
//                cardActivation(true, morseCard);
//                cardActivation(true, pigpenCard);
//                cardActivation(true, semaphoreCard);
//                cardActivation(true, upsideDownCard);
//                cardActivation(true, wingdingsCard);
//            }
//
//        }else if(decode.isChecked()){
//
//            if (mainInputGetText.getText().toString().equals("")) {
//                cardActivation(false, betamazeCard);
//                cardActivation(false, brailleCard);
//                cardActivation(false, elianScriptCard);
//                cardActivation(false, aslCard);
//                cardActivation(false, morseCard);
//                cardActivation(false, pigpenCard);
//                cardActivation(false, semaphoreCard);
//                cardActivation(false, upsideDownCard);
//                cardActivation(false, wingdingsCard);
//            }else{
//                cardActivation(false, betamazeCard);
//                cardActivation(false, brailleCard);
//                cardActivation(false, elianScriptCard);
//                cardActivation(false, aslCard);
//                cardActivation(true, morseCard);
//                cardActivation(false, pigpenCard);
//                cardActivation(false, semaphoreCard);
//                cardActivation(false, upsideDownCard);
//                cardActivation(true, wingdingsCard);
//            }
//
//        }


        encode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dEncodeStatus = 1;
            }
        });

        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dEncodeStatus = 2;
            }
        });

        if(dEncodeStatus == 1){
            if (mainInputGetText.getText().toString().equals("")) {

                cardActivation(false, betamazeCard);
                cardActivation(false, brailleCard);
                cardActivation(false, elianScriptCard);
                cardActivation(false, aslCard);
                cardActivation(false, morseCard);
                cardActivation(false, pigpenCard);
                cardActivation(false, semaphoreCard);
                cardActivation(false, upsideDownCard);
                cardActivation(false, wingdingsCard);

            }else{

                cardActivation(true, betamazeCard);
                cardActivation(true, brailleCard);
                cardActivation(true, elianScriptCard);
                cardActivation(true, aslCard);
                cardActivation(true, morseCard);
                cardActivation(true, pigpenCard);
                cardActivation(true, semaphoreCard);
                cardActivation(true, upsideDownCard);
                cardActivation(true, wingdingsCard);

            }
        }else{

            if (mainInputGetText.getText().toString().equals("")) {

                cardActivation(false, betamazeCard);
                cardActivation(false, brailleCard);
                cardActivation(false, elianScriptCard);
                cardActivation(false, aslCard);
                cardActivation(false, morseCard);
                cardActivation(false, pigpenCard);
                cardActivation(false, semaphoreCard);
                cardActivation(false, upsideDownCard);
                cardActivation(false, wingdingsCard);

            }else{

                cardActivation(false, betamazeCard);
                cardActivation(false, brailleCard);
                cardActivation(false, elianScriptCard);
                cardActivation(false, aslCard);
                cardActivation(true, morseCard);
                cardActivation(false, pigpenCard);
                cardActivation(false, semaphoreCard);
                cardActivation(false, upsideDownCard);
                cardActivation(true, wingdingsCard);

            }

        }



        mainInputGetText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                encode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dEncodeStatus = 1;
                        if (mainInputGetText.getText().toString().equals("")) {

                            cardActivation(false, betamazeCard);
                            cardActivation(false, brailleCard);
                            cardActivation(false, elianScriptCard);
                            cardActivation(false, aslCard);
                            cardActivation(false, morseCard);
                            cardActivation(false, pigpenCard);
                            cardActivation(false, semaphoreCard);
                            cardActivation(false, upsideDownCard);
                            cardActivation(false, wingdingsCard);

                        }else{

                            cardActivation(true, betamazeCard);
                            cardActivation(true, brailleCard);
                            cardActivation(true, elianScriptCard);
                            cardActivation(true, aslCard);
                            cardActivation(true, morseCard);
                            cardActivation(true, pigpenCard);
                            cardActivation(true, semaphoreCard);
                            cardActivation(true, upsideDownCard);
                            cardActivation(true, wingdingsCard);

                        }
                    }
                });

                decode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dEncodeStatus = 2;
                        if (mainInputGetText.getText().toString().equals("")) {

                            cardActivation(false, betamazeCard);
                            cardActivation(false, brailleCard);
                            cardActivation(false, elianScriptCard);
                            cardActivation(false, aslCard);
                            cardActivation(false, morseCard);
                            cardActivation(false, pigpenCard);
                            cardActivation(false, semaphoreCard);
                            cardActivation(false, upsideDownCard);
                            cardActivation(false, wingdingsCard);

                        }else{

                            cardActivation(false, betamazeCard);
                            cardActivation(false, brailleCard);
                            cardActivation(false, elianScriptCard);
                            cardActivation(false, aslCard);
                            cardActivation(true, morseCard);
                            cardActivation(false, pigpenCard);
                            cardActivation(false, semaphoreCard);
                            cardActivation(false, upsideDownCard);
                            cardActivation(true, wingdingsCard);

                        }
                    }
                });

                if(dEncodeStatus == 1){
                    if (mainInputGetText.getText().toString().equals("")) {

                        cardActivation(false, betamazeCard);
                        cardActivation(false, brailleCard);
                        cardActivation(false, elianScriptCard);
                        cardActivation(false, aslCard);
                        cardActivation(false, morseCard);
                        cardActivation(false, pigpenCard);
                        cardActivation(false, semaphoreCard);
                        cardActivation(false, upsideDownCard);
                        cardActivation(false, wingdingsCard);

                    }else{

                        cardActivation(true, betamazeCard);
                        cardActivation(true, brailleCard);
                        cardActivation(true, elianScriptCard);
                        cardActivation(true, aslCard);
                        cardActivation(true, morseCard);
                        cardActivation(true, pigpenCard);
                        cardActivation(true, semaphoreCard);
                        cardActivation(true, upsideDownCard);
                        cardActivation(true, wingdingsCard);

                    }
                }else{

                    if (mainInputGetText.getText().toString().equals("")) {

                        cardActivation(false, betamazeCard);
                        cardActivation(false, brailleCard);
                        cardActivation(false, elianScriptCard);
                        cardActivation(false, aslCard);
                        cardActivation(false, morseCard);
                        cardActivation(false, pigpenCard);
                        cardActivation(false, semaphoreCard);
                        cardActivation(false, upsideDownCard);
                        cardActivation(false, wingdingsCard);

                    }else{

                        cardActivation(false, betamazeCard);
                        cardActivation(false, brailleCard);
                        cardActivation(false, elianScriptCard);
                        cardActivation(false, aslCard);
                        cardActivation(true, morseCard);
                        cardActivation(false, pigpenCard);
                        cardActivation(false, semaphoreCard);
                        cardActivation(false, upsideDownCard);
                        cardActivation(true, wingdingsCard);

                    }

                }






//                dEncodeStatus = mainActivityGetActivity.getDEncodeStatus();
//
//                if (dEncodeStatus == 1){
//
//
//
//                }else{
//
//
//
//                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        betamazeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(101);
            }
        });

        brailleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(102);
            }
        });

        elianScriptCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(103);
            }
        });

        aslCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(104);
            }
        });

        morseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(105);
            }
        });

        pigpenCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(106);
            }
        });

        semaphoreCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(107);
            }
        });

        upsideDownCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(108);
            }
        });

        wingdingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(109);
            }
        });


        return cipherFontView;
    }

    protected void codedResActivityOpen(int cipherNumber){
        MainActivity mainActivity = (MainActivity) getActivity();
        Intent codedResultActivity = new Intent(getContext(), CodedResultActivity.class);
        codedResultActivity.putExtra("inputString", mainActivity.getInputText());
        codedResultActivity.putExtra("DEncodeStatus", mainActivity.getDEncodeStatus());
        codedResultActivity.putExtra("cipherNumber", cipherNumber);
        codedResultActivity.putExtra("cipherFrag", 1);

        cipherFontFragment.this.startActivity(codedResultActivity);
    }

    protected void cardActivation(boolean bool, CardView card) {
        if (bool) {
            card.setClickable(true);
            card.setAlpha(1);
            card.setEnabled(true);
        } else {
            card.setClickable(false);
            card.setAlpha(0.5f);
            card.setEnabled(false);
        }
    }

}