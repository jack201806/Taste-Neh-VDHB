package com.example.app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.app.Activitys.VersionActivity;
import com.example.app.Activitys.CarActivity;
import com.example.app.Activitys.IntroduceActivity;
import com.example.app.Activitys.LoginActivity;
import com.example.app.Activitys.OrderActivity;
import com.example.app.Details.Detail;
import com.example.app.MyActivityManager;
import com.example.app.R;


public class MyFragment extends Fragment {

    private View root;

    //自定义toast样式
    private View my_toast;
    private Toast toast;
    private TextView tv;

    private RelativeLayout login;
    private ImageView userHead;
    private TextView userName;
    private TextView userPhone;
    private LinearLayout myOrder;
    private LinearLayout myCar;
    private LinearLayout myVersion;
    private LinearLayout ourIntroduce;
    private LinearLayout myCount;
    //头像地址
    private String url;
    private Bitmap bitmap;

    //登陆状态
    private static boolean ifLogin = false;

    public MyFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        if (root==null){
            root = inflater.inflate(R.layout.fragment_my, container, false);
            login=root.findViewById(R.id.login);
            userHead=root.findViewById(R.id.user_head);
            userName=root.findViewById(R.id.user_name);
            userPhone=root.findViewById(R.id.user_phone);
            myOrder=root.findViewById(R.id.my_order);
            myCar=root.findViewById(R.id.my_car);
            myVersion=root.findViewById(R.id.my_version);
            ourIntroduce=root.findViewById(R.id.our_introduce);
            myCount=root.findViewById(R.id.my_count);
            Glide.with(getContext()).load(R.raw.header).circleCrop().into(userHead);
            findViews();
        }
        ViewGroup parent = (ViewGroup) root.getParent();
        if (parent!=null){
            parent.removeView(root);
        }
        //设置监听器事件
        setListeners();
        return root;
    }




    private void setListeners() {
        MyListener1 listener=new MyListener1();
        myOrder.setOnClickListener(listener);
        myCar.setOnClickListener(listener);
        myVersion.setOnClickListener(listener);
        ourIntroduce.setOnClickListener(listener);
        myCount.setOnClickListener(listener);
        userHead.setOnClickListener(listener);
        login.setOnClickListener(listener);

    }

    class MyListener1 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.login:
                        if(!ifLogin){
                            Intent i=new Intent(getActivity(),LoginActivity.class);
                            startActivityForResult(i,100);
                        }
                        break;
                    case R.id.my_order:

                        if(ifLogin){
                            Log.e("lll","登录");
                            Intent intent1=new Intent(getActivity(), OrderActivity.class);
                            startActivity(intent1);
                        }
                        else{
                            Log.e("lll","未登录");
                            Intent i=new Intent(getActivity(),LoginActivity.class);
                            startActivityForResult(i,100);
                        }
                        break;
                    case R.id.my_car:
                        if(ifLogin){
                            Intent intent2=new Intent(getActivity(), CarActivity.class);
                            startActivity(intent2);
                        }
                        else{
                            Intent i1=new Intent(getActivity(),LoginActivity.class);
                            startActivityForResult(i1,100);
                        }
                        break;
                    case R.id.my_version:
                            Intent intent4=new Intent(getActivity(), VersionActivity.class);
                            startActivity(intent4);
                        break;
                    case R.id.our_introduce:
                            Intent intent3=new Intent(getActivity(), IntroduceActivity.class);
                            startActivity(intent3);
                        break;
                    case R.id.my_count:
                        if(ifLogin){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setCancelable(true);
                            AlertDialog dialog = null;
                            builder.setTitle("退出后将不能查看订单，确定退出吗？")
                                    .setItems(new String[]{"取消","确定"},new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which){
                                                case 1:
                                                    MyActivityManager.finishAll();
                                                    //当前登陆状态设置为未登录
                                                    ifLogin=false;
                                                    Intent intent5=new Intent(getActivity(), LoginActivity.class);
                                                    startActivity(intent5);
                                                    break;
                                            }
                                        }
                                    });
                            dialog = builder.create();
                            dialog.show();
                        }
                        else{
                            Intent i4=new Intent(getActivity(),LoginActivity.class);
                            startActivityForResult(i4,100);
                        }
                        break;
                    case R.id.user_head:
                        if (ifLogin){
                            Intent intent=new Intent(getActivity(), Detail.class);
                            intent.putExtra("url",url);
                            intent.putExtra("name",userName.getText().toString());
                            intent.putExtra("phone",userPhone.getText().toString());
                            startActivityForResult(intent,50);
                        }
                        break;
                }
        }
    }
    private void findViews() {
        //使用自定义Toast
        my_toast = getLayoutInflater().inflate(R.layout.my_toast,null);
        toast = new Toast(this.getContext());
        toast.setView(my_toast);
        tv = my_toast.findViewById(R.id.toast_info);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==11 ){
            ifLogin=true;
            userName.setText(data.getStringExtra("userName"));
            userPhone.setText(data.getStringExtra("userPhone"));
            url="http://m.imeitou.com/uploads/allimg/2020100609/yjc23pars01.jpg";
            Glide.with(getContext()).load(url).circleCrop().into(userHead);
        }
        if (requestCode==50 && resultCode==51 ){
            ifLogin=true;
            userName.setText(data.getStringExtra("userName"));
            userPhone.setText(data.getStringExtra("userPhone"));
            byte buf[] = data.getByteArrayExtra("userHead");
            bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length);
            Glide.with(getContext()).load(bitmap).circleCrop().into(userHead);
        }
    }
    //定义showToast方法
    private void showToast(String str) {
        tv.setText(str);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}