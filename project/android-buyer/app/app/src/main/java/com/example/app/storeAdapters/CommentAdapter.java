package com.example.app.storeAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.app.Bean.Comment;
import com.example.app.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Comment> mDatas;

    public CommentAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }
    public void addDate(List<Comment> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.comment_item,parent,false));
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = mDatas.get(position);
        holder.itemView.setTag(position);
        holder.userHeader.setImageResource(R.drawable.user_header_img);
        holder.userName.setText(comment.getUserName());
        holder.userComment.setText(comment.getUserComment());
        holder.orderTime.setText(comment.getOrderTime());
        holder.ratingBar.setRating(comment.getUserScore());
        holder.userStore.setText(comment.getUserScore()+"");
        ImgAdapter imgAdapter = new ImgAdapter(mContext,R.layout.item_img,mDatas.get(position).getCommentImgs());
        holder.gridView.setAdapter(imgAdapter);
        setListViewHeightBasedOnChildren(holder.gridView);
    }
    private void setListViewHeightBasedOnChildren(GridView lvOrders) {
        //获得adapter
        ImgAdapter adapter = (ImgAdapter) lvOrders.getAdapter();
        if (adapter == null) {
            return;
        }
        int col = 3;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i += col) {
            View listItem = adapter.getView(i, null, lvOrders);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lvOrders.getLayoutParams();
        params.height = totalHeight;
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        lvOrders.setLayoutParams(params);
    }
    @Override
    public int getItemCount() {
        return  mDatas.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userHeader;
        RatingBar ratingBar;
        TextView userName;
        TextView userStore;
        TextView orderTime;
        TextView userComment;
        GridView gridView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            userHeader = itemView.findViewById(R.id.user_header);
            userName = itemView.findViewById(R.id.user_name);
            userStore = itemView.findViewById(R.id.user_score);
            orderTime = itemView.findViewById(R.id.order_time);
            userComment = itemView.findViewById(R.id.user_comment);
            gridView = itemView.findViewById(R.id.gridView);
            ratingBar = itemView.findViewById(R.id.service_grade_ratingBar);
        }
    }
}
