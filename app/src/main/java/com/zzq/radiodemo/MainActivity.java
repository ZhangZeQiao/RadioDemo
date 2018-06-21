package com.zzq.radiodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zzq.radiodemo.mediaPlayer.AudioActivity;
import com.zzq.radiodemo.vitamio.VitamioActivity;
import com.zzq.radiodemo.webview.WebViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onJumpToMp(View view) {
        Intent intent = new Intent(this, AudioActivity.class);
        startActivity(intent);
    }

    public void onJumpToVtm(View view) {
        Intent intent = new Intent(this, VitamioActivity.class);
        startActivity(intent);
    }

    public void onJumpToWv(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }
}
