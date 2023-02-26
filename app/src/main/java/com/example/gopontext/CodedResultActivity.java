package com.example.gopontext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CodedResultActivity extends AppCompatActivity {

    ASCII ascii = new ASCII();
    ADFGVX adfgvx = new ADFGVX();
    AFFINE affine = new AFFINE();
    ATBASH atbash = new ATBASH();
    AUTOKEY autokey = new AUTOKEY();
    BASE64 base64 = new BASE64();
    BEAUFORT beaufort = new BEAUFORT();
    BIN bin = new BIN();
    CAESER caeser = new CAESER();
    HEX hex = new HEX();
    OCTAL octal = new OCTAL();
    PORTA porta = new PORTA();
    RAILFENCE railfence = new RAILFENCE();
    ROT13 rot13 = new ROT13();
    VIGENERE vigenere = new VIGENERE();

    protected MaterialSpinner firstCipherSpinner, secondCipherSpinner;
    protected ConstraintLayout firstCipherSpinnerLayout, firstAddBtnLayout, secondCipherSpinnerLayout, secondAddBtnLayout;
    protected ImageButton goPrevActivityButton, firstPlusBtn, secondPlusBtn, firstSpinnerDelBtn, secondSpinnerDelBtn, outputCopyRoundButton, outputShareRoundButton;
    protected ImageView firstLeftDownArrowImageView, firstRightDownArrowImageView, secondLeftDownArrowImageView, secondRightDownArrowImageView, thirdLeftDownArrowImageView, thirdRightDownArrowImageView;
    protected String[] cipherMethodsText = {"ASCII", "ADFGVX", "AFFINE", "ATBASH", "AUTOKEY", "BINARY", "BEAUFORT", "BASE64", "CAESER", "HEXADECIMAL", "OCTAL", "PORTA", "RAILFENCE", "ROT13", "VIGENERE"};
    protected String[] cipherMethodsFont = {"BETAMAZE", "BRAILLE", "ELIAN SCRIPT", "AMERICAN SIGN LANGUAGE", "MORSE CODE", "PIGPEN", "SEMAPHORE", "UPSIDE DOWN", "WINGDINGS"};

    protected TextView activityTitleTextView, firstCipherTextView, inputTextView, outputTextView, outputCharacterNumber;
    protected ScrollView outputTextViewScrollView;
    protected ClipboardManager clipboardManager;

    protected String matrix36CharPat = "ph0qg64mea1yl2nofdxkr3cvs5zw7bj9uti8";
    protected String keyText = "CryPtoJetDEnCoDECipHer";
    protected int INTKEYA = 7;
    protected int INTKEYB = 2;
    protected int OFFSETINT = 10;
    protected int INTKEYSINGLE = 4;
    protected String MAININPUTSTRING;

    protected int DEncodeStatus;
    protected int cipherNumber;
    protected int twoCipherTransformation;
    protected int cipherFrag;

    protected int firstCipherNumber = 0;
    protected int secondCipherNumber = 0;

    protected String outputText;
    protected String cipherOutput;
    protected String firstOutputString;
    protected String secondOutputString;
//    protected String thirdOutputString;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coded_result);

        MAININPUTSTRING = getIntent().getExtras().getString("inputString");
        DEncodeStatus = getIntent().getExtras().getInt("DEncodeStatus");
        cipherNumber = getIntent().getExtras().getInt("cipherNumber");
        cipherFrag = getIntent().getExtras().getInt("cipherFrag");


        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        activityTitleTextView = findViewById(R.id.activityTitleTextView);
        firstCipherSpinnerLayout = findViewById(R.id.firstCipherSpinnerLayout);
        secondCipherSpinnerLayout = findViewById(R.id.secondCipherSpinnerLayout);
        firstLeftDownArrowImageView = findViewById(R.id.firstLeftDownArrowImageView);
        firstRightDownArrowImageView = findViewById(R.id.firstRightDownArrowImageView);
        secondLeftDownArrowImageView = findViewById(R.id.secondLeftDownArrowImageView);
        secondRightDownArrowImageView = findViewById(R.id.secondRightDownArrowImageView);
        thirdLeftDownArrowImageView = findViewById(R.id.thirdLeftDownArrowImageView);
        thirdRightDownArrowImageView = findViewById(R.id.thirdRightDownArrowImageView);
        firstAddBtnLayout = findViewById(R.id.firstAddBtnLayout);
        secondAddBtnLayout = findViewById(R.id.secondAddBtnLayout);
        firstPlusBtn = findViewById(R.id.firstPlusBtn);
        secondPlusBtn = findViewById(R.id.secondPlusBtn);
        firstSpinnerDelBtn = findViewById(R.id.firstSpinnerDelBtn);
        secondSpinnerDelBtn = findViewById(R.id.secondSpinnerDelBtn);
        outputCopyRoundButton = findViewById(R.id.outputCopyRoundButton);
        outputShareRoundButton = findViewById(R.id.scanCodeOutputShareRoundButton);
        firstCipherTextView = findViewById(R.id.firstCipherTextView);
        inputTextView = findViewById(R.id.inputTextView);
        outputTextView = findViewById(R.id.outputTextView);
        outputTextViewScrollView = findViewById(R.id.outputTextViewScrollView);
        outputCharacterNumber = findViewById(R.id.outputCharacterNumber);
        goPrevActivityButton = findViewById(R.id.goPrevActivityButton);

        firstCipherSpinner = findViewById(R.id.firstCipherSpinner);
        secondCipherSpinner = findViewById(R.id.secondCipherSpinner);

        firstCipherSpinner.setItems("--EMPTY--", "ASCII", "ADFGVX", "AFFINE", "ATBASH", "AUTOKEY", "BINARY", "BEAUFORT", "BASE64", "CAESER", "HEXADECIMAL", "OCTAL", "PORTA", "RAILFENCE", "ROT13", "VIGENERE");
        secondCipherSpinner.setItems("--EMPTY--", "ASCII", "ADFGVX", "AFFINE", "ATBASH", "AUTOKEY", "BINARY", "BEAUFORT", "BASE64", "CAESER", "HEXADECIMAL", "OCTAL", "PORTA", "RAILFENCE", "ROT13", "VIGENERE");

        if (cipherFrag == 0) {
            activityTitleTextView.setText("CipherText");
        } else if (cipherFrag == 1) {
            activityTitleTextView.setText("CipherFont");
        }

        inputTextView.setText(MAININPUTSTRING);
        cipherOutput = MAININPUTSTRING;

        goPrevActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodedResultActivity.this.finish();
            }
        });

        inputTextView.setMovementMethod(new ScrollingMovementMethod());
        inputTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (inputTextView.getLineCount() >= 5) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }

                return false;

            }
        });

        //outputTextView.setMovementMethod(new ScrollingMovementMethod());
        outputTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (inputTextView.getLineCount() >= 5) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;

            }
        });

        if (cipherFrag == 0) {

            firstCipherTextView.setText(cipherMethodsText[cipherNumber - 1]);

            firstCipherSpinnerLayout.setVisibility(View.GONE);
            secondCipherSpinnerLayout.setVisibility(View.GONE);
            secondAddBtnLayout.setVisibility(View.GONE);

            firstLeftDownArrowImageView.setVisibility(View.INVISIBLE);
            firstRightDownArrowImageView.setVisibility(View.INVISIBLE);
            secondLeftDownArrowImageView.setVisibility(View.INVISIBLE);
            secondRightDownArrowImageView.setVisibility(View.INVISIBLE);

            final AsyncBgTask firstCipherProc = new AsyncBgTask();
            firstCipherProc.execute();

            firstPlusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstCipherSpinnerLayout.setVisibility(View.VISIBLE);
                    firstCipherNumber = firstCipherSpinner.getSelectedIndex();
                    firstLeftDownArrowImageView.setVisibility(View.VISIBLE);
                    firstRightDownArrowImageView.setVisibility(View.VISIBLE);
                    secondAddBtnLayout.setVisibility(View.VISIBLE);
                    firstPlusBtn.setVisibility(View.GONE);
                    Toast.makeText(CodedResultActivity.this, "" + firstCipherNumber, Toast.LENGTH_SHORT).show();
                    firstCipherSpinner.setSelectedIndex(0);
//                cipherOutput = outputTextView.getText().toString();
//                twoCipherTransformation = 0;
//                AsyncBgTask secondCipherPlusProcTask = new AsyncBgTask();
//                secondCipherPlusProcTask.execute();

                }
            });

            firstSpinnerDelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstCipherNumber = 0;
                    firstCipherSpinnerLayout.setVisibility(View.GONE);
                    firstLeftDownArrowImageView.setVisibility(View.INVISIBLE);
                    firstRightDownArrowImageView.setVisibility(View.INVISIBLE);
                    secondAddBtnLayout.setVisibility(View.GONE);
                    firstPlusBtn.setVisibility(View.VISIBLE);
                    firstCipherSpinner.setSelectedIndex(0);

                    if (secondCipherNumber == 0) {
                        outputTextView.setText(firstOutputString);
                    } else if (secondCipherNumber > 0) {
                        cipherOutput = firstOutputString;
                        twoCipherTransformation = 0;
                        AsyncBgTask firstSpinnerDelProcTask = new AsyncBgTask();
                        firstSpinnerDelProcTask.execute();
                    }


                }
            });


            secondPlusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    secondCipherSpinnerLayout.setVisibility(View.VISIBLE);
                    secondCipherNumber = secondCipherSpinner.getSelectedIndex();
                    secondLeftDownArrowImageView.setVisibility(View.VISIBLE);
                    secondRightDownArrowImageView.setVisibility(View.VISIBLE);
                    secondPlusBtn.setVisibility(View.GONE);
                    secondCipherSpinner.setSelectedIndex(0);
//                cipherOutput = outputTextView.getText().toString();
//                twoCipherTransformation = 0;
//                AsyncBgTask thirdCipherPlusProcTask = new AsyncBgTask();
//                thirdCipherPlusProcTask.execute();
                }
            });


            secondSpinnerDelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    secondCipherNumber = 0;
                    secondCipherSpinnerLayout.setVisibility(View.GONE);
                    secondLeftDownArrowImageView.setVisibility(View.INVISIBLE);
                    secondRightDownArrowImageView.setVisibility(View.INVISIBLE);
                    secondPlusBtn.setVisibility(View.VISIBLE);
                    secondCipherSpinner.setSelectedIndex(0);

                    if (firstCipherNumber == 0) {
                        outputTextView.setText(firstOutputString);

                    } else if (firstCipherNumber > 0) {
                        cipherOutput = firstOutputString;
                        twoCipherTransformation = 0;
                        AsyncBgTask secondSpinnerDelProcTwo = new AsyncBgTask();
                        secondSpinnerDelProcTwo.execute();

                    }
                }
            });

            firstCipherSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    firstCipherNumber = position;


                    if (firstCipherNumber > 0) {

                        cipherOutput = firstOutputString;

                        if (secondCipherNumber == 0) {

                            twoCipherTransformation = 0;

                        } else if (secondCipherNumber > 0) {

                            twoCipherTransformation = 1;

                        }

                        AsyncBgTask secondCipherSpinnerProcTask = new AsyncBgTask();
                        secondCipherSpinnerProcTask.execute();

                    } else if (firstCipherNumber == 0) {

                        if (secondCipherNumber == 0) {
                            outputTextView.setText(firstOutputString);
                        } else if (secondCipherNumber > 0) {
                            cipherOutput = firstOutputString;
                            twoCipherTransformation = 0;
                            AsyncBgTask firstSpinnerDelProcTask = new AsyncBgTask();
                            firstSpinnerDelProcTask.execute();
                        }

                    }

                }
            });

            secondCipherSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    secondCipherNumber = position;

                    if (secondCipherNumber > 0) {

                        if (firstCipherNumber == 0) {

                            cipherOutput = firstOutputString;

                        } else if (firstCipherNumber > 0) {

                            cipherOutput = secondOutputString;

                        }

                        twoCipherTransformation = 0;
                        AsyncBgTask thirdCipherSpinnerProcTask = new AsyncBgTask();
                        thirdCipherSpinnerProcTask.execute();

                    } else if (secondCipherNumber == 0) {

                        if (firstCipherNumber == 0) {
                            outputTextView.setText(firstOutputString);

                        } else if (firstCipherNumber > 0) {
                            cipherOutput = firstOutputString;
                            twoCipherTransformation = 0;
                            AsyncBgTask secondSpinnerDelProcTwo = new AsyncBgTask();
                            secondSpinnerDelProcTwo.execute();

                        }

                    }


                }
            });


            outputCopyRoundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData outClipData = ClipData.newPlainText("OutputText", outputTextView.getText().toString());
                    clipboardManager.setPrimaryClip(outClipData);
                    Toast.makeText(CodedResultActivity.this, "Output Text is Copied!!!", Toast.LENGTH_SHORT).show();

                }
            });

            outputShareRoundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mainText;
                    if (outputTextView.getText().toString().length() <= 100) {
                        mainText = outputTextView.getText().toString();
                    } else {
                        mainText = outputTextView.getText().toString().substring(0, 100) + "...";
                    }
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Use CryptoCode to Decode this...");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Use CryptoCode to Decode this...\n \"" + mainText + "\"");
                    startActivity(Intent.createChooser(shareIntent, "Share Via"));
                }
            });

        } else if (cipherFrag == 1) {

            if (cipherNumber == 101 || cipherNumber == 102 || cipherNumber == 103 || cipherNumber == 104 || cipherNumber == 106 || cipherNumber == 107 || cipherNumber == 108) {

                outputCopyRoundButton.setVisibility(View.GONE);

            }

            firstCipherSpinnerLayout.setVisibility(View.GONE);
            secondCipherSpinnerLayout.setVisibility(View.GONE);
            firstAddBtnLayout.setVisibility(View.GONE);
            secondAddBtnLayout.setVisibility(View.GONE);

            firstCipherTextView.setText(cipherMethodsFont[cipherNumber - 101]);

            cipherFontProcedure(MAININPUTSTRING, cipherNumber, DEncodeStatus);

            //Rect bounds = new Rect();
            //outputTextView.getPaint().getTextBounds(outputTextView.getText().toString(),0, outputTextView.getText().toString().length(),bounds);


//            final int[] outputLintCount = {0};
//            final int[] lumgth = {0};
//
//            outputTextView.post(new Runnable() {
//                @Override
//                public void run() {
//                    outputLintCount[0] = outputTextView.getLineCount();
//                    lumgth[0] = 90;
//                    Toast.makeText(CodedResultActivity.this, ""+ lumgth[0], Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            Toast.makeText(CodedResultActivity.this, ""+ lumgth[0], Toast.LENGTH_SHORT).show();

//            ViewTreeObserver vto = this.outputTextView.getViewTreeObserver();
//            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//                @Override
//                public void onGlobalLayout() {
//                    ViewTreeObserver obs = outputTextView.getViewTreeObserver();
//                    obs.removeGlobalOnLayoutListener(this);
//                    outputLintCount[0] = outputTextView.getLineCount();
//
//                }
//            });

            //Toast.makeText(CodedResultActivity.this, "LineCount: "+ outputLintCount[0][0] +"LineHeight: "+outputTextView.getLineHeight()+"LineSpaceExtra: "+outputTextView.getLineSpacingExtra(), Toast.LENGTH_LONG).show();


            outputCopyRoundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData outClipData = ClipData.newPlainText("OutputText", outputTextView.getText().toString());
                    clipboardManager.setPrimaryClip(outClipData);
                    Toast.makeText(CodedResultActivity.this, "Output Text is Copied!!!", Toast.LENGTH_SHORT).show();

                }
            });

            if (cipherNumber == 101 || cipherNumber == 102 || cipherNumber == 103 || cipherNumber == 104 || cipherNumber == 106 || cipherNumber == 107 || cipherNumber == 108) {

                outputShareRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //outputTextView.measure(10000,10000);

//                    Rect bounds = new Rect();
//                    outputTextView.getPaint().getTextBounds(outputTextView.getText().toString(), 0, outputTextView.getText().length(), bounds);

//                    Paint.FontMetrics metrics = outputTextView.getPaint().getFontMetrics();
//                    int height = (int) (metrics.bottom - metrics.top);

//                    Toast.makeText(getApplicationContext(), ""+ (outputTextViewScrollView.getChildAt(0).getHeight() - outputTextViewScrollView.getHeight()), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), ""+ outputTextView.getHeight(), Toast.LENGTH_SHORT).show();

                        outputTextView.setDrawingCacheEnabled(true);
                        outputTextView.buildDrawingCache();
                        Bitmap bitmap = Bitmap.createBitmap(outputTextView.getDrawingCache());
                        bitmap.setHeight(outputTextView.getHeight() - 37);
                        bitmap.setWidth(outputTextView.getWidth());
                        shareCipherFontImage(bitmap);

                    }
                });

            } else {

                outputShareRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String mainText;
                        if (outputTextView.getText().toString().length() <= 100) {
                            mainText = outputTextView.getText().toString();
                        } else {
                            mainText = outputTextView.getText().toString().substring(0, 100) + " (+cont.)";
                        }
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Use CryptoCode to Decode this...");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Use CryptoCode to Decode this...\n \"" + mainText + "\"");
                        startActivity(Intent.createChooser(shareIntent, "Share Via"));

                    }
                });


            }


        }

        //cipherOutput = cipherProcedure(MAININPUTSTRING, cipherNumber, firstCipherNumber, secondCipherNumber, DEncodeStatus, 0);
        //outputTextView.setText(cipherOutput);
        //firstOutputString = outputTextView.getText().toString();
        //outputCharacterNumber.setText("Output Length: " + cipherOutput.length());

    }

    private void shareCipherFontImage(Bitmap shareBmp) {

        Uri imageUri = saveCachedImage(shareBmp);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("image/png");
        startActivity(shareIntent);

    }

    private Uri saveCachedImage(Bitmap shareBmp) {

        File imagesCacheFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesCacheFolder.mkdirs();
            File file = new File(imagesCacheFolder, "shared_image.png");
            FileOutputStream fos = new FileOutputStream(file);
            shareBmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            uri = FileProvider.getUriForFile(this, "com.example.gopontext.fileprovider", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;

    }

    protected void cipherFontProcedure(String inputString, int cipherFontNumber, int DEncodeStatus) {

        if (DEncodeStatus == 1) {
            applyFontEncodeCipher(inputString, cipherFontNumber);
        } else if (DEncodeStatus == 2) {
            applyFontDecodeCipher(inputString.trim(), cipherFontNumber);
        }

    }

    protected void applyFontEncodeCipher(String inputString, int cipherFontNumber) {

        if (cipherFontNumber == 101) {

            StringBuilder output = new StringBuilder();

            String betaMaze = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_:;\",.\\(\\)";

            Typeface betaMazeFontType = ResourcesCompat.getFont(getApplicationContext(), R.font.betamaze);
            outputTextView.setTextSize(25f);
            outputTextView.setTypeface(betaMazeFontType);

            for (int i = 0; i < inputString.length(); i++) {

                if (betaMaze.contains(Character.toString(inputString.charAt(i)))) {

                    output.append(inputString.charAt(i));

                } else {

                    output.append(Character.toChars(128473));

                }

            }

            //Toast.makeText(this, ""+output.toString(), Toast.LENGTH_SHORT).show();

            outputTextView.setText(output.toString());


        } else if (cipherFontNumber == 102) {

            StringBuilder output = new StringBuilder();

            String braille = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM$+*=',\".;:?!";
//            Pattern braillePattern = Pattern.compile(braille);
//            Matcher brailleMatcher = braillePattern.matcher(inputString);

            Typeface brailleFontType = ResourcesCompat.getFont(getApplicationContext(), R.font.braille);
            outputTextView.setTextSize(25f);
            outputTextView.setTypeface(brailleFontType);

            for (int i = 0; i < inputString.length(); i++) {

                if (braille.contains(Character.toString(inputString.charAt(i)))) {

                    output.append(inputString.charAt(i));

                } else {

                    output.append(Character.toChars(128472));

                }
            }

            outputTextView.setText(output.toString());


        } else if (cipherFontNumber == 103) {

            StringBuilder output = new StringBuilder();

            String elianScript = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
//            Pattern elianScriptPattern = Pattern.compile(elianScript);
//            Matcher elianScriptMatcher = elianScriptPattern.matcher(inputString);


            Typeface elianScriptFontType = ResourcesCompat.getFont(getApplicationContext(), R.font.elian_script);
            outputTextView.setTextSize(25f);
            outputTextView.setTypeface(elianScriptFontType);
            for (int i = 0; i < inputString.length(); i++) {

                if (elianScript.contains(Character.toString(inputString.charAt(i)))) {

                    output.append(inputString.charAt(i));

                } else {

                    output.append(Character.toChars(128472));

                }
            }

            outputTextView.setText(output.toString());


        } else if (cipherFontNumber == 104) {

            StringBuilder output = new StringBuilder();

            String asl = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz- ";
//            Pattern aslPattern = Pattern.compile(asl);
//            Matcher aslMatcher = aslPattern.matcher(inputString);

            Typeface aslFontType = ResourcesCompat.getFont(getApplicationContext(), R.font.asl);
            outputTextView.setTextSize(30f);
            outputTextView.setTypeface(aslFontType);
            for (int i = 0; i < inputString.length(); i++) {

                if (asl.contains(Character.toString(inputString.charAt(i)))) {

                    output.append(inputString.charAt(i));

                } else {

                    output.append(Character.toChars(128472));

                }
            }

            outputTextView.setText(output.toString());

        } else if (cipherFontNumber == 105) {

            StringBuilder output = new StringBuilder();

            String morseCode = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ";
            String smallMorse = "abcdefghijklmnopqrstuvwxyz";
            String morseSymbols = "!\"&'()+,-./:=?@";
            String[] morse = {"----- ", ".---- ", "..--- ", "...-- ", "....- ", "..... ", "-.... ", "--... ", "---.. ", "----. ", ".- ", "-... ", "-.-. ", "-.. ", ". ", "..-. ", "--. ", ".... ", ".. ", ".--- ", "-.- ", ".-.. ", "-- ", "-. ", "--- ", ".--. ", "--.- ", ".-. ", "... ", "- ", "..- ", "...- ", ".-- ", "-..- ", "-.-- ", "--.. ", "-.-.-- ", ".-..-. ", ".-... ", ".----. ", "-.--. ", "-.--.- ", ".-.-. ", "--..-- ", "-....- ", ".-.-.- ", "-..-. ", "---... ", "-...- ", "..--.. ", ".--.-. "};
//            Pattern libraBarcodePattern = Pattern.compile(libraBarcode);
//            Matcher libraBarcodeMatcher = libraBarcodePattern.matcher(inputString);
            for (int i = 0; i < inputString.length(); i++) {

                if (morseCode.contains(Character.toString(inputString.charAt(i)))) {

                    if (inputString.charAt(i) == ' ') {
                        output.append("/ ");
                    } else if (48 <= (int) inputString.charAt(i) && (int) inputString.charAt(i) <= 57) {

                        output.append(morse[(int) inputString.charAt(i) - 48]); // +48

                    } else if (65 <= (int) inputString.charAt(i) && (int) inputString.charAt(i) <= 90) {
                        output.append(morse[(int) inputString.charAt(i) - 55]); //+55
                    }

                } else if (smallMorse.contains(Character.toString(inputString.charAt(i)))) {

                    output.append(morse[((int) inputString.charAt(i) - 32) - 55]);

                } else if (morseSymbols.contains(Character.toString(inputString.charAt(i)))) {

                    if ((33 <= (int) inputString.charAt(i) && (int) inputString.charAt(i) <= 34)) {
                        output.append(morse[(int) inputString.charAt(i) + 3]); // 36 - 37  //-3
                    } else if ((38 <= (int) inputString.charAt(i) && (int) inputString.charAt(i) <= 41)) {
                        output.append(morse[(int) inputString.charAt(i)]); // 38 - 41
                    } else if ((43 <= (int) inputString.charAt(i) && (int) inputString.charAt(i) <= 47)) {
                        output.append(morse[(int) inputString.charAt(i) - 1]); // 42 - 46  //+1
                    } else if ((int) inputString.charAt(i) == 58) {
                        output.append(morse[(int) inputString.charAt(i) - 11]); // 47  //+11
                    } else if ((int) inputString.charAt(i) == 61) {
                        output.append(morse[(int) inputString.charAt(i) - 13]); // 48  //+13
                    } else if ((63 <= (int) inputString.charAt(i) && (int) inputString.charAt(i) <= 64)) {
                        output.append(morse[(int) inputString.charAt(i) - 14]); // 49 - 50  //+14
                    }

                } else {

                    output.append(inputString.charAt(i));

                }

            }

            outputTextView.setText(output.toString());

//            if (libraBarcodeMatcher.matches()) {

//                Typeface libraBarcodeFontType = ResourcesCompat.getFont(getApplicationContext(), R.font.morse);
//                outputTextView.setTypeface(libraBarcodeFontType);
//                outputTextView.setText("");
//                outputTextView.setText("Test");
//
//            } else {
//
//                outputTextView.setText("");
//
//            }

        } else if (cipherFontNumber == 106) {

            StringBuilder output = new StringBuilder();

            String pigpen = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm ";
//            Pattern pigpenPattern = Pattern.compile(pigpen);
//            Matcher pigpenMatcher = pigpenPattern.matcher(inputString);

            Typeface pigpenFontType = ResourcesCompat.getFont(getApplicationContext(), R.font.pigpen_cipher);
            outputTextView.setTextSize(25f);
            outputTextView.setTypeface(pigpenFontType);
            for (int i = 0; i < inputString.length(); i++) {

                if (pigpen.contains(Character.toString(inputString.charAt(i)))) {

                    output.append(inputString.charAt(i));

                } else {

                    output.append(Character.toChars(128472));

                }
            }

            outputTextView.setText(output.toString());

        } else if (cipherFontNumber == 107) {

            StringBuilder output = new StringBuilder();

            String semaphore = "0123456789qwertyuiopasdfghjklzxcvbnm+? ";
//            Pattern semaphorePattern = Pattern.compile(semaphore);
//            Matcher semaphoreMatcher = semaphorePattern.matcher(inputString);


            Typeface semaphoreFontType = ResourcesCompat.getFont(getApplicationContext(), R.font.semaphore);
            outputTextView.setTextSize(25f);
            outputTextView.setTypeface(semaphoreFontType);
            for (int i = 0; i < inputString.length(); i++) {

                if (semaphore.contains(Character.toString(inputString.charAt(i)))) {

                    output.append(inputString.charAt(i));

                } else {

                    output.append(Character.toChars(128472));

                }
            }

            outputTextView.setText(output.toString());


        } else if (cipherFontNumber == 108) {

            StringBuilder output = new StringBuilder();

            String upsideDown = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm~`!@#\\$%\\^&*\\(\\)_+\"':;\\{}<>?,./ ";
//            Pattern upsideDownPattern = Pattern.compile(upsideDown);
//            Matcher upsideDownMatcher = upsideDownPattern.matcher(inputString);

            Typeface upsideDownFontType = ResourcesCompat.getFont(getApplicationContext(), R.font.upside_down);
            outputTextView.setTextSize(25f);
            outputTextView.setTypeface(upsideDownFontType);
            for (int i = 0; i < inputString.length(); i++) {

                if (upsideDown.contains(Character.toString(inputString.charAt(i)))) {

                    output.append(inputString.charAt(i));

                } else {

                    output.append(Character.toChars(128472));

                }
            }

            outputTextView.setText(output.toString());


        } else if (cipherFontNumber == 109) {

            int flag = 0;

            for (int i = 0; i < inputString.length(); i++) {
                if (!(inputString.charAt(i) <= 255 && inputString.charAt(i) >= 32)) {
                    flag = 1;
                    break;
                }
            }

            if (flag == 0) {

                StringBuilder webdingsRes = new StringBuilder();

                int[] webdingsUTF =
                        {32, 128375, 128376, 128370, 128374, 127942, 127894, 128391, 128488, 128489, 128496, 128497, 127798, 127895,
                                128638, 128636, 128469, 128470, 128471, 9204, 9205, 9206, 9207, 9194, 9193, 9198, 9197, 9208, 9209, 9210, 128474,
                                128499, 128736, 127959, 127960, 127961, 127962, 127964, 127981, 127963, 127968, 127958, 127965, 128739,
                                128269, 127956, 128065, 128066, 127966, 127957, 128740, 127967, 128755, 128364, 128363, 128360, 128264,
                                127892, 127893, 128492, 128637, 128493, 128490, 128491, 11156, 10004, 128690, 9633, 128737, 128230, 128753,
                                9632, 128657, 128712, 128745, 128752, 128968, 128372, 9899, 128741, 128660, 128472, 128473, 10067, 128754,
                                128647, 128653, 9971, 128711, 8854, 128685, 128494, 124, 128495, 128498, 128697, 128698, 128713, 128714,
                                128700, 128125, 127947, 9975, 127938, 127948, 127946, 127940, 127949, 127950, 128664, 128480, 128738, 128176,
                                127991, 128179, 128106, 128481, 128482, 128483, 10031, 128388, 128389, 128387, 128390, 128441, 128442, 128443,
                                128373, 128368, 128445, 128446, 128203, 128466, 128467, 128214, 128218, 128478, 128479, 128451, 128450, 128444,
                                127917, 127900, 127896, 127897, 127911, 128191, 127902, 128247, 127903, 127916, 128253, 128249, 128254, 128251,
                                127898, 127899, 128250, 128187, 128421, 128422, 128423, 128377, 127918, 128379, 128380, 128223, 128385, 128384,
                                128424, 128425, 128447, 128426, 128476, 128274, 128275, 128477, 128229, 128594, 128371, 127779, 127780, 127781,
                                127782, 9729, 127783, 127784, 127785, 127786, 127788, 127787, 127772, 127777, 128715, 128719, 127869, 127864,
                                128718, 128717, 9413, 9855, 128710, 128392, 127891, 128484, 128485, 128486, 128487, 128746, 128063, 128038,
                                128031, 128021, 128008, 128620, 128622, 128621, 128623, 128506, 127757, 127759, 127758, 128330};

                for (int i = 0; i < inputString.length(); i++) {
                    Toast.makeText(this, "" + (inputString.charAt(i) - 32), Toast.LENGTH_SHORT).show();
                    char[] c = Character.toChars(webdingsUTF[(inputString.charAt(i) - 32)]);
                    webdingsRes.append(c);
                }

                outputTextView.setTextSize(25f);
                outputTextView.setText(webdingsRes.toString());

            } else {

                outputTextView.setText("");

            }

        }

    }

    protected void applyFontDecodeCipher(String inputString, int cipherFontNumber) {

        if (cipherFontNumber == 105) {

            String[] morse = {"----- ", ".---- ", "..--- ", "...-- ", "....- ", "..... ", "-.... ", "--... ", "---.. ", "----. ", ".- ", "-... ", "-.-. ", "-.. ", ". ", "..-. ", "--. ", ".... ", ".. ", ".--- ", "-.- ", ".-.. ", "-- ", "-. ", "--- ", ".--. ", "--.- ", ".-. ", "... ", "- ", "..- ", "...- ", ".-- ", "-..- ", "-.-- ", "--.. ", "-.-.-- ", ".-..-. ", ".-... ", ".----. ", "-.--. ", "-.--.- ", ".-.-. ", "--..-- ", "-....- ", ".-.-.- ", "-..-. ", "---... ", "-...- ", "..--.. ", ".--.-. "};
            StringBuilder encode = new StringBuilder();
            StringBuilder str = new StringBuilder();

            if ((inputString.charAt(inputString.length() - 1) == '.') || (inputString.charAt(inputString.length() - 1) == '-') || inputString.charAt(inputString.length() - 1) == '/' || inputString.charAt(inputString.length() - 1) == ' ') {
                inputString = inputString + " ";
            }

            for (int i = 0; i < inputString.length(); i++) {

                if (inputString.length() > 0) {

                    if (inputString.charAt(i) == '.' || inputString.charAt(i) == '-' || inputString.charAt(i) == ' ') {

                        if (inputString.charAt(i) != ' ') {

                            str.append(inputString.charAt(i));

                        } else if (inputString.charAt(i) == ' ') {

                            str.append(inputString.charAt(i));

                            int ind = arrLinStrSearch(morse, str.toString());

                            if (ind != -1) {

                                if (0 <= ind && ind <= 9) {

                                    encode.append((char) (ind + 48));
                                } else if (10 <= ind && ind <= 35) {

                                    encode.append((char) (ind + 55));
                                } else if (36 <= ind && ind <= 37) {

                                    encode.append((char) (ind) - 3);
                                } else if (38 <= ind && ind <= 41) {

                                    encode.append((char) (ind));
                                } else if (42 <= ind && ind <= 46) {

                                    encode.append((char) (ind + 1));
                                } else if (ind == 47) {

                                    encode.append((char) (ind + 11));
                                } else if (ind == 48) {

                                    encode.append((char) (ind + 13));
                                } else if (49 <= ind && ind <= 50) {

                                    encode.append((char) (ind + 14));
                                }

                                str.delete(0, str.toString().length());

                            } else {
                                encode.delete(0, encode.toString().length());
                                encode.append("");
                                break;
                            }

                        }

                    } else if (inputString.charAt(i) == '/' && inputString.charAt(i + 1) == ' ') {
                        encode.append(" ");
                        i++;
                    } else {

                        encode.append(inputString.charAt(i));
                    }
                } else {

                    encode.append("");
                }

            }

            outputTextView.setText(encode.toString());

        } else if (cipherFontNumber == 109) {

            StringBuilder str = new StringBuilder();
            StringBuilder encode = new StringBuilder();
            int codePoint = 0;

            int[] webdingsUTF =
                    {32, 128375, 128376, 128370, 128374, 127942, 127894, 128391, 128488, 128489, 128496, 128497, 127798, 127895,
                            128638, 128636, 128469, 128470, 128471, 9204, 9205, 9206, 9207, 9194, 9193, 9198, 9197, 9208, 9209, 9210, 128474,
                            128499, 128736, 127959, 127960, 127961, 127962, 127964, 127981, 127963, 127968, 127958, 127965, 128739,
                            128269, 127956, 128065, 128066, 127966, 127957, 128740, 127967, 128755, 128364, 128363, 128360, 128264,
                            127892, 127893, 128492, 128637, 128493, 128490, 128491, 11156, 10004, 128690, 9633, 128737, 128230, 128753,
                            9632, 128657, 128712, 128745, 128752, 128968, 128372, 9899, 128741, 128660, 128472, 128473, 10067, 128754,
                            128647, 128653, 9971, 128711, 8854, 128685, 128494, 124, 128495, 128498, 128697, 128698, 128713, 128714,
                            128700, 128125, 127947, 9975, 127938, 127948, 127946, 127940, 127949, 127950, 128664, 128480, 128738, 128176,
                            127991, 128179, 128106, 128481, 128482, 128483, 10031, 128388, 128389, 128387, 128390, 128441, 128442, 128443,
                            128373, 128368, 128445, 128446, 128203, 128466, 128467, 128214, 128218, 128478, 128479, 128451, 128450, 128444,
                            127917, 127900, 127896, 127897, 127911, 128191, 127902, 128247, 127903, 127916, 128253, 128249, 128254, 128251,
                            127898, 127899, 128250, 128187, 128421, 128422, 128423, 128377, 127918, 128379, 128380, 128223, 128385, 128384,
                            128424, 128425, 128447, 128426, 128476, 128274, 128275, 128477, 128229, 128594, 128371, 127779, 127780, 127781,
                            127782, 9729, 127783, 127784, 127785, 127786, 127788, 127787, 127772, 127777, 128715, 128719, 127869, 127864,
                            128718, 128717, 9413, 9855, 128710, 128392, 127891, 128484, 128485, 128486, 128487, 128746, 128063, 128038,
                            128031, 128021, 128008, 128620, 128622, 128621, 128623, 128506, 127757, 127759, 127758, 128330};

            for (int i = 0; i < inputString.length(); i++){
                str.append((int)inputString.charAt(i));
                str.append(" ");
            }

            for (int i = 0; i < inputString.length(); i++) {

                codePoint = Character.codePointAt(inputString, i);
                i += Character.charCount(codePoint) - 1;
                int index = arrLinIntSearch(webdingsUTF, codePoint);
                encode.append((char) (index + 32));
                //encode.append(" ");

            }

//            for (int i = 0; i < inputString.length(); i++) {
////
////                Toast.makeText(this, "" + (int) inputString.charAt(i), Toast.LENGTH_SHORT).show();
////
////                int index = arrLinIntSearch(webdingsUTF, inputString.charAt(i));
////
////                encode.append((char) (index + 32));
////
////            }
            outputTextView.setText(encode.toString());

        }

    }

    protected int arrLinStrSearch(String[] arr, String strToFind) {
        int flag = 0;
        int i;
        for (i = 0; i < arr.length; i++) {
            if (arr[i].equals(strToFind)) {
                flag = 1;
                break;
            }
        }
        if (flag == 1) {
            return i;
        } else {
            return -1;
        }
    }

    protected int arrLinIntSearch(int[] arr, int numToFind) {
        int flag = 0;
        int i;
        for (i = 0; i < arr.length; i++) {
            if (arr[i] == numToFind) {
                flag = 1;
                break;
            }
        }
        if (flag == 1) {
            return i;
        } else {
            return -1;
        }
    }

//    protected int arrBinSearch(int[] arr, int l, int r, int key) {
//
//        if (r >= l) {
//
//            int mid = l + (r - l) / 2;
//            int midNum = arr[mid];
//
//            if (midNum == key) {
//                return mid;
//            } else if (key < midNum) {
//                return arrBinSearch(arr, l, mid - 1, key);
//            } else {
//                return arrBinSearch(arr, mid + 1, r, key);
//            }
//        } else {
//            return -1;
//        }
//
//    }

    protected String cipherTextProcedure(String inputString, int cipherNumber, int firstCipherNumber, int secondCipherNumber, int DEncodeStatus, int cipherTransformation) {

        String result = null;

        if (DEncodeStatus == 1) {

            if (firstCipherNumber == 0 && secondCipherNumber == 0) {
                result = applyEncodeCipher(cipherNumber, inputString);
            }
            if (firstCipherNumber > 0) {
                result = applyEncodeCipher(firstCipherNumber, inputString);
            }
            if (secondCipherNumber > 0) {
                result = applyEncodeCipher(secondCipherNumber, inputString);
            }
            if (cipherTransformation == 1) {
                result = applyEncodeCipher(secondCipherNumber, applyEncodeCipher(firstCipherNumber, inputString));
            }

        } else if (DEncodeStatus == 2) {

            if (firstCipherNumber == 0 && secondCipherNumber == 0) {
                result = applyDecodeCipher(cipherNumber, inputString);
            }

            if (firstCipherNumber > 0) {
                result = applyDecodeCipher(firstCipherNumber, inputString);
            }
            if (secondCipherNumber > 0) {
                result = applyDecodeCipher(secondCipherNumber, inputString);
            }
            if (cipherTransformation == 1) {
                result = applyDecodeCipher(secondCipherNumber, applyDecodeCipher(firstCipherNumber, inputString));
            }

        }

        return result;
    }

    private String applyEncodeCipher(int cipherNumber, String inputString) {
        String encodeResult = null;

        if (cipherNumber == 1) {
            encodeResult = ascii.encodeProcedureASCII(inputString);
        } else if (cipherNumber == 2) {
            encodeResult = adfgvx.encodeProcedureADFGVX(inputString, matrix36CharPat, keyText);
        } else if (cipherNumber == 3) {
            encodeResult = affine.encodeProcedureAFFINE(inputString, INTKEYA, INTKEYB);
        } else if (cipherNumber == 4) {
            encodeResult = atbash.encodeProcedureATBASH(inputString);
        } else if (cipherNumber == 5) {
            encodeResult = autokey.encodeProcedureAUTOKEY(inputString, keyText);
        } else if (cipherNumber == 6) {
            encodeResult = bin.encodeProcedureBIN(inputString);
        } else if (cipherNumber == 7) {
            encodeResult = beaufort.encodeProcedureBEAUFORT(inputString, keyText);
        } else if (cipherNumber == 8) {
            encodeResult = base64.encodeProcedureBASE64(inputString);
        } else if (cipherNumber == 9) {
            encodeResult = caeser.encodeProcedureCASESER(inputString, OFFSETINT);
        } else if (cipherNumber == 10) {
            encodeResult = hex.encodeProcedureHEX(inputString);
        } else if (cipherNumber == 11) {
            encodeResult = octal.encodeProcedureOCTAL(inputString);
        } else if (cipherNumber == 12) {
            encodeResult = porta.encodeDecodeProcedurePORTA(inputString, keyText);
        } else if (cipherNumber == 13) {
            encodeResult = railfence.encodeProcedureRAILFENCE(inputString, INTKEYSINGLE);
        } else if (cipherNumber == 14) {
            encodeResult = rot13.encodeDecodeProcedureROT13(inputString);
        } else if (cipherNumber == 15) {
            encodeResult = vigenere.encodeProcedureVIGENERE(inputString, keyText);
        }

        return encodeResult;
    }

    private String applyDecodeCipher(int cipherNumber, String inputString) {
        String decodeResult = null;

        if (cipherNumber == 1) {
            decodeResult = ascii.decodeProcedureASCII(inputString + " ");
        } else if (cipherNumber == 2) {
            decodeResult = adfgvx.decodeProcedureADFGVX(inputString, matrix36CharPat, keyText);
        } else if (cipherNumber == 3) {
            decodeResult = affine.decodeProcedureAFFINE(inputString, INTKEYA, INTKEYB);
        } else if (cipherNumber == 4) {
            decodeResult = atbash.decodeProcedureATBASH(inputString);
        } else if (cipherNumber == 5) {
            decodeResult = autokey.decodeProcedureAUTOKEY(inputString, keyText);
        } else if (cipherNumber == 6) {
            decodeResult = bin.decodeProcedureBIN(inputString + " ");
        } else if (cipherNumber == 7) {
            decodeResult = beaufort.decodeProcedureBEAUFORT(inputString, keyText);
        } else if (cipherNumber == 8) {
            decodeResult = base64.decodeProcedureBASE64(inputString);
        } else if (cipherNumber == 9) {
            decodeResult = caeser.decodeProcedureCASESER(inputString, OFFSETINT);
        } else if (cipherNumber == 10) {
            decodeResult = hex.decodeProcedureHEX(inputString + " ");
        } else if (cipherNumber == 11) {
            decodeResult = octal.decodeProcedureOCTAL(inputString + " ");
        } else if (cipherNumber == 12) {
            decodeResult = porta.encodeDecodeProcedurePORTA(inputString, keyText);
        } else if (cipherNumber == 13) {
            decodeResult = railfence.decodeProcedureRAILFENCE(inputString, INTKEYSINGLE);
        } else if (cipherNumber == 14) {
            decodeResult = rot13.encodeDecodeProcedureROT13(inputString);
        } else if (cipherNumber == 15) {
            decodeResult = vigenere.decodeProcedureVIGENERE(inputString, keyText);
        }

        return decodeResult;

    }

    private class AsyncBgTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog;

        public AsyncBgTask() {
            dialog = new ProgressDialog(CodedResultActivity.this);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Doing Something...");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected String doInBackground(Void... voids) {

            outputText = cipherTextProcedure(cipherOutput.trim(), cipherNumber, firstCipherNumber, secondCipherNumber, DEncodeStatus, twoCipherTransformation);

            if (outputText.length() <= 1000) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (outputText.length() >= 1000 && outputText.length() <= 10000) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (outputText.length() >= 10000 && outputText.length() <= 100000) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (100000 <= outputText.length() && outputText.length() <= 400000) {
                try {
                    Thread.sleep(12000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (400000 <= outputText.length() && outputText.length() <= 1000000) {
                try {
                    Thread.sleep(16000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return outputText;
        }

        @Override
        protected void onPostExecute(String string) {

//            Toast.makeText(getApplicationContext(), ""+cipherNumber+" "+firstCipherNumber+" "+secondCipherNumber, Toast.LENGTH_SHORT).show();
            outputTextView.setText(outputText);
            outputCharacterNumber.setText("Output Length: " + String.valueOf(outputTextView.getText().toString().length()));

            if (outputText.equals("")) {
                firstPlusBtn.setVisibility(View.GONE);
                secondPlusBtn.setVisibility(View.GONE);
            }

            if (cipherNumber > 0 && firstCipherNumber == 0 && secondCipherNumber == 0) {
                firstOutputString = outputText;
            }
            if ((firstCipherNumber > 0 && secondCipherNumber == 0) || (firstCipherNumber == 0 && secondCipherNumber > 0)) {
                secondOutputString = outputText;
            }
//            if (firstCipherNumber > 0 && secondCipherNumber > 0) {
//                thirdOutputString = outputText;
//            }

            //Toast.makeText(CodedResultActivity.this, ""+secondOutputString + thirdOutputString, Toast.LENGTH_SHORT).show();

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

        }

    }

}