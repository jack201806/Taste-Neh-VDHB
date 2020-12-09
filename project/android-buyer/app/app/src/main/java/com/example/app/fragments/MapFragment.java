package com.example.app.fragments;
//import android.app.AlertDialog;
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.annotation.RequiresApi;
//import androidx.fragment.app.Fragment;
//
//import com.bumptech.glide.Glide;
//import com.example.app.R;
//
//
//
//public class MapFragment extends Fragment {
//
//    private View root;
//
//    public MapFragment() {
//        // Required empty public constructor
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        //透明化通知栏（必须Android 5.0以上才可以！！！）
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 好的当前活动的DecorView,在改变UI显示
//            View decorView = getActivity().getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            // 使其状态栏呈现透明色
//            getActivity().getWindow().setStatusBarColor(Color.rgb(253, 170, 28));
//        }
//        if (root == null) {
//            root = inflater.inflate(R.layout.fragment_map, container, false);
//            findViews();
//        }
//        ViewGroup parent = (ViewGroup) root.getParent();
//        if (parent != null) {
//            parent.removeView(root);
//        }
//        //设置监听器事件
//        setListeners();
//        return root;
//    }
//
//    private void setListeners() {
//    }
//
//    private void findViews() {
//    }
//}

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.app.R;

import android.graphics.Bitmap;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.example.app.map.BikingRouteOverlay;
import com.example.app.map.GlobalTool;
import com.example.app.utils.DrivingRouteOverlay;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment {

    private View root;

    //自定义toast样式
    private View my_toast;
    private Toast toast;
    private TextView tv;

        private MapView myMap;
        //定义BaiduMap控制器类属性
        private BaiduMap baiduMap;
        //定义UiSettingsUI控制器类属性
        private UiSettings uiSettings;
        //定位客户端类
        private LocationClient locClient;
        //定位客户端选项类
        private LocationClientOption locOption;
        private String address;
        //三个button
        private Button marker;
        private Button location;
        private Button navigate;
        //当前位置
        private LatLng position;
        //导航路线
        private LatLng point;
        BikingRouteOverlay overlay;
        private RoutePlanSearch routePlanSearch;

    public MapFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SDKInitializer.initialize(getActivity().getApplicationContext());


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
            root = inflater.inflate(R.layout.fragment_map, container, false);
        myMap = root.findViewById(R.id.bmapView);
        baiduMap = myMap.getMap();
        uiSettings = baiduMap.getUiSettings();
        marker=root.findViewById(R.id.marker);
        location=root.findViewById(R.id.location);
        navigate=root.findViewById(R.id.navigate);
        routePlanSearch= RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(listener);

        findViews();

        //设置默认比例尺大小
        zoomOperation();
        //显示校园时光塔位置
        showSchoolLocation();
        //定位
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //定位用户
                showLocationInfo();
            }
        });
        //点击目的地
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {


            //此方法就是点击地图监听
            @Override
            public void onMapClick(final LatLng latLng) {
                //获取经纬度
                final double latitude = latLng.latitude;
                final double longitude = latLng.longitude;
                Toast.makeText(getContext(),"目的地位置：纬度（"+latitude+"）经度（"+longitude+")",Toast.LENGTH_SHORT).show();
                //先清除图层
                baiduMap.clear();
                // 定义Maker坐标点
                point = new LatLng(latitude, longitude);
                // 构建MarkerOption，用于在地图上添加Marker
                MarkerOptions options = new MarkerOptions().position(point)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.red_marker));
                // 在地图上添加Marker，并显示
                baiduMap.addOverlay(options);

                //规划路线
                navigate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setElement();
                    }
                });
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {

            }
        });
        return root;
    }

    private void setElement() {
        PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", "西二旗地铁站");
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", "百度科技园");
        routePlanSearch.bikingSearch((new BikingRoutePlanOption())
                .from(stNode)
                .to(enNode));
        overlay = new BikingRouteOverlay(myMap.getMap());
    }

    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            Toast.makeText(getActivity(), "未定位到数据3", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
            Toast.makeText(getActivity(), "未定位到数据4", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
            Toast.makeText(getActivity(), "未定位到数据5", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            Toast.makeText(getActivity(), "未定位到数据6", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
            Toast.makeText(getActivity(), "未定位到数据7", Toast.LENGTH_SHORT).show();
        }


        public void onGetBikingRouteResult(BikingRouteResult result) {
            if (result != null) {
                List<BikingRouteLine> routeLineList = result.getRouteLines();
                if (routeLineList != null && !routeLineList.isEmpty() && routeLineList.size() != 0) {
//                    Toast.makeText(FiveActivity.this, routeLineList.get(0).getDistance() + "", Toast.LENGTH_SHORT).show();
                    overlay.removeFromMap();
                    overlay.setData(routeLineList.get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                    String instruction = routeLineList.get(0).getAllStep().get(0).getInstructions();
                    Toast.makeText(getActivity(), routeLineList.get(0).getAllStep().size()
                            + "-------:" + instruction, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "未定位到数据2", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "未定位到数据1", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void showSchoolLocation() {
        //获取纬度信息
        double lat = 37.997070;
        //获取经度信息
        double lng = 114.515060;
        position = new LatLng(
                lat,
                lng
        );
        //转换成百度坐标
        LatLng baiduLatLng = GlobalTool.transGpsToBaiduLatLng(position);
        //在地图上显示当前位置
        showLocPostionOnMap(baiduLatLng);
    }

    /**
     * 定位并显示定位数据
     */
    private void showLocationInfo () {
        //获取定位客户端类的对象
        locClient = new LocationClient(getActivity().getApplicationContext());
        //获取定位客户端选项类的对象
        locOption = new LocationClientOption();
        //1. 设置定位参数
        //打开GPS
        locOption.setOpenGps(true);
        //设置扫描间隔时间
        locOption.setScanSpan(1000);
        //设置定位模式(高精度定位模式)
        locOption.setLocationMode(
                LocationClientOption.LocationMode.Hight_Accuracy);
        //设置坐标系
        locOption.setCoorType("wgs84");
        //设置是否需要地址信息
        locOption.setIsNeedAddress(true);
        //2. 将设置的定位参数应用给定位客户端
        locClient.setLocOption(locOption);
        //3. 启动定位
        locClient.start();
        //开启图层定位
        baiduMap.setMyLocationEnabled(true);
        //4. 注册定位监听器（为了将定位的数据显示在地图上）
        locClient.registerLocationListener(
                new BDAbstractLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        //获取纬度信息
                        double lat = bdLocation.getLatitude();
                        //获取经度信息
                        double lng = bdLocation.getLongitude();
                        position = new LatLng(
                                lat,
                                lng
                        );
                        //获取当前定位时间
                        String locTime = bdLocation.getTime();
                        //转换成百度坐标
                        LatLng baiduLatLng = GlobalTool.transGpsToBaiduLatLng(position);
                        //在地图上显示当前位置
                        showLocPostionOnMap(baiduLatLng);
                    }
                });
    }

    /**
     * 将定位的坐标显示在地图上
     * @param baiduLatLng
     */
    private void showLocPostionOnMap (LatLng baiduLatLng){
        //1. 设置坐标显示的图标信息
        MyLocationConfiguration config =
                new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.COMPASS,//显示位置罗盘态
                        true,//是否允许显示方向信息
                        BitmapDescriptorFactory.fromResource(R.mipmap.blue_marker)
                );
        //2. 设置显示的样式
        baiduMap.setMyLocationConfiguration(config);
        //设置定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .latitude(baiduLatLng.latitude)
                .longitude(baiduLatLng.longitude)
                .build();
        //设置定位位置
        baiduMap.setMyLocationData(locData);
        //3. 把地图移动到定位位置
        //创建MapStatusUpdate对象
        MapStatusUpdate statusUpdate =
                MapStatusUpdateFactory.newLatLng(baiduLatLng);
        //给地图应用当前状态更新
        baiduMap.animateMapStatus(statusUpdate);
    }

    private void zoomOperation() {
        //设置是否显示比例尺
        myMap.showZoomControls(true);


        //设置比例尺放大缩小的范围
        baiduMap.setMaxAndMinZoomLevel(19,13);


        //设置当前默认比例尺大小
        //1.创建一个地图状态更新对象
        MapStatusUpdate statusUpdate= MapStatusUpdateFactory.zoomTo(18);
        //更新
        baiduMap.setMapStatus(statusUpdate);
    }

    private void findViews() {
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