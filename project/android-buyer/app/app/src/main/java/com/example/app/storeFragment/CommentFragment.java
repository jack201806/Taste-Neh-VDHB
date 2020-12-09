package com.example.app.storeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.app.Bean.Comment;
import com.example.app.Bean.StoreScore;
import com.example.app.R;
import com.example.app.storeAdapters.CommentAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {
    private RecyclerView rvComment;
    private CommentAdapter commentAdapter;
    private TextView storeScare;
    private RatingBar ratingBar;
    private TextView tasteScore;
    private TextView packageScore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment,container,false);
        findView(view);
        StoreScore ssc = new StoreScore();
        ssc.setTotalScore((float) 4.6);
        ssc.setTasteScore((float) 4.8);
        ssc.setPackageScore((float) 4.9);
        ratingBar.setRating(ssc.getTotalScore());
        storeScare.setText(ssc.getTotalScore()+"");
        tasteScore.setText(ssc.getTasteScore()+"");
        packageScore.setText(ssc.getPackageScore()+"");
        initData();
        return view;
    }

    private void initData() {
        List<Comment> commentList = new ArrayList<>();
        for (int i=0;i<10;i++){
            Comment comment = new Comment();
            comment.setOrderTime("2020-12-02");
            comment.setUserComment("好吃 量大 ，值得推荐");
            comment.setUserName("user"+i);
            comment.setUserScore((float) 4.6);
            List<Integer>imgs = new ArrayList<>();
            for(int j = 0;j<5;j++){
                imgs.add(R.drawable.fish);
            }
            comment.setCommentImgs(imgs);
            commentList.add(comment);
        }
        commentAdapter = new CommentAdapter(getContext());
        commentAdapter.addDate(commentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                // 直接禁止垂直滑动
                return false;
            }
        };
        rvComment.setLayoutManager(layoutManager);
        rvComment.setAdapter(commentAdapter);
    }

    private void findView(View view) {
        rvComment = view.findViewById(R.id.rv_comment);
        storeScare = view.findViewById(R.id.store_score);
        ratingBar = view.findViewById(R.id.comment_rating);
        tasteScore = view.findViewById(R.id.taste_store);
        packageScore = view.findViewById(R.id.pachage_store);
    }
}
