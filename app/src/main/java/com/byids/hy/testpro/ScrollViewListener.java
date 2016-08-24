package com.byids.hy.testpro;

import android.view.MotionEvent;

import com.byids.hy.testpro.View.MyCustomScrollView;

/**
 * Created by hy on 2016/8/15.
 */
public interface ScrollViewListener {
    void onScrollChanged(MyCustomScrollView scrollView, int x, int y, int oldx, int oldy);
    void onCommOnTouchEvent(MyCustomScrollView scrollView,MotionEvent ev);

}
