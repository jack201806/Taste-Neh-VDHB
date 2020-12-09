package com.example.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.Activitys.StoreActivity;
import com.example.app.Bean.Store;
import com.example.app.refresh.StoreAdapter;
import com.example.app.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {
    //刷新控件
    private ListView lvContent;
    private SmartRefreshLayout srl;
    private List<Store> stores;
    private StoreAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        //刷新控件
        lvContent = view.findViewById(R.id.lv_content);
        srl = view.findViewById(R.id.srl);
        //设置Header和Footer的样式
        //创建Header样式对象,已经在fragment1中设置
        //创建Footer样式
        FalsifyFooter footer = new FalsifyFooter(getActivity().getApplicationContext());
        //设置Footer样式
        srl.setRefreshFooter(footer);
        //设置回弹时间
        srl.setReboundDuration(3000);

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
                if (stores.size() < 10) {
                    loadMoreData();
                    //通知加载数据完毕
                    srl.finishLoadMore();
                } else {
                    //通知没有更多数据可加载
                    srl.finishLoadMoreWithNoMoreData();
                }
            }
        });
        //初始化数据
        stores = initData();

        //实例化Adapter
        adapter = new StoreAdapter(getActivity().getApplicationContext(), R.layout.store_item, stores);
        lvContent.setAdapter(adapter);
        //给ListView注册监听器
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store store = stores.get(position);
                Intent intent=new Intent(getActivity(), StoreActivity.class);
                startActivity(intent);
                //跳转到详情页面
            }
        });
        return view;

    }

    /*
     * 模拟加载更多数据
     */
    private void loadMoreData() {
        for (int i = 0; i < 5; i++) {
            Store store = new Store("店铺" + (int) (Math.random() * 100),"R.drawable."+(int) (Math.random() * 100));
            stores.add(store);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        stores.clear();
        stores.addAll(initData());//stus = initData()
        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据
     */
    private List<Store> initData() {
        List<Store> list = new ArrayList<>();
        Store store1 = new Store("五食堂", "五食堂");
        list.add(store1);
        Store store2 = new Store("三食堂", "三食堂");
        list.add(store2);
        Store store3 = new Store("一食堂", "一食堂");
        list.add(store3);
        return list;
    }
}