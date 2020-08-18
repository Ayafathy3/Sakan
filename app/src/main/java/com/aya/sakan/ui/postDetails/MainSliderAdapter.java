package com.aya.sakan.ui.postDetails;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    List<String> sliderList;

    public void setSliderList( List<String> sliderList ) {
        this.sliderList = sliderList;
    }

    @Override
    public int getItemCount() {
        return sliderList.size();
    }

    @Override
    public void onBindImageSlide( int position, ImageSlideViewHolder viewHolder ) {
        String slider_string = sliderList.get(position);
        viewHolder.bindImageSlide(slider_string);

    }
}
