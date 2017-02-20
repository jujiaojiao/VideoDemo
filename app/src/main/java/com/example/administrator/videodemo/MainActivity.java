package com.example.administrator.videodemo;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, IMediaPlayer.OnCompletionListener, IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnPreparedListener {

    private Toolbar toolbar;
    private SurfaceView surfaceView;
    private RelativeLayout linearLayout;
    private SurfaceHolder surfaceHolder;
    private IjkMediaPlayer mediaPlayer;
    private boolean f = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
//        toolbar = (Toolbar) findViewById(R.id.video_toolbar);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
//        TextView Ttitle = (TextView) findViewById(R.id.toolbar_title);
//        Ttitle.setText("视频详情");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        surfaceView = (SurfaceView) findViewById(R.id.video);
        linearLayout = (RelativeLayout) findViewById(R.id.linear1);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        // 4.0版本之下需要设置的属性
        // 设置Surface不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到界面
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mediaPlayer = new IjkMediaPlayer();
        try {
//            AssetFileDescriptor assetFileDescriptor = this.getAssets().openFd("test.mp4");
//            AssetFileDescriptor assetFileDescriptor = this.getResources().openRawResourceFd(R.raw.test);
//            String s = this.getAssets() + "/test.mp4";
//            mediaPlayer.setDataSource(s);
            Uri mUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test);
            mediaPlayer.setDataSource(this, mUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //mediaPlayer准备工作
        mediaPlayer.setOnPreparedListener(this);

        //MediaPlayer完成
        mediaPlayer.setOnCompletionListener(this);

        mediaPlayer.setOnBufferingUpdateListener(this);//当前加载进度的监听

        //当触摸surfaceview时显示或消失底部按钮
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == (MotionEvent.ACTION_DOWN)) {
                    if (f == true) {
                        f = false;
                        linearLayout.setVisibility(View.VISIBLE);
                    } else {
                        f = true;
                        linearLayout.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //连接ijkPlayer 和surfaceHOLDER
        mediaPlayer.setDisplay(holder);
        //开启异步准备
        mediaPlayer.prepareAsync();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        iMediaPlayer.seekTo(0);
        iMediaPlayer.start();
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        iMediaPlayer.start();
        iMediaPlayer.prepareAsync();
    }

    //此处为两个按钮监听
//    public void btnClick(View view) {
//
//        switch (view.getId()) {
//            case R.id.button1:
//                mediaPlayer.pause();
//                break;
//            case R.id.button2:
//                mediaPlayer.start();
//                break;
//        }
//    }
}