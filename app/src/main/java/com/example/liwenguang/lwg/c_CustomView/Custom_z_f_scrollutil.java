package com.example.liwenguang.lwg.c_CustomView;

import android.os.SystemClock;

/**
 * Created by liwenguang on 2016/11/10.
 */

public class Custom_z_f_scrollutil {

    private float startX;
    private float startY;
    private int distanceX;
    private int distanceY;
    private boolean isFinish; //是否移动完成
    //开始时间
    private long startTime;
    //总持续时间
    private long totalTime = 500;

    public float getCurrX() {
        return currX;
    }

    public void setCurrX(float currX) {
        this.currX = currX;
    }

    private float currX;

    /**
     *
     * @param scrollX   滑动起始X坐标
     * @param scrollY   滑动起始Y坐标
     * @param distanceX X滑动的距离
     * @param distanceY Y滑动的距离
     */
    public void startScroll(float scrollX, float scrollY, int distanceX, int distanceY) {
        this.startX = scrollX;
        this.startY = scrollY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = SystemClock.uptimeMillis();  //系统开机时间
        this.isFinish = false;
    }

    /**
     * 速度
     * 求移动一小段的距离
     * 求移动一小段对应的坐标
     * 求移动一小段对应的时间
     * ture:正在移动
     * false:移动结束
     * @return
     */
    public boolean computeScrollOffset(){
        if (isFinish){
            return false;
        }

        long endTime = SystemClock.uptimeMillis();

        long passTime = endTime - startTime;
        if(passTime < totalTime){
            //还没有移动结束
            //计算平均速度
//            float voleCity = distanceX/totalTime;
            //移动这个一小段对应的距离
            float distanceSamllX = passTime* distanceX/totalTime;

            currX = startX + distanceSamllX;
        }else{
            //移动结束
            isFinish = true;
            currX = startX + distanceX;
        }
        return true;
    }
}
