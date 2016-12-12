package com.example.liwenguang.lwg.c_CustomView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liwenguang.lwg.R;

import java.util.ArrayList;

/**
 * Created by liwenguang on 2016/11/10.
 */

public class Custom_z_b_adactivity extends AppCompatActivity {

    private static final String TAG = Custom_z_b_adactivity.class.getName();
    private ViewPager viewPager;
    private TextView textView;
    private LinearLayout ll_poing_group;
    private ArrayList<ImageView> imageViews;
    private final int imageIds[] = {R.drawable.c_b_a, R.drawable.c_b_b, R.drawable.c_b_c, R.drawable.c_b_d, R.drawable.c_b_e};
    private final String[] imageDescription = {"Dota","War3","LOL","Mo1","CS",};

    /**
     * 是否正在拖拽
     */
    private boolean isDragging = false;
    /**
     * 上次高亮显示的位置
     */
    private int prePosition = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(item);

            handler.sendEmptyMessageDelayed(0, 4000);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_custom_b_ad);
        viewPager = (ViewPager) findViewById(R.id.c_CustomView_b_one_viewpager);
        textView = (TextView) findViewById(R.id.c_CustomView_b_one_title);
        ll_poing_group = (LinearLayout) findViewById(R.id.c_CustomView_b_one_ll_poing_group);

        //ViewPager的使用
        //1 在布局文件中定义ViewPager
        //2 实例化ViewPager
        //3 准备数据
        imageViews = new ArrayList<ImageView>();
        for (int i=0; i<imageIds.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            //添加到集合中
            imageViews.add(imageView);

            //添加点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.c_b_custom_point_election);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16,16);

            if (i==0){
                point.setEnabled(true);  //显示红色
            }else{
                point.setEnabled(false);  //显示黑色
                params.leftMargin = 32;
            }
            ll_poing_group.addView(point, params);
        }
        //4绑定适配器数据
        MyPahgerAdapter myPahgerAdapter = new MyPahgerAdapter();
        viewPager.setAdapter(myPahgerAdapter);
        MyAddOnPageChangeListener myAddOnPageChangeListener = new MyAddOnPageChangeListener();
        viewPager.addOnPageChangeListener(myAddOnPageChangeListener);    //页面改变的监听

        //设置中间位置
        int item = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%imageViews.size(); //保证imageViews的整数倍
        viewPager.setCurrentItem(item);
        textView.setText(imageDescription[0]);

        //发消息，自动滚动
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    /**
     * 默认初始化两个，然后先销毁后实例化
     */
    class MyPahgerAdapter extends PagerAdapter{

        //得到图片的总数
        @Override
        public int getCount() {
            //return imageViews.size();
            return Integer.MAX_VALUE;
        }

        /**
         * 相当于getView方法
         * @param container  ViewPager本身
         * @param position   当前实例化页面的位置
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realPosition = position%imageViews.size();
            ImageView imageView = imageViews.get(realPosition);
            container.addView(imageView);   //添加到ViewPager中
            Log.d(TAG, "instantiateItem == " + position + ",imageview == " + imageView);

            //用户触摸图片，停止自动滚动，放手后继续自动滚动
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            Log.d(TAG, "onTouch==手指按下");
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.d(TAG, "onTouch==手指离开");
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(0,5000);
                            break;
                        //当用户滑动时会调用这个方法，就算手不松开，但是还是会自动滑动的BUG
                        /*case MotionEvent.ACTION_CANCEL:
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(0,5000);
                            break;*/
                    }
                    return false;
                }
            });

            imageView.setTag(realPosition);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"onclick 点击事件");
                    int position = (int) v.getTag();
                    Toast.makeText(Custom_z_b_adactivity.this, "正在玩" + imageDescription[position], Toast.LENGTH_SHORT).show();
                }
            });
            return imageView;
        }

        /**
         * 比较view和object是否是同一个实例
         * @param view   某个页面
         * @param object instantiateItem()返回的结果，因此返回的结果可以用int，然后在本方法进行特殊比较
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view == object){
                return true;
            }else{
                return false;
            }
        }

        /**
         * 释放资源
         * @param container viewPager
         * @param position  要释放的位置
         * @param object    要释放的界面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            Log.d(TAG, "position == " + position + ", object" + object);
            container.removeView((View) object);
        }
    }

    class MyAddOnPageChangeListener implements ViewPager.OnPageChangeListener{

        /**
         * 当页面滚动了的时候回调的方法
         * @param position               当前页面的位置
         * @param positionOffset         滑动了当前页面的百分比
         * @param positionOffsetPixels   在屏幕上滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 当某个页面被选中的时候回调的方法
         * @param position   被选中页面的的位置
         */
        @Override
        public void onPageSelected(int position) {
            int realPosition = position%imageViews.size();
            //设置对应页面的文本信息
            textView.setText(imageDescription[realPosition]);
            //把上一个红色的设置为灰色
            ll_poing_group.getChildAt(prePosition).setEnabled(false);
            //当前的设置为红色
            ll_poing_group.getChildAt(realPosition).setEnabled(true);

            prePosition = realPosition;
        }

        /**
         * 当页面滚动状态变化的时候回调的方法
         * 静止->滑动
         * 滑动->静止
         * 静止->拖拽
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING){
                isDragging = true;
                Log.d(TAG, "SCROLL_STATE_DRAGGING 拖拽中");
                handler.removeCallbacksAndMessages(null);
            }else if(state == ViewPager.SCROLL_STATE_SETTLING){
                Log.d(TAG, "SCROLL_STATE_SETTLING 滚动中");
            }else if(state == ViewPager.SCROLL_STATE_IDLE && isDragging){
                isDragging = false;
                Log.d(TAG, "SCROLL_STATE_IDLE 静止中");
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(0, 4000);
            }
        }
    }
}
