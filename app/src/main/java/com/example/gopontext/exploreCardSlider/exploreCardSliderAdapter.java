package com.example.gopontext.exploreCardSlider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.gopontext.R;

import java.util.List;

public class exploreCardSliderAdapter extends PagerAdapter {

    private List<exploreCardModel> exploreCardModel;
    private LayoutInflater layoutInflater;
    private Context context;

    public exploreCardSliderAdapter(List<com.example.gopontext.exploreCardSlider.exploreCardModel> exploreCardModel, Context context) {
        this.exploreCardModel = exploreCardModel;
        this.context = context;
    }

    @Override
    public int getCount() {
        return exploreCardModel.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.single_cipher_card_item, container, false);
        TextView cardTitleTextView, cardDescTextView;
        Button cardExploreMoreBtn;

        cardTitleTextView = view.findViewById(R.id.cardTitleTextView);
        cardDescTextView = view.findViewById(R.id.cardDescTextView);
        cardExploreMoreBtn = view.findViewById(R.id.cardExploreMoreBtn);


        cardTitleTextView.setText(exploreCardModel.get(position).getTitle());
        cardDescTextView.setText(exploreCardModel.get(position).getDesc());

        cardExploreMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singleCipherExploreIntent = new Intent(context, com.example.gopontext.SingleCipherExploreActivity.class);
                singleCipherExploreIntent.putExtra("TITLE", exploreCardModel.get(position).getTitle());
                singleCipherExploreIntent.putExtra("DESC", exploreCardModel.get(position).getDesc());
                context.startActivity(singleCipherExploreIntent);
                //Toast.makeText(context, ""+exploreCardModel.get(position).getId()+"\n"+exploreCardModel.get(position).getTitle()+"\n"+exploreCardModel.get(position).getDesc(), Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
