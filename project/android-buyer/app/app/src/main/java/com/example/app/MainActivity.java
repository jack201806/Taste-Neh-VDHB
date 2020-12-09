package com.example.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.example.app.fragments.MapFragment;
import com.example.app.fragments.MyFragment;
import com.example.app.fragments.ProductFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost tabHost;
    private Map<String, LinearLayout> linearLayoutMap = new HashMap<>();
    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();
    private long exitTime;//用户点击返回按钮的时间
    //自定义toast样式
    private View my_toast;
    private Toast toast;
    private TextView tv;

    private MapFragment MapFragment= new MapFragment();
    private MyFragment MyFragment=new MyFragment();
    //private ProductFragment ProductFragment=new ProductFragment();



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //透明化通知栏（必须Android 5.0以上才可以！！！）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            /**
             * 想实现白底黑字的通知栏，必须写这句话：Window window = getWindow();
             * 然后写上下方3句话，注意开头一定是window....，而不能是this.getWindow....
             * 否则不能达到预期效果，本人亲测如此
             */
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //底部导航栏
            window.setNavigationBarColor(Color.rgb(255, 255, 255));
        }


        initFragment();

        //使用自定义Toast
        my_toast = getLayoutInflater().inflate(R.layout.my_toast, null);
        toast = new Toast(getApplicationContext());
        toast.setView(my_toast);
        tv = my_toast.findViewById(R.id.toast_info);


        MyActivityManager.addActivity(this);
    }


    private void initFragment() {
        //获得FragmentTabHost的引用
        tabHost = findViewById(android.R.id.tabhost);
        //初始化
        tabHost.setup(this, getSupportFragmentManager(),
                android.R.id.tabcontent);
        //创建内容页面TabSpec对象
        final TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1").setIndicator(getTabSpecView(Color.rgb(255, 255, 255), "tab1", "浏览", R.drawable.product_default));
        tabHost.addTab(tab1, ProductFragment.class, null);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2").setIndicator(getTabSpecView(Color.rgb(255, 255, 255), "tab2", "地图", R.drawable.map_default));
        tabHost.addTab(tab2, MapFragment.class, null);
        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3").setIndicator(getTabSpecView(Color.rgb(255, 255, 255), "tab3", "我的", R.drawable.my_default));
        tabHost.addTab(tab3, MyFragment.class, null);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "tab1":
                        changeStyle("tab1",
                                R.drawable.product, Color.rgb(0,255,0));
                        changeStyle("tab2",
                                R.drawable.map_default, Color.rgb(205, 205, 205));
                        changeStyle("tab3",
                                R.drawable.my, Color.rgb(205, 205, 205));
                        showToast("进入浏览板块");
                        break;
                    case "tab2":
                        changeStyle("tab1",
                                R.drawable.product_default, Color.rgb(205, 205, 205));
                        changeStyle("tab2",
                                R.drawable.map, Color.rgb(0,255,0));
                        changeStyle("tab3",
                                R.drawable.my_default, Color.rgb(205, 205, 205));
                        showToast("进入地图板块");
                        break;
                    case "tab3":
                        changeStyle("tab1",
                                R.drawable.product_default, Color.rgb(205, 205, 205));
                        changeStyle("tab2",
                                R.drawable.map_default, Color.rgb(205, 205, 205));
                        changeStyle("tab3",
                                R.drawable.my, Color.rgb(0,255,0));
                        showToast("进入我的板块");
                        break;
                }
            }
        });
        //设置默认选中的标签页:参数是下标
        tabHost.setCurrentTab(0);
        changeStyle("tab1",
                R.drawable.product,Color.rgb(0,255,0));
    }
    //定义方法，更改每个标签的样式
    private void changeStyle(Object obj, int resId, int txt) {
        imageViewMap.get(obj).setImageResource(resId);
        textViewMap.get(obj).setTextColor(txt);
    }

    public View getTabSpecView(int color, String tag, String tilte, int drawable) {
        View view = getLayoutInflater().inflate(R.layout.tab_spec_layout, null);

        //获取tab_spec_layout布局当中视图控件的引用
        LinearLayout ll = view.findViewById(R.id.ll);
        ll.setBackgroundColor(color);
        //将LinearLayout对象存储到Map中
        linearLayoutMap.put(tag, ll);

        ImageView icon = view.findViewById(R.id.icon);
        icon.setImageResource(drawable);
        //将ImageView对象存储到Map中
        imageViewMap.put(tag, icon);

        TextView tvTitle = view.findViewById(R.id.title);
        tvTitle.setText(tilte);
        //将TextView对象存储到Map中
        textViewMap.put(tag, tvTitle);

        return view;
    }


    @Override
    //按下任意按键时会自动调用
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户连续两次点击返回按钮，时间间隔小于2s时，退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK) {//判断是否点击了返回按钮
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("再次点击将退出程序");
                exitTime = System.currentTimeMillis();//获取当前时间的毫秒数
            } else {
                this.finish();//时间间隔小于2s，退出程序
            }
        }
        return true;
    }

    //定义showToast方法
    private void showToast(String str) {
        tv.setText(str);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


}