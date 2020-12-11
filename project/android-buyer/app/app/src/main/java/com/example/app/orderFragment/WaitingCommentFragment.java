package com.example.app.orderFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.Activitys.StoreActivity;
import com.example.app.Bean.Order;
import com.example.app.Bean.Store;
import com.example.app.R;
import com.example.app.fragments.MyFragment;
import com.example.app.orderAdapter.OrderAdapter;
import com.example.app.utils.ConfigUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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
import java.util.List;

public class WaitingCommentFragment extends Fragment {
    private SmartRefreshLayout waitingCommentList;
    private ListView waitingCommentListContent;
    private List<Order> orderList;
    private OrderAdapter orderAdapter;
    private Gson gson = new Gson();
    private String str;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://表示接收服务端的字符串

                    str = msg.obj.toString();
                    Log.e("lll", str);
                    if (!str.equals("已完成订单界面")) {
                        //1. 得到集合的类型
                        Type type = new TypeToken<List<Store>>() {
                        }.getType();
                        //初始化数据
                        orderList = gson.fromJson(str, type);
                        orderAdapter = new OrderAdapter(getActivity().getApplicationContext(),orderList,R.layout.order_item);
                        waitingCommentListContent.setAdapter(orderAdapter);
                        //给ListView注册监听器
                        waitingCommentListContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Order order = orderList.get(position);
                                Intent intent = new Intent(getActivity(), StoreActivity.class);
                                intent.putExtra("storeId", String.valueOf(order.getStoreId()));
                                startActivity(intent);
                                //跳转到详情页面
                            }
                        });
                    }else{

                    }
                    break;
            }
        }

    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.waiting_comment,container,false);
        waitingCommentList = view.findViewById(R.id.waiting_comment_list);
        waitingCommentListContent = view.findViewById(R.id.waiting_comment_order_list_content);
        waitingCommentList.setReboundDuration(2000);
        //给智能刷新控件注册下拉刷新事件监听器
        waitingCommentList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                //通知刷新完毕
                waitingCommentList.finishRefresh();
            }
        });
        android_post_waiting_comment_orders();
        initData();
        return view;
    }
    private void initData() {
        for(int i = 0;i<orderList.size();i++){
            double amount = 0;
            Order order =orderList.get(i);
            for(int j =0;j<order.getProducts().size();j++){
                amount += order.getProducts().get(j).getProductPrice()*order.getProducts().get(j).getProductPrice();
            }
            order.setOrderNum(order.getProducts().size());
            order.setOrderAmount(amount);
        }
    }

    private void android_post_waiting_comment_orders() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.SERVER_ADDR + "android_post_waiting_comment_orders");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    //获取网络输出流
                    OutputStream out = conn.getOutputStream();
                    out.write(MyFragment.userInfo.getBytes());
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
    //刷新数据
    private void refreshData() {
        orderList.clear();
        android_post_waiting_comment_orders();
        initData();
        orderAdapter.notifyDataSetChanged();
    }
}
