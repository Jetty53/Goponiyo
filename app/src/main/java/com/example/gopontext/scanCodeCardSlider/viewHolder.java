package com.example.gopontext.scanCodeCardSlider;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gopontext.R;
import com.example.gopontext.views.scanCodeEncodeFragment;

public class viewHolder extends RecyclerView.ViewHolder {

    public TextView scanCodeProcessTitle;
    public CardView scanCodeSingleItemCardView;

    public viewHolder(@NonNull View itemView) {
        super(itemView);
        scanCodeProcessTitle = itemView.findViewById(R.id.scanCodeProcTextView);
        scanCodeSingleItemCardView = itemView.findViewById(R.id.scanCodeSingleItemCardView);
    }

    void setData(String scanCodeProcessTitle){

        this.scanCodeProcessTitle.setText(scanCodeProcessTitle);

    }


}
