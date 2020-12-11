package com.example.app.orderAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.Bean.SearchProduct;
import com.example.app.R;

import java.util.List;

public class SearchProductAdapter extends BaseAdapter {
    private int layout;
    private List<SearchProduct> productList;
    private Context mcontext;

    public SearchProductAdapter(int layout, List<SearchProduct> productList, Context mcontext) {
        this.layout = layout;
        this.productList = productList;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(holder == null){
            view = LayoutInflater.from(mcontext).inflate(layout,null);
            holder = new ViewHolder();
            holder.storePhoto = view.findViewById(R.id.store_header);
            holder.storeName = view.findViewById(R.id.store_name);
            holder.storeScore = view.findViewById(R.id.store_sale);
            holder.storeSale = view.findViewById(R.id.store_sale);
            holder.allowDelivery = view.findViewById(R.id.allow_delivery);
            holder.deliveryCast = view.findViewById(R.id.delivery_cast);
            holder.productName = view.findViewById(R.id.product_name);
            holder.productIntro = view.findViewById(R.id.product_intro);
            holder.productPrice = view.findViewById(R.id.product_price);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        SearchProduct searchProduct = productList.get(i);
//        holder.storePhoto.setImg
        holder.storeName.setText(searchProduct.getStoreName());
        holder.storeScore.setText(searchProduct.getStoreScore()+"");
        holder.storeSale.setText(searchProduct.getStoreSale()+"");
        holder.allowDelivery.setText(searchProduct.getAllowDelivery()+"");
        holder.deliveryCast.setText(searchProduct.getDeliveryCast()+"");
        holder.productName.setText(searchProduct.getProductName());
        holder.productIntro.setText(searchProduct.getProductIntro());
        holder.productPrice.setText(searchProduct.getProductPrice());
        return view;
    }
    static class ViewHolder{
        private ImageView storePhoto;
        private TextView storeName;
        private TextView storeScore;
        private TextView storeSale;
        private TextView allowDelivery;
        private TextView deliveryCast;
        private TextView productName;
        private TextView productIntro;
        private TextView productPrice;


    }
}
