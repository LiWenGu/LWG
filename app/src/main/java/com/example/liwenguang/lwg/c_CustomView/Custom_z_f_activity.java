package com.example.liwenguang.lwg.c_CustomView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.liwenguang.lwg.R;

/**
 * Created by liwenguang on 2016/11/10.
 */

public class Custom_z_f_activity extends Activity {

    private  Custom_z_f_myviewpager custom_z_f_myviewpager;
    private  RadioGroup custom_z_f_rg;
    private int[] ids = {R.drawable.c_f_a1, R.drawable.c_f_a2, R.drawable.c_f_a3, R.drawable.c_f_a4, R.drawable.c_f_a5, R.drawable.c_f_a6};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_custom_f_viewpager);

        custom_z_f_myviewpager = (Custom_z_f_myviewpager) findViewById(R.id.c_CustomView_f_one_viewpager);
        custom_z_f_rg = (RadioGroup) findViewById(R.id.c_CustomView_f_one_rg);
        //添加页面
        for (int i=0; i<ids.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);

            //添加到myViewPager这个ViewGroup中
            custom_z_f_myviewpager.addView(imageView);
        }

        View view = LayoutInflater.from(this).inflate(R.layout.activity_c_custom_f_ztest, null);
        custom_z_f_myviewpager.addView(view);

        //添加点
        for (int i=0; i<custom_z_f_myviewpager.getChildCount(); i++){
            RadioButton button = new RadioButton(this);
            button.setId(i); //0-5的id
            if (i==0){
                button.setChecked(true);
            }
            custom_z_f_rg.addView(button);
        }

        custom_z_f_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             *
             * @param group
             * @param checkedId 0-5之间
             */
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                custom_z_f_myviewpager.scrollToPager(checkedId);
            }
        });

        custom_z_f_myviewpager.setOnPagerChangListener(new Custom_z_f_myviewpager.OnPagerChangListener() {
            @Override
            public void onScrollToPager(int position) {
                custom_z_f_rg.check(position);
            }
        });
    }


}
