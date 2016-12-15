package com.example.liwenguang.lwg.f_New;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.liwenguang.lwg.R;

/**
 * Created by li-pc on 2016/12/13.
 */

public class New_a_Material extends AppCompatActivity implements View.OnClickListener{
    Button f_a_material;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a);
        init();
    }

    private void init() {
        f_a_material = (Button) findViewById(R.id.f_a_btn);
        f_a_material.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.f_a_btn:
                f_a_material();
                break;
        }
    }

    private void f_a_material() {
        Intent intent = new Intent();
        intent.setClassName("com.example.materialtest", "com.example.materialtest.MainActivity");
        startActivity(intent);
    }
}
