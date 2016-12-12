package com.example.liwenguang.lwg.d_H5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.liwenguang.lwg.R;

public class H5_z_b_JsCallJavaVideoActivity extends AppCompatActivity{

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_h5_b_jscalljavavideo);
        webView = (WebView) findViewById(R.id.d_b_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new AndroidAndJsInterface(), "android");
        webView.loadUrl("file:///android_asset/RealNetJSCallJavaActivity.htm");
    }

    class AndroidAndJsInterface{
        /**
         * 将被js调用
         */
        @JavascriptInterface
        public void  playVideo(int itemid, String videourl, String itemtitle){

            videourl = "http://vfx.mtime.cn/Video/2016/11/11/mp4/161111091703099158_480.mp4";
            //1.把所有的播放器调用，用户自主选择
            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(videourl), "video/*");
            startActivity(intent);
            Toast.makeText(H5_z_b_JsCallJavaVideoActivity.this, "来自于Android的有参代码，被Js调用了", Toast.LENGTH_SHORT).show();
        }


    }
}
