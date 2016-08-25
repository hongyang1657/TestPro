package com.byids.hy.testpro.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.byids.hy.testpro.PullUpMenuListener;
import com.byids.hy.testpro.ScrollViewListener;

/**
 * Created by hy on 2016/8/15.
 * com.byids.hy.testpro.View.MyCustomScrollView
 */
public class MyCustomScrollView extends ScrollView{

    private View inner;
    private GestureDetector gestureDetector;
    private ScrollViewListener scrollViewListener;
    //private PullUpMenuListener pullUpMenuListener;

    public MyCustomScrollView(Context context) {
        super(context);
    }

    public MyCustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setGestureDetector(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }

    /*public void setScrollViewListener(ScrollViewListener scrollViewListener){
        this.scrollViewListener = scrollViewListener;
    }*/




    /*@SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        if (getChildCount()>0){
            inner = getChildAt(0);
        }
    }*/

    /*@Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner==null){
            return super.onTouchEvent(ev);
        }else {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }*/

    /*public void commOnTouchEvent(MotionEvent ev) {
        scrollViewListener.onCommOnTouchEvent(this,ev);

    }*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
        //return false;
    }

    //设置两个ScrollView联动监听
    /*public void setDownOnConnectionListener(PullUpMenuListener pullUpMenuListener){
        this.pullUpMenuListener = pullUpMenuListener;
    }*/

    /*@Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //联动
        *//*if (pullUpMenuListener != null) {
            pullUpMenuListener.onScrollConnectionDown(this,l,t,oldl,oldt);
        }*//*

        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }*/

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("result", "onInterceptTouchEvent: ======下拉Scroll的预处理========");
        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("result", "onTouchEvent: ======下拉Scroll处理事件========");
        return super.onTouchEvent(ev);
        //return false;
    }
}
