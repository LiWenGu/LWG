package com.example.liwenguang.lwg.c_CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.liwenguang.lwg.R;

/**
 * Created by liwenguang on 2016/11/10.
 *  1.构造方法实例化类
 *  2.测量-measure(int, int) --> onMeasure()
 *  如果当前View是一个ViewGroup，还有义务测量孩子
 *  孩子有建议权
 *  3.指定位置-layout()-->onLayout()
 *  指定控件的位置，一般View不用写这个方法，ViewGroup的时候才需要
 *  4.绘制师徒--draw()-->onDraw(canvas)
 *  根据上面两个方法参数，进行绘制
 */

public class Custom_z_d_ToggleButton extends View implements View.OnClickListener, View.OnTouchListener {

    private Bitmap backgroundBitmap;
    private Bitmap slidingBitmap;
    private int slideLeftMax;  //距离左边最大的距离
    private int slideLeft;
    private Paint paint;

    public Custom_z_d_ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.c_d_switch_background);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.c_d_slide_button);
        slideLeftMax = backgroundBitmap.getWidth() - slidingBitmap.getWidth();
        paint = new Paint();
        paint.setAntiAlias(true); //设置抗锯齿

        this.setOnClickListener(this);
        this.setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        canvas.drawBitmap(slidingBitmap, slideLeft, 0, paint);
    }

    private boolean isOpen = false;
    private boolean isEnableClick = true;  //默认让点击事件生效，滑动事件不生效

    @Override
    public void onClick(View v) {
        if (isEnableClick){
            isOpen = !isOpen;
            flushView();
        }
    }

    private void flushView() {
        if (isOpen){
            slideLeft = slideLeftMax;
        }else{
            slideLeft = 0;
        }

        invalidate();
    }

    private float startX;
    private float lastX;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.记录按下的坐标
                lastX = startX = event.getX();
                isEnableClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                //2.计算结束值
                float endX = event.getX();
                //3.计算偏移值
                float distanceX = endX - startX;
                slideLeft += distanceX;
                //4.屏蔽非法值
                if (slideLeft < 0){
                    slideLeft = 0;
                }else if (slideLeft > slideLeftMax){
                    slideLeft = slideLeftMax;
                }
                //5.刷新
                invalidate();

                //6.数据还原
                startX = event.getX();

                if (Math.abs(endX - lastX) > 5){
                    //滑动
                    isEnableClick = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isEnableClick){
                    if (slideLeft > slideLeftMax/2){
                        isOpen = true;
                    }else{
                        isOpen = false;
                    }
                    flushView();
                }
                break;
        }
        return true;
    }
}
