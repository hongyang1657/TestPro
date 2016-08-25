package com.byids.hy.testpro;

import android.view.MotionEvent;

import com.byids.hy.testpro.View.MyCustomScrollView;
import com.byids.hy.testpro.View.MyPullUpScrollView;

/**
 * Created by hy on 2016/8/15.
 *
 */
public interface ScrollViewListener {
    void onScrollChanged(MyPullUpScrollView scrollView, int x, int y, int oldx, int oldy);
    void onCommOnTouchEvent(MyPullUpScrollView scrollView,MotionEvent ev);

}
