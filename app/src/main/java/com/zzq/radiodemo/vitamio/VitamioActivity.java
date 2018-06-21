package com.zzq.radiodemo.vitamio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zzq.radiodemo.R;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;

/**
 * Created by ZZQ on 2018/6/19.
 * Android简易实战教程--第四十一话《vitamio网络收音机》 - CSDN博客  https://blog.csdn.net/qq_32059827/article/details/53142963
 */

public class VitamioActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitamio);

        // 使用vitamio需要初始化引擎,引擎的检查
        /**---------------需要注意 begin---------------**/
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        /**---------------需要注意 end---------------**/
        try {
            mMediaPlayer = new MediaPlayer(this);
            // 设置监听器
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Toast.makeText(getApplicationContext(), "资源有问题", Toast.LENGTH_LONG).show();
                    return true;
                }
            });
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Toast.makeText(getApplicationContext(), "准备好了", Toast.LENGTH_LONG).show();
                    mMediaPlayer.start();
                }
            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Toast.makeText(getApplicationContext(), "播放完了", Toast.LENGTH_LONG).show();
                }
            });
            //准备播放
            mMediaPlayer.reset();//重置MediaPlayer
            // 设置播放来源
            // TODO: 2018/6/19 采用 Vitamio主要是用来播放 mms协议电台，但是目前没有找到可用的 mms链接
            // mMediaPlayer.setDataSource("http://live.xmcdn.com/live/606/24.m3u8?transcode=ts"); // Demo（有效）
            // mMediaPlayer.setDataSource("http://live.xmcdn.com/live/74/64.m3u8?transcode=ts"); // 抓包（有效）
            mMediaPlayer.setDataSource("http://live.xmcdn.com/live/74/64.m3u8"); // 抓包（有效）
            mMediaPlayer.prepareAsync();//网络，所以使用异步加载。真正的播放去对应的监听器里面开启(onPrepared)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playRadio(View view) {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayer != null) {
            // 销毁活动，释放资源
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDestroy();
    }
}
