package com.example.liwenguang.lwg.c_CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;


public class Custom_z_k_View extends Button {


    public Custom_z_k_View(Context context){
        super(context);
    }

    public Custom_z_k_View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Custom_z_k_View", "MyView onTouchEvent.");
        Log.e("Custom_z_k_View","MyView onTouchEvent default return "
                + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

}
