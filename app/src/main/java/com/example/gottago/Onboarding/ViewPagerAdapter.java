package com.example.gottago.Onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.gottago.R;

public class ViewPagerAdapter extends PagerAdapter {

   Context context;
    int sliderAllImages[] = {R.drawable.asset1, R.drawable.asset2, R.drawable.asset3 };
    int sliderAllTitles[] = {R.string.screen1, R.string.screen2, R.string.screen3 };
    int sliderAllDesc[] = {R.string.screen1desc, R.string.screen2desc, R.string.screen3desc };

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return sliderAllTitles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_screen, container, false);

        ImageView sliderImage = (ImageView) view.findViewById(R.id.sliderImage);
        TextView sliderTilte = (TextView) view.findViewById(R.id.sliderTitle);
        TextView sliderDesc = (TextView) view.findViewById(R.id.sliderDesc);

        sliderImage.setImageResource(sliderAllImages[position]);
        sliderTilte.setText(this.sliderAllTitles[position]);
        sliderDesc.setText(this.sliderAllDesc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
