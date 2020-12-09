package com.example.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.orderAdapter.ImgAdapter;
import com.example.app.orderAdapter.ProductAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private ImageView storeHeader;
    private TextView storeName;
    private LinearLayout submitComment;
    private RatingBar totalRating;
    private RatingBar packageRating;
    private RatingBar tasteRating;
    private EditText commentText;
    private LinearLayout goPhoto;
    private List<Uri> mSelected;
    private GridView gridView;
    private ImgAdapter imgAdapter;
    private TextView totalText;
    private TextView packageText;
    private TextView tasteText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        findView();
        goPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(CommentActivity.this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .capture(true)
                        .maxSelectable(9)
                        .captureStrategy(new CaptureStrategy(true,"com.example.orders.fileprovider"))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .showPreview(false) // Default is `true`
                        .forResult(200);
            }
        });
        tasteRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                tasteText.setText(ratingBar.getRating()+"");
            }
        });
        totalRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                totalText.setText(ratingBar.getRating()+"");
            }
        });
        packageRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                packageText.setText(ratingBar.getRating()+"");
            }
        });
    }

    private void findView() {
        storeHeader = findViewById(R.id.store_header);
        storeName = findViewById(R.id.store_name);
        submitComment = findViewById(R.id.submit_commment);
        totalRating = findViewById(R.id.total_rating);
        packageRating = findViewById(R.id.package_rating);
        tasteRating = findViewById(R.id.taste_rating);
        commentText = findViewById(R.id.comment_text);
        gridView = findViewById(R.id.gridView);
        goPhoto = findViewById(R.id.go_photo);
        totalText = findViewById(R.id.total_score);
        tasteText = findViewById(R.id.taste_score);
        packageText = findViewById(R.id.package_score);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            imgAdapter = new ImgAdapter(this,R.layout.item_img,mSelected);
            gridView.setAdapter(imgAdapter);
            if(totalRating.getRating()!=0&&packageRating.getRating()!=0&&tasteRating.getRating()!=0&&!commentText.getText().equals("")){
                submitComment.setBackground(getResources().getDrawable(R.drawable.circle_shape_changed));
            }
        }
    }
    private void setListViewHeightBasedOnChildren(GridView lvOrders) {
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
        params.height = totalHeight + (lvOrders.getHeight() * (adapter.getCount() - 1));
        //给listview设置高度
        lvOrders.setLayoutParams(params);
    }
}