package com.example.learnjni;

import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private JNI jni;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jni = new JNI();
        ll = (LinearLayout) findViewById(R.id.ll);
        jni.uninstall("/data/data/" + getPackageName(), Build.VERSION.SDK_INT);
    }

    public void add(View view){
        int result = jni.sum(99, 1);
        Log.e("tag", "result" + result);
    }

    public void string(View view){
        String result = jni.sayHello("i am from java");
        Log.e("tag", "result" + result);
    }

    public void array(View view){
        int array[] = {1,2,3,4,5,6,7,8,9};
        jni.increaseArrayElements(array);
        for (int i=0; i<array.length; i++){
            Log.e("tag", "第" + i + "个数是：" + array[i]);
        }
    }

    public void check(View view){
        int result = jni.checkPwd("123456");
        Log.e("tag", "是否是正确的：" + result);
    }

    public void check_handle(View view){
        final PressureView pressureView = new PressureView(MainActivity.this);
        ll.addView(pressureView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    SystemClock.sleep(500);
                    int pressure = Math.abs(jni.getPressure());
                    pressureView.setPressure(pressure);
                    if (pressure > 220){
                        break;
                    }
                }
            }
        }).start();

    }
}
