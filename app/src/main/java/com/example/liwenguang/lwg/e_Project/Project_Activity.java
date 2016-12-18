package com.example.liwenguang.lwg.e_Project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.liwenguang.lwg.R;

/**
 * Created by li-pc on 2016/12/12.
 */

public class Project_Activity extends AppCompatActivity implements View.OnClickListener{

    Button e_a_design;
    Button e_b_tianqi;
    Button e_c_jni;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_project_main);
        init();
    }

    private void init() {
        e_a_design = (Button) findViewById(R.id.e_a_btn);
        e_a_design.setOnClickListener(this);
        e_b_tianqi = (Button) findViewById(R.id.e_b_btn);
        e_b_tianqi.setOnClickListener(this);
        e_c_jni = (Button) findViewById(R.id.e_c_btn);
        e_c_jni.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.e_a_btn:
                e_a_design();
                break;
            case R.id.e_b_btn:
                e_b_tianqi();
                break;
            case R.id.e_c_btn:
                e_c_jni();
                break;
        }
    }

    private void e_a_design() {
        Intent intent = new Intent();
        intent.setClassName("com.example.materialtest", "com.example.materialtest.MainActivity");
        startActivity(intent);
    }

    private void e_b_tianqi() {
        Intent intent = new Intent();
        intent.setClassName("com.example.coolweather", "com.example.coolweather.MainActivity");
        startActivity(intent);
    }

    private void e_c_jni() {
        Intent intent = new Intent();
        intent.setClassName("com.example.learnjni", "com.example.learnjni.MainActivity");
        startActivity(intent);
    }
}
