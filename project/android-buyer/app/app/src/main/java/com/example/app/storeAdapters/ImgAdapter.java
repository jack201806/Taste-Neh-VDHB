package com.example.app.storeAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.example.app.R;

import java.util.List;

public class ImgAdapter extends BaseAdapter {
    private Context mContext;
    private int layout;
    private List<Integer> imgId;

    public ImgAdapter(Context mContext, int layout, List<Integer> imgId) {
        this.mContext = mContext;
        this.layout = layout;
        this.imgId = imgId;
    }

    @Override
    public int getCount() {
        return imgId.size();
    }

    @Override
    public Object getItem(int i) {
        return imgId.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (null == holder){
            view = LayoutInflater.from(mContext).inflate(layout,null);
            holder = new ViewHolder();
            holder.img = view.findViewById(R.id.comment_img_item);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.img.setImageResource(imgId.get(i));
        return view;
    }
    static class ViewHolder{
        private ImageView img;
    }
}
