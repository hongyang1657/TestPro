package com.byids.hy.testpro.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by gqgz2 on 2016/9/13.
 * com.byids.hy.testpro.View.LightValueHsScrollView
 */
public class LightValueHsScrollView extends HorizontalScrollView{

    public LightValueHsScrollView(Context context) {
        super(context);
    }

    public LightValueHsScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LightValueHsScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //添加滑动阻尼
    @Override
    public void fling(int velocityX) {
        super.fling(velocityX/3);
    }
}
