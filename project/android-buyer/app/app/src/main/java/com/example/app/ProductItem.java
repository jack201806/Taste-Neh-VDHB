package com.example.app;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.storeAdapters.CurrentOrderAdapter;
import com.example.app.vo.RightVo;
import com.jaeger.library.StatusBarUtil;

import static com.example.app.storeFragment.ProductsFragment.orderProductList;
import static com.example.app.storeFragment.ProductsFragment.totalOrderAmount;
import static com.example.app.storeFragment.ProductsFragment.rightData;


public class ProductItem extends AppCompatActivity {
    private ImageView productImg;
    private TextView productName;
    private TextView productPrice;
    private ImageView addProduct;
    private ImageView subtractProduct;
    private TextView productIntro;
    private TextView productNum;
    private TextView orderAmount;
    private LinearLayout nextAction;
    private TextView nextActionText;
    private TextView deliveryCast;
    private RightVo product;
    private double allowDelivery;
    private RelativeLayout currentOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item);
        StatusBarUtil.setTranslucent(this, Color.parseColor("#ff0000"));
        findView();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        intent.putExtra("backPosition",position);
        product = rightData.get(position);
        Log.e("cook2",product.toString());
        allowDelivery = intent.getDoubleExtra("allowDelivery",0.0);
        productImg.setImageResource(R.drawable.fish);
        productName.setText(product.getProductName());
        productPrice.setText(product.getProductPrice()+"");
        nextActionText.setText("¥"+allowDelivery+"起送");
        productIntro.setText(product.getProductIntro());
        if (totalOrderAmount == 0){
            orderAmount.setText("未选购任何商品");
            orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            orderAmount.setTextColor(Color.parseColor("#A9A9A9"));
        }else{
            orderAmount.setText("¥"+String.format("%.2f", totalOrderAmount)+"");
            orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            orderAmount.setTextColor(Color.parseColor("#000000"));
        }
        if (product.getProductNum() !=0){
            subtractProduct.setVisibility(View.VISIBLE);
            productNum.setText(product.getProductNum()+"");
            productNum.setVisibility(View.VISIBLE);
        }
        deliveryCast.setText(intent.getIntExtra("deliveryCast",0)+"");
        setListener();
        setResult(100,intent);
//        finish();
    }

    private void setListener() {
        MyListener myListener = new MyListener();
        addProduct.setOnClickListener(myListener);
        subtractProduct.setOnClickListener(myListener);
        nextAction.setOnClickListener(myListener);
        currentOrder.setOnClickListener(myListener);
    }

    private void findView() {
        productImg = findViewById(R.id.product_img);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        addProduct = findViewById(R.id.add_product);
        subtractProduct = findViewById(R.id.subtract_product);
        productIntro = findViewById(R.id.product_intro);
        productNum = findViewById(R.id.product_num);
        orderAmount = findViewById(R.id.order_amount);
        nextAction = findViewById(R.id.next_action);
        nextActionText = findViewById(R.id.next_action_text);
        deliveryCast = findViewById(R.id.delivery_cost);
        currentOrder = findViewById(R.id.current_order);
    }
    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                //增加商品数量
                case R.id.add_product:
                    if(product.getProductNum() == 0){
                        subtractProduct.setVisibility(View.VISIBLE);
                        productNum.setVisibility(View.VISIBLE);
                        product.setProductNum(product.getProductNum()+1);
                        productNum.setText(product.getProductNum()+"");
                        product.setOrderAmount(product.getProductNum()*product.getProductPrice());
                        orderProductList.add(product);
                        totalOrderAmount += product.getProductPrice();
                        orderAmount.setText("¥"+String.format("%.2f", totalOrderAmount)+"");
                        orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                        orderAmount.setTextColor(Color.parseColor("#000000"));
                        if (allowDelivery < totalOrderAmount){//订单金额大于等于起送金额
                            nextActionText.setText("结算");
                            nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape_changed));
                        }else{//订单金额小于起送金额
                            nextActionText.setText("差¥"+String.format("%.2f",allowDelivery-totalOrderAmount)+"起送");
                            nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                        }
                    }else{
                        orderProductList.remove(orderProductList.indexOf(product));
                        product.setProductNum(product.getProductNum()+1);
                        productNum.setText(product.getProductNum()+"");
                        product.setOrderAmount(product.getProductNum()*product.getProductPrice());
                        orderProductList.add(product);
                        totalOrderAmount += product.getProductPrice();
                        orderAmount.setText("¥"+String.format("%.2f", totalOrderAmount)+"");
                        if (allowDelivery < totalOrderAmount){//订单金额大于等于起送金额
                            nextActionText.setText("结算");
                            nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape_changed));
                        }else{//订单金额小于起送金额
                            nextActionText.setText("差¥"+(allowDelivery-totalOrderAmount)+"起送");
                            nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                        }
                    }
                    break;
                //减少商品数量
                case R.id.subtract_product:
                    if(product.getProductNum() == 1){
                        subtractProduct.setVisibility(View.INVISIBLE);
                        productNum.setVisibility(View.INVISIBLE);
                        product.setProductNum(product.getProductNum()-1);
                        productNum.setText(product.getProductNum()+"");
                        totalOrderAmount -= product.getProductPrice();
                        orderProductList.remove(orderProductList.indexOf(product));
                        orderProductList.remove(product);
                        product.setOrderAmount(0);
                        if (totalOrderAmount <= 0){
                            orderAmount.setText("未选购任何商品");
                            orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                            orderAmount.setTextColor(Color.parseColor("#A9A9A9"));
                            nextActionText.setText("¥"+allowDelivery+"起送");
                            nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                        }else{
                            if (String.format("%.2f", totalOrderAmount).equals("0.00")){//订单金额大于等于于起送金额
                                nextActionText.setText("结算");
                                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape_changed));
                            }else{//订单金额小于于起送金额
                                nextActionText.setText("差¥"+String.format("%.2f",(allowDelivery-totalOrderAmount))+"起送");
                                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                            }
                            orderAmount.setText("¥"+String.format("%.2f", totalOrderAmount)+"");
                        }
                    }else{
                        orderProductList.remove(orderProductList.indexOf(product));
                        product.setProductNum(product.getProductNum()-1);
                        productNum.setText(product.getProductNum()+"");
                        product.setOrderAmount(product.getProductNum()*product.getProductPrice());
                        orderProductList.add(product);
                        totalOrderAmount -= product.getProductPrice();
                        if (String.format("%.2f", totalOrderAmount).equals("0.00")){
                            orderAmount.setText("未选购任何商品");
                            orderAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                            orderAmount.setTextColor(Color.parseColor("#A9A9A9"));
                            nextActionText.setText("¥"+allowDelivery+"起送");
                            nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                        }else{
                            if (allowDelivery < totalOrderAmount){//订单金额大于等于于起送金额
                                nextActionText.setText("结算");
                                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape_changed));
                            }else{//订单金额小于于起送金额
                                nextActionText.setText("差¥"+String.format("%.2f",(allowDelivery-totalOrderAmount))+"起送");
                                nextAction.setBackground(getResources().getDrawable(R.drawable.circle_shape2));
                            }
                            orderAmount.setText("¥"+String.format("%.2f", totalOrderAmount)+"");
                        }
                    }
                    break;
                //下一步操作
                case R.id.next_action:
                    break;
                case R.id.current_order:
                    if (orderProductList.size() != 0) {
                        Dialog bgSetDialog = new Dialog(ProductItem.this, R.style.OrderDialogStyle);
                        view = LayoutInflater.from(ProductItem.this).inflate(R.layout.pop_order_more, null);
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
                        CurrentOrderAdapter currentOrderAdapter = new CurrentOrderAdapter(ProductItem.this, R.layout.pop_order_item, orderProductList);
                        listView.setAdapter(currentOrderAdapter);
                        //将布局设置给Dialog
                        bgSetDialog.setContentView(view);
                        //获取当前Activity所在的窗体
                        Window dialogWindow = bgSetDialog.getWindow();
                        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        //设置Dialog从窗体底部弹出
                        dialogWindow.setGravity(Gravity.BOTTOM);
                        bgSetDialog.show();//显示对话框
                    }
                    break;
            }
        }
    }
}