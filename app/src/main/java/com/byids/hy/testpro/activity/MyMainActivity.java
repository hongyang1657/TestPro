package com.byids.hy.testpro.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.byids.hy.testpro.OnMainListener;
import com.byids.hy.testpro.PullDownMenuListener;
import com.byids.hy.testpro.R;
import com.byids.hy.testpro.View.MyCustomViewPager;
import com.byids.hy.testpro.adapter.MyFragmentAdapter;
import com.byids.hy.testpro.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hy on 2016/8/15.
 */
public class MyMainActivity extends FragmentActivity{
    private String TAG = "result";

    private MyCustomViewPager viewPager;
    private MyFragmentAdapter adapter;
    private List<Fragment> viewList = new ArrayList<Fragment>();
    private GestureDetector gestureDetector;
    private boolean b1 = true;       //下拉菜单隐藏为true，出现为false
    private OnMainListener onMainListener;
    private int pagerState;

    //几个控件
    private TextView tvRoom;//房间名
    private ImageView ivMusic;
    private ImageView ivMedia;

    //背景图片
    private int[] ivBackList1 = {R.mipmap.back_10,R.mipmap.back_12,R.mipmap.back_13,R.mipmap.back_14};
    private int[] ivBackList2 = {R.mipmap.back_5,R.mipmap.back_6,R.mipmap.back_8,R.mipmap.back_9};
    private int[] ivBackList3 = {R.mipmap.back_1,R.mipmap.back_2,R.mipmap.back_3,R.mipmap.back_4};
    private int[] ivBackList = {R.mipmap.back_10,R.mipmap.back_12,R.mipmap.back_13,R.mipmap.back_14,R.mipmap.back_5,R.mipmap.back_6,R.mipmap.back_8,R.mipmap.back_9,R.mipmap.back_1,R.mipmap.back_2,R.mipmap.back_3,R.mipmap.back_4};

    //房间名数组
    private String[] roomList = {"客厅","卧室","书房","测试"};

    private MyFragment myFragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.my_main_layout);
        initView();
    }
    private void initView(){
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Log.i(TAG, "initView: ____________________"+width+"___________________"+height);

        ivMusic = (ImageView) findViewById(R.id.iv_music);
        ivMedia = (ImageView) findViewById(R.id.iv_media);
        ivMusic.setOnClickListener(mediaListener);
        ivMedia.setOnClickListener(mediaListener);
        int h = (int) ((int) height*0.03);
        int w = (int) ((int) width*0.035);
        int h1 = (int) ((int) height*0.021);
        ivMusic.setPadding(0,h,w,0);
        ivMedia.setPadding(0,h1,w,0);
        viewPager = (MyCustomViewPager) findViewById(R.id.id_vp);

        for (int i=0;i<4;i++){
            myFragment1 = new MyFragment(i,roomList[i],ivBackList);
            pullMenu(myFragment1);
            viewList.add(myFragment1);
        }

        /*myFragment1 = new MyFragment(0,"客厅",ivBackList1);
        pullMenu(myFragment1);
        viewList.add(myFragment1);
        MyFragment myFragment2 = new MyFragment(1,"卧室",ivBackList2);
        pullMenu(myFragment2);
        viewList.add(myFragment2);
        MyFragment myFragment3 = new MyFragment(2,"书房",ivBackList3);
        pullMenu(myFragment3);
        viewList.add(myFragment3);
        MyFragment myFragment4 = new MyFragment(3,"测试",ivBackList1);
        pullMenu(myFragment4);
        viewList.add(myFragment4);*/

        adapter = new MyFragmentAdapter(getSupportFragmentManager(),viewList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);  //多设置一页
        viewPager.addOnPageChangeListener(pagerChangeListener);
        //手势
    }

    View.OnClickListener mediaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_music:
                    Toast.makeText(MyMainActivity.this, "音乐", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.iv_media:
                    Toast.makeText(MyMainActivity.this, "媒体", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    private void pullMenu(MyFragment myF){
        //自定义的下拉监听
        myF.setPullDownMenuListener(new PullDownMenuListener() {
            @Override
            public void pullDown(boolean b) {
                b1 = b;
                if (b==false){      //下拉菜单出现时
                    //隐藏桌面小控件
                    ivMusic.setVisibility(View.GONE);
                    ivMedia.setVisibility(View.GONE);
                    //设置viewpager不能滑动
                    viewPager.setCanScroll(false);
                }else if (b==true){     //下拉菜单隐藏时
                    ivMusic.setVisibility(View.VISIBLE);
                    ivMedia.setVisibility(View.VISIBLE);
                    viewPager.setCanScroll(true);
                }
            }

            @Override
            public void scrollPager() {

            }
        });
    }

    //viewPager滑动监听
    private int roomPostion = 0;  //房间号
    ViewPager.OnPageChangeListener pagerChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //Log.i(TAG, "onPageScrolled: --------------"+position);
        }

        @Override
        public void onPageSelected(int position) {
            //传出position
            roomPostion = position;
        }

        //state状态  0：无触碰；  1：拖动；   2：松手
        @Override
        public void onPageScrollStateChanged(int state) {
            Log.i(TAG, "onPageScrollStateChanged: =-=-=-=-=-=-=-="+state);
            pagerState = state;
            boolean isChange = false;
            if (state==2){
                isChange = true;
            }

            if (state==1){
                scrollViewPager();
            }
            if (state==0){
                switch (roomPostion){
                    case 0:
                        downScrollViewPager("客厅");
                        break;
                    case 1:
                        downScrollViewPager("卧室");
                        break;
                    case 2:
                        downScrollViewPager("淋浴间");
                        break;
                    case 3:
                        downScrollViewPager("测试");
                        break;
                }
            }
            onMainListener.onMainAction(pagerState);   //Activity向Fragment通信
        }
    };
    //滑动viewpager时，控件消失
    private void scrollViewPager(){
        ivMusic.setVisibility(View.GONE);
        ivMedia.setVisibility(View.GONE);
    }
    //滑动结束后，控件显现
    private void downScrollViewPager(String roomName){
        if (b1==false){
            ivMusic.setVisibility(View.GONE);
            ivMedia.setVisibility(View.GONE);
        }else if (b1==true){
            showAnimation();
            ivMusic.setVisibility(View.VISIBLE);
            ivMedia.setVisibility(View.VISIBLE);
        }

    }
    //控件滑出显示的动画
    private void showAnimation(){
        ObjectAnimator.ofFloat(ivMusic,"translationX",200,-10,0).setDuration(800).start();
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(ivMedia,"translationX",250,-10,0);
        oa1.setDuration(1000);
        //oa1.setStartDelay(100);
        oa1.start();

    }



    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(10);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }

    /*@Override
    public void onAttachFragment(Fragment fragment) {
        try {
            onMainListener = (OnMainListener)myFragment1;
        } catch (Exception e) {

        }
        super.onAttachFragment(fragment);
    }*/
    public void onConnectionFragment(MyFragment fragment){
        onMainListener = (OnMainListener)fragment;
    }

}
