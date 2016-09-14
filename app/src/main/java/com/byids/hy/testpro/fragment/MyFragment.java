package com.byids.hy.testpro.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.byids.hy.testpro.PullDownMenuListener;
import com.byids.hy.testpro.PullUpMenuListener;
import com.byids.hy.testpro.R;
import com.byids.hy.testpro.ScrollViewListener;
import com.byids.hy.testpro.View.MyCustomScrollView;
import com.byids.hy.testpro.View.MyPullUpScrollView;
import com.byids.hy.testpro.activity.MyMainActivity;

import java.io.InputStream;
import java.util.Random;

/**
 * Created by hy on 2016/8/15.
 */
@SuppressLint({"ValidFragment", "NewApi"})
public class MyFragment extends Fragment implements PullUpMenuListener,GestureDetector.OnGestureListener {
    private String TAG = "result";
    private View view = null;
    
    private LinearLayout linearMenu;  //下拉菜单
    private LinearLayout linearClick;        //隐藏的，代替下拉菜单点击的四个按钮
    private TextView tvSet;
    private TextView tvMonitoring; //监控
    private TextView tvLock;
    private TextView tvSecurity;

    private int width;  //屏幕宽
    private int height;    //屏幕高
    private int btHeight;     //头部菜单的高度
    private int btHeight_X3;  //头部菜单高度的三倍  (因为两个ScrollView的联动为3倍率)
    private int AirConditionHeight;        //空调控制部分的布局高度
    private boolean isHeadShown = false;     //头菜单是否显示

    private TextView tvScene;       //场景
    private LinearLayout linearScene;    //场景控制部分
    private TextView tvLight;           //灯光
    //上拉菜单初始化浮现的部分（以上三个部分）的高度
    private int tvSceneHeight;
    private int linearSceneHeight;
    private int tvLightHeight;
    private int initFloatHeight;      //打开app时浮现部分的高度（三个部分高度之和）

    private int lightValueHeight1;      //灯光刻度条黄条的长度
    private int lightValueHeight2;      //灯光刻度条四个灰条的长度
    private int horizontalHeight;      //horizontalScrollView的长度
    private int rlLightValueHeight;    //relativeLayout的长度
    private int lightAxisExWidth;       //数轴左右多出部分的长度
    private int lightAxisinitValue;      //数轴初始化滑到0刻度时需要滚动的数值
    private int linearPullUpScrollHeight;        //上拉菜单的长度

    private ImageView ivBackGround;   //背景图片
    private ImageView ivBackGroundTrans;      //切换背景图片

    private MyCustomScrollView scrollView;
    private int roomIndex;     //房间标示
    private String roomName;   //房间名
    private GestureDetector detector;
    //private MyMainActivity.MyOntouchListener listener;
    private MyMainActivity.MyOnTouchListener myOnTouchListener;
    private Activity activity;
    private ImageView btPullMenu;
    private RelativeLayout linear;

    //上拉菜单
    MyPullUpScrollView svPullUpMenu;
    PullDownMenuListener pullDownMenuListener;//自定义的下拉监听
    ViewGroup.LayoutParams params;
    LinearLayout linearPullUpScroll;  //上拉菜单整个布局

    private int scrollY; //下滑的距离
    private static final int size = 4;
    private float y;
    private int position;    //上拉菜单滑动位置
    private int mScrollY;    //滑动停止的位置
    private int lightSVPosition;    //灯光调光模块动态值

    //---------------------控制部分 上---------------------
    private TextView tvRoomName;     //房间名部分
    private Button btShezhi;
    private Button btJiankong;
    private Button btMensuo;
    private Button btBufang;

    //场景
    private RelativeLayout rlXiuxian;
    private ImageView ivXiuxianOut;      //内部图片和文字
    private ImageView ivXiuxianIn;
    private TextView tvXiuxian;

    private RelativeLayout rlYule;
    private ImageView ivYule1;
    private ImageView ivYule2;
    private ImageView ivYule3;
    private TextView tvYule;

    private RelativeLayout rlJuhui;
    private ImageView ivJuhui1;
    private ImageView ivJuhui2;
    private TextView tvJuhui;

    private RelativeLayout rlLikai;
    private ImageView ivLikai;
    private TextView tvLikai;


    //灯光
    private HorizontalScrollView hsLightValue;
    private RelativeLayout rlLightValue;
    private ImageView ivLightValue1;      //黄色指针
    private ImageView ivLightValue2;      //灰色指针
    private RelativeLayout rlLight;
    private ImageView ivLightSwitch;
    private TextView tvLightSwitch;
    private TextView tvLightValue;    //灯光亮度值

    //窗帘（布帘，纱帘），卷帘（左，右卷帘）
    private RelativeLayout rlBulian,rlJuanlian;
    private ImageView ivBulianHead,ivJuanlianHead;
    private ImageView ivBulian1,ivBulian2,ivBulian3,ivBulian4,ivBulian5,ivBulian6,
            ivJuanlian1,ivJuanlian2,ivJuanlian3,ivJuanlian4,ivJuanlian5,ivJuanlian6;
    private ImageView ivBulianHead1,ivJuanlianHead1;
    private TextView tvBulian,tvJuanlian;

    private RelativeLayout rlShalian,rlJuanlianR;
    private ImageView ivShalianHead,ivJuanlianHeadR;
    private ImageView ivShalian1,ivShalian2,ivShalian3,ivShalian4,ivShalian5,ivShalian6,
            ivJuanlian1R,ivJuanlian2R,ivJuanlian3R,ivJuanlian4R,ivJuanlian5R,ivJuanlian6R;
    private ImageView ivShalianHead1,ivJuanlianHead1R;
    private TextView tvShalian,tvJuanlianR;
    //窗帘,卷帘，全关 全开
    private RelativeLayout rlAll,rlStop,rlAllJuan,rlStopJuan;
    private ImageView ivAll,ivStop,ivAllJuan,ivStopJuan;
    private TextView tvAll,tvStop,tvAllJuan,tvStopJuan;


    //空调
    private RelativeLayout rlKongtiao;
    private ImageView ivKongtiaoSwitch;
    private TextView tvKongtiaoSwitch;

    private TextView tvKongtiaoTemp;
    private RelativeLayout rlKongtiaoMoshi;
    private ImageView ivKongtiaoMoshi;
    private TextView tvKongtiaoMoshi;

    private SeekBar sbTemp;
    private RelativeLayout rlAirControl1;
    private RelativeLayout rlAirControl2;
    private RelativeLayout rlAirControl3;

    //控制部分 下
    private LinearLayout linearAirDetails;

    Random random1 = new Random();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    setBGPAnimation();
                    //svPullUpMenu.setLayoutParams(params);            //更新scrollView？
                    Log.i(TAG, "handleMessage:切换背景图 ");
                    break;
                default:
                    break;
            }
        }
    };
    private Handler handlerAir = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearAirDetails.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }

        }
    };


    private int[] backList;  //背景图片组
    public MyFragment(int roomIndex,String roomName,int[] backList) {
        this.roomIndex = roomIndex;
        this.roomName = roomName;
        this.backList = backList;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout,null);

        initView();

        btPullMenu.setOnClickListener(pullMenuListener);
        //上拉菜单   子scrollview
        svPullUpMenu = (MyPullUpScrollView) view.findViewById(R.id.scroll_pull_up);
        linearPullUpScroll = (LinearLayout) view.findViewById(R.id.linear_pull_up_scroll);
        svPullUpMenu.setOnConnectionListener(this);
        svPullUpMenu.setScrollViewListener(pullDownListener);

        svPullUpMenu.setOnTouchListener(new View.OnTouchListener() {
            //判断ScrollView是否停下
            private int lastY = 0;
            private int touchEventId = -9983761;
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    View scroller = (View) msg.obj;
                    if (msg.what == touchEventId) {
                        if (lastY == scroller.getScrollY()) {
                            handleStop(scroller);
                        } else {
                            handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller), 15);
                            lastY = scroller.getScrollY();
                        }
                    }
                }
            };

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    handler.sendMessageDelayed(handler.obtainMessage(touchEventId, view), 5);
                }

                svPullUpMenu.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

            private void handleStop(Object view) {
                ScrollView scroller = (ScrollView) view;
                mScrollY = scroller.getScrollY();
                Log.i(TAG, "handleStop: ----------mScrollY----------"+mScrollY);
                /*if (mScrollY>(btHeight_X3/2)&&mScrollY<=btHeight_X3){
                    scrollToBottom();        //滑动到隐藏头
                    isHeadShown = false;
                    pullDown(true);

                }else if(mScrollY<=(btHeight_X3/2)){
                    scrollToTop();           //滑动到显示头
                    isHeadShown = true;
                    pullDown(false);
                }*/
                if(mScrollY<btHeight_X3){
                    scrollToBottom();        //滑动到隐藏头
                    isHeadShown = false;
                    pullDown(true);
                }
            }
        });
        svPullUpMenu.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                position = i1;
                Log.i(TAG, "onScrollChange: i="+i+"   i1="+i1+"   i2="+i2+"   i3="+i3);
            }
        });

        //获取控件高度
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        linearMenu.measure(w, h);
        btHeight = linearMenu.getMeasuredHeight();         //头菜单的高度
        btHeight_X3 = btHeight*3;

        //获取浮现的三个部分的高度
        tvScene.measure(w,h);
        linearScene.measure(w,h);
        tvLight.measure(w,h);
        tvSceneHeight = tvScene.getMeasuredHeight();
        linearSceneHeight = linearScene.getMeasuredHeight();
        tvLightHeight = tvLight.getMeasuredHeight();
        initFloatHeight = tvSceneHeight+linearSceneHeight+tvLightHeight;  //app打开时下面浮动部分的高度
        Log.i(TAG, "onCreateView:app打开时下面浮动部分的高度 "+tvSceneHeight+"-----------"+linearSceneHeight+"-----------------"+tvLightHeight);

        //整个上拉菜单的高度
        svPullUpMenu.measure(w,h);
        linearPullUpScrollHeight = svPullUpMenu.getMeasuredHeight();
        Log.i(TAG, "onCreateView: 整个上拉菜单的高度"+linearPullUpScrollHeight);

        //获取灯光刻度条图片的长度
        ivLightValue1.measure(w,h);
        lightValueHeight1 = ivLightValue1.getMeasuredWidth();
        ivLightValue2.measure(w,h);
        lightValueHeight2 = ivLightValue2.getMeasuredWidth();
        lightAxisExWidth = (lightValueHeight1+lightValueHeight2)*9+3;        //初始化数轴左右多出部分的长度

        //获取灯光刻度条HorizontalScrollView控件的宽度
        hsLightValue.measure(w,h);
        horizontalHeight = hsLightValue.getMeasuredWidthAndState();
        //获取灯光刻度条rlLightValue控件的宽度
        ViewTreeObserver vto2 = rlLightValue.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlLightValue.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                rlLightValueHeight = rlLightValue.getWidth();      //数轴可显示部分的长度
                Log.i(TAG, "onCreateView: 控件的长度？？"+rlLightValueHeight);
                lightAxisinitValue = lightAxisExWidth - (rlLightValueHeight/2);
                Log.i(TAG, "onCreateView: !!!!!!!!!!!!!!!!!!!!!"+lightAxisinitValue);
                //初始化hsScrollView的位置（指针指向0刻度）
                Log.i(TAG, "run: ----------------------"+lightAxisinitValue);
                hsLightValue.scrollTo(lightAxisinitValue,0);

            }
        });

        //设置


        scrollView = (MyCustomScrollView) view.findViewById(R.id.id_scroll);
        //scrollView.setDownOnConnectionListener(this);
        /*scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/
        //获取屏幕宽高，设置图片大小一致
        WindowManager wm = this.getActivity().getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        params = ivBackGround.getLayoutParams();
        int statusHeight = getStatusBar();
        params.height = height - statusHeight+500;       //设置图片长度，使上拉时有图片上升的一个效果
        params.width = width;
        ivBackGround.setLayoutParams(params);
        ivBackGroundTrans.setLayoutParams(params);

        //设置头部控件高度和点击按钮一致
        ViewGroup.LayoutParams layoutParams = linearClick.getLayoutParams();
        layoutParams.height = btHeight;
        linearClick.setLayoutParams(layoutParams);

        tvRoomName.setText(roomName);    //设置房间名
        ViewGroup.LayoutParams layoutParams1 = tvRoomName.getLayoutParams();
        layoutParams1.height = height-initFloatHeight+btHeight*2;     //设置房间textview的高度 = 屏幕高 - 浮出部分高 + 上部控件的高*2
        tvRoomName.setLayoutParams(layoutParams1);

        scrollToBottomInit();   //初始化ScrollView的位置

        //手势
        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), this);
        myOnTouchListener = new MyMainActivity.MyOnTouchListener() {
            @Override
            public boolean onTouch(MotionEvent ev) {
                boolean result = mGestureDetector.onTouchEvent(ev);
                return result;
            }
        };

        ((MyMainActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        activity = getActivity();
        super.onActivityCreated(savedInstanceState);

    }

    private void initView(){

        linear = (RelativeLayout) view.findViewById(R.id.linear);
        linearMenu = (LinearLayout) view.findViewById(R.id.linear_menu);
        linearClick = (LinearLayout) view.findViewById(R.id.linear_up_menu_click);

        ivBackGround = (ImageView) view.findViewById(R.id.id_iv);
        ivBackGroundTrans = (ImageView) view.findViewById(R.id.id_iv1);

        btPullMenu = (ImageView) view.findViewById(R.id.button);
        tvSet = (TextView) view.findViewById(R.id.tv_set);
        tvMonitoring = (TextView) view.findViewById(R.id.tv_monitoring);
        tvLock = (TextView) view.findViewById(R.id.tv_lock);
        tvSecurity = (TextView) view.findViewById(R.id.tv_security);

        //控制部分  上
        tvRoomName = (TextView) view.findViewById(R.id.tv_blank);
        btShezhi = (Button) view.findViewById(R.id.bt_shezhi);
        btJiankong = (Button) view.findViewById(R.id.bt_jiankong);
        btMensuo = (Button) view.findViewById(R.id.bt_mensuo);
        btBufang = (Button) view.findViewById(R.id.bt_bufang);
        btShezhi.setOnClickListener(clickListener);
        btJiankong.setOnClickListener(clickListener);
        btMensuo.setOnClickListener(clickListener);
        btBufang.setOnClickListener(clickListener);
//        linearClick.

        initControler();    //初始化控制部分  下
        initBackGround();   //初始化背景图片
    }

    private void initControler(){
        //初始化 浮现的部分
        tvScene = (TextView) view.findViewById(R.id.tv_scene_name);
        linearScene = (LinearLayout) view.findViewById(R.id.linear_scene);
        tvLight = (TextView) view.findViewById(R.id.tv_light);

        //场景
         rlXiuxian = (RelativeLayout) view.findViewById(R.id.rl_xiuxian);
         rlYule = (RelativeLayout) view.findViewById(R.id.rl_yule);
         rlJuhui = (RelativeLayout) view.findViewById(R.id.rl_juhui);
         rlLikai = (RelativeLayout) view.findViewById(R.id.rl_likai);
        //灯光
         rlLight = (RelativeLayout) view.findViewById(R.id.relative_light_switch);
        //窗帘
        rlBulian = (RelativeLayout) view.findViewById(R.id.rl_bulian);
        rlShalian = (RelativeLayout) view.findViewById(R.id.rl_shalian);
        rlAll = (RelativeLayout) view.findViewById(R.id.rl_quanguan);
        rlStop = (RelativeLayout) view.findViewById(R.id.rl_tingzhi);
        //卷帘
        rlJuanlian = (RelativeLayout) view.findViewById(R.id.rl_juanlian);
        rlJuanlianR = (RelativeLayout) view.findViewById(R.id.rl_juanlian_r);
        rlAllJuan = (RelativeLayout) view.findViewById(R.id.rl_quanguan_juanlian);
        rlStopJuan = (RelativeLayout) view.findViewById(R.id.rl_tingzhi_juanlian);
        //空调
         rlKongtiao = (RelativeLayout) view.findViewById(R.id.rl_kongtiao_kaiguan);
         rlKongtiaoMoshi = (RelativeLayout) view.findViewById(R.id.rl_kongtiao_moshi);
        linearAirDetails = (LinearLayout) view.findViewById(R.id.linear_air_details);
         sbTemp = (SeekBar) view.findViewById(R.id.sb_air_temp);
         rlAirControl1 = (RelativeLayout) view.findViewById(R.id.rl_air_control_1);
         rlAirControl2 = (RelativeLayout) view.findViewById(R.id.rl_air_control_2);
         rlAirControl3 = (RelativeLayout) view.findViewById(R.id.rl_air_control_3);

        //---------------------------需要做动画的图片--------------------------
        //场景
        ivXiuxianOut = (ImageView) view.findViewById(R.id.iv_xiuxian_out);
        ivXiuxianIn = (ImageView) view.findViewById(R.id.iv_xiuxian_in);
        tvXiuxian = (TextView) view.findViewById(R.id.tv_xiuxian);
        ivYule1 = (ImageView) view.findViewById(R.id.iv_yule_1);
        ivYule2 = (ImageView) view.findViewById(R.id.iv_yule_2);
        ivYule3 = (ImageView) view.findViewById(R.id.iv_yule_3);
        tvYule = (TextView) view.findViewById(R.id.tv_yule);
        ivJuhui1 = (ImageView) view.findViewById(R.id.iv_juhui_1);
        ivJuhui2 = (ImageView) view.findViewById(R.id.iv_juhui_2);
        tvJuhui = (TextView) view.findViewById(R.id.tv_juhui);
        ivLikai = (ImageView) view.findViewById(R.id.iv_likai);
        tvLikai = (TextView) view.findViewById(R.id.tv_likai);
        //灯光
        hsLightValue = (HorizontalScrollView) view.findViewById(R.id.hs_light_point);
        rlLightValue = (RelativeLayout) view.findViewById(R.id.relative_light_control);
        ivLightValue1 = (ImageView) view.findViewById(R.id.iv_light_point1);
        ivLightValue2 = (ImageView) view.findViewById(R.id.iv_light_point2);
        ivLightSwitch = (ImageView) view.findViewById(R.id.iv_light_switch);
        tvLightSwitch = (TextView) view.findViewById(R.id.tv_light_kaiguan);
        tvLightValue = (TextView) view.findViewById(R.id.tv_light_point);
        //窗帘 布帘
        ivBulianHead = (ImageView) view.findViewById(R.id.iv_bulian_head);
        ivBulian1 = (ImageView) view.findViewById(R.id.iv_bulian_1);
        ivBulian2 = (ImageView) view.findViewById(R.id.iv_bulian_2);
        ivBulian3 = (ImageView) view.findViewById(R.id.iv_bulian_3);
        ivBulian4 = (ImageView) view.findViewById(R.id.iv_bulian_4);
        ivBulian5 = (ImageView) view.findViewById(R.id.iv_bulian_5);
        ivBulian6 = (ImageView) view.findViewById(R.id.iv_bulian_6);
        ivBulianHead1 = (ImageView) view.findViewById(R.id.iv_bulian_head_1);
        tvBulian = (TextView) view.findViewById(R.id.tv_bulian);
        //纱帘
        ivShalianHead = (ImageView) view.findViewById(R.id.iv_shalian_head);
        ivShalian1 = (ImageView) view.findViewById(R.id.iv_shalian_1);
        ivShalian2 = (ImageView) view.findViewById(R.id.iv_shalian_2);
        ivShalian3 = (ImageView) view.findViewById(R.id.iv_shalian_3);
        ivShalian4 = (ImageView) view.findViewById(R.id.iv_shalian_4);
        ivShalian5 = (ImageView) view.findViewById(R.id.iv_shalian_5);
        ivShalian6 = (ImageView) view.findViewById(R.id.iv_shalian_6);
        ivShalianHead1 = (ImageView) view.findViewById(R.id.iv_shalian_head_2);
        tvShalian = (TextView) view.findViewById(R.id.tv_shalian);
        //左 卷帘
        ivJuanlianHead = (ImageView) view.findViewById(R.id.iv_juanlian_head);
        ivJuanlian1 = (ImageView) view.findViewById(R.id.iv_juanlian_1);
        ivJuanlian2 = (ImageView) view.findViewById(R.id.iv_juanlian_2);
        ivJuanlian3 = (ImageView) view.findViewById(R.id.iv_juanlian_3);
        ivJuanlian4 = (ImageView) view.findViewById(R.id.iv_juanlian_4);
        ivJuanlian5 = (ImageView) view.findViewById(R.id.iv_juanlian_5);
        ivJuanlian6 = (ImageView) view.findViewById(R.id.iv_juanlian_6);
        ivJuanlianHead1 = (ImageView) view.findViewById(R.id.iv_juanlian_head_1);
        tvJuanlian = (TextView) view.findViewById(R.id.tv_juanlian);
        //右 卷帘
        ivJuanlianHeadR = (ImageView) view.findViewById(R.id.iv_juanlian_head_r);
        ivJuanlian1R = (ImageView) view.findViewById(R.id.iv_juanlian_1_r);
        ivJuanlian2R = (ImageView) view.findViewById(R.id.iv_juanlian_2_r);
        ivJuanlian3R = (ImageView) view.findViewById(R.id.iv_juanlian_3_r);
        ivJuanlian4R = (ImageView) view.findViewById(R.id.iv_juanlian_4_r);
        ivJuanlian5R = (ImageView) view.findViewById(R.id.iv_juanlian_5_r);
        ivJuanlian6R = (ImageView) view.findViewById(R.id.iv_juanlian_6_r);
        ivJuanlianHead1R = (ImageView) view.findViewById(R.id.iv_juanlian_head_1_r);
        tvJuanlianR = (TextView) view.findViewById(R.id.tv_juanlian_r);
        //全开 全关
        ivAll = (ImageView) view.findViewById(R.id.iv_chuanglian_all);
        tvAll = (TextView) view.findViewById(R.id.tv_all);
        ivStop = (ImageView) view.findViewById(R.id.iv_chuanglian_stop);
        tvStop = (TextView) view.findViewById(R.id.tv_chuanglian_stop);
        ivAllJuan = (ImageView) view.findViewById(R.id.iv_chuanglian_all_juanlian);
        tvAllJuan = (TextView) view.findViewById(R.id.tv_all_juanlian);
        ivStopJuan = (ImageView) view.findViewById(R.id.iv_chuanglian_stop_juanlian);
        tvStopJuan = (TextView) view.findViewById(R.id.tv_chuanglian_stop_juanlian);
        //空调
        ivKongtiaoSwitch = (ImageView) view.findViewById(R.id.iv_kongtiao_kaiguan);
        tvKongtiaoSwitch = (TextView) view.findViewById(R.id.tv_kongtiao_kaiguan);
        tvKongtiaoTemp = (TextView) view.findViewById(R.id.tv_kongtiao_wendu);
        ivKongtiaoMoshi = (ImageView) view.findViewById(R.id.iv_kongtiao_sleep);
        tvKongtiaoMoshi = (TextView) view.findViewById(R.id.tv_kongtiao_sleep);


        rlXiuxian.setOnClickListener(controlListener);
        rlYule.setOnClickListener(controlListener);
        rlJuhui.setOnClickListener(controlListener);
        rlLikai.setOnClickListener(controlListener);

        rlLight.setOnClickListener(controlListener);

        rlBulian.setOnClickListener(controlListener);
        rlShalian.setOnClickListener(controlListener);
        rlAll.setOnClickListener(controlListener);
        rlStop.setOnClickListener(controlListener);
        rlJuanlian.setOnClickListener(controlListener);
        rlJuanlianR.setOnClickListener(controlListener);
        rlAllJuan.setOnClickListener(controlListener);
        rlStopJuan.setOnClickListener(controlListener);

        rlKongtiao.setOnClickListener(controlListener);
        rlKongtiaoMoshi.setOnClickListener(controlListener);
        rlAirControl1.setOnClickListener(controlListener);
        rlAirControl2.setOnClickListener(controlListener);
        rlAirControl3.setOnClickListener(controlListener);

        sbTemp.setOnSeekBarChangeListener(tempSeekBarListener);  //空调调温SeekBar
        hsLightValue.setOnScrollChangeListener(hsChangeListener);
        //判断hsScrollView是否停下
        hsLightValue.setOnTouchListener(new View.OnTouchListener() {
            private int lastX = 0;
            private int touchEventId = -9983762;
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    View scroller = (View) msg.obj;
                    if (msg.what == touchEventId) {
                        if (lastX == scroller.getScrollX()) {
                            handleStop(scroller);
                        } else {
                            handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller),15);
                            lastX = scroller.getScrollX();
                        }
                    }
                }
            };

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    handler.sendMessageDelayed(handler.obtainMessage(touchEventId, view), 5);
                }
                hsLightValue.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

            private void handleStop(Object view) {
                HorizontalScrollView scroller = (HorizontalScrollView) view;
                int mScrollX = scroller.getScrollX();
                Log.i(TAG, "handleStop: ----------mScrollX11----------"+mScrollX);
                Log.i(TAG, "handleStop: ----------lightAxisinitValue----------"+lightAxisinitValue);

                if(mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*0.5){
                    hsScrollMeasure(0);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*0.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*1.5){
                    hsScrollMeasure(1);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*1.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*2.5){
                    hsScrollMeasure(2);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*2.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*3.5){
                    hsScrollMeasure(3);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*3.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*4.5){
                    hsScrollMeasure(4);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*4.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*5.5){
                    hsScrollMeasure(5);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*5.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*6.5){
                    hsScrollMeasure(6);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*6.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*7.5){
                    hsScrollMeasure(7);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*7.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*8.5){
                    hsScrollMeasure(8);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*8.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*9.5){
                    hsScrollMeasure(9);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*9.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*10.5){
                    hsScrollMeasure(10);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*10.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*11.5){
                    hsScrollMeasure(11);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*11.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*12.5){
                    hsScrollMeasure(12);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*12.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*13.5){
                    hsScrollMeasure(13);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*13.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*14.5){
                    hsScrollMeasure(14);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*14.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*15.5){
                    hsScrollMeasure(15);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*15.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*16.5){
                    hsScrollMeasure(16);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*16.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*17.5){
                    hsScrollMeasure(17);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*17.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*18.5){
                    hsScrollMeasure(18);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*18.5 && mScrollX<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*19.5){
                    hsScrollMeasure(19);
                }else if (mScrollX>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*19.5){
                    hsScrollMeasure(20);
                }

            }

            /*
            * lightValue 灯光亮度数值0~100
            * scrollTo  0~20   21个调光刻度
            *
            * */
            private void hsScrollMeasure(final int scrollTo){
                //Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        hsLightValue.smoothScrollTo(lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*scrollTo,0);
                    }
                });
            }
        });

    }

    //--------------------------------------点击事件-------------------------------------------
    View.OnClickListener controlListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_xiuxian:

                    setScaleAnimation(rlXiuxian);
                    clickXiuxian();
                    break;
                case R.id.rl_yule:
                    setScaleAnimation(rlYule);
                    clickYule();
                    break;
                case R.id.rl_juhui:
                    setScaleAnimation(rlJuhui);
                    clickJuhui();
                    break;
                case R.id.rl_likai:
                    setScaleAnimation(rlLikai);
                    clickLikai();
                    break;
                case R.id.relative_light_switch:
                    setScaleAnimation(rlLight);
                    break;
                case R.id.rl_bulian:
                    setScaleAnimation(rlBulian);
                    clickBulian();
                    break;
                case R.id.rl_shalian:
                    setScaleAnimation(rlShalian);
                    clickShalian();
                    break;
                case R.id.rl_quanguan:
                    setScaleAnimation(rlAll);
                    clickAll();
                    break;
                case R.id.rl_tingzhi:
                    setScaleAnimation(rlStop);
                    clickStop();
                    break;
                case R.id.rl_juanlian:
                    setScaleAnimation(rlJuanlian);
                    clickJuanlian();
                    break;
                case R.id.rl_juanlian_r:
                    setScaleAnimation(rlJuanlianR);
                    clickJuanlianR();
                    break;
                case R.id.rl_quanguan_juanlian:
                    setScaleAnimation(rlAllJuan);
                    clickAllJuan();
                    break;
                case R.id.rl_tingzhi_juanlian:
                    setScaleAnimation(rlStopJuan);
                    clickStopJuan();
                    break;
                case R.id.rl_kongtiao_kaiguan:
                    setScaleAnimation(rlKongtiao);
                    clickAirSwitch();
                    break;
                case R.id.rl_kongtiao_moshi:
                    setScaleAnimation(rlKongtiaoMoshi);
                    Toast.makeText(activity, "睡眠 模式", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_air_control_1:
                    setScaleAnimation(rlAirControl1);
                    Toast.makeText(activity, "制冷", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_air_control_2:
                    setScaleAnimation(rlAirControl2);
                    Toast.makeText(activity, "风速", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_air_control_3:
                    setScaleAnimation(rlAirControl3);
                    Toast.makeText(activity, "定时", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //灯光亮度调节监听
    View.OnScrollChangeListener hsChangeListener = new View.OnScrollChangeListener() {
        @Override
        public void onScrollChange(View view, int i, int i1, int i2, int i3) {
            lightSVPosition = i;
            Log.i(TAG, "onScrollChange: "+i);
            if(lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*0.5){
                tvLightValue.setText("0");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*0.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*1.5){
                tvLightValue.setText("5");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*1.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*2.5){
                tvLightValue.setText("10");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*2.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*3.5){
                tvLightValue.setText("15");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*3.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*4.5){
                tvLightValue.setText("20");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*4.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*5.5){
                tvLightValue.setText("25");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*5.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*6.5){
                tvLightValue.setText("30");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*6.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*7.5){
                tvLightValue.setText("35");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*7.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*8.5){
                tvLightValue.setText("40");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*8.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*9.5){
                tvLightValue.setText("45");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*9.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*10.5){
                tvLightValue.setText("50");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*10.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*11.5){
                tvLightValue.setText("55");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*11.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*12.5){
                tvLightValue.setText("60");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*12.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*13.5){
                tvLightValue.setText("65");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*13.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*14.5){
                tvLightValue.setText("70");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*14.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*15.5){
                tvLightValue.setText("75");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*15.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*16.5){
                tvLightValue.setText("80");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*16.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*17.5){
                tvLightValue.setText("85");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*17.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*18.5){
                tvLightValue.setText("90");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*18.5 && lightSVPosition<=lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*19.5){
                tvLightValue.setText("95");
            }else if (lightSVPosition>lightAxisinitValue+(lightValueHeight1+lightValueHeight2)*19.5){
                tvLightValue.setText("100");
            }
        }
    };

    //空调温度调节滑块监听
    SeekBar.OnSeekBarChangeListener tempSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (i<10){
                tvKongtiaoTemp.setText("16°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
            }else if (i>=10&&i<20){
                tvKongtiaoTemp.setText("17°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
            }else if (i>=20&&i<30){
                tvKongtiaoTemp.setText("18°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorBule));
            }else if (i>=30&&i<40){
                tvKongtiaoTemp.setText("19°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorBule));
            }else if (i>=40&&i<50){
                tvKongtiaoTemp.setText("20°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorBule));
            }else if (i>=50&&i<60){
                tvKongtiaoTemp.setText("21°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorGreen));
            }else if (i>=60&&i<70){
                tvKongtiaoTemp.setText("22°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorGreen));
            }else if (i>=70&&i<80){
                tvKongtiaoTemp.setText("23°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorGreen));
            }else if (i>=80&&i<90){
                tvKongtiaoTemp.setText("24°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorYellowGreen));
            }else if (i>=90&&i<100){
                tvKongtiaoTemp.setText("25°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorYellowGreen));
            }else if (i>=100&&i<110){
                tvKongtiaoTemp.setText("26°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorYellowGreen));
            }else if (i>=110&&i<120){
                tvKongtiaoTemp.setText("27°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorYellow));
            }else if (i>=120&&i<130){
                tvKongtiaoTemp.setText("28°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorYellow));
            }else if (i>=130&&i<140){
                tvKongtiaoTemp.setText("29°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorTextActive));
            }else if (i>=140){
                tvKongtiaoTemp.setText("30°");
                tvKongtiaoTemp.setTextColor(activity.getResources().getColor(R.color.colorTextActive));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    

    //初始化背景图片
    private void initBackGround(){
        //随机取出一张图片
        //ivBackGround.setImageResource(backList[random.nextInt(backList.length)]);
        //ivBackGroundTrans.setImageResource(backList[random.nextInt(backList.length)]);
        Bitmap bitmap = readBitMap(getActivity(),backList[random1.nextInt(backList.length)]);
        ivBackGround.setImageBitmap(bitmap);
        Bitmap bitmap1 = readBitMap(getActivity(),backList[random1.nextInt(backList.length)]);
        ivBackGroundTrans.setImageBitmap(bitmap1);

        //开线程  隔一段时间切换图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(50000);
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //设置切换图片的动画
    private int BGPFlag = 0;
    private void setBGPAnimation(){

        if (BGPFlag==0){
            ObjectAnimator animator1 = new ObjectAnimator().ofFloat(ivBackGround,"alpha",1f,0f).setDuration(1000);
            animator1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {          //动画结束的监听
                    super.onAnimationEnd(animation);
                    Bitmap bitmap = readBitMap(activity,backList[random1.nextInt(backList.length)]);
                    ivBackGround.setImageBitmap(bitmap);
                }
            });
            animator1.start();

            ObjectAnimator.ofFloat(ivBackGroundTrans,"alpha",0f,1f).setDuration(1000).start();

            BGPFlag = 1;
        }else if (BGPFlag==1){
            ObjectAnimator.ofFloat(ivBackGround,"alpha",0f,1f).setDuration(1000).start();
            ObjectAnimator animator2 = new ObjectAnimator().ofFloat(ivBackGroundTrans,"alpha",1f,0f).setDuration(1000);
            animator2.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {          //动画结束的监听
                    super.onAnimationEnd(animation);
                    Bitmap bitmap = readBitMap(activity,backList[random1.nextInt(backList.length)]);
                    ivBackGroundTrans.setImageBitmap(bitmap);
                }
            });
            animator2.start();
            BGPFlag = 0;
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);          //在锤子上测试，时间长了 java.io.FileNotFoundException
        return BitmapFactory.decodeStream(is,null,opt);
    }

    //---------------------设置点击按钮的缩放效果动画---------------------
    private void setScaleAnimation(View view){
        ObjectAnimator.ofFloat(view,"scaleX",1f,0.6f,1f).setDuration(400).start();
        ObjectAnimator.ofFloat(view,"scaleY",1f,0.6f,1f).setDuration(400).start();
    }


    //头部控件的点击事件
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.bt_shezhi:
                    Toast.makeText(getActivity(), "设置", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_jiankong:
                    Toast.makeText(getActivity(), "监控", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_mensuo:
                    Toast.makeText(getActivity(), "门锁", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_bufang:
                    Toast.makeText(getActivity(), "布防", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //自定义设置的下拉菜单监听
    public void pullDown(boolean f){
        pullDownMenuListener.pullDown(f);
    }
    public void scrollPager(){
        pullDownMenuListener.scrollPager();
    }

    public void setPullDownMenuListener(PullDownMenuListener pullDownMenuListener){
        this.pullDownMenuListener = pullDownMenuListener;
    }

    //上拉菜单滑动监听
    ScrollViewListener pullDownListener = new ScrollViewListener() {
        @Override
        public void onScrollChanged(MyPullUpScrollView scrollView, int x, int y, int oldx, int oldy) {
            int hideHeight = (initFloatHeight*2)+btHeight_X3;    //控件隐藏的临界点

            //加入手势，松开的时候 判断距离，选择菜单栏出现还是隐藏的动画
            scrollY = y;
            //Log.i("result", "onScrollChanged:-----------x-----------"+x+"----------y---------"+y);
            Log.i(TAG, "onScrollChanged: -------scrollY--------"+scrollY);
            //判断向下滑的时候(y<控件的高度)    button消失
            if (scrollY==btHeight_X3) {           //乘以三是因为手指移动和滑动是三倍率
                btPullMenu.setVisibility(View.VISIBLE);
                ObjectAnimator.ofFloat(btPullMenu,"alpha",0f,1f).setDuration(400).start();
            }else if (scrollY<btHeight_X3){
                btPullMenu.setVisibility(View.GONE);
            }
            //向上滑动到一大半时，隐藏桌面两个小控件，房间名。
            if (scrollY<hideHeight){
                tvRoomName.setAlpha(1f);
            }else if (scrollY>=hideHeight && scrollY<hideHeight+10){
                tvRoomName.setAlpha(0.9f);
            }else if (scrollY>=hideHeight+10 && scrollY<hideHeight+20){
                tvRoomName.setAlpha(0.8f);
            }else if (scrollY>=hideHeight+20 && scrollY<hideHeight+30){
                tvRoomName.setAlpha(0.7f);
            }else if (scrollY>=hideHeight+30 && scrollY<hideHeight+40){
                tvRoomName.setAlpha(0.6f);
            }else if (scrollY>=hideHeight+40 && scrollY<hideHeight+50){
                tvRoomName.setAlpha(0.5f);
            }else if (scrollY>=hideHeight+50 && scrollY<hideHeight+60){
                tvRoomName.setAlpha(0.4f);
            }else if (scrollY>=hideHeight+60 && scrollY<hideHeight+70){
                tvRoomName.setAlpha(0.3f);
            }else if (scrollY>=hideHeight+70 && scrollY<hideHeight+80){
                tvRoomName.setAlpha(0.2f);
            }else if (scrollY>=hideHeight+80 && scrollY<hideHeight+90){
                tvRoomName.setAlpha(0.1f);
            }else if (scrollY>=hideHeight+90 && scrollY<hideHeight+100){
                tvRoomName.setAlpha(0.05f);
            }else if (scrollY>=hideHeight+100){
                tvRoomName.setAlpha(0f);
            }
        }

        @Override
        public void onCommOnTouchEvent(MyPullUpScrollView scrollView, MotionEvent ev) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    y = ev.getY();
                    break;
                case MotionEvent.ACTION_UP:

                    break;
                case MotionEvent.ACTION_MOVE:
                    final float preY = y;
                    float nowY = ev.getY();
                    int deltaY = (int) (preY - nowY) / size;
                    int yy = linear.getTop() - deltaY;
                    break;
                default:
                    break;
            }
        }
    };


    //获取状态栏statusBar的高度
    private int getStatusBar(){
        Resources resources = getActivity().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    //点击事件
    boolean flag = true;
    View.OnClickListener pullMenuListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //隐藏button
            btPullMenu.setVisibility(View.GONE);

            //弹出菜单     smoothScroll
            if (flag==true){
                svPullUpMenu.smoothScrollToSlow(0,-btHeight_X3,1600);
                flag = false;
                pullDown(flag);
            }else if (flag==false){
                svPullUpMenu.smoothScrollToSlow(0,btHeight_X3,1600);
                flag = true;
                pullDown(flag);
            }
            /*Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {

                }
            });*/
        }
    };



    //滑动到隐藏头 初始化
    private void scrollToBottomInit(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                svPullUpMenu.scrollTo(0,btHeight_X3);
                scrollView.scrollTo(0,btHeight);
                flag = true;
                btPullMenu.setVisibility(View.VISIBLE);
            }
        });
    }
    //滑动到隐藏头
    private void scrollToBottom(){
        svPullUpMenu.smoothScrollToSlow(0,btHeight_X3,800);
        flag = true;
        btPullMenu.setVisibility(View.VISIBLE);
        /*Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });*/
    }
    //滑动到显示头
    private void scrollToTop(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                svPullUpMenu.smoothScrollTo(0,-(btHeight_X3));
                flag = false;
            }
        });
    }



    //两个ScrollView联动
    @Override
    public void onScrollConnection(MyPullUpScrollView scrollView0, int x, int y, int oldx, int oldy) {
        scrollView.scrollTo(x,y);
    }

    @Override
    public void onScrollConnectionDown(MyCustomScrollView scrollView, int x, int y, int oldx, int oldy) {
        svPullUpMenu.scrollTo(x,y);
    }


    /*
     *    ---------------------------------点击图标变色---------------------------------
     */
    //---------------------------------------场景-----------------------------------------
    private void changeColorToActive1(int imgId1,int imgId2,int colorId){
        ivXiuxianOut.setImageResource(imgId1);
        ivXiuxianIn.setImageResource(imgId2);
        tvXiuxian.setTextColor(activity.getResources().getColor(colorId));
    }
    private void changeColorToActive2(int imgId1,int imgId2,int imgId3,int colorId){
        ivYule1.setImageResource(imgId1);
        ivYule2.setImageResource(imgId2);
        ivYule3.setImageResource(imgId3);
        tvYule.setTextColor(activity.getResources().getColor(colorId));
    }
    private void changeColorToActive3(int imgId1,int imgId2,int colorId){
        ivJuhui1.setImageResource(imgId1);
        ivJuhui2.setImageResource(imgId2);
        tvJuhui.setTextColor(activity.getResources().getColor(colorId));
    }
    private void changeColorToActive4(int imgId1,int colorId){
        ivLikai.setImageResource(imgId1);
        tvLikai.setTextColor(activity.getResources().getColor(colorId));
    }
    //点击事件
    private void clickXiuxian(){
        changeColorToActive1(R.mipmap.theme2_jiating_xiuxian_active_ani_3x,R.mipmap.theme2_jiating_xiuxian_active_ani_in_3x,R.color.colorTextActive);
        changeColorToActive2(R.mipmap.theme2_jiating_yule_1_3x,R.mipmap.theme2_jiating_yule_2_3x,R.mipmap.theme2_jiating_yule_3_3x,R.color.colorText);
        changeColorToActive3(R.mipmap.theme2_jiating_juhui_ani_3x,R.mipmap. theme2_jiating_juhui_ani_in_3x,R.color.colorText);
        changeColorToActive4(R.mipmap.theme2_jiating_likai_ani_3x,R.color.colorText);
    }
    private void clickYule(){
        changeColorToActive1(R.mipmap.theme2_jiating_xiuxian_ani_3x,R.mipmap.theme2_jiating_xiuxian_ani_in_3x,R.color.colorText);
        changeColorToActive2(R.mipmap.theme2_jiating_yule_active_1_3x,R.mipmap.theme2_jiating_yule_active_2_3x,R.mipmap.theme2_jiating_yule_active_3_3x,R.color.colorTextActive);
        changeColorToActive3(R.mipmap.theme2_jiating_juhui_ani_3x,R.mipmap.theme2_jiating_juhui_ani_in_3x,R.color.colorText);
        changeColorToActive4(R.mipmap.theme2_jiating_likai_ani_3x,R.color.colorText);
    }
    private void clickJuhui(){
        changeColorToActive1(R.mipmap.theme2_jiating_xiuxian_ani_3x,R.mipmap.theme2_jiating_xiuxian_ani_in_3x,R.color.colorText);
        changeColorToActive2(R.mipmap.theme2_jiating_yule_1_3x,R.mipmap.theme2_jiating_yule_2_3x,R.mipmap.theme2_jiating_yule_3_3x,R.color.colorText);
        changeColorToActive3(R.mipmap.theme2_jiating_juhui_active_ani_3x,R.mipmap.theme2_jiating_juhui_active_ani_in_3x,R.color.colorTextActive);
        changeColorToActive4(R.mipmap.theme2_jiating_likai_ani_3x,R.color.colorText);
    }
    private void clickLikai(){
        changeColorToActive1(R.mipmap.theme2_jiating_xiuxian_ani_3x,R.mipmap.theme2_jiating_xiuxian_ani_in_3x,R.color.colorText);
        changeColorToActive2(R.mipmap.theme2_jiating_yule_1_3x,R.mipmap.theme2_jiating_yule_2_3x,R.mipmap.theme2_jiating_yule_3_3x,R.color.colorText);
        changeColorToActive3(R.mipmap.theme2_jiating_juhui_ani_3x,R.mipmap.theme2_jiating_juhui_ani_in_3x,R.color.colorText);
        changeColorToActive4(R.mipmap.theme2_jiating_likai_active_ani_3x,R.color.colorTextActive);
    }

    //----------------------------------窗帘-----------------------------------
    private void changeColorBulian(int imgIdHead,int imgId1,int colorId,String text){
        ivBulianHead.setImageResource(imgIdHead);
        ivBulian1.setImageResource(imgId1);
        ivBulian2.setImageResource(imgId1);
        ivBulian3.setImageResource(imgId1);
        ivBulian4.setImageResource(imgId1);
        ivBulian5.setImageResource(imgId1);
        ivBulian6.setImageResource(imgId1);
        ivBulianHead1.setImageResource(imgIdHead);
        tvBulian.setTextColor(activity.getResources().getColor(colorId));
        tvBulian.setText(text);
    }
    private void changeColorShalian(int imgIdHead,int imgId1,int colorId,String text){
        ivShalianHead.setImageResource(imgIdHead);
        ivShalian1.setImageResource(imgId1);
        ivShalian2.setImageResource(imgId1);
        ivShalian3.setImageResource(imgId1);
        ivShalian4.setImageResource(imgId1);
        ivShalian5.setImageResource(imgId1);
        ivShalian6.setImageResource(imgId1);
        ivShalianHead1.setImageResource(imgIdHead);
        tvShalian.setTextColor(activity.getResources().getColor(colorId));
        tvShalian.setText(text);
    }
    private void changeColorAll(int imgId1,int colorId,String text){
        ivAll.setImageResource(imgId1);
        tvAll.setTextColor(activity.getResources().getColor(colorId));
        tvAll.setText(text);
    }
    private void changeColorStop(int imgId1,int colorId){
        ivStop.setImageResource(imgId1);
        tvStop.setTextColor(activity.getResources().getColor(colorId));
    }

    private void changeColorJuanlian(int imgIdHead,int imgId1,int colorId,String text){
        ivJuanlianHead.setImageResource(imgIdHead);
        ivJuanlian1.setImageResource(imgId1);
        ivJuanlian2.setImageResource(imgId1);
        ivJuanlian3.setImageResource(imgId1);
        ivJuanlian4.setImageResource(imgId1);
        ivJuanlian5.setImageResource(imgId1);
        ivJuanlian6.setImageResource(imgId1);
        ivJuanlianHead1.setImageResource(imgIdHead);
        tvJuanlian.setTextColor(activity.getResources().getColor(colorId));
        tvJuanlian.setText(text);
    }
    private void changeColorJuanlianR(int imgIdHead,int imgId1,int colorId,String text){
        ivJuanlianHeadR.setImageResource(imgIdHead);
        ivJuanlian1R.setImageResource(imgId1);
        ivJuanlian2R.setImageResource(imgId1);
        ivJuanlian3R.setImageResource(imgId1);
        ivJuanlian4R.setImageResource(imgId1);
        ivJuanlian5R.setImageResource(imgId1);
        ivJuanlian6R.setImageResource(imgId1);
        ivJuanlianHead1R.setImageResource(imgIdHead);
        tvJuanlianR.setTextColor(activity.getResources().getColor(colorId));
        tvJuanlianR.setText(text);
    }
    private void changeColorAllJuan(int imgId1,int colorId,String text){
        ivAllJuan.setImageResource(imgId1);
        tvAllJuan.setTextColor(activity.getResources().getColor(colorId));
        tvAllJuan.setText(text);
    }
    private void changeColorStopJuan(int imgId1,int colorId){
        ivStopJuan.setImageResource(imgId1);
        tvStopJuan.setTextColor(activity.getResources().getColor(colorId));
    }

    //点击
    private int bulianFlag = 0;
    private int shalianFlag = 0;
    private int AllFlag = 0;
    private int StopFlag = 0;

    private int juanlianFlag = 0;
    private int juanlianRFlag = 0;
    private int AllFlagJuan = 0;
    private int StopFlagJuan = 0;

    private void clickBulian(){
        if (bulianFlag==0){
            changeColorBulian(R.mipmap.theme2_chuanglian_head_active_3x,R.mipmap.theme2_chuanglian_solidline_active_3x,R.color.colorTextActive,"布帘 开");
            bulianFlag = 1;
        }else if (bulianFlag==1){
            changeColorBulian(R.mipmap.theme2_chuanglian_head_3x,R.mipmap.chuanglian_solidline_3,R.color.colorText,"布帘 关");
            bulianFlag = 0;
        }
    }
    private void clickShalian(){
        if (shalianFlag==0){
            changeColorShalian(R.mipmap.theme2_chuanglian_head_active_3x,R.mipmap.theme2_chuanglian_viualline_active_3x,R.color.colorTextActive,"纱帘 开");
            shalianFlag = 1;
        }else if (shalianFlag==1){
            changeColorShalian(R.mipmap.theme2_chuanglian_head_3x,R.mipmap.theme2_chuanglian_viualline_3x,R.color.colorText,"纱帘 关");
            shalianFlag = 0;
        }
    }
    private void clickAll(){
        if (AllFlag==0){
            changeColorAll(R.mipmap.theme2_chuanglian_quanguan_open_3x,R.color.colorTextActive,"全 开");
            AllFlag = 1;
        }else if (AllFlag==1){
            changeColorAll(R.mipmap.theme2_chuanglian_quanguan_close_3x,R.color.colorText,"全 关");
            AllFlag = 0;
        }
    }
    private void clickStop(){
        if (StopFlag==0){
            changeColorStop(R.mipmap.theme2_chuanglian_zanting_active_3x,R.color.colorTextActive);
            StopFlag = 1;
        }else if (StopFlag==1){
            changeColorStop(R.mipmap.theme2_chuanglian_zanting_3x,R.color.colorText);
            StopFlag = 0;
        }
    }

    private void clickJuanlian(){
        if (juanlianFlag==0){
            changeColorJuanlian(R.mipmap.theme2_chuanglian_head_active_3x,R.mipmap.theme2_juanlian_solidline_active_3x,R.color.colorTextActive,"左卷帘 开");
            juanlianFlag = 1;
        }else if (juanlianFlag==1){
            changeColorJuanlian(R.mipmap.theme2_chuanglian_head_3x,R.mipmap.theme2_juanlian_solidline_3x,R.color.colorText,"左卷帘 关");
            juanlianFlag = 0;
        }
    }
    private void clickJuanlianR(){
        if (juanlianRFlag==0){
            changeColorJuanlianR(R.mipmap.theme2_chuanglian_head_active_3x,R.mipmap.theme2_juanlian_solidline_active_3x,R.color.colorTextActive,"右卷帘 开");
            juanlianRFlag = 1;
        }else if (juanlianRFlag==1){
            changeColorJuanlianR(R.mipmap.theme2_chuanglian_head_3x,R.mipmap.theme2_juanlian_solidline_3x,R.color.colorText,"右卷帘 关");
            juanlianRFlag = 0;
        }
    }
    private void clickAllJuan(){
        if (AllFlagJuan==0){
            changeColorAllJuan(R.mipmap.theme2_chuanglian_quanguan_open_3x,R.color.colorTextActive,"全 开");
            AllFlagJuan = 1;
        }else if (AllFlagJuan==1){
            changeColorAllJuan(R.mipmap.theme2_chuanglian_quanguan_close_3x,R.color.colorText,"全 关");
            AllFlagJuan = 0;
        }
    }
    private void clickStopJuan(){
        if (StopFlagJuan==0){
            changeColorStopJuan(R.mipmap.theme2_chuanglian_zanting_active_3x,R.color.colorTextActive);
            StopFlagJuan = 1;
        }else if (StopFlagJuan==1){
            changeColorStopJuan(R.mipmap.theme2_chuanglian_zanting_3x,R.color.colorText);
            StopFlagJuan = 0;
        }
    }



    //----------------------------------------空调-----------------------------------------
    private void changeColorAirSwitch(int imgId,int colorId,String text){
        ivKongtiaoSwitch.setImageResource(imgId);
        tvKongtiaoSwitch.setTextColor(activity.getResources().getColor(colorId));
        tvKongtiaoSwitch.setText(text);
    }
    //点击
    private int airSwitchFlag = 0;
    private void clickAirSwitch(){
        rlKongtiao.setClickable(false);   //点击后设为不可点击
        if (airSwitchFlag==0){
            changeColorAirSwitch(R.mipmap.theme2_kongtiao_fan_active_3x,R.color.colorTextActive,"空调 开");
            linearAirDetails.setVisibility(View.VISIBLE);
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            linearAirDetails.measure(w, h);
            AirConditionHeight = linearAirDetails.getMeasuredHeight();         //空调控制部分的高度
            Log.i(TAG, "onClick: ---------------------------"+AirConditionHeight);
            svPullUpMenu.smoothScrollBySlow(0,AirConditionHeight,2000);
            /*Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    svPullUpMenu.smoothScrollBySlow(0,AirConditionHeight,2000);
                }
            });*/

            //设置点击之后2秒内不能点击
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        rlKongtiao.setClickable(true);
                        Message message = new Message();
                        message.what = 2;
                        handlerAir.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            airSwitchFlag = 1;
        }else if (airSwitchFlag==1){
            changeColorAirSwitch(R.mipmap.theme2_kongtiao_fan_3x,R.color.colorText,"空调 关");
            svPullUpMenu.smoothScrollBySlow(0,-AirConditionHeight,2000);


            //开个线程，睡2秒，隐藏空调调温部分
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        rlKongtiao.setClickable(true);
                        Message message = new Message();
                        message.what = 1;
                        handlerAir.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            airSwitchFlag = 0;
        }
    }


    /*
     *    ----------------------------------------------------手势------------------------------------------------------
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return true;
    }


    @Override
    public void onShowPress(MotionEvent motionEvent) {
         Log.i(TAG, "onShowPress: ---------");
        if (scrollY==0){
            svPullUpMenu.smoothScrollToSlow(0,btHeight_X3,600);
            flag = true;
            pullDown(flag);
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.i(TAG, "onSingleTapUp: ---------");
        if (scrollY==0){
            svPullUpMenu.smoothScrollToSlow(0,btHeight_X3,600);
            flag = true;
            pullDown(flag);
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        //Log.i("result", "onScroll:---------------- "+motionEvent1);
        //Log.i(TAG, "onScroll: =================="+v);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        //Log.i(TAG, "onLongPress: ------------");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        try {
            if (motionEvent.getX() - motionEvent1.getX() < -89) {
                flingLeft();
                return true;
            } else if (motionEvent.getX() - motionEvent1.getX() > 89) {
                flingRight();
                return true;
            }else if (motionEvent.getY() - motionEvent1.getY() > 89) {
                flingUp();       //上滑
                return true;
            }else if (motionEvent.getY() - motionEvent1.getY() < -89) {
                flingDown();       //下滑
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void flingLeft() {//自定义方法：处理向左滑动事件
        //Log.i("result", "flingLeft:------left-------");
    }

    public void flingRight() {//自定义方法：处理向右滑动事件
        //Log.i("result", "flingLeft:------right-------");
    }

    public void flingUp() {          //自定义方法：处理向上滑动事件
        /*Log.i(TAG, "flingUp: 上滑");
        scrollToBottom();        //滑动到隐藏头
        pullDown(true);*/
    }

    public void flingDown() {        //自定义方法：处理向下滑动事件
        /*scrollToTop();
        pullDown(false);
        Log.i(TAG, "flingUp: 下滑");*/
    }


}
