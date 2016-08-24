package com.byids.hy.testpro.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

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
}
