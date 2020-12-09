package com.example.app.storeFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.app.CenterLayoutManager;
import com.example.app.ProductItem;
import com.example.app.R;
import com.example.app.data.DataUtil;
import com.example.app.storeAdapters.CurrentOrderAdapter;
import com.example.app.storeAdapters.LeftAdapter;
import com.example.app.storeAdapters.RightAdapter;
import com.example.app.vo.LeftVo;
import com.example.app.vo.RightVo;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {
    private RecyclerView mRvLeft, mRvRight;
    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;
    private TextView orderAmount;
    private TextView deliveryCost;
    private LinearLayout nextAction;
    private TextView nextActionText;
    private RelativeLayout currentOrder;
    public static List<RightVo> rightData;
    public static List<RightVo> orderProductList;
    private double allowDelivery;
    public static double totalOrderAmount = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product,container,false);
        findView(view);
        orderProductList = new ArrayList<>();
        allowDelivery = 10.0;
        orderAmount.setText("未选购任何商品");
        nextActionText.setText("¥"+allowDelivery+"起送");
        deliveryCost.setText("1");
        orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        orderAmount.setTextColor(Color.parseColor("#A9A9A9"));
        currentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderProductList.size() != 0) {
                    Dialog bgSetDialog = new Dialog(getContext(), R.style.OrderDialogStyle);
                    view = LayoutInflater.from(getContext()).inflate(R.layout.pop_order_more, null);
                    ListView listView = view.findViewById(R.id.current_order_list);
                    TextView popOrderAmount = view.findViewById(R.id.order_amount);
                    LinearLayout popNextAction = view.findViewById(R.id.next_action);
                    TextView popNextActionText = view.findViewById(R.id.next_action_text);
                    TextView popDeliveryCost = view.findViewById(R.id.delivery_cost);
                    popDeliveryCost.setText("1");
                    if (String.format("%.2f", totalOrderAmount).equals("0.00")){
                        popOrderAmount.setText("未选购任何商品");
                        popOrderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                        popOrderAmount.setTextColor(Color.parseColor("#A9A9A9"));
                        popNextActionText.setText("¥"+allowDelivery+"起送");
                        popNextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                    }else{
                        if (allowDelivery <= totalOrderAmount){//订单金额大于等于于起送金额
                            popNextActionText.setText("结算");
                            popNextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape_changed));
                        }else{//订单金额小于于起送金额
                            popNextActionText.setText("差¥"+String.format("%.2f",(allowDelivery-totalOrderAmount))+"起送");
                            popNextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                        }
                        popOrderAmount.setText("¥"+String.format("%.2f", totalOrderAmount)+"");
                        popOrderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                        popOrderAmount.setTextColor(Color.parseColor("#000000"));
                    }
                    CurrentOrderAdapter currentOrderAdapter = new CurrentOrderAdapter(getContext(), R.layout.pop_order_item, orderProductList);
                    listView.setAdapter(currentOrderAdapter);
                    //将布局设置给Dialog
                    bgSetDialog.setContentView(view);
                    //获取当前Activity所在的窗体
                    Window dialogWindow = bgSetDialog.getWindow();
                    setListViewHeightBasedOnChildren(listView);
                    dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    //设置Dialog从窗体底部弹出
                    dialogWindow.setGravity( Gravity.BOTTOM);
                    bgSetDialog.show();//显示对话框
                }
            }
        });
        initLeftData();
        initRightData();
        initListener();
        return view;
    }
    private void setListViewHeightBasedOnChildren(ListView lvOrders) {
        //获得adapter
        CurrentOrderAdapter adapter = (CurrentOrderAdapter) lvOrders.getAdapter();
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
    private void findView(View view) {
        currentOrder = view.findViewById(R.id.current_order);
        mRvLeft = view.findViewById(R.id.rv_left);
        mRvRight = view.findViewById(R.id.rv_right);
        orderAmount = view.findViewById(R.id.order_amount);
        deliveryCost = view.findViewById(R.id.delivery_cost);
        nextAction = view.findViewById(R.id.next_action);
        nextActionText = view.findViewById(R.id.next_action_text);
    }

    /**
     * 初始化左侧数据
     */
    private void initLeftData() {
        try {
            List<LeftVo> leftData = DataUtil.getLeftData(getContext());
            mLeftAdapter = new LeftAdapter(getContext());
            mRvLeft.setLayoutManager(new CenterLayoutManager(getContext()));
            mRvLeft.setAdapter(mLeftAdapter);
            mLeftAdapter.addData(leftData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化右侧数据
     */
    private void initRightData() {
        try {
            rightData = DataUtil.getRightData(getContext());
            mRightAdapter = new RightAdapter(getContext());
            mRvRight.setLayoutManager(new GridLayoutManager(getContext(), 3));
            mRvRight.setAdapter(mRightAdapter);
            mRightAdapter.addData(rightData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        mLeftAdapter.setOnItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //左侧滑动到中间
                mRvLeft.smoothScrollToPosition(position);
                //左侧刷新状态
                mLeftAdapter.notifyGlobal(position);
                //右侧滑动到相应位置
                GridLayoutManager layoutManager = (GridLayoutManager) mRvRight.getLayoutManager();
                if (layoutManager != null) {
                    layoutManager.scrollToPositionWithOffset(DataUtil.getTitlePosSa().get(position), 0);
                }
            }
        });
        mRightAdapter.setXmOnItemDeleteListener(new RightAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(final int position) {
                final View v = mRvRight.getLayoutManager().findViewByPosition(position);
                ImageView addProduct = v.findViewById(R.id.add_product);
                final TextView productNum = v.findViewById(R.id.product_num);
                final ImageView subtractProduct = v.findViewById(R.id.subtract_product);
                addProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(rightData.get(position).getProductNum() == 0){
                            subtractProduct.setVisibility(View.VISIBLE);
                            productNum.setVisibility(View.VISIBLE);
                            rightData.get(position).setProductNum(rightData.get(position).getProductNum()+1);
                            productNum.setText(rightData.get(position).getProductNum()+"");
                            orderProductList.add(rightData.get(position));
                            rightData.get(position).setOrderAmount(rightData.get(position).getProductNum()*rightData.get(position).getProductPrice());
                            totalOrderAmount += rightData.get(position).getProductPrice();
                            if (allowDelivery < totalOrderAmount){//订单金额大于等于起送金额
                                nextActionText.setText("结算");
                                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape_changed));
                            }else{//订单金额小于起送金额
                                nextActionText.setText("差¥"+String.format("%.2f",allowDelivery-totalOrderAmount)+"起送");
                                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                            }
                            orderAmount.setText("¥"+String.format("%.2f", totalOrderAmount)+"");
                            orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                            orderAmount.setTextColor(Color.parseColor("#000000"));
                        }else{
                            if (subtractProduct.getVisibility() == View.INVISIBLE){
                                subtractProduct.setVisibility(View.VISIBLE);
                            }
                            if (productNum.getVisibility() == View.INVISIBLE){
                                productNum.setVisibility(View.VISIBLE);
                            }
                            orderProductList.remove(orderProductList.indexOf(rightData.get(position)));
                            rightData.get(position).setProductNum(rightData.get(position).getProductNum()+1);
                            productNum.setText(rightData.get(position).getProductNum()+"");
                            rightData.get(position).setOrderAmount(rightData.get(position).getProductNum()*rightData.get(position).getProductPrice());
                            orderProductList.add(rightData.get(position));
                            Log.e("ykd",rightData.get(position).toString());
                            totalOrderAmount += rightData.get(position).getProductPrice();
                            if (allowDelivery < totalOrderAmount){//订单金额大于等于起送金额
                                nextActionText.setText("结算");
                                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape_changed));
                            }else{//订单金额小于起送金额
                                nextActionText.setText("差¥"+String.format("%.2f",(allowDelivery-totalOrderAmount))+"起送");
                                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                            }
                            orderAmount.setText("¥"+String.format("%.2f", totalOrderAmount)+"");
                            orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                            orderAmount.setTextColor(Color.parseColor("#000000"));
                        }
                        Log.e("orders",orderProductList.toString());
                    }
                });
                subtractProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(rightData.get(position).getProductNum() == 1){
                            subtractProduct.setVisibility(View.INVISIBLE);
                            productNum.setVisibility(View.INVISIBLE);
                            rightData.get(position).setProductNum(0);
                            rightData.get(position).setOrderAmount(0);
                            totalOrderAmount -= rightData.get(position).getProductPrice();
                            orderProductList.remove(orderProductList.indexOf(rightData.get(position)));
                            Log.e("total",String.format("%.2f", totalOrderAmount));
                            if (String.format("%.2f", totalOrderAmount).equals("0.00")){
                                orderAmount.setText("未选购任何商品");
                                orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                                orderAmount.setTextColor(Color.parseColor("#A9A9A9"));
                                nextActionText.setText("¥"+allowDelivery+"起送");
                                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                            }else{
                                if (allowDelivery <= totalOrderAmount){//订单金额大于等于于起送金额
                                    nextActionText.setText("结算");
                                    nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape_changed));
                                }else{//订单金额小于于起送金额
                                    nextActionText.setText("差¥"+String.format("%.2f",(allowDelivery-totalOrderAmount))+"起送");
                                    nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                                }
                                orderAmount.setText("¥"+String.format("%.2f", totalOrderAmount)+"");
                                orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                                orderAmount.setTextColor(Color.parseColor("#000000"));
                            }
                        }else{
                            orderProductList.remove(orderProductList.indexOf(rightData.get(position)));
                            rightData.get(position).setProductNum(rightData.get(position).getProductNum()-1);
                            productNum.setText(rightData.get(position).getProductNum()+"");
                            rightData.get(position).setOrderAmount(rightData.get(position).getProductNum()*rightData.get(position).getProductPrice());
                            orderProductList.add(rightData.get(position));
                            totalOrderAmount -= rightData.get(position).getProductPrice();
                            if (allowDelivery <= totalOrderAmount){//订单金额大于等于于起送金额
                                nextActionText.setText("结算");
                                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape_changed));
                            }else{//订单金额小于于起送金额
                                nextActionText.setText("差¥"+String.format("%.2f",(allowDelivery-totalOrderAmount))+"起送");
                                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                            }
                            orderAmount.setText("¥"+String.format("%.2f", totalOrderAmount)+"");
                            orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                            orderAmount.setTextColor(Color.parseColor("#000000"));
                        }
                        Log.e("orders",orderProductList.toString());
                    }
                });
            }
        });

        mRightAdapter.setOnItemClickListener(new RightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), ProductItem.class);
                RightVo rightVo = rightData.get(position);
                Log.e("cook1",rightVo.toString());
                intent.putExtra("position",position);
                intent.putExtra("deliveryCast",1);
                intent.putExtra("allowDelivery",allowDelivery);
                startActivityForResult(intent,200);
            }
        });


        mRvRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItemPosition;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //左侧滑动到中间,等滑动停止再操作,防止卡顿
                    int position = mRightAdapter.getDatas().get(firstVisibleItemPosition).getFakePosition();
                    mRvLeft.smoothScrollToPosition(position);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) mRvRight.getLayoutManager();
                if (layoutManager != null) {
                    //左侧刷新状态
                    firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    int position = mRightAdapter.getDatas().get(firstVisibleItemPosition).getFakePosition();
                    mLeftAdapter.notifyGlobal(position);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200&& resultCode == 100){
            int position = data.getIntExtra("backPosition",0);
            View v = mRvRight.getLayoutManager().findViewByPosition(position);
            TextView productNum = v.findViewById(R.id.product_num);
            ImageView subtractProduct = v.findViewById(R.id.subtract_product);
            if (rightData.get(position).getProductNum() != 0){
                subtractProduct.setVisibility(View.VISIBLE);
                productNum.setVisibility(View.VISIBLE);
                productNum.setText(rightData.get(position).getProductNum()+"");
            }else{
                subtractProduct.setVisibility(View.INVISIBLE);
                productNum.setVisibility(View.INVISIBLE);
            }
            if (String.format("%.2f", totalOrderAmount).equals("0.00")){
                orderAmount.setText("未选购任何商品");
                orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                orderAmount.setTextColor(Color.parseColor("#A9A9A9"));
                nextActionText.setText("¥"+allowDelivery+"起送");
                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
            }else{
                if (allowDelivery <= totalOrderAmount){//订单金额大于等于于起送金额
                    nextActionText.setText("结算");
                    nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape_changed));
                }else{//订单金额小于于起送金额
                    nextActionText.setText("差¥"+String.format("%.2f",(allowDelivery-totalOrderAmount))+"起送");
                    nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                }
                orderAmount.setText("¥"+String.format("%.2f", totalOrderAmount)+"");
                orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                orderAmount.setTextColor(Color.parseColor("#000000"));
            }
        }
    }
}
