package com.example.app.refresh;

import android.content.Context;
import android.media.Image;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.app.Bean.News;
import com.example.app.Bean.Store;
import com.example.app.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<News> newsList;
    private int layout;

    public NewsAdapter(Context context, int layout, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.i("lww", "getView");
        ViewHolder holder = null;
        if(null == convertView) {
            Log.i("lww", "内部");
            //加载item布局文件，赋值给convertView
            convertView = LayoutInflater.from(context)
                    .inflate(layout, null);
            holder = new ViewHolder();
            //创建Item中的控件对象
            holder.title = convertView.findViewById(R.id.tittle);
            holder.play=convertView.findViewById(R.id.play);
            //在convertView中缓存holder对象
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //给控件对象赋值
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.play.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                //1.先创建SoundPool构造器对象
                SoundPool.Builder builder = new SoundPool.Builder();
                //可选，设置允许容纳的最大音效流
                builder.setMaxStreams(10);
                //2.通过SoundPool构造器对象创建SoundPool对象
                SoundPool soundPool = builder.build();
                //3.加载音效文件，并保存其返回的音效id
                    int id = context.getResources().getIdentifier(
                            "yp" +1,
                            "raw",
                            context.getPackageName()
                    );
                    //加载音效文件
                    int soundId = soundPool.load(
                            context,
                            id,
                            1);
                //4.给SoundPool对象注册加载音效完成事件监听器
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                            soundPool.play(
                                    1,
                                    1,
                                    1,
                                    1,
                                    0,
                                    1);
                    }
                });
            }
        });
        return convertView;
    }
    static class ViewHolder{
        private TextView title;
        private LinearLayout play;
    }
}
