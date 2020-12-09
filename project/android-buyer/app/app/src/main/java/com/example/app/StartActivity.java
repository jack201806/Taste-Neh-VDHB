package com.example.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.example.app.intro.Intro2;

public class StartActivity extends AppCompatActivity {
    private ImageView image;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //透明化通知栏（必须Android 5.0以上才可以！！！）
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // 好的当前活动的DecorView,在改变UI显示
            View decorView =
            this.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // 使其状态栏呈现透明色
            this.getWindow().setStatusBarColor(Color.rgb(253,170,28));
        }


        //网络加载图片，头像
//        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603101761728&di=721ae5232e882c8cf1f247355b4cf068&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F70%2F91%2F01300000261284122542917592865.jpg";
//        Glide.with(this)
//                .load(url)
//                .circleCrop()//转换策略，特别适合头像的显示
////                .fitCenter()
////                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//默认磁盘缓存策略
////                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存
//                .placeholder(R.mipmap.loading)//正在加载时显示的图片
//                .error(R.mipmap.failure)//永久加载失败时显示的图片
//                .fallback(R.mipmap.notexist)//表示图片地址为null时显示的图片
//                .into(image);


        image = (ImageView) findViewById(R.id.welcome);
        AnimationSet animationSet = new AnimationSet(true);
        //设置透明度变化范围
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(2000);
        animationSet.addAnimation(alphaAnimation);
        image.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //image.setBackgroundResource(R.drawable.intro);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(StartActivity.this, Intro2.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}