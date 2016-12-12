package com.example.liwenguang.lwg.c_CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by liwenguang on 2016/11/10.
 *
 */

public class Custom_z_f_myviewpager extends ViewGroup{

    /**
     * 手势识别器
     * 1.定义出来
     * 2.实例化
     * 3.再onTouchEvent()把事件传递给手势识别器
     */

    private GestureDetector gestureDetector;

    public Custom_z_f_myviewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        scrollutil = new Scroller(context);
        //实例化手势识别器
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }

            /**
             *
             * @param e1
             * @param e2
             * @param distanceX  在X移动的距离
             * @param distanceY  在Y移动的距离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //scrollBy((int)distanceX, (int) distanceY);
                scrollBy((int)distanceX, getScrollY());  //上下不可滑动，为默认值，也可以设置为0.getScrollY()记录内容的变化，以手机屏幕为准的正负值
                return true;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历孩子，给每个孩子指定再屏幕的坐标位置
        for (int i=0; i<getChildCount(); i++){
            View childView = getChildAt(i);
            childView.layout(i*getWidth(), 0, (i+1)*getWidth(), getHeight());
        }
    }

    private float downX;
    private float downY;

    /**
     * 返回true直接拦截，然后触发本ViewGroup的ontouchevet方法，不向下传递
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);  //手势识别器用于左右滑动。

        boolean result = false; //默认传递给孩子

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //计算起始值
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                //计算绝对值
                float distanceX = Math.abs(endX - downX);
                float distanceY = Math.abs(endY - downY);
                if (distanceX > distanceY && distanceX > 5){
                    result = true;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return result;
    }

    private float startX;
    private int currentIndex;  //当前页面下标

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("myviewpager", "myviewpager ontouch" );

        super.onTouchEvent(event);

        //3.把事件传递给手势识别器
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //计算起始值
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //计算结束值
                float endX = event.getX();

                //当前页面下标
                int tempIndex = currentIndex;
                //计算偏移量
                if ((startX - endX) > getWidth()/2){
                    //显示下个页面
                    tempIndex++;
                }else if ((endX - startX) > getWidth()/2){
                    //显示上个页面
                    tempIndex--;
                }

                //根据下标位置移动到指定的界面
                scrollToPager(tempIndex);
                break;
        }
        return true;
    }

    Scroller scrollutil;

    /**
     * 屏蔽非法值
     * @param tempIndex
     */
    public void scrollToPager(int tempIndex) {
        if (tempIndex < 0){
            tempIndex = 0;
        }
        if (tempIndex >= getChildCount()){
            tempIndex = getChildCount() - 1;
        }
        Log.d("Tag",currentIndex +"index");
        currentIndex = tempIndex;

        if (mOnPagerChangListener != null){
            mOnPagerChangListener.onScrollToPager(currentIndex);
        }

        //scrollTo((currentIndex)*getWidth(), getScrollY());

        //计算总距离
        int distanceX = currentIndex*getWidth() - getScrollX();
        scrollutil.startScroll(getScrollX(), getScrollY(), distanceX, 0, Math.abs(distanceX));

        invalidate();  //onDraw();  computeScroll();
    }

    @Override
    public void computeScroll() {
        //super.computeScroll();
        if (scrollutil.computeScrollOffset()){
            float currX = scrollutil.getCurrX();

            scrollTo((int) currX, 0);

            invalidate();
        }
    }

    /**
     * 监听页面的改变地方
     */
    public interface OnPagerChangListener{
        void onScrollToPager(int position);
    }

    private OnPagerChangListener mOnPagerChangListener;

    /**
     * 设置页面改变的监听
     * @param l
     */
    public void setOnPagerChangListener(OnPagerChangListener l){
        mOnPagerChangListener = l;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("TAG", widthMeasureSpec + ":widthMeasureSpec");
        Log.d("TAG","width: + " + MeasureSpec.getSize(widthMeasureSpec) + ".mode = " + MeasureSpec.getMode(widthMeasureSpec));
        Log.d("TAG","height: + " + MeasureSpec.getSize(heightMeasureSpec) + ".mode = " + MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i=0; i<getChildCount(); i++){
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,heightMeasureSpec);
        }
    }


}
