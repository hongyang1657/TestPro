package com.byids.hy.testpro;

import com.byids.hy.testpro.View.MyCustomScrollView;
import com.byids.hy.testpro.View.MyPullUpScrollView;

/**
 * Created by hy on 2016/8/24.
 */
public interface PullUpMenuListener {
    void onScrollConnection(MyPullUpScrollView scrollView, int x, int y, int oldx, int oldy);
    //void onScrollConnectionDown(MyCustomScrollView scrollView, int x, int y, int oldx, int oldy);
}
