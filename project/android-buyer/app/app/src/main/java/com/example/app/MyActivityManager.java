package com.example.app;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

public class MyActivityManager{
    public static List<Activity> mActivites = new ArrayList<>();
    public static void addActivity(Activity act){ //将activity的实例放入list中进行维护
        mActivites.add(act);
    }
    public void removeActivity(Activity act){
        mActivites.remove(act);//将已经销毁的activity在list中清除，与实际的activity活动个数保持.一致
    }
    public static void finishAll(){
        for(Activity act:mActivites) {
            if (!act.isFinishing()) {//这里判断act是否正在销毁，如果没有就调用finish进行销毁
                act.finish();
            }
        }
    }
}

