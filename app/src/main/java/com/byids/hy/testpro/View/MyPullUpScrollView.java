package com.byids.hy.testpro.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Scroller;

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
    private Scroller mScroller;


    public MyPullUpScrollView(Context context) {
        super(context);
        mScroller = new Scroller(context);
    }

    public MyPullUpScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public MyPullUpScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    //自定义方法  可以设定滚动的时间
    public void smoothScrollToSlow(int fx,int fy,int duration){
        int dx = fx - getScrollX();
        int dy = fy - getScrollY();
        smoothScrollBySlow(dx,dy,duration);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBySlow(int dx, int dy,int duration) {
        //设置mScroller的滚动偏移量
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy,duration);//scrollView使用的方法（因为可以触摸拖动）
//      mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);  //普通view使用的方法
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
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
        //Log.i("result", "onInterceptTouchEvent:=======上拉Scroll预处理========= ");
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
        //Log.i("result", "onTouchEvent: =======上拉Scroll处理事件============");
        return super.onTouchEvent(ev);
        //return false;
    }

}
