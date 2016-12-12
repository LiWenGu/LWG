package com.example.liwenguang.lwg.c_CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liwenguang on 2016/11/10.
 */

public class Custom_z_h_IndexView extends View {

    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private int itemWidth;
    private int itemHeight;
    private Paint paint;

    public Custom_z_h_IndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
         init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);                  //设置抗锯齿
        paint.setTypeface(Typeface.DEFAULT_BOLD);  //设置粗体
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemHeight = getHeight()/words.length;
        itemWidth = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0; i<words.length; i++){

            if (touchIndex == i){
                //设置黑色
                paint.setColor(Color.BLACK);
            }else{
                //设置白色
                paint.setColor(Color.WHITE);
            }

            String word = words[i];

            Rect rect = new Rect();
            //画笔
            //0,1取下标为0，长度为1
            paint.getTextBounds(word, 0, 1, rect);

            //得到字母的宽和高
            int wordHeight = rect.height();
            int wordWidth = rect.width();

            //计算每个字母再屏幕上的坐标位置
            float wordX = itemWidth / 2 - wordWidth / 2;
            float wordY = itemHeight / 2 + wordHeight / 2 + i * itemHeight;

            canvas.drawText(word, wordX, wordY, paint);

        }
    }

    int touchIndex;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y/itemHeight);
                if (index != touchIndex){  //如果当前触摸的字母和上次触摸的字母不一样
                    touchIndex = index;
                    invalidate();      //重绘字母的颜色
                    if (onIndexChangeListener != null && touchIndex < words.length){
                        onIndexChangeListener.onIndexChange(words[touchIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 字母下标索引变化的监听器
     */
    public interface OnIndexChangeListener{
        /**
         * 当字母下标位置发生变化的时候回调字母
         * @param word
         */
        void onIndexChange(String word);
    }

    private OnIndexChangeListener onIndexChangeListener;

    public void setOnIndexChangeListener(OnIndexChangeListener onIndexChangeListener){
        this.onIndexChangeListener = onIndexChangeListener;
    }
}
