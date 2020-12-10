package com.example.app.fragment;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.Activitys.StoreActivity;
import com.example.app.Bean.Store;
import com.example.app.refresh.StoreAdapter;
import com.example.app.R;
import com.example.app.utils.ConfigUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {
    //刷新控件
    private ListView lvContent;
    private SmartRefreshLayout srl;
    private List<Store> stores;
    private List<Store> storesInit = new ArrayList<>();
    private StoreAdapter adapter;
    //定义Gson对象属性
    private Gson gson = new Gson();
    //刷新次数
    private int refreshCount = 0;
    private String str;
    private TextView textView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://表示接收服务端的字符串

                    str = msg.obj.toString();
                    Log.e("lll", str);
                    if (!str.equals("商家浏览界面")) {
                        //1. 得到集合的类型
                        Type type = new TypeToken<List<Store>>() {
                        }.getType();
                        //初始化数据
                        stores = gson.fromJson(str, type);
                        //实例化Adapter
                        for (int i = 0; i < stores.size(); i++) {
                            storesInit.add(stores.get(i));
                        }
                        if (refreshCount == 0) {
                            adapter = new StoreAdapter(getActivity().getApplicationContext(), R.layout.store_item, storesInit);
                            lvContent.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        //给ListView注册监听器
                        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Store store = storesInit.get(position);
                                Intent intent = new Intent(getActivity(), StoreActivity.class);
                                intent.putExtra("id", String.valueOf(store.getId()));
                                startActivity(intent);
                                //跳转到详情页面
                            }
                        });
                    }
                    break;
            }
        }

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        //刷新控件
        lvContent = view.findViewById(R.id.lv_content);
        srl = view.findViewById(R.id.srl);
//        textView = view.findViewById(R.id.load_text);
        //设置Header和Footer的样式
        //创建Header样式对象,已经在fragment1中设置
        //创建Footer样式
//        FalsifyFooter footer = new FalsifyFooter(getActivity().getApplicationContext());
//        //设置Footer样式
//        srl.setRefreshFooter(footer);
        //设置回弹时间
        srl.setReboundDuration(1500);

        //给智能刷新控件注册下拉刷新事件监听器
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                //通知刷新完毕
                srl.finishRefresh();
            }
        });

        //给智能刷新控件注册上拉加载更多事件监听器
        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //加载更多数据(假设超过10条数据则加载完毕）
                if (!str.equals("商家浏览界面")) {
                    loadMoreData();
                    //通知加载数据完毕
                    srl.finishLoadMore();
                } else {
                    //通知没有更多数据可加载
                    srl.finishLoadMoreWithNoMoreData();
                }
            }
        });
        android_store_init();
        return view;

    }

    private void android_store_init() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.SERVER_ADDR + "android_store_init");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    //  获取网络输出流
                    OutputStream out = conn.getOutputStream();
                    out.write((refreshCount + "").getBytes());
                    //必须要获取网络输入流，保证客户端和服务端建立连接
                    InputStream in = conn.getInputStream();
                    out.close();
                    //使用字符流读取
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "utf-8"));
                    //读取字符信息
                    String str = reader.readLine();
                    if (str == null) {
                        str = "网络连接错误";
                    }
                    Log.e("服务端的信息", str);
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

    /*
     * 模拟加载更多数据
     */
    private void loadMoreData() {
        refreshCount += 1;
        android_store_init();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        storesInit.clear();
        refreshCount = 0;
        android_store_init();
    }
}