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

    Button e_a_tianqi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_project_main);
        init();
    }

    private void init() {
        e_a_tianqi = (Button) findViewById(R.id.e_a_btn);
        e_a_tianqi.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.e_a_btn:
                e_a_tianqi();
                break;
        }
    }

    private void e_a_tianqi() {
        Intent intent = new Intent();
        intent.setClassName("com.example.coolweather", "com.example.coolweather.MainActivity");
        startActivity(intent);
    }
}
