package com.byids.hy.testpro.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.byids.hy.testpro.PullUpMenuListener;

/**
 * Created by hy on 2016/8/19.
 * com.byids.hy.testpro.View.MyPullUpScrollView
 */
public class MyPullUpScrollView extends ScrollView{
    private PullUpMenuListener pullUpMenuListener;


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
        //return true;          //可以下拉菜单
    }

    //设置两个ScrollView联动监听
    public void setOnConnectionListener(PullUpMenuListener pullUpMenuListener){
        this.pullUpMenuListener = pullUpMenuListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (pullUpMenuListener != null) {
            pullUpMenuListener.onScrollConnection(this,l/3,t/3,oldl,oldt);
        }
    }
}
