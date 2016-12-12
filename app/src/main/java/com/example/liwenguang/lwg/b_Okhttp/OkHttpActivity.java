package com.example.liwenguang.lwg.b_Okhttp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liwenguang.lwg.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by liwenguang on 2016/11/10.
 */

public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final int GET = 1;
    private static final int POST = 2;
    private static final String TAG = "OKHTTPUTILS";
    private Button btn_get_post;
    private Button btn_get_okhttputils;
    private Button btn_downfile_okhttputils;
    private ProgressBar pgs_downfile;
    private Button btn_multiFileUpload;
    private Button btn_image;
    private ImageView iv_icon;
    private Button btn_imagelist;
    private TextView tv_result;
    private OkHttpClient client = new OkHttpClient();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET:
                    tv_result.setText((String)msg.obj);
                    break;
                case POST:
                    tv_result.setText((String)msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_b_okhttp);
        btn_get_post = (Button) findViewById(R.id.b_okhttp_one_button);
        btn_get_okhttputils = (Button) findViewById(R.id.b_okhttp_two_button);
        btn_downfile_okhttputils = (Button) findViewById(R.id.b_okhttp_three_button);
        pgs_downfile = (ProgressBar) findViewById(R.id.b_okhttp_three_progress);
        btn_multiFileUpload = (Button) findViewById(R.id.b_okhttp_four_button);
        btn_image = (Button) findViewById(R.id.b_okhttp_five_button);
        iv_icon = (ImageView) findViewById(R.id.b_okhttp_five_image);
        btn_imagelist = (Button) findViewById(R.id.b_okhttp_six_button);
        btn_get_okhttputils.setOnClickListener(this);
        tv_result = (TextView) findViewById(R.id.b_okhttp_one_text);
        btn_get_post.setOnClickListener(this);
        btn_downfile_okhttputils.setOnClickListener(this);
        btn_image.setOnClickListener(this);
        btn_imagelist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b_okhttp_one_button:
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String result = getDataFromPost("http://api.m.mtime.cn/PageSubArea/TrailerList.api", "");
                                    Message msg = handler.obtainMessage();
                                    msg.what = GET;
                                    msg.obj = result;
                                    handler.sendMessage(msg);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).start();
                break;
            case R.id.b_okhttp_two_button:
                getDataPostByHttpUtils();
                break;
            case R.id.b_okhttp_three_button:     //下载文件
                downloadFile();
                break;
            case R.id.b_okhttp_four_button:
                //multiFileUpload(url);
                break;
            case R.id.b_okhttp_five_button:
                getImage();
                break;
            case R.id.b_okhttp_six_button:
                Intent intent = new Intent(this, OkHttpListActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * get请求
     * @return
     * @throws IOException
     */
    private String getDataFromGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * post请求
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    private String getDataFromPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 使用http-utils的get请求
     */
    private void getDataGetByHttpUtils(){
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        //url="http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    /**
     * 使用http-utils的post请求
     */
    private void getDataPostByHttpUtils(){
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        //url="http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .post()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public void downloadFile()
    {
        String url = "http://vfx.mtime.cn/Video/2016/11/07/mp4/161107094111718495_480.mp4";
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "okhttp-utils-test.mp4")//
                {

                    @Override
                    public void onBefore(Request request, int id)
                    {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id)
                    {
                        pgs_downfile.setProgress((int) (100 * progress));
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id)
                    {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }

    /**
     * 多文件上传
     * @param mBaseUrl
     */
    public void multiFileUpload(String mBaseUrl)
    {
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test1#.txt");
        if (!file.exists() || !file2.exists())
        {
            Toast.makeText(OkHttpActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

        String url = mBaseUrl + "user!uploadFile";
        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .addFile("mFile", "test1.txt", file2)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());  //上传文件进度条回调
    }

    public void getImage()
    {
        tv_result.setText("");
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        tv_result.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id)
                    {
                        Log.e("TAG", "onResponse：complete");
                        iv_icon.setImageBitmap(bitmap);
                    }
                });
    }

    public class MyStringCallback extends StringCallback
    {
        @Override
        public void onBefore(Request request, int id)
        {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id)
        {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();
            tv_result.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id)
        {
            Log.e(TAG, "onResponse：complete");
            tv_result.setText("onResponse:" + response);

            switch (id)
            {
                case 100:
                    Toast.makeText(OkHttpActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OkHttpActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
            //mProgressBar.setProgress((int) (100 * progress));
        }
    }
}
