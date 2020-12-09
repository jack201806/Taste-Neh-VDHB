package com.example.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.Activitys.NewsActivity;
import com.example.app.Bean.News;
import com.example.app.refresh.NewsAdapter;
import com.example.app.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    //刷新控件
    private ListView lvContent;
    private SmartRefreshLayout srl;
    private List<News> newsList;
    private NewsAdapter newsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
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
                if (newsList.size() < 10) {
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
        newsList = initData();

        //实例化Adapter
        newsAdapter = new NewsAdapter(getActivity().getApplicationContext(), R.layout.news_item, newsList);
        lvContent.setAdapter(newsAdapter);
        //给ListView注册监听器
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsList.get(position);
                Intent i=new Intent(getActivity(), NewsActivity.class);
                i.putExtra("title",news.getTitle());
                startActivity(i);
            }
        });
        return view;

    }

    /*
     * 模拟加载更多数据
     */
    private void loadMoreData() {
        for (int i = 0; i < 5; i++) {
            News news = new News("新闻" + (int) (Math.random() * 100));
            newsList.add(news);
        }
        newsAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        newsList.clear();
        newsList.addAll(initData());//stus = initData()
        newsAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据
     */
    private List<News> initData() {
        List<News> list = new ArrayList<>();
        News news1 = new News("习近平 ： 全面加强知识产权保护工作  激发创新活力推动构建新发展格局");
        list.add(news1);
        News news2 = new News("习近平 ： 全面加强知识产权保护工作  激发创新活力推动构建新发展格局");
        list.add(news2);
        News news3 = new News("习近平 ： 全面加强知识产权保护工作  激发创新活力推动构建新发展格局");
        list.add(news3);
        return list;
    }
}