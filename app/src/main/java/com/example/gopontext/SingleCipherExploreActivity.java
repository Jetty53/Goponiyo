package com.example.gopontext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

public class SingleCipherExploreActivity extends AppCompatActivity {

    protected TextView activityTitleTextView;
    protected ImageButton goPrevActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_cipher_explore);

        activityTitleTextView = findViewById(R.id.activityTitleTextView);
        goPrevActivityButton = findViewById(R.id.goPrevActivityButton);

        activityTitleTextView.setText(Objects.requireNonNull(getIntent().getExtras()).getString("TITLE"));

        goPrevActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleCipherExploreActivity.this.finish();
            }
        });


    }
}