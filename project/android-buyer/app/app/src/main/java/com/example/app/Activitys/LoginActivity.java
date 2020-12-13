package com.example.app.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.Bean.User;
import com.example.app.MainActivity;
import com.example.app.MyActivityManager;
import com.example.app.R;
import com.example.app.StartActivity;
import com.example.app.fragments.MyFragment;
import com.example.app.utils.ConfigUtil;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //  定义各组件
    private ImageView close_activity;
    private EditText username_login;
    private EditText pwd_login;
    private ImageView clear_text_login;
    private ImageView clear_pwd_login;
    private ImageView pwd_status_login;
    private boolean pwd_visible = false;//判断密码是否可见的变量
    private long exitTime;//用户点击返回按钮的时间

    //检查是否深色模式
    private static Configuration configuration;

    //自定义toast样式
    private View my_toast;
    private Toast toast;
    private TextView tv;

    //检测登录状态
    private String str;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1://表示接收服务端的字符串
                    Log.e("Handler接受的信息",(String) msg.obj);
                    str = (String) msg.obj;
                    Log.e("现在str的值",str);
                    //跳转登录
                    toast(str);
                    //把收到的字符串显示在文本框汇总
                    break;
            }
        }

    };

    private void toast(String str) {
        Log.e("现在str的值（toast方法）",str);
        if (str.contains("登录成功")) {
            showToast("登录成功");
            Gson gson = new Gson();
            String user_info = str.substring(str.indexOf(":")+1);
            User user = gson.fromJson(user_info,User.class);
            //  向SharedPreferences保存用户信息
            saveUsersToSP(user.getId());
            Intent intent = new Intent(this, MyFragment.class);
            intent.putExtra("userName",user.getUsername());
            intent.putExtra("userPhone",user.getPhone());
            setResult(11, intent);
            finish();
        }else if (str.contains("网络连接错误")){
            showToast("网络连接错误");
        }else {
            showToast("登录失败，用户名或密码错误");
        }
    }

    private void saveUsersToSP(final int id) {
        //1 获取SharedPreferences对象
        SharedPreferences userSp = getSharedPreferences(
                "cake_userinfo", MODE_PRIVATE);
        //2 获取Editor对象
        SharedPreferences.Editor editor = userSp.edit();
        //3 向SharedPreferences中添加数据
        String username = username_login.getText().toString().trim();
        String pwd = pwd_login.getText().toString().trim();
        editor.putInt("id",id);
        editor.putString("username",username);
        editor.putString("pwd",pwd);
        //4 提交修改
        editor.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //  检测系统是否深色模式
        Window window = getWindow();
        configuration = getResources().getConfiguration();
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        //透明化通知栏（必须Android 5.0以上才可以！！！）
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            switch (currentNightMode){
                case Configuration.UI_MODE_NIGHT_YES:
                    Log.i("信息","深色模式");
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(android.R.color.black));
                    window.setNavigationBarColor(Color.rgb(0,220,220));
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    Log.i("信息","不是深色模式");
                    /**
                     * 想实现白底黑字的通知栏，必须写这句话：Window window = getWindow();
                     * 然后写上下方3句话，注意开头一定是window....，而不能是this.getWindow....
                     * 否则不能达到预期效果，本人亲测如此
                     */
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.rgb(253,170,28));
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    window.setNavigationBarColor(getResources().getColor(android.R.color.white));
                    break;
            }
        }
        findViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        Log.i("信息",currentNightMode+"");
        Window window = getWindow();
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(android.R.color.black));
                window.setNavigationBarColor(Color.rgb(0,220,220));
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(android.R.color.white));
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.setNavigationBarColor(Color.rgb(0,220,220));
                break;
        }
    }

    private void findViews() {
        close_activity = findViewById(R.id.close_activity);
        username_login = findViewById(R.id.username_login);
        pwd_login = findViewById(R.id.pwd_login);
        clear_text_login = findViewById(R.id.clear_text_login);
        clear_pwd_login = findViewById(R.id.clear_pwd_login);
        pwd_status_login = findViewById(R.id.pwd_status_login);
        //  使用自定义Toast
        my_toast = getLayoutInflater().inflate(R.layout.my_toast,null);
        toast = new Toast(getApplicationContext());
        toast.setView(my_toast);
        tv = my_toast.findViewById(R.id.toast_info);
        //  为EditText注册文本监听器（必须写！！！否则就是检测不到）
        username_login.addTextChangedListener(watcher);
        pwd_login.addTextChangedListener(watcher);
        //注册监听器（必须写！！！否则就是按不动）
        clear_text_login.setOnClickListener(this);
        clear_pwd_login.setOnClickListener(this);
        pwd_status_login.setOnClickListener(this);
    }


    public void loginOperation(View view) {
        if (username_login.length()>0 && pwd_login.length()>0){
            String username = username_login.getText().toString();
            String password = pwd_login.getText().toString();
            //  构造用户Json串（买家版role为4）
            String json = "{\'username':\'"+username+"\',\'pwd\':\'"+password+"\'}";
            //  传输数据
            loginToServlet(json);
        }else if (username_login.length()==0){
            showToast("请输入用户名");
        }else {
            showToast("请输入密码");
        }
    }

    private void loginToServlet(final String json) {
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
                    Log.e("登录信息",json);
                    String data = "user="+json;
                    out.write(data.getBytes());
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

    //  该按钮关闭当前Activity，放弃注册或登录操作
    public void closeActivity(View view) {
        MyActivityManager.finishAll();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("code",1);
        startActivity(intent);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pwd_status_login:
                if (!pwd_visible){
                    pwd_status_login.setSelected(true);
                    pwd_visible = true;
                    pwd_status_login.setImageResource(R.drawable.pwd_visible);
                    //设置密码可见
                    pwd_login.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    pwd_status_login.setSelected(false);
                    pwd_visible = false;
                    pwd_status_login.setImageResource(R.drawable.pwd_invisible);
                    //设置密码不可见
                    pwd_login.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.clear_text_login:
                username_login.setText("");
                Log.e("状态（用户名）","清空完毕");
                break;
            case R.id.clear_pwd_login:
                pwd_login.setText("");
                Log.e("状态（密码）","清空完毕");
                break;
        }
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (username_login.getEditableText().length()>=1){
                clear_text_login.setVisibility(View.VISIBLE);
            }else {
                clear_text_login.setVisibility(View.INVISIBLE);
            }
            if (pwd_login.getEditableText().length()>=1){
                clear_pwd_login.setVisibility(View.VISIBLE);
            }else {
                clear_pwd_login.setVisibility(View.INVISIBLE);
            }
        }
    };

    //定义showToast方法
    private void showToast(String str) {
        tv.setText(str);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    //按下任意按键时会自动调用
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户连续两次点击返回按钮，时间间隔小于2s时，退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK){//判断是否点击了返回按钮
            if ((System.currentTimeMillis() - exitTime) > 2000){
                showToast("再次点击将返回");
                exitTime = System.currentTimeMillis();//获取当前时间的毫秒数
            } else {
                this.finish();//时间间隔小于2s，退出程序
            }
        }
        return true;
    }
}