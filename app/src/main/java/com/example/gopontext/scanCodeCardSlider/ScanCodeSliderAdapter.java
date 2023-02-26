package com.example.gopontext.scanCodeCardSlider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gopontext.R;

import java.util.List;

public class ScanCodeSliderAdapter extends RecyclerView.Adapter<viewHolder> {

    private List<ScanCodeCardModel> scanCodeCardModels;
    private Context context;

    private OnItemsClickListener listener = null;

    public ScanCodeSliderAdapter(List<ScanCodeCardModel> scanCodeCardModels, Context context) {
        this.scanCodeCardModels = scanCodeCardModels;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_scan_code_card_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {

        final ScanCodeCardModel items = scanCodeCardModels.get(position);
//        holder.scanCodeSingleItemCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.setData(scanCodeCardModels.get(position).getScanCodeProcedure());

        holder.scanCodeSingleItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener != null){
                    listener.onItemClick(items);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return scanCodeCardModels.size();
    }

    public interface OnItemsClickListener{
        void onItemClick(ScanCodeCardModel scanCodeCardModels);
    }

//    public static class viewHolder extends RecyclerView.ViewHolder{
//
//        TextView scanCodeProcessTitle;
//        com.example.gopontext.views.scanCodeEncodeFragment scanCodeEncodeFragment;
//        CardView scanCodeSingleItemCardView;
//
//        public viewHolder(@NonNull View itemView) {
//            super(itemView);
//            scanCodeProcessTitle = itemView.findViewById(R.id.scanCodeProcTextView);
//            scanCodeSingleItemCardView = itemView.findViewById(R.id.scanCodeSingleItemCardView);
//        }
//
//        private void setData(String scanCodeProcessTitle){
//            this.scanCodeProcessTitle.setText(scanCodeProcessTitle);
//        }
//    }
}


