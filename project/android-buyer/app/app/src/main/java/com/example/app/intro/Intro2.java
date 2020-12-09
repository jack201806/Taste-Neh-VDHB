package com.example.app.intro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.app.MainActivity;
import com.example.app.R;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import java.util.Timer;
import java.util.TimerTask;

public class Intro2 extends AppIntro2 {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //透明化通知栏（必须Android 5.0以上才可以！！！）
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // 好的当前活动的DecorView,在改变UI显示
            View decorView = this.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // 使其状态栏呈现透明色
            this.getWindow().setStatusBarColor(Color.rgb(253,170,28));
        }
//        setContentView(R.layout.activity_intro2);

        // 在这里添加四个幻灯片的片段

        // AppIntro会自动生成点指示符和按钮。
        addSlide(SampleSlide.newInstance(R.layout.firstslide));
        addSlide(SampleSlide.newInstance(R.layout.secondslide));


        // 只需设置标题，描述，背景图片,背景颜色。
        //addSlide(AppIntroFragment.newInstance("欢迎界面", "欢迎", R.drawable.hello, 32));


        //显示状态栏
        showStatusBar(true);
        //隐藏状态栏
        //showStatusBar(false);


        //编辑设备上导航栏的颜色
        //setNavBarColor(Color.parseColor("#ff0000"));
        setNavBarColor("#550000ff");

        //  显示或隐藏状态栏
//        showStatusBar(true);

        //  打开震动并设置强度
        setVibrate(true);
        setVibrateIntensity(30);

        //  隐藏SKIP和DONE按钮
//        setSkipText("跳过");
//        setDoneText("完成");

        //  动画，只能选1个，否则会报错
        setFadeAnimation();


        //设置定时跳转
        final Intent it = new Intent(this, MainActivity.class); //你要转向的Activity
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
            }
        };
        timer.schedule(task, 1000 * 3); //5秒后

    }


    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }


    //    @Override
    public void onSlideChanged(Fragment oldFragment, Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        Toast.makeText(this, "欢迎", Toast.LENGTH_SHORT).show();
    }
}
