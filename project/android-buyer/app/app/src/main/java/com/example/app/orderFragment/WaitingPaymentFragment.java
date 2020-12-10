package com.example.app.orderFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.app.Bean.Order;
import com.example.app.Bean.UserProduct;
import com.example.app.R;
import com.example.app.orderAdapter.OrderAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class WaitingPaymentFragment extends Fragment {
    private SmartRefreshLayout waitingPaymentList;
    private ListView waitingPaymentListContent;
    private List<Order> orderList;
    private OrderAdapter orderAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.waiting_payment,container,false);
        waitingPaymentList = view.findViewById(R.id.waiting_payment_list);
        waitingPaymentListContent = view.findViewById(R.id.waiting_payment_order_list_content);
        waitingPaymentList.setReboundDuration(2000);
        //给智能刷新控件注册下拉刷新事件监听器
        waitingPaymentList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                //通知刷新完毕
                waitingPaymentList.finishRefresh();
            }
        });
        //初始化数据
        orderList = initData();
        orderAdapter = new OrderAdapter(getActivity().getApplicationContext(),orderList,R.layout.order_item);
        waitingPaymentListContent.setAdapter(orderAdapter);
        return view;
    }
    private List<Order> initData() {
        List <Order> list = new ArrayList<Order>();
        Order order = new Order();
        order.setOrderId("001");
        order.setOrderNum(2);
        order.setOrderState("待付款");
        order.setOrderTime("2020/11/23 9:56");
        order.setStoreHeader("/data/data/com.example.orders/files/store_header/store.jpg");
        float amount = 0;
        List<UserProduct> productList = new ArrayList<>();
        for(int i = 0;i<2;i++){
            UserProduct userProduct = new UserProduct();
            userProduct.setProductId("001");
            userProduct.setProductNum(2);
            userProduct.setProductName("水煮鱼");
            userProduct.setProductStandards("大份 微辣");
            userProduct.setProductPrice((float) 28.08);
            userProduct.setProductImg("/data/data/com.example.orders/files/product_imgs/fish.jpg");
            amount += userProduct.getProductNum()*userProduct.getProductPrice();
            productList.add(userProduct);
        }
        order.setOrderAmount(amount);
        order.setStoreName("河北师范大学三食堂");
        order.setProducts(productList);
        for(int i=0;i<6;i++){
            list.add(order);
        }
        return list;
    }


    //刷新数据
    private void refreshData() {
        orderList.clear();
        orderList.addAll(initData());//stus = initData()
        orderAdapter.notifyDataSetChanged();
    }
}