package com.example.app.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.app.Activitys.SearchActivity;
import com.example.app.Bean.Store;
import com.example.app.R;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.widget.Button;
import android.widget.ListView;

import com.example.app.Bean.Dish;
import com.example.app.banner.GlideImageLoader;
import com.example.app.refresh.StoreAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {

    private View root;

    //自定义toast样式
    private View my_toast;
    private Toast toast;
    private TextView tv;

    private Button btnToJson;
    private Button btnToObj;
    private TextView tvJson;
    private TextView tvObj;
    //定义Gson对象属性
    private Gson gson;
    private List<Integer> images;


    //刷新控件
    private ListView lvContent;
    private SmartRefreshLayout srl;
    private List<Store> stus;
    private StoreAdapter adapter;

    //搜索
    private SearchView searchView;

    //导航
    private ViewPager my_viewpager;
    private TabLayout my_tab;
    private List<Fragment> fragments;
    private List<String> titles;



    public ProductFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //大困难，终于解决了   第二次点击fragment不显示子fragment内容
        if (root != null) {
            ViewGroup parent = (ViewGroup) root.getParent();
            if (parent != null) {
                parent.removeView(root);
            }
            return root;
        }


        //透明化通知栏（必须Android 5.0以上才可以！！！）
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // 好的当前活动的DecorView,在改变UI显示
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // 使其状态栏呈现透明色
            getActivity().getWindow().setStatusBarColor(Color.rgb(253,170,28));
        }


        root=inflater.inflate(R.layout.fragment_product,container,false);
        findViews();

        //导航
        initView();


        //搜索
        searchView = (SearchView) root.findViewById(R.id.searchView);
        //搜索框不自动缩小为一个搜索图标，而是match_parent
        searchView.setIconifiedByDefault(false);
        //显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        //默认提示文本
        searchView.setQueryHint("请输入搜索内容");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //单击搜索按钮的监听
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i=new Intent(getActivity(), SearchActivity.class);
                i.putExtra("searchInfo",query);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });



        images = new ArrayList<>();
        images.add(R.drawable.a1);
        images.add(R.drawable.a2);
        images.add(R.drawable.a3);
        images.add(R.drawable.a4);
        setListener();
        //Gson对象实例化
        initGson();


        Banner banner = root.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置轮播时间
        banner.setDelayTime(2000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();



//        Banner banner = (Banner) findViewById(R.id.banner);
//        //设置banner样式
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
//        //设置图片加载器
//        banner.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        banner.setImages(images);
//        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
//        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
//        //设置自动轮播，默认为true
//        banner.isAutoPlay(true);
//        //设置轮播时间
//        banner.setDelayTime(1500);
//        //设置指示器位置（当banner模式中有指示器时）
//        banner.setIndicatorGravity(BannerConfig.CENTER);
//        //banner设置方法全部调用完毕时最后调用
//        banner.start();

        return root;
        // Inflate the layout for this fragment
    }

    //导航
    @SuppressLint("ResourceType")
    private void initView() {

        my_tab=(TabLayout) root.findViewById(R.id.my_tab);
        my_viewpager=(ViewPager) root.findViewById(R.id.my_viewpager);
        fragments=new ArrayList<>();       //碎片的集合
        fragments.add(new com.example.app.fragment.Fragment1());
        fragments.add(new com.example.app.fragment.Fragment2());
        fragments.add(new com.example.app.fragment.Fragment3());
        fragments.add(new com.example.app.fragment.Fragment4());
        fragments.add(new com.example.app.fragment.Fragment5());
        fragments.add(new com.example.app.fragment.Fragment6());

        titles=new ArrayList<>();     //标题的集合
        titles.add("最新");
        titles.add("热门");
        titles.add("食堂");
        titles.add("国培");
        titles.add("校外");
        titles.add("零食");

        MyAdapter1 adapter=new MyAdapter1(getActivity().getSupportFragmentManager());
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



    /**
     * 初始化Gson对象
     */
    private void initGson() {
//        gson = new Gson();
        //允许配置参数的Gson对象初始化
        gson = new GsonBuilder()//创建GsonBuilder对象
                .setPrettyPrinting()//格式化输出
                .serializeNulls()//允许输出Null值属性
                .setDateFormat("YY:MM:dd")//日期格式化
                .create();//创建Gson对象
    }

    private void setListener() {
        ProductFragment.MyListener listener = new ProductFragment.MyListener();
        btnToJson.setOnClickListener(listener);
        btnToObj.setOnClickListener(listener);
    }

class MyListener implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_toJson:
                //普通Student对象序列化
//                    convertToSimpJson();
                //嵌套类的对象的序列化
//                    convertToComJson();
                //对象数组序列化
//                    convertToArrayJson();
                //对象集合序列化
                convertListObjToJson();
                break;
            case R.id.btn_toObj:
                //普通Json串的反序列化
//                    convertToSimpObj();
                //嵌套Json串的反序列化
//                    convertToComObj();
                //对象数组反序列化
//                    convertToArrayObj();
                //集合对象反序列化
                convertToListObj();
                break;
        }
    }
}

    /**
     * 集合对象反序列化（JSONArray转对象的集合）
     */
    private void convertToListObj() {
        String json = tvJson.getText().toString();
        //反序列化
        //1. 得到集合的类型
        Type type = new TypeToken<List<Dish>>(){}.getType();
        //2. 反序列化
        List<Dish> stus = gson.fromJson(json, type);
        tvObj.setText(stus.toString());
    }

    /**
     * 对象集合序列化
     */
    private void convertListObjToJson() {
        List<Dish> dishes = new ArrayList<>();
        Dish dish1=new Dish();
        dish1.setId("1");
        dish1.setInfo("红烧鱼");
        dish1.setName("鱼");
        dish1.setPrice("20");
        dish1.setShopId("1");
        Dish dish2=new Dish();
        dish2.setId("1");
        dish2.setInfo("红烧鱼");
        dish2.setName("鱼");
        dish2.setPrice("20");
        dish2.setShopId("1");
        dishes.add(dish1);
        dishes.add(dish2);
        //序列化
        String json = gson.toJson(dishes);
        tvJson.setText(json);
    }

    private void findViews() {
        //找控件
        btnToJson = root.findViewById(R.id.btn_toJson);
        btnToObj = root.findViewById(R.id.btn_toObj);
        tvJson = root.findViewById(R.id.tv_json);
        tvObj = root.findViewById(R.id.tv_obj);

        //使用自定义Toast
        my_toast = getLayoutInflater().inflate(R.layout.my_toast,null);
        toast = new Toast(this.getContext());
        toast.setView(my_toast);
        tv = my_toast.findViewById(R.id.toast_info);
    }

    //定义showToast方法
    private void showToast(String str) {
        tv.setText(str);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


}