package com.example.liwenguang.lwg.d_H5;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.liwenguang.lwg.R;

public class H5_z_c_JsCallJavaCallPhoneActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_h5_b_jscalljavavideo);
        webView = (WebView) findViewById(R.id.d_b_webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new AndroidAndJSInterface(), "Android");

        //加载本地资源
        //webView.loadUrl("http://atguigu.com/teacher.shtml");

        webView.loadUrl("file:///android_asset/JsCallJavaCallPhone.html");
    }

    class AndroidAndJSInterface {
        /**
         * 该方法将被js调用,用于加载数据
         */
        @JavascriptInterface
        public void showcontacts() {

            // 下面的代码建议在子线程中调用
            String json = "[{\"name\":\"李文广\", \"phone\":\"13182816892\"}]";
            // 调用JS中的方法
            webView.loadUrl("javascript:show('" + json + "')");

        }

        /**
         * 拨打电话
         * @param phone
         */
        @JavascriptInterface
        public void call(String phone) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(H5_z_c_JsCallJavaCallPhoneActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);

        }
    }
}
