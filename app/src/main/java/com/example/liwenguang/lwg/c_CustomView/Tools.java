package com.example.liwenguang.lwg.c_CustomView;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;

/**
 * Created by liwenguang on 2016/11/10.
 */
public class Tools {

    public static void hideView(ViewGroup view){
        hideView(view, 0);
    }

    public static void showView(ViewGroup view){
        showView(view, 0);
    }

    public static void hideView(ViewGroup view, int startOffset){
        //tweenanimtion(0, 180, view, startOffset, false);
        PropertyAnimator(view, startOffset, 0, 180);
    }

    public static void showView(ViewGroup view, int startOffset){
        //tweenanimtion(180, 360, view, startOffset, true);
        PropertyAnimator(view, startOffset, 180, 360);
    }

    /**
     * 补间动画
     * @param begin 开始角度
     * @param end   结束角度
     * @param view
     * @param startOffset  延迟时间
     * @param flag   是否让子View可点击
     */
    private static void tweenanimtion(int begin, int end, ViewGroup view, int startOffset, boolean flag) {
        RotateAnimation ra = new RotateAnimation(begin,end,view.getWidth()/2, view.getHeight());
        Log.d("lwg",view.getWidth()/2 + "and" + view.getHeight());
        ra.setDuration(500);
        ra.setFillAfter(true);
        ra.setStartOffset(startOffset);
        view.startAnimation(ra);
        //如果是view则无法获取到子控件，因此为viewgroup
        for (int i=0; i<view.getChildCount(); i++){
            view.getChildAt(i).setEnabled(flag);
        }
    }

    /**
     * 属性动画
     * @param view
     * @param startOffset  延迟时间
     * @param i          开始的角度
     * @param i2         结束的角度
     */
    private static void PropertyAnimator(ViewGroup view, int startOffset, int i, int i2) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", i, i2);
        animator.setDuration(500);
        animator.setStartDelay(startOffset);
        animator.start();
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight());
    }
}
