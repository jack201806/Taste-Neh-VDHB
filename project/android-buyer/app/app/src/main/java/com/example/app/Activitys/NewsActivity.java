package com.example.app.Activitys;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;

import java.io.File;

public class NewsActivity extends AppCompatActivity {

    private VideoView videoview;
    private ImageView watch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        videoview=(VideoView) findViewById(R.id.vv);
        watch=findViewById(R.id.watch);
        String uri="android.resource://"+getPackageName()+"/"+R.raw.life3;
        videoview.setVideoURI(Uri.parse(uri));
        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.setLooping(false);//设置视频重复播放
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoview.start();//播放视频
            }
        });
    }
}
