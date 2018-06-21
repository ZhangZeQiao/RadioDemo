package com.zzq.radiodemo.mediaPlayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.zzq.radiodemo.R;

import java.io.IOException;

/**
 *
 * @author SurfaceView和Mediaplayer实现播放网络和本地视频 一 - CSDN博客  https://blog.csdn.net/qq_33544860/article/details/51150742
 * @date 2018/6/15
 */

public class VideoActivity extends AppCompatActivity {

    private Button mBtPlay;
    private SurfaceView mSvScreen;

    private MediaPlayer mMediaPlayer;
    private String mMediaUri = "http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";
    private String mMediaUri_m3u8 = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();

        mMediaPlayer = new MediaPlayer();
        SurfaceHolder surfaceHolder = mSvScreen.getHolder();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //准备完成后播放
                mMediaPlayer.start();
            }
        });
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            //该方法表示Surface已经创建完成，可以在该方法中进行绘图操作
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mMediaPlayer.reset();
                try {
                    //设置视屏文件图像的显示参数
                    mMediaPlayer.setDisplay(holder);
                    //网络资源
                    mMediaPlayer.setDataSource(VideoActivity.this, Uri.parse(mMediaUri_m3u8));
                    //异步准备：准备工作在子线程中进行，当播放网络视频时候一般采用此方法
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //当 SurfaceView的大小发生改变时候触发该方法
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            //当Surface销毁时候，同时把 MediaPlayer也销毁
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    //释放资源
                    mMediaPlayer.release();
                }
            }
        });
        mSvScreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.pause();
                        } else {
                            mMediaPlayer.start();
                        }
                        break;
                }
                //返回True代表事件已经处理了
                return true;
            }
        });
    }

    private void initView() {
        mBtPlay = (Button) findViewById(R.id.bt_play);
        mSvScreen = (SurfaceView) findViewById(R.id.sv_screen);
    }
}
