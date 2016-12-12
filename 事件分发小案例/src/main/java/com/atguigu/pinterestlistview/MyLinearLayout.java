package com.atguigu.pinterestlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout {


	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//必须要super
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		super.dispatchTouchEvent(ev);
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float y = event.getY();
		float x = event.getX();
		if (x < getWidth()/3){
			getChildAt(0).dispatchTouchEvent(event);
		}else if (x > 2*getWidth()/3){
			getChildAt(2).dispatchTouchEvent(event);
		}else if (x >getWidth()/3 && x < 2*getWidth()/3){
			if (y < getHeight()/2){
				for (int i=0; i<getChildCount(); i++){
					getChildAt(i).dispatchTouchEvent(event);
				}
			}else if (y > getHeight()/2){
				getChildAt(1).dispatchTouchEvent(event);
			}
		}
		super.onTouchEvent(event);
		return true;
	}
}
