package com.zzq.radiodemo.webview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.zzq.radiodemo.R;

/**
 * Created by ZZQ on 2018/6/20.
 */

public class WebViewActivity extends AppCompatActivity {

    private WebView mWvRadio;
    private WebSettings mWebSettings;
    private TextView mTvInfo;

    private void initView() {
        mWvRadio = (WebView) findViewById(R.id.wv_radio);
        mTvInfo = (TextView) findViewById(R.id.tv_info);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mWebSettings = mWvRadio.getSettings();
        // 允许使用js
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDomStorageEnabled(true);
        // 自适应手机屏幕
        mWebSettings.setUseWideViewPort(true);
        // 是否使用overview mode加载页面，默认值 false
        // 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
        mWebSettings.setLoadWithOverviewMode(true);
        // 布局算法
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        // 缩放(zoom)
        mWebSettings.setSupportZoom(true);          // 是否支持缩放
        mWebSettings.setBuiltInZoomControls(true);  // 是否使用内置缩放机制
        mWebSettings.setDisplayZoomControls(true);  // 是否显示内置缩放控件

        // mWvRadio.loadUrl("http://www.fm914.com/");
        mWvRadio.loadUrl("http://fm914.grtn.cn/");
        // 设置不用系统浏览器打开,直接显示在当前 Webview
        mWvRadio.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mTvInfo.setText("加载中...");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mTvInfo.setText("加载完成");
            }

        });
    }

    // 点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWvRadio.canGoBack()) {
            mWvRadio.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 销毁Webview
    @Override
    protected void onDestroy() {
        if (mWvRadio != null) {
            mWvRadio.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWvRadio.clearHistory();

            ((ViewGroup) mWvRadio.getParent()).removeView(mWvRadio);
            mWvRadio.destroy();
            mWvRadio = null;
        }
        super.onDestroy();
    }

}
