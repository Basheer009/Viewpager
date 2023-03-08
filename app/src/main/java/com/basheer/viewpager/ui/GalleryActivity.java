package com.basheer.viewpager.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.basheer.viewpager.R;
import com.basheer.viewpager.adapter.ImageAdapter;
import com.basheer.viewpager.bean.DataBean;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.DrawableIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {

    @BindView(R.id.banner1)
    Banner mBanner1;
    @BindView(R.id.banner2)
    Banner mBanner2;
    @BindView(R.id.indicator)
    DrawableIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        /**
         * GalleryEffects
         */
        mBanner1.setAdapter(new ImageAdapter(DataBean.getTestData2()));
        mBanner1.setIndicator(new CircleIndicator(this));
        //add gallery effects
        mBanner1.setBannerGalleryEffect(50, 10);
        //(It can be used in combination with other Page Transformers, such as Alpha Page Transformer, but it will display conflicts with other Page Transformers with scaling)
        //Add transparency effect (gallery with transparency effect is better)
        //mBanner1.addPageTransformer(new AlphaPageTransformer());


        /**
         * MeizuEffect
         */
        mBanner2.setAdapter(new ImageAdapter(DataBean.getTestData()));
        mBanner2.setIndicator(indicator, false);
        //AddMeizuEffect
        mBanner2.setBannerGalleryMZ(20);


    }


}