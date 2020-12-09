package com.example.app.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app.R;

import com.example.app.StartActivity;
import com.example.app.storeFragment.CommentFragment;
import com.example.app.storeFragment.ProductsFragment;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
    private ViewPager my_viewpager;
    private TabLayout my_tab;
    private List<Fragment> fragments;
    private List<String> titles;
    private RelativeLayout storeMore;
    private ImageView storeHeader;
    private TextView storeName;
    private TextView storeScore;
    private TextView storeSale;
    private TextView storeAnnouncement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_info);
        StatusBarUtil.setTransparent(this);
        findView();
        storeName.setText("河北师范大学三食堂");
        storeScore.setText("4.8");
        storeSale.setText("589");
        storeAnnouncement.setText("本店是新店，欢迎大家光顾");
        initView();
        storeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog bgSetDialog = new Dialog(StoreActivity.this, R.style.BottomDialogStyle);
                //填充对话框的布局
                view = LayoutInflater.from(StoreActivity.this).inflate(R.layout.pop_store_more, null);
                //初始化控件
                TextView storeName = view.findViewById(R.id.store_name);
                storeName.setText("李拜六麻辣烫冒菜");
                TextView storeAddress = view.findViewById(R.id.store_address);
                storeAddress.setText("河北省石家庄市师范大学国培大厦一层");
                TextView storeBusinessTime = view.findViewById(R.id.store_business_time);
                storeBusinessTime.setText("09:30-21:00");
                //将布局设置给Dialog
                bgSetDialog.setContentView(view);
                //获取当前Activity所在的窗体
                Window dialogWindow = bgSetDialog.getWindow();
                dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity( Gravity.BOTTOM);
                bgSetDialog.show();//显示对话框
            }
        });
    }

    private void findView() {
        storeMore = findViewById(R.id.pop_store_more);
        storeName = findViewById(R.id.store_name);
        storeHeader = findViewById(R.id.store_img);
        storeScore  = findViewById(R.id.store_score);
        storeSale = findViewById(R.id.month_sale_num);
        storeAnnouncement = findViewById(R.id.store_announcement);
    }

    private void initView() {
        my_tab=(TabLayout) findViewById(R.id.tl_tab);
        my_viewpager=(ViewPager) findViewById(R.id.vp_content);
        fragments=new ArrayList<>();       //碎片的集合
        fragments.add(new ProductsFragment());
        fragments.add(new CommentFragment());
        titles=new ArrayList<>();     //标题的集合
        titles.add("点菜");
        titles.add("评价");

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