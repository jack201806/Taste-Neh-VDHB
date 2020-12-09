package com.example.app.orderAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.Bean.Order;
import com.example.app.CommentActivity;
import com.example.app.R;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orderList;
    private int layout;

    public OrderAdapter(Context context, List<Order> orderList, int layout) {
        this.context = context;
        this.orderList = orderList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(null == view){
            view = LayoutInflater.from(context).inflate(layout,null);
            holder = new ViewHolder();
            //创建控件对象
            holder.storeHeader = view.findViewById(R.id.store_header);
            holder.storeName = view.findViewById(R.id.store_name);
            holder.orderState = view.findViewById(R.id.order_state);
            holder.lvOrders = view.findViewById(R.id.lv_orders);
            holder.orderId = view.findViewById(R.id.order_id);
            holder.orderTime = view.findViewById(R.id.order_time);
            holder.orderNum = view.findViewById(R.id.order_num);
            holder.orderAmount = view.findViewById(R.id.order_amount);
            holder.nextActionText = view.findViewById(R.id.next_action_text);
            holder.nextAction = view.findViewById(R.id.next_action);
            //在view中缓存holder对象
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        //给控件对象赋值
        Order order = orderList.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile(order.getStoreHeader());
        holder.storeHeader.setImageBitmap(bitmap);//本地文件中获取图片
        holder.storeName.setText(order.getStoreName());
        //点击店铺名称，前往店铺
        holder.storeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.orderState.setText(order.getOrderState());
        //加载订单里的商品列表
        ProductAdapter productAdapter = new ProductAdapter(context,order.getProducts(),R.layout.order_product_item);
        holder.lvOrders.setAdapter(productAdapter);
        setListViewHeightBasedOnChildren(holder.lvOrders);
        productAdapter.notifyDataSetChanged();
        holder.orderId.setText(order.getOrderId());
        holder.orderTime.setText(order.getOrderTime());
        holder.orderNum.setText(order.getOrderNum()+"");
        holder.orderAmount.setText(String.format("%.2f",order.getOrderAmount())+"");
        //根据订单状态确定按钮的内容和点击事件
        switch (order.getOrderState()){
            case "待付款":
                holder.nextActionText.setText("去付款");
                holder.nextAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case "待送达":
                holder.nextActionText.setText("确认送达");
                holder.nextAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orderList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                break;
            case "待评价":
                holder.nextActionText.setText("去评价");
                holder.nextAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CommentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        context.startActivity(intent);
                        orderList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                break;
            case "已完成":
                holder.nextActionText.setText("再来一单");
                holder.nextAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
        }
        return view;
    }

    private void setListViewHeightBasedOnChildren(ListView lvOrders) {
        //获得adapter
        ProductAdapter adapter = (ProductAdapter) lvOrders.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, lvOrders);
            listItem.measure(0, 0);
            //计算总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = lvOrders.getLayoutParams();
        //计算分割线高度
        params.height = totalHeight + (lvOrders.getDividerHeight() * (adapter.getCount() - 1));
        //给listview设置高度
        lvOrders.setLayoutParams(params);
    }

    static class ViewHolder{
        private ImageView storeHeader;
        private TextView storeName;
        private TextView orderState;
        private ListView lvOrders;
        private TextView orderId;
        private TextView orderTime;
        private TextView orderNum;
        private TextView orderAmount;
        private TextView nextActionText;
        private LinearLayout nextAction;
    }
}
