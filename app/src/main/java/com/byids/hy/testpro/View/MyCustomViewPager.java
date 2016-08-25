package com.byids.hy.testpro.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by hy on 2016/8/19.
 * com.byids.hy.testpro.View.MyCustomViewPager
 */
public class MyCustomViewPager extends ViewPager{
    private boolean isCanScroll = true;

    public MyCustomViewPager(Context context) {
        super(context);
    }

    public MyCustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //设置viewpager能否左右滑动
    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (isCanScroll){
            super.scrollTo(x, y);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("result", "dispatchTouchEvent: =====viewpager的预处理======");
        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("result", "onTouchEvent: ========viewpager的处理事件==========");
        return super.onTouchEvent(ev);
    }
}
