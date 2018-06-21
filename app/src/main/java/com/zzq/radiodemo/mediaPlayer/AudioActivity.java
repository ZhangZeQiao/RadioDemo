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
 * @author SurfaceView和Mediaplayer实现播放网络和本地视频 一 - CSDN博客  https://blog.csdn.net/qq_33544860/article/details/51150742
 * @date 2018/6/15
 */

public class AudioActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    // 喜马拉雅电台网络直播 TODO 注意版权问题
    // m3u8的地址你都不能直接得到，我们用得到这个文件的链接地址的前半段，拼接上二级文件的相对地址，得到一个地址
    // private String mMediaUri = "http://live.xmcdn.com/live/606/24.m3u8";
    private String mMediaUri = "http://live.xmcdn.com/live/606/24.m3u8?transcode=ts"; //TODO 可以播放
    // private String mMediaUri = "http://live.xmcdn.com/live/606/64.m3u8";
    // private String mMediaUri = "http://live.xmcdn.com/live/606/64.m3u8?transcode=ts";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        mMediaPlayer = new MediaPlayer();
        try {
            // 网络资源
            mMediaPlayer.setDataSource(AudioActivity.this, Uri.parse(mMediaUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 异步准备：准备工作在子线程中进行，当播放网络视频时候一般采用此方法
        mMediaPlayer.prepareAsync();
        // 准备完成后播放
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer.start();
            }
        });
        // 播放错误处理
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // 可以重新播放或者做其他事情
                return false;
            }
        });
    }

    public void onPlayRadio(View view) {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayer != null) {
            //释放资源
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDestroy();
    }
}
