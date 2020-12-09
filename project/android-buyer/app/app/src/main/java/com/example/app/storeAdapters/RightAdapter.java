package com.example.app.storeAdapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.app.R;
import com.example.app.vo.RightVo;

import java.util.ArrayList;
import java.util.List;

public class RightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    /**
     * Item类型
     */
    public static final int TITLE = 1;
    public static final int CONTENT = 2;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<RightVo> mDatas;
    public OnItemClickListener mOnItemClickListener;
    public RightAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    public void addData(List<RightVo> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }
    public List<RightVo> getDatas() {
        return mDatas;
    }
    @Override
    public int getItemViewType(int position) {
        RightVo rightVo = mDatas.get(position);
        return rightVo.getItemType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case RightAdapter.TITLE:
                return new TitleViewHolder(mInflater.inflate(R.layout.item_right_title, parent, false));
            case RightAdapter.CONTENT:
                View view = mInflater.inflate(R.layout.item_right_content, parent, false);
                view.setOnClickListener(this);
                ContentViewHolder  contentViewHolder = new ContentViewHolder(view);
                return contentViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final RightVo rightVo = mDatas.get(position);
        holder.itemView.setTag(position);
        switch (getItemViewType(position)) {
            case RightAdapter.TITLE:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.tvRightTitle.setText(rightVo.getTitle());
                break;
            case RightAdapter.CONTENT:
                final ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                //加载itemView
//                Bitmap bitmap = BitmapFactory.decodeFile(rightVo.getProductImg());
//                contentViewHolder.productImg.setImageBitmap(bitmap);
                contentViewHolder.productName.setText(rightVo.getProductName());
                contentViewHolder.productIntro.setText(rightVo.getProductIntro());
                contentViewHolder.productSale.setText(rightVo.getProductSale()+"");
                contentViewHolder.productPrice.setText(rightVo.getProductPrice()+"");
                contentViewHolder.addProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        xmOnItemDeleteListener.onDeleteClick(position);
                    }
                });
                contentViewHolder.subtractProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        xmOnItemDeleteListener.onDeleteClick(position);
                    }
                });
//                contentViewHolder.addProduct.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(rightVo.getProductNum() == 0){
//                            contentViewHolder.subtractProduct.setVisibility(View.VISIBLE);
//                            contentViewHolder.productNum.setVisibility(View.VISIBLE);
//                            rightVo.setProductNum(rightVo.getProductNum()+1);
//                            contentViewHolder.productNum.setText(rightVo.getProductNum()+"");
//                        }else{
//                            rightVo.setProductNum(rightVo.getProductNum()+1);
//                            contentViewHolder.productNum.setText(rightVo.getProductNum()+"");
//                        }
//                    }
//                });
//                contentViewHolder.subtractProduct.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(rightVo.getProductNum() == 1){
//                            contentViewHolder.subtractProduct.setVisibility(View.INVISIBLE);
//                            contentViewHolder.productNum.setVisibility(View.INVISIBLE);
//                            rightVo.setProductNum(rightVo.getProductNum()-1);
//                        }else{
//                            rightVo.setProductNum(rightVo.getProductNum()-1);
//                            contentViewHolder.productNum.setText(rightVo.getProductNum()+"");
//                        }
//
//                    }
//                });
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        return mDatas.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (mDatas.get(position).getItemType()) {
                        case RightAdapter.TITLE:
                            return 3;                //Title占3份
                        case RightAdapter.CONTENT:
                            return 3;                //Content占3份
                    }
                    return 3;
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
    //	定义接口方法
    public interface onItemDeleteListener {
        void onDeleteClick(int position);
    }

    //	声明接口
    private onItemDeleteListener xmOnItemDeleteListener;


    //	根据我们在回调里写的是 xmOnItemDeleteListener 调用的onDeleteClick
    //	系统就会执行下面的方法
    public void setXmOnItemDeleteListener(onItemDeleteListener xmOnItemDeleteListener){
        this.xmOnItemDeleteListener = xmOnItemDeleteListener;
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {

        TextView tvRightTitle;

        TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRightTitle = itemView.findViewById(R.id.tv_right_title);
        }
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        ImageView productImg;
        TextView productName;
        TextView productIntro;
        TextView productSale;
        TextView productPrice;
        ImageView addProduct;
        TextView productNum;
        ImageView subtractProduct;

        ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.product_img);
            productName = itemView.findViewById(R.id.product_name);
            productIntro = itemView.findViewById(R.id.product_intro);
            productSale = itemView.findViewById(R.id.product_sale);
            productPrice = itemView.findViewById(R.id.product_price);
            addProduct = itemView.findViewById(R.id.add_product);
            productNum = itemView.findViewById(R.id.product_num);
            subtractProduct = itemView.findViewById(R.id.subtract_product);
        }
    }
}
