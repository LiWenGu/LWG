package com.example.javacallc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private JNI jni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jni = new JNI();
    }

    public void add(View view){
        int result = jni.add(99, 1);
        Log.d("TAG", result + ",");
    }

    public void string(View view){

    }

    public void array(View view){

    }

    public void check(View view){

    }
}
