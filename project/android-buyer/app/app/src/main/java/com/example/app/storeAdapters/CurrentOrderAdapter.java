package com.example.app.storeAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.vo.RightVo;

import java.util.List;

public class CurrentOrderAdapter extends BaseAdapter {
    private Context mContext;
    private int layout;
    private List<RightVo> orderProductList;

    public CurrentOrderAdapter(Context mContext, int layout, List<RightVo> orderProductList) {
        this.mContext = mContext;
        this.layout = layout;
        this.orderProductList = orderProductList;
    }

    @Override
    public int getCount() {
        return orderProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(layout,null);
            holder = new ViewHolder();
            //创建控件对象
            holder.productPhoto = view.findViewById(R.id.product_photo);
            holder.productName = view.findViewById(R.id.product_name);
            holder.productStandards = view.findViewById(R.id.product_standards);
            holder.productPrice = view.findViewById(R.id.product_price);
            holder.productNum = view.findViewById(R.id.product_num);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        RightVo rightVo = orderProductList.get(position);
        holder.productName.setText(rightVo.getProductName());
        holder.productStandards.setText(rightVo.getProductIntro());
        holder.productPrice.setText(String.format("%.2f",rightVo.getProductPrice())+"");
        holder.productNum.setText(rightVo.getProductNum()+"");
        return view;
    }
    static class ViewHolder{
        private ImageView productPhoto;
        private TextView productName;
        private TextView productStandards;
        private TextView productPrice;
        private TextView productNum;
    }
}
