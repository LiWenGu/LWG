package com.example.liwenguang.lwg.c_CustomView;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.liwenguang.lwg.R;

/**
 * Created by liwenguang on 2016/11/10.
 */

public class Custom_Mainactivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_custom_0_main);
    }

    public void c_custom_a_button(View view) {
        Intent intent = new Intent(this, Custom_z_a_photoactivity.class);
        startActivity(intent);
    }

    public void c_custom_b_button(View view) {
        Intent intent = new Intent(this, Custom_z_b_adactivity.class);
        startActivity(intent);
    }

    public void c_custom_c_button(View view) {
        Intent intent = new Intent(this, Custom_z_c_popactivity.class);
        startActivity(intent);
    }

    public void c_custom_d_button(View view) {
        Intent intent = new Intent(this, Custom_z_d_activity.class);
        startActivity(intent);
    }

    public void c_custom_e_button(View view) {
        Intent intent = new Intent(this, Custom_z_e_activity.class);
        startActivity(intent);
    }

    public void c_custom_f_button(View view) {
        Intent intent = new Intent(this, Custom_z_f_activity.class);
        startActivity(intent);
    }

    public void c_custom_g_button(View view) {
        Intent intent = new Intent();

        intent.setComponent(new ComponentName("com.atguigu.pinterestlistview","com.atguigu.pinterestlistview.MainActivity"));

        intent.setAction(Intent.ACTION_VIEW);

        startActivity(intent);
    }

    public void c_custom_h_button(View view) {
        Intent intent = new Intent(this, Custom_z_h_activity.class);
        startActivity(intent);
    }

    public void c_custom_i_button(View view) {
        Intent intent = new Intent(this, Custom_z_i_activity.class);
        startActivity(intent);
    }

    public void c_custom_j_button(View view) {
        Intent intent = new Intent(this, Custom_z_j_activity.class);
        startActivity(intent);
    }

    public void c_custom_k_button(View view) {
        Intent intent = new Intent(this, Custom_z_k_main.class);
        startActivity(intent);
    }
}


