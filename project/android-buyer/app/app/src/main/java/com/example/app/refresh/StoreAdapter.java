package com.example.app.refresh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.Bean.News;
import com.example.app.Bean.Store;
import com.example.app.R;

import java.util.List;

public class StoreAdapter extends BaseAdapter {
    private Context context;
    private List<Store> stores;
    private int layout;

    public StoreAdapter(Context context, int layout, List<Store> stores) {
        this.context = context;
        this.stores = stores;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return stores.size();
    }

    @Override
    public Object getItem(int position) {
        return stores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("lww", "getView");
        ViewHolder holder = null;
        if(null == convertView) {
            Log.i("lww", "内部");
            //加载item布局文件，赋值给convertView
            convertView = LayoutInflater.from(context)
                    .inflate(layout, null);
            holder = new ViewHolder();
            //创建Item中的控件对象
            holder.name = convertView.findViewById(R.id.name);
            holder.image = convertView.findViewById(R.id.image);
            //在convertView中缓存holder对象
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //给控件对象赋值
        Store store = stores.get(position);
        holder.name.setText(store.getName());
        holder.image.setImageResource(R.drawable.fish);
        return convertView;
    }
    static class ViewHolder{
        private TextView name;
        private ImageView image;
    }
}
