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

    private int btHeight;     //头部菜单的高度
    private int btHeight_X3;  //头部菜单高度的三倍  (因为两个ScrollView的联动为3倍率)
    private int AirConditionHeight;        //空调控制部分的布局高度
    private boolean isHeadShown = false;     //头菜单是否显示


    private ImageView ivBackGround;   //背景图片
    private ImageView ivBackGroundTrans;      //切换背景图片

    private MyCustomScrollView scrollView;
    private int roomIndex;     //房间标示
    private String roomName;   //房间名
    private GestureDetector detector;
    //private MyMainActivity.MyOntouchListener listener;
    private Activity activity;
    private ImageView btPullMenu;
    private RelativeLayout linear;

    //上拉菜单
    MyPullUpScrollView svPullUpMenu;
    PullDownMenuListener pullDownMenuListener;//自定义的下拉监听
    ViewGroup.LayoutParams params;

    private int scrollY; //下滑的距离
    private static final int size = 4;
    private float y;

    //---------------------控制部分 上---------------------
    private TextView tvRoomName;
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
    private RelativeLayout rlLight;
    private ImageView ivLightSwitch;
    private TextView tvLightSwitch;
    private TextView tvLightValue;    //灯光亮度值

    //窗帘
    private RelativeLayout rlBulian;
    private ImageView ivBulianHead;
    private ImageView ivBulian1;
    private ImageView ivBulian2;
    private ImageView ivBulian3;
    private ImageView ivBulian4;
    private ImageView ivBulian5;
    private ImageView ivBulian6;
    private ImageView ivBulianHead1;
    private TextView tvBulian;

    private RelativeLayout rlShalian;
    private ImageView ivShalianHead;
    private ImageView ivShalian1;
    private ImageView ivShalian2;
    private ImageView ivShalian3;
    private ImageView ivShalian4;
    private ImageView ivShalian5;
    private ImageView ivShalian6;
    private ImageView ivShalianHead1;
    private TextView tvShalian;

    private RelativeLayout rlAll;
    private ImageView ivAll;       //窗帘，全关 全开
    private TextView tvAll;

    private RelativeLayout rlStop;
    private ImageView ivStop;
    private TextView tvStop;

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
                    Log.i(TAG, "handleMessage: 111111111111111111111111");
                    break;
                default:
                    break;
            }
        }
    };

    private int[] backList;  //背景图片组
    public MyFragment(int roomIndex, String roomName,int[] backList) {
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
        scrollToBottomInit();   //初始化ScrollView的位置


        btPullMenu.setOnClickListener(pullMenuListener);
        //上拉菜单   子scrollview
        svPullUpMenu = (MyPullUpScrollView) view.findViewById(R.id.scroll_pull_up);
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
                int mScrollY = scroller.getScrollY();
                Log.i(TAG, "handleStop: ----------mScrollY----------"+mScrollY);
                if (mScrollY>(btHeight_X3/2)&&mScrollY<=btHeight_X3){
                    scrollToBottom();        //滑动到隐藏头
                    isHeadShown = false;
                    pullDown(true);

                }else if(mScrollY<=(btHeight_X3/2)){
                    scrollToTop();           //滑动到显示头
                    isHeadShown = true;
                    pullDown(false);
                }
            }
        });

        /*svPullUpMenu.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.i(, "onScrollChange: ");
            }
        });*/

        //获取控件高度
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        linearMenu.measure(w, h);
        btHeight = linearMenu.getMeasuredHeight();         //头菜单的高度
        btHeight_X3 = btHeight*3;
        Log.i("result", "onCreateView: ----kongjian-----"+btHeight);

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
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        params = ivBackGround.getLayoutParams();
        int statusHeight = getStatusBar();
        params.height = height - statusHeight+500;       //设置图片长度，使上拉时有图片上升的一个效果
        params.width = width;
        //Log.i("result", "onCreateView:------------高度 "+(height - statusHeight)+"------宽度"+width);
        ivBackGround.setLayoutParams(params);
        ivBackGroundTrans.setLayoutParams(params);
        svPullUpMenu.setLayoutParams(params);   //设置上拉菜单全屏

        //设置头部控件高度和点击按钮一致
        ViewGroup.LayoutParams layoutParams = linearClick.getLayoutParams();
        layoutParams.height = btHeight;
        linearClick.setLayoutParams(layoutParams);


        //手势
        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), this);
        MyMainActivity.MyOnTouchListener myOnTouchListener = new MyMainActivity.MyOnTouchListener() {
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
        //手势
        activity = getActivity();
        //gestureDetector = new GestureDetector(activity,new MyCustomGesture());

        /*listener = new MyMainActivity.MyOntouchListener(){

            @Override
            public void onTouchEvent(MotionEvent event) {
                gestureDetector.onTouchEvent(event);
            }
        };
        ((MyMainActivity) activity).registerListener(listener);*/
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

        tvRoomName.setText(roomName);    //设置房间名

        initControler();    //初始化控制部分  下
        initBackGround();   //初始化背景图片
    }

    private void initControler(){
        //场景
         rlXiuxian = (RelativeLayout) view.findViewById(R.id.rl_xiuxian);
         rlYule = (RelativeLayout) view.findViewById(R.id.rl_yule);
         rlJuhui = (RelativeLayout) view.findViewById(R.id.rl_juhui);
         rlLikai = (RelativeLayout) view.findViewById(R.id.rl_likai);
        //灯光
         hsLightValue = (HorizontalScrollView) view.findViewById(R.id.hs_light_point);
         rlLight = (RelativeLayout) view.findViewById(R.id.relative_light_switch);
        //窗帘
         rlBulian = (RelativeLayout) view.findViewById(R.id.rl_bulian);
         rlShalian = (RelativeLayout) view.findViewById(R.id.rl_shalian);
         rlAll = (RelativeLayout) view.findViewById(R.id.rl_quanguan);
         rlStop = (RelativeLayout) view.findViewById(R.id.rl_tingzhi);
        //空调
         rlKongtiao = (RelativeLayout) view.findViewById(R.id.rl_kongtiao_kaiguan);
         tvKongtiaoTemp = (TextView) view.findViewById(R.id.tv_kongtiao_wendu);
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
        //全开 全关
        ivAll = (ImageView) view.findViewById(R.id.iv_chuanglian_all);
        tvAll = (TextView) view.findViewById(R.id.tv_all);
        ivStop = (ImageView) view.findViewById(R.id.iv_chuanglian_stop);
        tvStop = (TextView) view.findViewById(R.id.tv_chuanglian_stop);
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
        rlKongtiao.setOnClickListener(controlListener);
        rlKongtiaoMoshi.setOnClickListener(controlListener);
        rlAirControl1.setOnClickListener(controlListener);
        rlAirControl2.setOnClickListener(controlListener);
        rlAirControl3.setOnClickListener(controlListener);

    }

    int airFlag = 0;
    View.OnClickListener controlListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_xiuxian:
                    setScaleAnimation(rlXiuxian);
                    Toast.makeText(activity, "休闲", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_yule:
                    setScaleAnimation(rlYule);
                    Toast.makeText(activity, "娱乐", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_juhui:
                    setScaleAnimation(rlJuhui);
                    Toast.makeText(activity, "聚会", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_likai:
                    setScaleAnimation(rlLikai);
                    Toast.makeText(activity, "离开", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.relative_light_switch:
                    setScaleAnimation(rlLight);
                    Toast.makeText(activity, "灯 开关", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_bulian:
                    setScaleAnimation(rlBulian);
                    Toast.makeText(activity, "布帘", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_shalian:
                    setScaleAnimation(rlShalian);
                    Toast.makeText(activity, "纱帘", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_quanguan:
                    setScaleAnimation(rlAll);
                    Toast.makeText(activity, "全关", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_tingzhi:
                    setScaleAnimation(rlStop);
                    Toast.makeText(activity, "停止", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_kongtiao_kaiguan:
                    setScaleAnimation(rlKongtiao);
                    if (airFlag==0){
                        linearAirDetails.setVisibility(View.VISIBLE);
                        airFlag = 1;
                        Toast.makeText(activity, "空调开", Toast.LENGTH_SHORT).show();
                        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        linearAirDetails.measure(w, h);
                        AirConditionHeight = linearAirDetails.getMeasuredHeight();         //空调控制部分的高度
                        Log.i(TAG, "onClick: ---------------------------"+AirConditionHeight);


                        /*Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                svPullUpMenu.smoothScrollTo(0,1537+AirConditionHeight);
                            }
                        });*/



                    }else if (airFlag==1){
                        linearAirDetails.setVisibility(View.GONE);
                        airFlag = 0;
                        Toast.makeText(activity, "空调关", Toast.LENGTH_SHORT).show();
                    }
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

    //初始化背景图片
    private void initBackGround(){
        //随机取出一张图片
        Random random = new Random();
        ivBackGround.setImageResource(backList[random.nextInt(backList.length)]);
        ivBackGroundTrans.setImageResource(backList[random.nextInt(backList.length)]);

        //开线程  隔一段时间切换图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(8000);
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
                    //ivBackGround.setImageResource(backList[random1.nextInt(backList.length)]);
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
                    //ivBackGroundTrans.setImageResource(backList[random1.nextInt(backList.length)]);
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
            //加入手势，松开的时候 判断距离，选择菜单栏出现还是隐藏的动画
            scrollY = y;
            //Log.i("result", "onScrollChanged:-----------x-----------"+x+"----------y---------"+y);
            //Log.i(TAG, "onScrollChanged: -------scrollY--------"+scrollY);

            //判断向下滑的时候(y<控件的高度)    button消失
            if (scrollY==btHeight_X3) {           //乘以三是因为手指移动和滑动是三倍率
                btPullMenu.setVisibility(View.VISIBLE);
            }else {
                btPullMenu.setVisibility(View.GONE);
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

            //两种方式弹出菜单：1.smoothScroll    2.属性动画
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (flag==true){
                        svPullUpMenu.smoothScrollTo(0,-btHeight_X3);
                        flag = false;
                        pullDown(flag);
                    }else if (flag==false){
                        svPullUpMenu.smoothScrollTo(0,btHeight_X3);
                        flag = true;
                    }
                }
            });
        }
    };



    //滑动到隐藏头 初始化
    private void scrollToBottomInit(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                svPullUpMenu.smoothScrollTo(0,btHeight_X3);
                scrollView.smoothScrollTo(0,btHeight);
                flag = true;
                btPullMenu.setVisibility(View.VISIBLE);
            }
        });
    }
    //滑动到隐藏头
    private void scrollToBottom(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                svPullUpMenu.smoothScrollTo(0,btHeight_X3);
                flag = true;
                btPullMenu.setVisibility(View.VISIBLE);
            }
        });
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
     *    ----------------------------------------------------手势------------------------------------------------------
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.i(TAG, "onDown: ----------");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        //Log.i(TAG, "onShowPress: ---------");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        //Log.i(TAG, "onSingleTapUp: ---------");
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
            }else if (motionEvent.getY() - motionEvent1.getY() > 89) {
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
        //Log.i(TAG, "flingUp: 上滑");
        //scrollToBottom();        //滑动到隐藏头
        pullDown(true);
    }

    public void flingDown() {        //自定义方法：处理向下滑动事件
        //scrollToTop();
        //pullDown(false);
    }


}
