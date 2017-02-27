package com.dus.banner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dus.banner.view.BannerViewGroup;

public class MainActivity extends AppCompatActivity {
    private BannerViewGroup bannerGroup;
    private  int [] imgs = {
        R.drawable.pic_1,
        R.drawable.pic_2,
        R.drawable.pic_3,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bannerGroup = (BannerViewGroup) findViewById(R.id.img_group);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i=0;i<imgs.length;i++){
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(params);
            iv.setImageResource(imgs[i]);
            bannerGroup.addView(iv);
        }
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
