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
 * Created by hy on 2016/8/19.
 * com.byids.hy.testpro.View.MyPullUpScrollView
 */
public class MyPullUpScrollView extends ScrollView{
    private PullUpMenuListener pullUpMenuListener;
    private View inner;
    private ScrollViewListener scrollViewListener;


    public MyPullUpScrollView(Context context) {
        super(context);
    }

    public MyPullUpScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPullUpScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);    //可以滑动上拉菜单  ，可以点击
        //return false;          //可以下拉菜单
    }

    //设置两个ScrollView联动监听
    public void setOnConnectionListener(PullUpMenuListener pullUpMenuListener){
        this.pullUpMenuListener = pullUpMenuListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (pullUpMenuListener != null) {
            //*********两个ScrollView联动的倍率是3，这里如果做修改，MyFragment里的数值都需要修改***********
            pullUpMenuListener.onScrollConnection(this,l/3,t/3,oldl,oldt);
        }
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public void commOnTouchEvent(MotionEvent ev) {
        scrollViewListener.onCommOnTouchEvent(this,ev);
    }



    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        if (getChildCount()>0){
            inner = getChildAt(0);
        }
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener){
        this.scrollViewListener = scrollViewListener;
    }

    //添加滑动阻尼   velocityY/3
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY/2);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("result", "onInterceptTouchEvent:=======上拉Scroll预处理========= ");
        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner==null){
            return super.onTouchEvent(ev);
            //return false;
        }else {
            commOnTouchEvent(ev);
        }
        Log.i("result", "onTouchEvent: =======上拉Scroll处理事件============");
        return super.onTouchEvent(ev);
        //return false;
    }

}
