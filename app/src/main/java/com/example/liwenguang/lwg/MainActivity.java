package com.example.liwenguang.lwg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.liwenguang.lwg.a_Rxjava.BaseOperationActivity;
import com.example.liwenguang.lwg.b_Okhttp.OkHttpActivity;
import com.example.liwenguang.lwg.c_CustomView.Custom_Mainactivity;
import com.example.liwenguang.lwg.d_H5.H5_Activity;
import com.example.liwenguang.lwg.e_Project.Project_Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void first_Rxjava(View view){
        startActivity(new Intent(this, BaseOperationActivity.class));
    }

    public void second_okhttp(View view){
        startActivity(new Intent(this, OkHttpActivity.class));
    }

    public void third_CustomView(View view){
        startActivity(new Intent(this, Custom_Mainactivity.class));
    }

    public void fourth_AndroidH5(View view){
        startActivity(new Intent(this, H5_Activity.class));
    }

    public void fifth_Project(View view){
        startActivity(new Intent(this, Project_Activity.class));
    }


}
