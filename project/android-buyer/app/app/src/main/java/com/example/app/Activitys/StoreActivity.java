package com.example.app.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app.Bean.Store;
import com.example.app.R;

import com.example.app.StartActivity;
import com.example.app.storeFragment.CommentFragment;
import com.example.app.storeFragment.ProductsFragment;
import com.example.app.utils.ConfigUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private Gson gson;
    private Store store;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1://表示接收服务端的字符串
                    String storeInfo = (String) msg.obj;
                    store=gson.fromJson(storeInfo, Store.class);
                    break;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_info);
        StatusBarUtil.setTransparent(this);
        findView();
        //初始化店铺统一内容
        initView();
        //为商家信息赋值
        storeName.setText(store.getName());
        storeScore.setText("4.8");
        storeSale.setText("589");
        storeAnnouncement.setText("本店是新店，欢迎大家光顾");
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
        String storeName=getIntent().getStringExtra("storeName");
        //查询店铺详情
        android_prodicy_init("dsdcv");

    }

    private void android_prodicy_init(final String json) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.SERVER_ADDR+"android_user_login");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    //  获取网络输出流
                    OutputStream out = conn.getOutputStream();
                    //  获取待发送的字符串
                    String id=getIntent().getStringExtra("id");
                    out.write(id.getBytes());
                    //必须要获取网络输入流，保证客户端和服务端建立连接
                    InputStream in = conn.getInputStream();
                    out.close();
                    //使用字符流读取
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "utf-8"));
                    //读取字符信息
                    String str = reader.readLine();
                    if (str==null){
                        str = "网络连接错误";
                    }
                    Log.e("服务端的信息",str);
                    //关闭流
                    reader.close();
                    in.close();
                    //借助于Message，把收到的字符串显示在页面上
                    //创建Message对象
                    Message msg = new Message();
                    //设置Message对象的参数
                    msg.what = 1;
                    msg.obj = str;
                    //发送Message
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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