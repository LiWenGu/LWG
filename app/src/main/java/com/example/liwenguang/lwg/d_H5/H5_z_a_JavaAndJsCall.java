package com.example.liwenguang.lwg.d_H5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liwenguang.lwg.R;

public class H5_z_a_JavaAndJsCall extends AppCompatActivity implements View.OnClickListener{


    private EditText etNumber;
    private EditText etPassword;
    private Button btnLogin;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_h5_a_java_and_js_call);

        findViews();
        initWebView();
    }


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-11-14 08:21:50 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        etNumber = (EditText)findViewById( R.id.et_number );
        etPassword = (EditText)findViewById( R.id.et_password );
        btnLogin = (Button)findViewById( R.id.btn_login );

        btnLogin.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-11-14 08:21:50 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == btnLogin ) {
            // Handle clicks for btnLogin
            login();
        }
    }

    private void login() {
        String name = etNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)){
            Toast.makeText(H5_z_a_JavaAndJsCall.this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(H5_z_a_JavaAndJsCall.this, "登陆", Toast.LENGTH_SHORT).show();
            login(name);
        }
    }

    private void login(String name) {
        webView.loadUrl("javascript:javaCallJs(" + "'" + name + "'" + ")");
        setContentView(webView);
    }

    private void initWebView() {
        //1.加载网页-H5,html,自定义浏览器
        webView = new WebView(H5_z_a_JavaAndJsCall.this);
        WebSettings webSettings = webView.getSettings();

        //设置支持javascript(js)
        webSettings.setJavaScriptEnabled(true);

        //不调用浏览器-自定义浏览器
        webView.setWebViewClient(new WebViewClient());

        //添加javascript调用的借口
        //以后js可以通过Android字段调用这个类中的任何方法
        webView.addJavascriptInterface(new AndroidAndJsInterface(), "Android");

        //加载网络的网络-本地的网络
        //webView.loadUrl("http://www.atguigu.com/teacher.shtml");
        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");

    }

    class AndroidAndJsInterface{
        /**
         * 将被js调用
         */
        @JavascriptInterface
        public void showToast(){
            Toast.makeText(H5_z_a_JavaAndJsCall.this, "来自于Android代码，被Js调用了", Toast.LENGTH_SHORT).show();
        }
    }

}
