package com.example.liwenguang.lwg.c_CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;


public class Custom_z_j_SlideLayout extends ViewGroup {

    private View menuView;
    private View contentView;
    private boolean isMenuShow;


    public Custom_z_j_SlideLayout(Context context, AttributeSet attrs) {
       super(context, attrs);
        scroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        menuView = getChildAt(0);
        contentView = getChildAt(1);
        Log.d("TAG", "onMeasure:" + widthMeasureSpec + "," + heightMeasureSpec );
        menuView.measure(MeasureSpec.makeMeasureSpec(menuView.getLayoutParams().width, MeasureSpec.EXACTLY), heightMeasureSpec);
        contentView.measure(widthMeasureSpec, heightMeasureSpec);
   }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //menuView.getWidth();获取为空
        menuView.layout(-menuView.getMeasuredWidth(), t, l, b);
        contentView.layout(l, t, r, b);
    }

    private float startX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                Log.d("TAG", "down");
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                int distanceX = (int) (endX - startX);  //手从左往右滑时，end>start,distancx>0,要让图片从右向左滑，需要取反

                int nextScrollX = -getScrollX() + distanceX;
                //滑动距离小于菜单的宽度；手势滑动方向向右滑，即图片向左滑
                if (nextScrollX <= menuView.getWidth() && nextScrollX >=0){
                    scrollBy(-distanceX, 0);
                }
                startX = endX;
                Log.d("TAG", "move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("TAG", "up");
                Log.d("TAG", "getScrollX" + getScrollX());  //返回的像素
                if (-getScrollX() > menuView.getWidth()/2){
                    //openMenu
                    isMenuShow = true;
                    //scrollTo(-menuView.getWidth(),0);
                }else{
                    //closeMenu
                    isMenuShow = false;
                    //scrollTo(0, 0);
                }
                flushState();
                break;
        }
        return true;
    }

    private Scroller scroller;

    private void flushState() {
        if (isMenuShow){
            int distance = menuView.getWidth() - (-getScrollX());
            scroller.startScroll(getScrollX(), 0, -distance, 0);
        }else{
            int distance = 0 - getScrollX();
            scroller.startScroll(getScrollX(), 0, distance, 0);
        }
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }
}
