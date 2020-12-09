package com.example.app.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.app.R;
import com.example.app.orderFragment.FinishedFragment;
import com.example.app.orderFragment.WaitingArrivedFragment;
import com.example.app.orderFragment.WaitingCommentFragment;
import com.example.app.orderFragment.WaitingPaymentFragment;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private ViewPager my_viewpager;
    private TabLayout my_tab;
    private List<Fragment> fragments;
    private List<String> titles;
    private ImageView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        StatusBarUtil.setTranslucent(this, Color.parseColor("#ff0000"));
        initView();
        message = findViewById(R.id.message);
        //点击跳转消息界面
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void initView() {
        my_tab=(TabLayout) findViewById(R.id.tl_tab);
        my_viewpager=(ViewPager) findViewById(R.id.vp_content);
        fragments=new ArrayList<>();       //碎片的集合
        fragments.add(new WaitingPaymentFragment());
        fragments.add(new WaitingArrivedFragment());
        fragments.add(new WaitingCommentFragment());
        fragments.add(new FinishedFragment());

        titles=new ArrayList<>();     //标题的集合
        titles.add("待付款");
        titles.add("待送达");
        titles.add("待评价");
        titles.add("已完成");

        MyAdapter1 adapter=new MyAdapter1(getSupportFragmentManager());
        my_viewpager.setAdapter(adapter);
        my_tab.setupWithViewPager(my_viewpager);    //关联TabLayout和ViewPager

    }

    private class MyAdapter1 extends FragmentPagerAdapter {     //适配器
        public MyAdapter1(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);  //返回碎片集合的第几项
        }

        @Override
        public int getCount() {
            return fragments.size();    //返回碎片集合大小
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);    //返回标题的第几项
        }
    }
}