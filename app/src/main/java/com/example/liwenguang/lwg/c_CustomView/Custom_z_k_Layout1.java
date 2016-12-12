package com.example.liwenguang.lwg.c_CustomView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


public class Custom_z_k_Layout1 extends ViewPager {


    public Custom_z_k_Layout1(Context context) {
        super(context);
    }

    public Custom_z_k_Layout1(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("Custom_z_k_Layout1", "MyLayout onInterceptTouchEvent.");
        Log.e("Custom_z_k_Layout1", "MyLayout onInterceptTouchEvent default return "
                + super.onInterceptTouchEvent(ev));
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Custom_z_k_Layout1", "MyLayout onTouchEvent.");
        Log.e("Custom_z_k_Layout1", "MyLayout onTouchEvent default return "
                + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

}
