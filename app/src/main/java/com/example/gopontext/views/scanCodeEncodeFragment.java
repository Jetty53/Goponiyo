package com.example.gopontext.views;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Environment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gopontext.MainActivity;
import com.example.gopontext.R;
import com.example.gopontext.ScanCodeActivity;
import com.example.gopontext.scanCodeCardSlider.ScanCodeCardModel;
import com.example.gopontext.scanCodeCardSlider.ScanCodeSliderAdapter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class scanCodeEncodeFragment extends Fragment {

    protected RecyclerView scanCodeBarcodeProcedureCardRecyclerView;

    protected ScanCodeSliderAdapter scanCodeSliderAdapter;
    protected List<ScanCodeCardModel> scanCodeCardModels;

    protected ImageView scanCodeBarcodeImageView;
    protected ImageButton scanCodeImageCloseBtn;
    protected ImageButton scanCodeDeleteAllInputText, scanCodeCopyAllInputText, scanCodePasteAllInputText, scanCodeShareAllInputText;
    protected EditText scanCodeInputEditText;
    protected TextView scanCodeBarcodeErrorTextView, scanCodeProcedureTextView, scanCodeOutputCharacterNumber;
    protected Button scanCodeSaveBtn, scanCodeShareBtn;
    private ClipboardManager clipboardManager;
    protected int STORAGE_PERMISSION_CODE = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View scanCodeEncodeView = inflater.inflate(R.layout.fragment_scan_code_encode, container, false);

        scanCodeBarcodeImageView = scanCodeEncodeView.findViewById(R.id.scanCodeBarcodeImageView);
        scanCodeInputEditText = scanCodeEncodeView.findViewById(R.id.scanCodeInputEditText);
        scanCodeBarcodeErrorTextView = scanCodeEncodeView.findViewById(R.id.scanCodeBarcodeErrorTextView);
        scanCodeSaveBtn = scanCodeEncodeView.findViewById(R.id.scanCodeSaveBtn);
        scanCodeShareBtn = scanCodeEncodeView.findViewById(R.id.scanCodeShareBtn);
        scanCodeProcedureTextView = scanCodeEncodeView.findViewById(R.id.scanCodeProcedureTextView);
        scanCodeImageCloseBtn = scanCodeEncodeView.findViewById(R.id.scanCodeImageCloseBtn);
        scanCodeDeleteAllInputText = scanCodeEncodeView.findViewById(R.id.scanCodeInputDeleteRoundButton);
        scanCodeCopyAllInputText = scanCodeEncodeView.findViewById(R.id.scanCodeInputCopyRoundButton);
        scanCodePasteAllInputText = scanCodeEncodeView.findViewById(R.id.scanCodeInputPasteRoundButton);
        scanCodeShareAllInputText = scanCodeEncodeView.findViewById(R.id.scanCodeOutputShareRoundButton);
        scanCodeOutputCharacterNumber = scanCodeEncodeView.findViewById(R.id.scanCodeOutputCharacterNumber);

        scanCodeInputEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2000)});

        scanCodeBarcodeErrorTextView.setText("Please, enter some input text !!!"+'\n'+"( Using emojis or different language characters is not Recommended )");

        clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        if (scanCodeInputEditText.getText().length() != 0) {
            scanCodeDeleteAllInputText.setEnabled(true);
            scanCodeDeleteAllInputText.setAlpha(1.0f);
            scanCodeDeleteAllInputText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanCodeInputEditText.setText("");
                }
            });
        }
        scanCodeDeleteAllInputText.setEnabled(false);
        scanCodeDeleteAllInputText.setAlpha(0.5f);
        if (scanCodeInputEditText.getText().length() == 0) {
            scanCodeCopyAllInputText.setEnabled(false);
            scanCodeCopyAllInputText.setAlpha(0.5f);
        } else {
            scanCodeCopyAllInputText.setEnabled(true);
            scanCodeCopyAllInputText.setAlpha(1.0f);
            scanCodeCopyAllInputText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData inpClipData = ClipData.newPlainText("InputText", scanCodeInputEditText.getText().toString().trim());
                    clipboardManager.setPrimaryClip(inpClipData);
                    Toast.makeText(getContext(), "Input Text is Copied!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (scanCodeInputEditText.getText().length() == 0) {
            scanCodeShareAllInputText.setEnabled(false);
            scanCodeShareAllInputText.setAlpha(0.5f);
        } else {
            scanCodeShareAllInputText.setEnabled(true);
            scanCodeShareAllInputText.setAlpha(1.0f);
            scanCodeShareAllInputText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mainText = scanCodeInputEditText.getText().toString();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Use CryptoCode to Encode this...");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Use CryptoCode to Encode this...\n \"" + mainText + "\"");
                    startActivity(Intent.createChooser(shareIntent, "Share Via"));
                }
            });
        }

        if (scanCodeInputEditText.getText().toString().length() < 2000) {
            scanCodePasteAllInputText.setEnabled(true);
            scanCodePasteAllInputText.setAlpha(1.0f);
            scanCodePasteAllInputText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData.Item pasteItem = clipboardManager.getPrimaryClip().getItemAt(0);
                    String dataToPaste = pasteItem.getText().toString();
                    if (!dataToPaste.isEmpty()) {
                        int currentCursorPosition = scanCodeInputEditText.getSelectionStart();
                        String prevString = scanCodeInputEditText.getText().toString();
                        String cursorToSetAfterPaste;
                        Toast.makeText(getContext(), ""+dataToPaste.length(), Toast.LENGTH_SHORT).show();
                        if (dataToPaste.length() > (1000 - scanCodeInputEditText.getText().toString().length())){
                            scanCodeInputEditText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(1000 - scanCodeInputEditText.getText().toString().length())) + prevString.substring(currentCursorPosition));
                            cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(1000 - scanCodeInputEditText.getText().toString().length()));
                        }else{
                            scanCodeInputEditText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste + prevString.substring(currentCursorPosition));
                            cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste;
                        }

                        scanCodeInputEditText.setSelection(cursorToSetAfterPaste.length());
                    } else {
                        Toast.makeText(getContext(), "Nothing To Paste!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            scanCodePasteAllInputText.setEnabled(false);
            scanCodePasteAllInputText.setAlpha(0.5f);
        }

        scanCodeOutputCharacterNumber.setText(scanCodeInputEditText.getText().toString().length() +" / 2000");


        scanCodeInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                scanCodeBarcodeImageView.setImageBitmap(null);
                scanCodeImageCloseBtn.setVisibility(View.GONE);
                scanCodeProcedureTextView.setText("");
                scanCodeSaveBtn.setEnabled(false);
                scanCodeSaveBtn.setAlpha(0.5f);
                scanCodeShareBtn.setEnabled(false);
                scanCodeShareBtn.setAlpha(0.5f);

                if (scanCodeInputEditText.getText().toString().length() == 0){
                    scanCodeBarcodeErrorTextView.setText("Please, enter some input text !!!"+'\n'+"( Using emojis or different language characters is not Recommended )");
                }else{
                    scanCodeBarcodeErrorTextView.setText("Please, choose any encode procedure !!!");
                }

                scanCodeOutputCharacterNumber.setText(scanCodeInputEditText.getText().toString().length() +" / 2000");

                if (scanCodeInputEditText.getText().length() > 0) {
                    scanCodeDeleteAllInputText.setEnabled(true);
                    scanCodeDeleteAllInputText.setAlpha(1.0f);
                    scanCodeDeleteAllInputText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            scanCodeInputEditText.setText("");
                        }
                    });
                } else {
                    scanCodeDeleteAllInputText.setEnabled(false);
                    scanCodeDeleteAllInputText.setAlpha(0.5f);
                }

                if (scanCodeInputEditText.getText().length() > 0) {
                    scanCodeCopyAllInputText.setEnabled(true);
                    scanCodeCopyAllInputText.setAlpha(1.0f);
                    scanCodeCopyAllInputText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipData clipData = ClipData.newPlainText("text", scanCodeInputEditText.getText().toString().trim());
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(getContext(), "Input Text is Copied!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    scanCodeCopyAllInputText.setEnabled(false);
                    scanCodeCopyAllInputText.setAlpha(0.5f);
                }

                if (scanCodeInputEditText.getText().length() > 0) {
                    scanCodeShareAllInputText.setEnabled(true);
                    scanCodeShareAllInputText.setAlpha(1.0f);
                    scanCodeShareAllInputText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String mainText = scanCodeInputEditText.getText().toString();
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Use CryptoCode to Encode this...");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, "Use CryptoCode to Encode this...\n \"" + mainText + "\"");
                            startActivity(Intent.createChooser(shareIntent, "Share Via"));
                        }
                    });
                } else {
                    scanCodeShareAllInputText.setEnabled(false);
                    scanCodeShareAllInputText.setAlpha(0.5f);
                }

                if (scanCodeInputEditText.getText().toString().length() < 2000) {
                    scanCodePasteAllInputText.setEnabled(true);
                    scanCodePasteAllInputText.setAlpha(1.0f);
                    scanCodePasteAllInputText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipData.Item pasteItem = clipboardManager.getPrimaryClip().getItemAt(0);
                            String dataToPaste = pasteItem.getText().toString();
                            if (!dataToPaste.isEmpty()) {
                                int currentCursorPosition = scanCodeInputEditText.getSelectionStart();
                                String prevString = scanCodeInputEditText.getText().toString();
                                String cursorToSetAfterPaste;
                                Toast.makeText(getContext(), ""+dataToPaste.length(), Toast.LENGTH_SHORT).show();
                                if (dataToPaste.length() > (2000 - scanCodeInputEditText.getText().toString().length())){
                                    scanCodeInputEditText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(2000 - scanCodeInputEditText.getText().toString().length())) + prevString.substring(currentCursorPosition));
                                    cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(2000 - scanCodeInputEditText.getText().toString().length()));
                                }else{
                                    scanCodeInputEditText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste + prevString.substring(currentCursorPosition));
                                    cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste;
                                }

                                scanCodeInputEditText.setSelection(cursorToSetAfterPaste.length());
                            } else {
                                Toast.makeText(getContext(), "Nothing To Paste!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    scanCodePasteAllInputText.setEnabled(false);
                    scanCodePasteAllInputText.setAlpha(0.5f);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        scanCodeSaveBtn.setEnabled(false);
        scanCodeSaveBtn.setAlpha(0.5f);

        scanCodeShareBtn.setEnabled(false);
        scanCodeShareBtn.setAlpha(0.5f);

        scanCodeImageCloseBtn.setVisibility(View.GONE);

        scanCodeCardModels = new ArrayList<>();

        scanCodeBarcodeProcedureCardRecyclerView = scanCodeEncodeView.findViewById(R.id.scanCodeBarcodeProcedureCardRecyclerView);
        scanCodeBarcodeProcedureCardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        scanCodeCardModels.add(new ScanCodeCardModel("UPC-A", 1));
        scanCodeCardModels.add(new ScanCodeCardModel("UPC-E", 2));
        scanCodeCardModels.add(new ScanCodeCardModel("EAN-8", 3));
        scanCodeCardModels.add(new ScanCodeCardModel("EAN-13", 4));
        scanCodeCardModels.add(new ScanCodeCardModel("Code 39", 5));
        scanCodeCardModels.add(new ScanCodeCardModel("Code 93", 6));
        scanCodeCardModels.add(new ScanCodeCardModel("Code 128", 7));
        scanCodeCardModels.add(new ScanCodeCardModel("Codabar", 8));
        scanCodeCardModels.add(new ScanCodeCardModel("ITF", 9));
        scanCodeCardModels.add(new ScanCodeCardModel("QR Code", 10));
        scanCodeCardModels.add(new ScanCodeCardModel("Data Matrix", 11));
        scanCodeCardModels.add(new ScanCodeCardModel("AZTEC", 12));
        scanCodeCardModels.add(new ScanCodeCardModel("PDF 417", 13));

        scanCodeSliderAdapter = new ScanCodeSliderAdapter(scanCodeCardModels, getContext());
        scanCodeBarcodeProcedureCardRecyclerView.setAdapter(scanCodeSliderAdapter);

//        scanCodeCardModels.add(new ScanCodeCardModel("MaxiCode", 14));
//        scanCodeCardModels.add(new ScanCodeCardModel("RSS-14", 15));
//        scanCodeCardModels.add(new ScanCodeCardModel("RSS-"+'\n'+"Expanded", 16));

        final MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        scanCodeSliderAdapter.setOnItemClickListener(new ScanCodeSliderAdapter.OnItemsClickListener() {

            @Override
            public void onItemClick(ScanCodeCardModel scanCodeCardModels) {

                String scanCodeInputText = scanCodeInputEditText.getText().toString().trim();

                String onlyDigit = "[0-9]+";
                String wholeAscii = "[[!-~][ ]]+";
                String onlyAlphaNumericCharacter = "[[0-9][A-Z][-./$% ]]+";
                String onlyNumericCharacter = "[[0-9][-$:/+.]]+";

                Pattern onlyDigitPattern = Pattern.compile(onlyDigit);
                Pattern onlyWholeAsciiPattern = Pattern.compile(wholeAscii);
                Pattern onlyAlphaNumericCharacterPattern = Pattern.compile(onlyAlphaNumericCharacter);
                Pattern onlyNumericCharacterPattern = Pattern.compile(onlyNumericCharacter);

                Matcher m1 = onlyDigitPattern.matcher(scanCodeInputText);
                Matcher m2 = onlyWholeAsciiPattern.matcher(scanCodeInputText);
                Matcher m3 = onlyAlphaNumericCharacterPattern.matcher(scanCodeInputText);

                //scanCodeInputEditText.setText(String.valueOf(scanCodeCardModels.getCardPosition()));

                try {

                    if (scanCodeCardModels.getCardPosition() == 1) {

                        scanCodeProcedureTextView.setText("   UPC-A   ");

                        if (scanCodeInputText.length() > 0) {

                            if (m1.matches()) {
                                if (scanCodeInputText.length() == 12) {
                                    int commonCalc = (3 * (Character.getNumericValue(scanCodeInputText.charAt(0)) + Character.getNumericValue(scanCodeInputText.charAt(2)) + Character.getNumericValue(scanCodeInputText.charAt(4)) + Character.getNumericValue(scanCodeInputText.charAt(6)) + Character.getNumericValue(scanCodeInputText.charAt(8)) + Character.getNumericValue(scanCodeInputText.charAt(10)))) + (Character.getNumericValue(scanCodeInputText.charAt(1)) + Character.getNumericValue(scanCodeInputText.charAt(3)) + Character.getNumericValue(scanCodeInputText.charAt(5)) + Character.getNumericValue(scanCodeInputText.charAt(7)) + Character.getNumericValue(scanCodeInputText.charAt(9)));

                                    int result = (commonCalc + Character.getNumericValue(scanCodeInputText.charAt(11))) % 10;

                                    if (result != 0) {
                                        scanCodeBarcodeImageView.setVisibility(View.GONE);
                                        scanCodeBarcodeErrorTextView.setText("Contents Do not Pass CheckSum");

                                        scanCodeSaveBtn.setEnabled(false);
                                        scanCodeSaveBtn.setAlpha(0.5f);

                                        scanCodeShareBtn.setEnabled(false);
                                        scanCodeShareBtn.setAlpha(0.5f);

                                    } else {
                                        scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                        scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.UPC_A, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                        scanCodeSaveBtn.setEnabled(true);
                                        scanCodeSaveBtn.setAlpha(1f);

                                        scanCodeShareBtn.setEnabled(true);
                                        scanCodeShareBtn.setAlpha(1f);

                                    }
                                    scanCodeImageCloseBtn.setVisibility(View.VISIBLE);
                                } else if (scanCodeInputText.length() == 11) {
//                                int commonCalc = (3 * (scanCodeInputText.charAt(0) + scanCodeInputText.charAt(2) + scanCodeInputText.charAt(4) + scanCodeInputText.charAt(6) + scanCodeInputText.charAt(8) + scanCodeInputText.charAt(10))) + (scanCodeInputText.charAt(1) + scanCodeInputText.charAt(3) + scanCodeInputText.charAt(5) + scanCodeInputText.charAt(7) + scanCodeInputText.charAt(9));
//                                int result = commonCalc % 10;
//                                if (result == 0){
//                                    scanCodeInputText = scanCodeInputText + "0";
//                                }else{
//                                    scanCodeInputText = scanCodeInputText + String.valueOf((10 - result));
//                                }
                                    scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                    scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.UPC_A, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                    scanCodeSaveBtn.setEnabled(true);
                                    scanCodeSaveBtn.setAlpha(1f);

                                    scanCodeShareBtn.setEnabled(true);
                                    scanCodeShareBtn.setAlpha(1f);

                                    scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                } else {

                                    scanCodeBarcodeImageView.setVisibility(View.GONE);
                                    scanCodeBarcodeErrorTextView.setText("Input length must be 11 or 12 digits long, but found " + scanCodeInputText.length());

                                    scanCodeSaveBtn.setEnabled(false);
                                    scanCodeSaveBtn.setAlpha(0.5f);

                                    scanCodeShareBtn.setEnabled(false);
                                    scanCodeShareBtn.setAlpha(0.5f);

                                    scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                }
                            } else {

                                scanCodeBarcodeImageView.setVisibility(View.GONE);
                                scanCodeBarcodeErrorTextView.setText("Only 0 to 9 digits are allowed as UPC-A input");

                                scanCodeSaveBtn.setEnabled(false);
                                scanCodeSaveBtn.setAlpha(0.5f);

                                scanCodeShareBtn.setEnabled(false);
                                scanCodeShareBtn.setAlpha(0.5f);

                                scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                            }

                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }

                    } else if (scanCodeCardModels.getCardPosition() == 2) {

                        scanCodeProcedureTextView.setText("   UPC-E   ");

                        if (scanCodeInputText.length() > 0) {

                            if (m1.matches()) {

                                if (scanCodeInputText.charAt(0) == '0' || scanCodeInputText.charAt(0) == '1') {

                                    if (scanCodeInputText.length() == 8) {

                                        int commonCalc = (3 * (Character.getNumericValue(scanCodeInputText.charAt(0))
                                                + Character.getNumericValue(scanCodeInputText.charAt(2))
                                                + Character.getNumericValue(scanCodeInputText.charAt(4))
                                                + Character.getNumericValue(scanCodeInputText.charAt(6))))
                                                + (Character.getNumericValue(scanCodeInputText.charAt(1))
                                                + Character.getNumericValue(scanCodeInputText.charAt(3))
                                                + Character.getNumericValue(scanCodeInputText.charAt(5))
                                                + Character.getNumericValue(scanCodeInputText.charAt(7)));

                                        int result = commonCalc % 10;

                                        if (result != 0) {
                                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                                            scanCodeBarcodeErrorTextView.setText("Contents Do not Pass CheckSum");

                                            scanCodeSaveBtn.setEnabled(false);
                                            scanCodeSaveBtn.setAlpha(0.5f);

                                            scanCodeShareBtn.setEnabled(false);
                                            scanCodeShareBtn.setAlpha(0.5f);

                                        } else {
                                            scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                            scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.UPC_E, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                            scanCodeSaveBtn.setEnabled(true);
                                            scanCodeSaveBtn.setAlpha(1f);

                                            scanCodeShareBtn.setEnabled(true);
                                            scanCodeShareBtn.setAlpha(1f);

                                        }
                                        scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                    } else if (scanCodeInputText.length() == 7) {

                                        scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                        scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.UPC_E, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                        scanCodeSaveBtn.setEnabled(true);
                                        scanCodeSaveBtn.setAlpha(1f);

                                        scanCodeShareBtn.setEnabled(true);
                                        scanCodeShareBtn.setAlpha(1f);

                                        scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                    } else {

                                        scanCodeBarcodeImageView.setVisibility(View.GONE);
                                        scanCodeBarcodeErrorTextView.setText("Input length must be 7 or 8 digits long, but found " + scanCodeInputText.length());

                                        scanCodeSaveBtn.setEnabled(false);
                                        scanCodeSaveBtn.setAlpha(0.5f);

                                        scanCodeShareBtn.setEnabled(false);
                                        scanCodeShareBtn.setAlpha(0.5f);

                                        scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                    }

                                } else {

                                    scanCodeBarcodeImageView.setVisibility(View.GONE);
                                    scanCodeBarcodeErrorTextView.setText("First Digit Must be 0 or 1");

                                    scanCodeSaveBtn.setEnabled(false);
                                    scanCodeSaveBtn.setAlpha(0.5f);

                                    scanCodeShareBtn.setEnabled(false);
                                    scanCodeShareBtn.setAlpha(0.5f);

                                    scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                }

                            } else {

                                scanCodeBarcodeImageView.setVisibility(View.GONE);
                                scanCodeBarcodeErrorTextView.setText("Only 0 to 9 digits are allowed as UPC-E input");

                                scanCodeSaveBtn.setEnabled(false);
                                scanCodeSaveBtn.setAlpha(0.5f);

                                scanCodeShareBtn.setEnabled(false);
                                scanCodeShareBtn.setAlpha(0.5f);

                                scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                            }

                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }

                    } else if (scanCodeCardModels.getCardPosition() == 3) {

                        scanCodeProcedureTextView.setText("   EAN-8   ");

                        if (scanCodeInputText.length() > 0) {

                            if (m1.matches()) {

                                if (scanCodeInputText.length() == 8) {

                                    int commonCalc = (3 * (Character.getNumericValue(scanCodeInputText.charAt(0))
                                            + Character.getNumericValue(scanCodeInputText.charAt(2))
                                            + Character.getNumericValue(scanCodeInputText.charAt(4))
                                            + Character.getNumericValue(scanCodeInputText.charAt(6))))
                                            + (Character.getNumericValue(scanCodeInputText.charAt(1))
                                            + Character.getNumericValue(scanCodeInputText.charAt(3))
                                            + Character.getNumericValue(scanCodeInputText.charAt(5))
                                            + Character.getNumericValue(scanCodeInputText.charAt(7)));

                                    int result = commonCalc % 10;
                                    if (result != 0) {
                                        scanCodeBarcodeImageView.setVisibility(View.GONE);
                                        scanCodeBarcodeErrorTextView.setText("Contents Do not Pass CheckSum");

                                        scanCodeSaveBtn.setEnabled(false);
                                        scanCodeSaveBtn.setAlpha(0.5f);

                                        scanCodeShareBtn.setEnabled(false);
                                        scanCodeShareBtn.setAlpha(0.5f);

                                    } else {

                                        scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                        scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.EAN_8, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                        scanCodeSaveBtn.setEnabled(true);
                                        scanCodeSaveBtn.setAlpha(1f);

                                        scanCodeShareBtn.setEnabled(true);
                                        scanCodeShareBtn.setAlpha(1f);

                                    }
                                    scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                } else if (scanCodeInputText.length() == 7) {

                                    scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                    scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.EAN_8, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                    scanCodeSaveBtn.setEnabled(true);
                                    scanCodeSaveBtn.setAlpha(1f);

                                    scanCodeShareBtn.setEnabled(true);
                                    scanCodeShareBtn.setAlpha(1f);

                                    scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                } else {

                                    scanCodeBarcodeImageView.setVisibility(View.GONE);
                                    scanCodeBarcodeErrorTextView.setText("Input length must be 7 or 8 digits long, but found " + scanCodeInputText.length());

                                    scanCodeSaveBtn.setEnabled(false);
                                    scanCodeSaveBtn.setAlpha(0.5f);

                                    scanCodeShareBtn.setEnabled(false);
                                    scanCodeShareBtn.setAlpha(0.5f);

                                    scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                }

                            } else {

                                scanCodeBarcodeImageView.setVisibility(View.GONE);
                                scanCodeBarcodeErrorTextView.setText("Only 0 to 9 digits are allowed as EAN-8 input");

                                scanCodeSaveBtn.setEnabled(false);
                                scanCodeSaveBtn.setAlpha(0.5f);

                                scanCodeShareBtn.setEnabled(false);
                                scanCodeShareBtn.setAlpha(0.5f);

                                scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                            }

                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }

                    } else if (scanCodeCardModels.getCardPosition() == 4) {

                        scanCodeProcedureTextView.setText("   EAN-13   ");

                        if (scanCodeInputText.length() > 0) {

                            if (m1.matches()) {

                                if (scanCodeInputText.length() == 13) {

                                    int commonCalc = (3 * (Character.getNumericValue(scanCodeInputText.charAt(1))
                                            + Character.getNumericValue(scanCodeInputText.charAt(3))
                                            + Character.getNumericValue(scanCodeInputText.charAt(5))
                                            + Character.getNumericValue(scanCodeInputText.charAt(7))
                                            + Character.getNumericValue(scanCodeInputText.charAt(9))
                                            + Character.getNumericValue(scanCodeInputText.charAt(11))))
                                            + (Character.getNumericValue(scanCodeInputText.charAt(0))
                                            + Character.getNumericValue(scanCodeInputText.charAt(2))
                                            + Character.getNumericValue(scanCodeInputText.charAt(4))
                                            + Character.getNumericValue(scanCodeInputText.charAt(6))
                                            + Character.getNumericValue(scanCodeInputText.charAt(8))
                                            + Character.getNumericValue(scanCodeInputText.charAt(10))
                                            + Character.getNumericValue(scanCodeInputText.charAt(12)));

                                    int result = commonCalc % 10;

                                    if (result != 0) {

                                        scanCodeBarcodeImageView.setVisibility(View.GONE);
                                        scanCodeBarcodeErrorTextView.setText("Contents Do not Pass CheckSum");

                                        scanCodeSaveBtn.setEnabled(false);
                                        scanCodeSaveBtn.setAlpha(0.5f);

                                        scanCodeShareBtn.setEnabled(false);
                                        scanCodeShareBtn.setAlpha(0.5f);

                                    } else {

                                        scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                        scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.EAN_13, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                        scanCodeSaveBtn.setEnabled(true);
                                        scanCodeSaveBtn.setAlpha(1f);

                                        scanCodeShareBtn.setEnabled(true);
                                        scanCodeShareBtn.setAlpha(1f);

                                    }
                                    scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                } else if (scanCodeInputText.length() == 12) {

                                    scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                    scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.EAN_13, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                    scanCodeSaveBtn.setEnabled(true);
                                    scanCodeSaveBtn.setAlpha(1f);

                                    scanCodeShareBtn.setEnabled(true);
                                    scanCodeShareBtn.setAlpha(1f);

                                    scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                } else {

                                    scanCodeBarcodeImageView.setVisibility(View.GONE);
                                    scanCodeBarcodeErrorTextView.setText("Input length must be 12 or 13 digits long, but found " + scanCodeInputText.length());

                                    scanCodeSaveBtn.setEnabled(false);
                                    scanCodeSaveBtn.setAlpha(0.5f);

                                    scanCodeShareBtn.setEnabled(false);
                                    scanCodeShareBtn.setAlpha(0.5f);

                                    scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                                }

                            } else {

                                scanCodeBarcodeImageView.setVisibility(View.GONE);
                                scanCodeBarcodeErrorTextView.setText("Only 0 to 9 digits are allowed as EAN-13 input");

                                scanCodeSaveBtn.setEnabled(false);
                                scanCodeSaveBtn.setAlpha(0.5f);

                                scanCodeShareBtn.setEnabled(false);
                                scanCodeShareBtn.setAlpha(0.5f);

                                scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                            }

                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }

                    } else if (scanCodeCardModels.getCardPosition() == 5) {

                        scanCodeProcedureTextView.setText("   CODE-39   ");

                        if (scanCodeInputText.length() > 0) {

                            if (m2.matches()) {

                                if (scanCodeInputText.length() <= 40){

                                    scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                    scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.CODE_39, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                    scanCodeSaveBtn.setEnabled(true);
                                    scanCodeSaveBtn.setAlpha(1f);

                                    scanCodeShareBtn.setEnabled(true);
                                    scanCodeShareBtn.setAlpha(1f);

                                }else{

                                    scanCodeBarcodeImageView.setVisibility(View.GONE);
                                    scanCodeBarcodeErrorTextView.setText("No more that 40 input characters are allowed!!!");

                                    scanCodeSaveBtn.setEnabled(false);
                                    scanCodeSaveBtn.setAlpha(0.5f);

                                    scanCodeShareBtn.setEnabled(false);
                                    scanCodeShareBtn.setAlpha(0.5f);

                                }

                            } else {

                                scanCodeBarcodeImageView.setVisibility(View.GONE);
                                scanCodeBarcodeErrorTextView.setText("Illegal Input Character Detected !!!");

                                scanCodeSaveBtn.setEnabled(false);
                                scanCodeSaveBtn.setAlpha(0.5f);

                                scanCodeShareBtn.setEnabled(false);
                                scanCodeShareBtn.setAlpha(0.5f);

                            }

                            scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }

                    } else if (scanCodeCardModels.getCardPosition() == 6) {

                        scanCodeProcedureTextView.setText("   CODE-93   ");

                        if (scanCodeInputText.length() > 0) {

                            if (m3.matches()) {

                                if (scanCodeInputText.length() <= 80){

                                    scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                    scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.CODE_93, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                    scanCodeSaveBtn.setEnabled(true);
                                    scanCodeSaveBtn.setAlpha(1f);

                                    scanCodeShareBtn.setEnabled(true);
                                    scanCodeShareBtn.setAlpha(1f);

                                }else{

                                    scanCodeBarcodeImageView.setVisibility(View.GONE);
                                    scanCodeBarcodeErrorTextView.setText("");

                                    scanCodeSaveBtn.setEnabled(false);
                                    scanCodeSaveBtn.setAlpha(0.5f);

                                    scanCodeShareBtn.setEnabled(false);
                                    scanCodeShareBtn.setAlpha(0.5f);

                                }

                            } else {

                                scanCodeBarcodeImageView.setVisibility(View.GONE);
                                scanCodeBarcodeErrorTextView.setText("(1) 0 to 9 digits" + '\n' + "(2) A to Z uppercase letters" + '\n' + "(3) -./$% special characters" + '\n' + '\n' + "are allowed only as Input");

                                scanCodeSaveBtn.setEnabled(false);
                                scanCodeSaveBtn.setAlpha(0.5f);

                                scanCodeShareBtn.setEnabled(false);
                                scanCodeShareBtn.setAlpha(0.5f);

                            }
                            scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }

                    } else if (scanCodeCardModels.getCardPosition() == 7) {

                        scanCodeProcedureTextView.setText("   CODE-128   ");

                        if (scanCodeInputText.length() > 0) {

                            if (scanCodeInputText.length() <= 80){

                                if (m2.matches()) {
                                    scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                    scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.CODE_128, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                    scanCodeSaveBtn.setEnabled(true);
                                    scanCodeSaveBtn.setAlpha(1f);

                                    scanCodeShareBtn.setEnabled(true);
                                    scanCodeShareBtn.setAlpha(1f);

                                } else {

                                    scanCodeBarcodeImageView.setVisibility(View.GONE);
                                    scanCodeBarcodeErrorTextView.setText("Illegal Input Character Detected !!!");

                                    scanCodeSaveBtn.setEnabled(false);
                                    scanCodeSaveBtn.setAlpha(0.5f);

                                    scanCodeShareBtn.setEnabled(false);
                                    scanCodeShareBtn.setAlpha(0.5f);

                                }

                            }else{

                                scanCodeBarcodeImageView.setVisibility(View.GONE);
                                scanCodeBarcodeErrorTextView.setText("Only 80 characters are allowed as CODE-128 input");

                                scanCodeSaveBtn.setEnabled(false);
                                scanCodeSaveBtn.setAlpha(0.5f);

                                scanCodeShareBtn.setEnabled(false);
                                scanCodeShareBtn.setAlpha(0.5f);

                            }


                            scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }

                    } else if (scanCodeCardModels.getCardPosition() == 8) {

                        scanCodeProcedureTextView.setText("   CODABAR   ");

                        if (scanCodeInputText.length() > 0) {

                            if (scanCodeInputText.charAt(0) == 'A' || scanCodeInputText.charAt(0) == 'B' || scanCodeInputText.charAt(0) == 'C' || scanCodeInputText.charAt(0) == 'D' || scanCodeInputText.charAt(0) == 'a' || scanCodeInputText.charAt(0) == 'b' || scanCodeInputText.charAt(0) == 'c' || scanCodeInputText.charAt(0) == 'd') {
                                if (scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'A' || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'B' || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'C' || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'D' || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'a' || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'b' || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'c' || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'd') {
                                    Matcher m4 = onlyNumericCharacterPattern.matcher(scanCodeInputText.substring(1, scanCodeInputText.length() - 1));
                                    if (m4.matches()) {
                                        scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                        scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.CODABAR, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                        scanCodeSaveBtn.setEnabled(true);
                                        scanCodeSaveBtn.setAlpha(1f);

                                        scanCodeShareBtn.setEnabled(true);
                                        scanCodeShareBtn.setAlpha(1f);

                                    } else {

                                        scanCodeBarcodeImageView.setVisibility(View.GONE);
                                        scanCodeBarcodeErrorTextView.setText("Illegal Input Character Detected!!!");

                                        scanCodeSaveBtn.setEnabled(false);
                                        scanCodeSaveBtn.setAlpha(0.5f);

                                        scanCodeShareBtn.setEnabled(false);
                                        scanCodeShareBtn.setAlpha(0.5f);

                                    }
                                } else {

                                    scanCodeBarcodeImageView.setVisibility(View.GONE);
                                    scanCodeBarcodeErrorTextView.setText("Illegal Start/Stop Symbol Detected");

                                    scanCodeSaveBtn.setEnabled(false);
                                    scanCodeSaveBtn.setAlpha(0.5f);

                                    scanCodeShareBtn.setEnabled(false);
                                    scanCodeShareBtn.setAlpha(0.5f);

                                }
                                scanCodeImageCloseBtn.setVisibility(View.VISIBLE);
                            } else if (scanCodeInputText.charAt(0) == 'E'
                                    || scanCodeInputText.charAt(0) == 'N'
                                    || scanCodeInputText.charAt(0) == '*'
                                    || scanCodeInputText.charAt(0) == 'T'
                                    || scanCodeInputText.charAt(0) == 'e'
                                    || scanCodeInputText.charAt(0) == 'n'
                                    || scanCodeInputText.charAt(0) == 't') {

                                if (scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'E'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'N'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == '*'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'T'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'e'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'n'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 't') {

                                    Matcher m4 = onlyNumericCharacterPattern.matcher(scanCodeInputText.substring(1, scanCodeInputText.length() - 1));
                                    if (m4.matches()) {
                                        scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                        scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder()
                                                .createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.CODABAR, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                        scanCodeSaveBtn.setEnabled(true);
                                        scanCodeSaveBtn.setAlpha(1f);

                                        scanCodeShareBtn.setEnabled(true);
                                        scanCodeShareBtn.setAlpha(1f);

                                    } else {

                                        scanCodeBarcodeImageView.setVisibility(View.GONE);
                                        scanCodeBarcodeErrorTextView.setText("Illegal Input Character Detected!!!");

                                        scanCodeSaveBtn.setEnabled(false);
                                        scanCodeSaveBtn.setAlpha(0.5f);

                                        scanCodeShareBtn.setEnabled(false);
                                        scanCodeShareBtn.setAlpha(0.5f);

                                    }
                                } else {

                                    scanCodeBarcodeImageView.setVisibility(View.GONE);
                                    scanCodeBarcodeErrorTextView.setText("Illegal Start/Stop Symbol Detected");

                                    scanCodeSaveBtn.setEnabled(false);
                                    scanCodeSaveBtn.setAlpha(0.5f);

                                    scanCodeShareBtn.setEnabled(false);
                                    scanCodeShareBtn.setAlpha(0.5f);

                                }
                                scanCodeImageCloseBtn.setVisibility(View.VISIBLE);
                            } else {
                                if (scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'A'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'B'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'C'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'D'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'E'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'N'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == '*'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'T'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'a'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'b'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'c'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'd'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'e'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 'n'
                                        || scanCodeInputText.charAt(scanCodeInputText.length() - 1) == 't') {

                                    scanCodeBarcodeImageView.setVisibility(View.GONE);
                                    scanCodeBarcodeErrorTextView.setText("Illegal Start/Stop Symbol Detected");

                                    scanCodeSaveBtn.setEnabled(false);
                                    scanCodeSaveBtn.setAlpha(0.5f);

                                    scanCodeShareBtn.setEnabled(false);
                                    scanCodeShareBtn.setAlpha(0.5f);

                                } else {

                                    Matcher m4 = onlyNumericCharacterPattern.matcher(scanCodeInputText);
                                    if (m4.matches()) {

                                        scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                        scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.CODABAR, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                        scanCodeSaveBtn.setEnabled(true);
                                        scanCodeSaveBtn.setAlpha(1f);

                                        scanCodeShareBtn.setEnabled(true);
                                        scanCodeShareBtn.setAlpha(1f);

                                    } else {

                                        scanCodeBarcodeImageView.setVisibility(View.GONE);
                                        scanCodeBarcodeErrorTextView.setText("Illegal Input Character Detected!!!");

                                        scanCodeSaveBtn.setEnabled(false);
                                        scanCodeSaveBtn.setAlpha(0.5f);

                                        scanCodeShareBtn.setEnabled(false);
                                        scanCodeShareBtn.setAlpha(0.5f);

                                    }

                                }
                                scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                            }

                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }

                    } else if (scanCodeCardModels.getCardPosition() == 9) {

                        scanCodeProcedureTextView.setText("   ITF   ");

                        if (scanCodeInputText.length() > 0) {

                            if (m1.matches()) {

                                if (scanCodeInputText.length() % 2 == 0) {

                                    scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                    scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.ITF, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                    scanCodeSaveBtn.setEnabled(true);
                                    scanCodeSaveBtn.setAlpha(1f);

                                    scanCodeShareBtn.setEnabled(true);
                                    scanCodeShareBtn.setAlpha(1f);

                                } else {

                                    scanCodeBarcodeImageView.setVisibility(View.GONE);
                                    scanCodeBarcodeErrorTextView.setText("Input Length should be even");

                                    scanCodeSaveBtn.setEnabled(false);
                                    scanCodeSaveBtn.setAlpha(0.5f);

                                    scanCodeShareBtn.setEnabled(false);
                                    scanCodeShareBtn.setAlpha(0.5f);

                                }

                            } else {

                                scanCodeBarcodeImageView.setVisibility(View.GONE);
                                scanCodeBarcodeErrorTextView.setText("Illegal Input Character Detected!!!");

                                scanCodeSaveBtn.setEnabled(false);
                                scanCodeSaveBtn.setAlpha(0.5f);

                                scanCodeShareBtn.setEnabled(false);
                                scanCodeShareBtn.setAlpha(0.5f);

                            }
                            scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }
                    } else if (scanCodeCardModels.getCardPosition() == 10) {

                        scanCodeProcedureTextView.setText("   QR-CODE   ");

                        if (scanCodeInputText.length() > 0) {

                            scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                            scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.QR_CODE, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                            scanCodeSaveBtn.setEnabled(true);
                            scanCodeSaveBtn.setAlpha(1f);

                            scanCodeShareBtn.setEnabled(true);
                            scanCodeShareBtn.setAlpha(1f);

                            scanCodeImageCloseBtn.setVisibility(View.VISIBLE);
                        } else {
                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);
                        }
                    } else if (scanCodeCardModels.getCardPosition() == 11) {

                        scanCodeProcedureTextView.setText("   DATA MATRIX   ");

                        if (scanCodeInputText.length() > 0) {

                            int flag = 0;

                            for (int i = 0; i < scanCodeInputText.length(); i++) {
                                if (!(scanCodeInputText.charAt(i) <= 255)) {
                                    flag = 1;
                                    break;
                                }
                            }

                            if (flag == 0) {

                                scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.DATA_MATRIX, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                scanCodeSaveBtn.setEnabled(true);
                                scanCodeSaveBtn.setAlpha(1f);

                                scanCodeShareBtn.setEnabled(true);
                                scanCodeShareBtn.setAlpha(1f);

                            } else {

                                scanCodeBarcodeImageView.setVisibility(View.GONE);
                                scanCodeBarcodeErrorTextView.setText("Illegal Input Character Detected !!!");

                                scanCodeSaveBtn.setEnabled(false);
                                scanCodeSaveBtn.setAlpha(0.5f);

                                scanCodeShareBtn.setEnabled(false);
                                scanCodeShareBtn.setAlpha(0.5f);

                            }
                            scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }

                    } else if (scanCodeCardModels.getCardPosition() == 12) {

                        scanCodeProcedureTextView.setText("   AZTEC   ");

                        if (scanCodeInputText.length() > 0) {

                            scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                            scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.AZTEC, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                            scanCodeSaveBtn.setEnabled(true);
                            scanCodeSaveBtn.setAlpha(1f);

                            scanCodeShareBtn.setEnabled(true);
                            scanCodeShareBtn.setAlpha(1f);

                            scanCodeImageCloseBtn.setVisibility(View.VISIBLE);
                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }

                    } else if (scanCodeCardModels.getCardPosition() == 13) {

                        scanCodeProcedureTextView.setText("   PDF-417   ");

                        if (scanCodeInputText.length() > 0) {

                            int flag = 0;

                            for (int i = 0; i < scanCodeInputText.length(); i++) {
                                if (!(scanCodeInputText.charAt(i) <= 255)) {
                                    flag = 1;
                                    break;
                                }
                            }

                            if (flag == 0) {

                                scanCodeBarcodeImageView.setVisibility(View.VISIBLE);
                                scanCodeBarcodeImageView.setImageBitmap(new BarcodeEncoder().createBitmap(multiFormatWriter.encode(scanCodeInputText, BarcodeFormat.PDF_417, scanCodeBarcodeImageView.getWidth(), scanCodeBarcodeImageView.getHeight())));
                                scanCodeSaveBtn.setEnabled(true);
                                scanCodeSaveBtn.setAlpha(1f);

                                scanCodeShareBtn.setEnabled(true);
                                scanCodeShareBtn.setAlpha(1f);

                            } else {

                                scanCodeBarcodeImageView.setVisibility(View.GONE);
                                scanCodeBarcodeErrorTextView.setText("Illegal Character Detected!!!");

                                scanCodeSaveBtn.setEnabled(false);
                                scanCodeSaveBtn.setAlpha(0.5f);

                                scanCodeShareBtn.setEnabled(false);
                                scanCodeShareBtn.setAlpha(0.5f);

                            }
                            scanCodeImageCloseBtn.setVisibility(View.VISIBLE);

                        } else {

                            scanCodeBarcodeImageView.setVisibility(View.GONE);
                            scanCodeBarcodeErrorTextView.setText("Empty Input Detected !!!");

                            scanCodeSaveBtn.setEnabled(false);
                            scanCodeSaveBtn.setAlpha(0.5f);

                            scanCodeShareBtn.setEnabled(false);
                            scanCodeShareBtn.setAlpha(0.5f);

                            scanCodeImageCloseBtn.setVisibility(View.GONE);

                        }

                    }

                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });

        scanCodeSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    reqStoragePermission();
                } else {

                    BitmapDrawable drawable = (BitmapDrawable) scanCodeBarcodeImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    saveScanCodeImage(bitmap);

                }

            }
        });

        scanCodeShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable = (BitmapDrawable) scanCodeBarcodeImageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                shareScanCodeImage(bitmap);

            }
        });

        scanCodeImageCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scanCodeBarcodeImageView.setImageBitmap(null);
                scanCodeImageCloseBtn.setVisibility(View.GONE);
                scanCodeProcedureTextView.setText("");
                scanCodeSaveBtn.setEnabled(false);
                scanCodeSaveBtn.setAlpha(0.5f);
                scanCodeShareBtn.setEnabled(false);
                scanCodeShareBtn.setAlpha(0.5f);

                if (scanCodeInputEditText.getText().toString().length() == 0){
                    scanCodeBarcodeErrorTextView.setText("Please, enter some input text !!!"+'\n'+"( Using emojis or different language characters is not Recommended )");
                }else{
                    scanCodeBarcodeErrorTextView.setText("Please, choose any encode procedure !!!");
                }

                //scanCodeBarcodeImageView.destroyDrawingCache();

            }
        });


        return scanCodeEncodeView;
    }

    private void saveScanCodeImage(Bitmap bitmap) {

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH-mm-ss", Locale.getDefault()).format(new Date());

        String root = Environment.getExternalStorageDirectory().toString() + "/CryptoCode/";
        File storePath = new File(root);
        storePath.mkdirs();
        String fileName = "CryptoCode-ScanCode-" + currentDate + "-" + currentTime + ".jpg";
        File scanCodeImageFile = new File(storePath, fileName);
        MediaScannerConnection.scanFile(getContext(), new String[]{scanCodeImageFile.getAbsolutePath()}, new String[]{"images/jpeg"}, null);
        if (scanCodeImageFile.exists()) {
            scanCodeImageFile.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(scanCodeImageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(getContext(), "Image Saved" + storePath, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void shareScanCodeImage(Bitmap shareBmp) {

        Uri imageUri = saveCachedImage(shareBmp);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("image/png");
        startActivity(shareIntent);

    }

    private Uri saveCachedImage(Bitmap shareBmp) {

        File imagesCacheFolder = new File(getContext().getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesCacheFolder.mkdirs();
            File file = new File(imagesCacheFolder, "shared_image.png");
            FileOutputStream fos = new FileOutputStream(file);
            shareBmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            uri = FileProvider.getUriForFile(getContext(), "com.example.gopontext.fileprovider", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;

    }

    private void reqStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(getContext())
                    .setTitle("Permission Needed")
                    .setMessage("Need Permission to save image")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "ACCESS GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "ACCESS DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}