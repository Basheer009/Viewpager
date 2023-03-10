package com.basheer.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.basheer.viewpager.adapter.ImageAdapter;
import com.basheer.viewpager.adapter.ImageTitleAdapter;
import com.basheer.viewpager.adapter.ImageTitleNumAdapter;
import com.basheer.viewpager.adapter.MultipleTypesAdapter;
import com.basheer.viewpager.bean.DataBean;
import com.youth.banner.indicator.DrawableIndicator;
import com.basheer.viewpager.ui.ConstraintLayoutBannerActivity;
import com.basheer.viewpager.ui.GalleryActivity;
import com.basheer.viewpager.ui.RecyclerViewBannerActivity;
import com.basheer.viewpager.ui.TVActivity;
import com.basheer.viewpager.ui.TouTiaoActivity;
import com.basheer.viewpager.ui.VideoActivity;
import com.basheer.viewpager.ui.Vp2FragmentRecyclerviewActivity;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.util.BannerUtils;
import com.youth.banner.util.LogUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.indicator)
    RoundLinesIndicator indicator;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //??????????????????????????????????????????????????????BannerImageAdapter
        ImageAdapter adapter = new ImageAdapter(DataBean.getTestData2());

        banner.setAdapter(adapter)
//              .setCurrentItem(0,false)
                .addBannerLifecycleObserver(this)//???????????????????????????
                .setIndicator(new CircleIndicator(this))//???????????????
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                    LogUtils.d("position???" + position);
                });

        //??????item????????????????????????(????????????????????????????????????????????????????????????????????????????????????)
//        banner.addPageTransformer(new MarginPageTransformer( BannerUtils.dp2px(10)));

        //???????????????????????????
        refresh.setOnRefreshListener(() -> {
            //????????????????????????3???????????????????????????setRefreshing ???false
            new Handler().postDelayed(() -> {
                refresh.setRefreshing(false);

                //???banner??????????????????
                banner.setDatas(DataBean.getTestData());

                //???setDatas()????????????????????????????????????adapter?????????????????????setDatas()???????????????
//                adapter.updateData(DataBean.getTestData());
//                banner.setCurrentItem(banner.getStartPosition(), false);
//                banner.setIndicatorPageChange();

            }, 3000);
        });

    }


    @OnClick({R.id.style_image, R.id.style_image_title, R.id.style_image_title_num, R.id.style_multiple,
            R.id.style_net_image, R.id.change_indicator, R.id.rv_banner, R.id.cl_banner, R.id.vp_banner,
            R.id.banner_video, R.id.banner_tv, R.id.gallery, R.id.topLine})
    public void click(View view) {
        indicator.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.style_image:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageAdapter(DataBean.getTestData()));
                banner.setIndicator(new CircleIndicator(this));
                banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
                break;
            case R.id.style_image_title:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageTitleAdapter(DataBean.getTestData()));
                banner.setIndicator(new CircleIndicator(this));
                banner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
                banner.setIndicatorMargins(new IndicatorConfig.Margins(0, 0,
                        BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(12)));
                break;
            case R.id.style_image_title_num:
                refresh.setEnabled(true);
                //??????????????????????????????title?????????adapter??????????????????????????????????????????????????????????????????????????????
                banner.setAdapter(new ImageTitleNumAdapter(DataBean.getTestData()));
                banner.removeIndicator();
                break;
            case R.id.style_multiple:
                refresh.setEnabled(true);
                banner.setIndicator(new CircleIndicator(this));
                banner.setAdapter(new MultipleTypesAdapter(this, DataBean.getTestData()));
                break;
            case R.id.style_net_image:
                refresh.setEnabled(false);
                //??????????????????????????????????????????
//                banner.setAdapter(new ImageNetAdapter(DataBean.getTestData3()));

                //??????????????????????????????????????????
                banner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData3()) {
                    @Override
                    public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
                        //????????????????????????
                        Glide.with(holder.itemView)
                                .load(data.imageUrl)
                                .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                                .into(holder.imageView);
                    }
                });
                banner.setIndicator(new RoundLinesIndicator(this));
                banner.setIndicatorSelectedWidth(BannerUtils.dp2px(15));
                break;
            case R.id.change_indicator:
                indicator.setVisibility(View.VISIBLE);
                //???????????????????????????????????????????????????
                banner.setIndicator(indicator, false);
                banner.setIndicatorSelectedWidth(BannerUtils.dp2px(15));
                break;
            case R.id.gallery:
                startActivity(new Intent(this, GalleryActivity.class));
                break;
            case R.id.rv_banner:
                startActivity(new Intent(this, RecyclerViewBannerActivity.class));
                break;
            case R.id.cl_banner:
                startActivity(new Intent(this, ConstraintLayoutBannerActivity.class));
                break;
            case R.id.vp_banner:
                startActivity(new Intent(this, Vp2FragmentRecyclerviewActivity.class));
                break;
            case R.id.banner_video:
                startActivity(new Intent(this, VideoActivity.class));
                break;
            case R.id.banner_tv:
                startActivity(new Intent(this, TVActivity.class));
                break;
            case R.id.topLine:
                startActivity(new Intent(this, TouTiaoActivity.class));
                break;
        }
    }
}
