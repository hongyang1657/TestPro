package com.byids.hy.testpro.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.byids.hy.testpro.R;
import com.byids.hy.testpro.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 手势测试
 * Created by hy on 2016/8/15.
 */
public class TestActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_item1);

    }


}
