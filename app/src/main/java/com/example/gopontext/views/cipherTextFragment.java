package com.example.gopontext.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gopontext.CodedResultActivity;
import com.example.gopontext.MainActivity;
import com.example.gopontext.R;
import com.example.gopontext.cipherProcs.ADFGVX;
import com.example.gopontext.cipherProcs.AFFINE;
import com.example.gopontext.cipherProcs.ASCII;
import com.example.gopontext.cipherProcs.ATBASH;
import com.example.gopontext.cipherProcs.AUTOKEY;
import com.example.gopontext.cipherProcs.BASE64;
import com.example.gopontext.cipherProcs.BEAUFORT;
import com.example.gopontext.cipherProcs.BIN;
import com.example.gopontext.cipherProcs.CAESER;
import com.example.gopontext.cipherProcs.HEX;
import com.example.gopontext.cipherProcs.OCTAL;
import com.example.gopontext.cipherProcs.PORTA;
import com.example.gopontext.cipherProcs.RAILFENCE;
import com.example.gopontext.cipherProcs.ROT13;
import com.example.gopontext.cipherProcs.VIGENERE;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Objects;


public class cipherTextFragment extends Fragment {

    protected CardView asciiCard, adfgvxCard, affineCard, atbashCard, autokeyCard, binaryCard, beaufortCard, base64Card, caeserCard, hexCard, octalCard, portaCard, railfenceCard, rot13Card, vigenereCard;

    protected EditText mainInputGetText;

    public cipherTextFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View cipherTextView = inflater.inflate(R.layout.fragment_cipher_text, container, false);

        final MainActivity mainActivityGetActivity = (MainActivity) getActivity();

        mainInputGetText = mainActivityGetActivity.getMainInputEditText();

        asciiCard = cipherTextView.findViewById(R.id.asciiCipherCard);
        adfgvxCard = cipherTextView.findViewById(R.id.adfgvxCipherCard);
        affineCard = cipherTextView.findViewById(R.id.affineCipherCard);
        atbashCard = cipherTextView.findViewById(R.id.atbashCipherCard);
        autokeyCard = cipherTextView.findViewById(R.id.autokeyCipherCard);
        binaryCard = cipherTextView.findViewById(R.id.binCipherCard);
        beaufortCard = cipherTextView.findViewById(R.id.beaufortCipherCard);
        base64Card = cipherTextView.findViewById(R.id.base64CipherCard);
        caeserCard = cipherTextView.findViewById(R.id.caeserCipherCard);
        hexCard = cipherTextView.findViewById(R.id.hexCipherCard);
        octalCard = cipherTextView.findViewById(R.id.octalCipherCard);
        portaCard = cipherTextView.findViewById(R.id.portaCipherCard);
        railfenceCard = cipherTextView.findViewById(R.id.railfenceCipherCard);
        rot13Card = cipherTextView.findViewById(R.id.rot13CipherCard);
        vigenereCard = cipherTextView.findViewById(R.id.vigenereCipherCard);

        cardActivation(false, asciiCard);
        cardActivation(false, adfgvxCard);
        cardActivation(false, affineCard);
        cardActivation(false, atbashCard);
        cardActivation(false, autokeyCard);
        cardActivation(false, binaryCard);
        cardActivation(false, beaufortCard);
        cardActivation(false, base64Card);
        cardActivation(false, caeserCard);
        cardActivation(false, hexCard);
        cardActivation(false, octalCard);
        cardActivation(false, portaCard);
        cardActivation(false, railfenceCard);
        cardActivation(false, rot13Card);
        cardActivation(false, vigenereCard);

        mainInputGetText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mainInputGetText.getText().toString().equals("")) {

                    cardActivation(false, asciiCard);
                    cardActivation(false, adfgvxCard);
                    cardActivation(false, affineCard);
                    cardActivation(false, atbashCard);
                    cardActivation(false, autokeyCard);
                    cardActivation(false, binaryCard);
                    cardActivation(false, beaufortCard);
                    cardActivation(false, base64Card);
                    cardActivation(false, caeserCard);
                    cardActivation(false, hexCard);
                    cardActivation(false, octalCard);
                    cardActivation(false, portaCard);
                    cardActivation(false, railfenceCard);
                    cardActivation(false, rot13Card);
                    cardActivation(false, vigenereCard);

                } else {

                    cardActivation(true, asciiCard);
                    cardActivation(true, adfgvxCard);
                    cardActivation(true, affineCard);
                    cardActivation(true, atbashCard);
                    cardActivation(true, autokeyCard);
                    cardActivation(true, binaryCard);
                    cardActivation(true, beaufortCard);
                    cardActivation(true, base64Card);
                    cardActivation(true, caeserCard);
                    cardActivation(true, hexCard);
                    cardActivation(true, octalCard);
                    cardActivation(true, portaCard);
                    cardActivation(true, railfenceCard);
                    cardActivation(true, rot13Card);
                    cardActivation(true, vigenereCard);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                //Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();

            }
        });

        asciiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //currentDialog = new AlertDialog.Builder(getContext());
                //View view = getLayoutInflater().inflate(R.layout.coded_result_layout, null);

                //currentDialog.setView(view);
                //codedResDialog = currentDialog.create();
                //codedResDialog.show();

//                codedResDialog = new Dialog(Objects.requireNonNull(getContext()));
//                codedResDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                View dialogView = getLayoutInflater().inflate(R.layout.coded_result_layout, null);
//                codedResDialog.setContentView(dialogView);
//                dialogCloseBtn = dialogView.findViewById(R.id.dialogCloseBtn);
//                codedResDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                int width = getResources().getDisplayMetrics().widthPixels;
//                int height = getResources().getDisplayMetrics().heightPixels;
//                codedResDialog.getWindow().setLayout(width-100, height-100);
//                codedResDialog.show();
//
//                dialogCloseBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        codedResDialog.dismiss();
//                    }
//                });

//                final String inputString = mainActivity.getInputText();
//                final int DEncodeStatus = mainActivity.getDEncodeStatus();
//
//                codedResDialog(1, inputString, DEncodeStatus);

                codedResActivityOpen(1);

            }
        });

        adfgvxCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codedResActivityOpen(2);
            }
        });

        affineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codedResActivityOpen(3);

            }
        });

        atbashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codedResActivityOpen(4);

            }
        });

        autokeyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(5);


            }
        });

        binaryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codedResActivityOpen(6);

            }
        });

        beaufortCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codedResActivityOpen(7);

            }
        });

        base64Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codedResActivityOpen(8);

            }
        });

        caeserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codedResActivityOpen(9);

            }
        });

        hexCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codedResActivityOpen(10);
            }
        });

        octalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codedResActivityOpen(11);
            }
        });

        portaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(12);


            }
        });

        railfenceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(13);

            }
        });

        rot13Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codedResActivityOpen(14);

            }
        });

        vigenereCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codedResActivityOpen(15);


            }
        });

        return cipherTextView;
    }

    protected void codedResActivityOpen(int cipherNumber) {

        MainActivity mainActivity = (MainActivity) getActivity();
        Intent codedResultActivity = new Intent(getContext(), CodedResultActivity.class);
        //Toast.makeText(getContext(), "Jeet", Toast.LENGTH_SHORT).show();
        codedResultActivity.putExtra("inputString", mainActivity.getInputText());
        codedResultActivity.putExtra("DEncodeStatus", mainActivity.getDEncodeStatus());
        codedResultActivity.putExtra("cipherNumber", cipherNumber);
        codedResultActivity.putExtra("cipherFrag", 0);

        cipherTextFragment.this.startActivity(codedResultActivity);

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