package com.example.gopontext;

import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gopontext.controller.cipherPagerAdapter;
import com.example.gopontext.views.cipherTextFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

//    implements NavigationView.OnNavigationItemSelectedListener

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton navigationDrawerIcon;
    private ImageButton deleteAllInputText, copyAllInputText, pasteAllInputText, shareAllInputText;
    private EditText plainText;
    private TextView inputCharNumber;
    private ClipboardManager clipboardManager;
    private RadioGroup dEncodeRadioGroup;
    private RadioButton encode, decode;
    protected int DEncodeStatus, encodeOrDecode = 1;

//    private TextView cipherText;
//    protected String st;
//    protected char[] ch;
//    protected long[] integerResults;
//    protected String resultString;

    @SuppressLint("Range")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        deleteAllInputText = findViewById(R.id.scanCodeInputDeleteRoundButton);
        copyAllInputText = findViewById(R.id.scanCodeInputCopyRoundButton);
        pasteAllInputText = findViewById(R.id.scanCodeInputPasteRoundButton);
        shareAllInputText = findViewById(R.id.scanCodeOutputShareRoundButton);

        drawerLayout = findViewById(R.id.nav_drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationDrawerIcon = findViewById(R.id.navigationDrawerButton);

//        viewPager = findViewById(R.id.cipherMethodViewPager);
//        TabLayout tabs = findViewById(R.id.cipherTabLayout);

        plainText = findViewById(R.id.scanCodeInputEditText);

        dEncodeRadioGroup = findViewById(R.id.encodeDecodeRadioGroup);
        encode = findViewById(R.id.encodeRadioButton);
        decode = findViewById(R.id.decodeRadioButton);

        encode.setChecked(true);
        plainText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1000)});

        //Toast.makeText(this, ""+dEncodeRadioGroup.getCheckedRadioButtonId(), Toast.LENGTH_SHORT).show();


//        encode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                DEncodeStatus = 1;
//                //Toast.makeText(MainActivity.this, "Encode is Checked"+DEncodeStatus, Toast.LENGTH_SHORT).show();
//            }
//        });

//        decode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                DEncodeStatus = 2;
//                //Toast.makeText(MainActivity.this, "Decode is Checked"+DEncodeStatus, Toast.LENGTH_SHORT).show();
//            }
//        })

        navigationDrawer();

//        viewPager.setAdapter(new cipherPagerAdapter(getSupportFragmentManager(), 3));

//        tabs.setupWithViewPager(viewPager);
//
//        tabs.setTabTextColors(getResources().getColor(R.color.fourthThemeColor), Color.WHITE);

        inputCharNumber = findViewById(R.id.outputCharacterNumber);
        inputCharNumber.setText(plainText.getText().length() + " / 1000");

        View.OnClickListener encodeOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encodeOrDecode = 1;
                plainText.setFilters(new InputFilter[]{
                        new InputFilter.LengthFilter(1000)
                });

                inputCharNumber.setText(plainText.getText().length() + " / 1000");
                if (plainText.getText().toString().length() >= 1000) {
                    pasteAllInputText.setEnabled(false);
                    pasteAllInputText.setAlpha(0.5f);
                    if (plainText.getText().toString().length() > 1000) {
                        inputCharNumber.setText(plainText.getText().length() + " / 1000");
                        inputCharNumber.setTextColor(Color.RED);
                        inputCharNumber.setTypeface(null, Typeface.BOLD_ITALIC);
                        inputCharNumber.setTextSize(18f);
                    }
                }

            }
        };

        View.OnClickListener decodeOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encodeOrDecode = 2;
                plainText.setFilters(new InputFilter[]{
                        new InputFilter.LengthFilter(10000)
                });
                inputCharNumber.setText(plainText.getText().length() + " / 10000");
                if (pasteAllInputText.getAlpha() == 0.5f && !pasteAllInputText.isEnabled()) {
                    if (plainText.getText().toString().length() <= 10000) {
                        pasteAllInputText.setEnabled(true);
                        pasteAllInputText.setAlpha(1.0f);
                    }
                }
                if (plainText.getText().toString().length() <= 10000) {
                    inputCharNumber.setTextColor(getResources().getColor(R.color.secondaryThemeColor));
                    inputCharNumber.setTypeface(null, Typeface.ITALIC);
                    inputCharNumber.setTextSize(15f);
                }


            }
        };

        encode.setOnClickListener(encodeOnClick);

        decode.setOnClickListener(decodeOnClick);

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        if (plainText.getText().length() != 0) {
            deleteAllInputText.setEnabled(true);
            deleteAllInputText.setAlpha(1.0f);
            deleteAllInputText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plainText.setText("");
                }
            });
        }
        deleteAllInputText.setEnabled(false);
        deleteAllInputText.setAlpha(0.5f);

        if (plainText.getText().length() == 0) {
            copyAllInputText.setEnabled(false);
            copyAllInputText.setAlpha(0.5f);
        } else {
            copyAllInputText.setEnabled(true);
            copyAllInputText.setAlpha(1.0f);
            copyAllInputText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData inpClipData = ClipData.newPlainText("InputText", plainText.getText().toString().trim());
                    clipboardManager.setPrimaryClip(inpClipData);
                    Toast.makeText(MainActivity.this, "Input Text is Copied!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (plainText.getText().length() == 0) {
            shareAllInputText.setEnabled(false);
            shareAllInputText.setAlpha(0.5f);
        } else {
            shareAllInputText.setEnabled(true);
            shareAllInputText.setAlpha(1.0f);
            shareAllInputText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mainText = plainText.getText().toString();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Use CryptoCode to Encode this...");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Use CryptoCode to Encode this...\n \"" + mainText + "\"");
                    startActivity(Intent.createChooser(shareIntent, "Share Via"));
                }
            });
        }

//        if (encodeOrDecode == 1) {
//            if (plainText.getText().toString().length() < 1000) {
//                pasteAllInputText.setEnabled(true);
//                pasteAllInputText.setAlpha(1.0f);
//                pasteAllInputText.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ClipData.Item pasteItem = clipboardManager.getPrimaryClip().getItemAt(0);
//                        String dataToPaste = pasteItem.getText().toString();
//                        if (!dataToPaste.isEmpty()) {
//                            int currentCursorPosition = plainText.getSelectionStart();
//                            String prevString = plainText.getText().toString();
//                            String cursorToSetAfterPaste;
//                            Toast.makeText(MainActivity.this, "Clipboard data length: "+dataToPaste.length(), Toast.LENGTH_SHORT).show();
//                            if (dataToPaste.length() > (1000 - plainText.getText().toString().length())){
//                                plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(1000 - plainText.getText().toString().length())) + prevString.substring(currentCursorPosition));
//                                cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(1000 - plainText.getText().toString().length()));
//                            }else{
//                                plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste + prevString.substring(currentCursorPosition));
//                                cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste;
//                            }
//                            Toast.makeText(MainActivity.this, "Before Error: "+cursorToSetAfterPaste.length(), Toast.LENGTH_SHORT).show();
//                            plainText.setSelection(cursorToSetAfterPaste.length());
//                        } else {
//                            Toast.makeText(MainActivity.this, "Nothing To Paste!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            } else {
//                pasteAllInputText.setEnabled(false);
//                pasteAllInputText.setAlpha(0.5f);
//            }
//        }
//
//        else if (encodeOrDecode == 2) {
//            if (plainText.getText().toString().length() < 10000) {
//                pasteAllInputText.setEnabled(true);
//                pasteAllInputText.setAlpha(1.0f);
//                pasteAllInputText.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ClipData.Item pasteItem = clipboardManager.getPrimaryClip().getItemAt(0);
//                        String dataToPaste = pasteItem.getText().toString();
//                        if (!dataToPaste.isEmpty()) {
//                            int currentCursorPosition = plainText.getSelectionStart();
//                            String prevString = plainText.getText().toString();
//                            String cursorToSetAfterPaste;;
//                            Toast.makeText(MainActivity.this, "Clipboard data length: "+dataToPaste.length(), Toast.LENGTH_SHORT).show();
//                            if (dataToPaste.length() > (10000 - plainText.getText().toString().length())){
//
//                                plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(10000 - plainText.getText().toString().length())) + prevString.substring(currentCursorPosition));
//                                cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(10000 - plainText.getText().toString().length()));
//                            }else{
//                                plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste + prevString.substring(currentCursorPosition));
//                                cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste;
//                            }
//                            Toast.makeText(MainActivity.this, "Before Error: "+cursorToSetAfterPaste.length(), Toast.LENGTH_SHORT).show();
//                            plainText.setSelection(cursorToSetAfterPaste.length());
//                        } else {
//                            Toast.makeText(MainActivity.this, "Nothing To Paste!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            } else {
//                pasteAllInputText.setEnabled(false);
//                pasteAllInputText.setAlpha(0.5f);
//            }
//        }

        pasteAllInputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (encodeOrDecode == 1){
//                    Toast.makeText(MainActivity.this, "encode", Toast.LENGTH_SHORT).show();
                    if (plainText.getText().toString().length() < 1000) {
                        pasteAllInputText.setEnabled(true);
                        pasteAllInputText.setAlpha(1.0f);

                        ClipData.Item pasteItem = clipboardManager.getPrimaryClip().getItemAt(0);
                        String dataToPaste = pasteItem.getText().toString();

                        if (!dataToPaste.isEmpty()) {
                            int currentCursorPosition = plainText.getSelectionStart();
                            String prevString = plainText.getText().toString();
                            String cursorToSetAfterPaste;

                            if (dataToPaste.length() > (1000 - plainText.getText().toString().length())){
                                plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(1000 - plainText.getText().toString().length())) + prevString.substring(currentCursorPosition));
                                cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(1000 - plainText.getText().toString().length()));
                            }else{
                                plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste + prevString.substring(currentCursorPosition));
                                cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste;
                            }

                            plainText.setSelection(cursorToSetAfterPaste.length());
                        } else {
                            Toast.makeText(MainActivity.this, "Nothing To Paste!", Toast.LENGTH_SHORT).show();
                        }


                    }else{
                        pasteAllInputText.setEnabled(false);
                        pasteAllInputText.setAlpha(0.5f);
                    }
                }
                else{

                    if (plainText.getText().toString().length() < 10000) {

                        pasteAllInputText.setEnabled(true);
                        pasteAllInputText.setAlpha(1.0f);
                        ClipData.Item pasteItem = clipboardManager.getPrimaryClip().getItemAt(0);
                        String dataToPaste = pasteItem.getText().toString();
                        if (!dataToPaste.isEmpty()) {
                            int currentCursorPosition = plainText.getSelectionStart();
                            String prevString = plainText.getText().toString();
                            String cursorToSetAfterPaste;;

                            if (dataToPaste.length() > (10000 - plainText.getText().toString().length())){

                                plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(10000 - plainText.getText().toString().length())) + prevString.substring(currentCursorPosition));
                                cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(10000 - plainText.getText().toString().length()));
                            }else{
                                plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste + prevString.substring(currentCursorPosition));
                                cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste;
                            }

                            plainText.setSelection(cursorToSetAfterPaste.length());
                        } else {
                            Toast.makeText(MainActivity.this, "Nothing To Paste!", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        pasteAllInputText.setEnabled(false);
                        pasteAllInputText.setAlpha(0.5f);
                    }

                }
            }
        });

        plainText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                //inputCharNumber.setText(s.length() + "/ 1000");

            }

            //
            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                if (encode.isChecked()) {
                    inputCharNumber.setText(s.length() + " / 1000");
                    if (plainText.getText().toString().length() <= 1000) {
                        inputCharNumber.setTextColor(getResources().getColor(R.color.secondaryThemeColor));
                        inputCharNumber.setTypeface(null, Typeface.ITALIC);
                        inputCharNumber.setTextSize(15f);
                    } else {
                        inputCharNumber.setTextColor(Color.RED);
                        inputCharNumber.setTypeface(null, Typeface.BOLD_ITALIC);
                        inputCharNumber.setTextSize(18f);
                    }
                } else if (decode.isChecked()) {
                    inputCharNumber.setText(s.length() + " / 10000");
                }


//                deleteAllInputText.setAlpha(1.0f);
                if (plainText.getText().length() > 0) {
                    deleteAllInputText.setEnabled(true);
                    deleteAllInputText.setAlpha(1.0f);
                    deleteAllInputText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            plainText.setText("");
                        }
                    });
                } else {
                    deleteAllInputText.setEnabled(false);
                    deleteAllInputText.setAlpha(0.5f);
                }

                if (plainText.getText().length() > 0) {
                    copyAllInputText.setEnabled(true);
                    copyAllInputText.setAlpha(1.0f);
                    copyAllInputText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipData clipData = ClipData.newPlainText("text", plainText.getText().toString().trim());
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(MainActivity.this, "Input Text is Copied!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    copyAllInputText.setEnabled(false);
                    copyAllInputText.setAlpha(0.5f);
                }

                if (plainText.getText().length() > 0) {
                    shareAllInputText.setEnabled(true);
                    shareAllInputText.setAlpha(1.0f);
                    shareAllInputText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String mainText = plainText.getText().toString();
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Use CryptoCode to Encode this...");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, "Use CryptoCode to Encode this...\n \"" + mainText + "\"");
                            startActivity(Intent.createChooser(shareIntent, "Share Via"));
                        }
                    });
                } else {
                    shareAllInputText.setEnabled(false);
                    shareAllInputText.setAlpha(0.5f);
                }

//                if (encodeOrDecode == 1) {
//                    if (plainText.getText().toString().length() < 1000) {
//                        pasteAllInputText.setEnabled(true);
//                        pasteAllInputText.setAlpha(1.0f);
//                        pasteAllInputText.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ClipData.Item pasteItem = clipboardManager.getPrimaryClip().getItemAt(0);
//                                String dataToPaste = pasteItem.getText().toString();
//                                if (!dataToPaste.isEmpty()) {
//                                    int currentCursorPosition = plainText.getSelectionStart();
//                                    String prevString = plainText.getText().toString();
//                                    String cursorToSetAfterPaste;
//                                    Toast.makeText(MainActivity.this, "Clipboard data length (Encode): "+dataToPaste.length(), Toast.LENGTH_SHORT).show();
//                                    if (dataToPaste.length() > (1000 - plainText.getText().toString().length())){
//                                        plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0, (1000 - plainText.getText().toString().length())) + prevString.substring(currentCursorPosition));
//                                        cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(1000 - plainText.getText().toString().length()));
//                                    }else{
//                                        plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste + prevString.substring(currentCursorPosition));
//                                        cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste;
//                                    }
//                                    Toast.makeText(MainActivity.this, "Before Error Encode: "+cursorToSetAfterPaste.length(), Toast.LENGTH_SHORT).show();
//                                    plainText.setSelection(cursorToSetAfterPaste.length());
//                                } else {
//                                    Toast.makeText(MainActivity.this, "Nothing To Paste!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    } else {
//                        pasteAllInputText.setEnabled(false);
//                        pasteAllInputText.setAlpha(0.5f);
//                    }
//                }
//
//                else if (encodeOrDecode == 2) {
//                    if (plainText.getText().toString().length() < 10000) {
//                        pasteAllInputText.setEnabled(true);
//                        pasteAllInputText.setAlpha(1.0f);
//                        pasteAllInputText.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ClipData.Item pasteItem = clipboardManager.getPrimaryClip().getItemAt(0);
//                                String dataToPaste = pasteItem.getText().toString();
//                                if (!dataToPaste.isEmpty()) {
//                                    int currentCursorPosition = plainText.getSelectionStart();
//                                    String prevString = plainText.getText().toString();
//                                    String cursorToSetAfterPaste;
//                                    Toast.makeText(MainActivity.this, "Clipboard data length (Decode): "+dataToPaste.length(), Toast.LENGTH_SHORT).show();
//                                    if (dataToPaste.length() > (10000 - plainText.getText().toString().length())){
//                                        plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(10000 - plainText.getText().toString().length())) + prevString.substring(currentCursorPosition));
//                                        cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(10000 - plainText.getText().toString().length()));
//                                    }else{
//                                        plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste + prevString.substring(currentCursorPosition));
//                                        cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste;
//                                    }
//                                    Toast.makeText(MainActivity.this, "Before Error Decode: "+cursorToSetAfterPaste.length(), Toast.LENGTH_SHORT).show();
//                                    plainText.setSelection(cursorToSetAfterPaste.length());
//
//                                } else {
//                                    Toast.makeText(MainActivity.this, "Nothing To Paste!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    } else {
//                        pasteAllInputText.setEnabled(false);
//                        pasteAllInputText.setAlpha(0.5f);
//                    }
//                }

                if (plainText.getText().toString().length() == 0){
                    pasteAllInputText.setEnabled(true);
                    pasteAllInputText.setAlpha(1.0f);
                }

                pasteAllInputText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (encodeOrDecode == 1){

//                    Toast.makeText(MainActivity.this, "encode", Toast.LENGTH_SHORT).show();
                            if (plainText.getText().toString().length() < 1000) {

                                ClipData.Item pasteItem = clipboardManager.getPrimaryClip().getItemAt(0);
                                String dataToPaste = pasteItem.getText().toString();

                                if (!dataToPaste.isEmpty()) {
                                    int currentCursorPosition = plainText.getSelectionStart();
                                    String prevString = plainText.getText().toString();
                                    String cursorToSetAfterPaste;

                                    if (dataToPaste.length() > (1000 - plainText.getText().toString().length())){
                                        plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(1000 - plainText.getText().toString().length())) + prevString.substring(currentCursorPosition));
                                        cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0,(1000 - plainText.getText().toString().length()));
                                    }else{
                                        plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste + prevString.substring(currentCursorPosition));
                                        cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste;
                                    }

                                    plainText.setSelection(cursorToSetAfterPaste.length());
                                } else {
                                    Toast.makeText(MainActivity.this, "Nothing To Paste!", Toast.LENGTH_SHORT).show();
                                }

                            }

                            if (plainText.getText().toString().length() < 1000){
                                pasteAllInputText.setEnabled(true);
                                pasteAllInputText.setAlpha(1.0f);
                            }else{
                                pasteAllInputText.setEnabled(false);
                                pasteAllInputText.setAlpha(0.5f);
                            }

                        } else{

                            if (plainText.getText().toString().length() < 10000) {

                                ClipData.Item pasteItem = clipboardManager.getPrimaryClip().getItemAt(0);
                                String dataToPaste = pasteItem.getText().toString();
                                if (!dataToPaste.isEmpty()) {
                                    int currentCursorPosition = plainText.getSelectionStart();
                                    String prevString = plainText.getText().toString();
                                    String cursorToSetAfterPaste;

                                    if (dataToPaste.length() > (10000 - plainText.getText().toString().length())){

                                        plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0, (10000 - plainText.getText().toString().length())) + prevString.substring(currentCursorPosition));
                                        cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste.substring(0, (10000 - plainText.getText().toString().length()));
                                    }else{
                                        plainText.setText(prevString.substring(0, currentCursorPosition) + dataToPaste + prevString.substring(currentCursorPosition));
                                        cursorToSetAfterPaste = prevString.substring(0, currentCursorPosition) + dataToPaste;
                                    }

                                    plainText.setSelection(cursorToSetAfterPaste.length());
                                } else {
                                    Toast.makeText(MainActivity.this, "Nothing To Paste!", Toast.LENGTH_SHORT).show();
                                }

                            }

                            if (plainText.getText().toString().length() < 10000){
                                pasteAllInputText.setEnabled(true);
                                pasteAllInputText.setAlpha(1.0f);
                            }else{
                                pasteAllInputText.setEnabled(false);
                                pasteAllInputText.setAlpha(0.5f);
                            }

                        }
                    }
                });

//
//                ch = new char[s.length()];
//
//                for (int i = 0; i < s.length(); i++){
//                    ch[i] = s.charAt(i);
//                }
//
//                integerResults = new long[s.length()];
//
//                for (int i = 0; i < s.length(); i++){
//                    integerResults[i] =  ch[i];
//                }
//
//                String[] strArray = new String[s.length()];
//
//                for (int i = 0; i < s.length(); i++){
//                    strArray[i] = String.valueOf(integerResults[i]);
//                }
//
//                resultString = Arrays.toString(strArray).replaceAll(","," ").replace("[","").replace("]","");
//
//                cipherText.setText(resultString);


//                if(Character.toString(s.charAt(count-1)).equals(" ")){
//                    cipherText.setText(" ");
//                }else{
//                    cipherText.setText("");
//                    cipherText.setText(Character.toString(s.charAt(count-1)));
//                }
//               st = Character.toString(s.charAt(count-1));
//                Log.d("start:",""+start);
//                Log.d("before:",""+before);
//                Log.d("count:",""+count);
//                Log.d("CharSequence",""+s);
//                cipherText.setText(st);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        useCipherTextFragment(new cipherTextFragment());

    }

    private void useCipherTextFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.cipherTextFragContainer, fragment);
        fragmentTransaction.commit();

    }


    private void navigationDrawer() {
        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(this);

        navigationDrawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_explore:
                Intent exploreIntent = new Intent(MainActivity.this, ExploreActivity.class);
                startActivity(exploreIntent);
                break;
            case R.id.nav_scancode:
                Intent scanCodeIntent = new Intent(MainActivity.this, ScanCodeActivity.class);
                startActivity(scanCodeIntent);
                break;
            case R.id.nav_filecode:
                break;
            case R.id.nav_enigma:
                break;
            case R.id.nav_lorenz:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_share_app:
                break;
            case R.id.nav_rate_us:
                break;
            case R.id.nav_privacy_policy:
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getInputText() {
        String retStr = null;
        if (encode.isChecked()){
            if (plainText.getText().toString().length() > 1000){
                retStr = plainText.getText().toString().substring(0,1000).trim();
            }else{
                retStr = plainText.getText().toString().trim();
            }
        }else if(decode.isChecked()){
            if (plainText.getText().toString().length() > 10000){
                retStr = plainText.getText().toString().substring(0,10000).trim();
            }else{
                retStr = plainText.getText().toString().trim();
            }
        }
        return retStr;
    }

    public int getDEncodeStatus() {

        if (encode.isChecked()) {
            DEncodeStatus = 1;
            //decode.setChecked(false);
        } else if (decode.isChecked()) {
            DEncodeStatus = 2;
            //encode.setChecked(false);
        }
        //Toast.makeText(this, ""+DEncodeStatus, Toast.LENGTH_SHORT).show();
        return DEncodeStatus;
    }

    public EditText getMainInputEditText() {

        return plainText;

    }

    public RadioGroup getMainRadioGroup(){
        return dEncodeRadioGroup;
    }

    public RadioButton getMainEncodeRadioButton(){
        return encode;
    }

    public RadioButton getMainDecodeRadioButton(){
        return decode;
    }


//    public void setCurrentItem(int item, boolean smoothScroll) {
//        viewPager.setCurrentItem(item, smoothScroll);
//    }

}
