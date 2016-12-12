package com.example.liwenguang.lwg.c_CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.liwenguang.lwg.R;

/**
 * Created by liwenguang on 2016/11/10.
 * 自定义属性
 */

public class Custom_z_e_attrs extends View{

    private static final String TAG = "Custom_z_e_attrs";
    private int myAge;
    private String myName;
    private Bitmap myBg;

    public Custom_z_e_attrs(Context context) {
        this(context, null);
    }

    public Custom_z_e_attrs(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取属性的三个方式
        //1.用命名空间获取
        /*String age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_age");
        String name = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_name");
        String bg = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_bg");
        Log.d(TAG, "age = " + age + ", name = " + name + ", bg = " + bg);*/
        //2.遍历属性集合
        for (int i=0; i<attrs.getAttributeCount(); i++){
            String name = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
            Log.d(TAG, "name = " + name + ", value = " + value);
        }

        //3.使用系统工具
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Custom_z_e_attrs);
        for (int i=0; i<typedArray.getIndexCount(); i++){
            int index = typedArray.getIndex(i);
            switch (index){
                case R.styleable.Custom_z_e_attrs_my_age:
                    myAge = typedArray.getInt(index, 0);
                    break;
                case R.styleable.Custom_z_e_attrs_my_name:
                    myName = typedArray.getString(index);
                    break;
                case R.styleable.Custom_z_e_attrs_my_bg:
                    Drawable drawable = typedArray.getDrawable(index);
                    BitmapDrawable drawable1 = (BitmapDrawable) drawable;
                    myBg = drawable1.getBitmap();
                    break;
            }
        }

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawText(myName + "---" + myAge, 50, 50, paint);
        canvas.drawBitmap(myBg, 100, 100, paint);
    }
}
