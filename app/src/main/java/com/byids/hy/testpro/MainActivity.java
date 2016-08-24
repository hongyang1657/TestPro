package com.byids.hy.testpro;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements Animation.AnimationListener{
    private Button button;
    private ImageView imageView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //testAnimation();
                scrollToPosition();
            }
        });
    }

    private void testAnimation(){
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0.2f);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(1000);
        animationSet.addAnimation(translateAnimation);
        imageView.startAnimation(animationSet);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void scrollToPosition(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageView.scrollTo(0,800);
            }
        });
    }
}
