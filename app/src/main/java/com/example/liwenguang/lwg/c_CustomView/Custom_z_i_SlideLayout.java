package com.example.liwenguang.lwg.c_CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;







public class Custom_z_i_SlideLayout extends FrameLayout {

    private static final String TAG = Custom_z_i_SlideLayout.class.getSimpleName();
    private View contentView;
    private View menuView;

    /**
     * 滚动者
     */
    private Scroller scroller;

    /**
     * Content的宽
     */
    private int contentWidth;
    private int menuWidth;
    private int ViewHeight;//他们的高都是相同的

    public Custom_z_i_SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    /**
     * 当布局文件加载完成的时候回调这个方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        menuView = getChildAt(1);
    }

    /**
     * 再测量方法里，得到各个控件的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        contentWidth = contentView.getMeasuredWidth();
        //contentWidth = getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
        ViewHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //指定菜单的位置
        menuView.layout(contentWidth, 0, contentWidth+menuWidth, ViewHeight);
    }

    private float startX;
    private float startY;
    private float downX;   //只记录一次
    private float downY;   //只记录一次

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.按下记录坐标
                downX = startX = event.getX();
                downY = startY = event.getY();
                Log.e(TAG,"SlideLayout-onTouchEvent-ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"SlideLayout-onTouchEvent-ACTION_MOVE");
                //2.记录结束值
                float endX = event.getX();
                float endY = event.getY();
                //3.计算偏移量
                float distanceX = endX - startX;

                int toScrollX = (int) (getScrollX() - distanceX);
                if (toScrollX < 0){
                    toScrollX = 0;   //View不能向左滑
                }else if (toScrollX > menuWidth){
                    toScrollX = menuWidth;  //view不能向右滑过大
                }
                scrollTo(toScrollX, getScrollY());

                startX = event.getX();
                startY = event.getY();
                //在X轴和Y轴滑动的距离
                float DX = Math.abs(endX - downX);
                float DY = Math.abs(endY - downY);
                if (DX > DY && DX > 8){
                    //水平方向滑动
                    //响应侧滑
                    //反拦截-事件给SlideLayout
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"SlideLayout-onTouchEvent-ACTION_UP");
                int totalScrollx = getScrollX();   //偏移量
                if (totalScrollx < menuWidth/2){
                    //关闭Menu
                    closeMenu();
                }else {
                    //打开Menu
                    openMenu();
                }
                break;
        }
        return true;
    }

    /**
     * true:拦截孩子的事件，但会执行当前控件的onTouchEvent()方法
     * false:不拦截孩子的事件，事件继续传递
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.按下记录坐标
                downX = startX = event.getX();
                Log.e(TAG,"SlideLayout-onTouchEvent-ACTION_DOWN");
                if (onStateChangeListener != null){
                    onStateChangeListener.onDown(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"SlideLayout-onTouchEvent-ACTION_MOVE");
                //2.记录结束值
                float endX = event.getX();
                float endY = event.getY();
                //3.计算偏移量
                float distanceX = endX - startX;

                startX = event.getX();
                //在X轴和Y轴滑动的距离
                float DX = Math.abs(endX-downX);
                if(DX>8){
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return intercept;
    }

    /**
     * 打开menu
     */
    private void openMenu() {
        //---->menuWidth
        int distanceX = menuWidth - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, getScrollY());
        invalidate();
        if (onStateChangeListener != null){
            onStateChangeListener.onOpen(this);
        }
    }

    /**
     * 关闭menu
     */
    public void closeMenu() {
        //----->0
        int distanceX = 0 - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, getScrollY());
        invalidate();
        if (onStateChangeListener != null){
            onStateChangeListener.onClose(this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 监听某个布局状态的改变
     */
    public interface OnStateChangeListener{
        void onClose(Custom_z_i_SlideLayout slideLayout);
        void onDown(Custom_z_i_SlideLayout slideLayout);
        void onOpen(Custom_z_i_SlideLayout slideLayout);
    }

    private OnStateChangeListener onStateChangeListener;

    public void setOnStateChangeListener(OnStateChangeListener listener){
        onStateChangeListener = listener;
    }
}
