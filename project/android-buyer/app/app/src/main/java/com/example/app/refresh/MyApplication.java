package com.example.app.refresh;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

public class MyApplication extends Application {
    static {
        //设置Header样式
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(
                new DefaultRefreshHeaderCreator() {
                    @NonNull
                    @Override
                    public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                        //创建Header样式
                        BezierRadarHeader header =
                                new BezierRadarHeader(context);
                        return header;
                    }
                }
        );
        //设置Footer样式
        SmartRefreshLayout.setDefaultRefreshFooterCreator(
                new DefaultRefreshFooterCreator() {
                    @NonNull
                    @Override
                    public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                        //实例化一种Footer样式
                        FalsifyFooter footer =
                                new FalsifyFooter(context);
                        return footer;
                    }
                }
        );
    }
}
