package com.example.app.orderAdapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
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
    private List<Uri>datas;
    private Dialog dialog;
    private ImageView mImageView;

    public ImgAdapter(Context mContext, int layout, List<Uri> datas) {
        this.mContext = mContext;
        this.layout = layout;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
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
        holder.img.setImageURI(datas.get(i));
        dialog = new Dialog(mContext, R.style.Image_AlertDialog_style);
        dialog.setContentView(R.layout.my_dialog);
        mImageView = dialog.findViewById(R.id.my_image);
        mImageView.setImageURI(datas.get(i));
        //大图的点击事件（点击让他消失）
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return view;
    }

    static class ViewHolder{
        private ImageView img;
    }
}
