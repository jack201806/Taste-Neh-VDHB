package com.example.app.orderAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.app.Bean.UserProduct;
import com.example.app.R;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<UserProduct> userProductList;
    private int layout;

    public ProductAdapter(Context context, List<UserProduct> userProductList, int layout) {
        this.context = context;
        this.userProductList = userProductList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return userProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return userProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder  = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(layout,null);
            holder = new ViewHolder();
            //创建控件对象
            holder.productPhoto = view.findViewById(R.id.product_photo);
            holder.productName = view.findViewById(R.id.product_name);
            holder.productStandards = view.findViewById(R.id.product_standards);
            holder.productPrice = view.findViewById(R.id.product_price);
            holder.productNum = view.findViewById(R.id.product_num);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        UserProduct userProduct = userProductList.get(position);
        //给控件对象赋值
//        Bitmap bitmap = BitmapFactory.decodeFile(userProduct.getProductImg());
//        holder.productPhoto.setImageBitmap(bitmap);
        holder.productName.setText(userProduct.getProductName());
        holder.productStandards.setText(userProduct.getProductStandards());
        holder.productPrice.setText(String.format("%.2f",userProduct.getProductPrice())+"");
        holder.productNum.setText(userProduct.getProductNum()+"");
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
