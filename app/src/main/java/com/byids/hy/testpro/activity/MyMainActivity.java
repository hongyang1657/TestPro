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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
public class MyMainActivity extends FragmentActivity {
    private String TAG = "result";

    private MyCustomViewPager viewPager;
    private MyFragmentAdapter adapter;
    private List<Fragment> viewList = new ArrayList<Fragment>();
    private GestureDetector gestureDetector;

    //几个控件
    private TextView tvRoom;//房间名
    private ImageView ivMusic;
    private ImageView ivMedia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_main_layout);
        initView();
    }
    private void initView(){
        tvRoom = (TextView) findViewById(R.id.tv_room);
        tvRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyMainActivity.this, "room", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1);
            }
        });
        ivMusic = (ImageView) findViewById(R.id.iv_music);
        ivMedia = (ImageView) findViewById(R.id.iv_media);
        viewPager = (MyCustomViewPager) findViewById(R.id.id_vp);


        MyFragment myFragment1 = new MyFragment("头1","房间1");
        pullMenu(myFragment1);
        viewList.add(myFragment1);
        MyFragment myFragment2 = new MyFragment("头2","房间2");
        pullMenu(myFragment2);
        viewList.add(myFragment2);
        MyFragment myFragment3 = new MyFragment("头3","房间3");
        pullMenu(myFragment3);
        viewList.add(myFragment3);

        adapter = new MyFragmentAdapter(getSupportFragmentManager(),viewList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);  //多设置一页
        viewPager.addOnPageChangeListener(pagerChangeListener);


        //手势

    }

    private void pullMenu(MyFragment myF){
        //自定义的下拉监听
        myF.setPullDownMenuListener(new PullDownMenuListener() {
            @Override
            public void pullDown(boolean b) {
                if (b==false){      //下拉菜单出现时
                    //隐藏桌面小控件
                    tvRoom.setVisibility(View.GONE);
                    ivMusic.setVisibility(View.GONE);
                    ivMedia.setVisibility(View.GONE);
                    //设置viewpager不能滑动
                    viewPager.setCanScroll(false);
                }else if (b==true){     //下拉菜单隐藏时
                    tvRoom.setVisibility(View.VISIBLE);
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
                }
            }
        }
    };
    //滑动viewpager时，控件消失
    private void scrollViewPager(){
        tvRoom.setVisibility(View.GONE);
        ivMusic.setVisibility(View.GONE);
        ivMedia.setVisibility(View.GONE);
    }
    //滑动结束后，控件显现
    private void downScrollViewPager(String roomName){
        tvRoom.setText(roomName);
        showAnimation();
        tvRoom.setVisibility(View.VISIBLE);
        ivMusic.setVisibility(View.VISIBLE);
        ivMedia.setVisibility(View.VISIBLE);
    }
    //控件滑出显示的动画
    private void showAnimation(){
        ObjectAnimator.ofFloat(tvRoom,"translationX",-400,6,0).setDuration(1200).start();
        ObjectAnimator.ofFloat(ivMusic,"translationX",200,-6,0).setDuration(1200).start();
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(ivMedia,"translationX",250,-6,0);
        oa1.setDuration(1300);
        //oa1.setStartDelay(100);
        oa1.start();

    }



    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ====activity处理事件======");
        for (MyOntouchListener listener : touchListeners){
            listener.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
        //return false;
    }

    private ArrayList<MyOntouchListener> touchListeners = new ArrayList<MyOntouchListener>();

    public void registerListener(MyOntouchListener listener){
        touchListeners.add(listener);
    }

    public void unRegisterListener(MyOntouchListener listener){
        touchListeners.remove(listener);
    }

    public interface MyOntouchListener{
        public void onTouchEvent(MotionEvent event);
    }*/


    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(
            10);

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


}
